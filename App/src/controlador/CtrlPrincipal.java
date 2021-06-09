package controlador;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.dao.ConexionDao;
import modelo.dao.DatosPersonalesDao;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import modelo.dao.VinculacionDao;
import modelo.dto.ConexionDto;
import modelo.dto.DatosPersonalesDto;
import modelo.dto.EmpresaDto;
import modelo.dto.ProyectoDto;
import modelo.dto.RequisitoDto;
import modelo.dto.VinculacionDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import src.Info;
import src.Source;
import vista.paneles.PanelAcercaDe;
import vista.paneles.PanelCard;
import vista.paneles.PanelConexion;
import vista.paneles.PanelDatosPersonales;
import vista.paneles.PanelGestionarEmpresas;
import vista.paneles.PanelGestionarProyectos;
import vista.paneles.PanelGestionarRequisitos;
import vista.paneles.PanelVinculacion;
import vista.ventanas.VentanaPrincipal;

public class CtrlPrincipal implements ActionListener {

    // * Vista
    private VentanaPrincipal laVista;

    // * Modelos
    private ProyectoDao dao;
    private EmpresaDao daoE;
    private RequisitoDao daoR;

    // * Atributos
    private int TestId;
    private List<ProyectoDto> proyectos;
    private int canBefore;
    private int canAfter;

    public CtrlPrincipal(VentanaPrincipal laVista, ProyectoDao dao, EmpresaDao daoE, RequisitoDao daoR) {
        this.laVista = laVista;
        this.dao = dao;
        this.daoE = daoE;
        this.daoR = daoR;

        // * Definir datos
        this.laVista.setTitle(Info.NombreSoftware);
        this.laVista.setIconImage(Source.imgIconoDefault);

        // * Inicializar
        mtdInit();
    }

    private void mtdInit() {
        proyectos = new ArrayList<>();
        laVista.pnlContenedor.setLayout(new GridBagLayout());
        mtdMensaje("Cargando ...");

        // * Definir oyentes
        laVista.setLocationRelativeTo(null);
        laVista.bntSalir.addActionListener(this);
        laVista.btnConexion.addActionListener(this);
        laVista.btnDatosPersonales.addActionListener(this);
        laVista.btnGestionarProyectos.addActionListener(this);
        laVista.btnGestionarEmpresas.addActionListener(this);
        laVista.btnVinculacion.addActionListener(this);
        laVista.btnAcercaDe.addActionListener(this);

        laVista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                mtdAbriendoPrograma();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                mtdCerrandoPrograma();

            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == laVista.bntSalir) {
            mtdSalirDelPrograma();
        }

        if (e.getSource() == laVista.btnConexion) {
            modalConfigurarConexion();
        }

        if (e.getSource() == laVista.btnDatosPersonales) {
            modalDatosPersonales();
        }

        if (e.getSource() == laVista.btnGestionarProyectos) {
            modalGestionarProyectos();
        }

        if (e.getSource() == laVista.btnGestionarEmpresas) {
            modalGestionarEmpresas();
        }

        if (e.getSource() == laVista.btnVinculacion) {
            modalVinculacion();
        }

