
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import Modelo.EDD.Lista;

/**
 *
 * @author JGDV
 */
public class Proceso extends Thread {
    public int ID;
    public String Nombre;
    public String Status; // cambiado de pStatus a String
    public String Bound; // cambiado de pStatus a String

    // EJECUCIÓN TECNICA
    public int PC; // INCREMENTA 1 POR CICLO
    public int MAR; // INCREMENTA 1 POR CICLO
    public int Prioridad;

    // PLANIFICACIÓN Y TIEMPO REAL
    public int deadlineOriginal; // Tiempo límite asignado
    public int tiempoRestanteDeadline; // "Cuenta regresiva visual"

    // Entrada / Salida (E/S)
    public int ciclosParaGenerarExcepcion; //
    public int ciclosParaSatisfacerIO; //

    // Para algoritmos dinámicos
    public int tiempoLlegada; // Útil para FCFS y SRT

    // Parámetros de instrucción
    public int cantidadInstrucciones; // Cantidad de instrucciones del proceso (1-10)
    public int duracionCicloInstruccion; // Duración de cada ciclo de instrucción (1-5)
    public int burstTime; // multiplicación de cantidad de instrucciones y numero de ciclos

    public Proceso(int ID, String Nombre, String Status, String Bound, int PC, int MAR, int Prioridad,
            int deadlineOriginal, int tiempoRestanteDeadline, int ciclosParaGenerarExcepcion,
            int ciclosParaSatisfacerIO, int cantidadInstrucc, int cantidadInstrucciones, int duracionCicloInstruccion) {
        this.ID = ID; // ID DE 4 DIGIYOS
        this.Nombre = Nombre;
        this.Status = Status;
        this.Bound = Bound;
        this.PC = PC; // 6 dígitos aleatorios
        this.MAR = MAR; // 6 dígitos aleatorios
        this.Prioridad = Prioridad; // prioridad del 1 al 10, con thread priorite
        this.deadlineOriginal = deadlineOriginal; // MAX 10 SEGUNDAS
        this.tiempoRestanteDeadline = tiempoRestanteDeadline; // MAX de 1 a 10 SEGUNDOS
        this.ciclosParaGenerarExcepcion = ciclosParaGenerarExcepcion; // 1 a 10 ciclos para gen expectación
        this.ciclosParaSatisfacerIO = ciclosParaSatisfacerIO; // 1 a 10 ciclos para gen expectación
        this.tiempoLlegada = cantidadInstrucc; // número de dos digitos
        this.cantidadInstrucciones = cantidadInstrucciones; // 1 a 10 instrucciones
        this.duracionCicloInstruccion = duracionCicloInstruccion; // 1 a 5 duración del ciclo
        this.burstTime = this.cantidadInstrucciones * this.duracionCicloInstruccion;
    }

    /**
     * El proceso es un hilo, puesto que varios procesos van a estar en
     * ejecución, y se necesita siempre tener en cuenta
     * 
     */

    /**
     *
     * @return
     */

    /*
     * GETTERS
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

    public int getCantidadInstrucciones() {
        return cantidadInstrucciones;
    }

    public int getDuracionCicloInstruccion() {
        return duracionCicloInstruccion;
    }

    public int getBurstTime() {
        return burstTime;
    }

    /*
     * SETTERS
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

    public void setSNuevo() {
        this.Status = "NUEVO";
    }

    public void setSListo() {
        this.Status = "LISTO";
    }

    public void setSSuspendido() {
        this.Status = "SUSPENDIDO";
    }

    public void setSTerminado() {
        this.Status = "TERMINADO";
    }

    public void setSBloqueado() {
        this.Status = "BLOQUEADO";
    }

    public void setSLISTOySUSPENDIDO() {
        this.Status = "NUEVO";
    }

    public void setSBLOQUEADOySUSPENDIDO() {
        this.Status = "BLOQUEADOySUSPENDIDO";
    }

    public void setBoundCPU() {
        this.Bound = "CPU";
    }

    public void setBoundES() {
        this.Bound = "ES";
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

    public void setCantidadInstrucciones(int cantidadInstrucciones) {
        this.cantidadInstrucciones = cantidadInstrucciones;
        this.burstTime = this.cantidadInstrucciones * this.duracionCicloInstruccion;
    }

    /*
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * PARA REESTABLECER EL CICLO DE DURACIÓN DE INSTRUCCIÓN
     * 
     * CADA VEZ QUE SE CAMBIA LA DURACIÓN DE UN CICLO
     * 
     */
    public void setDuracionCicloInstruccion(int duracionCicloInstruccion) {
        this.duracionCicloInstruccion = duracionCicloInstruccion;
        this.burstTime = this.cantidadInstrucciones * this.duracionCicloInstruccion;
    }

