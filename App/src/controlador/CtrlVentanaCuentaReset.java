package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.VentanaDao;
import vista.paneles.panel_loggin;
import vista.paneles.panel_recuper_cuenta;
import vista.ventanas.Base;

public class CtrlVentanaCuentaReset extends VentanaDao {
    
    public CtrlVentanaCuentaReset() {
    }
    
    public CtrlVentanaCuentaReset(Base ventana_principal) {
        this.setVentana_activa(ventana_principal);
        this.getVentana_activa().setTitle("Recuperar cuenta");
        this.setP(ventana_principal.getPanel_contenedor());
        
        this.abrir_ventana();
    }
    
   
    
    public void testing(){       
    }
    
}
