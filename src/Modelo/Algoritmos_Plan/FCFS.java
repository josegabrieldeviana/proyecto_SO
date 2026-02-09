/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Algoritmos_Plan;

import Modelo.clasesSO.RTOSmaster;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joseg
 */
public class FCFS {
    /**
     * Debo pasar el RTOSmaster como un parametro, puesto que en cada aplicación
     * sistemas de planificación, el cambio de estados implican interrupciones
     * que cambian de modo al RTOS. 
     */
    public RTOSmaster RTOS; 

    public FCFS(RTOSmaster RTOS, DefaultTableModel modelNew) {
        this.RTOS = RTOS;
        
}}
