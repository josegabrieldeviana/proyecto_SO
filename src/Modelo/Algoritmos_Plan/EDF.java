/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.EDD.Lista;
import Modelo.EDD.Nodo;
import Modelo.clasesSO.Proceso;
import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.RelojSO;

/**
 * Implementación del algoritmo de planificación EDF (Earliest Deadline First).
 *
 * Política: en cada tick del RelojSO se selecciona el proceso READY
 * con el menor tiempoRestanteDeadline para ejecutarse en la CPU.
 * Si un proceso con deadline menor llega mientras la CPU está ocupada,
 * se realiza una apropiación (preemption) inmediata.
 *
 * Extiende Thread para estar sincronizado con el RelojSO mediante su
 * semáforo CicloEvent (1 permiso = 1 tick), igual que SRT.
 *
 * NO usa librerías externas.
 *
 * @author joseg
 */
public class EDF extends Thread {

    // ─────────────────────────────── Atributos del sistema ────────────────────
    /** Referencia al RTOS para cambios de modo kernel/usuario. */
    public RTOSmaster RTOS;

    /**
     * Lista de colas por estado:
     * índice 0 = NEW
     * índice 1 = READY
     * índice 2 = RUNNING
     * índice 3 = BLOCKED
     * índice 4 = READY SUSPENDED
     * índice 5 = BLOCKED SUSPENDED
     * índice 6 = EXIT
     */
    public Lista<Lista<Proceso>> colasPorEstado;

    /** Reloj del sistema operativo. Proporciona el semáforo de ticks. */
    public RelojSO reloj;

    // ─────────────────────────────── Control del hilo ─────────────────────────
    /** Bandera volátil para detener el planificador desde fuera del hilo. */
    private volatile boolean running = true;

    // ─────────────────────────────── Estadísticas ─────────────────────────────
    /** Número de procesos que completaron su ejecución antes del deadline. */
    private int procesosCompletados;

    /** Número de procesos que no cumplieron su deadline (deadline miss). */
    private int deadlinesMissed;

    /** Número total de ticks procesados desde el inicio. */
    private int ticksTotales;

    /**
     * Número total de cambios de contexto (preemptions + asignaciones iniciales).
     */
    private int cambiosDeContexto;

