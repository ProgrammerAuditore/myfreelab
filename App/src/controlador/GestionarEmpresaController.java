package controlador;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import src.Source;
import vista.paneles.PanelEmpresa;
import vista.paneles.PanelGestionarEmpresas;

public class GestionarEmpresaController {

    // Atributos o campos
    // Vista
    private PanelGestionarEmpresas panel;
    private PanelEmpresa panelCrearEmpresa;
    private JDialog modal;
    
    // Modelo
    private ConexionDao modelo; 
    private ConexionDto conn;

    // Constructores
    public GestionarEmpresaController() {
    }

    public GestionarEmpresaController(PanelGestionarEmpresas mi_panel) {
        this.panel = mi_panel;
    }

    // Métodos
    public void init() {
        try {
            modal = (JDialog) panel.getParent().getParent().getParent();
        } catch (Exception e) {}
       
       /**** Funciones que sirven inicializar componentes ****/
        
       /**** Funciones que sirven como oyentes ****/
       fncOyente_BtnCrear();
    }
    
    private void fncOyente_BtnCrear(){
        this.panel.btnCrear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                
                // Panel para crear una nueva empresa
                panelCrearEmpresa = new PanelEmpresa();
                panelCrearEmpresa.etqTitulo.setText("CREAR EMPRESA");
                panelCrearEmpresa.setSize(860, 540);
                panelCrearEmpresa.setBounds(0,0, 860, 540);
                panelCrearEmpresa.setLocation(0, 0);
                
                try {
                    
                   //System.out.println("Abriendo modal de crear empresa ...");
                    modal = (JDialog) panel.getParent().getParent().getParent();
                    modal.remove(panel);
                    modal.pack();
                    modal.validate();
                    modal.repaint();

                    modal.setContentPane(panelCrearEmpresa);
                    modal.pack();
                    modal.validate();
                    modal.repaint();
                    
                    mtdBtnAceptar_CrearEmpresa();
                    mtdBtnCancelar_CrearEmpresa();
                
                } catch (Exception err) {}
            }
        });
    }
    
    
    private void mtdBtnAceptar_CrearEmpresa(){
        panelCrearEmpresa.btnAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if( panelCrearEmpresa.cmpCorreo.isAprobado() && panelCrearEmpresa.cmpCEO.isAprobado() && panelCrearEmpresa.cmpTelMovil.isAprobado() &&
                panelCrearEmpresa.cmpNombreEmpresa.isAprobado() && panelCrearEmpresa.cmpDireccion.isAprobado() ){
                    modal.remove(panelCrearEmpresa);
                    modal.pack();
                    modal.validate();
                    modal.repaint();

                    modal.setContentPane(panel);
                    modal.pack();
                    modal.validate();
                    modal.repaint();
                    JOptionPane.showMessageDialog(null, "La nueva empresa se creo exitosamente.");
                }else{
                    JOptionPane.showMessageDialog(null, "Los datos son incorrectos.");
                }
            }
        });
    }
    
    private void mtdBtnCancelar_CrearEmpresa(){
        panelCrearEmpresa.btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                modal.remove(panelCrearEmpresa);
                modal.pack();
                modal.validate();
                modal.repaint();

                modal.setContentPane(panel);
                modal.pack();
                modal.validate();
                modal.repaint();
            }
        });
    }
   
    // Getters y Setters
    public PanelGestionarEmpresas getPanel() {
        return panel;
    }

    public void setPanel(PanelGestionarEmpresas panel) {
        this.panel = panel;
    }

}
