package controlador;

import index.MyFreeLab;
import modelo.InterfaceCard;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
import src.Recursos;
import vista.paneles.PanelCardProyectos;
import vista.ventanas.VentanaPrincipal;

public class CtrlTarjetaProyectos extends InterfaceCard {
    
    // * Modelo
    ProyectoDto dto;
    ProyectoDao dao;
    FabricarModal fabrica;
    
    // * Vistas
    PanelCardProyectos tarjeta;
    
    // * Atributo
    GridBagConstraints tarjeta_dimensiones;
    private VentanaPrincipal laVista;
    Integer item;
    
    // * Evenetos
    MouseListener eventoCotizar;
    MouseListener eventoModificar;
    
    public CtrlTarjetaProyectos(
            VentanaPrincipal laVista,
            ProyectoDto dto, 
            ProyectoDao dao, 
            FabricarModal fabrica, 
            Integer item ) {
        
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
        this.fabrica = fabrica;
        this.item = item;
        this.tarjeta = new PanelCardProyectos();
        this.tarjeta_dimensiones = new GridBagConstraints();
        
        mtdInit();
    }
    
    private void mtdInit(){
        tituloTarjeta = dto.getCmpNombre();
        tipoTarjeta = "PanelCardProyectos";
        estadoTarjeta = dto.getCmpCtrlEstado();
        
        mtdEstablecerDatos();
        mtdCrearEventos();
        mtdEstablecerDimensiones();
        mtdEstablecerTipoDeTarjeta();
    }
    
    private void mtdEstablecerDatos(){
        // Definir los datos de cada tarjeta de presentación
        tarjeta.etqTitulo.setText(dto.getCmpNombre());
        tarjeta.cmpFechaInicial.setText(dto.getCmpFechaInicial());
        tarjeta.cmpFechaInicial.setToolTipText(null);
        
        tarjeta.cmpFechaFinal.setText(dto.getCmpFechaFinal());
        tarjeta.cmpFechaFinal.setToolTipText(null);
        
        tarjeta.cmpCostoEstimado.setText("" + dto.getCmpCostoEstimado());
        tarjeta.cmpCostoEstimado.setToolTipText(null);
        
        tarjeta.cmpMontoInicial.setText("" + dto.getCmpMontoAdelantado());
        tarjeta.cmpMontoInicial.setToolTipText(null);
        mtdCalcularProgresoDelProyecto();
    }
    
