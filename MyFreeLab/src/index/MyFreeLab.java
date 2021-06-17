package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import hilos.HiloSplash;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import modelo.FabricarModal;
import modelo.ObjEjecucionXml;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import src.Info;
import src.Source;
import vista.ventanas.VentanaPrincipal;

public class MyFreeLab {
    
    private VentanaPrincipal ventana;
    
    public void mtdTagInit() {
        
        if( Source.OsLinuxDeb )
            mtdVerificarPIDLinux();
        else if ( Source.OsWin )
            mtdVerificarPIDWin();
        
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
        
        // * Abrir la ventana del programa
        ventana.setState(JFrame.ICONIFIED);
        ventana.setVisible(true);
        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {}
        ventana.setAutoRequestFocus(true);
        ventana.requestFocus();
        ventana.setExtendedState(JFrame.NORMAL);
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
    
    public void mtdVerificarPIDWin(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        
        if( Source.dataRun.getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
            //System.out.println("XX " + Source.dataRun.getAbsolutePath());
            String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "tasklist /fi \"imagename eq java*\" ";
            try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                result = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
                // e.printStackTrace();
            }

            //System.out.println("RS : " + result);
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
    public void mtdTagTest(){
        mtdVerInformacionDelSoftware();
        
        /*
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
        */
        
        /*
        // * Inicializar testeo
        Testing probar = new Testing();
        probar.setLocationRelativeTo(null);
        probar.setVisible(true);
        */
    }
    
    // * Obtener el PID del programa
    public void mtdTagPID(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        
        if( Source.dataRun.getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
            String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "";
            
            // * Verificar el sistema operativo
            if( Source.OsLinuxDeb )
                cmd = "ps -C java -o pid=";
            if ( Source.OsWin )
                cmd = "tasklist /fi \"imagename eq java*\" ";
            
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
    public void mtdTagHelp(){
        System.out.println(Info.NombreSoftware);
        System.out.println(Info.Autor);
        System.out.println("");
        System.out.println("flags: ");
        
        System.out.print("  --init       ");
        System.out.println("Iniciar " + Info.NombreSoftware);
        
        System.out.print("  --pid       ");
        System.out.println("Ver el PID del programa");
        
        System.out.print("  --help, -h  ");
        System.out.println("Obtener ayuda del programa");
        
        System.out.println("");
        System.out.println(Info.Homepage);
   
    }

    private void mtdVerInformacionDelSoftware() {
        System.out.println("#docReporte : " + Source.dirHome );
        System.out.println("#Path Actual : " + new File(".").getAbsolutePath());
        System.out.println("#PID : " + mtdObtenerPID() );
        System.out.println("#SO : " + Source.SistemaOs);
        System.out.println("#TimeTmp : " + Source.timeTmp);
        System.out.println("#bkgAside : " + Source.bkgAside);
        System.out.println("#bkgLogo : " + Source.bkgLogo);
        System.out.println("#dataConexion : " + Source.dataConexion.getAbsolutePath());
        System.out.println("#dataRun : " + Source.dataRun.getAbsolutePath());
        System.out.println("#docVersionesXml : " + Source.docVersionesXml);
        System.out.println("#docReporte : " + Source.docReporte);
        System.out.println("#\n");
    }
    
    private int mtdObtenerPID(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();

        if( Source.dataRun.getAbsoluteFile().exists() ){
                archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
                String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

                // * Obtener todos los procesos PID de java
                String result = null;
                String cmd = "";

                // * Verificar el sistema operativo
                if( Source.OsLinuxDeb ){
                    cmd = "ps -C java -o pid=";
                }if ( Source.OsWin ){
                    cmd = "tasklist /fi \"imagename eq java*\" ";
                }

                // * Obtener todo los procesos PID de java
                try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                        Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                    result = s.hasNext() ? s.next() : null;
                    //System.out.println( result );
                } catch (IOException e) {
                    // e.printStackTrace();
                }

                // * Si el pid almacenado en el archivo .run
                // esta en ejecución devuelve el pid 
                if( result.contains(pid) )
                    return Integer.parseInt(pid);        
                
            }
        
        // * Si el pid almacenado en el archivo .run
        // no está en ejecución devuelve una nueva PID 
        return Source.PID;
    }
    
}