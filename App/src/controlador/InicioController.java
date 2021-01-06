package controlador;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.dao.FreelancerDao;
import src.Info;
import src.Source;
import vista.paneles.p_conexion;
import vista.paneles.panel_inicio;
import vista.ventanas.VentanaInicio;

public class InicioController implements Runnable{
    
    // Atributos o campos
    private panel_inicio pInicio; // Vista
    private VentanaInicio vInicio; // Vista
    
    // Constructuros
    public InicioController() {}
    
    public InicioController(VentanaInicio vp) {
        // Para poder controlar los eventos en el panel
        vInicio = vp;
        Source.pEjecucion = new Thread(this, "Ejecucion");
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
        Source.pEjecucion.start();
        vInicio.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Source.iconDefault)));
        vInicio.setTitle(Info.NombreSoftware );
        vInicio.setLocationRelativeTo(null);
        vInicio.setEnabled(true);
        vInicio.setVisible(true);
    }
    
    private void fncEstablecerMensaje(){
        
        // Verificar si tenemos una conexion registrada
        try {
            System.out.println("Estado de conexión = " + Source.conn.getConn());
            
            if( Source.conn.getConn().isValid(100) ){
                int freelancers = new FreelancerDao().listar().size();
                System.out.println("Total de freenlancers = " + freelancers);

                if( freelancers == 0 ){
                    pInicio.lbl_msg.setText("No hay freelancers creados");
                    pInicio.lbl_accion.setText("Crear un freelancer");
                }else{
                    pInicio.lbl_msg.setText("Conexión a la base de datos, "+ freelancers +" querys");
                    pInicio.lbl_accion.setText("Espera por los freelancer, por favor...");
                }
            }else{
                pInicio.lbl_msg.setText("Sin conexión a la base de datos");
                pInicio.lbl_accion.setText("Configurar");
                Source.conn.cerrar_conexion();
                System.out.println("TESTING :: cerrando la conexión");
            }
            
            
        } catch (Exception e) {}

    }
    
    private ConexionController ctlConexion = null;
    private void fncMenuItemConexion(){
        vInicio.menuItem_Conexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Patron de diseño singleton
                if( vInicio.panelConexion == null ){
                    vInicio.panelConexion = new p_conexion();
                    
                    // Habilitar el controlador ConexionController
                    ctlConexion = new ConexionController();
                    ctlConexion.setPanel(vInicio.panelConexion);
                    ctlConexion.init();
                    
                }
                
                // Cargar los datos de la conexión
                ctlConexion.fncCargarDatosConexion();
                
                // Crear un nuevo modal para "Configurar conexión"
                vInicio.setModalNuevo(vInicio.panelConexion, "Configurar conexión");
            }
        });
    }

    @Override
    public void run() {
        while (Source.pEjecucion.isAlive()) {            
            System.out.println("Ejecutando hilo cada 3seg!!!");
            fncEstablecerMensaje();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
    }
    
}