        if (e.getSource() == laVista.btnAcercaDe) {
            modalAcercaDe();
        }

    }

    private void mtdHabilitarMenus() {
        // * Habilitar las opciones de menu del menubar
        laVista.menuEditar.setEnabled(true);
        mtdRellenarContenedor();
    }

    private void mtdDesHabilitarMenus() {
        // * DesHabilitar las opciones de menu del menubar
        laVista.menuEditar.setEnabled(false);
        mtdVaciarProyectos();
        laVista.cmpProyectos.setText("Proyectos : " + "#");
        laVista.cmpEmpresas.setText("Empresas : " + "#");
        mtdMensaje("Sin conexión a la base de datos.");
    }

    private void mtdAbriendoPrograma() {
        ////System.out.println("Ventana abierto.");

        // Obtener los datos de la conexion antes de abrir el programa
        if (CtrlHiloConexion.checkConexion()) {
            ////System.out.println("Iniciando el programa con exion establecida.");
            mtdHabilitarMenus();
            mtdCrearHiloDesconexion();
            
        } else {
            mtdDesHabilitarMenus();
        
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
        laVista.dispose();
        //System.exit(0);

    }

    private void modalConfigurarConexion() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelConexion vista = new PanelConexion();
        ConexionDto dto = new ConexionDto();
        ConexionDao dao = new ConexionDao();
        CtrlConexion controlador = new CtrlConexion(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
        
        /*
        if (CtrlHiloConexion.ctrlEstado) {
            mtdCrearHiloDesconexion();
            //if( !laVista.menuEditar.isEnabled() )
                //mtdHabilitarMenus();
        } else {
            mtdDesHabilitarMenus();
        }
        */

    }

    private void modalDatosPersonales() {

        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelDatosPersonales vista = new PanelDatosPersonales();
        DatosPersonalesDao dao = new DatosPersonalesDao();
        DatosPersonalesDto dto = new DatosPersonalesDto();
        CtrlDatosPersonales controlador = new CtrlDatosPersonales(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalGestionarProyectos() {
        
        canBefore = dao.mtdListar().size();
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarProyectos vista = new PanelGestionarProyectos();
        ProyectoDao dao = new ProyectoDao();
        ProyectoDto dto = new ProyectoDto();
        CtrlGestionarProyectos controlador = new CtrlGestionarProyectos(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
        canAfter = dao.mtdListar().size();
        
        if( canAfter != canBefore  )
        mtdRellenarContenedor();

    }

    private void modalGestionarRequisitos(ProyectoDto proyecto_dto) {
        
        canBefore = daoR.mtdListar().size();
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarRequisitos vista = new PanelGestionarRequisitos();
        RequisitoDao dao = new RequisitoDao();
        RequisitoDto dto = new RequisitoDto();
        CtrlGestionarRequisitos controlador = new CtrlGestionarRequisitos(vista, proyecto_dto, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
        canAfter = daoR.mtdListar().size();
        
        if( canAfter != canBefore )
        mtdRellenarContenedor();

    }

    private void modalGestionarEmpresas() {

        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarEmpresas vista = new PanelGestionarEmpresas();
        EmpresaDao dao = new EmpresaDao();
        EmpresaDto dto = new EmpresaDto();
        CtrlGestionarEmpresas controlador = new CtrlGestionarEmpresas(vista, dao, dto);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalVinculacion() {

        // * Crear el modal Vinculación con su respectivo patrón de diseño MVC
        PanelVinculacion vista = new PanelVinculacion();
        EmpresaDao empresa_dao = new EmpresaDao();
        ProyectoDao proyecto_dao = new ProyectoDao();
        VinculacionDao vinculacion_dao = new VinculacionDao();
        VinculacionDto vinculacion_dto = new VinculacionDto();
        CtrlVinculacion controlador = new CtrlVinculacion(vista, proyecto_dao, empresa_dao, vinculacion_dao, vinculacion_dto);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalAcercaDe() {

        // * Crear el modal Vinculación con su respectivo patrón de diseño MVC
        PanelAcercaDe vista = new PanelAcercaDe();
        CtrlAcercaDe controlador = new CtrlAcercaDe(vista);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void mtdCrearHiloDesconexion() {
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

        Thread hilo = new Thread(watcher);
        hilo.setDaemon(true);
        hilo.start();

    }
    
    private void mtdCrearHiloConexion() {
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

        Thread hilo = new Thread(watcher);
        hilo.setDaemon(true);
        hilo.start();

    }
    
    private void mtdRellenarContenedor() {
        mtdVaciarProyectos();
        proyectos = dao.mtdListar();
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

    private void mtdVaciarProyectos() {
        proyectos.clear();
        laVista.pnlContenedor.removeAll();
        laVista.pnlContenedor.validate();
        laVista.pnlContenedor.revalidate();
        laVista.pnlContenedor.repaint();
    }

    private void mtdPintarProyectos(int tam) {

        for (int i = 0; i < tam; i++) {
            PanelCard tarjeta_proyecto = new PanelCard();
            ProyectoDto proyecto = proyectos.get(i);
            GridBagConstraints c = new GridBagConstraints();
            tarjeta_proyecto.setVisible(true);
            
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
            
            int cantidad = daoR.mtdCantidadReq( proyecto.getCmpID() );
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
            
            //System.out.println("Testin :: Tarjeta agregado #" + i);
        }
    }

    private void mtdCotizarProyecto(ProyectoDto proyecto) {

        // Imprimir o mostrar el reporte generado
        JasperPrint jp = mtdGenerarReporte(proyecto);

        if (jp.getPages().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lo siento, el reporte no tiene paginas que mostrar.");

        } else {
            // Mostar el reporte de Cotización
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setTitle("Cotizar : " + proyecto.getCmpNombre());
            jviewer.setVisible(true);
            //JasperViewer.viewReport(jp);

        }

    }

    private JasperPrint mtdGenerarReporte(ProyectoDto proyecto) {
        JasperPrint jp = null;

        try {
            String pathReporteCotizacion = getClass().getResource("../" + "storage/reporte/CotizacionOrg.jrxml").getFile();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("rpProyectoID", proyecto.getCmpID());
            parametros.put("rpNombreProyecto", proyecto.getCmpNombre());
            parametros.put("rpTitulo", Info.NombreSoftware);

            JasperReport jr = JasperCompileManager.compileReport(pathReporteCotizacion);

            jp = JasperFillManager.fillReport(jr, parametros, CtrlHiloConexion.getConexion());

        } catch (JRException ex) {
            Logger.getLogger(CtrlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
            int opc = JOptionPane.showConfirmDialog(null, msg[0], msg[1], JOptionPane.YES_NO_OPTION);

            if (opc == JOptionPane.YES_OPTION) {
                if (pro.mtdEliminar(dto)) {
                    JOptionPane.showMessageDialog(null, "El proyecto `" + dto.getCmpNombre() + "` se elimino exitosamente.");
                    mtdRellenarContenedor();
                }
            }

        }

    }

    private void mtdMensaje(String msg) {
        JPanel mensaje = new JPanel();
        JLabel titulo = new JLabel();

        titulo.setText(msg);
        titulo.setFont(new Font("Arial ", Font.PLAIN, 24));
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

}
