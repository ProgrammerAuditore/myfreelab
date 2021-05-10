package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.MdlConexion;
import modelo.ObjConexion;
import modelo.dto.ConexionDto;
import vista.paneles.PanelConexion;

public class CtrlConexion implements MouseListener{
    
    private PanelConexion laVista;
    private MdlConexion elModelo;
    public JDialog modal;
    private ConexionDto datos;
    //private ObjConexion conn;
    //private Connection conexion;

    public CtrlConexion(PanelConexion laVista, MdlConexion elModelo) {
        this.laVista = laVista;
        this.elModelo = elModelo;
        
        // * Definir Oyentes
        this.laVista.btnCerrarConexion.addMouseListener(this);
        this.laVista.btnEstablecerConexion.addMouseListener(this);
        
        
        // * Crear el modal
        mtdInit();
    }
    
    private void mtdInit(){
        datos = new ConexionDto();
        modal = new JDialog();
        
        modal.setModal(true);
        modal.setTitle("Configurar conexión");
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setResizable(false);
        modal.setContentPane(laVista);
        
        if( CtrlHiloConexion.ctrlEstado == true  ){
            datos = CtrlHiloConexion.ctrlDatos;
            mtdEstablecerDatos();
            estilosConexionAbierto();
        } else {
            estilosConexionCerrada();
        }
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnCerrarConexion )
            if( laVista.btnCerrarConexion.isEnabled() )
                mtdCerrarConexion();
        
        if( e.getSource() == laVista.btnEstablecerConexion )
            if( laVista.btnEstablecerConexion.isEnabled() )
                mtdEstablecerConexion();
        
    }
    
    private void mtdEstablecerConexion() {
        System.out.println("Establecer conexión");
        
        // Si mtdCapturarDatos() Es Verdadero
        if( mtdCapturarDatos() ){
            CtrlHiloConexion.ctrlDatos = datos;
            if(CtrlHiloConexion.mtdEstablecer()){
                estilosConexionAbierto();
            }
        }
        
        /*
        // Si mtdCapturarDatos() Es Verdadero
        if( mtdCapturarDatos() ){
            try {
                
                // Realizar la conexion
                conn = new ObjConexion(datos);
                conn.mtdEstablecerConexion();
                conexion = conn.getConexion();

                // Si la conexio es valida  
                if(conexion.isValid(1000)){
                    estilosConexionAbierto();
                    CtrlHiloConexion.ctrlDatos = datos;
                    CtrlHiloConexion.mtdEstablecer();
                    
                    // Mostrar mensaje
                    System.out.println("La conexion se establecio exitosamente.");
                }

            } catch (SQLException ex) {
                Logger.getLogger(CtrlConexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassCastException ex) {
                Logger.getLogger(CtrlConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
    }
    
    private void mtdCerrarConexion(){
        System.out.println("Cerrar conexión");
        
        if( CtrlHiloConexion.mtdCerrar() ){
            estilosConexionCerrada();
        }
        
        /*
        // * Verificar si conn y conexion son instancias
        if( conn != null && conexion != null ){
            try {
                
                // Verificar si conexion esta establecida
                if( conexion.isValid(1000) ){
                
                    // Cerrar la conexión
                    conexion.close();
                    conn = null;
                    conexion = null;
                    estilosConexionCerrada();
                    CtrlHiloConexion.mtdCerrar();
                    
                    // Mostrar mensaje
                    System.out.println("La conexion se cerro existamente.");
                
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(CtrlConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
    }
    
    private boolean mtdCapturarDatos(){
        
        // Validar todos los campos del formulario de vista
        if( laVista.cmpHost.isAprobado() && 
            laVista.cmpPuerto.isAprobado() &&
            laVista.cmpUsuario.isAprobado() && 
            laVista.cmpDatabase.isAprobado() ){
            
            // Obtener todo los datos validados
            datos.setDatabase( laVista.cmpDatabase.getText() );
            datos.setHost( laVista.cmpHost.getText() );
            datos.setPuerto( laVista.cmpPuerto.getText() );
            datos.setUsuario( laVista.cmpUsuario.getText() );
            
            if( laVista.cmpNull.isSelected() ){
                datos.setPass("");
            }else{
                datos.setPass( laVista.cmpContrasenha.getPassword().toString() );
            }
            
            return true;
        }else{
            System.out.println("Falto informacion");
        }
        
        return false;
    }
    
    private void mtdEstablecerDatos(){
        
        laVista.cmpDatabase.setText( datos.getDatabase() );
        laVista.cmpHost.setText( datos.getHost());
        laVista.cmpPuerto.setText( datos.getHost());
        laVista.cmpUsuario.setText( datos.getUsuario());
        
        if( datos.getPass().isEmpty() ){
            laVista.cmpNull.setSelected(true);
            laVista.cmpContrasenha.setText("");
        } else{
            laVista.cmpContrasenha.setText( datos.getPass() );
        }
        
    }
    
    private void estilosConexionAbierto(){
        laVista.panelEstado.setBackground(Color.GREEN);
        laVista.btnEstablecerConexion.setEnabled(false);
        
        laVista.btnCerrarConexion.setEnabled(true);
        laVista.cmpContrasenha.setEnabled(false);
        laVista.cmpDatabase.setEnabled(false);
        laVista.cmpUsuario.setEnabled(false);
        laVista.cmpPuerto.setEnabled(false);
        laVista.cmpHost.setEnabled(false);
        laVista.cmpNull.setEnabled(false);
    }
    
    private void estilosConexionCerrada(){
        laVista.panelEstado.setBackground(Color.RED);
        laVista.btnCerrarConexion.setEnabled(false);
        
        laVista.btnEstablecerConexion.setEnabled(true);
        laVista.cmpContrasenha.setEnabled(true);
        laVista.cmpDatabase.setEnabled(true);
        laVista.cmpUsuario.setEnabled(true);
        laVista.cmpPuerto.setEnabled(true);
        laVista.cmpHost.setEnabled(true);
        laVista.cmpNull.setEnabled(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    
}
