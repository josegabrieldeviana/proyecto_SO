/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

/**
 *
 * Se asume que 1 ciclo de reloj= 1 seg
 * 
 * @author joseg
 */
public class RelojSO extends Thread {

    private long sleepTime = 1000; // Default: 1 second
    private int ciclos = 0;
    private boolean activo = true;

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(sleepTime);
                ciclos++;
                // System.out.println("Ciclo: " + ciclos);
            } catch (InterruptedException ex) {
                System.getLogger(RelojSO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public synchronized void setCicloDuracion(long ms) {
        this.sleepTime = ms;
    }

    public synchronized long getCicloDuracion() {
        return this.sleepTime;
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
}
