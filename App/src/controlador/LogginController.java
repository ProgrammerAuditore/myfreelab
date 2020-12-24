package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.paneles.panel_loggin;
import vista.paneles.panel_recuper_cuenta;
import vista.ventanas.Base;

public class LogginController extends VentanaController {
    
    public LogginController() {
    }
    
    public LogginController(Base ventana_principal) {
        this.setVentana_activa(ventana_principal);
        this.getVentana_activa().setTitle("Iniciar session");
        this.setP(ventana_principal.getPanel_contenedor());
        
        this.abrir_ventana();
        this.eventos_de_mouse();
    }
    
    public void eventos_de_mouse(){
        panel_loggin a  = (panel_loggin) this.getP();
        a.lbl_recuperar_cuenta.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Recuperar cuenta...");
                CuentaResetController a1 = new CuentaResetController( new Base(new panel_recuper_cuenta()) );
                cerrar_ventana();
            }
        });
    }
    
}
