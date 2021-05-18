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
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
       
        
    }
    
    
    @Override
    public void itemStateChanged(ItemEvent e) {
       
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
