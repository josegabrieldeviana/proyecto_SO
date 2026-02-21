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
public class PEPP extends Thread{
    public RTOSmaster RTOS;
    public Lista<Lista<Proceso>> colasPorEstado;
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;
    public RelojSO reloj; 

    public PEPP(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram, RelojSO reloj) {
        this.RTOS = RTOS;
        this.colasPorEstado = colasPorEstado;
        this.cpu = cpu;
        this.disco = disco;
        this.ram = ram;
        this.reloj = reloj;
    }
    
    private volatile boolean running = true;

    public void paraAlgoritmo(){
        this.running = false;
        System.out.println("PEPP -> Deteniendo planificador por solicitud externa.");
    }
    
    public void run() {
        while (running) {
            try {
                /* 0) Sincronización reloj */
                reloj.getCicloEvent().acquire();
                System.out.println("PEPP -> Tick recibido del reloj.");
                
                /* resto de los running aquí:
                 * - Revisar la cola de listos (ordenada por prioridad).
                 * - Si el proceso en cabeza tiene mayor prioridad que el actual en CPU,
                 *   se hace un cambio de contexto (expropiación/preemption).
                 * - Ejecutar el proceso seleccionado por 1 tick.
                 */
            } catch (InterruptedException e) {
                System.out.println("PEPP -> Hilo Interrumpido");
                break;
            } catch(Exception e){
                System.out.println("PEPP -> Error en el bucle principal: " + e.getMessage());
                break;
            }
        }
    }

}
