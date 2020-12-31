package src;

import java.awt.Font;
import java.io.File;

public class Source {
    
    // * Recursos externos
    public static final File dataConexion = new Resource().dataConexion();
    
    // * Recursos internos
    public static final String bkgDefault = Rutas.pathBkgDefault;   
    public static final String temasContenedor = Rutas.pahtContenedorThemes;
    public static final String fuentesContenedor = Rutas.pathContenedorFuentes;
    
    // * Recursos del sistema
    // Fuentes
    private static String fontName = "BaiJamjuree";
    public static final Font fontTextField = new MyFont( fontName ).changeStyle( MyFont.PLAIN, 14);
    public static final Font fontButton = new MyFont( fontName ).changeStyle( MyFont.BOLD, 14);
    public static final Font fontLabelEtiqueta = new MyFont( fontName ).changeStyle( MyFont.SEMI_BOLD, 16);
    public static final Font fontLabelTitulo = new MyFont( fontName ).changeStyle( MyFont.BLACK, 36);
    public static final Font fontLabelSubtitulo = new MyFont( fontName ).changeStyle( MyFont.LIGHT, 26);
    public static final Font fontLabelEnlace = new MyFont( fontName ).changeStyle( MyFont.ITALIC, 16);
    
    // Estilos
    public static final String styleButtonDefault = "bulma";
    
}