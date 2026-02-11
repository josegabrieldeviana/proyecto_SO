/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import java.util.concurrent.Semaphore;

/**
 * Introduces la cantidad de procesos cuantificados en la RAM
 * Por default van a ser el doble que la memoria principal siempre
 * 
 * Aquí van a estar las colas de estado READY y BLOCKED
 * 
 * 
 * @author joseg
 */
public class RAM {
    public final Semaphore capacidadRAM;

    public RAM(int capacidadRAMint) {
        this.capacidadRAM=new Semaphore(capacidadRAMint);
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