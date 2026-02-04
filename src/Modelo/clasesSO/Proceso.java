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
   
    //EJECUCIÓN TECNICA
    public int PC; //INCREMENTA 1 POR CICLO
    public int MAR; //INCREMENTA 1 POR CICLO
    public int Prioridad;
    
    
    //PLANIFICACIÓN Y TIEMPO REAL
    public int deadlineOriginal; //Tiempo límite asignado
    public int tiempoRestanteDeadline; // "Cuenta regresiva visual"
    
    // Entrada / Salida (E/S)
    public int ciclosParaGenerarExcepcion; // 
    public int ciclosParaSatisfacerIO; //
    
    // Para algoritmos dinámicos
    public int tiempoLlegada; // Útil para FCFS y SRT



    public Proceso(int ID, String Nombre, String Status, String Bound, int PC, int MAR, int Prioridad, int deadlineOriginal, int tiempoRestanteDeadline, int ciclosParaGenerarExcepcion, int ciclosParaSatisfacerIO, int cantidadInstrucc) {
        this.ID = ID; //ID DE 4 DIGIYOS
        this.Nombre = Nombre;
        this.Status = Status;
        this.Bound = Bound; 
        this.PC = PC; // 6 dígitos aleatorios
        this.MAR = MAR; // 6 dígitos aleatorios
        this.Prioridad = Prioridad; // prioridad del 1 al 10, con thread priorite
        this.deadlineOriginal = deadlineOriginal; //MAX 10 SEGUNDAS
        this.tiempoRestanteDeadline = tiempoRestanteDeadline; //MAX de 1 a 10 SEGUNDOS
        this.ciclosParaGenerarExcepcion = ciclosParaGenerarExcepcion; //1 a 10 ciclos para gen expectación
        this.ciclosParaSatisfacerIO = ciclosParaSatisfacerIO; //1 a 10 ciclos para gen expectación
        this.tiempoLlegada = cantidadInstrucc; //número de dos digitos
    }
    
    
    /**
     El proceso es un hilo, puesto que varios procesos van a estar en
     ejecución, y se necesita siempre tener en cuenta 
     
     */
      
    /**
     *
     * @return
     */
    
    /*
    GETTERS
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

    
    public int getDeadlineOriginal() {
        return deadlineOriginal;
    }
    
    
        public int getTiempoRestanteDeadline() {
        return tiempoRestanteDeadline;
    }

    public int getCiclosParaGenerarExcepcion() {
        return ciclosParaGenerarExcepcion;
    }

    public int getCiclosParaSatisfacerIO() {
        return ciclosParaSatisfacerIO;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    
    
    
    /*
    SETTERS
    */
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

    public void setDeadlineOriginal(int deadlineOriginal) {
        this.deadlineOriginal = deadlineOriginal;
    }
    
        public void setBound(String Bound) {
        this.Bound = Bound;
    }

    public void setTiempoRestanteDeadline(int tiempoRestanteDeadline) {
        this.tiempoRestanteDeadline = tiempoRestanteDeadline;
    }

    public void setCiclosParaGenerarExcepcion(int ciclosParaGenerarExcepcion) {
        this.ciclosParaGenerarExcepcion = ciclosParaGenerarExcepcion;
    }

    public void setCiclosParaSatisfacerIO(int ciclosParaSatisfacerIO) {
        this.ciclosParaSatisfacerIO = ciclosParaSatisfacerIO;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public String toString() {
        return "Nombre de proceso:" + this.Nombre + " " + this.Status + " " + ;
    }public String toString() {
    return "--- DETALLES DEL PROCESO ---\n" +
           "ID del Proceso: " + this.ID + "\n" +
           "Nombre: " + this.Nombre + "\n" +
           "Estatus Actual: " + this.Status + "\n" +
           "Tipo de Bound: " + this.Bound + "\n" +
           "Program Counter (PC): " + this.PC + "\n" +
           "Memory Address Register (MAR): " + this.MAR + "\n" +
           "Prioridad: " + this.Prioridad + "\n" +
           "Deadline Original: " + this.deadlineOriginal + " seg\n" +
           "Tiempo Restante Deadline: " + this.tiempoRestanteDeadline + " seg\n" +
           "Ciclos para Generar Excepción: " + this.ciclosParaGenerarExcepcion + "\n" +
           "Ciclos para Satisfacer I/O: " + this.ciclosParaSatisfacerIO + "\n" +
           "Cantidad de Instrucciones (Tiempo Llegada): " + this.tiempoLlegada + "\n" +
           "----------------------------";
}

    
    /**
     
                idDelProceso,
                nombreDelProceso,
                estatusDelProceso,
                tipoDeBound,
                programCounter,
                memoryAddressRegister,
                prioridadDelHilo,
                deadlineOriginalSegundos,
                tiempoRestanteDeadline,
                ciclosParaExcepcion,
                ciclosParaSatisfacerIO,
                tiempoLlegada
     
     */
    
    
} 
