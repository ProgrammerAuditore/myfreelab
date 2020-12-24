package controlador;

import controlador.interfaces.ventana_acciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import vista.paneles.panel_conexion;
import vista.ventanas.Base;

public class ConexionController extends VentanaController implements ventana_acciones{
    
    // Atributos o campos
    private panel_conexion mi_panel; // Vista
    private ConexionDao modelo = new ConexionDao(); // Modelo
    private ConexionDto conn;
    
    // Constructores
    public ConexionController() {}
    
    public ConexionController(Base vp) {
        this.inicializar(vp);
    }
    
    // Métodos
    private void inicializar(Base vp){
        // Para poder usar abrir y cerrar ventana
        this.setVentanaActiva( vp );
        
        // Para poder controlar los eventos en el panel
        this.mi_panel  = (panel_conexion) vp.getPanelContenedor();
        
        // Establecer propiedades modelo la ventana
        this.getVentanaActiva().setTitle("Configurar conexion a la base de datos");
        
        // Establecer ventana acciones
        this.eventos_de_mouse();
        this.eventos_de_teclado();
    }

    @Override
    public void eventos_de_teclado() {
        
    }

    @Override
    public void eventos_de_mouse() {
        this.establecerConexion();
    }
    
    private void establecerConexion(){
        
        mi_panel.btnEstablecerConn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String password = mi_panel.cmpPass.getText();
                
                try {
                    
                    String database = mi_panel.cmpDatabase.getText();
                    String puerto = mi_panel.cmpPuerto.getText();
                    String host = mi_panel.cmpHost.getText();
                    String usuario = mi_panel.cmpUsuario.getText();
                    
                    
                    if( password.length() == 0)
                        password = "";
                
                ConexionDto conexion = new ConexionDto(puerto, host, database, usuario, password);
                modelo.regitrar_conexion(conexion );
                
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "Error los campos son incorrectos...");
                    // x.printStackTrace();
                }
                
                
            }
        });

    }
    
//    public void capturar(){
//        conn =  modelo.obtener_conexion();
//    }
//    
//    public void actualizar_conexion(){
//        conn.setPuerto(1234);
//        modelo.actualizar_conexion(conn);
//    }
//    
//    public void mostrar(){
//        conn =  modelo.obtener_conexion();
//        System.out.println("" + conn.getPuerto());
//    } 
    
}
