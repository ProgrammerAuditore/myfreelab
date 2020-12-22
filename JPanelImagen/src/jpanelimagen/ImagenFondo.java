
package jpanelimagen;

import java.io.File;
import java.io.Serializable;

public class ImagenFondo implements Serializable{
    private File rutaImagen;
    private Float Opacity;

    public ImagenFondo(File rutaImagen, Float Opacity) {
        this.rutaImagen = rutaImagen;
        this.Opacity = Opacity;
        this.fncVerificador();
    }

    public File getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(File rutaImagen) {
        this.rutaImagen = rutaImagen;
        this.fncVerificador();
    }

    public Float getOpacity() {
        return Opacity;
    }

    public void setOpacity(Float Opacity) {
        this.Opacity = Opacity;
    }
    
    private void fncVerificador(){
        if( System.getProperty("os.name").equals("Windows 10") ){
            this.rutaImagen = new File( this.rutaImagen.getAbsolutePath().replace('\\', '/') );
        }
        
        String extension = this.rutaImagen.getAbsolutePath();
        if( !(this.rutaImagen.exists() || extension.contains(".png") || extension.contains(".jpg") || extension.contains(".jpeg") || extension.contains(".gif")) ){
            this.rutaImagen = null;
        }
    }
    
}