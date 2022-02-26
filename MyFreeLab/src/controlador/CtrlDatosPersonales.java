package controlador;

import index.MyFreeLab;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlDatosPersonales.mtdInit.titulo"));
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
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
            }
        });
        
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
       
        // * Verificar la restricción de los campos
        if( !mtdValidarCamposDatos() ){
            //if( mtdRtnNombreApellido() && mtdRtnCorreoDireccion() && mtdRtnTMovil() ){
                //if( mtdCapturarDatos()){

                    if( dao.mtdConsultar(dto) == false ){

                        // Registrarlo a la base de datos...
                        ////System.out.println("Registrarlo a la base de datos");
                        if(dao.mtdInsertar(dto)){
                            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                                    .getProperty("ctrlDatosPersonales.mtdEstablecerDatos.msg1"));
                        }

                    } else{

                        // Actualizar los datos personales en la base de datos
                        String[] msg = new String[2];
                        // Titulo
                        msg[0] = MyFreeLab.idioma
                                    .getProperty("ctrlDatosPersonales.mtdEstablecerDatos.msg2");
                        // Pregunta
                        msg[1] = MyFreeLab.idioma
                                    .getProperty("ctrlDatosPersonales.mtdEstablecerDatos.msg3"); 
                        int opc = JOptionPane.showConfirmDialog(laVista, msg[1], msg[0], JOptionPane.YES_NO_OPTION);
                        mtdCapturarDatos();

                        if( opc ==  JOptionPane.YES_OPTION ){
                            if(dao.mtdActualizarDatos(dto))
                                JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                                    .getProperty("ctrlDatosPersonales.mtdEstablecerDatos.msg4"));
                        }

                    }

                //}
            //}
        }
        
    }
    
    private boolean mtdValidarCamposDatos () {
        String gMensaje = "Verifica los siguientes datos: \n\n";
        int gCheck = 0;
        
        // * Validar datos del campo nombres
        if( laVista.cmpNombres.getText().isEmpty() || 
            laVista.cmpNombres.getText().trim().length() >= 30 || 
            !laVista.cmpNombres.isAprobado() ){
            gCheck++;
            laVista.cmpNombres.getEstiloNoAprobado();
            gMensaje += "* El campo nombre es incorrecto. \n";
        }
        
        // * Validar datos del campo apellidos
        if( laVista.cmpApellidos.getText().isEmpty() || 
            laVista.cmpApellidos.getText().trim().length() >= 30 || 
            !laVista.cmpApellidos.isAprobado() ){
            gCheck++;
            laVista.cmpApellidos.getEstiloNoAprobado();
            gMensaje += "* El campo apellido es incorrecto. \n";
        }
        
        // * Validar datos del campo direccion
        if( laVista.cmpDireccion.getText().isEmpty() || 
            laVista.cmpDireccion.getText().trim().length() >= 30 || 
            !laVista.cmpDireccion.isAprobado() ){
            gCheck++;
            laVista.cmpDireccion.getEstiloNoAprobado();
            gMensaje += "* El campo direccion es incorrecto. \n";
        }
        
        // * Validar datos del campo telefono o movil
        if( laVista.cmpTelMovil.getText().isEmpty() || 
            laVista.cmpTelMovil.getText().trim().length() < 10 || 
            laVista.cmpTelMovil.getText().trim().length() > 10 ||
            !laVista.cmpTelMovil.isAprobado() ){
            gCheck++;
            laVista.cmpTelMovil.getEstiloNoAprobado();
            gMensaje += "* El campo telefono o movil es incorrecto. \n";
        }
        
        // * Validar datos del correo
        if( laVista.cmpCorreo.getText().isEmpty() || 
            laVista.cmpCorreo.getText().trim().length() >= 30 || 
            !laVista.cmpCorreo.isAprobado() ){
            gCheck++;
            laVista.cmpCorreo.getEstiloNoAprobado();
            gMensaje += "* El campo correo es incorrecto. \n";
        }
        
         if( gCheck > 0)
            JOptionPane.showMessageDialog(laVista, gMensaje);
        
        return (gCheck > 0);
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
                dto.setCmpNombres(laVista.cmpNombres.getText().trim() );
                dto.setCmpApellidos( laVista.cmpApellidos.getText().trim() );
                dto.setCmpDireccion(laVista.cmpDireccion.getText().trim() );
                dto.setCmpCorreo(laVista.cmpCorreo.getText().trim() );
                dto.setCmpTMovil(laVista.cmpTelMovil.getText().trim() );
            
                return true;
        }else{
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                                .getProperty("ctrlDatosPersonales.mtdCapturarDatos.msg1"));
        }
            
        return false;
    }
    
    /*
    private boolean mtdRtnNombreApellido(){
        boolean gAceptado = true;
        String msg=MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnNombreApellido.msg1");
        
        if( laVista.cmpNombres.getText().trim().length() >= 30 ){
            gAceptado = false;
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnNombreApellido.msg2");
        }
        
        if( laVista.cmpApellidos.getText().trim().length() >= 30 ){
            gAceptado = false;
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnNombreApellido.msg3");
        }
        
        if( !gAceptado ){
            msg = msg + "\n";
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnNombreApellido.msg4");
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnNombreApellido.msg5");
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnNombreApellido.msg6");
            JOptionPane.showMessageDialog(laVista, msg);
        }
        
        return gAceptado;
    }
    
    private boolean mtdRtnCorreoDireccion(){
        boolean gAceptado = true;
        String msg=MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnCorreoDireccion.msg1");
        
        if( laVista.cmpDireccion.getText().trim().length() >= 60 ){
            gAceptado = false;
            msg +=MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnCorreoDireccion.msg2");
        }
        
        if( laVista.cmpCorreo.getText().trim().length() >= 60 ){
            gAceptado = false;
            msg +=MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnCorreoDireccion.msg3");
        }
        
        if( !gAceptado ){
            msg = msg + "\n";
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnCorreoDireccion.msg4");
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnCorreoDireccion.msg5");
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnCorreoDireccion.msg6");
            JOptionPane.showMessageDialog(laVista, msg);
        }
        
        return gAceptado;
    }
    
    private boolean mtdRtnTMovil(){
        boolean gAceptado = true;
        String msg=MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnTMovil.msg1");
        
        if( laVista.cmpTelMovil.getText().trim().length() < 10 || laVista.cmpTelMovil.getText().trim().length() > 10 ){
            gAceptado = false;
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnTMovil.msg2");
        }
        
        if( !gAceptado ){
            msg = msg + "\n";
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnTMovil.msg3");
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnTMovil.msg4");
            msg += MyFreeLab.idioma
                   .getProperty("ctrlDatosPersonales.mtdRtnTMovil.msg5");
            JOptionPane.showMessageDialog(laVista, msg);
        }
        
        return gAceptado;
    }
    */
    

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}


    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
