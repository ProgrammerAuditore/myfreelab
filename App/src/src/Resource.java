package src;

import java.io.File;
import java.io.IOException;

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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    private String patString = null;
    private File objFile = null;
}