    // ──────────────────────────────────────────────────────────────────────────
    // CONSTRUCTORES
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Constructor principal de EDF.
     *
     * Inicializa el planificador y mueve todos los procesos en estado NEW
     * a la cola READY para que estén disponibles desde el primer tick.
     *
     * @param RTOS           Referencia al sistema operativo maestro.
     * @param colasPorEstado Lista de listas, una por cada estado del proceso.
     * @param reloj          Reloj del SO que emite ticks periódicos.
     */
    public EDF(RTOSmaster RTOS, Lista<Lista<Proceso>> colasPorEstado, RelojSO reloj) {
        this.RTOS = RTOS;
        this.colasPorEstado = colasPorEstado;
        this.reloj = reloj;
        this.procesosCompletados = 0;
        this.deadlinesMissed = 0;
        this.ticksTotales = 0;
        this.cambiosDeContexto = 0;

        // Mover todos los procesos NEW → READY al inicializar
        System.out.println("EDF -> Inicializando planificador. Moviendo procesos NEW a READY...");
        Lista<Proceso> colaNuevo = colasPorEstado.BuscarPosicion(0);
        if (colaNuevo != null) {
            // Recorremos la cola NEW y movemos cada proceso a READY
            // Usamos un iterador auxiliar para no modificar la lista mientras la recorremos
            Lista<Proceso> copiaNew = new Lista<>();
            Nodo<Proceso> aux = colaNuevo.Head;
            while (aux != null) {
                copiaNew.addLast(aux.getData());
                aux = aux.getNext();
            }
            // Ahora movemos cada uno a READY
            Nodo<Proceso> auxCopia = copiaNew.Head;
            while (auxCopia != null) {
                Proceso p = auxCopia.getData();
                p.cambiarEstado("READY", colasPorEstado);
                auxCopia = auxCopia.getNext();
            }
        }
        System.out.println("EDF -> Inicialización completa.");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // HILO PRINCIPAL (run)
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Bucle principal del planificador EDF.
     *
     * Por cada tick del RelojSO:
     * 1. Decrementa el tiempoRestanteDeadline de todos los procesos activos.
     * 2. Ejecuta un tick en el proceso RUNNING (si existe).
     * 3. Aplica la lógica EDF de selección/apropiación.
     * 4. Imprime un snapshot completo del estado del sistema en consola.
     */
    @Override
    public void run() {
        System.out.println("EDF -> Planificador iniciado.");
        while (running) {
            try {
                // ── 0) Sincronización con el reloj ─────────────────────────
                reloj.getCicloEvent().acquire();
                ticksTotales++;
                System.out.println("\n" +
                        "[==================================================]\n" +
                        "  EDF -> Tick #" + ticksTotales + " recibido del reloj.\n" +
                        "[==================================================]");

                // ── 1) Verificar si ya terminaron todos los procesos ────────
                if (verificarListoYRunningVacio()) {
                    break;
                }

                // ── 2) Decrementar deadlines de todos los procesos activos ──
                decrementarDeadlines();

                // ── 3) Ejecutar un tick en el proceso RUNNING ───────────────
                procesarTickEnEjecucion();

                // ── 4) Aplicar política EDF (selección / apropiación) ───────
                preemptEDF();

                // ── 5) Imprimir snapshot completo del sistema ───────────────
                imprimirEstadoCompleto();

            } catch (InterruptedException e) {
                System.out.println("EDF -> Hilo interrumpido.");
                break;
            } catch (Exception e) {
                System.out.println("EDF -> Error en bucle principal: " + e.getMessage());
                break;
            }
        }
        System.out.println("\nEDF -> Planificador EDF detenido totalmente.");
        System.out.println(obtenerEstadisticas());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // LÓGICA EDF
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Agrega un proceso al planificador EDF moviéndolo a la cola READY.
     * Puede llamarse en tiempo de ejecución para simular la llegada de
     * procesos de tiempo real durante la simulación.
     *
     * @param p Proceso a agregar.
     */
    public void agregarProceso(Proceso p) {
        if (p == null) {
            System.err.println("[ERROR EDF] Intento de agregar proceso nulo.");
            return;
        }
        p.setSListo();
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        if (colaReady != null) {
            colaReady.addLast(p);
            System.out.println("EDF -> Proceso P" + p.getID() + " (" + p.getNombre() +
                    ") agregado a READY. Deadline: " + p.getTiempoRestanteDeadline());
        }
    }

    /**
     * Decrementa en 1 el tiempoRestanteDeadline de cada proceso en READY y RUNNING.
     * Si un proceso alcanza deadline == 0 antes de terminar (deadline miss),
     * se registra el evento pero el proceso continúa para no romper la simulación.
     */
    private void decrementarDeadlines() {
        // Decrementar en READY (índice 1)
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        if (colaReady != null) {
            Nodo<Proceso> aux = colaReady.Head;
            while (aux != null) {
                Proceso p = aux.getData();
                if (p.getTiempoRestanteDeadline() > 0) {
                    p.setTiempoRestanteDeadline(p.getTiempoRestanteDeadline() - 1);
                }
                // ─ Deadline Miss ─────────────────────────────────────────
                if (p.getTiempoRestanteDeadline() <= 0 && p.getBurstTime() > 0) {
                    deadlinesMissed++;
                    System.err.println("EDF -> ¡DEADLINE MISS! Proceso P" + p.getID() +
                            " (" + p.getNombre() + ") no alcanzó su deadline en READY.");
                }
                aux = aux.getNext();
            }
        }

        // Decrementar en RUNNING (índice 2)
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);
        if (colaRunning != null) {
            Nodo<Proceso> aux = colaRunning.Head;
            while (aux != null) {
                Proceso p = aux.getData();
                if (p.getTiempoRestanteDeadline() > 0) {
                    p.setTiempoRestanteDeadline(p.getTiempoRestanteDeadline() - 1);
                }
                // ─ Deadline Miss ─────────────────────────────────────────
                if (p.getTiempoRestanteDeadline() <= 0 && p.getBurstTime() > 0) {
                    deadlinesMissed++;
                    System.err.println("EDF -> ¡DEADLINE MISS! Proceso P" + p.getID() +
                            " (" + p.getNombre() + ") no alcanzó su deadline en RUNNING.");
                }
                aux = aux.getNext();
            }
        }
    }

    /**
     * Ejecuta un tick en el proceso actualmente en RUNNING.
     * Si el proceso termina (burstTime llega a 0), lo mueve a EXIT
     * e incrementa el contador de procesos completados.
     */
    private void procesarTickEnEjecucion() {
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);
        if (colaRunning == null || colaRunning.isEmpty()) {
            return; // CPU vacía, nada que ejecutar
        }

        Proceso pActual = colaRunning.buscarLast();
        System.out.println("EDF -> Ejecutando P" + pActual.getID() +
                " (" + pActual.getNombre() +
                ") | BurstTime restante: " + pActual.getBurstTime() +
                " | Deadline restante: " + pActual.getTiempoRestanteDeadline());

        boolean terminado = pActual.ejecutarTick();

        if (terminado) {
            procesosCompletados++;
            System.out.println("EDF -> P" + pActual.getID() + " (" + pActual.getNombre() +
                    ") ha TERMINADO. Total completados: " + procesosCompletados);
            pActual.cambiarEstado("EXIT", colasPorEstado);
        }
    }

