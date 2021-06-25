package controlador;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.FabricarModal;
import modelo.ObjEjecucionXml;
import modelo.dao.ConexionDao;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import modelo.dto.ProyectoDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import src.Info;
import src.Source;
import vista.paneles.PanelCard;
import vista.ventanas.VentanaPrincipal;

public class CtrlPrincipal implements ActionListener {

    // * Vista
    private VentanaPrincipal laVista;

    // * Modelos
    private ProyectoDao daoP;
    private EmpresaDao daoE;
    private RequisitoDao daoR;
    private FabricarModal fabrica;

    // * Atributos
    private int TestId;
    private List<ProyectoDto> proyectos;
    private int canBefore;
    private int canAfter;
    private List<PanelCard> tarjetas;
    
    // * Catcher
    public static boolean estadoModalConfigurarConexion;
    public static boolean cambiosModalGestionarProyectos;

    public CtrlPrincipal(VentanaPrincipal laVista, FabricarModal fabrica, ProyectoDao dao, EmpresaDao daoE, RequisitoDao daoR) {
        this.laVista = laVista;
        this.daoP = dao;
        this.daoE = daoE;
        this.daoR = daoR;
        this.fabrica = fabrica;

        // * Definir datos
        this.laVista.setTitle(Info.NombreSoftware);
        this.laVista.setIconImage(Source.imgIconoDefault);

        // * Inicializar
        mtdInit();
    }

