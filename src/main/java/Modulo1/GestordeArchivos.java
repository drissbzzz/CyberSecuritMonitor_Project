/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo1;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author driss
 */
public class GestordeArchivos extends Thread{

    String ruta;
    boolean activo=false; //bandera para permitir activar el escaneo al apretar el boton 
    Logger logger;
    
    public GestordeArchivos(String ruta,  JTextArea area){
        this.ruta=ruta;
        this.logger = new Logger("log_sdas.txt", area);
    }
    
    public void run(){
       crearRegistroCarpeta(ruta);
       while(true){
           if(activo){
               try {
                   Thread.sleep(2000);
                   crearComparativa(ruta);
                   escanearCarpeta();                  
               } catch (InterruptedException ex) {
                   System.out.println(ex.getMessage());
               }
               
           }else{
               try {
                   Thread.sleep(2000);
               } catch (InterruptedException ex) {
                   System.out.println(ex.getMessage());
               }
           }
       }
    }
    
    public ArrayList<Archivo> getPrimerGuardado() {
        return primerGuardado;
    }

    public ArrayList<Archivo> getComparativa() {
        return comparativa;
    }
    ArrayList<Archivo> primerGuardado = new ArrayList<Archivo>();
    ArrayList<Archivo> comparativa = new ArrayList<Archivo>();

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
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
    
    public void crearRegistroCarpeta(String ruta){
        ArrayList<Archivo> lista = new ArrayList<>();
        File carpeta = new File(ruta);
        for (File f : carpeta.listFiles()){
                if (f.isDirectory()){
                    System.out.println(f.getName()+ " - Carpeta"); 
                }else{
                    System.out.println("Archivo añadido");
                    Archivo a = new Archivo(f); 
                    lista.add(a);
                }
            }
        setPrimerGuardado(lista);
    }
    
    public void crearComparativa(String ruta){
        ArrayList<Archivo> lista = new ArrayList<>(); 
        File carpeta = new File(ruta);
        for (File f : carpeta.listFiles()){
                if (f.isDirectory()){
                    
                }else{
                    Archivo a = new Archivo(f); 
                    lista.add(a);
                }
            }
        setComparativa(lista);
    }
    public void escanearCarpeta() {
        if (getPrimerGuardado().size() > 0) {
            // Recorre cada archivo antiguo
            for (int i = 0; i < getPrimerGuardado().size(); i++) {
                boolean coincidencia = false;
                // Recorre todos los archivos de la comparativa
                for (int j = 0; j < getComparativa().size(); j++) {
                    String hashAntiguo = getPrimerGuardado().get(i).getHash();
                    String nombreAntiguo = getPrimerGuardado().get(i).getNombre();
                    String hashNuevo = getComparativa().get(j).getHash();
                    String nombreNuevo = getComparativa().get(j).getNombre();
                    if (hashAntiguo.equals(hashNuevo)) {
                        coincidencia = true;
                    } else if (nombreAntiguo.equals(nombreNuevo)) {
                        System.out.println(nombreAntiguo + " se modifico");
                        logger.escribir("["+getComparativa().get(j).getFechaEscaneo()+"][INTEGRIDAD] "+nombreAntiguo + " se modifico");
                        coincidencia = true;
                    }
                }
                // Después de comparar con todos los archivos nuevos
                if (!coincidencia) {
                    String fecha = LocalDateTime.now().toString();
                    System.out.println(getPrimerGuardado().get(i).getNombre() + " se elimino");
                    logger.escribir("["+fecha+"][INTEGRIDAD] "+getPrimerGuardado().get(i).getNombre()+ " se elimino");
                }
            }
            for (int i = 0; i < getComparativa().size(); i++) {
                boolean nuevo = true;
                for (int j = 0; j < getPrimerGuardado().size(); j++) {
                    String hashAntiguo = getPrimerGuardado().get(j).getHash();
                    String nombreAntiguo = getPrimerGuardado().get(j).getNombre();
                    String hashNuevo = getComparativa().get(i).getHash();
                    String nombreNuevo = getComparativa().get(i).getNombre();
                    if (hashAntiguo.equals(hashNuevo)) {
                        nuevo = false;
                    } else if (nombreAntiguo.equals(nombreNuevo)) {
                        nuevo = false;
                    }
                }
                if (nuevo == true) {
                    System.out.println(getComparativa().get(i).getNombre() + " se creo");
                    logger.escribir("["+getComparativa().get(i).getFechaEscaneo()+"][INTEGRIDAD] "+getComparativa().get(i).getNombre()+ " se creo");
                }
            }
            setPrimerGuardado(getComparativa());
        } else {
            System.out.println("No se encontró un guardado primario para continuar con el escaneo");
        }
    }
}
