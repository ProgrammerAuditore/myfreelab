package src;

import java.io.File;
import java.io.IOException;

class Resource {

    public File dataConexion() {
        File data = null;
        try {
            // Crear la carpeta config
            new File("config").mkdir();
            // Crear el archivo conn dentro de la carpeta etc 
            new File( Rutas.pathDataConexion ).createNewFile(); 
            // Obtener el archivo conn
            data = new File( Rutas.pathDataConexion );
        } catch (IOException ex) {
            System.out.println("Resource :: Archivo conn no localizado...");
        }
        return data;
    }
    
}
