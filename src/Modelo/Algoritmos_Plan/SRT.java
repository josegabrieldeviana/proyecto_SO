/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.EDD.Lista;
import Modelo.clasesSO.Proceso;
import Modelo.clasesSO.RTOSmaster;
import Modelo.clasesSO.RelojSO;
import java.util.concurrent.Semaphore;

/**
 *
 * @author joseg
 */
public class SRT {
    /**
     * Debo pasar el RTOSmaster como un parametro, puesto que en cada aplicación
     * sistemas de planificación, el cambio de estados implican interrupciones
     * que cambian de modo al RTOS. 
     * 
     * 
     * Step 1: Input number of processes with arrival time and burst time.
Step 2: Initialize remaining times (burst times), current time = 0, and counters.
Step 3: At each time unit, add processes that have arrived into the ready queue.
Step 4: Select the process with the shortest remaining time (preempt if a shorter one arrives).
Step 5: Execute the selected process for 1 unit, reduce its remaining time, and increment current time.
Step 6: If a process completes:

Turnaround Time = Completion Time − Arrival Time
Waiting Time = Turnaround Time − Burst Time
Step 7: Repeat Steps 3–6 until all processes complete.
Step 8: Calculate average waiting time and turnaround time.
Step 9: Display completion, waiting, and turnaround times for each process, along with averages.
     * 
     */
    public RTOSmaster RTOS; 
    
    public Lista<Lista<Proceso>> colasPorEstado;
    public Semaphore cpu;
    public Semaphore disco;
    public Semaphore ram;
    public RelojSO reloj;
    
    public SRT(Lista<Lista<Proceso>> colasPorEstado, Semaphore cpu, Semaphore disco, Semaphore ram) {
        
        this.RTOS = RTOS;
        this.colasPorEstado=colasPorEstado;
        this.cpu=cpu;
        this.disco=disco;
        this.ram=ram;
        this.reloj=reloj;
        
        /*
        manera de conseguir los ticks del reloj con un semaforo
        usando volatile. El reloj puede ser visto como un recurso compartido
        */
        
        
        

}}