    /*
     * No se si utilice este método
     */
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public String toString() {
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
                "Cantidad de Instrucciones: " + this.cantidadInstrucciones + "\n" +
                "Duración del Ciclo de Instrucción: " + this.duracionCicloInstruccion + "\n" +
                "Burst Time: " + this.burstTime + "\n" +
                "----------------------------";
    }

    /**
     * Método de debugging para imprimir todos los atributos del proceso.
     * Imprime mensajes de error si detecta valores nulos o inválidos.
     * No usa ninguna librería externa, solo System.out y System.err.
     */
    public void debugPrint() {
        System.out.println("");
        System.out.println("========== DEBUG: PROCESO ==========");

        // ID
        System.out.print("ID: ");
        System.out.println(this.ID);
        if (this.ID <= 0) {
            System.err.println("[ERROR] ID invalido o no inicializado: " + this.ID);
        }

        // Nombre
        System.out.print("Nombre: ");
        if (this.Nombre == null) {
            System.out.println("NULL");
            System.err.println("[ERROR] Nombre es nulo");
        } else if (this.Nombre.length() == 0) {
            System.out.println("(vacio)");
            System.err.println("[ERROR] Nombre esta vacio");
        } else {
            System.out.println(this.Nombre);
        }

        // Status
        System.out.print("Status: ");
        if (this.Status == null) {
            System.out.println("NULL");
            System.err.println("[ERROR] Status es nulo");
        } else if (this.Status.length() == 0) {
            System.out.println("(vacio)");
            System.err.println("[ERROR] Status esta vacio");
        } else {
            System.out.println(this.Status);
        }

        // Bound
        System.out.print("Bound: ");
        if (this.Bound == null) {
            System.out.println("NULL");
            System.err.println("[ERROR] Bound es nulo");
        } else if (this.Bound.length() == 0) {
            System.out.println("(vacio)");
            System.err.println("[ERROR] Bound esta vacio");
        } else {
            System.out.println(this.Bound);
        }

        // PC
        System.out.print("PC (Program Counter): ");
        System.out.println(this.PC);
        if (this.PC < 0) {
            System.err.println("[ERROR] PC tiene valor negativo: " + this.PC);
        }

        // MAR
        System.out.print("MAR (Memory Address Register): ");
        System.out.println(this.MAR);
        if (this.MAR < 0) {
            System.err.println("[ERROR] MAR tiene valor negativo: " + this.MAR);
        }

        // Prioridad
        System.out.print("Prioridad: ");
        System.out.println(this.Prioridad);
        if (this.Prioridad < 1 || this.Prioridad > 10) {
            System.err.println("[ERROR] Prioridad fuera de rango (1-10): " + this.Prioridad);
        }

        // Deadline Original
        System.out.print("Deadline Original: ");
        System.out.println(this.deadlineOriginal + " seg");
        if (this.deadlineOriginal <= 0) {
            System.err.println("[ERROR] deadlineOriginal invalido: " + this.deadlineOriginal);
        }

        // Tiempo Restante Deadline
        System.out.print("Tiempo Restante Deadline: ");
        System.out.println(this.tiempoRestanteDeadline + " seg");
        if (this.tiempoRestanteDeadline < 0) {
            System.err.println("[ERROR] tiempoRestanteDeadline negativo: " + this.tiempoRestanteDeadline);
        }

        // Ciclos Para Generar Excepcion
        System.out.print("Ciclos Para Generar Excepcion: ");
        System.out.println(this.ciclosParaGenerarExcepcion);
        if (this.ciclosParaGenerarExcepcion <= 0) {
            System.err.println("[ERROR] ciclosParaGenerarExcepcion invalido: " + this.ciclosParaGenerarExcepcion);
        }

        // Ciclos Para Satisfacer IO
        System.out.print("Ciclos Para Satisfacer I/O: ");
        System.out.println(this.ciclosParaSatisfacerIO);
        if (this.ciclosParaSatisfacerIO <= 0) {
            System.err.println("[ERROR] ciclosParaSatisfacerIO invalido: " + this.ciclosParaSatisfacerIO);
        }

        // Tiempo de Llegada
        System.out.print("Tiempo de Llegada: ");
        System.out.println(this.tiempoLlegada);
        if (this.tiempoLlegada < 0) {
            System.err.println("[ERROR] tiempoLlegada negativo: " + this.tiempoLlegada);
        }

        // Cantidad de Instrucciones
        System.out.print("Cantidad de Instrucciones: ");
        System.out.println(this.cantidadInstrucciones);
        if (this.cantidadInstrucciones < 1 || this.cantidadInstrucciones > 10) {
            System.err.println("[ERROR] cantidadInstrucciones fuera de rango (1-10): " + this.cantidadInstrucciones);
        }

        // Duración del Ciclo de Instrucción
        System.out.print("Duración del Ciclo de Instrucción: ");
        System.out.println(this.duracionCicloInstruccion);
        if (this.duracionCicloInstruccion < 1 || this.duracionCicloInstruccion > 5) {
            System.err
                    .println("[ERROR] duracionCicloInstruccion fuera de rango (1-5): " + this.duracionCicloInstruccion);
        }

        // Burst Time
        System.out.print("Burst Time: ");
        System.out.println(this.burstTime);
        if (this.burstTime != this.cantidadInstrucciones * this.duracionCicloInstruccion) {
            System.err.println(
                    "[ERROR] burstTime no coincide con la operacion (instrucciones * duracion): " + this.burstTime);
        }

        System.out.println("====================================");
    }

