package modelo.dao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import vista.paneles.panel_loggin;


public class VentanaDao {
    
    // Propiedades
    private JFrame ventana_activa=null;
    private JPanel p = new JPanel();
    
    // Métodos
    public boolean abrir_ventana(){
        if( !ventana_activa.isVisible() ){
            ventana_activa.setLocationRelativeTo(null);
            ventana_activa.setVisible(true);
            
            return true;
        }
        
        return false;
    }
    
    public boolean cerrar_ventana(){
        if( ventana_activa.isVisible() ){
            ventana_activa.setVisible(false);
            ventana_activa.dispose();
            
            return true;
        }
        
        return false;
    }

    public JFrame getVentana_activa() {
        return ventana_activa;
    }

    public void setVentana_activa(JFrame ventana_activa) {
        this.ventana_activa = ventana_activa;
    }

    public JPanel getP() {
        return p;
    }

    public void setP(JPanel p) {
        this.p = p;
    }
    
}