    private void mtdCrearEventos(){
        eventoCotizar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCotizarProyecto();
            }
        };
        
        eventoModificar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdModificarProyecto();
            }
        };
        
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
        tarjeta.btnCotizar.removeMouseListener(eventoCotizar);
        tarjeta.btnModificar.removeMouseListener(eventoModificar);
        
        // * Establecer el estilo de diseño de la tarjeta
        if( gctrlEstado == 0 ){
            tarjeta.mtdCardEliminadoProyecto();
            
        } else if( gctrlEstado == 100 ){
            tarjeta.mtdCardRealizadoProyecto();
            
        } else {
            tarjeta.mtdCardActivoProyecto();
            
        }
        
        // * Establecer los eventos
        if( dto.getCmpCtrlEstado() > 0 ){

            // * Verificar el costo estimado
            if( dto.getCmpCostoEstimado() == 0 ){
                tarjeta.btnCotizar.setEnabled(false);
            }else{
                // Definir el evento para el boton Cotizar
                tarjeta.btnCotizar.setEnabled(true);
                tarjeta.btnCotizar.addMouseListener(eventoCotizar);
                
            }
            
            if( dto.getCmpCtrlEstado() == 1 || dto.getCmpCtrlEstado() == 50 ){

                // Definir el evento para el boton Modificar
                tarjeta.btnModificar.addMouseListener(eventoModificar);
            
            }

        }
        
    }
    
    private void mtdCotizarProyecto() {
        
        try {
            // Imprimir o mostrar el reporte generado
            JasperPrint jp = mtdGenerarReporte();

            if (jp.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                        .getProperty("ctrlTarjetaProyecto.mtdCotizarProyecto.msg1"));

            } else {
                // Mostar el reporte de Cotización
                JasperViewer jviewer = new JasperViewer(jp, false);
                jviewer.setTitle(MyFreeLab.idioma
                        .getProperty("ctrlTarjetaProyecto.mtdCotizarProyecto.msg2")
                        +" : " + dto.getCmpNombre());
                jviewer.setVisible(true);
                //JasperViewer.viewReport(jp);

            }
        } catch (Exception e) {
            // El archivo no existe
            //System.out.println("" + e.getMessage());
            CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma
                        .getProperty("ctrlTarjetaProyecto.mtdCotizarProyecto.msg3"));
        }

    }
     
    private JasperPrint mtdGenerarReporte() {
        JasperPrint jp = null;

        try {
            String pathReporteCotizacion = new File( Recursos.docCotizacionJasper().get("jrxml_file") ).getAbsolutePath();

            Map<String, Object> parametros = new HashMap<String, Object>();
            // * Atributos
            parametros.put("SubReportDir", Recursos.docCotizacionJasper().get("root_dir"));
            parametros.put("rpCostoEstimado", "" + dto.getCmpCostoEstimado());
            parametros.put("rpNombreProyecto", dto.getCmpNombre());
            parametros.put("rpProyectoID", dto.getCmpID());
            
            // * Información del software
            parametros.put("rpNombreSoftware", Info.NombreSoftware);
            parametros.put("rpCopyright", Info.Copyright );
            parametros.put("rpEtqDerechosReservados", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqDerechosReservados"));
            
            // Introducción
            parametros.put("rpTitulo", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpTitulo"));
            parametros.put("rpSeccion1", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpSeccion1")
                    .replaceAll("<Proyecto>", dto.getCmpNombre() ));
            parametros.put("rpSeccion2", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpSeccion2"));
            
            // Etiquetas
            parametros.put("rpEtqClientes", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqClientes"));
            parametros.put("rpEtqDatosPersonales", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqDatosPersonales"));
            parametros.put("rpEtqRequisitoID", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqRequisitoID"));
            parametros.put("rpEtqNombreRequisito", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqNombreRequisito"));
            parametros.put("rpEtqCosto", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqCosto"));
            parametros.put("rpEtqCostoTotal", MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.rpEtqCostoTotal"));
            
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
            dto.setCmpActualizadoEn( fncObtenerFechaYHoraActual() );
            if(dao.mtdActualizar(dto)){

                // * Actualizar el costo estimado en la tarjeta
                tarjeta.cmpCostoEstimado.setText( "" + dto.getCmpCostoEstimado() );
                mtdEstablecerTipoDeTarjeta();
                
            }
        }
        
    }
    
    private String mtdFormatearFechas(String fecha, boolean bd){
        if(bd){
            // 01/12/2022
            String dia = fecha.substring(0, 2);
            String mes = fecha.substring(3, 5);
            String anho =  fecha.substring(6, 10);
            String horario = "00:00:00";
            return dia+"-"+mes+"-"+anho+" "+horario;
        }else{
            // 2021/07/07 22:36:39
            String dia = fecha.substring(8, 10);
            String mes = fecha.substring(5, 7);
            String anho =  fecha.substring(0, 4);
            String horario = "00:00:00";
            return dia+"-"+mes+"-"+anho+" "+horario;
        }
    }
    
    private long obtenerDiferenciasEnDias(String fechaInicial, String fechaFinal){
        long difference_In_Days = 0;
        SimpleDateFormat sdf
            = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");
        
        try {
            Date d1 = sdf.parse(fechaInicial);
            Date d2 = sdf.parse(fechaFinal);
            
            // * Calcular el tiempo en milisegundos
            long difference_In_Time
                = d2.getTime() - d1.getTime();
            
            //System.out.println("" + d1);
            //System.out.println("" + d2);
            //System.out.println(fechaInicial + " <-> " + fechaFinal + " ::: " + difference_In_Time );
            
            // * Calculadndo la diferencia en dias 
            difference_In_Days
                = TimeUnit
                      .MILLISECONDS
                      .toDays(difference_In_Time)
                  % 365;
            
        } catch (ParseException e) {
            //System.out.println("Mensaje de Error \n****\n" + e.getMessage()+"***\n");
        }
        
        return difference_In_Days;
    }
    
    private boolean mtdCalcularProgresoDelProyecto(){
        
        String fechaInicial = mtdFormatearFechas(dto.getCmpFechaInicial(), true);
        String fechaFinal = mtdFormatearFechas(dto.getCmpFechaFinal(), true);
        String fechaActual = mtdFormatearFechas(fncObtenerFechaYHoraActual(), false);
        //String fechaPrueba = mtdFormatearFechas("05/08/2021", true);
        
        try {
            //System.out.println(dto.getCmpNombre());
            //System.out.println("Fecha formateado:: " + fechaInicial);
            //System.out.println("Fecha formateado:: " + fechaFinal);
            //System.out.println("Fecha formateado:: " + Recursos.fechayHoraActual);
            long diasTotal = obtenerDiferenciasEnDias(fechaInicial, fechaFinal);
            //System.out.println("Dias Total: " + diasTotal);
            long diasTranscurrido = obtenerDiferenciasEnDias(fechaInicial, fechaActual);
            //System.out.println("Dias Transcurrido: " + obtenerDiferenciasEnDias(fechaInicial, fechaActual));
            long porcentaje = (diasTranscurrido * 100) / diasTotal;
            //System.out.println("Porcentaje del proyecto: " + ( (diasTranscurrido*100) / diasTotal ) );
            tarjeta.pgrProyecto.setValue((int) porcentaje);
            
            return true;
        } catch (Exception e) {}
        
        return false;
    }
    
    private String fncObtenerFechaYHoraActual(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    
    public PanelCardProyectos getTarjeta() {
        return tarjeta;
    }

    public GridBagConstraints getTarjeta_dimensiones() {
        return tarjeta_dimensiones;
    }
    
    @Override
    public String mtdObtenerTituloTarjeta() {
        return tituloTarjeta;
    }

    @Override
    public String mtdObtenerTipoTarjeta() {
        return tipoTarjeta;
    }

    @Override
    public Integer mtdObtenerEstadoTarjeta() {
        return estadoTarjeta;
    }
    
    @Override
    public GridBagConstraints mtdObtenerDimensionesTarjetas() {
        return tarjeta_dimensiones;
    }

    @Override
    public PanelCardProyectos mtdTarjetaDeProyecto() {
        return tarjeta;
    }
    
}
