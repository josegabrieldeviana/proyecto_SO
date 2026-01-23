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
    public String Status; //cambiado de pStatus a String
    public String Bound; //cambiado de pStatus a String
    public int PC;
    public int MAR;
    public int Prioridad;
    public int duracion;

    public Proceso(int ID, String Nombre, String Status, String Bound, int PC, int MAR, int Prioridad, int duracion) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Status = Status;
        this.Bound = Bound;
        this.PC = PC;
        this.MAR = MAR;
        this.Prioridad = Prioridad;
        this.duracion = duracion;
    }
    
    
    /**
     El proceso es un hilo, puesto que varios procesos van a estar en
     ejecución, y se necesita siempre tener en cuenta 
     
     */
    @Override
    public void run(){
        for(int i=duracion; i<=duracion; i--){
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

    public String getStatus() {
        return Status;
    }

    public String getBound() {
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

    
    public int getDuracion() {
        return duracion;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    

    public void setSNuevo(){
        this.Status = "NUEVO";
    }
    
    public void setSListo(){
        this.Status = "LISTO";
    }
    
    public void setSSuspendido(){
        this.Status = "SUSPENDIDO";
    }
    
    public void setSTerminado(){
        this.Status = "TERMINADO";
    }
    
    public void setSBloqueado(){
        this.Status = "BLOQUEADO";
    }
    
    
    public void setSLISTOySUSPENDIDO(){
        this.Status = "NUEVO";
    }
    
    public void setSBLOQUEADOySUSPENDIDO(){
        this.Status="BLOQUEADOySUSPENDIDO";
    }
    

    public void setBoundCPU() {
        this.Bound = "CPU";
    }
    
    public void setBoundES(){
        this.Bound="ES";
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setMAR(int MAR) {
        this.MAR = MAR;
    }

    public void setPrioridad(int Prioridad) {
        this.Prioridad = Prioridad;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    
    
    
    
    
} 
