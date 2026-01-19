/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.clasesSO;

/**
 *
 * @author joseg
 */
public class RelojSO extends Thread {
    @Override
    public void run(){
        for(int i=5; i > 0; i--){
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(RelojSO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
}
