/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import java.util.concurrent.Semaphore;

/**
 *
 * Se asume que 1 ciclo de reloj= 1 seg
 * 
 * @author joseg
 */
public class RelojSO extends Thread {

    private static long sleepTime = 1000; // Default: 1 second
    private volatile int ciclos = 0;
    private boolean activo = true;
    private Semaphore CicloEvent=new Semaphore(0);
    /*
    Este semaforo va a notificarle a c/política de planificación
    que un tick ha pasado
    */

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(sleepTime);
                ciclos++;
                // System.out.println("Ciclo: " + ciclos);
                CicloEvent.release();
                
                /*
                SECCIÓN DE DEBUGGEO
                */
                
                //System.out.println("Estos son sus permitrs"+this.CicloEvent.toString());
                /*
                Esto es un signal, aquí se libera el semaforo "un tick ha pasado"
                */
                
                
            } catch (InterruptedException ex) {
                System.getLogger(RelojSO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public synchronized void setCicloDuracion(long ms) {
        RelojSO.sleepTime = ms;
    }

    public static synchronized long getCicloDuracion() {
        return RelojSO.sleepTime;
    }

    public synchronized int getCiclos() {
        return this.ciclos;
    }

    public synchronized void resetCiclos() {
        this.ciclos = 0;
    }

    public void deternereReloj() {
        this.activo = false;
    }

    public static long getSleepTime() {
        return sleepTime;
    }

    public boolean isActivo() {
        return activo;
    }

    public Semaphore getCicloEvent() {
        return CicloEvent;
    }

    public static void setSleepTime(long sleepTime) {
        RelojSO.sleepTime = sleepTime;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setCicloEvent(Semaphore CicloEvent) {
        this.CicloEvent = CicloEvent;
    }
    
    
}
