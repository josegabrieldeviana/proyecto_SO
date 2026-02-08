/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

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
    
    //int static randNum=Math.random();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    var log=System.getLogger("logSO");
    
    Lista colaNuevo=new Lista(); //puede funcionar como pila por addfirst o deletefirst, etc
    RTOSmaster RTOS1=new RTOSmaster(0); //inicializo el RTOS con el PSW en 0 (modo kernel).
    RTOS1.xPRand(10, colaNuevo); //lo que XPrand va a hacer es PONER adentro de una colaNuevo los procesos
    
    RTOS1.xPRand(20, colaNuevo);
    RTOS1.xPRand(1, colaNuevo);
    
    Vista_1 vista=new Vista_1(RTOS1);
    vista.setVisible(true);
    
    
    RoundRobin robin1=new RoundRobin(RTOS1);
    
    Proceso P1=(Proceso) colaNuevo.buscarLast();
   
    }
    
    
    
}
