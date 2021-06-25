package controlador;

import java.awt.Dialog;
import java.awt.Window;
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
        this.laVista.btnEliminar.addMouseListener(this);
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
        
        modal.setTitle("Gestionar empresas");
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
        
        if( e.getSource() == laVista.btnEliminar )
            mtdEliminarEmpresa();
        
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
            JOptionPane.showMessageDialog(null, "El proyecto `"+ cmpEmpresa +"` no existe  .");
            
        }
        
    }
    
    private void mtdCrearEmpresa(){
        
        if( mtdValidarCampo() ){
            ////System.out.println("CtrlGestionarEmpresas - Crear empresa [!]");
            dto.setCmpNombre( cmpEmpresa );
            
            if( !dao.mtdComprobar(dto) ){
                
                if( dao.mtdInsetar(dto) ){
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(null, "La empresa `"+ dto.getCmpNombre() +"` se creo exitosamente.");
                }
                
            }else 
            JOptionPane.showMessageDialog(null,  "La empresa `"+ dto.getCmpNombre() +"` ya existe.");
            
        }
        
    }
    
    private void mtdModificarEmpresa(){
        int seleccionado = laVista.tblEmpresas.getSelectedRow();
        
        if( seleccionado >= 0 ){
            ////System.out.println("CtrlGestionarEmpresas - Modificar empresa [!]");
            String[] msg = new String[2];
            dto = mtdObtenerEmpresa(seleccionado);
            
            msg[1] = "Modificar empresa";
            msg[0] = "¿Seguro que deseas modificar la empresa seleccionado?";
            int opc = JOptionPane.showConfirmDialog(null, msg[0], msg[1], JOptionPane.YES_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdActualizar(dto) ){
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(null, "La empresa `" + dto.getCmpNombre() + "` se modifico exitosamente.");
                }
            }
            
        }
        
    }
    
    private void mtdEliminarEmpresa(){
        int seleccionado = laVista.tblEmpresas.getSelectedRow();
        
        if( seleccionado >= 0 ){
            ////System.out.println("CtrlGestionarEmpresas - Eliminar empresa [!]");
            String[] msg = new String[2];
            dto = mtdObtenerEmpresa(seleccionado);
            
            msg[1] = "Eliminar empresa";
            msg[0] = "¿Seguro que deseas eliminar la empresa seleccionado?";
            int opc = JOptionPane.showConfirmDialog(null, msg[0], msg[1], JOptionPane.YES_NO_OPTION );
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdEliminar(dto) ){
                    modeloTabla.removeRow(seleccionado);
                    JOptionPane.showMessageDialog(null, "La empresa `" + dto.getCmpNombre() + "` se elimino exitosamente.");
                }
            }
            
        }
        
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
            JOptionPane.showMessageDialog(null, "Verifica que el campo sea un dato valido.");
            return false;
        } else if( campo.length() > 30 ){
            JOptionPane.showMessageDialog(null, "El campo debe ser menor a 30 caracteres.");
            return false;
        }
        
        cmpEmpresa = campo;
        laVista.cmpEmpresa.setText(null);
        return true;
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
