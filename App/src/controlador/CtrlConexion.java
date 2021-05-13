package controlador;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.ConexionDao;
import modelo.dao.MyFreeLabDao;
import modelo.dto.ConexionDto;
import vista.paneles.PanelConexion;

public class CtrlConexion implements MouseListener{
    
    // * Vistas
    private PanelConexion laVista;
    public JDialog modal;
    
    // * Modelos
    private ConexionDto dto;
    private ConexionDao dao;

    public CtrlConexion(PanelConexion laVista, ConexionDto dto, ConexionDao dao) {
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
        
        // * Definir Oyentes
        this.laVista.btnCerrarConexion.addMouseListener(this);
        this.laVista.btnEstablecerConexion.addMouseListener(this);
        
        // * Inicializar
        mtdInit();
    }
    
    private void mtdInit(){
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
            dto = CtrlHiloConexion.ctrlDatos;
            mtdEstablecerDatos();
            estilosConexionAbierto();
        } else {
            estilosConexionCerrada();
            dto = dao.obtener_conexion();
            
            if( dto != null )
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
            CtrlHiloConexion.ctrlDatos = dto;
            if(CtrlHiloConexion.mtdEstablecer()){
                estilosConexionAbierto();
                
                if( !MyFreeLabDao.mtdChecarTablas() ){
                    int opc = JOptionPane.showConfirmDialog(null,"Se detecto que la base de datos\n"
                            + "No cuenta con las tablas necesarios\n"
                            + "¿Deseas crearlos?", "Crear tablas", JOptionPane.YES_OPTION);
                    
                    if( JOptionPane.YES_OPTION == opc ){
                        
                        MyFreeLabDao.mtdCrearTablaDatosPersonales();
                        MyFreeLabDao.mtdCrearTablaProyectos();
                        JOptionPane.showMessageDialog(null, "Creando tablas...");
                    }else{
                        
                        mtdCerrarConexion();
                        JOptionPane.showMessageDialog(null, "Se cerro la conexion, tablas insuficientes.");
                    }
                    
                }
                
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
            
            // Obtener todo los dto validados
            dto.setDatabase( laVista.cmpDatabase.getText() );
            dto.setHost( laVista.cmpHost.getText() );
            dto.setPuerto( laVista.cmpPuerto.getText() );
            dto.setUsuario( laVista.cmpUsuario.getText() );
            
            if( laVista.cmpNull.isSelected() ){
                dto.setPass("");
            }else{
                dto.setPass(Arrays.toString(laVista.cmpContrasenha.getPassword()) );
            }
            
            return true;
        }else{
            System.out.println("Falto informacion");
        }
        
        return false;
    }
    
    private void mtdEstablecerDatos(){
        
        laVista.cmpDatabase.setText(dto.getDatabase() );
        laVista.cmpHost.setText(dto.getHost());
        laVista.cmpPuerto.setText("" + dto.getPuerto() );
        laVista.cmpUsuario.setText(dto.getUsuario() );
        
        String passwd = dto.getPass().trim();
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
