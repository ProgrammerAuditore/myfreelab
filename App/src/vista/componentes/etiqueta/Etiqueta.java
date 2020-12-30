package vista.componentes.etiqueta;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import src.Source;

public class Etiqueta extends JLabel{

    public Etiqueta() {
        this.init();
    }

    private void init() {
        setText("Etiqueta");
        setFont(Source.fontLabelEtiqueta );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setFont( Source.fontLabelEtiqueta.deriveFont( getFont().getStyle() , getFont().getSize() ) );
        
    }

    @Override
    public void setFont(Font font) {
        super.setFont( Source.fontLabelEtiqueta.deriveFont(font.getStyle(), font.getSize()) );
    }
 
}
