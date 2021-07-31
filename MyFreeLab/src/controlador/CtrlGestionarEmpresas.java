package controlador;

import index.MyFreeLab;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.dao.EmpresaDao;
import modelo.dto.EmpresaDto;
import vista.paneles.PanelGestionarEmpresas;

public class CtrlGestionarEmpresas implements MouseListener {
    
    // * Vistas
    private PanelGestionarEmpresas laVista;
    public JDialog modal;
    
    // * Modelos
    private EmpresaDao dao;
    private EmpresaDto dto;
    
    // Atributos
    private String cmpEmpresa;
    private DefaultTableModel modeloTabla;
    private List<EmpresaDto> empresas;

    public CtrlGestionarEmpresas(PanelGestionarEmpresas laVista, EmpresaDao dao, EmpresaDto dto) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
        
        // * Crear objetos
        modeloTabla = new DefaultTableModel();
        empresas =  new ArrayList<>();
        
        // * Definir oyentes
        this.laVista.btnBuscar.addMouseListener(this);
        this.laVista.btnCrear.addMouseListener(this);
        this.laVista.btnRemover.addMouseListener(this);
        this.laVista.btnModificar.addMouseListener(this);
        
        // * Definir modelo para la tabla
        modeloTabla = (DefaultTableModel) this.laVista.tblEmpresas.getModel();
        this.laVista.tblEmpresas.getTableHeader().setReorderingAllowed(false);
        this.laVista.tblEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.laVista.tblEmpresas.setModel(modeloTabla);
        
