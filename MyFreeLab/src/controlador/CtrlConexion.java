package controlador;

import index.MyFreeLab;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.ConexionDao;
import modelo.dao.MyFreeLabDao;
import modelo.dto.ConexionDto;
import src.Recursos;
import vista.paneles.PanelConexion;

public class CtrlConexion implements MouseListener{
    
    // * Vistas
    private PanelConexion laVista;
    public JDialog modal;
    
    // * Modelos
    private ConexionDto dto;
    private ConexionDao dao;
    
    // * Atributos
    public boolean btnXClickeado;

    public CtrlConexion(PanelConexion laVista, ConexionDto dto, ConexionDao dao) {
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
        
        // * Definir Oyentes
        this.laVista.btnCerrarConexion.addMouseListener(this);
        this.laVista.btnEstablecerConexion.addMouseListener(this);
        
        // * Inicializar
        //mtdInit();
    }
    
    public void mtdInit(){
        //modal = new JDialog();
        CtrlPrincipal.estadoModalConfigurarConexion = false;
        
        //modal.setModal(true);
        //modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlConexion.mtdInit.titulo"));
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
            dto = dao.obtener_datos();
            
            if( dto != null )
                mtdEstablecerDatos();
        }        
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
                CtrlPrincipal.estadoModalConfigurarConexion = true;
            }
        });
        
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
        ////System.out.println("Establecer conexión");
        
        // Si mtdCapturarDatos() Es Verdadero
        if( mtdCapturarDatos() ){
            CtrlHiloConexion.ctrlDatos = dto;
            if(CtrlHiloConexion.mtdEstablecer()){
                if( !MyFreeLabDao.mtdChecarTablas() ){  
                    int opc = JOptionPane.showConfirmDialog(laVista,
                            MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg1") +
                            MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg2") +
                            MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg3"),
                            MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg4"), 
                            JOptionPane.YES_OPTION);
                    
                    if( JOptionPane.YES_OPTION == opc ){
                            // * Creando tablas en la base de datos
                            // El orden es importante...
                            MyFreeLabDao.mtdCrearTablaDatosPersonales();
                            MyFreeLabDao.mtdCrearTablaProyectos();
                            MyFreeLabDao.mtdCrearTablaEmpresas();
                            MyFreeLabDao.mtdCrearTablaRequisitos();
                            MyFreeLabDao.mtdCrearTablaAsociados();
                            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                                    .getProperty("ctrlConexion.mtdEstablecerConexion.msg5"));
                            
                    }else{
                        
                        mtdCerrarConexion();
                        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                                .getProperty("ctrlConexion.mtdEstablecerConexion.msg6"));
                    }
                    
                }
                
                if(CtrlHiloConexion.checkConexion() == true){
                    CtrlPrincipal.modificacionesCard = true;
                    estilosConexionAbierto();
                    modal.setVisible(false);
                    modal.dispose();
                }
                
                
            }else{ 
                // * Error al establecer la conexión
                JOptionPane.showMessageDialog(laVista, 
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg7") +
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg8") +
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg9") +
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg10") +
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg11") +
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg12") +
                MyFreeLab.idioma.getProperty("ctrlConexion.mtdEstablecerConexion.msg13"));
            }
            
        }
        
    }
    
    private void mtdCerrarConexion(){
        ////System.out.println("Cerrar conexión");
        
        if( CtrlHiloConexion.mtdCerrar() ){
            if( CtrlHiloConexion.checkConexion() == false ){
                estilosConexionCerrada();
                modal.setVisible(false);
                modal.dispose();
            }
        }
        
    }
    
    private boolean mtdCapturarDatos(){
        
        // Validar todos los campos del formulario de vista
        if( laVista.cmpHost.isAprobado() && 
            laVista.cmpPuerto.isAprobado() &&
            laVista.cmpUsuario.isAprobado() && 
            laVista.cmpDatabase.isAprobado() ){
            
            // Obtener todo los dto validados
            dto.setDatabase( laVista.cmpDatabase.getText().trim() );
            dto.setHost( laVista.cmpHost.getText().trim() );
            dto.setPuerto( laVista.cmpPuerto.getText().trim() );
            dto.setUsuario( laVista.cmpUsuario.getText().trim() );
            
            if( laVista.cmpNull.isSelected() ){
                dto.setPass("");
            }else{
                dto.setPass(Arrays.toString(laVista.cmpContrasenha.getPassword()) );
            }
            
            return true;
        }else{
            ////System.out.println("Falto informacion");
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
        establecerImg(Recursos.bkgConexionOn);
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
        establecerImg(Recursos.bkgConexionOff);
        laVista.btnCerrarConexion.setEnabled(false);
        
        laVista.btnEstablecerConexion.setEnabled(true);
        laVista.cmpContrasenha.setEnabled(true);
        laVista.cmpDatabase.setEnabled(true);
        laVista.cmpUsuario.setEnabled(true);
        laVista.cmpPuerto.setEnabled(true);
        laVista.cmpHost.setEnabled(true);
        laVista.cmpNull.setEnabled(true);
    }
    
    private void establecerImg(String pathImg){
        laVista.bckgPanel.setImgBackgroundEnabled(true);
        laVista.bckgPanel.setImgBackgroundIn_Ex(true);
        laVista.bckgPanel.setImgRutaInterno( pathImg );
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
