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
    public final Semaphore capacidadCPU;

    public CPU(int capacidadCPUint) {
        this.capacidadCPU = new Semaphore(capacidadCPUint);
    }

    
}
/*
public class DISCO {
    public final Semaphore capacidadDISCO; //la capacidad tiene que ser mucho mayor que la de la RAM.

    public DISCO(int capacidadDisco) {
        this.capacidadDISCO =new Semaphore(capacidadDisco);
    }
}
*/