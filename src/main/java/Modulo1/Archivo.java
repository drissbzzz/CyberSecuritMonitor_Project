/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modulo1;

import java.io.File;
import java.time.LocalDateTime;

/**
 *
 * @author driss
 */
public class Archivo {
    
    String nombre;
    String metadatos;
    String hash;
    LocalDateTime fechaEscaneo;
    Hasher h = new Hasher();
    
    public Archivo (File archivo){       
        setMetadatos(archivo.getAbsolutePath() + archivo.getName() + archivo.length() + archivo.lastModified());
        setNombre(archivo.getName());
        setFechaEscaneo(LocalDateTime.now());
        byte[] mdBytes = metadatos.getBytes();
        setHash(h.hash(mdBytes));
    }
    
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaEscaneo() {
        return fechaEscaneo;
    }

    public void setFechaEscaneo(LocalDateTime fechaEscaneo) {
        this.fechaEscaneo = fechaEscaneo;
    }

    public String getMetadatos() {
        return metadatos;
    }

    public void setMetadatos(String metadatos) {
        this.metadatos = metadatos;
    }
    
}
