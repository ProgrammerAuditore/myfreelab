package controlador;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.DatosPersonalesDao;
import modelo.dto.DatosPersonalesDto;
import vista.paneles.PanelDatosPersonales;

public class CtrlDatosPersonales implements MouseListener{
    
    // * Vistas
    private PanelDatosPersonales laVista;
    public JDialog modal;
    
    // * Modelos
    private DatosPersonalesDao dao;
    private DatosPersonalesDto dto;

    public CtrlDatosPersonales(PanelDatosPersonales laVista, DatosPersonalesDto dto, DatosPersonalesDao dao) {
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
        
        // * Definir oyentes
        this.laVista.btnAceptar.addMouseListener(this);
        this.laVista.btnCancelar.addMouseListener(this);
        
    }
   
    public void mtdInit() {
        //modal.setModal(true);
        //modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Datos personales");
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        
        // Verificar si hay conexion al servidor de base datos
        if( CtrlHiloConexion.ctrlEstado == true ){
            
            // Extraer los datos personales desde la base de datos
            if( dao.mtdConsultar(dto) ){
                
                // Establecer los datos al laVista o formulario del panel
                mtdEstablecerDatosFrm();
                
            }
            
        }
        
    }
    
    private void mtdEstablecerDatosFrm(){
        
        laVista.cmpNombres.setText(dto.getCmpNombres() );
        laVista.cmpApellidos.setText(dto.getCmpApellidos());
        laVista.cmpDireccion.setText(dto.getCmpDireccion());
        laVista.cmpCorreo.setText(dto.getCmpCorreo());
        laVista.cmpTelMovil.setText(dto.getCmpTMovil());
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnCancelar ){
            modal.setVisible(false);
            modal.dispose();
        }
        
        if( e.getSource() == laVista.btnAceptar ){
            mtdEstablecerDatos();
        }
        
    }
    
    private void mtdEstablecerDatos(){
        ////System.out.println("Establecer datos personales [!DP]");
        
        if( mtdCapturarDatos() ){
            
            if( dao.mtdConsultar(dto) == false ){
                
                // Registrarlo a la base de datos...
                ////System.out.println("Registrarlo a la base de datos");
                dao.mtdInsertar(dto);
            
            } else{
                
                // Actualizar los datos personales en la base de datos
                String[] msg = new String[2];
                // Titulo
                msg[0] = "Modificar datos personales";
                // Pregunta
                msg[1] = "Los datos personales, ya están definidos\n¿Deseas actualizarlo?"; 
                int opc = JOptionPane.showConfirmDialog(null, msg[1], msg[0], JOptionPane.YES_NO_OPTION);
                mtdCapturarDatos();
                
                if( opc ==  JOptionPane.YES_OPTION ){
                    if(dao.mtdActualizarDatos(dto))
                        JOptionPane.showMessageDialog(null, "Datos personales actualizados.");
                }
                
            }
            
        }
        
    }
    
    private boolean mtdCapturarDatos(){
        
        // Verificar si los datos son aprobados o correctos o validos
        if( laVista.cmpApellidos.isAprobado() && 
            laVista.cmpCorreo.isAprobado() &&
            laVista.cmpDireccion.isAprobado() &&
            laVista.cmpNombres.isAprobado() &&
            laVista.cmpTelMovil.isAprobado() ){
            
                // Definir los datos
                dto.setCmpID(1);
                dto.setCmpNombres(laVista.cmpNombres.getText() );
                dto.setCmpApellidos( laVista.cmpApellidos.getText() );
                dto.setCmpDireccion(laVista.cmpDireccion.getText() );
                dto.setCmpCorreo(laVista.cmpCorreo.getText() );
                dto.setCmpTMovil(laVista.cmpTelMovil.getText() );
            
                return true;
        }else{
            JOptionPane.showMessageDialog(null, "Verifica los campos, para datos personales.");
        }
            
        return false;
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
