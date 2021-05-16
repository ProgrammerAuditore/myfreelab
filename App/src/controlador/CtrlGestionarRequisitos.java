package controlador;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.dao.RequisitoDao;
import modelo.dto.ProyectoDto;
import modelo.dto.RequisitoDto;
import vista.paneles.PanelGestionarRequisitos;

public class CtrlGestionarRequisitos implements MouseListener{
    
    // * Vistas
    private PanelGestionarRequisitos laVista;
    public JDialog modal;
    
    // * Modelos
    private RequisitoDto dto;
    private RequisitoDao dao;
    private ProyectoDto proyecto_dto;
    
    // * Atributos
    private DefaultTableModel modeloTabla;
    private String cmpRequisito;
    private double cmpCosto;
    private double cmpMonto;
    private List<RequisitoDto> requisitos;
    
    public CtrlGestionarRequisitos(PanelGestionarRequisitos laVista, ProyectoDto proyecto_dto,  RequisitoDto dto, RequisitoDao dao) {
        this.laVista = laVista;
        this.proyecto_dto = proyecto_dto;
        this.dto = dto;
        this.dao = dao;
        
        // * Definir modelo de la tabla
        modeloTabla = (DefaultTableModel) this.laVista.tblRequisitos.getModel();
        this.laVista.tblRequisitos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.laVista.tblRequisitos.getTableHeader().setReorderingAllowed(false);
        this.laVista.tblRequisitos.setModel(modeloTabla);
        
        // * Definir oyentes
        this.laVista.btnCrear.addMouseListener(this);
        this.laVista.btnBuscar.addMouseListener(this);
        this.laVista.btnModificar.addMouseListener(this);
        this.laVista.btnEliminar.addMouseListener(this);
        
        // * Inicializar
        //mtdInit();
    }
    
    public void mtdInit(){
        requisitos = new ArrayList<RequisitoDto>();
        //modal = new JDialog();
        
        modal.setTitle("Gestionar requisitos | " + proyecto_dto.getCmpNombre() );
        //modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane( laVista );
        
        if( CtrlHiloConexion.ctrlEstado == true ){
            // * Rellenar tablas
            mtdRellenarTabla();
        }
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnBuscar )
            mtdBuscarRequisito();
            
        if( e.getSource() == laVista.btnCrear )
            mtdCrearRequisito();
        
        if( e.getSource() == laVista.btnModificar )
            mtdModificarRequisito();
        
