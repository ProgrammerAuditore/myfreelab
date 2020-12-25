package index;

import controlador.InicioController;
import controlador.LogginController;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.FreelancerDao;
import modelo.dto.FreelancerDto;
import vista.paneles.panel_inicio;
import vista.paneles.panel_loggin;
import vista.ventanas.VentanaInicio;

public class Index {
    public static void main(String[] args) {
        try {
            System.out.println("Inicializando programa...");
            new File( "src/source/config/db.dat" ).createNewFile();
            
            
            
            // Iniciar la ventana de loggin como ventana principal
            //new LogginController( new Base(new panel_loggin()) ).abrir_ventana();
            new InicioController( new VentanaInicio(new panel_inicio()) ).abrir_ventana();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
