/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cybersecuritmonitor_project;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author driss
 */
public class GestorArchivos {

    String ruta;
    ArrayList<Archivo> primerGuardado = new ArrayList<Archivo>();
    ArrayList<Archivo> comparativa = new ArrayList<Archivo>();
    Logger logger = new Logger("log_sdas.txt");

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setPrimerGuardado(ArrayList<Archivo> primerGuardado) {
        this.primerGuardado = primerGuardado;
    }

    public void setComparativa(ArrayList<Archivo> comparativa) {
        this.comparativa = comparativa;
    }
    
    public void listarCarpeta(File ruta){
            for (File fichero : ruta.listFiles()){
                if (fichero.isDirectory()){
                    listarCarpeta(fichero);
                }else{
                    System.out.println(fichero.getName()+" - Ruta: "+fichero.getPath());
                }
            }
    }

    public void crearRegistroCarpeta(String ruta) {
        ArrayList<Archivo> listaArchivos = new ArrayList<Archivo>();
        File carpeta = new File(ruta);
        
        
        
    }
  
}
