package index;

import controlador.LogginController;
import modelo.dao.FreelancerDao;
import modelo.dto.FreelancerDto;
import vista.paneles.panel_loggin;
import vista.ventanas.Base;

public class Index {
    public static void main(String[] args) {
        System.out.println("Inicializando programa...");
        
        // Iniciar la ventana de loggin como ventana principal
        new LogginController( new Base(new panel_loggin()) ).abrir_ventana();
        
    }
}
