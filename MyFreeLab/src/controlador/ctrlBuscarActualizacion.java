package controlador;

import index.MyFreeLab;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.in;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import modelo.ObjVersionesXml;
import src.Info;
import src.Source;
import vista.paneles.PanelActualizacion;
import vista.ventanas.VentanaPrincipal;

public class ctrlBuscarActualizacion implements MouseListener {

    // * Vista
    public JDialog modal;
    private PanelActualizacion laVista;

    // * Modelo
    private ObjVersionesXml objDocXml;
    
    // * Atributos
    private String msgPrevia;

    public ctrlBuscarActualizacion(PanelActualizacion laVista, ObjVersionesXml modelo) {
        this.laVista = laVista;
        this.objDocXml = modelo;

        // * Establecer oyentes
        this.laVista.btnBuscar.addMouseListener(this);

    }

    public void init() {
        msgPrevia = VentanaPrincipal.etqMensaje.getText();
        mtdEstablecerDatosDelProgramaActual();

        // * Establecer propiedades para la modal
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlBuscarActualizacion.mdInit.titulo"));
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
                //System.out.println("Cerrando PanelActualizacion");
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                Runnable verificar = () -> {
                    
                    try { Thread.sleep(450); } catch (InterruptedException ex) {}
                    mtdBuscarActualizacion();
                    
                };
                
                Thread HiloBuscarActualizacion =  new Thread(verificar);
                HiloBuscarActualizacion.setName("HiloBuscarActualizacion");
                HiloBuscarActualizacion.setPriority(9);
                HiloBuscarActualizacion.run();
                
            }

        });

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getSource() == laVista.btnBuscar) {
            if (laVista.btnBuscar.isEnabled()) {
                mtdBuscarActualizacion();
            }
        }

    }

    private void mtdBuscarActualizacion() {
        
        CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdBuscarActualizacion.msg1"));
        
        String path = Source.dirTemp;
        // Verificar el sistema operativo
        path += Source.OsWin ? "" : "/" ;
        path += "myfreelab-" + Source.timeTmp + ".mfl";
        String url = Info.LinkVersiones;
        File archivo = new File(path);
        objDocXml.setArchivoXml(archivo);
        objDocXml.setPath_archivo(path);
        
        // * Descargar el archivo XML
        if (mtdDescargaURL(url, path)) {

            if (archivo.exists())
                ProcesoDeActualizacion();
            
        }
        
        CtrlPrincipal.mensajeCtrlPrincipal( msgPrevia );
    }
    
    private void ProcesoDeActualizacion(){
        HashMap<String, String> doc = objDocXml.mtdMapearUltimaVersion();

        // * Verificar versiones
        int versionNum = Integer.parseInt( doc.get("app_num_version") );
        if ( versionNum > Integer.parseInt(Info.sVersionNum) ||
            !doc.get("app_name_version").contains(Info.sProduccion) ) {

            // * Actualizar el programa
            CtrlPrincipal.mensajeCtrlPrincipal("Nueva versión encontrada");
            // * Establecer informacion de la nueva version
            laVista.etqVersionActual.setText("Nueva versión");
            laVista.cmpVersionActual.setText(doc.get("app_name_version"));
            laVista.cmpNovedades.setText(doc.get("app_novedades"));

            laVista.cmpVersionActual.setBorder(new LineBorder(Color.green));
            laVista.cmpNovedades.setBorder(new LineBorder(Color.green));

            int resp = JOptionPane.showConfirmDialog(laVista,
                    MyFreeLab.idioma.getProperty("ctrlBuscarActualizacion.ProcesoDeActualizacion.msg1"),
                    MyFreeLab.idioma.getProperty("ctrlBuscarActualizacion.ProcesoDeActualizacion.msg2"), 
                    JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {

                // Verificar el sistema operativo
                CtrlPrincipal.mensajeCtrlPrincipal(
                        MyFreeLab.idioma.getProperty("ctrlBuscarActualizacion.ProcesoDeActualizacion.msg3")+
                        " " + doc.get("app_name_version"));
                if ( Source.OsWin ) {
                    //System.out.println("Link de descargar :: " + doc.get("app_link_exe"));
                    mtdInstalarActualizacionExe( doc.get("app_link_exe") , versionNum );

                } else  {
                    //System.out.println("Link de descargar :: " + doc.get("app_link_deb"));
                    mtdInstalarActualizacionDeb( doc.get("app_link_deb"), versionNum );

                }

            }


        // * Verificar si la version es identico
        } else if( versionNum == Integer.parseInt(Info.sVersionNum)  ){
            JOptionPane.showMessageDialog(laVista, Info.NombreSoftware + " "+
                    MyFreeLab.idioma.getProperty("ctrlBuscarActualizacion.ProcesoDeActualizacion.msg4"));
        }
    }
    
    private boolean mtdInstalarActualizacionExe(String url, int versionNum) {
        CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstalarActualizacionExe.msg1"));
        String fileName;
        fileName = Source.dirTemp + "myfreelab-" + versionNum + ".exe";
        File archivo = new File(fileName);
        //System.out.println("Ejecutable exe :: " + path);

        if( !archivo.exists() ){
            if (mtdDescargaURL(url, fileName)){
                //System.out.println("#Ejecutable descargado : " + fileName );
            }
        }
        
        // * Procede a instalarlo
        // Instalar la version nueva
        if ( archivo.exists()  ) {
            modal.getParent().setVisible(false);
            try {
                String dir = archivo.getCanonicalPath();
                String cmd = "cmd /c start " + fileName + "";
                Runtime run = Runtime.getRuntime();
                Process pr = run.exec(cmd);

            } catch (IOException ex) {
                Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstalarActualizacionExe.msg2"));
            System.exit(0);
            return true;
        } else{
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstalarActualizacionExe.msg3"));
        }
        
        return false;
    }


    private boolean mtdInstalarActualizacionDeb(String url, int versionNum) {
        CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstalarActualizacionDeb.msg1"));
        String fileName;
        fileName = Source.dirTemp + "/" + "myfreelab-" + versionNum + ".deb";
        File archivo = new File(fileName);
        //System.out.println("Ejecutable deb :: " + path);

        if( !archivo.exists() ){
            if (mtdDescargaURL(url, fileName)) {
                //System.out.println("#Paquete descargado : " + fileName );
            }
        }
        
        // * Procede a instalarlo
        // Instalar la version nueva
        if ( archivo.exists()  ) {
            modal.getParent().setVisible(false);
            try {
                // Se procede su instalacion
                String dir = archivo.getCanonicalPath();
                String cmd = mtdInstaladorDeb() + " " + fileName;
                Runtime run = Runtime.getRuntime();
                Process pr = run.exec(cmd);

            } catch (IOException ex) {}

            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstalarActualizacionDeb.msg2"));
            System.exit(0);
            return true;
        } else{
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstalarActualizacionDeb.msg3"));
        }
        
        return false;
    }
    
    public boolean mtdDescargaURL(String FILE_URL, String FILE_NAME) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                //CtrlPrincipal.mensajeCtrlPrincipal("Buscando actualización "+ ((1024/bytesRead)* 100) +"%");
                CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdDescargaURL.msg1"));
            }
        } catch (IOException e) {
            // handle exception
            CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdDescargaURL.msg2"));
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdDescargaURL.msg2"));
            //System.out.println("" + e.getMessage());
        }

        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (new File(FILE_NAME).exists());
    }
    
    private void mtdEstablecerDatosDelProgramaActual(){
        
        // * Cambiar estilo de los campos
        laVista.etqVersionActual.setText(MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdEstablecerDatosDelProgramaActual.msg1"));
        laVista.cmpVersionActual.setBorder(null);
        laVista.cmpNovedades.setBorder(null);
        
        // * Establecer los datos
        laVista.cmpVersionActual.setText(Info.sVersionName + Info.sProduccion);
        laVista.cmpNovedades.setText(Info.Novedades);
        
    }

    private String mtdInstaladorDeb() {
        String instalador="";
        String comandoLinux = "apt list --installed qapt-deb-installer gdebi";
        // * Obtener todo los procesos PID de java
        try (InputStream inputStream = Runtime.getRuntime().exec(comandoLinux).getInputStream();
                Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
            instalador = s.hasNext() ? s.next() : null;
            //System.out.println( result );
        } catch (IOException e) {
            // e.printStackTrace();
        }
        if( comandoLinux.contains("qapt-deb-installer") ){
            return "qapt-deb-installer";
        } else if ( comandoLinux.contains("gdebi") ){        
            return "gdebi";
        } else {
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma.
                getProperty("ctrlBuscarActualizacion.mtdInstaladorDeb.msg1"));
            return "echo";
        }
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


}