        if( e.getSource() == laVista.btnEliminar )
            mtdEliminarRequisito();
        
    }
    
    private void mtdBuscarRequisito(){
        
        if( mtdValidarCampoRequisito() ){
            int tam = modeloTabla.getRowCount();
            boolean encontrado = false;
            
            for (int i = 0; i < tam; i++) {
                String req = String.valueOf( modeloTabla.getValueAt(i, 0) );
                
                if( req.equals(cmpRequisito) || modeloTabla.getValueAt(i, 1).equals(cmpRequisito) ){
                    laVista.tblRequisitos.setRowSelectionInterval(i, i);
                    encontrado = true;
                }
            }
            
            if( !encontrado )
            JOptionPane.showMessageDialog(null, "El proyecto `"+ cmpRequisito +"` no existe.");
            
        } else 
        JOptionPane.showMessageDialog(null, "Verifica que el campo sea un dato valido.");
        
    }
    
    private void mtdCrearRequisito(){
     
        if( mtdValidarCampoCosto() && mtdValidarCampoRequisito() ){
            dto.setCmpProID( proyecto_dto.getCmpID() );
            dto.setCmpNombre( cmpRequisito );
            dto.setCmpCosto( cmpCosto );
            
            if( dao.mtdInsetar(dto) ){
                mtdRellenarTabla();
                JOptionPane.showMessageDialog(null, "El requisito `" + dto.getCmpNombre() + "` se agrego exitosamente.");
            }

        }else 
        JOptionPane.showMessageDialog(null, "Verifica que el campo sea un dato valido.");
        
    }
    
    private void mtdModificarRequisito(){
        int seleccionado = laVista.tblRequisitos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            String[] msg = new String[2];
            dto = mtdObtenerRequisito(seleccionado);
            
            msg[0] = "Modificar requisito";
            msg[1] = "¿Seguro que deseas modificar el requisito seleccionado?";
            int opc = JOptionPane.showConfirmDialog(null, msg[1], msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdActualizar(dto) ){
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(null, "El requisito `" + dto.getCmpNombre()+ "` se modifico exitosamente.");
                }
            }
            
        }else 
        JOptionPane.showMessageDialog(null, "Selecciona una fila para modificar un requisito.");
            
    }
    
    private void mtdEliminarRequisito(){
        int seleccionado = laVista.tblRequisitos.getSelectedRow();
        
        if( seleccionado >= 0){
            String[] msg = new String[2];
            dto = mtdObtenerRequisito(seleccionado);
            
            msg[0] = "Eliminar requisito";
            msg[1] = "¿Seguro que deseas eliminar el requisito seleccionado?";
            int opc = JOptionPane.showConfirmDialog(null, msg[1], msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdEliminar(dto) ){
                    modeloTabla.removeRow(seleccionado);
                    mtdCalcularMonto();
                    JOptionPane.showMessageDialog(null, "El requisito `" + dto.getCmpNombre()+ "` se elimino exitosamente.");
                }
            }
            
        } else
        JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar un requisito.");
    
    }
    
    private RequisitoDto mtdObtenerRequisito(int fila){
        RequisitoDto requisito = new RequisitoDto();
        
        requisito.setCmpID( Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString()) );
        requisito.setCmpNombre( modeloTabla.getValueAt(fila, 1).toString() );
        requisito.setCmpCosto( Double.parseDouble(modeloTabla.getValueAt(fila, 2).toString())  );
        
        return requisito;
    }
    
    private boolean mtdValidarCampoRequisito(){
        String campo = laVista.cmpRequisito.getText();
        
        if( campo.isEmpty() || !laVista.cmpRequisito.isAprobado() ){
            return false;
        }else if ( campo.length() > 20 ){
            JOptionPane.showMessageDialog(null, "El campo debe ser menor a 20 caracteres.");
            return false;
        }
        
        cmpRequisito = campo;
        laVista.cmpRequisito.setText(null);
        return true;
    }
    
    private boolean mtdValidarCampoCosto(){
        String campo = laVista.cmpCosto.getText();
        
        if( campo.isEmpty() || !laVista.cmpCosto.isAprobado() ){
            return false;
        } else if( campo.equals("0") || campo.equals("0.0") ){
            JOptionPane.showMessageDialog(null, "El campo debe tener un costo mayor a 0.");
            return false;
        }
        
        cmpCosto =  Double.parseDouble(campo);
        laVista.cmpCosto.setText(null);
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

    private void mtdRellenarTabla() {
        mtdVaciar();
        requisitos = dao.mtdListar(proyecto_dto);
        
        if( requisitos.size() > 0)
            mtdAgregarRequisitos();
            mtdCalcularMonto();
        
    }

    private void mtdVaciar() {
        while( modeloTabla.getRowCount() > 0 ){
            modeloTabla.removeRow(0);
        }
    }

    private void mtdAgregarRequisitos() {
        int tam = requisitos.size();
        for (int i = 0; i < tam; i++) {
            mtdAgregarRequisito();
        }
    }

    private void mtdAgregarRequisito() {
        int fila = modeloTabla.getRowCount();
        modeloTabla.setNumRows( fila + 1 );
        
        laVista.tblRequisitos.setValueAt(requisitos.get(fila).getCmpID(), fila, 0);
        laVista.tblRequisitos.setValueAt(requisitos.get(fila).getCmpNombre(), fila, 1);
        laVista.tblRequisitos.setValueAt(requisitos.get(fila).getCmpCosto(), fila, 2);
        
    }
    
    private void mtdCalcularMonto(){
        int tam = modeloTabla.getRowCount();
        cmpMonto = 0;
        
        for (int i = 0; i < tam; i++) {
            cmpMonto += Double.parseDouble( laVista.tblRequisitos.getValueAt(i, 2).toString() );
        }
        
        laVista.cmpMontoEstimado.setText("" + cmpMonto);
    }
   
}
