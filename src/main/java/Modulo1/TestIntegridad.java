package Modulo1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestIntegridad {

    public static void main(String[] args) {
        // 1. PREPARACIÓN DEL ENTORNO
        // Creamos una carpeta de prueba dentro del proyecto
        String carpetaPrueba = "carpeta_secreta";
        crearCarpeta(carpetaPrueba);

        // Creamos unos archivos iniciales (El estado "Seguro")
        crearArchivo(carpetaPrueba + "/config.txt", "Configuración inicial: Puerto=8080");
        crearArchivo(carpetaPrueba + "/usuarios.db", "admin:1234");
        crearArchivo(carpetaPrueba + "/notas.txt", "Reunión el lunes");

        System.out.println("--- ENTORNO PREPARADO ---");
        System.out.println("Archivos originales creados en: " + carpetaPrueba);
        System.out.println("-------------------------");

        // 2. INICIAR EL MONITOR
        // Instanciamos tu GestorArchivos pasándole la ruta
        GestorArchivos monitor = new GestorArchivos(carpetaPrueba);
        
        // ¡IMPORTANTE! Activar el flag 'activo' para que el bucle funcione
        monitor.setActivo(true); 
        
        // Arrancamos el hilo
        monitor.start();

        try {
            // Esperamos 4 segundos para asegurarnos de que haga el primer escaneo
            System.out.println("\n[TEST] Monitor arrancando... Esperando primer escaneo...");
            Thread.sleep(4000);

            // 3. SIMULACIÓN DE ATAQUE (Modificaciones en tiempo real)
            System.out.println("\n--- INICIANDO ATAQUE SIMULADO ---");

            // A) MODIFICACIÓN: Cambiamos el contenido de config.txt
            System.out.println("[ATACANTE] Modificando 'config.txt'...");
            crearArchivo(carpetaPrueba + "/config.txt", "Configuración HACKEADA: Puerto=666");

            // B) ELIMINACIÓN: Borramos usuarios.db
            System.out.println("[ATACANTE] Borrando 'usuarios.db'...");
            new File(carpetaPrueba + "/usuarios.db").delete();

            // C) CREACIÓN: Añadimos un script malicioso
            System.out.println("[ATACANTE] Creando 'virus.sh'...");
            crearArchivo(carpetaPrueba + "/virus.sh", "rm -rf /");

            // 4. VERIFICACIÓN
            // Esperamos a que el monitor despierte (ciclo de 2s) y detecte los cambios
            System.out.println("[TEST] Esperando reacción del monitor...");
            Thread.sleep(4000);

            System.out.println("\n--- FIN DEL TEST ---");
            System.out.println("Revisa la consola y el archivo log_sdas.txt");
            System.exit(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // --- Métodos de utilidad para no ensuciar el main ---

    private static void crearCarpeta(String ruta) {
        File f = new File(ruta);
        if (!f.exists()) {
            f.mkdir();
        }
    }

    private static void crearArchivo(String ruta, String contenido) {
        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write(contenido);
        } catch (IOException e) {
            System.out.println("Error creando archivo de test: " + e.getMessage());
        }
    }
}


