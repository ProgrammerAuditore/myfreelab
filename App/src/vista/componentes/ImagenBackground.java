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
    private transient Image image;
    
    // Constructores
    public ImagenBackground(String ruta) {
        this.rutaFile = new File(ruta);
        this.fncVerificador();
    }
    
    public ImagenBackground(URL ruta) {
        image = Toolkit.getDefaultToolkit().getImage(ruta);
        if (image == null) {
            return;
        }
        this.rutaURL = ruta;
    }
    
    public ImagenBackground(String ruta, float o) {
        this.rutaFile = new File(ruta);
        this.opacidad = o;
        this.fncVerificador();
    }
    
    public ImagenBackground(URL ruta, float o) {
        image = Toolkit.getDefaultToolkit().getImage(ruta);
        if (image == null) {
            return;
        }
        this.opacidad = o;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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