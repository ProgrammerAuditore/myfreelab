package vista.componentes.jpanelbackground;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import src.Source;

/**
 *
 * @author victo
 */
public class JPanelBackground extends JPanel{
    
    private Background imgBackground = new Background();
    private boolean imgRutaInternoActivo = true;

    public JPanelBackground() {
        this.init();
    }
    
    private void init(){
        setBackground(null);
        setOpaque(true);
        setSize(new Dimension(200, 200));
        setPreferredSize(new Dimension(200, 200));
        setImgRutaInternoActivo(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(  imgBackground.getImgBackground() != null ){
            ImageIcon icOriginal = new ImageIcon(  imgBackground.getImgBackground().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH) );
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  imgBackground.getImgOpacidad() ));

            g.drawImage(icOriginal.getImage() , 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
        
    }

    public void setImgRutaInterno(String imgRutaInterno) {
        imgBackground.setImgRutaInterno( imgRutaInterno );
        Image icOrigina = Toolkit.getDefaultToolkit().getImage( getClass().getResource( imgBackground.getImgRutaInterno() ) );
        imgBackground.setImgBackground(icOrigina);
        //System.out.println("JPanelBackground::: Path = " + imgRutaInterno );
        //System.out.println("JPanelBackground::: Image = " + imgBackground.getImgBackground() );
        //repaint();
    }

    public void setImgRutaExterno(File imgRutaExterno) {
        imgBackground.setImgRutaExterno( imgRutaExterno );
        Image icOrigina = Toolkit.getDefaultToolkit().getImage( imgBackground.getImgRutaExterno().getAbsolutePath() );
        imgBackground.setImgBackground(icOrigina);
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
