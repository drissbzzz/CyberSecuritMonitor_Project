/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cybersecuritmonitor_project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        /*File carpeta = new File("C:\\Users\\alumno\\Documents\\NetBeansProjects\\CyberSecuritMonitor_Project\\CarpetaPrueba");

        // Crear el gestor de archivos
        GestorArchivos gestor = new GestorArchivos();

        System.out.println("=== CREANDO REGISTRO PRIMARIO ===");
        gestor.crearRegistroCarpeta(carpeta);

        // Mostrar los archivos del guardado primario
        for (Archivo a : gestor.getPrimerGuardado()) {
            System.out.println("Archivo: " + a.getNombre());
            System.out.println("Hash: " + a.getHash());
            System.out.println();
        }

        System.out.println("=== CREANDO COMPARATIVA ===");
        gestor.crearComparativa(carpeta);

        // Mostrar los archivos de la comparativa (opcional)
        for (Archivo a : gestor.getComparativa()) {
            System.out.println("Archivo comparativa: " + a.getNombre());
            System.out.println("Hash: " + a.getHash());
            System.out.println();
        }

        System.out.println("=== ESCANEANDO CARPETA ===");
        gestor.escanearCarpeta();*/
            try {
            // 1️⃣ Carpeta donde trabajamos
            File carpeta = new File("C:\\Users\\alumno\\Documents\\NetBeansProjects\\CyberSecuritMonitor_Project\\CarpetaPrueba");

            // Crear la carpeta si no existe
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // 2️⃣ Crear algunos archivos iniciales (guardado primario)
            File archivo1 = new File(carpeta, "archivo1.txt");
            File archivo2 = new File(carpeta, "archivo2.txt");
            File archivo3 = new File(carpeta, "archivo3.txt");

            // Escribir algo en los archivos
            Files.writeString(archivo1.toPath(), "Contenido A");
            Files.writeString(archivo2.toPath(), "Contenido B");
            Files.writeString(archivo3.toPath(), "Contenido C");

            // 3️⃣ Crear el gestor y generar el guardado primario
            GestorArchivos gestor = new GestorArchivos();
            System.out.println("=== CREANDO REGISTRO PRIMARIO ===");
            gestor.crearRegistroCarpeta(carpeta);

            for (Archivo a : gestor.getPrimerGuardado()) {
                System.out.println("Archivo primario: " + a.getNombre() + " | Hash: " + a.getHash());
            }

            // 4️⃣ Modificar, eliminar y crear archivos antes de la comparativa
            // Modificar archivo1
            Files.writeString(archivo1.toPath(), "Contenido modificado A");

            // Eliminar archivo2
            archivo2.delete();

            // Crear un archivo nuevo
            File archivoNuevo = new File(carpeta, "archivoNuevo.txt");
            Files.writeString(archivoNuevo.toPath(), "Contenido nuevo");

            // 5️⃣ Generar la comparativa
            System.out.println("\n=== CREANDO COMPARATIVA ===");
            gestor.crearComparativa(carpeta);

            for (Archivo a : gestor.getComparativa()) {
                System.out.println("Archivo comparativa: " + a.getNombre() + " | Hash: " + a.getHash());
            }

            // 6️⃣ Ejecutar escaneo
            System.out.println("\n=== ESCANEANDO CARPETA ===");
            gestor.escanearCarpeta();

            // ✅ Resultado esperado:
            // archivo1.txt -> modificado
            // archivo2.txt -> eliminado
            // archivo3.txt -> igual
            // archivoNuevo.txt -> creado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
