package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.MdlConexion;
import modelo.MdlPrincipal;
import modelo.ObjConexion;
import vista.paneles.PanelConexion;
import vista.ventanas.VentanaPrincipal;

public class CtrlPrincipal implements  ActionListener{
    
    private int TestId;
    private MdlPrincipal elModelo;
    private VentanaPrincipal laVista;
    private ObjConexion hconexion; 

    public CtrlPrincipal(MdlPrincipal elModelo, VentanaPrincipal laVista) {
        this.elModelo = elModelo;
        this.laVista = laVista;

        mtdInit();
    }

    private void mtdInit() {
        laVista.setLocationRelativeTo(null);
        laVista.bntSalir.addActionListener(this);
        laVista.btnConexion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if( e.getSource() == laVista.bntSalir )
            mtdSalirDelPrograma();
        
        if( e.getSource() == laVista.btnConexion )
            modalConfigurarConexion();
    }
    
    
    private void mtdSalirDelPrograma(){
        // * Método para cerrar el programa
        laVista.setVisible(false);
        laVista.dispose();
        System.exit(0);
        
    }

    private void modalConfigurarConexion() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelConexion vista = new PanelConexion();
        MdlConexion modelo = new MdlConexion();
        CtrlConexion controlador = new CtrlConexion(vista, modelo);
        controlador.modal.setLocationRelativeTo( laVista );
        controlador.modal.setVisible(true);
        
    }
    
    private void mtdTesting(String msg){
        System.out.println("ctrlPrincipal ::: " + msg + " ::: id [" + TestId + "]" );
        TestId++;
    }
    
}
