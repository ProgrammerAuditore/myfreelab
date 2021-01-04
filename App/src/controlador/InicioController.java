package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Conexion;
import modelo.dao.FreelancerDao;
import src.Info;
import vista.paneles.p_conexion;
import vista.paneles.panel_inicio;
import vista.ventanas.VentanaInicio;

public class InicioController{
    
    // Atributos o campos
    private panel_inicio pInicio; // Vista
    private VentanaInicio vInicio; // Vista
    
    // Constructuros
    public InicioController() {}
    
    public InicioController(VentanaInicio vp) {
        // Para poder controlar los eventos en el panel
        vInicio = vp;
       
        this.init();
    }
    
    // Métodos
    private void init(){
        // Establecer acciones y propiedades a la ventana
        pInicio = (panel_inicio) vInicio.getPanelContenedor();
        fncEstablecerMensaje();
        fncMenuItemConexion(); // Este método crea un modal para "Configurar conexión"
    }
    
    public void fncAbrirVentana(){
        vInicio.setTitle(Info.NombreSoftware );
        vInicio.setLocationRelativeTo(null);
        vInicio.setEnabled(true);
        vInicio.setVisible(true);
    }
    
    private void fncEstablecerMensaje(){
        Conexion conexion = Conexion.estado_conexion();
        
        // Verificar si tenemos una conexion registrada
        if( conexion.getConn() == null ){
            pInicio.lbl_msg.setText("Sin conexión a la base de datos");
            pInicio.lbl_accion.setText("Configurar");
        }else{
            int freelancers = new FreelancerDao().listar().size();
            
            if( freelancers == 0 ){
                pInicio.lbl_msg.setText("No hay freelancers creados");
                pInicio.lbl_accion.setText("Crear un freelancer");
            }else{
                pInicio.lbl_msg.setText("Espera por los freelancer");
                pInicio.lbl_accion.setText("Por favor...");
            }
        }

    }
    
    private void fncMenuItemConexion(){
        vInicio.menuItem_Conexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Patron de diseño singleton
                if( vInicio.panelConexion == null )
                    vInicio.panelConexion = new p_conexion();
                
                // Habilitar el controlador ConnController
                ConnController ctlConexion = new ConnController();
                ctlConexion.setMi_panel(vInicio.panelConexion);
                ctlConexion.init();
                
                // Crear un nuevo modal para "Configurar conexión"
                vInicio.setModalNuevo(vInicio.panelConexion, "Configurar conexión");
            }
        });
    }
    
}
