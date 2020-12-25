package index;

import controlador.InicioController;
import controlador.LogginController;
import modelo.dao.FreelancerDao;
import modelo.dto.FreelancerDto;
import vista.paneles.panel_inicio;
import vista.paneles.panel_loggin;
import vista.ventanas.VentanaInicio;

public class Index {
    public static void main(String[] args) {
        System.out.println("Inicializando programa...");
        
        // Iniciar la ventana de loggin como ventana principal
        //new LogginController( new Base(new panel_loggin()) ).abrir_ventana();
        new InicioController( new VentanaInicio(new panel_inicio()) ).abrir_ventana();
        
        
    }
}
