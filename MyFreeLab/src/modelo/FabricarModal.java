package modelo;

import controlador.CtrlAcercaDe;
import controlador.CtrlConexion;
import controlador.CtrlDatosPersonales;
import controlador.CtrlResumen;
import controlador.CtrlGestionarEmpresas;
import controlador.CtrlGestionarProyectos;
import controlador.CtrlGestionarRequisitos;
import controlador.CtrlPreferencias;
import controlador.CtrlVinculacion;
import controlador.ctrlBuscarActualizacion;
import javax.swing.JDialog;
import modelo.dao.ConexionDao;
import modelo.dao.DatosPersonalesDao;
import modelo.dao.EmpresaDao;
import modelo.dao.PreferenciaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.RequisitoDao;
import modelo.dao.VinculacionDao;
import modelo.dto.ConexionDto;
import modelo.dto.DatosPersonalesDto;
import modelo.dto.EmpresaDto;
import modelo.dto.PreferenciaDto;
import modelo.dto.ProyectoDto;
import modelo.dto.RequisitoDto;
import modelo.dto.VinculacionDto;
import vista.paneles.PanelAcercaDe;
import vista.paneles.PanelActualizacion;
import vista.paneles.PanelConexion;
import vista.paneles.PanelPreferencias;
import vista.paneles.PanelDatosPersonales;
import vista.paneles.PanelGestionarEmpresas;
import vista.paneles.PanelGestionarProyectos;
import vista.paneles.PanelGestionarRequisitos;
import vista.paneles.PanelResumen;
import vista.paneles.PanelVinculacion;
import vista.ventanas.VentanaPrincipal;

public class FabricarModal {
    
    private VentanaPrincipal laVista;
    private ProyectoDto proyecto;

    public FabricarModal(VentanaPrincipal laVista) {
        this.laVista = laVista;
    }
    
    public boolean construir(String modal){
        
        switch(modal){
            case "Preferencias" : modalPreferencias(); break;
            case "ConfigurarConexion" : modalConfigurarConexion(); break;
            case "GenerarInforme" : modalGenerarInforme(); break;
            case "DatosPersonales" : modalDatosPersonales(); break;
            case "GestionarProyectos" : modalGestionarProyectos(); break;
            case "GestionarEmpresas" : modalGestionarEmpresas(); break;
            case "GestionarRequisitos" : modalGestionarRequisitos(proyecto); break;
            case "Vinculacion" : modalVinculacion(); break;
            case "AcercaDe" : modalAcercaDe(); break;
            case "BuscarActualizacion" : modalBuscarActualizacion(); break;
        }
        
        return false;
    } 
    
    private void modalPreferencias() {

        // * Crear el modal Preferencias con su respectivo patrón de diseño MVC
        PanelPreferencias vista = new PanelPreferencias();
        PreferenciaDto dto = new PreferenciaDto();
        PreferenciaDao dao = new PreferenciaDao();
        CtrlPreferencias controlador = new CtrlPreferencias(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

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
    
    private void modalGenerarInforme(){
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelResumen vista = new PanelResumen();
        ProyectoDto dto = new ProyectoDto();
        ProyectoDao dao = new ProyectoDao();
        CtrlResumen controlador = new CtrlResumen(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
    }

    private void modalGestionarProyectos() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarProyectos vista = new PanelGestionarProyectos();
        ProyectoDao dao = new ProyectoDao();
        ProyectoDto dto = new ProyectoDto();
        CtrlGestionarProyectos controlador = new CtrlGestionarProyectos(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalGestionarRequisitos(ProyectoDto proyecto_dto) {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarRequisitos vista = new PanelGestionarRequisitos();
        RequisitoDao dao = new RequisitoDao();
        RequisitoDto dto = new RequisitoDto();
        CtrlGestionarRequisitos controlador = new CtrlGestionarRequisitos(vista, proyecto_dto, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

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
    
    private void modalBuscarActualizacion(){
        
        // * Crear el modal Vinculación con su respectivo patrón de diseño MVC
        PanelActualizacion vista = new PanelActualizacion();
        ObjVersionesXml modelo = new ObjVersionesXml();
        ctrlBuscarActualizacion controlador = new ctrlBuscarActualizacion(vista, modelo);
        controlador.modal = new JDialog(laVista);
        controlador.init();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
    }

    public ProyectoDto getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDto proyecto) {
        this.proyecto = proyecto;
    }

}
