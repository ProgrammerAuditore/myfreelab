package controlador;

import controlador.interfaces.ventana_acciones;
import vista.paneles.panel_recuper_cuenta;
import vista.ventanas.Base;

public class CuentaResetController extends VentanaController implements ventana_acciones{
    
    // Atributos o campos
    private panel_recuper_cuenta mi_panel; // Vista
    
    // Constructuros
    public CuentaResetController() {}
    
    public CuentaResetController(Base vp) {
        this.inicializar(vp);
    }
    
    // Métodos
    private void inicializar(Base vp){
        // Para poder usar abrir y cerrar ventana
        this.setVentanaActiva( vp );
        
        // Para poder controlar los eventos en el panel
        this.mi_panel  = (panel_recuper_cuenta) vp.getPanelContenedor();
        
        // Establecer propiedades a la ventana
        this.getVentanaActiva().setTitle("Recuperar cuenta");
        
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
