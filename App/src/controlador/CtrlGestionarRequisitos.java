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
import modelo.dto.RequisitoDto;
import vista.paneles.PanelGestionarRequisitos;

public class CtrlGestionarRequisitos implements MouseListener{
    
    // * Vistas
    private PanelGestionarRequisitos laVista;
    public JDialog modal;
    
    // * Modelos
    private RequisitoDto dto;
    private RequisitoDao dao;
    
    // * Atributos
    private DefaultTableModel modeloTabla;
    private String cmpRequisito;
    private double cmpCosto;
    private double cmpMonto;
    private List<RequisitoDto> requisitos;
    
    public CtrlGestionarRequisitos(PanelGestionarRequisitos laVista, RequisitoDto dto, RequisitoDao dao) {
        this.laVista = laVista;
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
        this.laVista.btnBuscar.addMouseListener(this);
        
        // * Inicializar
        mtdInit();
    }
    
    private void mtdInit(){
        requisitos = new ArrayList<RequisitoDto>();
        modal = new JDialog();
        
        modal.setTitle("Gestionar requisitos");
        modal.setType(Window.Type.UTILITY);
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
    
    private void mtdBuscarRequisito(){}
    
    private void mtdCrearRequisito(){
     
        if( mtdValidarCampoCosto() && mtdValidarCampoRequisito() ){
            dto.setCmpNombre( cmpRequisito );
            dto.setCmpCosto( cmpCosto );
            
            if( dao.mtdInsetar(dto) ){
                mtdRellenarTabla();
                JOptionPane.showMessageDialog(null, "El requisito `" + dto.getCmpNombre() + "` se agrego exitosamente.");
            }

        }else 
        JOptionPane.showMessageDialog(null, "Verifica que el campo sea un dato valido.");
        
    }
    
    private void mtdModificarRequisito(){}
    private void mtdEliminarRequisito(){}
    
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
        requisitos = dao.mtdListar();
        
        if( requisitos.size() > 0){
            mtdAgregarRequisitos();
            mtdCalcularMonto();
        }
        
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
