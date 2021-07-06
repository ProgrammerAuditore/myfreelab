package src;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;

public class Source {
    
    // * Recursos externos
    public static final File dataRun = new Resource().dataRun();
    public static final File dataConexion = new Resource().dataConexion();
    public static final Image imgIconoDefault = new Resource().iconoDefault();
    public static final HashMap<String, String> docReporte = new Resource().docJasper();
    
    // * Recursos internos
    public static final String bkgDefault = Rutas.pathBkgDefault;
    public static final String bkgLogo = Rutas.pathBkgLogo;
    public static final String bkgPortada = Rutas.pathBkgPortada;
    public static final String bkgSplash = Rutas.pathBkgSplash;
    public static final String bkgAside = Rutas.pathBkgAside;
    public static final String iconDefault = Rutas.pathIconDefault;
    public static final String temasContenedor = Rutas.pahtContenedorThemes;
    public static final String fuentesContenedor = Rutas.pathContenedorFuentes;
    public static final String docVersionesXml = Rutas.pathDocumentoVersionesXml;
    
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
    
    // Dimension de los paneles para el modal
    public static final Dimension tamDialogModal = new Dimension(850, 534);
    public static final Dimension tamDialogInfo = new Dimension(460, 444);
    
    public static final String SistemaOs = ManagementFactory.getOperatingSystemMXBean().getName();
    public static final int PID = new Resource().getPID();
    public static final boolean OsLinuxDeb = new Resource().getLinux();
    public static final boolean OsWin = new Resource().getWin();
    public static final String timeTmp = new Resource().getTimeTmp();
    public static final String dirTemp = System.getProperty("java.io.tmpdir");
    public static final String dirHome = System.getProperty("user.home");
    public static final String fechayHora = new Resource().fncObtenerFechaYHora(0);
            
}