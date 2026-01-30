/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import Modulo1.Logger;
import Modulo2.AnalizadorTrafico;
import Modulo2.generadorTrafico;
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
        String archivoPrueba = "traffic_test.log";
        
        // --- PASO 1: GENERACIÓN ---
        System.out.println("=== FASE 1: GENERANDO TRÁFICO ===");
        System.out.println("Generando 200.000 líneas de logs (esto tardará un poco)...");
        
        long inicio = System.currentTimeMillis();
        
        generadorTrafico generador = new generadorTrafico();
        generador.generarDatos(archivoPrueba);
        
        long fin = System.currentTimeMillis();
        System.out.println("Generación completada en " + (fin - inicio) + " ms.");
        System.out.println("Archivo creado: " + new File(archivoPrueba).getAbsolutePath());

        // --- PASO 2: ANÁLISIS ---
        System.out.println("\n=== FASE 2: ANALIZANDO LOGS ===");
        
        // Creamos un Logger "tonto" que imprima en consola para ver qué pasa
        // (Asumiendo que tu constructor de Logger acepta una ruta String)
        Logger loggerConsola = new Logger("log_resultados_test.txt") {
            @Override
            public void escribir(String mensaje) {
                // Sobrescribimos para ver la alerta en la consola de NetBeans
                System.out.println(mensaje);
                // También guardamos en archivo (llamando al padre si es necesario)
                super.escribir(mensaje); 
            }
        };
        AnalizadorTrafico analizador = new AnalizadorTrafico(loggerConsola);
        analizador.analizarArchivo(archivoPrueba);
        
        System.out.println("=== TEST FINALIZADO ===");
    }

}