    /**
     * Aplica la selección EDF:
     * - Busca el proceso con menor tiempoRestanteDeadline en la cola READY.
     * - Si la CPU está libre: lo pone en RUNNING.
     * - Si la CPU está ocupada y el candidato tiene deadline MENOR que el proceso
     * actual, realiza la apropiación (preemption).
     */
    private void preemptEDF() {
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);

        if (colaReady == null || colaReady.isEmpty()) {
            return; // Sin candidatos en READY, nada que decidir
        }

        // Candidato EDF: el proceso con el deadline más corto en READY
        Proceso candidatoEDF = colaReady.buscarPMinAtributo("tiempoRestanteDeadline");
        if (candidatoEDF == null) {
            return;
        }

        // ── CASO A: CPU vacía ─────────────────────────────────────────────────
        if (colaRunning == null || colaRunning.isEmpty()) {
            System.out.println("EDF -> CPU vacía. Seleccionando P" + candidatoEDF.getID() +
                    " (Deadline: " + candidatoEDF.getTiempoRestanteDeadline() + ") para RUNNING.");
            candidatoEDF.cambiarEstado("RUNNING", colasPorEstado);
            cambiosDeContexto++;

            // ── CASO B: CPU ocupada, comparar deadlines ───────────────────────────
        } else {
            Proceso procesoRunning = colaRunning.buscarLast();
            if (procesoRunning == null) {
                return;
            }

            if (candidatoEDF.getTiempoRestanteDeadline() < procesoRunning.getTiempoRestanteDeadline()) {
                // ── PREEMPTION ─────────────────────────────────────────────
                System.out.println("EDF -> PREEMPTION: P" + candidatoEDF.getID() +
                        " (Deadline: " + candidatoEDF.getTiempoRestanteDeadline() +
                        ") desplaza a P" + procesoRunning.getID() +
                        " (Deadline: " + procesoRunning.getTiempoRestanteDeadline() + ")");

                // Cambio de modo a KERNEL para el cambio de contexto
                if (RTOS != null) {
                    RTOS.PSW = 0;
                }

                procesoRunning.cambiarEstado("READY", colasPorEstado); // Devolver a READY
                candidatoEDF.cambiarEstado("RUNNING", colasPorEstado); // Llevar a CPU
                cambiosDeContexto++;

                // Volver a modo USUARIO
                if (RTOS != null) {
                    RTOS.PSW = 1;
                }

            } else {
                // Sin cambio: el proceso actual es el de menor deadline
                System.out.println("EDF -> Manteniendo P" + procesoRunning.getID() +
                        " (Deadline: " + procesoRunning.getTiempoRestanteDeadline() +
                        "). Candidato P" + candidatoEDF.getID() +
                        " tiene deadline mayor (" + candidatoEDF.getTiempoRestanteDeadline() + ").");
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // SNAPSHOT DE CONSOLA
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Imprime en consola un snapshot completo del estado de todas las colas
     * al finalizar cada tick. Muestra cada proceso con todos sus datos relevantes.
     *
     * Columnas por proceso:
     * ID | Nombre | Status | Bound | BurstTime | Deadline restante | Prioridad | PC
     * | MAR
     */
    private void imprimirEstadoCompleto() {
        String[] nombresEstados = {
                "NEW (0)", "READY (1)", "RUNNING (2)", "BLOCKED (3)",
                "READY_SUSPENDED (4)", "BLOCKED_SUSPENDED (5)", "EXIT (6)"
        };

        System.out.println("\n+-------------------------------------------------------------------------------------+");
        System.out.printf("|  %-82s|%n", " ESTADO DEL SISTEMA - Tick #" + ticksTotales);
        System.out.println("+-------------------------------------------------------------------------------------+");

        for (int i = 0; i < nombresEstados.length; i++) {
            Lista<Proceso> cola = colasPorEstado.BuscarPosicion(i);
            int tamanio = (cola == null) ? 0 : cola.size();

            System.out.printf("|  Cola %-25s  (%d proceso/s)%-30s|%n",
                    nombresEstados[i], tamanio, "");

            if (cola != null && !cola.isEmpty()) {
                // Encabezado de columnas
                System.out.printf("|    %-4s %-22s %-10s %-5s %-9s %-10s %-4s %-6s %-6s|%n",
                        "ID", "Nombre", "Status", "Bound", "BurstTime", "Deadline", "Pri", "PC", "MAR");
                System.out.println("|    " + "-".repeat(81) + "|");

                // Filas de procesos
                Nodo<Proceso> aux = cola.Head;
                while (aux != null) {
                    Proceso p = aux.getData();
                    System.out.printf("|    %-4d %-22s %-10s %-5s %-9d %-10d %-4d %-6d %-6d|%n",
                            p.getID(),
                            truncar(p.getNombre(), 22),
                            truncar(p.getStatus(), 10),
                            truncar(p.getBound(), 5),
                            p.getBurstTime(),
                            p.getTiempoRestanteDeadline(),
                            p.getPrioridad(),
                            p.getPC(),
                            p.getMAR());
                    aux = aux.getNext();
                }
            }
            System.out
                    .println("+-------------------------------------------------------------------------------------+");
        }

        // Pie con estadísticas rápidas
        System.out.printf("|  Completados: %-5d  Deadline Misses: %-5d  Cambios Contexto: %-5d  Tick: %-6d|%n",
                procesosCompletados, deadlinesMissed, cambiosDeContexto, ticksTotales);
        System.out.println("+-------------------------------------------------------------------------------------+\n");
    }

    /**
     * Trunca un String a un máximo de caracteres para que la tabla quede alineada.
     *
     * @param s   Cadena a truncar.
     * @param max Número máximo de caracteres.
     * @return Cadena truncada o la original si es más corta.
     */
    private String truncar(String s, int max) {
        if (s == null)
            return "null";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

    // ──────────────────────────────────────────────────────────────────────────
    // UTILIDADES
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Verifica si tanto READY como RUNNING están vacíos.
     * Si lo están, el planificador puede detenerse.
     *
     * @return true si ambas colas están vacías.
     */
    public boolean verificarListoYRunningVacio() {
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);
        boolean readyVacia = (colaReady == null || colaReady.isEmpty());
        boolean runningVacia = (colaRunning == null || colaRunning.isEmpty());

        if (readyVacia && runningVacia) {
            System.out.println("EDF -> No hay procesos en READY ni en RUNNING. Deteniendo planificador.");
            return true;
        }
        return false;
    }

    /**
     * Detiene el planificador EDF de forma segura desde un hilo externo
     * (por ejemplo, al pulsar el botón Stop en la interfaz).
     */
    public void paraAlgoritmo() {
        this.running = false;
        System.out.println("EDF -> Deteniendo planificador por solicitud externa.");
    }

    /**
     * Devuelve un resumen de las estadísticas del planificador EDF.
     *
     * @return String con las estadísticas formateadas.
     */
    public String obtenerEstadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ESTADÍSTICAS EDF ===\n");
        sb.append("Ticks totales ejecutados : ").append(ticksTotales).append("\n");
        sb.append("Procesos completados     : ").append(procesosCompletados).append("\n");
        sb.append("Deadline misses          : ").append(deadlinesMissed).append("\n");
        sb.append("Cambios de contexto      : ").append(cambiosDeContexto).append("\n");
        sb.append("========================\n");
        return sb.toString();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // GETTERS
    // ──────────────────────────────────────────────────────────────────────────

    public int getProcesosCompletados() {
        return procesosCompletados;
    }

    public int getDeadlinesMissed() {
        return deadlinesMissed;
    }

    public int getTicksTotales() {
        return ticksTotales;
    }

    public int getCambiosDeContexto() {
        return cambiosDeContexto;
    }
}
