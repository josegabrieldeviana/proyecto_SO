/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.Proceso;
import Modelo.EDD.Lista;
import Modelo.EDD.Nodo;
import javax.swing.table.DefaultTableModel;

/**
 * Implementación del algoritmo de planificación Round Robin.
 * Este algoritmo asigna un quantum de tiempo a cada proceso y los ejecuta
 * de manera circular, permitiendo que todos los procesos tengan acceso justo a la CPU.
 * 
 * @author joseg
 */
public class RoundRobin {
    /**
     * Debo pasar el RTOSmaster como un parametro, puesto que en cada aplicación
     * sistemas de planificación, el cambio de estados implican interrupciones
     * que cambian de modo al RTOS. 
     */
    public RTOSmaster RTOS;
    
    // Cola de procesos listos para ejecutar
    private Lista<Proceso> colaListos;
    
    // Proceso actualmente en ejecución
    private Proceso procesoEnEjecucion;
    
    // Quantum: tiempo máximo que un proceso puede ejecutarse antes de ser interrumpido
    private int quantum;
    
    // Tiempo restante del quantum actual
    private int tiempoRestanteQuantum;
    
    // Modelo de tabla para actualizar la interfaz
    private DefaultTableModel modeloTablaListos;
    private DefaultTableModel modeloTablaEjecucion;
    private DefaultTableModel modeloTablaTerminados;
    
    // Contador de ciclos totales ejecutados
    private int ciclosTotales;
    
    // Estadísticas
    private int procesosCompletados;
    private int cambiosDeContexto;

    /**
     * Constructor de Round Robin
     * 
     * @param RTOS Referencia al sistema operativo
     * @param quantum Tiempo de quantum (por defecto 3-5 ciclos)
     */
    public RoundRobin(RTOSmaster RTOS, int quantum) {
        this.RTOS = RTOS;
        this.colaListos = new Lista<>();
        this.procesoEnEjecucion = null;
        this.quantum = quantum;
        this.tiempoRestanteQuantum = quantum;
        this.ciclosTotales = 0;
        this.procesosCompletados = 0;
        this.cambiosDeContexto = 0;
    }
    
    /**
     * Constructor alternativo con quantum por defecto
     * 
     * @param RTOS Referencia al sistema operativo
     */
    public RoundRobin(RTOSmaster RTOS) {
        this(RTOS, 4); // Quantum por defecto: 4 ciclos
    }

    /**
     * Agrega un proceso a la cola de listos
     * 
     * @param p Proceso a agregar
     */
    public void agregarProceso(Proceso p) {
        if (p == null) {
            System.err.println("[ERROR RR] Intento de agregar proceso nulo");
            return;
        }
        
        // Cambiar estado del proceso a LISTO
        p.setSListo();
        
        // Agregar al final de la cola
        colaListos.addLast(p);
        
        System.out.println("[Round Robin] Proceso " + p.getID() + " agregado a cola de listos");
    }

