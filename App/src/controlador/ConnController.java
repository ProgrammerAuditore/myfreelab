package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import vista.paneles.p_conexion;

public class ConnController{
    
    // Atributos o campos
    private p_conexion panel; // Vista
    private ConexionDao modelo; // Modelo
    private ConexionDto conn;
    
    // Constructores
    public ConnController() {
    }

    public ConnController(p_conexion mi_panel) {
        this.panel = mi_panel;
    }
    
    // Métodos
    public void init() {
        // Establecer todo los eventos para Configurar conexión
        panel.btnEstablecerConexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                fncEstablecerConexion();
            }
        });
    }
    
    public void fncCargarDatosConexion(){
        System.out.println("Cargando los datos...");
        ConexionDao conexion = new ConexionDao();
        conn = conexion.obtener_conexion();
        
        panel.cmpUsuario.setText( conn.getUsuario() );
        panel.cmpHost.setText( conn.getHost() );
        panel.cmpDatabase.setText( conn.getDatabase() );
        panel.cmpPuerto.setText( "" + conn.getPuerto() );
        panel.cmpContrasenha.setText( conn.getPass() );
        
        if( conn.getPass().length() == 0 ){
            panel.cmpContrasenha.setEditable(false);
            panel.cmpContrasenha.setEnabled(false);
            panel.cmpNull.setSelected(true);
        }
    }
    
    private void fncEstablecerConexion(){
        String password = String.valueOf( panel.cmpContrasenha.getPassword() );
        
        if( panel.cmpDatabase.isAprobado() && panel.cmpHost.isAprobado() && 
            panel.cmpPuerto.isAprobado() && panel.cmpUsuario.isAprobado()){
            
            int puerto;
            try {
                puerto = Integer.parseInt(panel.cmpPuerto.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Verifica el puerto, por favor.");
                return;
            }
            
            String database = panel.cmpDatabase.getText();
            
            String host = panel.cmpHost.getText();
            String usuario = panel.cmpUsuario.getText();

            if(panel.cmpNull.isSelected()){
                password = "";
            }

            ConexionDto conexion = new ConexionDto(puerto, host, database, usuario, password);
            modelo = new ConexionDao();
            modelo.regitrar_conexion(conexion );
            
            JOptionPane.showMessageDialog(null, "Espera, estableciendo la conexión.");
        }else{
            JOptionPane.showMessageDialog(null, "Verifica los campos, por favor.");
        }
    }
    
    // Getters y Setters
    public p_conexion getPanel() {
        return panel;
    }

    public void setPanel(p_conexion panel) {
        this.panel = panel;
    }

}
