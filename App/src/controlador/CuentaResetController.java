package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.paneles.panel_loggin;
import vista.paneles.panel_recuper_cuenta;
import vista.ventanas.Base;

public class CuentaResetController extends VentanaController {
    
    // Atributos o campos
    private panel_recuper_cuenta mi_panel;
    
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
        this.eventos_de_mouse();
    }
        
    public void eventos_de_mouse(){
    }

}
