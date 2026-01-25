/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cybersecuritmonitor_project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 *
 * @author driss
 */
public class generadorTrafico {

    String ruta = "";
    Random r = new Random();
    String[] ps = {"4444", "31337", "6667","1337", "445"};

    public void generarDatos(String ruta) {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime tiempoSimulado = LocalTime.of(8, 0, 0);
        
         try (BufferedWriter w = new BufferedWriter(new FileWriter(ruta))) {
            for (int i = 1; i <= 200000; i++) {
                
                String port = "";
                int prob = r.nextInt(100)+1;
                if (prob<=10){
                    port = ps[r.nextInt(ps.length)];
                }else if (prob>10&&prob<=20){
                    port = r.nextInt(1023)+1+"";
                    while(port.equals("80")| port.equals("443")){
                        port = r.nextInt(1023)+1+"";
                    }
                }else{
                    port = r.nextInt(1024, 49150)+1+"";
                }
                if (i % 50 == 0 && prob > 5) { 
                    tiempoSimulado = tiempoSimulado.plusSeconds(1);
                }
                String hora = tiempoSimulado.format(formatoHora);
                String ip= r.nextInt(255)+1+"."+r.nextInt(255)+"."+r.nextInt(255)+"."+r.nextInt(255);               
                String linea = "["+hora+"]"+"[RED] Conexión detectada desde "+ip+" al puerto "+ port;
                w.write(linea);
                w.newLine();
            }
            System.out.println("Datos generados y escritos en el archivo correctamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    }
