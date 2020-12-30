/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes.etiqueta;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import src.Source;

/**
 *
 * @author victo
 */
public class SubTitulo extends JLabel {
    
    public SubTitulo() {
        this.init();
    }

    private void init() {
        setText("Subtitulo");
        setFont(Source.fontLabelSubtitulo );
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