    private void mtdInit() {
        tarjetas = new ArrayList<>();
        proyectos = new ArrayList<>();
        laVista.pnlContenedor.setLayout(new GridBagLayout());
        mtdMensaje("Cargando ...");

        // * Definir oyentes
        laVista.setLocationRelativeTo(null);
        laVista.btnSalir.addActionListener(this);
        laVista.btnConexion.addActionListener(this);
        laVista.btnDatosPersonales.addActionListener(this);
        laVista.btnGestionarProyectos.addActionListener(this);
        laVista.btnGestionarEmpresas.addActionListener(this);
        laVista.btnVinculacion.addActionListener(this);
        laVista.btnAcercaDe.addActionListener(this);
        laVista.btnActualizarPrograma.addActionListener(this);
        laVista.btnObtenerAyuda.addActionListener(this);

        laVista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                mtdAbriendoPrograma();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                ObjEjecucionXml archivoRun = new ObjEjecucionXml();
                archivoRun.setPath_archivo( Source.dataRun.getAbsolutePath() );
                archivoRun.mtdGenerarXmlRun();
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                mtdSalirDelPrograma();

            }
        });
        
        laVista.cmpBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char caracter = e.getKeyChar();
                
                if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                    
                    if( laVista.cmpBusqueda.getText().isEmpty() ){
                        if( CtrlHiloConexion.ctrlEstado ){
                            mtdHabilitarMenus();
                        } else {
                            mtdDesHabilitarMenus();
                        }
                    } else
                        mtdPintarFiltroProyectos( laVista.cmpBusqueda.getText() );
                    
                }
            }            
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == laVista.btnSalir) {
            mtdSalirDelPrograma();
        }

        if (e.getSource() == laVista.btnConexion) {
            modalConfigurarConexion();
        }

        if (e.getSource() == laVista.btnGestionarProyectos) {
            modalGestionarProyectos();
        }
        
        if (e.getSource() == laVista.btnDatosPersonales) {
            fabrica.construir("DatosPersonales");
        }

        if (e.getSource() == laVista.btnGestionarEmpresas) {
            fabrica.construir("GestionarEmpresas");
        }

        if (e.getSource() == laVista.btnVinculacion) {
            fabrica.construir("Vinculacion");
        }

        if (e.getSource() == laVista.btnAcercaDe) {
            fabrica.construir("AcercaDe");
        }
        
        if (e.getSource() == laVista.btnActualizarPrograma )
            fabrica.construir("BuscarActualizacion");
        
        if( e.getSource() == laVista.btnObtenerAyuda )
            mtdObtenerAyuda();
 
    }
    
    private void mtdDesHabSubMenus(boolean param){
        //laVista.menuArchivo.setEnabled(param);
        //laVista.menuAyuda.setEnabled(param);
        laVista.menuConfigurar.setEnabled(param);
        laVista.menuEditar.setEnabled(param);
    }

    private void mtdHabilitarMenus() {
        //laVista.setTitle(Info.NombreSoftware + " - [Estableciendo conexion]");
        CtrlPrincipal.mensajeCtrlPrincipal("Estableciendo conexión");
        
        // * Obtener y Crear tarjetas de presentacion para todos los proyecto creados
        mtdRellenarContenedor();
        
        // * Habilitar las opciones de menu del menubar
        mtdDesHabSubMenus(true);
        laVista.menuEditar.setEnabled(true);
        
        //laVista.setTitle(Info.NombreSoftware + " - [conexion establecida]");
        CtrlPrincipal.mensajeCtrlPrincipal("conexión establecida");
    }

    private void mtdDesHabilitarMenus() {
        //laVista.setTitle(Info.NombreSoftware + " - [Cerrando conexion]");
        CtrlPrincipal.mensajeCtrlPrincipal("Cerrando conexión");
        
        // * Vaciar y Borrar tarjetas de presentacion para todos los proyecto creados
        proyectos.clear();
        mtdVaciarContenedor();
        
        // * DesHabilitar las opciones de menu del menubar
        mtdDesHabSubMenus(true);
        laVista.menuEditar.setEnabled(false);
        
        laVista.cmpProyectos.setText("Proyectos : " + "#");
        laVista.cmpEmpresas.setText("Empresas : " + "#");
        mtdMensaje("Sin conexión a la base de datos.");
        
        //laVista.setTitle(Info.NombreSoftware + " - [conexion cerrada]");
        CtrlPrincipal.mensajeCtrlPrincipal("conexión cerrada");
    }

    private void mtdAbriendoPrograma() {
        ////System.out.println("Ventana abierto.");

        // Obtener los datos de la conexion antes de abrir el programa
        if (CtrlHiloConexion.checkConexion()) {
            ////System.out.println("Iniciando el programa con exion establecida.");
            Runnable carga = () -> {
                //mtdRellenarContenedor();
                mtdHabilitarMenus();
            };
            
            //mtdDesHabSubMenus(true);
            mtdCrearHiloDesconexion();
            Thread HiloCargandoProyectos = new Thread(carga);
            HiloCargandoProyectos.setName("HiloDeCarga");
            HiloCargandoProyectos.setPriority(9);
            HiloCargandoProyectos.start();
            
        } else {
            mtdDesHabilitarMenus();
            mtdCrearHiloConexion();
        
        }

    }

    private void mtdCerrandoPrograma() {
        ////System.out.println("Finalizo el programa.");

        // Guardar los datos de la conexion antes de cerrar el programa
        if (CtrlHiloConexion.checkConexion()) {
            new ConexionDao().regitrar_conexion(CtrlHiloConexion.ctrlDatos);
            ////System.out.println("Conexion guardada.");

            if (CtrlHiloConexion.mtdCerrar()) {
                mtdDesHabilitarMenus();
                ////System.out.println("Conexion finalizada.");
            }
        }

    }

    private void mtdSalirDelPrograma() {
        // * Método para cerrar el programa
        laVista.setVisible(false);
        mtdCerrandoPrograma();
        Source.dataRun.getAbsoluteFile().delete();
        laVista.dispose();
        System.exit(0);
        
    }
    
    private void mtdObtenerAyuda(){
        String url = Info.PaginaAyuda;

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

    private void modalConfigurarConexion() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        fabrica.construir("ConfigurarConexion");
        
        if( !estadoModalConfigurarConexion ){
            mtdDesHabSubMenus(false);

            if( CtrlHiloConexion.checkConexion() ){
                //laVista.setTitle(Info.NombreSoftware + " - [Estableciendo conexion, espere por favor...]");
                CtrlPrincipal.mensajeCtrlPrincipal("Estableciendo conexión, espere por favor...");
                mtdMensaje("Estableciendo conexión, espere por favor...");
                
                if( CtrlHiloConexion.ctrlEstado == true ){
                    mtdHabilitarMenus();
                }
                
            } else {
                //laVista.setTitle(Info.NombreSoftware + " - [Cerrando conexion, espere por favor...]");
                CtrlPrincipal.mensajeCtrlPrincipal("Cerrando conexión, espere por favor...");
                mtdMensaje("Cerrando conexión, espere por favor...");
                
                if( CtrlHiloConexion.ctrlEstado == false ){
                    mtdDesHabilitarMenus();
                }
            
            }
        }
        
    }

    private void modalGestionarProyectos() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        fabrica.construir("GestionarProyectos");
        
        if( cambiosModalGestionarProyectos ){
            mtdRellenarContenedor();
        }

    }

    private void modalGestionarRequisitos(ProyectoDto proyecto_dto) {
        
        canBefore = daoR.mtdListar().size();
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        fabrica.setProyecto(proyecto_dto);
        fabrica.construir("GestionarRequisitos");
        
        canAfter = daoR.mtdListar().size();
        
        if( canAfter != canBefore )
        mtdRellenarContenedor();

    }

    private void mtdCrearHiloDesconexion() {
        // * Este hilo monitorea si esta en Desconexion
        
        Runnable watcher = () -> {
            //System.out.println("CtrlPrincipal ::: Hilo mtdCrearHiloDesconexion Creado [!]");
            boolean estado = true;

            while (estado) {
                synchronized (CtrlHiloConexion.ctrlHiloC) {

                    if (CtrlHiloConexion.ctrlEstado == false) {
                        mtdDesHabilitarMenus();
                        mtdCrearHiloConexion();
                        estado = false;
                    }

                }
            }

            //System.out.println("CtrlPrincipal ::: Hilo mtdCrearHiloDesconexion Terminado [!]");
        }; 

        Thread HiloDesconexion = new Thread(watcher);
        HiloDesconexion.setName("HiloDesconexionPrincipal");
        HiloDesconexion.setPriority(9);
        //HiloDesconexion.setDaemon(true);
        HiloDesconexion.start();

    }
    
    private void mtdCrearHiloConexion() {
        // * Este hilo monitorea si esta en Conexion
        
        Runnable watcher = () -> {
            //System.out.println("CtrlPrincipal ::: Hilo mtdCrearHiloConexion Creado [!]");
            boolean estado = true;

            while (estado) {
                synchronized (CtrlHiloConexion.ctrlHiloC) {

                    //System.out.println("CtrlPrincipal ::: mtdCrearHiloConexion Run");
                    if (CtrlHiloConexion.ctrlEstado == true) {
                        //System.out.println("CtrlPrincipal ::: mtdCrearHiloConexion checkConexion");
                        mtdHabilitarMenus();
                        mtdCrearHiloDesconexion();
                        estado = false;
                    }

                }
            }

            //System.out.println("CtrlPrincipal ::: Hilo mtdCrearHiloConexion Terminado [!]");
        };

        Thread HiloConexion = new Thread(watcher);
        HiloConexion.setName("HiloConexionPrincipal");
        HiloConexion.setPriority(9);
        //HiloConexion.setDaemon(true);
        HiloConexion.start();

    }
    
    private void mtdRellenarContenedor() {
        proyectos.clear();
        tarjetas.clear();
        mtdVaciarContenedor();
        proyectos = daoP.mtdListar();
        int tam = proyectos.size();

        //System.out.println("[!] proyectos : " + tam);
        if (tam > 0) {
            mtdPintarProyectos(tam);
        } else {
            mtdMensaje("No hay proyectos creados.");
        }

        mtdEstablecerCamposBar();
        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.revalidate();
        laVista.pnlContenedor.repaint();
        proyectos.clear();
    }

    private void mtdVaciarContenedor() {
        laVista.pnlContenedor.removeAll();
        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.revalidate();
        laVista.pnlContenedor.repaint();
    }

    private void mtdPintarProyectos(int tam) {
        String puntos = "";
        
        for (int i = 0; i < tam; i++) {
            DecimalFormat costoFormato = new DecimalFormat("#.###");
            PanelCard tarjeta_proyecto = new PanelCard();
            ProyectoDto proyecto = proyectos.get(i);
            GridBagConstraints c = new GridBagConstraints();
            tarjeta_proyecto.setVisible(true);
            
            
            // * Calcular el costo estimado
            double costoEstimado = daoR.mtdObtenerCostoEstimado( proyecto.getCmpID() );
            BigDecimal bd = new BigDecimal(costoEstimado).setScale(2, RoundingMode.HALF_EVEN);
            proyecto.setCmpCostoEstimado( bd.doubleValue() );
            daoP.mtdActualizar(proyecto);
            
            // Definir el evento para el boton Modificar
            tarjeta_proyecto.btnModificar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    modalGestionarRequisitos(proyecto);
                }
            });

            // Definir el evento para el boton Eliminar
            tarjeta_proyecto.btnEliminar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    mtdEliminarProyecto(proyecto);
                }
            });
            
            int cantidad = daoR.mtdObtenerCantidadReq( proyecto.getCmpID() );
            //System.out.println("TEST ::"+ proyecto.getCmpNombre() +" - req : " + cantidad );
            // Verificar si es posible cotizar
            if ( cantidad == 0 ) {
                // Deshabilitar el boton de Cotizar
                tarjeta_proyecto.btnCotizar.setEnabled(false);

            } else {
                // Habilitar el boton de Cotizar
                tarjeta_proyecto.btnCotizar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        mtdCotizarProyecto(proyecto);
                    }
                });

            }
            
            // Definir los datos de cada tarjeta de presentación
            tarjeta_proyecto.etqTitulo.setText(proyectos.get(i).getCmpNombre());
            tarjeta_proyecto.cmpFechaInicial.setText(proyectos.get(i).getCmpFechaInicial());
            tarjeta_proyecto.cmpFechaFinal.setText(proyectos.get(i).getCmpFechaFinal());
            tarjeta_proyecto.cmpCostoEstimado.setText("" + proyectos.get(i).getCmpCostoEstimado());
            tarjeta_proyecto.cmpMontoInicial.setText("" + proyectos.get(i).getCmpMontoAdelantado());

            c.gridx = 0; // Columna 
            c.gridy = i; // Fila
            c.gridheight = 1; // Cantidad de columnas a ocupar
            c.gridwidth = 1; // Cantidad de filas a ocupar
            c.weightx = 0.0; // Estirar en ancho
            c.weighty = 0.0;// Estirar en alto
            c.insets = new Insets(30, 0, 30, 0);  //top padding
            c.fill = GridBagConstraints.BOTH; // El modo de estirar
            laVista.pnlContenedor.add(tarjeta_proyecto, c);
            tarjetas.add(tarjeta_proyecto);
            
            // * Mostrar progreso con puntos
            if( i%4 == 0 ){
                //laVista.setTitle(Info.NombreSoftware);
                puntos = "";
            }else{
                puntos += ".";
                //laVista.setTitle(Info.NombreSoftware + " - [Cargando " + puntos  + "]");
                CtrlPrincipal.mensajeCtrlPrincipal("Cargando " + puntos);
            }
            
            //System.out.println("Testin :: Tarjeta agregado #" + itemFila);
            try { Thread.sleep(60); } catch (InterruptedException ex) { }
        }
        
        //laVista.setTitle(Info.NombreSoftware + " - [conexion establecida]");
        CtrlPrincipal.mensajeCtrlPrincipal("conexión establecida");
    }

    private void mtdCotizarProyecto(ProyectoDto proyecto) {
        
        try {
            // Imprimir o mostrar el reporte generado
            JasperPrint jp = mtdGenerarReporte(proyecto);

            if (jp.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(laVista, "Lo siento, el reporte no tiene páginas que mostrar.");

            } else {
                // Mostar el reporte de Cotización
                JasperViewer jviewer = new JasperViewer(jp, false);
                jviewer.setTitle("Cotizar : " + proyecto.getCmpNombre());
                jviewer.setVisible(true);
                //JasperViewer.viewReport(jp);

            }
        } catch (Exception e) {
            // El archivo no existe
            //System.out.println("" + e.getMessage());
            CtrlPrincipal.mensajeCtrlPrincipal("Error al generar la cotización");
        }

    }

    private JasperPrint mtdGenerarReporte(ProyectoDto proyecto) {
        JasperPrint jp = null;

        try {
            String pathReporteCotizacion = new File( Source.docReporte.get("jrxml_file") ).getAbsolutePath();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("SubReportDir", Source.docReporte.get("root_dir"));
            parametros.put("rpCopyright", Info.Copyright );
            parametros.put("rpCostoEstimado", "" + proyecto.getCmpCostoEstimado());
            parametros.put("rpProyectoID", proyecto.getCmpID());
            parametros.put("rpNombreProyecto", proyecto.getCmpNombre());
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

    private void mtdEliminarProyecto(ProyectoDto dto) {

        ProyectoDao pro = new ProyectoDao();

        //System.out.println(" ddfd " + pro.mtdConsultar(dto) );
        if (pro.mtdComprobar(dto)) {
            String[] msg = new String[2];

            msg[0] = "Seguro que deseas eliminar el proyecto `" + dto.getCmpNombre() + "`.";
            msg[1] = "Confirmar";
            int opc = JOptionPane.showConfirmDialog(laVista, msg[0], msg[1], JOptionPane.YES_NO_OPTION);

            if (opc == JOptionPane.YES_OPTION) {
                if (pro.mtdEliminar(dto)) {
                    JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` se eliminó exitosamente.");
                    mtdRellenarContenedor();
                }
            }

        }

    }
    
    private void mtdPintarFiltroProyectos(String nombreProyecto){
        List<PanelCard> tarjetas = mtdFiltroProyectos(nombreProyecto);
        mtdVaciarContenedor();
        int itemFila =0;
        
        if( tarjetas.size() > 0 ){
            
            // * Pintar tarjetas de presentación
            for (PanelCard tarjeta : tarjetas) {

                //System.out.println("Tarjeta : "  + tarjeta.etqTitulo.getText());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0; // Columna 
                c.gridy = itemFila; // Fila
                c.gridheight = 1; // Cantidad de columnas a ocupar
                c.gridwidth = 1; // Cantidad de filas a ocupar
                c.weightx = 0.0; // Estirar en ancho
                c.weighty = 0.0;// Estirar en alto
                c.insets = new Insets(30, 0, 30, 0);  //top padding
                c.fill = GridBagConstraints.BOTH; // El modo de estirar
                laVista.pnlContenedor.add(tarjeta, c);
                itemFila++;
                
            }
            
            CtrlPrincipal.mensajeCtrlPrincipal(tarjetas.size() + " resultados para `" + nombreProyecto + "`");
        } else {
            mtdMensaje("No hay resultados para `" + nombreProyecto + "`");
        }
        
        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.revalidate();
        laVista.pnlContenedor.repaint();   
    }
    
    private List<PanelCard> mtdFiltroProyectos(String busqueda){
        List<PanelCard> tarjetas = new ArrayList<>();
        
        int cantidad = this.tarjetas.size();
        
        for (int i = 0; i < cantidad; i++) {
            String titulo = this.tarjetas.get(i).etqTitulo.getText();
            if( titulo.contains(busqueda) ){
                tarjetas.add( this.tarjetas.get(i) );
            }
        }
        
        return tarjetas;
    }

    
    private void mtdMensaje(String msg) {
        mtdVaciarContenedor();
        JPanel mensaje = new JPanel();
        JLabel titulo = new JLabel();

        titulo.setText(msg);
        titulo.setFont(new Font("Arial ", Font.PLAIN, 18));
        mensaje.add(titulo);
        laVista.pnlContenedor.add(mensaje);

        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.repaint();
    }

    private void mtdEstablecerCamposBar() {
        int empresas_c = daoE.mtdListar().size();
        int proyectos_c = proyectos.size();

        laVista.cmpProyectos.setText("Proyectos : " + proyectos_c);
        laVista.cmpEmpresas.setText("Empresas : " + empresas_c);
    }

    private void mtdTesting(String msg) {
        //System.out.println("ctrlPrincipal ::: " + msg + " ::: id [" + TestId + "]" );
        TestId++;
    }
    
    public static void mensajeCtrlPrincipal(String msg){
        msg = msg.replace("[", "");
        msg = msg.replace("]", "");
        VentanaPrincipal.etqMensaje.setText("["+msg+"]");
    }
    
}
