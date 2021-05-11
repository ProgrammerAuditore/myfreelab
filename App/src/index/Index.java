package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import hilos.HiloSplash;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.MdlPrincipal;
import vista.ventanas.VentanaPrincipal;

public class Index {
    public static void main(String[] args) {
        
        // * Inicializar el programa
        System.out.println("Inicializando programa...");
        
        

        // * Crear hilos
        HiloConexion hc = new HiloConexion();
        HiloPrincipal hp = new HiloPrincipal();
        HiloSplash hs = new HiloSplash();
        hc.setDaemon(true);
        hp.setDaemon(true);
        hs.setDaemon(true);
        
        // * Crear la ventana principal con su respectivo patrón de diseño MVC
        VentanaPrincipal frame_p = new VentanaPrincipal();
        MdlPrincipal mdl_p = new MdlPrincipal();
        CtrlPrincipal ctrl_p = new CtrlPrincipal(mdl_p, frame_p);
        
//        Testing probar = new Testing();
//        probar.setLocationRelativeTo(null);
//        probar.setVisible(true);
    
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
}
