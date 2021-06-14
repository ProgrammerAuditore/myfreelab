package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import hilos.HiloSplash;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.FabricarModal;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import src.Info;
import src.Source;
import vista.ventanas.Testing;
import vista.ventanas.VentanaPrincipal;

public class Index {
    public static void main(String[] args) {
        // * Inicializar el programa
        //System.out.println("Inicializando programa...");
        
        switch (args.length) {
            case 0:
                mtdInitMyfreelab();
                break;
            case 1:
                switch( args[0] ){
                    case "--pid" : mtdObtenerPID(); break;
                    case "--init" : mtdInitMyfreelab(); break;
                    case "--test" : mtdTesting();  break;
                    case "-h" :
                    case "--help" : mtdAyuda();  break;
                }   break;
            default:
                mtdAyuda();
                break;
        }
        
    }
    
    // * Inicializar el programa
    public static void mtdInitMyfreelab(){
        // * Crear hilos
        HiloConexion hc = new HiloConexion();
        HiloPrincipal hp = new HiloPrincipal();
        HiloSplash hs = new HiloSplash();
        hc.setDaemon(true);
        hp.setDaemon(true);
        hs.setDaemon(true);
        
        // * Crear la ventana principal con su respectivo patrón de diseño MVC
        VentanaPrincipal frame_p = new VentanaPrincipal();
        ProyectoDao daoP = new ProyectoDao();
        EmpresaDao daoE = new EmpresaDao();
        RequisitoDao daoR = new RequisitoDao();
        FabricarModal fabrica = new FabricarModal(frame_p);
        CtrlPrincipal ctrl_p = new CtrlPrincipal(frame_p, fabrica, daoP, daoE, daoR);
    
        // * Ejecutar hilos
        hs.start();
        hc.start();
        hp.start();
        
        try {
            hs.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frame_p.setVisible(true);
    
    }
    
    // * Ver información del programa
    public static void mtdObtenerPID(){
         String result = null;
        //String cmd = "ps --pid " + Source.PID + " -o pid,cmd ";
        String cmd = "ps -C java -o pid=";
        try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
            result = s.hasNext() ? s.next() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("" + result);
        System.out.println("" + Source.PID);
        System.out.println("Exect = " + result.contains("" + Source.PID) );
        System.exit(0);
    
    }
    
    // * Inicializar el programa de pruebas
    public static void mtdTesting(){
        // * Inicializar testeo
        Testing probar = new Testing();
        probar.setLocationRelativeTo(null);
        probar.setVisible(true);
        
    }
    
    public static void mtdAyuda(){
        System.out.println(Info.NombreSoftware);
        System.out.println(Info.Autor);
        System.out.println("");
        System.out.println("flags: ");
        
        System.out.print("  --init      ");
        System.out.println("Inicializar el programa");
        
        System.out.print("  --pid       ");
        System.out.println("Ver el PID del programa");
        
        System.out.print("  --help, -h  ");
        System.out.println("Obtener ayuda del programa");
        
        System.out.println("");
        System.out.println(Info.Homepage);
   
    }
    
}
