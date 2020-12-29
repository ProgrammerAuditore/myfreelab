package vista.componentes.jpanelbackground;

import java.io.File;

public class Background{
    
    private String imgRutaInterno;
    private File imgRutaExterno;
    private float imgOpacidad = 1.0f;
    private boolean imgRutaInternoActivo = true;

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

    public boolean isImgRutaInternoActivo() {
        return imgRutaInternoActivo;
    }

    public void setImgRutaInternoActivo(boolean imgRutaInternoActivo) {
        this.imgRutaInternoActivo = imgRutaInternoActivo;
    }
    
}
