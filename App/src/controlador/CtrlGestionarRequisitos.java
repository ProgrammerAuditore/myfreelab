package controlador;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
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
    private DefaultTableModel requisitos;
    private String cmpRequisito;
    private String cmpCosto;
    
    public CtrlGestionarRequisitos(PanelGestionarRequisitos laVista, RequisitoDto dto, RequisitoDao dao) {
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
        
        // * Definir modelo de la tabla
        requisitos = new DefaultTableModel();
        this.laVista.tblRequisitos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.laVista.tblRequisitos.getTableHeader().setReorderingAllowed(false);
        this.laVista.tblRequisitos.setModel(requisitos);
        
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
    private void mtdCrearRequisito(){}
    private void mtdModificarRequisito(){}
    private void mtdEliminarRequisito(){}
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
   
}
