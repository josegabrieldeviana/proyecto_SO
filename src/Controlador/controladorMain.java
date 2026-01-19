/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Modelo.clasesSO.*;
import java.lang.System.Logger.Level;

/**
 *
 * @author joseg
 */
public class controladorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    var log=System.getLogger("logSO");
    log.log(Level.INFO, "hola");
              
    RelojSO nuevoreloj=new RelojSO();
    nuevoreloj.start();
        
    }
    
}
