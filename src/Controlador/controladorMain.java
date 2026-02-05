/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Modelo.EDD.Lista;
import Modelo.EDD.ListaSimple;
import Modelo.clasesSO.*;
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
    log.log(Level.INFO, "hola");
    
    Lista colaNuevo=new Lista(); //puede funcionar como pila por addfirst o deletefirst, etc
    RTOSmaster RTOS1=new RTOSmaster();
    RTOS1.xPRand(10, colaNuevo);
    
    
    RTOS1.xPRand(20, colaNuevo);
    RTOS1.xPRand(1, colaNuevo);


    }
    
    
    
}
