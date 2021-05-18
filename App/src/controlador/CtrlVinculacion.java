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
import javax.swing.JOptionPane;
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
        
        if( e.getSource() == laVista.etqAsociar )
            if( laVista.etqAsociar.isEnabled() )
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
        int index_empresa = laVista.lstEmpresas.getSelectedIndex();
        
        if( index_empresa >= 0 ){
            // * Obtener los datos para la vinculación
            String nombre_empresa = laVista.lstEmpresas.getSelectedValue();
            vinculacion_dto.setCmpEmpID( lista_empresas.get(index_empresa).getCmpID() );
            vinculacion_dto.setCmpEmpNombre( nombre_empresa );
            //System.out.println("Nombre : " + nombre_empresa );
            
            int index_proyecto = laVista.cmbProyectos.getSelectedIndex();
            vinculacion_dto.setCmpProID( lista_proyectos.get(index_proyecto - 1).getCmpID() );
            vinculacion_dto.setCmpProNombre( lista_proyectos.get(index_proyecto - 1).getCmpNombre() );
            //System.out.println("Proyecto : " + lista_proyectos.get(index_proyecto - 1).getCmpNombre() );
            
            if( vinculacion_dao.mtdVincular(vinculacion_dto) ){
                mtdElegirItem();
                /*
                // * Agregar una empresa en la lista de asociados
                DefaultListModel modelo_empresas_asociadas = (DefaultListModel) laVista.lstEmpresasAsociadas.getModel();
                modelo_empresas_asociadas.addElement(nombre_empresa);
                laVista.lstEmpresasAsociadas.setModel(modelo_empresas_asociadas);

                // * Eliminar una empresas de la lista de empresas
                DefaultListModel modelo_empresa = (DefaultListModel) laVista.lstEmpresas.getModel();
                modelo_empresa.removeElementAt(index_empresa);
                laVista.lstEmpresas.setModel(modelo_empresa);
                */
                JOptionPane.showMessageDialog(null, "La empresa `" + vinculacion_dto.getCmpEmpNombre() 
                        + "` fue viculado al proyecto `" + vinculacion_dto.getCmpProNombre() + "`.");
            }
            
        } else
        JOptionPane.showMessageDialog(null, "Selecciona una empresa para vincular al proyecto actual.");
        
    }
    
    private void mtdEliminar(){
        int index = laVista.lstEmpresasAsociadas.getSelectedIndex();
        
        if( index >= 0 ){
            
            int index_empresa = laVista.lstEmpresasAsociadas.getSelectedIndex();
            vinculacion_dto.setCmpID( vinculos.get(index_empresa).getCmpID() );
            vinculacion_dto.setCmpEmpNombre( vinculos.get(index_empresa).getCmpEmpNombre() );
            vinculacion_dto.setCmpProNombre( vinculos.get(index_empresa).getCmpProNombre() );
            
            if( vinculacion_dao.mtdEliminar(vinculacion_dto) ){
                mtdElegirItem();
                /*
                // * Eliminar una empresas desde la lista de asiciados
                DefaultListModel modelo = (DefaultListModel) laVista.lstEmpresasAsociadas.getModel();
                String valor = laVista.lstEmpresasAsociadas.getSelectedValue();
                modelo.removeElementAt(index);
                laVista.lstEmpresasAsociadas.setModel(modelo);

                // * Agregar la empresa eliminada en la lista de empresas
                DefaultListModel m = (DefaultListModel) laVista.lstEmpresas.getModel();
                m.addElement( valor );
                laVista.lstEmpresas.setModel(m);
                */
                JOptionPane.showMessageDialog(null, "La empresa `" + vinculacion_dto.getCmpEmpNombre() 
                        + "` fue desviculado al proyecto `" + vinculacion_dto.getCmpProNombre() + "`.");
            }
        } else
        JOptionPane.showMessageDialog(null, "Selecciona una empresa vinculada para desvincular del proyecto actual.");
        
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
