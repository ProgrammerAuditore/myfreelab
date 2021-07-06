package controlador;

import controlador.interfaces.InterfaceCard;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.FabricarModal;
import modelo.dao.ProyectoDao;
import modelo.dto.ProyectoDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import src.Info;
import src.Source;
import vista.paneles.PanelCardProyectos;

public class CtrlTarjetaProyectos extends InterfaceCard {
    
    // * Modelo
    ProyectoDto dto;
    ProyectoDao dao;
    FabricarModal fabrica;
    
    // * Vistas
    PanelCardProyectos tarjeta;
    
    // * Atributo
    GridBagConstraints tarjeta_dimensiones;
    Integer item;
    
    public CtrlTarjetaProyectos(ProyectoDto dto, ProyectoDao dao, FabricarModal fabrica, Integer item) {
        this.dto = dto;
        this.dao = dao;
        this.fabrica = fabrica;
        this.item = item;
        this.tarjeta = new PanelCardProyectos();
        this.tarjeta_dimensiones = new GridBagConstraints();
        
        mtdInit();
    }
    
    private void mtdInit(){
        mtdEstablecerDatos();
        mtdEstablecerDimensiones();
        mtdEstablecerTipoDeTarjeta();
    }
    
    private void mtdEstablecerDatos(){
        // Definir los datos de cada tarjeta de presentación
        tarjeta.etqTitulo.setText(dto.getCmpNombre());
        tarjeta.cmpFechaInicial.setText(dto.getCmpFechaInicial());
        tarjeta.cmpFechaFinal.setText(dto.getCmpFechaFinal());
        tarjeta.cmpCostoEstimado.setText("" + dto.getCmpCostoEstimado());
        tarjeta.cmpMontoInicial.setText("" + dto.getCmpMontoAdelantado());
    }
    
    private void mtdEstablecerDimensiones(){
        tarjeta_dimensiones.gridx = 0; // Columna 
        tarjeta_dimensiones.gridy = item; // Fila
        tarjeta_dimensiones.gridheight = 1; // Cantidad de columnas a ocupar
        tarjeta_dimensiones.gridwidth = 1; // Cantidad de filas a ocupar
        tarjeta_dimensiones.weightx = 0.0; // Estirar en ancho
        tarjeta_dimensiones.weighty = 0.0;// Estirar en alto
        tarjeta_dimensiones.insets = new Insets(30, 0, 30, 0);  //top padding
        tarjeta_dimensiones.fill = GridBagConstraints.BOTH; // El modo de estirar
        tarjeta.setVisible(true);
    }
    
