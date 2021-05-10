package index;

import controlador.CtrlPrincipal;
import modelo.MdlPrincipal;
import vista.paneles.PanelInicio;
import vista.ventanas.Testing;
import vista.ventanas.VentanaInicio;
import vista.ventanas.VentanaPrincipal;

public class Index {
    public static void main(String[] args) {
      
        // * Inicializar el programa
        System.out.println("Inicializando programa...");
        
        // * Crear la ventana principal con su respectivo patrón de diseño MVC
        VentanaPrincipal frame_p = new VentanaPrincipal();
        MdlPrincipal mdl_p = new MdlPrincipal();
        CtrlPrincipal ctrl_p = new CtrlPrincipal(mdl_p, frame_p);
        frame_p.setVisible(true);
        
//        Testing probar = new Testing();
//        probar.setLocationRelativeTo(null);
//        probar.setVisible(true);
        
    }
}
