package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import hilos.HiloSplash;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.FabricarModal;
import modelo.ObjEjecucionXml;
import modelo.ObjVersionesXml;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import src.Info;
import src.Source;
import vista.ventanas.VentanaPrincipal;

public class MyFreeLab {
    
    private VentanaPrincipal ventana;
    
    public void mtdInit() {
        
        HiloConexion hc = new HiloConexion();
        HiloPrincipal hp = new HiloPrincipal();
        HiloSplash hs = new HiloSplash();
        hc.setDaemon(true);
        hp.setDaemon(true);
        hs.setDaemon(true);
        
        // * Crear la ventana principal con su respectivo patrón de diseño MVC
        ventana = new VentanaPrincipal();
        ProyectoDao daoP = new ProyectoDao();
        EmpresaDao daoE = new EmpresaDao();
        RequisitoDao daoR = new RequisitoDao();
        FabricarModal fabrica = new FabricarModal(ventana);
        CtrlPrincipal ctrl_p = new CtrlPrincipal(ventana, fabrica, daoP, daoE, daoR);
    
        // * Ejecutar hilos
        hs.start();
        hc.start();
        hp.start();
        
        try {
            hs.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ventana.setVisible(true);
        
    }
    
    public void mtdVerificarPIDLinux(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        
        if( Source.dataRun.getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
            String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "ps -C java -o pid=";
            try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                result = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
                // e.printStackTrace();
            }

            if( result.contains(pid) ){
                System.out.println(Info.NombreSoftware + " en ejecucion. ");
                System.exit(0);
            } else {
                Source.dataRun.delete();
                // *WARNING* PID corrupto
                System.out.println("[!] " + pid);
            }

        }
        
        if( archivoRun.mtdGenerarXmlRun() )
                archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
        
    }
    
    // * Inicializar el programa de pruebas
    public void mtdTesting(){
        
        try {
            ObjVersionesXml objDocXml = new ObjVersionesXml();
            File xml = new File( getClass().getResource("../" + Source.docVersionesXml).toURI() );
            objDocXml.setArchivoXml( xml );
            HashMap<String, String> info = objDocXml.mtdMapearUltimaVersionInterno();
            
            // * Establecer los datos
            System.out.println("" + info.get("app_name_version"));
            System.out.println("" + info.get("app_num_version"));
            System.out.println("" + info.get("app_novedades"));
        
        } catch (URISyntaxException ex) {
            //Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        // * Inicializar testeo
        Testing probar = new Testing();
        probar.setLocationRelativeTo(null);
        probar.setVisible(true);
        */
    }
    
    // * Obtener el PID del programa
    public void mtdObtenerPID(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        
        if( Source.dataRun.getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
            String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "ps -C java -o pid=";
            try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                result = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
                // e.printStackTrace();
            }
            
            if( result.contains(pid) ){
                // *WARNING* PID satisfactoria
                System.out.println("[¡] " + pid );
            }else{
                // *WARNING* PID propia
                System.out.println("[x] " + Source.PID);
            }
            
        } else{
            // *WARNING* PID propia
            System.out.println("[x] " + Source.PID);
        }
    }
    
    // * Mostrar mensaje de ayuda en la terminal
    public void mtdAyuda(){
        System.out.println(Info.NombreSoftware);
        System.out.println(Info.Autor);
        System.out.println("");
        System.out.println("flags: ");
        
        System.out.print("  --pid       ");
        System.out.println("Ver el PID del programa");
        
        System.out.print("  --help, -h  ");
        System.out.println("Obtener ayuda del programa");
        
        System.out.println("");
        System.out.println(Info.Homepage);
   
    }
    
}
