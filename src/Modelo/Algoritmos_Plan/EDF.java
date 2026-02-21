package Modelo.Algoritmos_Plan;

import Modelo.EDD.Lista;
import Modelo.EDD.Nodo;
import Modelo.clasesSO.Proceso;
import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.RelojSO;
import java.util.concurrent.Semaphore;

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

    // Semáforos adicionales mantenidos para compatibilidad con la inicialización
    // externa
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;

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
     * Constructor principal de EDF compatible con la inicialización en
     * controladorMain.
     *
     * @param colasPorEstado Lista de listas, una por cada estado del proceso.
     * @param cpu            Semáforo de CPU (mantenido por compatibilidad).
     * @param disco          Semáforo de disco (mantenido por compatibilidad).
     * @param ram            Semáforo de RAM (mantenido por compatibilidad).
     * @param reloj          Reloj del SO que emite ticks periódicos.
     */
    public EDF(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram, RelojSO reloj) {
        this.colasPorEstado = colasPorEstado;
        this.cpu = cpu;
        this.disco = disco;
        this.ram = ram;
        this.reloj = reloj;
        this.procesosCompletados = 0;
        this.deadlinesMissed = 0;
        this.ticksTotales = 0;
        this.cambiosDeContexto = 0;

        // Mover todos los procesos NEW → READY al inicializar
        System.out.println("EDF -> Inicializando planificador. Moviendo procesos NEW a READY...");
        Lista<Proceso> colaNuevo = colasPorEstado.BuscarPosicion(0);
        if (colaNuevo != null) {
            Lista<Proceso> copiaNew = new Lista<>();
            Nodo<Proceso> aux = colaNuevo.Head;
            while (aux != null) {
                copiaNew.addLast(aux.getData());
                aux = aux.getNext();
            }
            Nodo<Proceso> auxCopia = copiaNew.Head;
            while (auxCopia != null) {
                Proceso p = auxCopia.getData();
                p.cambiarEstado("READY", colasPorEstado);
                auxCopia = auxCopia.getNext();
            }
        }
        System.out.println("EDF -> Inicialización completa.");
    }

    /**
     * Constructor alternativo que incluye referencia al RTOS.
     */
    public EDF(RTOSmaster RTOS, Lista<Lista<Proceso>> colasPorEstado, RelojSO reloj) {
        this(colasPorEstado, null, null, null, reloj);
        this.RTOS = RTOS;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // HILO PRINCIPAL (run)
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Bucle principal del planificador EDF.
     */
    @Override
    public void run() {
        System.out.println("EDF -> Planificador iniciado.");
        while (running) {
            try {
                reloj.getCicloEvent().acquire();
                ticksTotales++;
                System.out.println("\n" +
                        "[==================================================]\n" +
                        "  EDF -> Tick #" + ticksTotales + " recibido del reloj.\n" +
                        "[==================================================]");

                if (verificarListoYRunningVacio()) {
                    System.out.println("EDF -> IDLE: Esperando procesos...");
                } else {
                    decrementarDeadlines();
                    procesarTickEnEjecucion();
                    preemptEDF();
                }

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

    private void decrementarDeadlines() {
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        if (colaReady != null) {
            Nodo<Proceso> aux = colaReady.Head;
            while (aux != null) {
                Proceso p = aux.getData();
                if (p.getTiempoRestanteDeadline() > 0) {
                    p.setTiempoRestanteDeadline(p.getTiempoRestanteDeadline() - 1);
                }
                if (p.getTiempoRestanteDeadline() <= 0 && p.getBurstTime() > 0) {
                    deadlinesMissed++;
                    System.err.println("EDF -> ¡DEADLINE MISS! Proceso P" + p.getID() +
                            " (" + p.getNombre() + ") no alcanzó su deadline en READY.");
                }
                aux = aux.getNext();
            }
        }

        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);
        if (colaRunning != null) {
            Nodo<Proceso> aux = colaRunning.Head;
            while (aux != null) {
                Proceso p = aux.getData();
                if (p.getTiempoRestanteDeadline() > 0) {
                    p.setTiempoRestanteDeadline(p.getTiempoRestanteDeadline() - 1);
                }
                if (p.getTiempoRestanteDeadline() <= 0 && p.getBurstTime() > 0) {
                    deadlinesMissed++;
                    System.err.println("EDF -> ¡DEADLINE MISS! Proceso P" + p.getID() +
                            " (" + p.getNombre() + ") no alcanzó su deadline en RUNNING.");
                }
                aux = aux.getNext();
            }
        }
    }

    private void procesarTickEnEjecucion() {
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);
        if (colaRunning == null || colaRunning.isEmpty()) {
            return;
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

    private void preemptEDF() {
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);

        if (colaReady == null || colaReady.isEmpty()) {
            return;
        }

        Proceso candidatoEDF = colaReady.buscarPMinAtributo("tiempoRestanteDeadline");
        if (candidatoEDF == null) {
            return;
        }

        if (colaRunning == null || colaRunning.isEmpty()) {
            System.out.println("EDF -> CPU vacía. Seleccionando P" + candidatoEDF.getID() +
                    " (Deadline: " + candidatoEDF.getTiempoRestanteDeadline() + ") para RUNNING.");
            candidatoEDF.cambiarEstado("RUNNING", colasPorEstado);
            cambiosDeContexto++;
        } else {
            Proceso procesoRunning = colaRunning.buscarLast();
            if (procesoRunning == null) {
                return;
            }

            if (candidatoEDF.getTiempoRestanteDeadline() < procesoRunning.getTiempoRestanteDeadline()) {
                System.out.println("EDF -> PREEMPTION: P" + candidatoEDF.getID() +
                        " (Deadline: " + candidatoEDF.getTiempoRestanteDeadline() +
                        ") desplaza a P" + procesoRunning.getID() +
                        " (Deadline: " + procesoRunning.getTiempoRestanteDeadline() + ")");

                if (RTOS != null) {
                    RTOS.PSW = 0;
                }

                procesoRunning.cambiarEstado("READY", colasPorEstado);
                candidatoEDF.cambiarEstado("RUNNING", colasPorEstado);
                cambiosDeContexto++;

                if (RTOS != null) {
                    RTOS.PSW = 1;
                }
            } else {
                System.out.println("EDF -> Manteniendo P" + procesoRunning.getID() +
                        " (Deadline: " + procesoRunning.getTiempoRestanteDeadline() +
                        "). Candidato P" + candidatoEDF.getID() +
                        " tiene deadline mayor (" + candidatoEDF.getTiempoRestanteDeadline() + ").");
            }
        }
    }

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
                System.out.printf("|    %-4s %-22s %-10s %-5s %-9s %-10s %-4s %-6s %-6s|%n",
                        "ID", "Nombre", "Status", "Bound", "BurstTime", "Deadline", "Pri", "PC", "MAR");
                System.out.println("|    " + "-".repeat(81) + "|");

                Nodo<Proceso> aux = cola.Head;
                while (aux != null) {
                    Proceso p = aux.getData();
                    System.out.printf("|    %-4d %-22s %-10s %-5s %-9d %-10d %-4d %-6d %-6d|%n",
                            p.getID(), truncar(p.getNombre(), 22), truncar(p.getStatus(), 10),
                            truncar(p.getBound(), 5), p.getBurstTime(), p.getTiempoRestanteDeadline(),
                            p.getPrioridad(), p.getPC(), p.getMAR());
                    aux = aux.getNext();
                }
            }
            System.out
                    .println("+-------------------------------------------------------------------------------------+");
        }

        System.out.printf("|  Completados: %-5d  Deadline Misses: %-5d  Cambios Contexto: %-5d  Tick: %-6d|%n",
                procesosCompletados, deadlinesMissed, cambiosDeContexto, ticksTotales);
        System.out.println("+-------------------------------------------------------------------------------------+\n");
    }

    private String truncar(String s, int max) {
        if (s == null)
            return "null";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

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

    public void paraAlgoritmo() {
        this.running = false;
        System.out.println("EDF -> Deteniendo planificador por solicitud externa.");
    }

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
