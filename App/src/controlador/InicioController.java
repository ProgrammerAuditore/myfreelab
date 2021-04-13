package controlador;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.dao.FreelancerDao;
import src.Info;
import src.Source;
import vista.paneles.PanelConexion;
import vista.paneles.PanelInicio;
import vista.ventanas.VentanaInicio;

public class InicioController implements Runnable{
    
    // Atributos o campos
    private PanelInicio pInicio; // Vista
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
        pInicio = (PanelInicio) vInicio.getPanelContenedor();
        fncEstablecerMensaje();
        
        /* Definir evento realeased  */
        // Este método crea un modal para "Configurar conexión"
        fncMenuItemConexion(); 
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
                    pInicio.lbl_msg.setText("Conexión a la base de datos ");
                    pInicio.lbl_accion.setText(freelancers +" querys");
                }
                
            }else{
                
                pInicio.lbl_msg.setText("Sin conexión a la base de datos");
                pInicio.lbl_accion.setText("Configurar");
                
                try {
                    Source.conn.destruir_conexion();
                    Source.conn = null;
                    ctlConexion.fncLookCerrarConexion();
                    System.out.println("TESTING :: cerrando la conexión");
                } catch (Exception e) {}
                
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
                    vInicio.panelConexion = new PanelConexion();
                    
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
            System.out.println("Ejecutando hilo cada 1seg!!!");
            
            fncEstablecerMensaje();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
    }
    
}
