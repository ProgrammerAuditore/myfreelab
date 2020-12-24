/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author victo
 */
public class PanelBackground extends JComponent{
    
    private ImagenBackground imagenFondo;
    
    public void setImagenFondo(ImagenBackground imagenFondo) {
        this.imagenFondo = imagenFondo;
        this.validate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if( imagenFondo != null ){
            if( imagenFondo.getRutaImagen().exists() && imagenFondo.getRutaImagen() != null ){
                
                ImageIcon ic = new ImageIcon( imagenFondo.getRutaImagen().getAbsolutePath() );
                ImageIcon icOriginal = new ImageIcon( ic.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
                
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, imagenFondo.getOpacity()));
                
                g.drawImage(icOriginal.getImage() , 0, 0, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            
            }
        }
    }    
    
}
