/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

import Modelo.EDD.Lista;
import Modelo.EDD.ListaSimple;
import java.lang.System.Logger.Level;

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
//    public void hacerP(int ID, String Nombre, String Status, String Bound, int PC, int MAR, int Prioridad, int duracion){
//        //Proceso P1=new Proceso(ID, Nombre, Status, Bound, PC, MAR, Prioridad, duracion);
//    }
    
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
    
    
    /*
    Un método que se encarga de hacer procesos con parametros aleatorios con
    math.random().
    
    @param número de procesos aleatorios a generar 
    @return genera una lista tipo "List" de "x" procesos random. 
    */
    public Lista xPRand(int numeroProcesosGen){
        
        
        var log=System.getLogger("LogSO");
        log.log(Level.INFO, "INICIALIZACIÓN DE  " + numeroProcesosGen + "    "
                + "                                  "
                + "                                  "
                + "                                  ");
        ListaSimple nombresList=new ListaSimple(); //lista de la que se va a escoger los nombres
        
        
        
//        //HAGO LISTA DE POSIBLES NOMBRES DE PROCESOS
//        nombresList.hacerListaString("Monitoreo_Altitud");
//        nombresList.hacerListaString("Control_Térmico");
//        nombresList.hacerListaString("P_Telemetría");
//        nombresList.hacerListaString("Orientación_Solar");
//        nombresList.hacerListaString("P_Análisis_Científico");
//        nombresList.hacerListaString("P_Captura_Cámara");
//        nombresList.hacerListaString("Muestreo_Radiación");
//        String nombrePruebaLista = nombresList.imprimirListas();
//        System.out.println(nombrePruebaLista);
//        
        

        Lista pList=new Lista(); //lista en que se van a poner procesos, después de pasar de paramlist los parametros
        
        
        //un for para cada proceso
        for(int counter=0; counter<numeroProcesosGen; counter++){
        Lista paramList=new Lista(); //lista en que se van a poner parametros            
            int ID=counter; 
            pList.addLast(ID); //añado un ID.
            
                //en cada ciclo de este for, se agregará un parametro aleatorio
                for (int i = 0; i < 12; i++) {
                    if (i==0) {
                        //NOMBRE
                        double critRandomDOUBLE=Math.random()*10;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        String critRandomSTRING=String.valueOf(critRandomINT); //convierto a String para poder elegir entre casos de nombres
                        switch(critRandomINT){
                            case 0: pList.addFirst("Monitoreo_Altitud");
                            case 1: pList.addLast("Control_Térmico");
                            case 2: pList.addLast("P_Telemetría");
                            case 3: pList.addLast("Orientación_Solar");
                            case 4: pList.addLast("Control_Térmico");
                            case 5: pList.addLast("P_Análisis_Científico");
                            case 6: pList.addLast("P_Captura_Cámara");
                            case 7: pList.addLast("Muestreo_Radiación");
                            case 8: pList.addLast("P_Telemetría");
                            case 9: pList.addLast("Orientación_Solar");
                            case 10: pList.addLast("Control_Térmico");
                            
                            
                            //STATUS
                            pList.addLast("NUEVO");
                        }
                    }
                    
                    if (i==1) {
                        //BOUND A 
                        double critRandomDOUBLE=Math.random()*10;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        String critRandomSTRING=String.valueOf(critRandomINT); //convierto a String para poder elegir entre casos de nombres
                        switch(critRandomINT){
                            case 0: pList.addLast("ES");
                            case 1: pList.addLast("ES");
                            case 2: pList.addLast("ES");
                            case 3: pList.addLast("ES");
                            case 4: pList.addLast("ES");
                            case 5: pList.addLast("ES");
                            case 6: pList.addLast("CPU");
                            case 7: pList.addLast("CPU");
                            case 8: pList.addLast("CPU");
                            case 9: pList.addLast("CPU");
                            case 10: pList.addLast("CPU");
                        }
                    }
                    
                    if (i==2) {
                        //PC
                        double critRandomDOUBLE=Math.random()*10000; //número de 4 digitos
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        pList.addLast(critRandomINT);
                    }
                    
                    if (i==3) {
                        //PC
                        double critRandomDOUBLE=Math.random()*10000; //número de 4 digitos
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        pList.addLast(critRandomINT);
                    }
                    
                    if (i==4) {
                        //PRIORIDAD
                        double critRandomDOUBLE=Math.random()*10;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        String critRandomSTRING=String.valueOf(critRandomINT); //convierto a String para poder elegir entre casos de nombres
                        switch(critRandomINT){
                            case 0: pList.addFirst(1);
                            case 1: pList.addLast(2);
                            case 2: pList.addLast(3);
                            case 3: pList.addLast(4);
                            case 4: pList.addLast(5);
                            case 5: pList.addLast(6);
                            case 6: pList.addLast(7);
                            case 7: pList.addLast(8);
                            case 8: pList.addLast(9);
                            case 9: pList.addLast(10);
                            case 10: pList.addLast(10);
                            
                            
                            //STATUS
                            
                        }
                    }
                    if (i==5) {
                        //deadlineOriginal
                        double critRandomDOUBLE=Math.random()*100;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        pList.addLast(critRandomINT);
                        
                    }
                    
                    if (i==6) {
                        //tiempoRestanteDeadline
                        double critRandomDOUBLE=Math.random()*100;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        pList.addLast(critRandomINT);
                        
                    }
                    
                    if (i==7) {
                        //ciclos para generar excepción
                        double critRandomDOUBLE=Math.random()*10;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        pList.addLast(critRandomINT);
                        
                    }
                    
                    if (i==8) {
                        //ciclosParaSatisfacerIO
                        double critRandomDOUBLE=Math.random()*10;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        pList.addLast(critRandomINT);
                        
                    }
                    
                    if (i==9) {
                        //cantidad de instrucciones
                        double critRandomDOUBLE=Math.random()*100;
                        //del 1 al 7 te da el nombre respectivo a su posición en nombresList, del 1 al 8 se repite.
                        int critRandomINT=(int)critRandomDOUBLE; //solo tomo la parte entera
                        System.out.println(critRandomINT); //temporal
                        pList.addLast(critRandomINT);
                        
                    }
                    
                    
             
            }
    };
        return pList;
    }
}
