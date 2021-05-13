package controlador;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.EmpresaDao;
import modelo.dto.EmpresaDto;
import vista.paneles.PanelGestionarEmpresas;

public class CtrlGestionarEmpresas implements MouseListener {
    
    // * Vistas
    private PanelGestionarEmpresas laVista;
    public JDialog modal;
    
    // * Modelos
    private EmpresaDao dao;
    private EmpresaDto dto;
    
    // Atributos
    private String cmpEmpresa;

    public CtrlGestionarEmpresas(PanelGestionarEmpresas laVista, EmpresaDao dao, EmpresaDto dto) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
        
        // * Definir oyentes
        this.laVista.btnBuscar.addMouseListener(this);
        this.laVista.btnCrear.addMouseListener(this);
        this.laVista.btnEliminar.addMouseListener(this);
        this.laVista.btnModificar.addMouseListener(this);
        
        
        mtdInit();
    }

    private void mtdInit() {
        modal = new JDialog();
        
        modal.setTitle("Gestionar empresas");
        modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnBuscar )
            mtdBuscarEmpresa();
        
        if( e.getSource() == laVista.btnCrear )
            mtdCrearEmpresa();
        
        if( e.getSource() == laVista.btnModificar )
            mtdModificarEmpresa();
        
        if( e.getSource() == laVista.btnEliminar )
            mtdEliminarEmpresa();
        
    }
    
    private void mtdBuscarEmpresa(){
        System.out.println("CtrlGestionarEmpresas - Buscar empresa [!]");
    }
    
    private void mtdCrearEmpresa(){
        
        if( mtdValidarCampo() ){
            System.out.println("CtrlGestionarEmpresas - Crear empresa [!]");
            dto.setCmpNombre( cmpEmpresa );
            
            if( dao.mtdInsetar(dto) ){
                JOptionPane.showMessageDialog(null, "La empresa `"+ dto.getCmpNombre() +"` se creo exitosamente.");
            }
            
        } else
        JOptionPane.showMessageDialog(null, "Verifica que los campo sea valido.");
        
    }
    
    private void mtdModificarEmpresa(){
        System.out.println("CtrlGestionarEmpresas - Modificar empresa [!]");
    }
    
    private void mtdEliminarEmpresa(){
        System.out.println("CtrlGestionarEmpresas - Eliminar empresa [!]");
    }
    
    private boolean mtdValidarCampo(){
        String campo = laVista.cmpEmpresa.getText().trim();
        
        if( campo.isEmpty() || !laVista.cmpEmpresa.isAprobado() ){
            return false;
        } else if( campo.length() > 20 ){
            JOptionPane.showMessageDialog(null, "El campo debe ser menor a 20 caracteres.");
            return false;
        }
        
        cmpEmpresa = campo;
        laVista.cmpEmpresa.setText(null);
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
  
}
