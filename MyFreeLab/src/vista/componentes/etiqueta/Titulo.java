package vista.componentes.etiqueta;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import src.Source;

public class Titulo extends JLabel{
    
    public Titulo() {
        this.init();
    }

    private void init() {
        setText("Titulo");
        setFont( Source.fontLabelTitulo );
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