    private void mtdEstablecerTipoDeTarjeta(){
        int gctrlEstado = dto.getCmpCtrlEstado();
        
        // * Establecer el estilo de diseño de la tarjeta
        if( gctrlEstado == 0 ){
            tarjeta.mtdCardRecuperarProyecto();
            
        } else if( gctrlEstado == 100 ){
            tarjeta.mtdCardRealizadoProyecto();
            
        } else {
            tarjeta.mtdCardActivoProyecto();
            
        }
        
        // * Establecer los eventos
        if( dto.getCmpCtrlEstado() > 0 ){

            // Definir el evento para el boton Eliminar
            tarjeta.btnEliminar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    mtdEliminarProyecto();
                }
            });
            
            // Definir el evento para el boton Cotizar
            if( dto.getCmpCostoEstimado() == 0 ){
                tarjeta.btnCotizar.setEnabled(false);
            }else{
                tarjeta.btnCotizar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        mtdCotizarProyecto();
                    }
                });
            }
            
            if( dto.getCmpCtrlEstado() == 1 || dto.getCmpCtrlEstado() == 50 ){

                // Definir el evento para el boton Modificar
                tarjeta.btnModificar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        mtdModificarProyecto();
                    }
                });
            
            }

        }else{
            
            // Definir el evento para el boton Recuperar
            tarjeta.btnEliminar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    mtdRecuperarProyecto();
                }
            });
            
            // Definir el evento para el boton Remover
            tarjeta.btnEliminar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    mtdRemoverProyecto();
                }
            });
        
        }
        
    }
    
    private void mtdEliminarProyecto(){
        ProyectoDao pro = new ProyectoDao();

        //System.out.println(" ddfd " + pro.mtdConsultar(dto) );
        if (pro.mtdComprobar(dto)) {
            String[] msg = new String[2];
            dto.setCmpCtrlEstado(0);
            dto.setCmpActualizadoEn(Source.fechayHora);

            msg[0] = "Seguro que deseas eliminar el proyecto `" + dto.getCmpNombre() + "`.";
            msg[1] = "Confirmar";
            int opc = JOptionPane.showConfirmDialog(null, msg[0], msg[1], JOptionPane.YES_NO_OPTION);

            if (opc == JOptionPane.YES_OPTION) {
                if (pro.mtdEliminar(dto)) {
                    JOptionPane.showMessageDialog(null, "El proyecto `" + dto.getCmpNombre() + "` se eliminó exitosamente.");
                    CtrlPrincipal.modificacionesCard = true;
                }
            }

        }
    }
    
    private void mtdCotizarProyecto() {
        
        try {
            // Imprimir o mostrar el reporte generado
            JasperPrint jp = mtdGenerarReporte();

            if (jp.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lo siento, el reporte no tiene páginas que mostrar.");

            } else {
                // Mostar el reporte de Cotización
                JasperViewer jviewer = new JasperViewer(jp, false);
                jviewer.setTitle("Cotizar : " + dto.getCmpNombre());
                jviewer.setVisible(true);
                //JasperViewer.viewReport(jp);

            }
        } catch (Exception e) {
            // El archivo no existe
            //System.out.println("" + e.getMessage());
            CtrlPrincipal.mensajeCtrlPrincipal("Error al generar la cotización");
        }

    }
     
    private JasperPrint mtdGenerarReporte() {
        JasperPrint jp = null;

        try {
            String pathReporteCotizacion = new File( Source.docReporte.get("jrxml_file") ).getAbsolutePath();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("SubReportDir", Source.docReporte.get("root_dir"));
            parametros.put("rpCopyright", Info.Copyright );
            parametros.put("rpCostoEstimado", "" + dto.getCmpCostoEstimado());
            parametros.put("rpProyectoID", dto.getCmpID());
            parametros.put("rpNombreProyecto", dto.getCmpNombre());
            parametros.put("rpTitulo", Info.NombreSoftware);

            JasperReport jr = JasperCompileManager.compileReport(pathReporteCotizacion);

            jp = JasperFillManager.fillReport(jr, parametros, CtrlHiloConexion.getConexion());

        } catch (JRException ex) {
            // Error en la base de datos
            //Logger.getLogger(CtrlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            CtrlPrincipal.mensajeCtrlPrincipal("Error al generar la cotización");
        }

        return jp;
    }
    
    private void mtdModificarProyecto(){
        
        BigDecimal montoActual = new BigDecimal( dto.getCmpCostoEstimado() );
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        fabrica.setProyecto(dto);
        fabrica.construir("GestionarRequisitos");
        
        // * Verificar si el costo estimado cambia
        BigDecimal montoDespues = new BigDecimal( CtrlGestionarRequisitos.proyectoMonto.doubleValue() );
        if( montoDespues != montoActual ){
            
            // * Actualizar el costo estimado en la base de datos
            dto.setCmpCostoEstimado( montoDespues.doubleValue() );
            dto.setCmpActualizadoEn( Source.fechayHora );
            dao.mtdActualizar(dto);
            
            // * Actualizar el costo estimado en la tarjeta
            tarjeta.cmpCostoEstimado.setText( "" + dto.getCmpCostoEstimado() );
        
        }
        
    }
    
    private void mtdRecuperarProyecto(){}
    private void mtdRemoverProyecto(){}

    public PanelCardProyectos getTarjeta() {
        return tarjeta;
    }

    public GridBagConstraints getTarjeta_dimensiones() {
        return tarjeta_dimensiones;
    }
    
    @Override
    protected String mtdObtenerTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String mtdObtenerTitulo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
