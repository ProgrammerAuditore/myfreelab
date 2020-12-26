package index;

import controlador.InicioController;
import vista.paneles.panel_inicio;
import vista.ventanas.VentanaInicio;

public class Index {
    public static void main(String[] args) {
      
            System.out.println("Inicializando programa...");
            
            // * Inicializar el programa
            new InicioController( new VentanaInicio(new panel_inicio())).abrir_ventana();
        
    }
}
