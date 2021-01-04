package src;

import java.io.File;

class Resource {

    public File dataConexion() {
        return new File( getClass().getResource(Rutas.pathDataConexion).getFile() );
    }
    
}
