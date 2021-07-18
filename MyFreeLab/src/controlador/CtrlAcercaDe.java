package controlador;

import index.MyFreeLab;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JDialog;
import src.Info;
import vista.paneles.PanelAcercaDe;

public class CtrlAcercaDe implements MouseListener{
    
    // Vista
    public JDialog modal;
    private PanelAcercaDe laVista;

    public CtrlAcercaDe(PanelAcercaDe laVista) {
        this.laVista = laVista;
        
        // Definir oyentes
        laVista.btnAceptar.addMouseListener(this);
        laVista.etqLink.addMouseListener(this);
        
    }
    
    public void mtdInit(){
        mtdEstablecerDatos();
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlAcercaDe.mtdInit.titulo"));
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);

    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnAceptar )
            if( laVista.btnAceptar.isEnabled() )
                mtdBtnAceptar();
        
        if( e.getSource() == laVista.etqLink )
            mtdVisitarSitioWeb();
        
    }
    
    private void mtdBtnAceptar(){
        // * Cerrar el modal
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdEstablecerDatos(){
        this.laVista.etqTitulo.setText( Info.NombreSoftware );
        
        this.laVista.cmpDescripcion.setLineWrap(true);
        this.laVista.cmpDescripcion.setEditable(false);
        
        String acercaDe = this.laVista.cmpDescripcion.getText();
        acercaDe = acercaDe.replace("<Descripcion>", Info.Descripcion);
        acercaDe = acercaDe.replace("<Detalles>", Info.Detalle );
        acercaDe = acercaDe.replace("<Copyright>", Info.Copyright);
        acercaDe = acercaDe.replace("<Avatar>", Info.Avatar);
        acercaDe = acercaDe.replace("<Mantenedor>", Info.Mantenedor);
        acercaDe = acercaDe.replace("<SitioWeb>", Info.SitioWeb);
        this.laVista.cmpDescripcion.setText(acercaDe);
        
    }
    
    private void mtdVisitarSitioWeb(){
        String url = Info.SitioWeb;

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
