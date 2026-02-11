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
import java.util.concurrent.Semaphore;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joseg
 */
public class FCFS {
    /**
     * Debo pasar el RTOSmaster como un parametro, puesto que en cada aplicación
     * sistemas de planificación, el cambio de estados implican interrupciones
     * que cambian de modo al RTOS. 
     */
    public RTOSmaster RTOS; 

    
    //PARA CUANDO HAGA LA INTEGRACIÓN CON EL DEFAULT MODEL CUSTOM QUE TENGA LAS LISTAS Y TAL.
 //   public FCFS(RTOSmaster RTOS, DefaultTableModel modelNew) {
 //       this.RTOS = RTOS;
        
//}
    
    public FCFS(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram) {
        


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
            
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            APLICAR AL FINAL TODA LA PARTE DE SEMAFOROSSSSSS
            
            */
            //entrando a CPU a iniciarse
//            PReadyIteracion.start();
//            try {
//                cpu.acquire();
//            } catch (Exception e) {
//            }
            /*
            aqui abajo hago sección critica para cada uno de los recursos y después se va-
            */
        }
        //iterar por los running y pasarlo a exit
        for (int i = 0; i < colasPorEstado.BuscarPosicion(2).size(); i++) {
            Proceso PRunningIteracion=colasPorEstado.BuscarPosicion(2).BuscarPosicion(i);
            PRunningIteracion.cambiarEstado("EXIT", colasPorEstado);
            
            ////sacando a 
        }
}


}
