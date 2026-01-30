/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo3;

import java.util.Random;

/**
 *
 * @author driss
 */
public class hiloActor extends Thread{

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getUsoCPU() {
        return usoCPU;
    }

    public void setUsoCPU(double usoCPU) {
        this.usoCPU = usoCPU;
    }
    
    String  nombre;
    double usoCPU;
    boolean running=true;
    Random r = new Random();
    
    hiloActor(String nombre){
        this.nombre=nombre;
        this.usoCPU=1.0;
    }
    
    public void run(){
        while(running&&!isInterrupted()){
            try{
                setUsoCPU(r.nextInt(70)+1);
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                running = false;
            }
        }
    }
    
    
}
