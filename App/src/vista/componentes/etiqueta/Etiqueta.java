package vista.componentes.etiqueta;

import java.awt.Font;
import javax.swing.JLabel;

public class Etiqueta extends JLabel{

    public Etiqueta() {
        this.init();
    }

    private void init() {
        setText("Titulo");
        setFont(new Font("Arial Rounded MT Bold", 0, 16));
    }

}
