package controlador;

import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JDialog;
import modelo.dao.EmpresaDao;
import modelo.dto.EmpresaDto;
import vista.paneles.PanelGestionarEmpresas;

public class CtrlGestionarEmpresas {
    
    // * Vistas
    private PanelGestionarEmpresas laVista;
    public JDialog modal;
    
    // * Modelos
    private EmpresaDao dao;
    private EmpresaDto dto;

    public CtrlGestionarEmpresas(PanelGestionarEmpresas laVista, EmpresaDao dao, EmpresaDto dto) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
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
    
    
    
    
    
}