        // * Inicializar
        //mtdInit();
    }

    public void mtdInit() {
        //modal = new JDialog();
        
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlGestionarEmpresa.mtdInit.titulo"));
        //modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
        if( CtrlHiloConexion.ctrlEstado == true ){
            mtdRellenarTabla();
        }
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
            }
        });
    }
    
    private void mtdRellenarTabla() {
        mtdVaciarTabla();
        empresas = dao.mtdListar();
        
        if( empresas.size() > 0 )
            mtdAgregarEmpresas();
    }
    
    private void mtdVaciarTabla(){
        while( modeloTabla.getRowCount() > 0 ){
            modeloTabla.removeRow(0);
        }
    }

    private void mtdAgregarEmpresas() {
        int tam = empresas.size();
        
        for (int i = 0; i < tam; i++)
            mtdAgregarEmpresa();
    }

    private void mtdAgregarEmpresa() {
        int fila = modeloTabla.getRowCount();
        modeloTabla.setNumRows(fila + 1);
        
        modeloTabla.setValueAt( empresas.get(fila).getCmpID(), fila, 0);
        modeloTabla.setValueAt( empresas.get(fila).getCmpNombre() , fila, 1);
        modeloTabla.setValueAt( empresas.get(fila).getCmpDireccion(), fila, 2);
        modeloTabla.setValueAt( empresas.get(fila).getCmpCorreo(), fila, 3);
        modeloTabla.setValueAt( empresas.get(fila).getCmpTMovil(), fila, 4);
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnBuscar )
            mtdBuscarEmpresa();
        
        if( e.getSource() == laVista.btnCrear )
            mtdCrearEmpresa();
        
        if( e.getSource() == laVista.btnModificar )
            mtdModificarEmpresa();
        
        if( e.getSource() == laVista.btnRemover )
            mtdRemoverEmpresa();
        
    }
    
    private void mtdBuscarEmpresa(){
        
        if( mtdValidarCampo() ){
            ////System.out.println("CtrlGestionarEmpresas - Buscar empresa [!]");
            int tam = modeloTabla.getRowCount();
            boolean resultado = false;
            
            for (int i = 0; i < tam; i++) {
                String valor = String.valueOf( modeloTabla.getValueAt(i, 0) );
                
                if( valor.equals(cmpEmpresa ) || modeloTabla.getValueAt(i, 1).equals( cmpEmpresa )){
                    laVista.tblEmpresas.setRowSelectionInterval(i, i);
                    resultado = true;
                }
            }
            
            if( !resultado )
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdBuscarEmpresa.msg1")
                    .replace("<Empresa>", cmpEmpresa)
            );
            
        }
        
    }
    
    private void mtdCrearEmpresa(){
        
        if( mtdValidarCampo() ){
            ////System.out.println("CtrlGestionarEmpresas - Crear empresa [!]");
            dto.setCmpNombre( cmpEmpresa );
            
            if( !dao.mtdComprobar(dto) ){
                
                if( dao.mtdInsetar(dto) ){
                    // * Notificar al controlador principal
                    //CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    CtrlPrincipal.ctrlBarraEstadoNumEmpresas =  empresas.size();
                    CtrlPrincipal.actualizarBarraEstado();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdCrearEmpresa.msg1")
                    .replace("<Empresa>", dto.getCmpNombre())
                    );
                }
                
            }else 
            JOptionPane.showMessageDialog(laVista,  MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdCrearEmpresa.msg2")
                    .replace("<Empresa>", dto.getCmpNombre())
            );
            
        }
        
    }
    
    private void mtdModificarEmpresa(){
        int seleccionado = laVista.tblEmpresas.getSelectedRow();
        
        if( seleccionado >= 0 ){
            ////System.out.println("CtrlGestionarEmpresas - Modificar empresa [!]");
            dto = mtdObtenerEmpresa(seleccionado);
            
            if( !mtdValidarDatos(dto) ){
                return;
            }
            
            String[] msg = new String[2];
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdModificarEmpresa.msg1") + " | " + dto.getCmpNombre();
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdModificarEmpresa.msg2");
            int opc = JOptionPane.showConfirmDialog(laVista, msg[0], msg[1], JOptionPane.YES_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdActualizar(dto) ){
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdModificarEmpresa.msg3")
                    .replace("<Empresa>", dto.getCmpNombre())
                    );
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdModificarEmpresa.msg4"));
        
    }
    
    private void mtdRemoverEmpresa(){
        int seleccionado = laVista.tblEmpresas.getSelectedRow();
        
        if( seleccionado >= 0 ){
            ////System.out.println("CtrlGestionarEmpresas - Eliminar empresa [!]");
            String[] msg = new String[2];
            dto = mtdObtenerEmpresa(seleccionado);
            
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdRemoverEmpresa.msg1") + " | " + dto.getCmpNombre();
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdRemoverEmpresa.msg2");
            int opc = JOptionPane.showConfirmDialog(laVista, msg[0], msg[1], JOptionPane.YES_NO_OPTION );
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdRemover(dto) ){
                    // * Notificar al controlador principal
                    //CtrlPrincipal.modificacionesCard = true;
                    modeloTabla.removeRow(seleccionado);
                    CtrlPrincipal.ctrlBarraEstadoNumEmpresas =  modeloTabla.getRowCount();
                    CtrlPrincipal.actualizarBarraEstado();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdRemoverEmpresa.msg3")
                    .replace("<Empresa>", dto.getCmpNombre())
                    );
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdRemoverEmpresa.msg4"));
        
    }
    
    private EmpresaDto mtdObtenerEmpresa(int fila){
        EmpresaDto empresa = new EmpresaDto();
        
        empresa.setCmpID( Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString()) );
        empresa.setCmpNombre( String.valueOf( modeloTabla.getValueAt(fila, 1))  );
        empresa.setCmpDireccion( String.valueOf(modeloTabla.getValueAt(fila, 2)) );
        empresa.setCmpCorreo( String.valueOf(modeloTabla.getValueAt(fila, 3)) );
        empresa.setCmpTMovil( String.valueOf(modeloTabla.getValueAt(fila, 4)) );
        
        return empresa;
    }
    
    private boolean mtdValidarCampo(){
        String campo = laVista.cmpEmpresa.getText().trim();
        
        if( campo.isEmpty() || !laVista.cmpEmpresa.isAprobado() ){
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarCampo.msg1"));
            return false;
        } else if( campo.length() > 30 ){
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarCampo.msg2"));
            return false;
        }
        
        cmpEmpresa = campo;
        laVista.cmpEmpresa.setText(null);
        return true;
    }
    
    private boolean mtdValidarDatos(EmpresaDto empresa){
        int errores = 0;
        String msg = MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg1");
        
        if( empresa.getCmpNombre().isEmpty() ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg2");
        } else if( empresa.getCmpNombre().length() > 30 ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg3");
        }
        
        if( empresa.getCmpCorreo().isEmpty() ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg4");
        } else if( empresa.getCmpCorreo().length() > 60 ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg5");
        } else if( !mtdComprobarCorreo( empresa.getCmpCorreo() ) ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg6");
        }
        
        if( empresa.getCmpDireccion().isEmpty() ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg7");
        } else if( empresa.getCmpDireccion().length() > 60 ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg8");
        }
        
        if( !mtdComprobarTMovil(empresa.getCmpTMovil()) ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg9");
        }
        
        //System.out.println("Errores : "+ errores);
        if( errores > 0 ){
            JOptionPane.showMessageDialog(laVista, msg, 
                    MyFreeLab.idioma
                    .getProperty("ctrlGestionarEmpresa.mtdValidarDatos.msg10")
                    + " | " + empresa.getCmpNombre(),
                    JOptionPane.YES_OPTION
            );
            return false;
        }
        
        return true;
    }
    
    private boolean mtdComprobarTMovil(String tmovil){
        int contador = 0;
        
        if( tmovil.length() == 10 ){
            
            for (int i = 0; i < tmovil.length(); i++) {
                char letra = tmovil.charAt(i);
                if( Character.isDigit(letra) )
                    contador++;
            }
            
            //System.out.println("Digitos : " + contador);
            return (contador == 10);
        }
        
        return false;
    }
    
    private boolean mtdComprobarCorreo(String correo){
        String campo = correo;
        String nombre = null;
        String dominio = null;
        String extension = null;
        
        if( campo.contains("@") && campo.contains(".") ){
            nombre = campo.substring(0, campo.indexOf("@"));
            dominio = campo.substring(campo.indexOf("@") + 1, campo.indexOf("."));
            extension = campo.substring(campo.indexOf(".") + 1, campo.length());
            
            if( nombre.length() > 3 && dominio.length() > 3 && extension.length() > 2 ){
                return true;
            }
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