    /**
     * Ejecuta un ciclo del algoritmo Round Robin
     * Este es el método principal que implementa la lógica del algoritmo
     */
    public void ejecutarCiclo() {
        // Si no hay proceso en ejecución, tomar el siguiente de la cola
        if (procesoEnEjecucion == null) {
            if (!colaListos.isEmpty()) {
                procesoEnEjecucion = colaListos.deleteFirst();
                if (procesoEnEjecucion != null) {
                    // Cambiar estado a EJECUCION
                    procesoEnEjecucion.setStatus("EJECUCION");
                    tiempoRestanteQuantum = quantum;
                    cambiosDeContexto++;
                    
                    System.out.println("[Round Robin] Proceso " + procesoEnEjecucion.getID() + 
                                     " comienza ejecución (Quantum: " + quantum + ")");
                }
            } else {
                System.out.println("[Round Robin] No hay procesos en cola de listos");
                return;
            }
        }
        
        // Si hay proceso en ejecución
        if (procesoEnEjecucion != null) {
            // Ejecutar 1 ciclo del proceso
            ejecutarCicloProceso(procesoEnEjecucion);
            
            // Decrementar quantum restante
            tiempoRestanteQuantum--;
            ciclosTotales++;
            
            // Decrementar tiempo restante del proceso (simulación)
            int instruccionesRestantes = procesoEnEjecucion.getCantidadInstrucciones();
            
            System.out.println("[Round Robin] Ciclo " + ciclosTotales + 
                             " - Proceso " + procesoEnEjecucion.getID() + 
                             " - Quantum restante: " + tiempoRestanteQuantum +
                             " - Instrucciones restantes: " + instruccionesRestantes);
            
            // Verificar si el proceso terminó
            if (instruccionesRestantes <= 1) {
                // Proceso terminado
                procesoEnEjecucion.setSTerminado();
                procesoEnEjecucion.setCantidadInstrucciones(0);
                procesosCompletados++;
                
                System.out.println("[Round Robin] Proceso " + procesoEnEjecucion.getID() + 
                                 " TERMINADO (Total completados: " + procesosCompletados + ")");
                
                procesoEnEjecucion = null;
                tiempoRestanteQuantum = quantum;
            }
            // Verificar si el quantum se agotó
            else if (tiempoRestanteQuantum <= 0) {
                // Quantum agotado, mover al final de la cola
                procesoEnEjecucion.setSListo();
                
                System.out.println("[Round Robin] Quantum agotado - Proceso " + 
                                 procesoEnEjecucion.getID() + " vuelve a cola de listos");
                
                colaListos.addLast(procesoEnEjecucion);
                procesoEnEjecucion = null;
                tiempoRestanteQuantum = quantum;
            }
        }
    }

    /**
     * Ejecuta un ciclo de un proceso específico
     * Simula la ejecución incrementando PC y MAR, y decrementando instrucciones
     * 
     * @param p Proceso a ejecutar
     */
    private void ejecutarCicloProceso(Proceso p) {
        // Incrementar Program Counter
        p.setPC(p.getPC() + 1);
        
        // Incrementar Memory Address Register
        p.setMAR(p.getMAR() + 1);
        
        // Decrementar cantidad de instrucciones restantes
        int instruccionesRestantes = p.getCantidadInstrucciones();
        if (instruccionesRestantes > 0) {
            p.setCantidadInstrucciones(instruccionesRestantes - 1);
        }
        
        // Decrementar tiempo restante de deadline (si aplica)
        int tiempoRestante = p.getTiempoRestanteDeadline();
        if (tiempoRestante > 0) {
            p.setTiempoRestanteDeadline(tiempoRestante - 1);
        }
    }

    /**
     * Cambia el contexto entre procesos
     * Simula el cambio de modo del RTOS (kernel/usuario)
     */
    public void cambiarContexto() {
        if (RTOS != null) {
            // Cambiar a modo kernel para el cambio de contexto
            RTOS.PSW = 0; // Modo KERNEL
            
            System.out.println("[Round Robin] Cambio de contexto - Modo KERNEL");
            
            // Aquí se guardaría el estado del proceso actual
            // y se cargaría el estado del siguiente proceso
            
            // Después del cambio, volver a modo usuario
            RTOS.PSW = 1; // Modo USUARIO
            
            System.out.println("[Round Robin] Cambio de contexto completado - Modo USUARIO");
        }
    }

