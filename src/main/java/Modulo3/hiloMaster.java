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
    ArrayList<String> listaNegra;
    ArrayList<String> listaBlanca;
    String ruta;
    Logger log;
    HashMap<String, Integer> map = new HashMap<>() ;
    
    public hiloMaster(hiloActor[] arrayhilos, String nombre, Logger log, String negra, String blanca){
        this.monitorizados = arrayhilos;
        this.nombre = nombre;
        this.ruta=negra;
        this.log = log;
        this.listaNegra=prepararLista(negra);
        this.listaBlanca=prepararLista(blanca);
    }
    
    public void run() {
        System.out.println(nombre+" esta listo para salir a cazar");
        while (true && !isInterrupted()) {
            try {
                Thread.sleep(2000);
                for (hiloActor h : monitorizados) {
                    int contador;
                    if (h.isAlive()&&!h.isInterrupted()) { // con este metodo, cazamos procesos con el mismo nombre y que se repiten mucho sin estar en la lista permitida
                        if (map.containsKey(h.getNombre())) {
                            contador = map.get(h.getNombre()) + 1;
                            map.put(h.getNombre(), contador);
                        } else {
                            map.put(h.getNombre(), 1);
                        }
                        escanearHilo(h);
                    }
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
        if(estaEnLaListaBlanca(hilo.getNombre())){
            return;
        }
        if(estaEnLaListaNegra(hilo.getNombre())){
            cerrarProceso(hilo);
            log.escribir("[PROCESOS] KILL: "+nombre+" (Lista Negra)" );
            return;
        }else if(usoCPU>40){
            cerrarProceso(hilo);
            log.escribir("[PROCESOS] KILL: "+nombre+" (CPU: "+usoCPU+"%)");
            actualizarLista(ruta,nombre);
            return;
        }else if(map.containsKey(nombre)&&(map.get(hilo.getNombre())>3)){
            cerrarProceso(hilo);
            log.escribir("[PROCESOS] KILL: "+nombre+" (Persistencia sospechosa)");
            actualizarLista(ruta,nombre);
            return;
        }      
          
    }

    private ArrayList<String> prepararLista(String lista) {
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
        return listaFormateada;
    }
    
    private void actualizarLista(String ruta, String nuevo) {
        if (!listaNegra.contains(nuevo)) {
            listaNegra.add(nuevo);
            try (FileWriter fw = new FileWriter(ruta, true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(nuevo+",");
                bw.flush();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private boolean estaEnLaListaNegra(String nombre){
        boolean si = false;
        for(String sospechoso: listaNegra){
            if(nombre.equals(sospechoso)){
                si = true;
            }           
        }
        return si;
    }
    private boolean estaEnLaListaBlanca(String nombre){
        boolean si = false;
        for(String normal: listaBlanca){
            if(nombre.equals(normal)){
                si = true;
            }           
        }
        return si;
    }  
}
