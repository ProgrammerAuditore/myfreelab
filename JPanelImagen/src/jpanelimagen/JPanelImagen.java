package jpanelimagen;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelImagen extends JPanel implements Serializable{
    
    private ImagenFondo imagenFondo;
    private boolean ratonPresionado = false;
    private Point puntoPresionado;
    private ArrastreListener arrastreEvento;
    
    // Método constructor
    public JPanelImagen(){
        this.addMouseListener( new MouseAdapter() {
            
            
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if(ratonPresionado){
                    Point  posicionActual  = e.getPoint();
                    if( Math.abs( puntoPresionado.x - posicionActual.x) > 50 ){
                        // Gesto de Arrastra
                        if( arrastreEvento != null  )
                            arrastreEvento.arrastre();
                    }
                }
                
                ratonPresionado = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ratonPresionado = true;
                puntoPresionado = e.getPoint();
            }
            
        });
    }
    
    public void addArrastreListener(ArrastreListener e){
        this.arrastreEvento = e;
    }
    
    public void removeArrastreListener(){
        this.arrastreEvento = null;
    }

    public ImagenFondo getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(ImagenFondo imagenFondo) {
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