    /**
     * 
     * idDelProceso,
     * nombreDelProceso,
     * estatusDelProceso,
     * tipoDeBound,
     * programCounter,
     * memoryAddressRegister,
     * prioridadDelHilo,
     * deadlineOriginalSegundos,
     * tiempoRestanteDeadline,
     * ciclosParaExcepcion,
     * ciclosParaSatisfacerIO,
     * tiempoLlegada
     * 
     */

    /**
     * Cambia el estado del proceso, validando que la transición sea válida.
     * Las transiciones imposibles son:
     * - LISTO -> BLOQUEADO
     * - BLOQUEADO -> EJECUCION
     * - NUEVO -> BLOQUEADO
     * - EJECUCION -> BLOQUEADO
     * - EJECUCION -> SUSPENDIDO
     * 
     * 
     * 
     * 
     * @param nuevoEstado El nuevo estado al que se desea cambiar (en mayúsculas)
     * @return true si el cambio fue exitoso, false si la transición no es válida
     * @throws IllegalArgumentException si el nuevo estado es nulo o vacío
     */
    public boolean cambiarEstado(String nuevoEstado, Lista<Lista<Proceso>> ColasEstadoDestino) {
        // Validar que el nuevo estado no sea nulo o vacío
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo o vacío");
        }

        // Convertir a mayúsculas para asegurar consistencia
        String estadoDestino = nuevoEstado.toUpperCase();
        String estadoActual = this.Status.toUpperCase();

        // Validar transiciones imposibles
        if (estadoActual.equals("READY") && estadoDestino.equals("BLOCKED")) {
            System.err.println("[ERROR] Transición imposible: READY -> BLOCKED");
            return false;
        }

