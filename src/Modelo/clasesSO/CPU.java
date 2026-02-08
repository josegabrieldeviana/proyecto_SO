/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * Aquí va a estar un solo proceso que se ejecute, por ello
 * es que se va a poner solo 1 en el semaforo.
 * 
 * 
 * @author joseg
 */
public class CPU {
    public final Semaphore capacidadCPU=new Semaphore(1);
}
