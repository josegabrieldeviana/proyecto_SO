/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import Modelo.EDD.Lista;

/**
 * Esta es la clase con los métodos necesarios para hacer un proceso.
 * Servirá como un SO que pueda realizar instrucciones privilegiadas
 * y que pueda hacer nuevos procesos,
 * 
 * Aquí estarán disponibles las siguientes pautas:
 * 
 * Configuración Inicial Dinámica: Al iniciar el simulador por primera
vez, el sistema no dependerá de archivos externos; en su lugar,
deberá generar automáticamente un conjunto inicial de procesos con
parámetros aleatorios (instrucciones, prioridad, periodos y deadlines)
para poblar las colas de estado.
○ Creación Masiva de Procesos: La interfaz debe contar con un botón
funcional etiquetado como "Generar 20 Procesos Aleatorios". Esta
función tiene como objetivo agilizar las pruebas de rendimiento y
estresar el planificador de manera inmediata sin necesidad de carga
manual.
○ Generación de Tareas de Emergencia: Debe estar presente la
funcionalidad de añadir procesos aleatorios individuales durante la
ejecución para observar el comportamiento del sistema ante nuevas
ráfagas de CPU o E/S.
 * 
 * cada vez que tengas un método de nuevo proceso, se va a tener un nuevo
 * "reloj" de cuenta regresiva de 20 segundos.
 * 
 * @author joseg
 */
public class RTOSmaster {
    
    /**
     * Función para hacer procesos, debería ejecutarse en main.
     * @param ID ID del proceso
     * @param Nombre Nombre del proceso
     * @param Status Status del proceso
     * @param Bound Bound del proceso
     * @param PC PC del proceso
     * @param MAR MAR del proceso
     * @param Prioridad PRIORIDAD del proceso
     * @param duracion duracion del proceso
     */
    public void hacerP(int ID, String Nombre, String Status, String Bound, int PC, int MAR, int Prioridad, int duracion){
        Proceso P1=new Proceso(ID, Nombre, Status, Bound, PC, MAR, Prioridad, duracion);
    }
    
        /**
     * Función para hacer lista de 20 procesos, debería ejecutarse en main.
     * ID del proceso (se ponen los numeros de los procesos en consecución)
     * Nombre Nombre del proceso
     * Status Status del proceso
     * Bound Bound del proceso
     * PC PC del proceso
     * MAR MAR del proceso
     * Prioridad PRIORIDAD del proceso
     * duracion on del proceso
     * @return Lista de 20 procesos con todos sus atributos random 
     */
    
    
    //ver si puedo cambiarlo a listaSimple
    public Lista veintePRand(){
        Lista pList20=new Lista();
        for(int counter=0; counter<20; counter++){
        /*
            
        */
        
        /*
        Hacer MAR aleatorio que no se repita entre otros parametros aleatorios
        */
        
        /*
        Hacer PC aleatorio que no se repita entre otros parametros aleatorios, ni este entre otros procesos ya hechos
        */
        
        /*
        Hacer MAR aleatorio que no se repita entre otros parametros aleatorios
        */
        
        
    };
        return pList20;
    }
}
