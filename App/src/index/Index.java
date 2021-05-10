package index;

import controlador.CtrlPrincipal;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import modelo.MdlPrincipal;
import vista.paneles.PanelInicio;
import vista.splash.Bienvenida;
import vista.ventanas.Testing;
import vista.ventanas.VentanaInicio;
import vista.ventanas.VentanaPrincipal;

public class Index {
    public static void main(String[] args) {
      
        // * Inicializar el programa
        System.out.println("Inicializando programa...");
        
        /*
        // * Splash de bienvenida
        Bienvenida splash = new Bienvenida();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        for (int i = 0; i <= 100; i++) {
            
            try {
                Thread.sleep(i * 2);
                splash.pbProgreso.setValue(i);
                splash.etqCarga.setText( "" + i + "%" );
                
            } catch (Exception e) {}
            
        }
        splash.setVisible(false);
        splash.dispose();
        */

        // * Crear hilos
        HiloConexion hc = new HiloConexion();
        HiloPrincipal hp = new HiloPrincipal();
        
        // * Crear la ventana principal con su respectivo patrón de diseño MVC
        VentanaPrincipal frame_p = new VentanaPrincipal();
        MdlPrincipal mdl_p = new MdlPrincipal();
        CtrlPrincipal ctrl_p = new CtrlPrincipal(mdl_p, frame_p);
        frame_p.setVisible(true);
        
//        Testing probar = new Testing();
//        probar.setLocationRelativeTo(null);
//        probar.setVisible(true);
    
        // * Ejecutar hilos
        hc.start();
        hp.start();

    }
}
