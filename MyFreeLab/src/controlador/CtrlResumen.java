package controlador;

import index.MyFreeLab;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.ProyectoDao;
import modelo.dto.ProyectoDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import src.Info;
import vista.paneles.PanelResumen;
import vista.ventanas.VentanaPrincipal;

public class CtrlResumen implements MouseListener{
    
    // * Vista
    PanelResumen laVista;
    public JDialog modal;
    
    // * Modelo
    ProyectoDao dao; 
    ProyectoDto dto;
    
    // * Atributos

    public CtrlResumen(PanelResumen laVista, ProyectoDto dto, ProyectoDao dao ) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
        
        this.laVista.btnAceptar.addMouseListener(this);
        
    }
    
    public void mtdInit(){
        
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(MyFreeLab.idioma.getProperty("panelInforme.mtdInit.titulo"));
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        
        // Verificar si hay conexion al servidor de base datos
        if( CtrlHiloConexion.ctrlEstado == true ){
            mtdEstablecerDatos();
        }
        
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
        
        if( e.getSource() == laVista.btnAceptar ){
            modal.setVisible(false);
            modal.dispose();
        }
        
    }
    
    /*
    private void mtdGenerarInforme(){
        String msg = VentanaPrincipal.etqMensaje.getText();
        CtrlPrincipal.mensajeCtrlPrincipal("Generando informe");
        
        try {
            // Imprimir o mostrar el reporte generado
            JasperPrint jp = mtdCargarJasperReports();

            if (jp.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                        .getProperty("ctrlTarjetaProyecto.mtdCotizarProyecto.msg1"));

            } else {
                
                // Cerrar el modal
                modal.setVisible(false);
                modal.dispose();
                
                // Mostar el reporte de Cotización
                JasperViewer jviewer = new JasperViewer(jp, false);
                jviewer.setTitle("GGG");
                jviewer.setVisible(true);
                //JasperViewer.viewReport(jp);

            }
        } catch (Exception e) {
            // El archivo no existe
            //System.out.println("" + e.getMessage());
            CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma
                        .getProperty("ctrlTarjetaProyecto.mtdCotizarProyecto.msg3"));
        }
        
        CtrlPrincipal.mensajeCtrlPrincipal(msg);
    }
    
    private JasperPrint mtdCargarJasperReports(){
        JasperPrint jp = null;
        String path="/home/Windows10/Documents/NetBeansProjects/Proyectos/netbeans-freelancer-software/MyFreeLab/shared/";

        try {
            String pathReporteCotizacion = new File( path + "ReporteOrg.jrxml" ).getAbsolutePath();
            
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("SubReportDir", path);
            parametros.put("rpCopyright", Info.Copyright );
            parametros.put("rpTitulo", Info.NombreSoftware);

            JasperReport jr = JasperCompileManager.compileReport(pathReporteCotizacion);

            jp = JasperFillManager.fillReport(jr, parametros, CtrlHiloConexion.getConexion());

        } catch (JRException ex) {
            // Error en la base de datos
            //Logger.getLogger(CtrlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma
                        .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.msg1"));
        }

        return jp;
    }   
    */
    
    private void mtdEstablecerDatos(){
        
        // Tab 1
        laVista.cmpProEnProceso.setText("" + dao.mtdRowCount(1));
        laVista.cmpProFinalizados.setText("" + dao.mtdRowCount(100));
        laVista.cmpProEliminados.setText("" + dao.mtdRowCount(0));
        laVista.cmpProTotales.setText("" + dao.mtdRowCount());
        
        BigDecimal letMontoEsperado = dao.mtdSumarCostoEstimado(0,100);
        BigDecimal letMontoEnPerdidas = dao.mtdSumarCostoEstimado(0,0);
        BigDecimal letMontoLibres = dao.mtdSumarCostoEstimado(100,100);
        double monto = letMontoLibres.doubleValue() 
                + (letMontoEsperado.doubleValue() - letMontoEnPerdidas.doubleValue());
        BigDecimal letMontoObtenido = new BigDecimal(monto).setScale(2, RoundingMode.HALF_EVEN);
        
        // Tab 2
        laVista.cmpMontoEsperado.setText("" + letMontoEsperado.doubleValue());
        laVista.cmpMontoPerdidas.setText("" + letMontoEnPerdidas.doubleValue());
        laVista.cmpMontoLibre.setText("" + letMontoLibres.doubleValue());
        laVista.cmpMontoObtenido.setText("" + letMontoObtenido );
    
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
