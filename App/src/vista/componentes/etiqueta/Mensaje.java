package vista.componentes.etiqueta;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import src.Source;

public class Mensaje extends JLabel{

    public Mensaje() {
        this.init();
    }

    private void init() {
        setText("Mensaje");
        setFont(Source.fontTextField);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //this.setFont( Source.fontLabelEtiqueta.deriveFont( getFont().getStyle() , getFont().getSize() ) );
        
    }

    @Override
    public void setFont(Font font) {
        super.setFont( Source.fontLabelEtiqueta.deriveFont(font.getStyle(), font.getSize()) );
    }
 
}
