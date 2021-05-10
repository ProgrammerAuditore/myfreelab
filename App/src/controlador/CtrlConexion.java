package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import modelo.MdlConexion;
import vista.paneles.PanelConexion;

public class CtrlConexion implements MouseListener{
    
    private PanelConexion laVista;
    private MdlConexion elModelo;
    public JDialog modal;

    public CtrlConexion(PanelConexion laVista, MdlConexion elModelo) {
        this.laVista = laVista;
        this.elModelo = elModelo;
        
        // * Definir Oyentes
        this.laVista.btnCerrarConexion.addMouseListener(this);
        this.laVista.btnEstablecerConexion.addMouseListener(this);
        
        
        // * Crear el modal
        mtdInit();
    }
    
    private void mtdInit(){
        modal = new JDialog();
        
        modal.setModal(true);
        modal.setTitle("Configurar conexión");
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setResizable(false);
        modal.setContentPane(laVista);
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnCerrarConexion )
            mtdCerrarConexion();
        
        if( e.getSource() == laVista.btnEstablecerConexion )
            mtdEstablecerConexion();
    }
    
    private void mtdEstablecerConexion() {
        System.out.println("Establecer conexión");
    }
    
    private void mtdCerrarConexion(){
        System.out.println("Cerrar conexión");
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
