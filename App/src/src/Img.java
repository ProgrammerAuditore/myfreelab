package src;

import java.io.File;
import vista.componentes.ImagenBackground;

public class Img {
    
    // * Crear un constructor
    public Img() {}
    
    // * Métodos - Cargar todo los recusos
    public void cargar_backgrounds() {
        cargar_bckgLoggin();
    }
    
    // * Métodos - Cargas
    public void cargar_bckgLoggin(){
        try {
            Img.bckgLoggin.setRutaImagen(new File( getClass().getResource(pathLoggin ).getPath() ));
            Img.bckgLoggin.setOpacity(0.5f);
        } catch (Exception e) {
            System.err.println("El background loggin no tiene recurso");
            e.printStackTrace();
        }
    }
    
    // * Background - Rutas
    private String pathLoggin = "/storage/img/background_loggin.png";
    
    // * Background - Objetos
    public static final ImagenBackground bckgLoggin = new ImagenBackground();
    
}
