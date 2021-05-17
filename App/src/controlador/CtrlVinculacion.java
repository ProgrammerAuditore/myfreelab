package controlador;

import java.awt.Dialog;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.VinculacionDao;
import modelo.dto.EmpresaDto;
import modelo.dto.ProyectoDto;
import vista.paneles.PanelVinculacion;

public class CtrlVinculacion implements MouseListener, ItemListener{
    
    // * Vistas
    private PanelVinculacion laVista;
    public JDialog modal;
    
    // * Modelos
    private EmpresaDao empresa_dao;
    private ProyectoDao proyecto_dao;
    private VinculacionDao vinculacion_dao;
    
    // * Atributos
    private List<EmpresaDto> lista_empresa;
    private List<ProyectoDto> lista_proyecto;
    private DefaultListModel lista_asociada;
    
    public CtrlVinculacion(PanelVinculacion laVista, ProyectoDao proyectos, EmpresaDao empresas, VinculacionDao vinculacion_dao) {
        this.laVista = laVista;
        this.modal = modal;
        this.empresa_dao = empresas;
        this.proyecto_dao = proyectos;
        this.vinculacion_dao = vinculacion_dao;
        
        // * Defini propiedades
        this.laVista.lstEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.laVista.lstEmpresasAsociadas.setFocusable(false);
        this.laVista.lstEmpresasAsociadas.setEnabled(false);
        
        // * Definir oyentes
        this.laVista.btnCancelar.addMouseListener(this);
        this.laVista.btnAceptar.addMouseListener(this);
        this.laVista.cmbProyectos.addItemListener(this);
        
        
    }
    
    public void mtdInit(){
        lista_asociada = new DefaultListModel();
        lista_empresa = new ArrayList<>();
        lista_proyecto = new ArrayList<>();
        
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Vinculación");
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        
        if( CtrlHiloConexion.ctrlEstado == true ){
            mtdRellenarProyectos();
            mtdRellenarEmpresas();
        }
        
    }
    
    private void mtdRellenarProyectos(){
        laVista.cmbProyectos.removeAllItems();
        lista_proyecto.clear();
        
        laVista.cmbProyectos.addItem("Ninguno");
        lista_proyecto = proyecto_dao.mtdListar();
        
        for (int i = 0; i < lista_proyecto.size(); i++) {
            laVista.cmbProyectos.addItem( lista_proyecto.get(i).getCmpNombre() );
        }
        
    }
    
    private void mtdRellenarEmpresas(){
        laVista.lstEmpresas.removeAll();
        lista_empresa.clear();
        
        DefaultListModel modelo = new DefaultListModel();
        lista_empresa = empresa_dao.mtdListar();
        
        for (int i = 0; i < lista_empresa.size(); i++) {
            modelo.addElement( lista_empresa.get(i).getCmpNombre() );
        }
        
        laVista.lstEmpresas.setModel(modelo);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnCancelar)
            mtdBtnCancelar();
        
        if( e.getSource() == laVista.btnAceptar )
            mtdBtnAceptar();
        
    }
    
    private void mtdBtnCancelar(){
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdBtnAceptar(){
        
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        
        if( e.getSource() == laVista.cmbProyectos )
            mtdCambio();
    
    }
    
    private void mtdCambio(){
        String seleccionado = String.valueOf( laVista.cmbProyectos.getSelectedItem() );
        if( seleccionado.equalsIgnoreCase("Ninguno") ){
            laVista.etqAsociar.setEnabled(false);
            laVista.etqEliminar.setEnabled(false);
            laVista.btnAceptar.setEnabled(false);
        } else {
            laVista.etqAsociar.setEnabled(true);
            laVista.etqEliminar.setEnabled(true);
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
