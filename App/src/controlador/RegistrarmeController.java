package controlador;

import controlador.interfaces.ventana_acciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vista.paneles.panel_registrarme;
import vista.ventanas.Base;

public class RegistrarmeController extends VentanaController implements ventana_acciones{
    
    // Atributos o campos
    private panel_registrarme mi_panel; // Vista
    
    // Constructuros
    public RegistrarmeController() {}
    
    public RegistrarmeController(Base vp) {
        this.inicializar(vp);
    }
    
    // Métodos
    private void inicializar(Base vp){
        // Para poder usar abrir y cerrar ventana
        this.setVentanaActiva( vp );
        
        // Para poder controlar los eventos en el panel
        this.mi_panel  = (panel_registrarme) vp.getPanelContenedor();
        
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
        this.fncVolver();
    }
    
    private void fncVolver() {
        Base a = (Base) this.getVentanaActiva();
        a.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                volver_ventana_anterior();
            }
            
        });
        
    }

}
