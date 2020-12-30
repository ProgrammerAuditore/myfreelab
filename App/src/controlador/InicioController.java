package controlador;

import controlador.interfaces.ventana_acciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Conexion;
import modelo.dao.FreelancerDao;
import vista.paneles.panel_conexion;
import vista.paneles.panel_inicio;
import vista.ventanas.Base;
import vista.ventanas.VentanaInicio;

public class InicioController extends VentanaController implements ventana_acciones{
    
    // Atributos o campos
    private panel_inicio mi_panel; // Vista
    
    // Constructuros
    public InicioController() {}
    
    public InicioController(VentanaInicio vp) {
        this.inicializar(vp);
    }
    
    // Métodos
    private void inicializar(VentanaInicio vp){
        // Para poder usar abrir y cerrar ventana
        this.setVentanaActiva( vp );
        
        // Para poder controlar los eventos en el panel
        this.mi_panel  = (panel_inicio) vp.getPanelContenedor();
        
        // Establecer propiedades a la ventana
        this.fncEstablecerMensaje();
        this.getVentanaActiva().setTitle("MyFreenLab v0.2.0Alpha");
        
        // Establecer ventana acciones
        this.eventos_de_mouse();
        this.eventos_de_teclado();
    }
    
    private void fncEstablecerMensaje(){
        Conexion conexion = Conexion.estado_conexion();
        
        // Verificar si tenemos una conexion registrada
        if( conexion.getConn() == null ){
            this.mi_panel.lbl_msg.setText("Sin conexión a la base de datos");
            this.mi_panel.lbl_accion.setText("Configurar");
        }else{
            int freelancers = new FreelancerDao().listar().size();
            
            if( freelancers == 0 ){
                this.mi_panel.lbl_msg.setText("No hay freelancers creados");
                this.mi_panel.lbl_accion.setText("Crear un freelancer");
            }else{
                this.mi_panel.lbl_msg.setText("Espera por los freelancer");
                this.mi_panel.lbl_accion.setText("Por favor...");
            }
        }

    }
    
    // Eventos
    @Override
    public void eventos_de_teclado() {
        
    }

    @Override
    public void eventos_de_mouse() {
        fncAbrirConfigConexion();
    }
    
    // Métodos para cotrolar la vista
    private void fncAbrirConfigConexion(){
        this.mi_panel.lbl_accion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(mi_panel.lbl_accion.getText().equals("Configurar")){
                    ConexionController vista = new ConexionController(new Base(new panel_conexion()));
                    vista.setVentanaAnterior( getVentanaActiva() );
                    vista.abrir_ventana();

                    cerrar_ventana_actual();
                }
            }
        });
    }
    
}
