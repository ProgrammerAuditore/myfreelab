package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import modelo.dao.ConexionDao;
import modelo.dao.DatosPersonalesDao;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import modelo.dto.ConexionDto;
import modelo.dto.DatosPersonalesDto;
import modelo.dto.EmpresaDto;
import modelo.dto.ProyectoDto;
import modelo.dto.RequisitoDto;
import vista.paneles.PanelConexion;
import vista.paneles.PanelDatos;
import vista.paneles.PanelGestionarEmpresas;
import vista.paneles.PanelGestionarProyectos;
import vista.paneles.PanelGestionarRequisitos;
import vista.ventanas.VentanaPrincipal;

public class CtrlPrincipal implements  ActionListener{
    
    // * Vista
    private VentanaPrincipal laVista;
    
    // * Atributos
    private int TestId;

    public CtrlPrincipal(VentanaPrincipal laVista) {
        this.laVista = laVista;
        
        // * Inicializar
        mtdInit();
    }

    private void mtdInit() {
        
        // * Definir oyentes
        laVista.setLocationRelativeTo(null);
        laVista.bntSalir.addActionListener(this);
        laVista.btnConexion.addActionListener(this);
        laVista.btnDatosPersonales.addActionListener(this);
        laVista.btnGestionarProyectos.addActionListener(this);
        laVista.btnGestionarEmpresas.addActionListener(this);
        laVista.btnGestionarRequisitos.addActionListener(this);
        
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
        
        if( e.getSource() == laVista.bntSalir )
            mtdSalirDelPrograma();
        
        if( e.getSource() == laVista.btnConexion )
            modalConfigurarConexion();
        
        if( e.getSource() == laVista.btnDatosPersonales )
            modalDatosPersonales();
        
        if( e.getSource() == laVista.btnGestionarProyectos )
            modalGestionarProyectos();
        
        if( e.getSource() == laVista.btnGestionarEmpresas )
            modalGestionarEmpresas();
        
        if( e.getSource() == laVista.btnGestionarRequisitos )
            modalGestionarRequisitos();
        
    }
    
    
    private void mtdSalirDelPrograma(){
        // * Método para cerrar el programa
        laVista.setVisible(false);
        mtdCerrandoPrograma();
        laVista.dispose();
        System.exit(0);
        
    }

    private void modalConfigurarConexion() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelConexion vista = new PanelConexion();
        ConexionDto dto = new ConexionDto();
        ConexionDao dao = new ConexionDao();
        CtrlConexion controlador = new CtrlConexion(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo( laVista );
        controlador.modal.setVisible(true);
        
        if( CtrlHiloConexion.ctrlEstado ){
            mtdHabilitarMenus();
            mtdCrearHilo();

        }else
        mtdDesHabilitarMenus();
        
    }
    
    private void modalDatosPersonales() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelDatos vista = new PanelDatos();
        DatosPersonalesDao dao = new DatosPersonalesDao();
        DatosPersonalesDto dto = new DatosPersonalesDto();
        CtrlDatosPersonales controlador = new CtrlDatosPersonales(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo( laVista );
        controlador.modal.setVisible(true);
        
    }
    
    private void modalGestionarProyectos(){
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarProyectos vista = new PanelGestionarProyectos();
        ProyectoDao dao = new ProyectoDao();
        ProyectoDto dto = new ProyectoDto();
        CtrlGestionarProyectos controlador = new CtrlGestionarProyectos( vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo( laVista );
        controlador.modal.setVisible(true);
        
    }
    
    private void modalGestionarRequisitos(){
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarRequisitos vista = new PanelGestionarRequisitos();
        RequisitoDao dao = new RequisitoDao();
        RequisitoDto dto = new RequisitoDto();
        CtrlGestionarRequisitos controlador = new CtrlGestionarRequisitos( vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo( laVista );
        controlador.modal.setVisible(true);
        
    }
    
    private void modalGestionarEmpresas(){
        
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
    
    private void mtdHabilitarMenus(){
        // * Habilitar las opciones de menu del menubar
        laVista.menuEditar.setEnabled(true);
    }
    
    private void mtdDesHabilitarMenus(){
        // * DesHabilitar las opciones de menu del menubar
        laVista.menuEditar.setEnabled(false);
    }
    
    private void mtdAbriendoPrograma() {
        System.out.println("Ventana abierto.");

        // Obtener los datos de la conexion antes de abrir el programa
        if( CtrlHiloConexion.checkConexion() ){
            System.out.println("Iniciando el programa con exion establecida.");
            mtdHabilitarMenus();
            mtdCrearHilo();
        }
        
    }
    
    private void mtdCerrandoPrograma() {
        System.out.println("Finalizo el programa.");

        // Guardar los datos de la conexion antes de cerrar el programa
        if( CtrlHiloConexion.checkConexion() ){
            new ConexionDao().regitrar_conexion( CtrlHiloConexion.ctrlDatos );
            System.out.println("Conexion guardada.");
            
            if(CtrlHiloConexion.mtdCerrar())
                System.out.println("Conexion finalizada.");
        }
        
    }
    
    private void mtdCrearHilo(){
        Runnable watcher = () -> {
            System.out.println("CtrlPrincipal ::: Hilo Creado [!]");
            boolean estado = true;
            
            while( estado ){
                synchronized( CtrlHiloConexion.ctrlHiloC ){
                    
                    if( CtrlHiloConexion.ctrlEstado == false ){
                        mtdDesHabilitarMenus();
                        estado = false;
                    }
                    
                }
            }
            
            System.out.println("CtrlPrincipal ::: Hilo Terminado [!]");
        };
        
        Thread hilo = new Thread(watcher);
        hilo.setDaemon(true);
        hilo.start();
        
    }
    
    private void mtdTesting(String msg){
        System.out.println("ctrlPrincipal ::: " + msg + " ::: id [" + TestId + "]" );
        TestId++;
    }
    
}
