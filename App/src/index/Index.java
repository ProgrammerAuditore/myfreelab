package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import hilos.HiloSplash;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import vista.ventanas.VentanaPrincipal;

public class Index {
    public static void main(String[] args) {
        
        // * Inicializar el programa
        //System.out.println("Inicializando programa...");
        
        

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
        CtrlPrincipal ctrl_p = new CtrlPrincipal(frame_p, daoP, daoE);
        
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
