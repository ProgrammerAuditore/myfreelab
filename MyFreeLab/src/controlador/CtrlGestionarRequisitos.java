package controlador;

import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public static BigDecimal proyectoMonto;
    
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
        CtrlGestionarRequisitos.proyectoMonto = new BigDecimal(0);
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
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
            }
        });
        
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
            JOptionPane.showMessageDialog(laVista, "El proyecto `"+ cmpRequisito +"` no existe.");
            
        }
        
    }
    
    private void mtdCrearRequisito(){
     
        if( mtdValidarCampoRequisito() && mtdValidarCampoCosto() ){
            dto.setCmpProID( proyecto_dto.getCmpID() );
            dto.setCmpNombre( cmpRequisito );
            BigDecimal campoCosto =  new BigDecimal(cmpCosto).setScale(2, RoundingMode.HALF_EVEN);
            dto.setCmpCosto( campoCosto.doubleValue() );
            
            if( !dao.mtdComprobar(dto) ){
                
                if( dao.mtdInsetar(dto) ){
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, "El requisito `" + dto.getCmpNombre() + "` se agregó exitosamente.");
                }
                
            }else
            JOptionPane.showMessageDialog(laVista, "El requisito `" + dto.getCmpNombre() + "` ya existe.");   

        }
        
    }
    
    private void mtdModificarRequisito(){
        int seleccionado = laVista.tblRequisitos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            String[] msg = new String[2];
            dto = mtdObtenerRequisito(seleccionado);
            
            msg[0] = "Modificar requisito";
            msg[1] = "¿Seguro que deseas modificar el requisito seleccionado?";
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1], msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdActualizar(dto) ){
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, "El requisito `" + dto.getCmpNombre()+ "` se modificó exitosamente.");
                }
            }
            
        }else 
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para modificar un requisito.");
            
    }
    
    private void mtdEliminarRequisito(){
        int seleccionado = laVista.tblRequisitos.getSelectedRow();
        
        if( seleccionado >= 0){
            String[] msg = new String[2];
            dto = mtdObtenerRequisito(seleccionado);
            
            msg[0] = "Eliminar requisito";
            msg[1] = "¿Seguro que deseas eliminar el requisito seleccionado?";
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1], msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdRemover(dto) ){
                    modeloTabla.removeRow(seleccionado);
                    mtdCalcularMonto();
                    JOptionPane.showMessageDialog(laVista, "El requisito `" + dto.getCmpNombre()+ "` se eliminó exitosamente.");
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para eliminar un requisito.");
    
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
            JOptionPane.showMessageDialog(laVista, "Verifica que el campo requisito sea un dato valido.");
            return false;
        }else if ( campo.length() > 30 ){
            JOptionPane.showMessageDialog(laVista, "El campo debe ser menor a 30 caracteres.");
            return false;
        }
        
        cmpRequisito = campo;
        laVista.cmpRequisito.setText(null);
        return true;
    }
    
    private boolean mtdValidarCampoCosto(){
        String campo = laVista.cmpCosto.getText();
        
        if( campo.isEmpty() || !laVista.cmpCosto.isAprobado() ){
            JOptionPane.showMessageDialog(laVista, "Verifica que el campo costo sea un dato valido.");
            return false;
        } else if( campo.equals("0") || campo.equals("0.0") ){
            JOptionPane.showMessageDialog(laVista, "El campo debe tener un costo mayor a 0.");
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
        dto.setCmpProID( proyecto_dto.getCmpID()  );
        requisitos = dao.mtdListar(dto);
        
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
            mtdRellenarRequisito();
        }
    }

    private void mtdRellenarRequisito() {
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
        
        BigDecimal cmpMonto = new BigDecimal(this.cmpMonto).setScale(2, RoundingMode.HALF_EVEN);
        laVista.cmpMontoEstimado.setText("" + cmpMonto.doubleValue());
        proyectoMonto = cmpMonto;
    }
   
}
