package controlador;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.in;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import modelo.ObjXml;
import src.Info;
import vista.paneles.PanelActualizacion;

public class ctrlBuscarActualizacion implements MouseListener{
    
    // * Vista
    JDialog modal;
    PanelActualizacion laVista;
    
    // * Modelo
    ObjXml objDocXml;

    public ctrlBuscarActualizacion(PanelActualizacion laVista, ObjXml modelo) {
        this.laVista = laVista;
        this.objDocXml = modelo;
        
        // * Establecer oyentes
        this.laVista.btnBuscar.addMouseListener(this);
        
    }
    
    public void init(){
        
        // * Establecer propiedades para la modal
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Buscar actualizacion");
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getPreferredSize() );
        modal.setResizable(false);
        modal.setContentPane(laVista);       
        
        mtdEstablecerDatos();
        
    }
    
    private void mtdEstablecerDatos(){
        laVista.cmpVersionActual.setText( Info.sVersion + Info.sProduccion );
        laVista.cmpNovedades.setText( Info.Descripcion );
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnBuscar )
            if( laVista.btnBuscar.isEnabled() )
                mtdBuscarActualizacion();
        
    }
    
    private void mtdBuscarActualizacion(){
            String time = java.time.LocalDateTime.now().toString();
            time = time.replace(':','-');
            time = time.replace('.','-');
            time = time.replaceAll("-", "");
            
            String fileName = Info.dirTemp + "myfreelab-" + time + ".mfl";
            String url = "https://gitlab.com/ProgrammerAuditore/storege-mfl/-/raw/master/MyFreeLab.mfl?inline=false";
            objDocXml.setPath_archivo(fileName);
            
            if( mtdDescargaURL(url, fileName) ){
                File archivo = new File(fileName);
                /*
                System.out.println("getAbsolutePath :: " + archivo.getAbsolutePath());
                System.out.println("getCanonicalPath :: " + archivo.getCanonicalPath());
                System.out.println("getCanonicalFile().getName() :: " + archivo.getCanonicalFile().getName());
                System.out.println("getPath :: " + archivo.getPath());
                System.out.println("getName :: " + archivo.getName());
                System.out.println("getName : " + archivo.getName().substring( 0, archivo.getName().indexOf('.') ) );
                */
                
                if(archivo.exists()){
                    objDocXml.setPath_archivo(fileName);
                    HashMap<String, String> doc = objDocXml.mtdMapearUltimaVersion();
                   
                    if ( doc.get("app_version").equals( Info.sVersion + Info.sProduccion ) ){
                        JOptionPane.showMessageDialog(null, "El programa esta en la ultima version");
                        
                    } else {
                        laVista.etqVersionActual.setText("Nueva version");
                        laVista.cmpVersionActual.setText( doc.get("app_version") );
                        laVista.cmpNovedades.setText(doc.get("app_novedades"));
                        
                        laVista.cmpVersionActual.setBorder(new LineBorder(Color.green));
                        laVista.cmpNovedades.setBorder(new LineBorder(Color.green));
                        
                        int resp = JOptionPane.showConfirmDialog(null,
                                "Existe una nueva version del programa\n¿Deseas descargarlo e instalarlo?",
                                "Descargar e instalar", JOptionPane.YES_NO_OPTION);
                        
                        if( resp == JOptionPane.YES_OPTION )
                            mtdInstalarActualizacion( doc.get("app_link_descarga") );
                    
                    }
                    
                }
            }
    }
    
    private void mtdInstalarActualizacion(String url){
        String time = java.time.LocalDateTime.now().toString();
            time = time.replace(':','-');
            time = time.replace('.','-');
            time = time.replaceAll("-", "");
            
        String fileName = Info.dirTemp + "myfreelab-" + time + ".exe";
        //System.out.println("Descargar e instalar version nueva");
        
        if( mtdDescargaURL(url, fileName) ){
            File archivo = new File(fileName);
            
            if( archivo.exists() ){
                String OS = System.getProperty("os.name").toLowerCase();
                
                if (OS.indexOf("win") >= 0) {
                    try {
                        String dir = archivo.getCanonicalPath();
                        String cmd = "cmd /c start " + fileName + "";
                        Runtime run = Runtime.getRuntime();
                        Process pr = run.exec(cmd);
                        System.exit(0);

                    } catch (IOException ex) {
                        Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }
        
    }
    
    public boolean mtdDescargaURL(String FILE_URL, String FILE_NAME ) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
            JOptionPane.showMessageDialog(null, "No se pudo acceder al sitio oficial, error 404.");
        }
        
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return (new File(FILE_NAME).exists());
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
