package src;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

class Resource {

    public File dataConexion() {
        String OS = System.getProperty("os.name").toLowerCase();
        File data = null;
        String dir = "";
        
        try {
            
            // Verificar el sistema operativo
            if ( !(OS.indexOf("win") >= 0) ) {
                dir = "/var/tmp/myfreelab-cache-";
            }
            
            // Crear la carpeta config
            new File( dir + "config").mkdir();
            
            // Crear el archivo conn dentro de la carpeta etc 
            new File( dir + Rutas.pathDataConexion ).createNewFile(); 
            
            // Obtener el archivo conn
            //System.out.println("Archivo conn :: " + dir + Rutas.pathDataConexion);
            data = new File( dir + Rutas.pathDataConexion );
            
        } catch (IOException ex) {
            //System.out.println("Resource :: Archivo conn no localizado...");
        }
        
        return data;
    }
    
    public Image iconoDefault(){
        String OS = System.getProperty("os.name").toLowerCase();
        
        if (OS.indexOf("win") >= 0) {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(Rutas.pathIconoDefultMs));
        } else if (OS.indexOf("mac") >= 0) {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(Rutas.pathIconoDefultApple));
        } else {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(Rutas.pathIconoDefultMs));
        }
        
    }
    
}
