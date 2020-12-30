package src;

import java.awt.Font;
import java.io.File;

public class Source {
    
    // * Recursos externos
    public static final File dataConexion = new Resource().dataConexion();
    
    // * Recursos internos
    public static final String bkgLoggin = Rutas.pathBkgLoggin;   
    public static final String temasContenedor = Rutas.pahtContenedorThemes;
    public static final String fuentesContenedor = Rutas.pathContenedorFuentes;
    
    // * Recursos del sistema
    public static final Font fontTextField = new MyFont("Poppins").changeStyle(MyFont.PLAIN, 16);
    public static final Font fontButton = new MyFont("Nunito").changeStyle(MyFont.BOLD, 16);
    public static final Font fontLabelEtiqueta = new MyFont("Quicksand").changeStyle(MyFont.BOLD, 18);
    public static final Font fontLabelTitulo = new MyFont("Barlow").changeStyle(MyFont.PLAIN, 36);
    public static final Font fontLabelSubtitulo = new MyFont("Quicksand").changeStyle(MyFont.PLAIN, 26);
    public static final Font fontLabelEnlace = new MyFont("Montserrat").changeStyle(MyFont.ITALIC, 18);
    
}