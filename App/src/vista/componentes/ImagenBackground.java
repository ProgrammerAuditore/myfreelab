package vista.componentes;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.Serializable;
import java.net.URL;

public class ImagenBackground implements Serializable{
    
    // Propiedades
    private URL rutaURL;
    private File rutaFile;
    private float opacidad = 1.0f;
    
    // Constructores
    public ImagenBackground(String ruta) {
        this (new File(ruta));
    }
    
    public ImagenBackground(String ruta, float o) {
        this (new File(ruta), o);
    }
    
    public ImagenBackground(File ruta) {
        this.rutaFile = ruta;
        if (!this.rutaFile.getAbsoluteFile().exists()) {
            return;
        }
        this.fncVerificador();
    }
    
    public ImagenBackground(File ruta, float o) {
        this.rutaFile = ruta;
        this.opacidad = o;
        if (!this.rutaFile.getAbsoluteFile().exists()) {
            return;
        }
        this.fncVerificador();
    }
    
    public ImagenBackground(URL ruta, float o) {
        Image image = Toolkit.getDefaultToolkit().getImage(ruta);
        if (image == null) {
            return;
        }
        this.opacidad = o;
        this.rutaURL = ruta;
    }
    
    public ImagenBackground(URL ruta) {
        Image image = Toolkit.getDefaultToolkit().getImage(ruta);
        if (image == null) {
            return;
        }
        this.rutaURL = ruta;
    }
    
    // Métodos
    private void fncVerificador(){
        if( System.getProperty("os.name").equals("Windows 10") ){
            this.rutaFile = new File( this.rutaFile.getAbsolutePath().replace('\\', '/') );
        }
        
        String extension = this.rutaFile.getAbsolutePath();
        if( !(this.rutaFile.exists() || extension.contains(".png") || extension.contains(".jpg") || extension.contains(".jpeg") || extension.contains(".gif")) ){
            this.rutaFile = null;
        }
    }
    
    // Getters y Setters
    public float getOpacidad() {
        return opacidad;
    }

    public void setOpacidad(float opacidad) {
        this.opacidad = opacidad;
    }

    public URL getRutaURL() {
        return rutaURL;
    }

    public void setRutaURL(URL rutaURL) {
        this.rutaURL = rutaURL;
    }

    public File getRutaFile() {
        return rutaFile;
    }

    public void setRutaFile(File rutaFile) {
        this.rutaFile = rutaFile;
    }
    
}