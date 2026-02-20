/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.EDD.Lista;
import Modelo.clasesSO.Proceso;
import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.RelojSO;
import java.time.Duration;
import java.util.concurrent.Semaphore;

/**
 *
 * @author joseg
 */
public class SRT extends Thread {
    /**
     * Debo pasar el RTOSmaster como un parametro, puesto que en cada aplicación
     * sistemas de planificación, el cambio de estados implican interrupciones
     * que cambian de modo al RTOS.
     * 
     * 
     * Step 1: Input number of processes with arrival time and burst time.
     * Step 2: Initialize remaining times (burst times), current time = 0, and
     * counters.
     * Step 3: At each time unit, add processes that have arrived into the ready
     * queue.
     * Step 4: Select the process with the shortest remaining time (preempt if a
     * shorter one arrives).
     * Step 5: Execute the selected process for 1 unit, reduce its remaining time,
     * and increment current time.
     * Step 6: If a process completes:
     * 
     * Turnaround Time = Completion Time − Arrival Time
     * Waiting Time = Turnaround Time − Burst Time
     * Step 7: Repeat Steps 3–6 until all processes complete.
     * Step 8: Calculate average waiting time and turnaround time.
     * Step 9: Display completion, waiting, and turnaround times for each process,
     * along with averages.
     * 
     */
    public RTOSmaster RTOS;

    public Lista<Lista<Proceso>> colasPorEstado;
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;
    public RelojSO reloj;

    /*
     * En una simulación de sistema operativo, no se desea que el programador se
     * ejecute a la velocidad de la CPU (millones de veces por segundo); se desea
     * que se ejecute exactamente a la velocidad del reloj.
     * 
     * A. Cambia el estado del hilo (Eficiencia)
     * Al llamar a acquire(), la Máquina Virtual Java (JVM) le indica al sistema
     * operativo:
     * "Este hilo SRT está ahora en estado de ESPERA. Retírelo de la CPU. No le dé más energía"
     * .
     * El hilo SRT deja de existir para la CPU hasta que el reloj llama a release().
     * Esto ahorra el 100% de energía y recursos durante la espera.
     * B. Simula una "Interrupción" (Realismo)
     * En una PC real, cuando el temporizador alcanza su tiempo, envía una señal
     * eléctrica física (una interrupción) a la CPU. La CPU abandona lo que está
     * haciendo y ejecuta el Programador. Al usar un semáforo, se simula lo
     * siguiente:
     * Reloj: "¡TICK! (Liberar)"
     * SRT: "¡Estoy despierto! Restaré el tiempo restante ahora".
     * C. Gestiona el "Tiempo de Ráfaga" con precisión.
     * Como se desea restar el tiempo de ráfaga restante, el semáforo garantiza una
     * proporción de 1 a 1:
     * 1 Permiso Liberado = 1 Segundo Transcurrido.
     * 1 Permiso Adquirido = 1 Unidad de Tiempo de Ráfaga Restada.
     * Si la lógica de SRT tarda 0,1 segundos en calcular cuál es el proceso más
     * corto, ¡no importa! El hilo del reloj ya está de vuelta en su propio bucle,
     * esperando a que pase el siguiente segundo. Se ejecutan en paralelo.
     */

    public SRT(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram, RelojSO reloj) {

        this.RTOS = RTOS;
        this.colasPorEstado = colasPorEstado;
        this.cpu = cpu;
        this.disco = disco;
        this.ram = ram;
        this.reloj = reloj;

        /*
         * El reloj aqui va a representar los ticks DESDE la ejecución del algo de
         * planificación
         * manera de conseguir los ticks del reloj con un semaforo
         * usando volatile. El reloj puede ser visto como un recurso compartido
         */

        /*
         * Se manda todo a la cola de "Ready"
         */
        for (int i = 0; i < colasPorEstado.BuscarPosicion(0).size(); i++) {
            Proceso PNuevoIteracion = colasPorEstado.BuscarPosicion(0).BuscarPosicion(i);
            if (PNuevoIteracion.Status != "NUEVO") {

            } else {
                PNuevoIteracion.cambiarEstado("READY", colasPorEstado);
            }
            // entrando a RAM a quedarse ahí
        }

    }

    private volatile boolean running = true;

