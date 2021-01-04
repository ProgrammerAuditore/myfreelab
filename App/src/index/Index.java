package index;

import controlador.InicioController;
import vista.paneles.panel_inicio;
import vista.ventanas.Testing;
import vista.ventanas.VentanaInicio;

public class Index {
    public static void main(String[] args) {
      
        System.out.println("Inicializando programa...");
            
        // * Inicializar el programa
        VentanaInicio ventana = new VentanaInicio(new panel_inicio());
        InicioController controlador = new InicioController( ventana  );
        controlador.fncAbrirVentana();
//        Testing probar = new Testing();
//        probar.setLocationRelativeTo(null);
//        probar.setVisible(true);
        
    }
}
