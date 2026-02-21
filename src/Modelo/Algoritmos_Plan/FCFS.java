/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.EDD.Lista;
import Modelo.clasesSO.CPU;
import Modelo.clasesSO.DISCO;
import Modelo.clasesSO.Proceso;
import Modelo.clasesSO.RAM;
import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.RelojSO;
import java.util.concurrent.Semaphore;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joseg
 */
public class FCFS extends Thread {
    public RTOSmaster RTOS;
    public Lista<Lista<Proceso>> colasPorEstado;
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;
    public RelojSO reloj;
    
    public FCFS(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram, RelojSO reloj) {
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
        System.out.println("FCFS -> Deteniendo planificador por solicitud externa.");
    }
    
    public void run() {
        while (running) {
            try {
                /* 0) Sincronización reloj */
                reloj.getCicloEvent().acquire();
                System.out.println("FCFS -> Tick recibido del reloj.");
                
                /* resto de los running aquí:
                 * - Tomar el proceso en la cabeza de la cola FIFO.
                 * - Ejecutarlo por 1 tick.
                 * - IMPORTANTE: FCFS no es expropiativo, así que el proceso actual 
                 *   no se suelta hasta que termine o se bloquee por E/S.
                 */
            } catch (InterruptedException e) {
                System.out.println("FCFS -> Hilo Interrumpido");
                break;
            } catch(Exception e){
                System.out.println("FCFS -> Error en el bucle principal: " + e.getMessage());
                break;
            }
        }
    }
    
}


/*
CODIGO VIEJO

        //iterar por los new y pasarlo a ready
        for (int i = 0; i < colasPorEstado.BuscarPosicion(0).size(); i++) {
            Proceso PNuevoIteracion = colasPorEstado.BuscarPosicion(0).BuscarPosicion(i);
            PNuevoIteracion.cambiarEstado("READY", colasPorEstado);
            
            //entrando a RAM a quedarse ahí
        }
        //iterar por los ready y pasarlo a running
        for (int i = 0; i < colasPorEstado.BuscarPosicion(1).size(); i++) {
            Proceso PReadyIteracion=colasPorEstado.BuscarPosicion(1).BuscarPosicion(i);
            PReadyIteracion.cambiarEstado("RUNNING", colasPorEstado);
            /*
            
            NECESITO PRIMERO PONER TODOS LOS PROCESOS EN UN ESTADO DE "RUNNING" (DONDE VAN A EMPEZAR CON SU BURST TIME), 
            Y QUE EL MOMENTO EN QUE SEAN ACEPTADOS 
            ("ADENTRO DE SECCIÓN CRITICA EN EJECUCION CON SU BURST"), VAN A PONERSE DE "`PRIMERO" EN LA LISTA
            
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            
            */
            //entrando a CPU a iniciarse
            
            /*
            19/2/26 COMENTADO
            *COMENTO, NO COMIENZO
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            COMIENZO THREAD PARA ESTAR EN RUNNING
            
            
            
            */
//            //PReadyIteracion.start();
//            try {
//                cpu.acquire();
//                System.out.println("");
//            } catch (Exception e) {
//            }
//            /*
//            aqui abajo hago sección critica para cada uno de los recursos y después se va-
//            */
//        }
//        //iterar por los running y pasarlo a exit
//        for (int i = 0; i < colasPorEstado.BuscarPosicion(2).size(); i++) {
//            Proceso PRunningIteracion=colasPorEstado.BuscarPosicion(2).BuscarPosicion(i);
//            PRunningIteracion.cambiarEstado("EXIT", colasPorEstado);

