/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Modelo.Algoritmos_Plan.FCFS;
import Modelo.Algoritmos_Plan.RoundRobin;
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
        RAM ram = new RAM(100);
        DISCO disco = new DISCO(150);

        

        RTOSmaster RTOS1 = new RTOSmaster(0); // inicializo el RTOS con el PSW en 0 (modo kernel).

        
        Lista<Proceso> colaNuevo = new Lista<>(); // puede funcionar como pila por addfirst o deletefirst, etc
        RTOS1.xPRand(10, colaNuevo); //LOS 10 PROCESOS INICIALES

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

        
        
        
        

        RelojSO reloj = new RelojSO();

        /*
         * GUI (LO COMENTAMOS PARA ENFOCARNOS EN ALGORITMOS)
         */
         Vista_1 vista = new Vista_1(RTOS1, colaNuevo, reloj);
         vista.setVisible(true);

        


        RoundRobin robin1 = new RoundRobin(RTOS1);

        /* FCFS 
        SOLO PARA UNA SOLA VEZ QUE SE EJECUTA (SE INSTANCIA)
        EN EL MAIN DE VISTA SERÁ CADA VEZ QUE SE PRESIONA EL BOTÓN
        */
        Proceso P1 = (Proceso) colaNuevo.buscarLast();
        P1.cambiarEstado("RUNNING", colasPorEstado);
        
        //FCFS FCFS = new FCFS(colasPorEstado, cpu, disco, ram);
        
        //P1.debugPrint();

        /* ROUNRROBIN */

        /* SRT */


    }

}
