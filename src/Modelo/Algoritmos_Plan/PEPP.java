/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.EDD.Lista;
import Modelo.clasesSO.Proceso;
import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.RelojSO;
import java.util.concurrent.Semaphore;

/**
 *
 * @author joseg
 */
public class PEPP extends Thread {
    public RTOSmaster RTOS;
    public Lista<Lista<Proceso>> colasPorEstado;
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;
    public RelojSO reloj;

    public PEPP(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram, RelojSO reloj) {
        this.colasPorEstado = colasPorEstado;
        this.cpu = cpu;
        this.disco = disco;
        this.ram = ram;
        this.reloj = reloj;
    }

    private volatile boolean running = true;

    public void paraAlgoritmo() {
        this.running = false;
        System.out.println("PEPP -> Deteniendo planificador por solicitud externa.");
    }

    @Override
    public void run() {
        while (running) {
            try {
                /* 0) Sincronización reloj */
                reloj.getCicloEvent().acquire();
                System.out.println("PEPP -> Tick recibido del reloj.");

                /* 1) Manejar llegada de procesos (NUEVO -> READY) */
                manejarLlegadaProcesos();

                /* 2) Verificar si no hay trabajo pendiente */
                if (verificarListoYRunningVacio()) {
                    // Seguimos esperando por si llegan nuevos procesos,
                    // o podemos terminar como SRT. Por ahora seguiremos.
                    // break;
                }

                /* 3) Ejecutar el proceso actual en CPU */
                procesarTickenEjecucion();

                /* 4) Verificar Preemption por Prioridad */
                preemptRunning();

            } catch (InterruptedException e) {
                System.out.println("PEPP -> Hilo Interrumpido");
                break;
            } catch (Exception e) {
                System.out.println("PEPP -> Error en el bucle principal: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
        System.out.println("PEPP -> Planificador detenido totalmente.");
    }

    private void manejarLlegadaProcesos() {
        Lista<Proceso> colaNuevo = colasPorEstado.BuscarPosicion(0);
        int size = colaNuevo.size();
        for (int i = 0; i < size; i++) {
            // Tomamos siempre el primero de la lista de nuevos
            Proceso p = colaNuevo.BuscarPosicion(0);
            if (p != null) {
                p.cambiarEstado("READY", colasPorEstado);
            }
        }
    }

    private void procesarTickenEjecucion() {
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);
        if (!colaRunning.isEmpty()) {
            Proceso pActual = colaRunning.buscarLast();
            System.out.println("PEPP -> Ejecutando P" + pActual.ID + " (Prioridad: " + pActual.Prioridad + ", BT: "
                    + pActual.burstTime + ")");

            // Ejecutamos el tick y verificamos si terminó
            boolean terminado = pActual.ejecutarTick();

            if (terminado) {
                System.out.println("PEPP -> P" + pActual.ID + " ha terminado.");
                pActual.cambiarEstado("EXIT", colasPorEstado);
            }
        }
    }

    private void preemptRunning() {
        Lista<Proceso> colaReady = colasPorEstado.BuscarPosicion(1);
        Lista<Proceso> colaRunning = colasPorEstado.BuscarPosicion(2);

        if (colaReady.isEmpty() && colaRunning.isEmpty())
            return;

        // Candidato: Proceso en READY con la MENOR prioridad numérica (MAYOR prioridad
        // real)
        Proceso candidato = colaReady.buscarPMinAtributo("Prioridad");

        // Caso A: CPU vacía
        if (colaRunning.isEmpty()) {
            if (candidato != null) {
                System.out.println("PEPP -> CPU vacía. Seleccionando P" + candidato.ID + " (Prioridad: "
                        + candidato.Prioridad + ")");
                candidato.cambiarEstado("RUNNING", colasPorEstado);
            }
        }
        // Caso B: Hay alguien en CPU, comparar prioridades
        else {
            Proceso pActual = colaRunning.buscarLast();
            if (candidato != null && candidato.Prioridad < pActual.Prioridad) {
                System.out.println("PEPP -> PREEMPTION: P" + candidato.ID + " (Prioridad: " + candidato.Prioridad +
                        ") expropia a P" + pActual.ID + " (Prioridad: " + pActual.Prioridad + ")");
                pActual.cambiarEstado("READY", colasPorEstado);
                candidato.cambiarEstado("RUNNING", colasPorEstado);
            }
        }
    }

    private boolean verificarListoYRunningVacio() {
        return colasPorEstado.BuscarPosicion(1).isEmpty() && colasPorEstado.BuscarPosicion(2).isEmpty();
    }

}
