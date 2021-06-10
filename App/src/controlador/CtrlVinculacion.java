package controlador;

import java.awt.Dialog;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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
    private List<VinculacionDto> lista_asociados;
    
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
        lista_empresas = new ArrayList<>();
        lista_proyectos = new ArrayList<>();
        lista_asociados = new ArrayList<>();
        
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Vinculación");
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        
        if( CtrlHiloConexion.ctrlEstado ){
            mtdVaciarEmpresas();
            mtdCargarEmpresas();
            mtdCargarProyectos();
        }
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
            }
        });
        
    }
    
    private void mtdCargarEmpresas(){
        DefaultListModel<String> modelo = new DefaultListModel<>();
        lista_empresas = empresa_dao.mtdListar();
        int tam = lista_empresas.size();
        
        for (int i = 0; i < tam; i++) {
            modelo.addElement( "" + lista_empresas.get(i).getCmpNombre() );
        }
        
        laVista.lstEmpresas.setModel(modelo);
    }
    
    private void mtdCargarProyectos(){
        lista_proyectos = proyecto_dao.mtdListar();
        int tam = lista_proyectos.size();
        
        for (int i = 0; i < tam; i++) {
            laVista.cmbProyectos.addItem( lista_proyectos.get(i).getCmpNombre() );
            
        }
    }
    
     
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.etqAsociar )
            if( laVista.etqAsociar.isEnabled() )
                mtdAsociar();
        
        if( e.getSource() == laVista.etqEliminar )
            if( laVista.etqEliminar.isEnabled() )
                mtdEliminar();
        
        if( e.getSource() == laVista.btnCancelar )
            if( laVista.btnCancelar.isEnabled() )
                mtdBtnCancelar();
        
    }
    
    private void mtdBtnCancelar(){
        modal.setVisible(false);
        modal.dispose();
        
    }
    
    private void mtdAsociar(){
        int seleccionado = laVista.lstEmpresas.getSelectedIndex();
        
        if( seleccionado >= 0){
            // * Definir datos para vinculacion_dto
            int index_proyecto = laVista.cmbProyectos.getSelectedIndex();
            String nombre_proyecto = (String) laVista.cmbProyectos.getSelectedItem();
            int id_proyecto = lista_proyectos.get( index_proyecto - 1 ).getCmpID();
            String nombre_empresa = (String) laVista.lstEmpresas.getSelectedValue();
            int id_empresa  = mtdObtenerEmpresaId( nombre_empresa );
            
            /*
            System.out.println("***********************************************");
            System.out.println("Nombre proyecto : "  + nombre_proyecto);
            System.out.println("Nombre id : "  + id_proyecto);
            System.out.println("Nombre empresa : "  + nombre_empresa);
            System.out.println("Empresa id : "  + id_empresa);
            System.out.println("\n");
            */
            
            // * Establecer datos para vinculacion_dto
            vinculacion_dto.setCmpEmpID( id_empresa );
            vinculacion_dto.setCmpProID( id_proyecto );
            vinculacion_dto.setCmpEmpNombre( nombre_empresa );
            vinculacion_dto.setCmpProNombre( nombre_proyecto );
            
            if( vinculacion_dao.mtdVincular(vinculacion_dto) ){
                mtdCargarEmpresas();
                mtdDefinirEmpresasAsociados();
                mtdReEstablecerEmpresas();
                JOptionPane.showMessageDialog(null, "La empresa `"+ vinculacion_dto.getCmpEmpNombre() +"` \n"
                       + "fue asociada con el proyecto `"+ vinculacion_dto.getCmpProNombre()+"` \n"
                       + "exitosamente.");
            }
        
        }else 
        JOptionPane.showMessageDialog(null, "Selecciona una empresa para vincular al proyecto actual.");
        
    }
    
    private void mtdEliminar(){
        int index = laVista.lstEmpresasAsociadas.getSelectedIndex();
        
        if( index >= 0 ){
            
            int index_empresa = laVista.lstEmpresasAsociadas.getSelectedIndex();
            vinculacion_dto.setCmpID(lista_asociados.get(index_empresa).getCmpID() );
            vinculacion_dto.setCmpEmpNombre(lista_asociados.get(index_empresa).getCmpEmpNombre() );
            vinculacion_dto.setCmpProNombre(lista_asociados.get(index_empresa).getCmpProNombre() );
            
            if( vinculacion_dao.mtdEliminar(vinculacion_dto) ){
                mtdCargarEmpresas();
                mtdDefinirEmpresasAsociados();
                mtdReEstablecerEmpresas();
                JOptionPane.showMessageDialog(null, "La empresa `" + vinculacion_dto.getCmpEmpNombre() + "` \n"
                        + "fue desviculado al proyecto `" + vinculacion_dto.getCmpProNombre() + "` \n"
                        + "exitosamente.");
            }
        } else
        JOptionPane.showMessageDialog(null, "Selecciona una empresa asociada para desvincular del proyecto actual.");
        
    }
    
    private int mtdObtenerEmpresaId(String nombre_empresa){
        int id = 0;
        
        for (int i = 0; i < lista_empresas.size(); i++) {
            String nombre_seleccioando = lista_empresas.get(i).getCmpNombre();
            
            if( nombre_seleccioando.equals( nombre_empresa ) ){
                id = lista_empresas.get(i).getCmpID();
                break;
            }
        }   
        
        return id;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        
        if( e.getSource() == laVista.cmbProyectos );
            mtdElegirProyecto();
        
    }
    
    private void mtdElegirProyecto(){
        String nombre_empresa = (String) laVista.cmbProyectos.getSelectedItem();
        mtdVaciarEmpresas();
        mtdCargarEmpresas();
        
        if( !nombre_empresa.equals("Ninguno") ){
            laVista.etqAsociar.setEnabled(true);
            
            if( mtdDefinirEmpresasAsociados() ){
                laVista.lstEmpresasAsociadas.setEnabled(true);
                laVista.lstEmpresasAsociadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                laVista.etqEliminar.setEnabled(true);
                mtdReEstablecerEmpresas();
                
            }
        }
    }
    
    private void mtdVaciarEmpresas(){
       lista_empresas.clear();
       laVista.lstEmpresas.removeAll();
       String[] ninguno = {"Ninguno"};
       
       laVista.lstEmpresasAsociadas.setListData(ninguno);
       laVista.lstEmpresasAsociadas.setEnabled(false);
       laVista.etqAsociar.setEnabled(false);
       laVista.etqEliminar.setEnabled(false);
    }
    
    private boolean mtdDefinirEmpresasAsociados(){
        int index = laVista.cmbProyectos.getSelectedIndex();
        
        if( index >=0 ){
            vinculacion_dto.setCmpProID( lista_proyectos.get(index - 1).getCmpID() );

            DefaultListModel<String> modelo = new DefaultListModel<>();
            lista_asociados = vinculacion_dao.mtdListar(vinculacion_dto);
            
            for (int i = 0; i < lista_asociados.size(); i++) {
                modelo.addElement("" + lista_asociados.get(i).getCmpEmpNombre() );
            }
            
            laVista.lstEmpresasAsociadas.setModel(modelo);
            return true;
        }
        
        return false;
    }
    
    private void mtdReEstablecerEmpresas(){
        DefaultListModel<String> modelo = (DefaultListModel<String>) laVista.lstEmpresas.getModel();
        
        for (int i = 0; i < lista_asociados.size(); i++) {
            String valor = lista_asociados.get(i).getCmpEmpNombre();
            
            laVista.lstEmpresas.setSelectedValue( valor , false);
            int index = laVista.lstEmpresas.getSelectedIndex();
            
            if( index >= 0 )
            modelo.remove(index);
        }
        
        laVista.lstEmpresas.setModel(modelo);
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