        if (estadoActual.equals("BLOCKED") && estadoDestino.equals("RUNNING")) {
            System.err.println("[ERROR] Transición imposible: BLOCKED -> RUNNING");
            return false;
        }

        if (estadoActual.equals("NEW") && estadoDestino.equals("BLOCKED")) {
            System.err.println("[ERROR] Transición imposible: NEW -> BLOCKED");
            return false;
        }

        if (estadoActual.equals("RUNNING") && estadoDestino.equals("BLOCKED")) {
            System.err.println("[ERROR] Transición imposible: RUNNING -> BLOCKED");
            return false;
        }

        if (estadoActual.equals("RUNNING")
                && (estadoDestino.equals("READYSUSPENDED") || estadoDestino.equals("SUSPENDIDO"))) {
            System.err.println("[ERROR] Transición imposible: RUNNING -> SUSPENDIDO");
            return false;
        }

        // Si la transición es válida, cambiar el estado
        this.Status = estadoDestino;

        // Identificar el índice de la cola de destino
        int indiceCola = -1;
        switch (estadoDestino) {
            case "NEW":
            case "NUEVO":
                indiceCola = 0;
                break;
            case "READY":
            case "LISTO":
                indiceCola = 1;
                break;
            case "RUNNING":
            case "EJECUCION":
                indiceCola = 2;
                break;
            case "BLOCKED":
            case "BLOQUEADO":
                indiceCola = 3;
                break;
            case "READYSUSPENDED":
            case "SUSPENDIDO":
                indiceCola = 4;
                break;
            case "BLOCKEDSUSPENDED":
            case "BLOQUEADOYSUSPENDIDO":
                indiceCola = 5;
                break;
            case "EXIT":
            case "TERMINADO":
                indiceCola = 6;
                break;
            default:
                System.err.println("[ERROR] Estado desconocido: " + estadoDestino);
                return false;
        }

        // Añadir el proceso a la cola correspondiente
        if (ColasEstadoDestino != null) {
            Lista<Proceso> colaDestino = ColasEstadoDestino.BuscarPosicion(indiceCola);
            if (colaDestino != null) {
                colaDestino.addLast(this);
                System.out
                        .println("[INFO] Proceso añadido a la cola " + estadoDestino + " (indice " + indiceCola + ")");
            } else {
                System.err.println("[ERROR] La cola de destino para el índice " + indiceCola + " es nula.");
                return false;
            }
        } else {
            System.err.println("[ERROR] La lista de colas ColasEstadoDestino es nula.");
            return false;
        }

        System.out.println("[INFO] Transición exitosa: " + estadoActual + " -> " + estadoDestino);
        // System.out.println("[INFO] Transición exitosa: " + estadoActual + " -> " +
        // estadoDestino + " En la cola..."+
        // ColasEstadoDestino.BuscarPosicion(indiceCola).printString()); PARA PROPOSITOS
        // DE DEBUGGING.
        return true;
    }

    @Override
    public void run() { //EL BURST TIME (S)=CANTIDAD DE INSTRUCCIONES EN UN PROCESO*N INSTRUCCIONES EN UN CICLO=TIEMPO EN CPU.
        for (int i = 0; i < burstTime; i++) { //CICLO
            try {
                System.out.println("[DEBUG] DURACION DE PROCESO RUNNING"+RelojSO.getCicloDuracion());
                Thread.sleep(RelojSO.getCicloDuracion()); //VA A ESTAR ACTIVO EL THREAD POR EL TIEMPO EN CADA CICLO
                this.PC++;
                this.MAR++;
                System.out.println("[DEBUG] SEGUNDOS EN EJECUCIÓN DE PROCESO......"+i);
            } catch (InterruptedException ex) {
                System.err.println("[ERROR] Proceso " + this.ID + " interrumpido.");
                return;
            }
        }
        this.Status = "TERMINADO";
        System.out.println(
                "[INFO] Proceso " + this.ID + " ha terminado su ejecución después de " + burstTime + " ciclos.");
    }

}
