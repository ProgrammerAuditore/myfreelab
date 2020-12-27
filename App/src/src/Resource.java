package src;

import java.io.File;
import java.io.IOException;
import vista.componentes.ImagenBackground;

class Resource {

    public Resource() {
    }
   
    public File dataConexion() {
        File data = null;
        try {
            patString = Rutas.pathDataConexion;
            objFile = new File( patString );
            
            // Si el archivo .dat no existe, se crea
            if( !objFile.exists() ){
                objFile.createNewFile();
            }
            
            data = objFile;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public ImagenBackground bkgLoggin() {
        ImagenBackground bckg = null;
        try {
            patString = Rutas.pathBkgLoggin;
            bckg = new ImagenBackground( getClass().getResource(patString) );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bckg;
    }
    
    private String patString = null;
    private File objFile = null;
}
