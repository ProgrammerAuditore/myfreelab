package vista.componentes.jpanelbackground;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import src.Source;

/**
 *
 * @author victo
 */
public class JPanelBackground extends JPanel{
    
    protected Background imgBackground = new Background();
    protected boolean imgRutaInternoActivo = true;

    public JPanelBackground() {
        this.init();
    }
    
    private void init(){
        setBackground(null);
        setOpaque(true);
        imgBackground.setImgRutaInternoActivo(true);
        imgBackground.setImgRutaInterno( Source.bkgLoggin );
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        ImageIcon ic = null;
        ImageIcon icOriginal = null;
        setImgRutaInternoActivo(imgRutaInternoActivo);
        
        // Verificar si el archivo es invocado por getClass().getResource()
        if(  imgBackground.getImgRutaInterno() != null && imgBackground.isImgRutaInternoActivo() ){
            
            //Toolkit.getDefaultToolkit().getImage(ruta)
            ic = new ImageIcon( getClass().getResource( imgBackground.getImgRutaInterno() ) );
            icOriginal = new ImageIcon( ic.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
//            Image icOrigina = Toolkit.getDefaultToolkit().getImage( getClass().getResource( imgBackground.getImgRutaInterno() ));
//            ic = new ImageIcon( icOrigina );
//            icOriginal = new ImageIcon( ic.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
           
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  imgBackground.getImgOpacidad() ));

            g.drawImage(icOriginal.getImage() , 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        // Verificar si el archivo es invocado por java.io.File
        }else if( imgBackground.getImgRutaExterno() != null && !imgBackground.isImgRutaInternoActivo() ){
            
           
            ic = new ImageIcon( imgBackground.getImgRutaExterno().getAbsolutePath() );
            icOriginal = new ImageIcon( ic.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, imgBackground.getImgOpacidad() ));

            g.drawImage(icOriginal.getImage() , 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            
        }
        
    }

    public void setImgRutaInterno(String imgRutaInterno) {
        imgBackground.setImgRutaInterno( imgRutaInterno );
        //repaint();
    }

    public void setImgRutaExterno(File imgRutaExterno) {
        imgBackground.setImgRutaExterno( imgRutaExterno );
        //repaint();
    }

    public void setImgOpacidad(float imgOpacidad) {
        imgBackground.setImgOpacidad( imgOpacidad );
        //repaint();
    }
    
    public void setImgRutaInternoActivo(boolean imgRutaInternoActivo) {
        this.imgRutaInternoActivo = imgRutaInternoActivo;
        imgBackground.setImgRutaInternoActivo( imgRutaInternoActivo );
        //repaint();
    }

}
