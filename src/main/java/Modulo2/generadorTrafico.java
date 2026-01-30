/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo2;

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

    Random r = new Random();
    String[] ps = {"4444", "31337", "6667","1337", "445"};
    String[] ipSospechosas = {
        "185.220.101.5",   // Nodo de salida Tor (común para anonimato)
        "45.146.164.125",  // Escaneos masivos de vulnerabilidades
        "103.203.57.42",   // Reportada por intentos de fuerza bruta SSH
        "193.142.146.223", // Actividad de Botnet detectada
        "77.247.110.14",   // Servidor de C&C (Command & Control)
        "45.95.147.236",   // Escáner de puertos persistente
        "80.241.215.115",  // Asociada a campañas de Phishing
        "185.156.177.126", // Intento de explotación de exploits conocidos
        "91.240.118.220",  // IPs de hosting "Bulletproof" (usadas por malware)
        "141.98.10.210",   // Fuerza bruta RDP
        "194.26.135.215",  // Distribución de troyanos
        "109.237.103.11",  // Servidor proxy malicioso
        "45.129.33.155",   // Inyecciones SQL automáticas
        "5.188.62.74",     // Tráfico masivo de spam
        "92.118.160.17"    // Reconocimiento de red sospechoso
    };

    public void generarDatos(String ruta) {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime tiempoSimulado = LocalTime.of(8, 0, 0); //Formato de fecha que puso Jose en el log       
         try (BufferedWriter w = new BufferedWriter(new FileWriter(ruta))) {
            for (int i = 1; i <= 200000; i++) {                
                String port;
                String ip;
                int prob = r.nextInt(100)+1;
                if (prob<=10){ //IP Sospechosa
                    port = r.nextInt(1024, 49150)+1+"";
                    ip = ipSospechosas[r.nextInt(ipSospechosas.length)];
                }else if (prob>10&&prob<=20){//Puerto reservado usado por IP Sospechosa
                    port = r.nextInt(1023)+1+"";
                    ip = ipSospechosas[r.nextInt(ipSospechosas.length)];
                }else if(prob>20&&prob<=25){// Puerto apuntado en la lista
                    port = ps[r.nextInt(5)];
                    ip= r.nextInt(254)+1+"."+r.nextInt(256)+"."+r.nextInt(256)+"."+r.nextInt(256);
                }
                else{
                    port = r.nextInt(1024, 49150)+1+"";
                    ip= r.nextInt(254)+1+"."+r.nextInt(256)+"."+r.nextInt(256)+"."+r.nextInt(256);
                }
                if (i % 50 == 0 && prob > 5) { 
                    tiempoSimulado = tiempoSimulado.plusSeconds(1);
                }
                String hora = tiempoSimulado.format(formatoHora);           
                String linea = "["+hora+"]"+"[RED] Conexion detectada desde "+ip+" al puerto "+ port;
                w.write(linea);
                w.newLine();
            }
            System.out.println("Datos generados y escritos en el archivo correctamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
