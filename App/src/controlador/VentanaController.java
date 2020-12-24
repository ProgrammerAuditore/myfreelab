package controlador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import vista.paneles.panel_loggin;


public class VentanaController {
    
    // Propiedades
    private JFrame ventanaActiva = null;
    
    // Métodos
    public boolean abrir_ventana(){
        if( !ventanaActiva.isVisible() ){
            ventanaActiva.setLocationRelativeTo(null);
            ventanaActiva.setVisible(true);
            
            return true;
        }
        
        return false;
    }
    
    public boolean cerrar_ventana_actual(){
        if( ventanaActiva.isVisible() ){
            ventanaActiva.setVisible(false);
            ventanaActiva.dispose();
            
            return true;
        }
        
        return false;
    }

    public JFrame getVentanaActiva() {
        return ventanaActiva;
    }

    public void setVentanaActiva(JFrame ventanaActiva) {
        this.ventanaActiva = ventanaActiva;
    }

}
