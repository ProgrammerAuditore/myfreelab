package controlador;

import java.awt.Dialog;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import modelo.dao.VinculacionDao;
import modelo.dto.EmpresaDto;
import modelo.dto.ProyectoDto;
import modelo.dto.VinculacionDto;
import vista.paneles.PanelVinculacion;

public class CtrlVinculacion implements MouseListener, ItemListener{
    
    // * Vistas
    private PanelVinculacion laVista;
    public JDialog modal;
    
    // * Modelos
    private EmpresaDao empresa_dao;
    private ProyectoDao proyecto_dao;
    private VinculacionDao vinculacion_dao;
    private VinculacionDto vinculacion_dto;
    
    // * Atributos
    private List<EmpresaDto> lista_empresas;
    private List<ProyectoDto> lista_proyectos;
    private DefaultListModel lista_asociados;
    private List<VinculacionDto> vinculos;
    
    public CtrlVinculacion(PanelVinculacion laVista, ProyectoDao proyectos, EmpresaDao empresas, VinculacionDao vinculacion_dao, VinculacionDto vinculacion_dto) {
        this.laVista = laVista;
        this.modal = modal;
        this.empresa_dao = empresas;
        this.proyecto_dao = proyectos;
        this.vinculacion_dao = vinculacion_dao;
        this.vinculacion_dto = vinculacion_dto;
        
        // * Defini propiedades
        this.laVista.lstEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.laVista.lstEmpresasAsociadas.setFocusable(false);
        this.laVista.lstEmpresasAsociadas.setEnabled(false);
        
        // * Definir oyentes
        this.laVista.etqAsociar.addMouseListener(this);
        this.laVista.etqEliminar.addMouseListener(this);
        this.laVista.btnCancelar.addMouseListener(this);
        this.laVista.btnAceptar.addMouseListener(this);
        this.laVista.cmbProyectos.addItemListener(this);
        
        
    }
    
    public void mtdInit(){
        lista_asociados = new DefaultListModel();
        lista_empresas = new ArrayList<>();
        lista_proyectos = new ArrayList<>();
        vinculos = new ArrayList<>();
        
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Vinculación");
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        
        if( CtrlHiloConexion.ctrlEstado == true ){
            mtdRellenarProyectos();
            mtdDeshabilitar();
            mtdVaciarListaAsociados();
        }
        
    }
    
    private void mtdRellenarProyectos(){
        laVista.cmbProyectos.removeAllItems();
        lista_proyectos.clear();
        
        laVista.cmbProyectos.addItem("Ninguno");
        lista_proyectos = proyecto_dao.mtdListar();
        
        for (int i = 0; i < lista_proyectos.size(); i++) {
            laVista.cmbProyectos.addItem(lista_proyectos.get(i).getCmpNombre() );
        }
        
    }
    
    private void mtdRellenarEmpresas(){
        laVista.lstEmpresas.removeAll();
        lista_empresas.clear();
        
        DefaultListModel modelo = new DefaultListModel();
        lista_empresas = empresa_dao.mtdListar();
        
        for (int i = 0; i < lista_empresas.size(); i++) {
            modelo.addElement( lista_empresas.get(i).getCmpNombre() );
        }
        
        laVista.lstEmpresas.setModel(modelo);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnCancelar)
            mtdBtnCancelar();
        
        if( e.getSource() == laVista.btnAceptar )
            mtdBtnAceptar();
        
        if( e.getSource() == laVista.etqAsociar )
            if( laVista.etqEliminar.isEnabled() )
            mtdAsociar();
        
        if( e.getSource() == laVista.etqEliminar )
            if( laVista.etqEliminar.isEnabled() )
            mtdEliminar();
        
    }
    
    private void mtdBtnCancelar(){
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdBtnAceptar(){
        
    }
    
    private void mtdAsociar(){
        laVista.btnAceptar.setEnabled(true);
       
    }
    
    private void mtdEliminar(){
        laVista.btnAceptar.setEnabled(true);
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        
        if( e.getSource() == laVista.cmbProyectos )
            mtdElegirItem();
        
    }
    
    private void mtdElegirItem(){
        mtdDeshabilitar();
        mtdVaciarListaAsociados();
        mtdRellenarEmpresas();
        String seleccionado = String.valueOf( laVista.cmbProyectos.getSelectedItem() );
        
        if( !seleccionado.equalsIgnoreCase("Ninguno") ){
        laVista.etqAsociar.setEnabled(true);
            
            if(  mtdDefinirAsociados() ){
                laVista.etqEliminar.setEnabled(true);
                laVista.lstEmpresasAsociadas.setEnabled(true);
                laVista.lstEmpresasAsociadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                mtdRellenarLstEmpresas();
            }
        }
    }
    
    private void mtdRellenarLstEmpresas(){
        DefaultListModel modelo = (DefaultListModel) laVista.lstEmpresas.getModel();
        
        for (int i = 0; i < vinculos.size(); i++) {
            String valor = vinculos.get(i).getCmpEmpNombre();
            
            laVista.lstEmpresas.setSelectedValue( valor , false);
            int index = laVista.lstEmpresas.getSelectedIndex();
            
            modelo.remove(index);
        }
        
        laVista.lstEmpresas.setModel(modelo);
    }
        
    private boolean mtdDefinirAsociados(){
        int seleccionado = laVista.cmbProyectos.getSelectedIndex();
        
        if( seleccionado > 0 ){
            vinculacion_dto.setCmpProID( lista_proyectos.get(seleccionado - 1).getCmpID() );
            vinculos = vinculacion_dao.mtdListar(vinculacion_dto);
            
            //System.out.println( "" + lista_proyectos.get(seleccionado - 1).getCmpNombre() + " - " + vinculos.size() );
            Iterator<VinculacionDto> vc = vinculos.iterator();

            while(vc.hasNext()){
                lista_asociados.addElement( "" + vc.next().getCmpEmpNombre());
            }
                
            laVista.lstEmpresasAsociadas.setModel(lista_asociados);
            return true;
        }
        
        return false;
    }
    
    private void mtdVaciarListaAsociados(){
        String[] ninguno = {"Ninguno"};
        lista_asociados.clear();
        lista_asociados.removeAllElements();
        vinculos.clear();
        
        laVista.lstEmpresasAsociadas.removeAll();
        laVista.lstEmpresasAsociadas.setEnabled(false);
        laVista.lstEmpresasAsociadas.setFocusable(false);
        laVista.lstEmpresasAsociadas.setListData(ninguno);
    }
    
    private void mtdDeshabilitar(){
        laVista.etqAsociar.setEnabled(false);
        laVista.etqEliminar.setEnabled(false);
        laVista.btnAceptar.setEnabled(false);
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