    /**
     * Actualiza las tablas de la interfaz con el estado actual
     * 
     * @param modeloListos Modelo de tabla para procesos listos
     * @param modeloEjecucion Modelo de tabla para proceso en ejecución
     * @param modeloTerminados Modelo de tabla para procesos terminados
     */
    public void actualizarInterfaz(DefaultTableModel modeloListos, 
                                   DefaultTableModel modeloEjecucion, 
                                   DefaultTableModel modeloTerminados) {
        // Limpiar tablas
        if (modeloListos != null) {
            modeloListos.setRowCount(0);
        }
        if (modeloEjecucion != null) {
            modeloEjecucion.setRowCount(0);
        }
        
        // Actualizar tabla de procesos listos
        if (modeloListos != null && !colaListos.isEmpty()) {
            Nodo<Proceso> aux = colaListos.Head;
            while (aux != null) {
                Proceso p = aux.getData();
                modeloListos.addRow(new Object[] {
                    p.getID(),
                    p.getPrioridad(),
                    p.getNombre(),
                    p.getMAR(),
                    p.getPC(),
                    p.getTiempoRestanteDeadline()
                });
                aux = aux.getNext();
            }
        }
        
        // Actualizar tabla de proceso en ejecución
        if (modeloEjecucion != null && procesoEnEjecucion != null) {
            modeloEjecucion.addRow(new Object[] {
                procesoEnEjecucion.getID(),
                procesoEnEjecucion.getPrioridad(),
                procesoEnEjecucion.getNombre(),
                procesoEnEjecucion.getMAR(),
                procesoEnEjecucion.getPC(),
                procesoEnEjecucion.getTiempoRestanteDeadline()
            });
        }
    }

    /**
     * Obtiene estadísticas del algoritmo
     * 
     * @return String con las estadísticas formateadas
     */
    public String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS ROUND ROBIN ===\n");
        stats.append("Quantum configurado: ").append(quantum).append(" ciclos\n");
        stats.append("Ciclos totales ejecutados: ").append(ciclosTotales).append("\n");
        stats.append("Procesos completados: ").append(procesosCompletados).append("\n");
        stats.append("Cambios de contexto: ").append(cambiosDeContexto).append("\n");
        stats.append("Procesos en cola de listos: ").append(colaListos.size()).append("\n");
        stats.append("Proceso en ejecución: ");
        if (procesoEnEjecucion != null) {
            stats.append(procesoEnEjecucion.getID()).append(" (").append(procesoEnEjecucion.getNombre()).append(")");
        } else {
            stats.append("Ninguno");
        }
        stats.append("\n");
        stats.append("================================\n");
        
        return stats.toString();
    }

    /**
     * Ejecuta la simulación completa hasta que todos los procesos terminen
     */
    public void ejecutarSimulacionCompleta() {
        System.out.println("\n[Round Robin] Iniciando simulación completa...\n");
        
        int maxCiclos = 1000; // Límite de seguridad
        int ciclo = 0;
        
        while ((!colaListos.isEmpty() || procesoEnEjecucion != null) && ciclo < maxCiclos) {
            ejecutarCiclo();
            ciclo++;
            
            // Pequeña pausa para visualización (opcional)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("[ERROR] Interrupción en simulación: " + e.getMessage());
            }
        }
        
        System.out.println("\n[Round Robin] Simulación completada");
        System.out.println(obtenerEstadisticas());
    }

    // ==================== GETTERS Y SETTERS ====================

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        if (quantum > 0) {
            this.quantum = quantum;
            System.out.println("[Round Robin] Quantum actualizado a: " + quantum);
        } else {
            System.err.println("[ERROR RR] Quantum debe ser mayor a 0");
        }
    }

    public Lista<Proceso> getColaListos() {
        return colaListos;
    }

    public Proceso getProcesoEnEjecucion() {
        return procesoEnEjecucion;
    }

    public int getTiempoRestanteQuantum() {
        return tiempoRestanteQuantum;
    }

    public int getCiclosTotales() {
        return ciclosTotales;
    }

    public int getProcesosCompletados() {
        return procesosCompletados;
    }

    public int getCambiosDeContexto() {
        return cambiosDeContexto;
    }

    /**
     * Reinicia el planificador Round Robin
     */
    public void reiniciar() {
        colaListos.vaciar();
        procesoEnEjecucion = null;
        tiempoRestanteQuantum = quantum;
        ciclosTotales = 0;
        procesosCompletados = 0;
        cambiosDeContexto = 0;
        
        System.out.println("[Round Robin] Planificador reiniciado");
    }
}
