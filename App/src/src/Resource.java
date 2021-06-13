package src;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
        
        if ( OS.contains("win") ) {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(Rutas.pathIconoDefultMs));
        } else if ( OS.contains("mac") ) {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(Rutas.pathIconoDefultApple));
        } else {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(Rutas.pathIconoDefultMs));
        }
        
    }
    
    public HashMap<String, String> docJasper(){
        String OS = System.getProperty("os.name").toLowerCase();
        HashMap<String, String> docJasper = new HashMap<String, String>();
        
        if( OS.contains("win") ){
            
            File shared = new File(Rutas.pathSharedWin);
            if( shared.exists() && shared.isDirectory() )
                docJasper.put("root_dir", shared.getAbsolutePath() + "\\");
            
            File jasper = new File(Rutas.pathSharedWin+"\\"+"CotizacionOrg.jasper");
            if( jasper.exists() )
                docJasper.put("jasper_file", jasper.getAbsolutePath());
            
            File jrxml = new File(Rutas.pathSharedWin+"\\"+"CotizacionOrg.jrxml");
            if( jrxml.exists() )
                docJasper.put("jrxml_file", jrxml.getAbsolutePath());
            
        }else{
            
            File shared = new File(Rutas.pathSharedLinux);
            if( shared.exists() && shared.isDirectory() )
                docJasper.put("root_dir", shared.getAbsolutePath() + "/");
            
            File jasper = new File(Rutas.pathSharedLinux+"/"+"CotizacionOrg.jasper");
            if( jasper.exists() )
                docJasper.put("jasper_file", jasper.getAbsolutePath());
            
            File jrxml = new File(Rutas.pathSharedLinux+"/"+"CotizacionOrg.jrxml");
            if( jrxml.exists() )
                docJasper.put("jrxml_file", jrxml.getAbsolutePath());
            
        }
        
        
        return docJasper;
    }

    
}
