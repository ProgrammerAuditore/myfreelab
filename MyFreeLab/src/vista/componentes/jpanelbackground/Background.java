package vista.componentes.jpanelbackground;

import java.awt.Image;
import java.io.File;

public class Background{
    
    private String imgRutaInterno;
    private File imgRutaExterno; 
    private Image imgBackground;
    private float imgOpacidad = 1.0f;

    public Background() {
    }
    
    public String getImgRutaInterno() {
        return imgRutaInterno;
    }

    public void setImgRutaInterno(String imgRutaInterno) {
        this.imgRutaInterno = imgRutaInterno;
    }

    public File getImgRutaExterno() {
        return imgRutaExterno;
    }

    public void setImgRutaExterno(File imgRutaExterno) {
        this.imgRutaExterno = imgRutaExterno;
    }

    public float getImgOpacidad() {
        return imgOpacidad;
    }

    public void setImgOpacidad(float imgOpacidad) {
        this.imgOpacidad = imgOpacidad;
    }
    
    public Image getImgBackground() {
        return imgBackground;
    }

    public void setImgBackground(Image imgBackground) {
        this.imgBackground = imgBackground;
    }
    
}
