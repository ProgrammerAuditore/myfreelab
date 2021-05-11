package controlador;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ProyectoDao;
import modelo.dto.ProyectoDto;
import vista.paneles.PanelGestionarProyectos;

public class CtrlGestionarProyectos implements MouseListener{
    
    private PanelGestionarProyectos laVista;
    private ProyectoDao dao;
    private ProyectoDto dto;
    private String cmpProyecto;
    public JDialog modal;
    private DefaultTableModel modeloTabla = new DefaultTableModel();
    private List<ProyectoDto> proyectos = new ArrayList<>();

    public CtrlGestionarProyectos(PanelGestionarProyectos laVista, ProyectoDao dao, ProyectoDto dto) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
        
        // * Establecer oyentes
        this.laVista.btnBuscar.addMouseListener(this);
        this.laVista.btnCrear.addMouseListener(this);
        this.laVista.btnEliminar.addMouseListener(this);
        this.laVista.btnModificar.addMouseListener(this);
        
        // * Definir el modelo para la tabla
        modeloTabla = (DefaultTableModel) this.laVista.tblProyectos.getModel();
        this.laVista.tblProyectos.getTableHeader().setReorderingAllowed(false);
        this.laVista.tblProyectos.setModel(modeloTabla);
        
        mtdInit();
    }

    private void mtdInit() {
        mtdVaciarTabla();
        modal = new JDialog();
        
        modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
        // Verificar si hay conexion al servidor de base datos
        if( CtrlHiloConexion.checkConexion() ){
            proyectos = dao.mtdConsultar();
            
            if( proyectos.size() > 0 )
                mtdAgregarProyectos();
        
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnBuscar )
            mtdBuscarProyecto();
        
        if( e.getSource() == laVista.btnCrear )
            mtdCrearProyecto();
        
        if( e.getSource() == laVista.btnModificar )
            mtdModificarProyecto();
        
        if( e.getSource() == laVista.btnEliminar )
            mtdEliminarProyecto();
    
    }
    
    private void mtdBuscarProyecto(){
        System.out.println("Buscar proyectos");
        
        
        
    }
    
    private void mtdCrearProyecto(){
       
        if( mtdValidarCampo() ){
            System.out.println("Crear proyectos");
            dto.setCmpNombre( cmpProyecto );
            
            if(dao.mtdInsetar(dto)){
                dto.setCmpID( modeloTabla.getRowCount() + 1 );
                proyectos.add(dto);
            
                mtdAgregarProyecto();
                JOptionPane.showMessageDialog(null, "El proyecto `" + cmpProyecto + "` se creo exitosamente.");
            
            }
        } else 
        JOptionPane.showMessageDialog(null, "Verifica que el campo sea un dato valido.");
        
    }
    
    private void mtdModificarProyecto(){
        System.out.println("Modificar proyectos");
        
        
    }
    
    private void mtdEliminarProyecto(){
        System.out.println("Eliminar proyectos");
        
    }
    
    private boolean mtdValidarCampo(){
        String cmp = laVista.cmpProyecto.getText().trim();

        if( laVista.cmpProyecto.isAprobado() && !cmp.isEmpty() ){
            cmpProyecto = cmp;
            laVista.cmpProyecto.setText(null);
            return true;
        }
        
        return false;
    }
    
    private void mtdAgregarProyectos(){
        int num_filas = proyectos.size();

        for (int fila = 0; fila < num_filas; fila++) {
            mtdAgregarProyecto();
        }
    }
    
    private void mtdAgregarProyecto(){
        int fila = modeloTabla.getRowCount();
        modeloTabla.setNumRows( modeloTabla.getRowCount() + 1 );
        
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpID() , fila, 0);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpNombre(), fila, 1);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpFechaInicial(), fila, 2);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpFechaFinal(), fila, 3);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpCostoEstimado(), fila, 4);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpMontoAdelantado(), fila, 5);
    }
    
    private void mtdVaciarTabla(){
        while( modeloTabla.getRowCount() > 0){
            modeloTabla.removeRow(0);
        }
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