    public void stopAlgorithm() {
        this.running = false;
        System.out.println("SRT -> Deteniendo planificador por solicitud externa.");
    }

    /*
     * El run en este caso va a ser para conseguir los ticks respectivos al inicio
     * de
     * planificación.
     * Esto nos va a ser util al momento de iniciar
     */
    public void run() {
        while (running) {
            try {
                /* 0) Sincronización reloj */
                reloj.getCicloEvent().acquire(); // te devuelve los permits del semaforo que serían como ticks globales

                /*
                 * 2) verifico si listo y runnning estan vacíos, si lo estan entonces paro este
                 * Thread scheduler
                 */
                if (verificarListoYRunningVacio()) {
                    break;
                }

                /* 3) Manejar el proceso en la CPU. */
                procesarTickenEjecucion();

                /* 4) Ver si es necesario hacer preemption. */
                preemptRunning();

            } catch (InterruptedException e) {
                System.out.println("SRT -> Hilo interrumpido.");
                break;
            } catch (Exception e) {
                System.err.println("SRT -> Error en el bucle principal: " + e.getMessage());
                break;
            }

        }
        System.out.println("SRT -> Planificador SRT detenido totalmente.");
    }

    /*
     * Verifica si hay procesos en listo
     */
    private void procesarTickenEjecucion() {
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2); // 2: RUNNING
        if (!colaRunning.isEmpty()) {
            Proceso pActual = colaRunning.buscarLast();

            // Ejecutamos el tick y verificamos si terminó
            boolean terminado = pActual.ejecutarTick();

            if (terminado) {
                System.out.println("SRT -> P" + pActual.ID + " ha terminado.");
                pActual.cambiarEstado("EXIT", colasPorEstado);
            }
        }
    }

    /*
     * AQUÍ YO VERIFICO SI NO HAY NINGUNO EN LISTO NI
     */
    public boolean verificarListoYRunningVacio() {
        if (colasPorEstado.BuscarPosicion(1).isEmpty() && colasPorEstado.BuscarPosicion(2).isEmpty()) {
            return true;
        } else {
            System.out.println("EN EFECTO..........WAO....NO HAY PROCESOS EN LISTO!");
            return false;
        }
    }

    public void procesoTickSynced(Proceso P1) {
        // para los ticks sincronizados con el reloj del proceso
        boolean P1Exit = P1.ejecutarTick();
        /*
         * Si el proceso "se acaba", entonces se pone en
         * True y de ahí podemos cambiar proceso a "EXIT"
         * para que pueda borrarse.
         * 
         */

        if (P1Exit) {
            P1.cambiarEstado("EXIT", colasPorEstado);
            /*
             * Aqui, o en akgún otro lugar capaz en cambiar estado pondre
             * para que exit lo ponga en su tabla EXIT respectiva.
             * cualquier cosa puesta en EXIT se borra pero aún necesito testeo.
             */
        } else {
        }
    }

    public void preemptRunning() {

        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);

        // Proceso ProcesoBTMin=colaReady.buscarPMinAtributo("burstTime");
        // Proceso procesoRunnning=colaRunning.buscarLast();

        if (colaReady.isEmpty())
            return; // Si no hay nadie listo, no hay nada ue decidir

        // si no hay ningun proceso en running entonces...
        // se ejecuta normalmente solo una vez, va a ser cuando no haya ningun proceso
        // en running.

        Proceso candidatoSRT = colaReady.buscarPMinAtributo("burstTime");

        // CASO A CPU vacía
        if (colaRunning.isEmpty()) {
            candidatoSRT.cambiarEstado("RUNNING", colasPorEstado);
        }

        // CASO B> hay alguien en el CPU, comparamos a ver si lo sacamos
        else {
            Proceso procesoRunning = colaRunning.buscarLast();

            if (candidatoSRT.burstTime < procesoRunning.burstTime) {
                System.out.println("SRT -> PREEMPTION: P" + candidatoSRT.ID + " entra por P" + procesoRunning.ID);
                procesoRunning.cambiarEstado("READY", colasPorEstado);
                candidatoSRT.cambiarEstado("RUNNING", colasPorEstado);

            }

        }
    }
}

/*
 * COMENTARIOS PARA ARREGLAR SRT
 * 
 * 
 * 
 * 
 * 
 */
