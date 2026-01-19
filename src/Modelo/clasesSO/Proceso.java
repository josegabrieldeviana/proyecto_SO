/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

/**
 *
 * @author JGDV
 */
public class Proceso extends Thread {
    public int ID;
    public String Nombre;
    public PStatus Status;
    public PCPUoES Bound; //¿CPU o E/S bound?
    public int PC;
    public int MAR;
    public int Prioridad;
    
    
    /**
     El proceso es un hilo, puesto que varios procesos van a estar en
     ejecución, y se necesita siempre tener en cuenta 
     
     */
    @Override
    public void run(){
        for(int i=0; i<= 5; i++){
            System.out.println(i);
            try {
                Thread.sleep(1000);}
            catch (InterruptedException ex) {
                System.getLogger(Proceso.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);            
                    }}}
    /**
     *
     * @return
     */
    public int getID() {
        return ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public PStatus getStatus() {
        return Status;
    }

    public PCPUoES getBound() {
        return Bound;
    }

    public int getPC() {
        return PC;
    }

    public int getMAR() {
        return MAR;
    }

    public int getPrioridad() {
        return Prioridad;
    }

    
    
    
    
    
} 
