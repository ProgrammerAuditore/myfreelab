package controlador;

import controlador.interfaces.ventana_acciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vista.paneles.panel_conexion;
import vista.paneles.panel_loggin;
import vista.paneles.panel_recuper_cuenta;
import vista.ventanas.Base;

public class LogginController extends VentanaController implements ventana_acciones{
    
    // Atributos o campos
    private panel_loggin mi_panel; // Vista
    
    // Constructuros
    public LogginController() {}
    
    public LogginController(Base vp) {
        this.inicializar(vp);
    }
    
    // Métodos
    private void inicializar(Base vp){
        // Para poder usar abrir y cerrar ventana
        this.setVentanaActiva( vp );
        
        // Para poder controlar los eventos en el panel
        this.mi_panel  = (panel_loggin) vp.getPanelContenedor();
        
        // Establecer propiedades a la ventana
        this.getVentanaActiva().setTitle("Iniciar session");
        
        // Establecer ventana acciones
        this.eventos_de_mouse();
        this.eventos_de_teclado();
    }
        
    @Override
    public void eventos_de_mouse(){
        this.fncRecuperarCuenta();
        this.fncConfigurarConexion();
    }
    
    @Override
    public void eventos_de_teclado() {
    
    }
        
    public void fncRecuperarCuenta(){
        mi_panel.lbl_recuperar_cuenta.addMouseListener( new MouseAdapter() {
            
            @Override
            public void mouseReleased(MouseEvent e) {
                new CuentaResetController(new Base(new panel_recuper_cuenta())).abrir_ventana();
                cerrar_ventana_actual();
            }
            
        });
    }
    
    public void fncConfigurarConexion(){
        mi_panel.btnConfigConexion.addMouseListener( new MouseAdapter() {
            
            @Override
            public void mouseReleased(MouseEvent e) {
                new ConexionController(new Base(new panel_conexion())).abrir_ventana();
                cerrar_ventana_actual();
            }
            
        });
    }
    
}
