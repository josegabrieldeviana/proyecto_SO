/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Modelo.Algoritmos_Plan.FCFS;
import Modelo.Algoritmos_Plan.RoundRobin;
import Modelo.Algoritmos_Plan.SRT;
import Modelo.EDD.DebugObject;
import Modelo.EDD.Lista;
import Modelo.EDD.ListaSimple;
import Modelo.clasesSO.*;
import Vista.Vista_1;
import java.lang.System.Logger.Level;

/**
 *
 * @author joseg
 */
public class controladorMain {

    // int static randNum=Math.random();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        var log =System.getLogger("logSO");

        
        /*
         * LAS COMPONENTES
         */
        CPU cpu = new CPU(1); //se inicializa la cantidad de int del semaforo
        RAM ram = new RAM(100); //100 procesos max en memoria
        DISCO disco = new DISCO(150);//si no hay espacio en memoria, se va a suspended en ms

        

        RTOSmaster RTOS1 = new RTOSmaster(0); // inicializo el RTOS con el PSW en 0 (modo kernel).

        
        Lista<Proceso> colaNuevo = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        RTOS1.xPRand(10, colaNuevo); //LOS 10 PROCESOS INICIALES
//        System.out.println("-----------------------LOS READY-----------------------");

        /*
         * DEBUGGING LOGICA
         */
        Lista<Proceso> colaListo = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        Lista<Proceso> colaBloqueados = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        Lista<Proceso> colaExit = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        Lista<Proceso> colaReadyS = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        Lista<Proceso> colaBlockedS = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        Lista<Proceso> colaRunning = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        
        /*
        LISTA DE LISTA POR ESTADOS
        */
        
        Lista<Lista<Proceso>> colasPorEstado = new Lista<>();
        
        
        colasPorEstado.addLast(colaNuevo); // 0: NEW
        colasPorEstado.addLast(colaListo); // 1: READY
        colasPorEstado.addLast(colaRunning); // 2: RUNNING
        colasPorEstado.addLast(colaBloqueados); // 3: BLOCKED
        colasPorEstado.addLast(colaReadyS); // 4: READYSUSPENDED
        colasPorEstado.addLast(colaBlockedS); // 5: BLOCKEDSUSPENDED
        colasPorEstado.addLast(colaExit); // 6: EXIT

        
        
//        System.out.println("[DEBUG MAIN]  -----------------------LOS READY-----------------------");
//        int listaTamaño=colasPorEstado.BuscarPosicion(0).size();
//        for (int i = 0; i <= colasPorEstado.BuscarPosicion(0).size(); i++) {
//            Proceso readycolaproceso;
//            readycolaproceso = colasPorEstado.BuscarPosicion(0).BuscarPosicion(i);
//            if (readycolaproceso==null) {
//             System.out.println("");
//             break;
//            }else{
//                readycolaproceso.debugPrint();
//            }
//        }
        

        RelojSO reloj = new RelojSO();

        /*
         * GUI (LO COMENTAMOS PARA ENFOCARNOS EN ALGORITMOS)
         */
         Vista_1 vista = new Vista_1(RTOS1, reloj, colasPorEstado);
         vista.setVisible(true);

         
         
        /* FCFS */
       // FCFS FCFS=new FCFS(colasPorEstado, cpu.capacidadCPU, disco.capacidadDISCO, ram.capacidadRAM, reloj);
        
        
//los atributos de cpu, ram y disco son todos semaforos
        //P1.debugPrint();
        //System.exit(0);
        

        
        
        
        /* SRT */
        SRT SRT=new SRT(colasPorEstado, cpu.capacidadCPU, disco.capacidadDISCO, ram.capacidadRAM, reloj);
        vista.setSrtThread(SRT);
        SRT.start();
        
        
        
        /* RR */
        RoundRobin robin = new RoundRobin(RTOS1, 4);

    }

}
