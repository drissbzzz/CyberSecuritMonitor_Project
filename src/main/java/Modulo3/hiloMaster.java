/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo3;

import Modulo1.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author driss
 */
public class hiloMaster extends Thread{
    
    String nombre;
    hiloActor[] monitorizados;
    String[] listaNegra;
    String ruta;
    Logger log;
    HashMap<String, Integer> map = new HashMap<>() ;
    
    hiloMaster(hiloActor[] arrayhilos, String nombre, Logger log, String lista){
        this.monitorizados = arrayhilos;
        this.nombre = nombre;
        this.ruta=lista;
        this.log = log;
        this.listaNegra=prepararLista(lista);
    }
    
    public void run() {
        System.out.println("Trabajando");
        while (true) {
            try {
                Thread.sleep(2000);
                for (hiloActor h : monitorizados) {
                    int contador;
                    if (h.isAlive()) {
                        if (map.containsKey(h.getNombre())) {
                            contador = map.get(h.getNombre()) + 1;
                            map.put(h.getNombre(), contador);
                        } else {
                            map.put(h.getNombre(), 1);
                        }
                    }
                    escanearHilo(h);
                }
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
    }
    
    public void cerrarProceso(hiloActor muerto){
        muerto.interrupt();
    }
    public void escanearHilo(hiloActor hilo){
        double usoCPU = hilo.getUsoCPU();
        String nombre = hilo.getNombre();
        if(usoCPU>40){
            cerrarProceso(hilo);
            log.escribir(" Se ha interrumpido el proceso "+nombre+". Añadido a lista negra");
            actualizarLista(ruta,nombre);
        }else if(map.containsKey(nombre)&&(map.get(hilo.getNombre())>3)){
            cerrarProceso(hilo);
            log.escribir(" Se ha interrumpido el proceso "+nombre+". Proceso repetiendose constantemente");
            actualizarLista(ruta,nombre);
        }else if(estaEnLaLista(hilo.getNombre())){
            cerrarProceso(hilo);
            log.escribir(" Se ha interrumpido el proceso "+nombre+". Está en la lista negra");
        }
            
    }

    private String[] prepararLista(String lista) {
        ArrayList<String> listaFormateada = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(lista))){
            String linea;
            while((linea=br.readLine())!=null){
                String[] partes =linea.split(",");
                for (String parte: partes){
                    String nombre = parte.trim();
                    if(!nombre.isEmpty()){
                        listaFormateada.add(nombre);
                    }
                }
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        return listaFormateada.toArray(new String[0]);
    }
    
    private void actualizarLista(String ruta, String nuevo){
        try(FileWriter fw = new FileWriter(ruta, true);BufferedWriter bw = new BufferedWriter(fw)){
            bw.append(","+nuevo+"\n");
            bw.flush();
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    private boolean estaEnLaLista(String nombre){
        boolean si = false;
        for(String sospechoso: listaNegra){
            if(nombre.equals(sospechoso)){
                si = true;
            }           
        }
        return si;
    }
    
}
