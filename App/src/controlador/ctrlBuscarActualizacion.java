package controlador;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class ctrlBuscarActualizacion implements MouseListener {

    // * Vista
    public JDialog modal;
    private PanelActualizacion laVista;

    // * Modelo
    private ObjXml objDocXml;

    public ctrlBuscarActualizacion(PanelActualizacion laVista, ObjXml modelo) {
        this.laVista = laVista;
        this.objDocXml = modelo;

        // * Establecer oyentes
        this.laVista.btnBuscar.addMouseListener(this);

    }

    public void init() {
        mtdEstablecerDatos();

        // * Establecer propiedades para la modal
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Buscar actualizacion");
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

    private void mtdEstablecerDatos() {
        laVista.cmpVersionActual.setText(Info.sVersion + Info.sProduccion);
        laVista.cmpNovedades.setText(Info.Descripcion);
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

        String fileName;
        String OS = System.getProperty("os.name").toLowerCase();
        String time = java.time.LocalDateTime.now().toString();
        time = time.replace(':', '-');
        time = time.replace('.', '-');
        time = time.replaceAll("-", "");

        // Verificar el sistema operativo
        if (OS.indexOf("win") >= 0) {
            fileName = Info.dirTemp;
        } else {
            fileName = Info.dirTemp + "/";
        }

        fileName += "myfreelab-" + time + ".mfl";
        String url = "https://gitlab.com/ProgrammerAuditore/storege-mfl/-/raw/master/MyFreeLab.mfl?inline=false";
        objDocXml.setPath_archivo(fileName);
        //System.out.println("Archivo :: " + fileName);

        if (mtdDescargaURL(url, fileName)) {
            File archivo = new File(fileName);

            if (archivo.exists()) {
                objDocXml.setPath_archivo(fileName);
                HashMap<String, String> doc = objDocXml.mtdMapearUltimaVersion();

                if (doc.get("app_version").equals(Info.sVersion + Info.sProduccion)) {
                    JOptionPane.showMessageDialog(null, "El programa esta en la ultima version");

                } else {
                    
                    // * Establecer informacion de la nueva version
                    laVista.etqVersionActual.setText("Nueva version");
                    laVista.cmpVersionActual.setText(doc.get("app_version"));
                    laVista.cmpNovedades.setText(doc.get("app_novedades"));

                    laVista.cmpVersionActual.setBorder(new LineBorder(Color.green));
                    laVista.cmpNovedades.setBorder(new LineBorder(Color.green));

                    int resp = JOptionPane.showConfirmDialog(null,
                            "Existe una nueva version del programa\n¿Deseas descargarlo e instalarlo?",
                            "Descargar e instalar", JOptionPane.YES_NO_OPTION);

                    if (resp == JOptionPane.YES_OPTION) {
                        
                        // Verificar el sistema operativo
                        if (OS.indexOf("win") >= 0) {
                            System.out.println("Link de descargar :: " + doc.get("app_link_exe"));
                            mtdInstalarActualizacionExe( doc.get("app_link_exe") );
                            
                        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                            System.out.println("Link de descargar :: " + doc.get("app_link_deb"));
                            mtdInstalarActualizacionDeb( doc.get("app_link_deb") );
                            
                        }

                    }

                }

            }
        }
    }

    private void mtdInstalarActualizacionDeb(String url) {
        String fileName;
        String OS = System.getProperty("os.name").toLowerCase();
        String time = java.time.LocalDateTime.now().toString();
        time = time.replace(':', '-');
        time = time.replace('.', '-');
        time = time.replaceAll("-", "");

        fileName = Info.dirTemp + "/" + "myfreelab-" + time + ".deb";
        //System.out.println("Ejecutable deb :: " + fileName);

        if (mtdDescargaURL(url, fileName)) {
            File archivo = new File(fileName);
            if (archivo.exists()) {
                try {
                    String dir = archivo.getCanonicalPath();
                    String cmd = "qapt-deb-installer " + fileName;
                    Runtime run = Runtime.getRuntime();
                    Process pr = run.exec(cmd);
                    System.exit(0);

                } catch (IOException ex) {
                    Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    private void mtdInstalarActualizacionExe(String url) {
        String fileName;
        String OS = System.getProperty("os.name").toLowerCase();
        String time = java.time.LocalDateTime.now().toString();
        time = time.replace(':', '-');
        time = time.replace('.', '-');
        time = time.replaceAll("-", "");

        fileName = Info.dirTemp + "myfreelab-" + time + ".exe";
        //System.out.println("Ejecutable exe :: " + fileName);

        if (mtdDescargaURL(url, fileName)) {
            File archivo = new File(fileName);
            if (archivo.exists()) {
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

    public boolean mtdDescargaURL(String FILE_URL, String FILE_NAME) {
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
            //System.out.println("" + e.getMessage());
        }

        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(ctrlBuscarActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (new File(FILE_NAME).exists());
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
