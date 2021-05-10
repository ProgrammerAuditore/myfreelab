package controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.DatosPersonalesDao;
import modelo.dto.DatosPersonalesDto;
import vista.paneles.PanelDatos;

public class CtrlDatos implements MouseListener{
    
    private PanelDatos laVista;
    private DatosPersonalesDao dao;
    public JDialog modal;
    private DatosPersonalesDto datos_dto;

    public CtrlDatos(PanelDatos laVista, DatosPersonalesDao dao) {
        this.laVista = laVista;
        this.dao = dao;
        
        // * Definir oyentes
        this.laVista.btnAceptar.addMouseListener(this);
        this.laVista.btnCancelar.addMouseListener(this);
        
        mtdInit();
    }
   
    private void mtdInit() {
        datos_dto = new DatosPersonalesDto();
        modal = new JDialog();
        
        modal.setModal(true);
        modal.setTitle("Datos personales");
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        
        // Verificar si hay conexion al servidor de base datos
        if( CtrlHiloConexion.checkConexion() ){
            
            // Extraer los datos personales desde la base de datos
            if( dao.mtdChecar(datos_dto) ){
                
                // Establecer los datos al laVista o formulario del panel
                mtdEstablecerDatosFrm();
                
            }
            
        }
        
    }
    
    private void mtdEstablecerDatosFrm(){
        
        laVista.cmpNombres.setText( datos_dto.getCmpNombres() );
        laVista.cmpApellidos.setText( datos_dto.getCmpApellidos());
        laVista.cmpDireccion.setText( datos_dto.getCmpDireccion());
        laVista.cmpCorreo.setText( datos_dto.getCmpCorreo());
        laVista.cmpTelMovil.setText( datos_dto.getCmpTMovil());
        
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
        System.out.println("Establecer datos personales [!DP]");
        
        if( mtdCapturarDatos() ){
            
            if( dao.mtdChecar(datos_dto) == false ){
                
                // Registrarlo a la base de datos...
                System.out.println("Registrarlo a la base de datos");
                dao.mtdInsertar(datos_dto);
            
            } else{
                
                // Actualizar los datos personales en la base de datos
                String[] msg = new String[2];
                // Titulo
                msg[0] = "Modificar datos personales";
                // Pregunta
                msg[1] = "Los datos personales, ya están definidos, ¿Deseas actualizarlo?"; 
                int opc = JOptionPane.showConfirmDialog(null, msg[0], msg[1], JOptionPane.YES_NO_OPTION);
                
                if( opc ==  JOptionPane.YES_OPTION ){
                    if(dao.mtdActualizarDatos(datos_dto))
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
                datos_dto.setCmpID(1);
                datos_dto.setCmpNombres(laVista.cmpNombres.getText() );
                datos_dto.setCmpApellidos( laVista.cmpApellidos.getText() );
                datos_dto.setCmpDireccion(laVista.cmpDireccion.getText() );
                datos_dto.setCmpCorreo(laVista.cmpCorreo.getText() );
                datos_dto.setCmpTMovil(laVista.cmpTelMovil.getText() );
            
                return true;
        }else{
            System.out.println("Los datos personales no son validos.");
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