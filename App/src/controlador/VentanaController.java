package controlador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import vista.paneles.panel_loggin;


public class VentanaController {
    
    // Propiedades
    private JFrame ventanaActiva = null;
    private JFrame ventanaAnterior = null;
    
    public VentanaController(){}
    
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
    
    public void volver_ventana_anterior(){
        JFrame ant =  ventanaAnterior;
        ant.setLocationRelativeTo(null);
        ant.setVisible(true);
        
        JFrame act =  ventanaActiva;
        act.setLocationRelativeTo(null);
        act.setVisible(false);
        act.dispose();
    }

    public JFrame getVentanaActiva() {
        return ventanaActiva;
    }

    public void setVentanaActiva(JFrame ventanaActiva) {
        this.ventanaActiva = ventanaActiva;
    }

    public JFrame getVentanaAnterior() {
        return ventanaAnterior;
    }

    public void setVentanaAnterior(JFrame ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
    }
        
}
