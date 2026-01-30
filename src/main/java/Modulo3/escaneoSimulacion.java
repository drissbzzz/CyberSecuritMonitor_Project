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



public class escaneoSimulacion {
    
    
    public static void main(String[] args) {
        Logger logger;
        logger = new Logger("log_simulacion.txt");
        String rutaListaNegra = "lista_negra.txt";
        String rutaListaBlanca = "lista_blanca.txt";

        hiloActor[] procesos = {
            new hiloActor("chrome.exe"),          // Proceso normal
            new hiloActor("svchost.exe"),         // Proceso normal
            new hiloActor("keylogger.exe"),       // En lista negra
            new hiloActor("bitcoin_miner.exe"),   // Activará alerta por CPU
            new hiloActor("malware_test.exe"),     // Activará alerta por CPU
            new hiloActor("proceso_persistente.exe")// Activará alerta por persistencia
        };

        hiloMaster monitor = new hiloMaster(procesos, "EL matador", logger, rutaListaNegra, rutaListaBlanca);
        monitor.start();
        System.out.println("[SISTEMA] Iniciando simulación de procesos...");
        for (hiloActor p : procesos) {
            p.start();
        }
        try {
            Thread.sleep(15000); // 15 segundos de prueba
            System.out.println("[SISTEMA] Prueba finalizada. Revisa log_simulacion.txt");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

