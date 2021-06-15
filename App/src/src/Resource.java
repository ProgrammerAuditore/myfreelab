package src;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

class Resource {
    
    public File dataConexion() {
        String OS = System.getProperty("os.name").toLowerCase();
        String WinTemp = System.getProperty("java.io.tmpdir");
        File data = null;
        String dir = "";
        
        try {
            
            // Verificar el sistema operativo
            if ( !(OS.indexOf("win") >= 0) ) {
                dir = "/var/tmp/myfreelab-cache-";
            } else { 
                dir = WinTemp+"myfreelab-cache-";
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
    
    public File dataRun() {
        String OS = System.getProperty("os.name").toLowerCase();
        String WinTemp = System.getProperty("java.io.tmpdir");
        File data = null;
        String dir = "";
        
        // Verificar el sistema operativo
        if ( !(OS.indexOf("win") >= 0) )
            dir = "/var/tmp/myfreelab-cache-";
        else
            dir = WinTemp + "myfreelab-cache-";

        // Crear la carpeta config
        new File( dir + "config").mkdir();

        //  * Generar la ruta del archivo
        File  archivoRun = new File( dir + Rutas.pathDataEjecucion ); 

        // Obtener el archivo
        data = archivoRun;
            
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

    int getPID() {
        String vmName = ManagementFactory.getRuntimeMXBean().getName();
        int pid_a = vmName.indexOf("@");
        String pid = vmName.substring(0, pid_a);
        
        return Integer.parseInt(pid);
    }

    boolean getLinux() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }
    
    boolean getWin() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.contains("win");
    }
    
    String getTimeTmp(){
        String time = java.time.LocalDateTime.now().toString();
        time = time.replace(':', '-');
        time = time.replace('.', '-');
        time = time.replaceAll("-", "");
        
        return time;
    }
        
}
