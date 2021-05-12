package controlador;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.MdlConexion;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import vista.paneles.PanelConexion;

public class CtrlConexion implements MouseListener{
    
    private PanelConexion laVista;
    private MdlConexion elModelo;
    public JDialog modal;
    private ConexionDto datos;

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
        
        //modal.setModal(true);
        modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
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
            datos = new ConexionDao().obtener_conexion();
            
            if( datos != null )
                mtdEstablecerDatos();
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
            }else 
                JOptionPane.showMessageDialog(null, "Conexion no establecida\n"
                + "Por favor, verifique los datos y el servidor en estado de ejecución.");
        }
        
    }
    
    private void mtdCerrarConexion(){
        System.out.println("Cerrar conexión");
        
        if( CtrlHiloConexion.mtdCerrar() ){
            estilosConexionCerrada();
        }
        
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
        laVista.cmpPuerto.setText( "" + datos.getPuerto() );
        laVista.cmpUsuario.setText( datos.getUsuario() );
        
        String passwd = datos.getPass().trim();
        if( passwd.isEmpty() || passwd.length() == 0  ){
            laVista.cmpNull.setSelected(true);
            laVista.cmpContrasenha.setText(null);
            laVista.cmpContrasenha.aceptarCampo();
            laVista.cmpContrasenha.setEditable(false);
            laVista.cmpContrasenha.setEnabled(false);
            laVista.cmpContrasenha.setFocusable(false);
        } else{
            laVista.cmpContrasenha.setText( passwd );
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
