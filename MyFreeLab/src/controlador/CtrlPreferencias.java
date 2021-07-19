package controlador;

import index.MyFreeLab;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JDialog;
import modelo.dao.PreferenciaDao;
import modelo.dto.PreferenciaDto;
import src.Info;
import vista.paneles.PanelAcercaDe;
import vista.paneles.PanelPreferencias;

public class CtrlPreferencias implements MouseListener{
    
    // Vista
    public JDialog modal;
    private PanelPreferencias laVista;
    
    // Modelos
    private PreferenciaDao dao;
    private PreferenciaDto dto;

    public CtrlPreferencias(PanelPreferencias laVista, PreferenciaDto dto, PreferenciaDao dao) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
        
        // Definir oyentes
        laVista.btnAceptar.addMouseListener(this);
        laVista.btnCancelar.addMouseListener(this);
    }
    
    public void mtdInit(){
        mtdEstablecerDatos();
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlPreferencias.mtdInit.titulo"));
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
            }
        });
        
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnAceptar )
            if( laVista.btnAceptar.isEnabled() )
                mtdBtnAceptar();
        
        if( e.getSource() == laVista.btnCancelar )
            mtdBtnCancelar();
        
    }
    
    private void mtdBtnAceptar(){
        // * Cerrar el modal
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdBtnCancelar(){
        // * Cerrar el modal
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdEstablecerDatos(){
        
        // * Obtener los datos
        if( dao.obtener_conexion() != null ){
            dto = dao.obtener_conexion();
        }else{
            dto = new PreferenciaDto();
        }
        
        
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
