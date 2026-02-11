/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import java.util.concurrent.Semaphore;

/**
 * Usualmente hay más procesos en disco que en MP.
 * 
 * 
 * @author joseg
 */
public class DISCO {
    public final Semaphore capacidadDISCO; //la capacidad tiene que ser mucho mayor que la de la RAM.

    public DISCO(int capacidadDiscoint) {
        this.capacidadDISCO =new Semaphore(capacidadDiscoint);
    }
}
