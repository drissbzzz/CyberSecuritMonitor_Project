/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo2;

import Modulo1.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author driss
 */
public class AnalizadorTrafico {

    private Logger logger;
    private final String[] puertosNegros = {"4444", "31337", "6667", "1337", "445"};

    public AnalizadorTrafico(Logger logger) {
        this.logger = logger;
    }

    public void analizarArchivo(String rutaArchivo) {
        logger.escribir("[RED] Iniciando análisis sobre: " + rutaArchivo);
        String horaActualAnalizada = "";
        // Usamos HashMap solo para contar repeticiones de IP
        HashMap<String, Integer> conexionesPorSegundo = new HashMap<>();
        int alertasGeneradas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    String hora = linea.substring(1, 9);// Extraer Hora                   
                    int indiceDesde = linea.indexOf("desde ") + 6;//Extraer IP: Buscamos qué hay entre "desde " y " al"
                    int indiceAl = linea.indexOf(" al puerto");
                    String ip = linea.substring(indiceDesde, indiceAl).trim();
                    int indicePuerto = linea.indexOf("puerto ") + 7;// Extraer Puerto
                    String puerto = linea.substring(indicePuerto).trim();
                    if (estaEnLaLista(puerto)) {
                        logger.escribir("[RED][ALERTA] Puerto sospechoso " + puerto + " usado por " + ip);
                        alertasGeneradas++;
                    }
                    if (!hora.equals(horaActualAnalizada)) {
                        horaActualAnalizada = hora;
                        conexionesPorSegundo.clear();
                    }
                    int conteo = conexionesPorSegundo.getOrDefault(ip, 0) + 1;
                    conexionesPorSegundo.put(ip, conteo);
                    if (conteo == 5) {
                        logger.escribir("[RED][ALERTA] Repetición constante de la IP: " + ip + " (" + hora + ")");
                        alertasGeneradas++;
                    }
                } catch (Exception e) {
                }
            }
            logger.escribir("[RED] Análisis finalizado. Alertas detectadas: " + alertasGeneradas);
        } catch (IOException e) {
            logger.escribir("[RED] Error crítico leyendo archivo: " + e.getMessage());
        }
    }

    public boolean estaEnLaLista(String puerto) {
        for (String p : puertosNegros) {
            if (p.equals(puerto)) {
                return true;
            }
        }
        int puertoInt = Integer.parseInt(puerto);
        if (puertoInt < 1024) {
            if (puertoInt == 80 || puertoInt == 443) {
                return false;
            }
            return true;
        }
        return false;
    }
}
