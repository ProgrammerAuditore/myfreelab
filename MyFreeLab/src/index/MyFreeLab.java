package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import hilos.HiloSplash;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import modelo.FabricarModal;
import modelo.ObjEjecucionXml;
import modelo.dao.ConexionDao;
import modelo.dao.EmpresaDao;
import modelo.dao.PreferenciaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import modelo.dto.ConexionDto;
import modelo.dto.PreferenciaDto;
import src.Info;
import src.Recursos;
import src.idiomas.Idiomas;
import vista.ventanas.VentanaPrincipal;

public class MyFreeLab {
    
    private VentanaPrincipal ventana;
    public static Properties idioma = new Idiomas("es");
    
    public void mtdTagInit() {
        
        MyFreeLab.mtdVerificarArranque();
        
        mtdInit();
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
    
    public static void mtdVerificarArranque(){
        if( Recursos.OsLinuxDeb )
            MyFreeLab.mtdVerificarPIDLinux();
        else if ( Recursos.OsWin )
            MyFreeLab.mtdVerificarPIDWin();
    }
    
    private void mtdInit(){
        PreferenciaDao dao = new PreferenciaDao();
        PreferenciaDto dto;
        
        if( dao.obtener_datos() == null ){ 
            dto = new PreferenciaDto();
        } else{
            dto = dao.obtener_datos();
        }
            
            if( dto.getIdioma().trim().equals("Español") || dto.getIdioma().trim().equals("Spanish") )
                idioma = new Idiomas("es");
            else
                idioma = new Idiomas("en");
            
            Recursos.mtdCambiarFuente( dto.getFuente() );
            Recursos.styleButtonDefault = dto.getEstilo();
            
    }
    
    public static void mtdVerificarPIDLinux(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        
        if( Recursos.dataRun().getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo(Recursos.dataRun().getAbsolutePath() );
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
            
            try{
                if( result.contains(pid) ){
                    System.out.println(Info.NombreSoftware + " en ejecucion. ");
                    System.exit(0);
                } else {
                    if( Recursos.dataRun().delete() ){
                        // *WARNING* PID corrupto
                        System.out.println("[!] " + pid);
                    }
                }
            }catch(Exception ex){
                System.out.println(Info.NombreSoftware + " suspendido. ");
                archivoRun.mtdGenerarXmlRun();
                System.exit(0);
            }

        }
        
        if( archivoRun.mtdGenerarXmlRun() )
                archivoRun.setPath_archivo(Recursos.dataRun().getAbsolutePath() );
        
    }
    
    public static void mtdVerificarPIDWin(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        
        if( Recursos.dataRun().getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo(Recursos.dataRun().getAbsolutePath() );
            //System.out.println("XX " + Recursos.dataRun.getAbsolutePath());
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

            //System.out.println("Respuesta CMD : " + result);
            //System.out.println("PID : " + pid);
            try{
                if( result.contains(pid) ){
                    System.out.println(Info.NombreSoftware + " en ejecucion. ");
                    System.exit(0);
                } else {
                    if( Recursos.dataRun().delete() ){
                        // *WARNING* PID corrupto
                        System.out.println("[!] " + pid);
                    }
                }
            }catch(Exception ex){
                System.out.println(Info.NombreSoftware + " suspendido. ");
                archivoRun.mtdGenerarXmlRun();
                System.exit(0);
            }

        }
        
        if( archivoRun.mtdGenerarXmlRun() )
                archivoRun.setPath_archivo(Recursos.dataRun().getAbsolutePath() );
        
    }
    
    // * Inicializar el programa de pruebas
    public void mtdTagTest(){
        mtdVerInformacionDelSoftware();
        
        /*
        try {
            ObjVersionesXml objDocXml = new ObjVersionesXml();
            File xml = new File( getClass().getResource("../" + Recursos.docVersionesXml).toURI() );
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
        
        if( Recursos.dataRun().getAbsoluteFile().exists() ){
            archivoRun.setPath_archivo(Recursos.dataRun().getAbsolutePath() );
            String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "";
            
            // * Verificar el sistema operativo
            if( Recursos.OsLinuxDeb )
                cmd = "ps -C java -o pid=";
            if ( Recursos.OsWin )
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
                System.out.println("[x] " + Recursos.PID);
            }
            
        } else{
            // *WARNING* PID propia
            System.out.println("[x] " + Recursos.PID);
        }
    }
    
    // * Mostrar mensaje de ayuda en la terminal
    public void mtdTagHelp(){
        System.out.println(Info.NombreSoftware);
        System.out.println(Info.Avatar);
        System.out.println("");
        System.out.println("flags: ");
        
        System.out.print("  --init              ");
        System.out.println("Iniciar " + Info.NombreSoftware);
        
        System.out.print("  --mkrun, -mr        ");
        System.out.println("Generar archivo de configuración .run");
        
        System.out.print("  --mkconn, -mc       ");
        System.out.println("Generar archivo de configuración .run");
        
        System.out.print("  --mkpref, -mp       ");
        System.out.println("Generar archivo de configuración .run");
        
        System.out.print("  --pid               ");
        System.out.println("Ver el PID del programa");
        
        System.out.print("  --help, -h          ");
        System.out.println("Obtener ayuda del programa");
        
        System.out.println("");
        System.out.println(Info.SitioWeb);
   
    }

    private void mtdVerInformacionDelSoftware() {
        System.out.println("#DirHome : " + Recursos.dirHome );
        System.out.println("#Path Actual : " + new File(".").getAbsolutePath());
        System.out.println("#PID : " + mtdObtenerPID() );
        System.out.println("#SO : " + Recursos.SistemaOs);
        System.out.println("#TimeTmp : " + Recursos.timeTmp);
        System.out.println("#bkgAside : " + Recursos.bkgAside);
        System.out.println("#bkgLogo : " + Recursos.bkgLogo);
        System.out.println("#dataPreferencias : " + Recursos.dataPreferencias().getAbsolutePath());
        System.out.println("#dataConexion : " + Recursos.dataConexion().getAbsolutePath());
        System.out.println("#dataRun : " + Recursos.dataRun().getAbsolutePath());
        System.out.println("#docVersionesXml : " + Recursos.docVersionesXml);
        System.out.println("#docReporte : " + Recursos.docCotizacionJasper());
        System.out.println("#\n");
    }
    
    private int mtdObtenerPID(){
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();

        if( Recursos.dataRun().getAbsoluteFile().exists() ){
                archivoRun.setPath_archivo(Recursos.dataRun().getAbsolutePath() );
                String pid = archivoRun.mtdMapearXmlRun().get("app_pid");

                // * Obtener todos los procesos PID de java
                String result = null;
                String cmd = "";

                // * Verificar el sistema operativo
                if( Recursos.OsLinuxDeb ){
                    cmd = "ps -C java -o pid=";
                }if ( Recursos.OsWin ){
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
        return Recursos.PID;
    }


    void mtdTagMkRun() {
        File run = Recursos.dataRun();
        ObjEjecucionXml archivoRun = new ObjEjecucionXml();
        archivoRun.setPath_archivo(run.getAbsolutePath() );
        
        if( run.exists() ) 
            run.delete();
        
        if( archivoRun.mtdGenerarXmlRun() ){
            System.out.println("" + run.getAbsolutePath());
        }
        
    }
    
    void mtdTagMkConn() {
        File conn = Recursos.dataRun();
        
        if( conn.exists() )
            conn.delete();
            
        ConexionDto conec = new ConexionDto("0", "", "", "", "");
        new ConexionDao().regitrar_datos(conec);
        
        if( conn.exists() ){
            System.out.println("" + conn.getAbsolutePath());
        }
        
    }

    void mtdTagMkPref() {
        File pconfig = Recursos.dataPreferencias();
        
        if( pconfig.exists() )
            pconfig.delete();
        
        PreferenciaDto pref = new PreferenciaDto();
        new PreferenciaDao().regitrar_datos(pref);
        
        if( pconfig.exists() ){
            System.out.println("" + pconfig.getAbsolutePath());
        }
    }
    
}
