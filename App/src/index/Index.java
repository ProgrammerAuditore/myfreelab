package index;

import controlador.CtrlVentanaLoggin;
import modelo.dao.FreelancerDao;
import modelo.dto.FreelancerDto;
import vista.paneles.panel_loggin;
import vista.ventanas.Base;

public class Index {
    public static void main(String[] args) {
        System.out.println("Inicializando programa...");
        CtrlVentanaLoggin a1 = new CtrlVentanaLoggin( new Base(new panel_loggin()) );
    }
}
