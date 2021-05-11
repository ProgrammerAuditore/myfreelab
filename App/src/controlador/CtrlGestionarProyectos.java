package controlador;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import vista.paneles.PanelGestionarProyectos;

public class CtrlGestionarProyectos implements MouseListener{
    
    private PanelGestionarProyectos laVista;
    public JDialog modal;

    public CtrlGestionarProyectos(PanelGestionarProyectos laVista) {
        this.laVista = laVista;
        
        // * Establecer oyentes
        this.laVista.btnBuscar.addMouseListener(this);
        this.laVista.btnCrear.addMouseListener(this);
        this.laVista.btnEliminar.addMouseListener(this);
        this.laVista.btnModificar.addMouseListener(this);
        
        mtdInit();
    }

    private void mtdInit() {
        modal = new JDialog();
        
        modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
    }


    @Override
    public void mouseReleased(MouseEvent e) {
    
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
