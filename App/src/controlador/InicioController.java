package controlador;

import controlador.interfaces.ventana_acciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vista.paneles.panel_inicio;
import vista.ventanas.VentanaInicio;

public class InicioController extends VentanaController implements ventana_acciones{
    
    // Atributos o campos
    private panel_inicio mi_panel; // Vista
    
    // Constructuros
    public InicioController() {}
    
    public InicioController(VentanaInicio vp) {
        this.inicializar(vp);
    }
    
    // Métodos
    private void inicializar(VentanaInicio vp){
        // Para poder usar abrir y cerrar ventana
        this.setVentanaActiva( vp );
        
        // Para poder controlar los eventos en el panel
        this.mi_panel  = (panel_inicio) vp.getPanelContenedor();
        
        // Establecer propiedades a la ventana
        this.getVentanaActiva().setTitle("MyFreeLab v1.0");
        
        // Establecer ventana acciones
        this.eventos_de_mouse();
        this.eventos_de_teclado();
    }

    @Override
    public void eventos_de_teclado() {
        
    }

    @Override
    public void eventos_de_mouse() {
        
    }
 
}
