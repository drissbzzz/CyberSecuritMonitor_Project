/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cybersecuritmonitor_project;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author driss
 */
public class testing {

    public static void main(String[] args) {

        File archivo = new File("C:\\Users\\alumno\\Documents\\NetBeansProjects\\CyberSecuritMonitor_Project\\CarpetaPrueba");
        /*Archivo a = new Archivo(archivo);
        System.out.println("Hash: "+a.getHash());*/
        System.out.println("LISTA DE ARCHIVOS");
        for (File f : listarCarpeta(archivo)){                     
            Archivo a = new Archivo(f);           
            System.out.println(a.getHash()+ ": "+a.getNombre()); 
        }   
    }
    
    public static List<File> listarCarpeta(File ruta){
        List<File> lista = new ArrayList<>();  
        for (File archivo : ruta.listFiles()){
                if (archivo.isDirectory()){
                    System.out.println(archivo.getName()+ " - Carpeta"); 
                }else{
                    System.out.println("Archivo añadido");
                    lista.add(archivo);
                }
            }
        return lista;
    }
}
