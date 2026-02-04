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
    
    
    //Lista pruebaList=new Lista();//prueba para metodo stringList
    //pruebaList.addLast("Monitoreo AAAA");
    //pruebaList.addLast("Control_Térmico AAAA");
    
    //String nombrePrueba=pruebaList.printString();
    
  //      System.out.println(nombrePrueba);
    RTOSmaster RTOS1=new RTOSmaster();
    Lista lista1P=RTOS1.xPRand(2);
    Lista lista2P=RTOS1.xPRand(1);
    Lista lista3P=RTOS1.xPRand(1);
    


Proceso P1DEBUG=(Proceso) lista1P.buscarLast();
Proceso P2DEBUG=(Proceso) lista1P.BuscarPosicion(0);
P1DEBUG.debugPrint();
P2DEBUG.debugPrint();



    }
    
    
    
}
