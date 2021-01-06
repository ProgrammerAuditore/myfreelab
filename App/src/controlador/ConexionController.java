package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import src.Source;
import vista.paneles.PanelConexion;

public class ConexionController{
    
    // Atributos o campos
    private PanelConexion panel; // Vista
    private ConexionDao modelo; // Modelo
    private ConexionDto conn;
    
    // Constructores
    public ConexionController() {
    }

    public ConexionController(PanelConexion mi_panel) {
        this.panel = mi_panel;
    }
    
    // Métodos
    public void init() {
        // Establecer todo los eventos para Configurar conexión
        panel.btnEstablecerConexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if( panel.btnEstablecerConexion.isEnabled()  )
                    fncEstablecerConexion();
            }
        });
    }
    
    public void fncCargarDatosConexion(){
        //System.out.println("Cargando los datos...");
        try {
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
            
            if( Source.conn.getConn() != null )
                Source.conn = Conexion.estado_conexion();
            
            fncEstadoConexion();
        } catch (Exception e) {
            System.out.println("El archivo db.dat no tiene propiedades o no existe.");
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
            
            Source.conn = Conexion.estado_conexion();
            fncEstadoConexion();
            
             if( Source.conn.getConn() == null )
                 JOptionPane.showMessageDialog(null, "Conexión no establecido.");
            
            
        }else{
            JOptionPane.showMessageDialog(null, "Verifica los campos, por favor.");
        }
    }
    
    private void fncEstadoConexion(){
        if( Source.conn.getConn() == null ){
            System.out.println("conn null = " + Source.conn.getConn());
            Source.conn.cerrar_conexion();
            panel.panel_estado.setBackground(Color.red);
            panel.btnEstablecerConexion.setEnabled(true);
        }else{
            System.out.println("conn abierto = " + Source.conn.getConn());
            Source.conn.cerrar_conexion();
            panel.panel_estado.setBackground(Color.green);
            panel.btnEstablecerConexion.addMouseListener(null);
            panel.btnEstablecerConexion.setEnabled(false);
        }
    }
    
    // Getters y Setters
    public PanelConexion getPanel() {
        return panel;
    }

    public void setPanel(PanelConexion panel) {
        this.panel = panel;
    }

}
