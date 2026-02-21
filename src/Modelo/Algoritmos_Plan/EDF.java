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
public class EDF extends Thread{
        public RTOSmaster RTOS;
    
    /*añadidura el 19 de feb, esto es para poder acceder a atributos modificados, abajo del constructor
    se pondra el this. de c/uno*/
    public Lista<Lista<Proceso>> colasPorEstado;
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;
    public RelojSO reloj;

    public EDF(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram, RelojSO reloj) {
        this.RTOS = RTOS;
        this.colasPorEstado = colasPorEstado;
        this.cpu = cpu;
        this.disco = disco;
        this.ram = ram;
        this.reloj = reloj;
    }
    
    private volatile boolean running=true;
    public void paraAlgoritmo(){
        try {
        this.running = false;
        System.out.println("EDF -> Deteniendo planificador por solicitud externa.");    
        } catch (Exception e) {
        }
        
    }
    
    public void run() {
        while (running) {
            try {
                /* 0) Sincronización reloj */
                reloj.getCicloEvent().acquire();
                System.out.println("EDF -> Tick recibido del reloj.");
                
                /* resto de los running aquí:
                 * - Revisar la cola de listos (PriorityQueue).
                 * - Ordenar o extraer el proceso con el menor valor de 'deadline'.
                 * - Ejecutarlo por 1 tick.
                 */
            } catch (InterruptedException e) {
                System.out.println("EDF -> Hilo Interrumpido");
                break;
            } catch(Exception e){
                System.out.println("EDF -> Error en el bucle principal: " + e.getMessage());
                break;
            }
        }
    }
    
    
}
