/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Modulo1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author driss
 */
public class Logger {
    
    String nombre;
    JTextArea area;
    
    public Logger(String nombreArchivo, JTextArea jA){ //Recibe el TextArea para poder escribir simultaneamente en archivo e interfaz
        this.nombre = nombreArchivo;
        this.area = jA;
    }
    public Logger(String nombreArchivo){
        this.nombre = nombreArchivo;
    }  
    public synchronized void escribir(String mensaje) {
        try (FileWriter fw = new FileWriter(nombre, true); 
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(mensaje);          
        } catch (IOException e) {
            System.err.println("Error escribiendo en el log ("+nombre+"): " + e.getMessage());
        }
        if (area != null) {
            SwingUtilities.invokeLater(() -> {
                area.append(mensaje + "\n");
                area.setCaretPosition(area.getDocument().getLength());
            });
        }
    }
}
