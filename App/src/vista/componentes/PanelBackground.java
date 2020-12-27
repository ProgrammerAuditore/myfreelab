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
    
    // Propiedades
    ImagenBackground imagen;
    
    // Constructores
    public PanelBackground() {
    }
    
    public PanelBackground(ImagenBackground imagen) {
        this.imagen = imagen;
        this.validate();
        this.repaint();
    }
    
    // Métodos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if( imagen != null ){
            
            ImageIcon ic = null;
            ImageIcon icOriginal = null;
               
            // Verificar si el archivo es invocado por getClass().getResource()
            if(imagen.getRutaURL() != null){
                
                ic = new ImageIcon( imagen.getRutaURL() );
                icOriginal = new ImageIcon( ic.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
            
            // Verificar si el archivo es invocado por java.io.File
            }else if( imagen.getRutaFile() != null ){
                
                ic = new ImageIcon( imagen.getRutaFile().getAbsolutePath() );
                icOriginal = new ImageIcon( ic.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
            
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, imagen.getOpacidad()));

            g.drawImage(icOriginal.getImage() , 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }

    // Getters y Setters
    public ImagenBackground getImagen() {
        return imagen;
    }

    public void setImagen(ImagenBackground imagen) {
        this.imagen = imagen;
    }
    
}
