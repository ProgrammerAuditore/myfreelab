package controlador;

import java.awt.Component;
import modelo.InterfaceCard;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.FabricarModal;
import modelo.ObjEjecucionXml;
import modelo.dao.ConexionDao;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import modelo.dto.EmpresaDto;
import modelo.dto.ProyectoDto;
import src.Info;
import src.Source;
import vista.componentes.boton.Boton;
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
    private List<EmpresaDto> empresas;
    private List<InterfaceCard> lista;
    private List<Boton> paginacion;
    private int numTotalRegistros;
    private int numMostrarTotalRegistros;
    private int numPaginacionActual;
    private int ctrlPaginacionFin;
    private int ctrlPaginacionInicio;
    private int ctrlPaginacionSeleccion;
    private int ctrlPaginacionSobra;
    private int numMostrarRegistroFin;
    private boolean cargarRegistros;
    
    // * Catcher
    public static boolean estadoModalConfigurarConexion;
    public static boolean cambiosModalGestionarProyectos;
    public static Boolean modificacionesCard;
    public static Integer ctrlBarraEstadoNumProyectos;
    public static Integer ctrlBarraEstadoNumEmpresas;

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
        cargarRegistros = true;
        ctrlPaginacionSobra = 0;
        numTotalRegistros = 0;
        numMostrarTotalRegistros = 10;
        CtrlPrincipal.ctrlBarraEstadoNumEmpresas = 0;
        CtrlPrincipal.ctrlBarraEstadoNumProyectos = 0;
        CtrlPrincipal.modificacionesCard = false;
        paginacion = new ArrayList<>();
        empresas = new ArrayList<>();
        proyectos = new ArrayList<>();
        lista = new ArrayList<>();
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
        laVista.checkProEliminados.addActionListener(this);
        laVista.checkProRealizados.addActionListener(this);
        laVista.checkProEnProceso.addActionListener(this);
        
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
                char letra = e.getKeyChar();
                
                if( Character.isAlphabetic(letra) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE  ){
                    mtdVaciarContenedor();
                    CtrlPrincipal.mensajeCtrlPrincipal("Escribe algo para buscar...");
                    mtdMensaje("Borrar todo para mostrar todos los resultados...");
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                char caracter = e.getKeyChar();
                
                //if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                    
                    if( laVista.cmpBusqueda.getText().isEmpty() ){
                        if( CtrlHiloConexion.ctrlEstado ){
                            mtdFiltrarListas("proyectos", 0, 100);
                        } else {
                            mtdDesHabilitarMenus();
                        }
                    } else if( laVista.cmpBusqueda.getText().length() <= 30){
                        mtdVaciarContenedor();
                        mtdFiltrarBusqueda( laVista.cmpBusqueda.getText() );
                    } else {
                        JOptionPane.showMessageDialog(laVista, "La busqueda debe ser menor o igual a 30 caracteres.");
                    }
                //}
                
            }           
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == laVista.checkProEliminados){
            if(laVista.checkProEliminados.isSelected()){
                mtdFiltrarListas("proyectos eliminados", 0, 0);
                laVista.checkProEliminados.setSelected(true);
            }else{
                mtdFiltrarListas("proyectos", 0, 100);
            }
        }
        
        if(e.getSource() == laVista.checkProRealizados){
            if(laVista.checkProRealizados.isSelected()){
                mtdFiltrarListas("proyectos realizados", 100, 100);
                laVista.checkProRealizados.setSelected(true);
            }else{
                mtdFiltrarListas("proyectos", 0, 100);
            }
        }
        
        if(e.getSource() == laVista.checkProEnProceso){
            if(laVista.checkProEnProceso.isSelected()){
                mtdFiltrarListas("proyectos en proceso", 1, 50);
                laVista.checkProEnProceso.setSelected(true);
            }else{
                mtdFiltrarListas("proyectos", 0, 100);
            }
        }

        if (e.getSource() == laVista.btnSalir) {
            mtdSalirDelPrograma();
        }

        if (e.getSource() == laVista.btnConexion) {
            modalConfigurarConexion();
        }

        if (e.getSource() == laVista.btnGestionarProyectos) {
            fabrica.construir("GestionarProyectos");
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
        proyectos.clear();
        lista.clear();
        mtdCrearPaginacion();
        mtdObtenerListaProyectos();
        mtdObtenerListaEmpresas();
        mtdFiltrarListas("proyectos", 0, 100);
        
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
        lista.clear();
        mtdVaciarContenedor();
        mtdVaciarPaginacion();
        CtrlPrincipal.actualizarBarraEstado();
        
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
         mtdCrearHiloModificaciones();

        // Obtener los datos de la conexion antes de abrir el programa
        if (CtrlHiloConexion.checkConexion()) {
            ////System.out.println("Iniciando el programa con exion establecida.");
            Runnable carga = () -> {
                //mtdObtenerListaProyectos();
                //mtdEstablecerCamposBar();
                //mtdFiltrarListas("proyectos", 0, 100);
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

    private void mtdObtenerListaProyectos() {
        proyectos.clear();
        proyectos = daoP.mtdListar(numMostrarTotalRegistros, numMostrarRegistroFin);
        int tam = proyectos.size();
        String puntos = "";
        
        for (int i = 0; i < tam; i++) {
            ProyectoDto proyecto = proyectos.get(i);
            
            // * Calcular el costo estimado
            double costoEstimado = daoR.mtdObtenerCostoEstimado( proyecto.getCmpID() );
            BigDecimal bd = new BigDecimal(costoEstimado).setScale(2, RoundingMode.HALF_EVEN);
            proyecto.setCmpCostoEstimado( bd.doubleValue() );
            daoP.mtdActualizar(proyecto);
            
            CtrlTarjetaProyectos tarjeta = new CtrlTarjetaProyectos(laVista, proyecto, daoP, fabrica, i);
            lista.add(tarjeta);
            
            // * Mostrar progreso con puntos
            puntos = ( i%4 == 0 ) ? "" : puntos + ".";
            CtrlPrincipal.mensajeCtrlPrincipal("Cargando " + puntos);
            
            //System.out.println("Testin :: Tarjeta agregado #" + itemFila);
            try { Thread.sleep(60); } catch (InterruptedException ex) { }
        }
        
        //laVista.setTitle(Info.NombreSoftware + " - [conexion establecida]");
        CtrlPrincipal.mensajeCtrlPrincipal("conexión establecida");
        CtrlPrincipal.actualizarBarraEstado();
    }
    
    private void mtdObtenerListaEmpresas(){
        /*
        empresas = daoE.mtdListar();
        int tam = empresas.size();
        
        
        */
        CtrlPrincipal.actualizarBarraEstado();
    }
    
    private void mtdCrearPaginacion(){
        mtdVaciarPaginacion();
        numTotalRegistros = 0;
        ctrlPaginacionInicio = 0;
        ctrlPaginacionFin = 0;
        ctrlPaginacionSeleccion = 0;
        
        CtrlPrincipal.ctrlBarraEstadoNumEmpresas = (int) daoE.mtdRowCount();
        numTotalRegistros += CtrlPrincipal.ctrlBarraEstadoNumEmpresas;
        
        CtrlPrincipal.ctrlBarraEstadoNumProyectos = (int) daoP.mtdRowCount();
        numTotalRegistros += CtrlPrincipal.ctrlBarraEstadoNumProyectos;
        
        // Registros 150 <-> 15 botones 
        ctrlPaginacionFin = numTotalRegistros / numMostrarTotalRegistros;
        //ctrlPaginacionFin = 20;
        
        if( (numTotalRegistros%10) > 0 ){
            ctrlPaginacionFin = ctrlPaginacionFin + 1;
        }
        
        
        for (int i = 0; i < ctrlPaginacionFin; i++) {
            
            String numeracion = "" + i;
            Boton btn = new Boton();
            btn.setImgButtonType("peace");
            btn.setTexto(numeracion);
            
            if( i == 0){
                    btn.setTexto("Home");
                    btn.setImgButtonType("primary");
                    numPaginacionActual = i;
                    ctrlPaginacionInicio = 0;
                    numMostrarRegistroFin = (int) (i * numMostrarTotalRegistros);
                    
                    if( (ctrlPaginacionFin-1) == 0 ){
                        VentanaPrincipal.etqPaginacion.setText( null );
                    }else{
                        VentanaPrincipal.etqPaginacion.setText( "Home "+" / Pág. "+(ctrlPaginacionFin-1) );
                    }
                    
            }
            
            if( ctrlPaginacionFin > 11 ){
                if( i == 11 ){
                    btn.setTexto(">>");
                    btn.setImgButtonType("danger");
                }
            }
            
            if( i > 11 ){
                //System.out.println("Pagina "+i+" omitido");
                continue;
            }
            
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    
                    if( btn.getTexto().equals("<<")){
                        ctrlPaginacionSeleccion = numPaginacionActual - 1;
                    }else
                    if( btn.getTexto().equals(">>")){
                        ctrlPaginacionSeleccion = numPaginacionActual + 1;
                    }else
                    if( btn.getTexto().equals("Home")){
                        ctrlPaginacionSeleccion = 0;
                    }else if( btn.getTexto().equals("End") ){
                        ctrlPaginacionSeleccion = ctrlPaginacionFin;
                    }else {
                        ctrlPaginacionSeleccion = Integer.parseInt(btn.getTexto());
                    }

                    if( ctrlPaginacionSeleccion == numPaginacionActual || ctrlPaginacionSeleccion < 0  ){
                            e.consume();
                            return;
                    }
                    
                    proyectos.clear();
                    lista.clear();
                    mtdVaciarContenedor();
                    mtdMensaje("Cargando proyectos... ");
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    synchronized( e ){
                        //System.out.println("Cargar registros: " + cargarRegistros);
                        
                        if( ctrlPaginacionSeleccion == numPaginacionActual || ctrlPaginacionSeleccion < 0  ){
                            e.consume();
                            return;
                        }
                        
                        mtdActualizarPaginacion();
                        numMostrarRegistroFin = (int) (ctrlPaginacionSeleccion * numMostrarTotalRegistros);
                        //System.out.println("Cargando registros = " + numMostrarTotalRegistros +
                        //" : " + numMostrarRegistroFin);

                        Runnable paginacion = () -> {
                            //System.out.println("Paginar: " + ctrlPaginacionSeleccion);
                            if( ctrlPaginacionSeleccion == 0 ){
                                    btn.setTexto("Home");
                                    VentanaPrincipal.etqPaginacion.setText( "Home "+" / Pág. "+(ctrlPaginacionFin-1) );
                            }else{
                                VentanaPrincipal.etqPaginacion.setText( "Pág. "+ctrlPaginacionSeleccion+" / Pág. "+(ctrlPaginacionFin-1) );
                            }
                            
                            if( ctrlPaginacionFin > 11 ){
                                if( ctrlPaginacionSeleccion == ctrlPaginacionFin ){
                                    btn.setTexto("End");
                                    VentanaPrincipal.etqPaginacion.setText( "End "+" / Pág. "+(ctrlPaginacionFin-1) );
                                }
                            }
                            
                            mtdObtenerListaProyectos();
                            mtdObtenerListaEmpresas();
                            mtdFiltrarListas("proyectos", 0, 100);

                            cargarRegistros = true;
                            numPaginacionActual = ctrlPaginacionSeleccion;
                            e.notify();
                        };


                        Thread HiloPaginacion = new Thread(paginacion);
                        HiloPaginacion.setName("HiloPaginacion");
                        HiloPaginacion.setPriority(9);
                        HiloPaginacion.run();
                            
                    }
                }
            });
            
            Dimension tam = new Dimension(50, 25);
            btn.setSize(tam);
            btn.setPreferredSize(tam);
            btn.setLocation(i*45, 0);
            btn.setVisible(true);
            laVista.panelPaginacion.add(btn);
        }
        
        laVista.panelPaginacion.validate();
        laVista.panelPaginacion.revalidate();
        laVista.panelPaginacion.repaint();
        laVista.panelPaginacion.setVisible(true);
        
    }
    
    public void mtdActualizarPaginacion(){
        Component[] botones = laVista.panelPaginacion.getComponents();
        if( ctrlPaginacionSeleccion > 9 ){
            int contador = ctrlPaginacionSeleccion;
            for (int i = botones.length; i > 0; i--) {
                Boton a = (Boton) botones[i-1];
                //System.out.println(a.getTexto());

                if( a.getTexto().equals("End") ){
                    a.setTexto(">>");
                }else if( a.getTexto().equals("Home") ){
                    a.setTexto("<<");
                }

                if(a.getTexto().equals("<<") || a.getTexto().equals(">>") ){
                    continue;
                }

                a.setTexto("" + (contador));
                contador--;
            }
        }
        
        for( Component link : botones ){
            Boton a = (Boton) link;
            if( a.getTexto().equals( ""+ctrlPaginacionSeleccion )){
                a.setImgButtonType("dark");
            }else{
                a.setImgButtonType("peace");
            }
            
            if( a.getTexto().equals("<<") || a.getTexto().equals("Home")  ){
                a.setImgButtonType("primary");
            }

            if( a.getTexto().equals(">>") || a.getTexto().equals("End")  ){
                a.setImgButtonType("danger");
            }            
        }
    
    }
    
    private void mtdFiltrarBusqueda(String busqueda){
        mtdVaciarContenedor();
        int tam = this.lista.size();
        
        for (int i = 0; i < tam; i++) {
            if( lista.get(i).mtdObtenerTituloTarjeta().trim().toLowerCase().contains(busqueda.trim().toLowerCase()) ){
                if( lista.get(i).mtdObtenerTipoTarjeta().equals("PanelCardProyectos") ){
                        laVista.pnlContenedor.add( 
                            lista.get(i).mtdTarjetaDeProyecto(), 
                            lista.get(i).mtdObtenerDimensionesTarjetas()
                        );
                }
            }
        }
        
        int res = laVista.pnlContenedor.getComponentCount();
        if(  res == 0 ){
            mtdMensaje("No hay resultados para `"+ busqueda +"` ...");
        } else{
            CtrlPrincipal.mensajeCtrlPrincipal( res + " resultados para `" + busqueda + "`");
        }
        
        // * Verificar la conexion a la base de datos
        if( CtrlHiloConexion.ctrlEstado == false ){
            CtrlPrincipal.mensajeCtrlPrincipal("Conexión cerrada");
        }else{
            CtrlPrincipal.mensajeCtrlPrincipal("Conexión establecida");
        }
        
        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.revalidate();
        laVista.pnlContenedor.repaint();
    }

    private void mtdFiltrarListas(String msg, int min, int max){
        mtdDeshabilitarFiltros();
        
        // * Verificar la conexion a la base de datos
        if( CtrlHiloConexion.ctrlEstado == false ){
            return;
        }else mtdVaciarContenedor();
            
        Runnable FiltrarListas = () -> {
            try {
                int contador = 0;
                if (lista.size() > 0) {

                    // * Rellenar proyectos
                    int tam = lista.size();

                    for (int i = 0; i < tam; i++) {
                        if( lista.get(i).mtdObtenerTipoTarjeta().equals("PanelCardProyectos") ){
                            if( lista.get(i).mtdObtenerEstadoTarjeta() >= min && lista.get(i).mtdObtenerEstadoTarjeta() <= max  ){
                                laVista.pnlContenedor.add( 
                                    lista.get(i).mtdTarjetaDeProyecto(), 
                                    lista.get(i).mtdObtenerDimensionesTarjetas()
                                );
                                contador++;
                            }
                        }
                    }

                    if(0 >= min && 100 <= max){
                        CtrlPrincipal.mensajeCtrlPrincipal("conexión establecida");
                    }else
                    if( contador == 0 ){
                        mtdMensaje("No hay " + msg + " para mostrar.");
                        CtrlPrincipal.mensajeCtrlPrincipal("conexión establecida");

                    } else if( contador > 0){
                        CtrlPrincipal.mensajeCtrlPrincipal(contador +" "+ msg +" obtenidos");

                    }

                } else {
                    mtdMensaje("No hay proyectos creados.");
                }

                laVista.pnlContenedor.validate();
                laVista.pnlContenedor.revalidate();
                laVista.pnlContenedor.repaint();
            } catch (Exception e) {
                //System.out.println("" + e.getMessage());
            }
        };
        
        Thread HiloFiltrarListas = new Thread(FiltrarListas);
        HiloFiltrarListas.setName("HiloRellenarContenedor");
        HiloFiltrarListas.setPriority(9);
        HiloFiltrarListas.start();
                
    }

    private void mtdVaciarContenedor() { 
        laVista.pnlContenedor.removeAll();
        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.revalidate();
        laVista.pnlContenedor.repaint();
    }
    
    private void mtdVaciarPaginacion(){
        VentanaPrincipal.etqPaginacion.setText(null);
        laVista.panelPaginacion.removeAll();
        laVista.panelPaginacion.validate();
        laVista.panelPaginacion.revalidate();
        laVista.panelPaginacion.repaint();
    }
    
    private void mtdDeshabilitarFiltros(){
        laVista.checkProEliminados.setSelected(false);
        laVista.checkProRealizados.setSelected(false);
        laVista.checkProEnProceso.setSelected(false);
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

    public static void actualizarBarraEstado(){
        if( CtrlHiloConexion.ctrlEstado ){
            VentanaPrincipal.cmpProyectos.setText("Proyectos : " + CtrlPrincipal.ctrlBarraEstadoNumProyectos);
            VentanaPrincipal.cmpEmpresas.setText("Empresas : " + ctrlBarraEstadoNumEmpresas);
        }else{
            VentanaPrincipal.cmpProyectos.setText("Proyectos : #");
            VentanaPrincipal.cmpEmpresas.setText("Empresas : #");
        }
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
    
    private void mtdCrearHiloModificaciones(){
        // * Este hilo monitorea si hay modificaciones en el proyectos
        Runnable watcher = () -> {
            while (true) {                
                synchronized(CtrlPrincipal.modificacionesCard){
                    if(CtrlPrincipal.modificacionesCard){
                        CtrlPrincipal.modificacionesCard = false;
                        lista.clear();
                        mtdCrearPaginacion();
                        mtdObtenerListaProyectos();
                        mtdObtenerListaEmpresas();
                        mtdFiltrarListas("proyectos", 0, 100);
                    }
                }
            }
        };
        
        Thread HiloCambios = new Thread(watcher);
        HiloCambios.setName("HiloCambiosPrincipal");
        HiloCambios.setPriority(9);
        HiloCambios.start();
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
        HiloConexion.start();

    }
    
}
