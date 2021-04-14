package src;

import java.awt.Font;
import java.io.File;
import modelo.Conexion;

public class Source {
    
    // * Recursos externos
    public static final File dataConexion = new Resource().dataConexion();
    
    // * Recursos internos
    public static final String bkgDefault = Rutas.pathBkgDefault; 
    public static final String iconDefault = Rutas.pathIconDefault;
    public static final String temasContenedor = Rutas.pahtContenedorThemes;
    public static final String fuentesContenedor = Rutas.pathContenedorFuentes;
    public static Conexion conn;
    public static Thread pEjecucion;
    
    // * Recursos del sistema
    // Fuentes
    private static String fontName = "K2D";
    public static final Font fontTextField = new MyFont( fontName ).changeStyle( MyFont.PLAIN, 12);
    public static final Font fontButton = new MyFont( fontName ).changeStyle( MyFont.BOLD, 14);
    public static final Font fontLabelEtiqueta = new MyFont( fontName ).changeStyle( MyFont.SEMI_BOLD, 14);
    public static final Font fontLabelTitulo = new MyFont( fontName ).changeStyle( MyFont.BLACK, 36);
    public static final Font fontLabelSubtitulo = new MyFont( fontName ).changeStyle( MyFont.LIGHT, 26);
    public static final Font fontLabelEnlace = new MyFont( fontName ).changeStyle( MyFont.ITALIC, 14);
    
    // Estilos
    public static final String styleButtonDefault = "bulma";
    
}