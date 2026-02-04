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
    }

    public void setDuracionCicloInstruccion(int duracionCicloInstruccion) {
        this.duracionCicloInstruccion = duracionCicloInstruccion;
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
     * @param nuevoEstado El nuevo estado al que se desea cambiar (en mayúsculas)
     * @return true si el cambio fue exitoso, false si la transición no es válida
     * @throws IllegalArgumentException si el nuevo estado es nulo o vacío
     */
    public boolean cambiarEstado(String nuevoEstado) {
        // Validar que el nuevo estado no sea nulo o vacío
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo o vacío");
        }

        // Convertir a mayúsculas para asegurar consistencia
        String estadoDestino = nuevoEstado.toUpperCase();
        String estadoActual = this.Status.toUpperCase();

        // Validar transiciones imposibles
        if (estadoActual.equals("LISTO") && estadoDestino.equals("BLOQUEADO")) {
            System.err.println("[ERROR] Transición imposible: LISTO -> BLOQUEADO");
            return false;
        }

        if (estadoActual.equals("BLOQUEADO") && estadoDestino.equals("EJECUCION")) {
            System.err.println("[ERROR] Transición imposible: BLOQUEADO -> EJECUCION");
            return false;
        }

        if (estadoActual.equals("NUEVO") && estadoDestino.equals("BLOQUEADO")) {
            System.err.println("[ERROR] Transición imposible: NUEVO -> BLOQUEADO");
            return false;
        }

        if (estadoActual.equals("EJECUCION") && estadoDestino.equals("BLOQUEADO")) {
            System.err.println("[ERROR] Transición imposible: EJECUCION -> BLOQUEADO");
            return false;
        }

        if (estadoActual.equals("EJECUCION") && estadoDestino.equals("SUSPENDIDO")) {
            System.err.println("[ERROR] Transición imposible: EJECUCION -> SUSPENDIDO");
            return false;
        }

        // Si la transición es válida, cambiar el estado
        this.Status = estadoDestino;
        System.out.println("[INFO] Transición exitosa: " + estadoActual + " -> " + estadoDestino);
        return true;
    }

}
