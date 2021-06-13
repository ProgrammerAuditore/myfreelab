package controlador;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;
import vista.paneles.PanelAcercaDe;

public class CtrlAcercaDe implements MouseListener{
    
    // Vista
    public JDialog modal;
    private PanelAcercaDe laVista;

    public CtrlAcercaDe(PanelAcercaDe laVista) {
        this.laVista = laVista;
        
        // Definir oyentes
        laVista.btnAceptar.addMouseListener(this);
        
    }
    
    public void mtdInit(){
        
        modal.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Acerca de");
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
        // Establecer evento para activar boton al leer la licencia
        laVista.contendor_licencia.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){ 
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                // Check if user has done dragging the scroll bar
                if(!e.getValueIsAdjusting()){
                    JScrollBar scrollBar = (JScrollBar) e.getAdjustable();
                    int extent = scrollBar.getModel().getExtent();
                    int maximum = scrollBar.getModel().getMaximum();
                    if(extent + e.getValue() == maximum){
                        laVista.contendor_licencia.setBorder(new LineBorder(Color.GREEN, 1));
                        laVista.btnAceptar.setEnabled(true);
                    }else{
                        laVista.contendor_licencia.setBorder(new LineBorder(Color.RED, 1));
                        laVista.btnAceptar.setEnabled(false);
                    }
                }
            }
        });

    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnAceptar )
            if( laVista.btnAceptar.isEnabled() )
                mtdBtnAceptar();
        
    }
    
    private void mtdBtnAceptar(){
        // * Cerrar el modal
        modal.setVisible(false);
        modal.dispose();
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
