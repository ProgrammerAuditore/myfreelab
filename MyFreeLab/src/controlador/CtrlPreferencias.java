package controlador;

import index.MyFreeLab;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.PreferenciaDao;
import modelo.dto.PreferenciaDto;
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
        if( mtdObtenerDatos() ){
            dto.setIdioma( String.valueOf(laVista.cmboxIdiomas.getSelectedItem()) );
            dto.setFuente( String.valueOf(laVista.cmboxFuentes.getSelectedItem()) );
            dto.setEstilo( String.valueOf(laVista.cmboxEstilos.getSelectedItem()) );
            
            dao.regitrar_datos(dto);
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlPreferencias.mtdBtnAceptar.msg1"));
            
            mtdBtnCancelar();
            
        }else
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlPreferencias.mtdBtnAceptar.msg2"));
    }
    
    private void mtdBtnCancelar(){
        // * Cerrar el modal
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdEstablecerDatos(){
        
        // * Obtener los datos
        if( dao.obtener_datos() != null ){
            dto = dao.obtener_datos();
        }else{
            dto = new PreferenciaDto();
        }
        
        if( MyFreeLab.idioma.getProperty("lang").equals("English") ){
            laVista.cmboxIdiomas.removeAllItems();
            laVista.cmboxIdiomas.addItem(MyFreeLab.idioma
                        .getProperty("ctrlPreferencias.mtdEstablecerDatos.msg1"));
            laVista.cmboxIdiomas.addItem(MyFreeLab.idioma
                        .getProperty("ctrlPreferencias.mtdEstablecerDatos.msg2"));
        }
        
        laVista.cmboxEstilos.setSelectedItem(dto.getEstilo().trim());
        laVista.cmboxFuentes.setSelectedItem(dto.getFuente().trim());
        
        if( dto.getIdioma().trim().equals("Español") || dto.getIdioma().trim().equals("Spanish")  ){
            laVista.cmboxIdiomas.setSelectedIndex(0);
        }else{
            laVista.cmboxIdiomas.setSelectedIndex(1);
        }
        
    }
    
    private boolean mtdObtenerDatos(){
        int contador=1;
        return (contador > 0);
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
