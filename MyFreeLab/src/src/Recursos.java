package src;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;

public class Recursos {
    
    // * Recursos externos
    public static final File dataRun(){ return new CargarRecurgos().dataRun(); }
    public static final File dataConexion(){ return new CargarRecurgos().dataConexion(); }
    public static final File dataPreferencias(){ return new CargarRecurgos().dataPreferencias(); }
    public static final Image imgIconoDefault(){ return new CargarRecurgos().iconoDefault(); }
    public static final HashMap<String, String> docCotizacionJasper(){ return new CargarRecurgos().docCotizacionJasper(); }
    //public static final HashMap<String, String> docInformeJasper = new CargarRecurgos().docInformeJasper();
    
    // * Recursos internos
    public static final String bkgDefault = Rutas.pathBkgDefault;
    public static final String bkgLogo = Rutas.pathIconoDefultApple;
    public static final String bkgPortada = Rutas.pathBkgPortada;
    public static final String bkgSplash = Rutas.pathBkgSplash;
    public static final String bkgAside = Rutas.pathBkgAside;
    public static final String bkgConexionOff = Rutas.pathBkgConexionOff;
    public static final String bkgConexionOn = Rutas.pathBkgConexionOn;
    public static final String imagenesContenedor = Rutas.pathContenedorImagenes;
    public static final String botonesContenedor = Rutas.pahtContenedorBotones;
    public static final String iconosContenedor = Rutas.pathContenedorIconos;
    public static final String fuentesContenedor = Rutas.pathContenedorFuentes;
    public static final String docVersionesXml = Rutas.pathDocumentoVersionesXml;
    
    // * Recursos del sistema
    // Fuentes
    private static String fontName = "K2D";
    public static Font fontTextField = new MyFont( fontName ).changeStyle( MyFont.PLAIN, 12);
    public static Font fontButton = new MyFont( fontName ).changeStyle( MyFont.BOLD, 14);
    public static Font fontLabelEtiqueta = new MyFont( fontName ).changeStyle( MyFont.SEMI_BOLD, 14);
    public static Font fontLabelTitulo = new MyFont( fontName ).changeStyle( MyFont.BLACK, 36);
    public static Font fontLabelSubtitulo = new MyFont( fontName ).changeStyle( MyFont.LIGHT, 26);
    public static Font fontLabelEnlace = new MyFont( fontName ).changeStyle( MyFont.ITALIC, 14);
    
    // Estilos
    public static String styleButtonDefault = "bulma";
    
    // Dimension de los paneles para el modal
    public static final Dimension tamDialogModal = new Dimension(850, 534);
    public static final Dimension tamDialogInfo = new Dimension(460, 444);
    
    // Informacion del sistema
    public static final String SistemaOs = ManagementFactory.getOperatingSystemMXBean().getName();
    public static final int PID = new CargarRecurgos().getPID();
    public static final boolean OsLinuxDeb = new CargarRecurgos().getLinux();
    public static final boolean OsWin = new CargarRecurgos().getWin();
    public static final String timeTmp = new CargarRecurgos().getTimeTmp();
    public static final String dirTemp = System.getProperty("java.io.tmpdir");
    public static final String dirHome = System.getProperty("user.home");
    
    public static void mtdCambiarFuente(String fontName){
        Recursos.fontTextField = new MyFont( fontName ).changeStyle( MyFont.PLAIN, 12);
        Recursos.fontButton = new MyFont( fontName ).changeStyle( MyFont.BOLD, 14);
        Recursos.fontLabelEtiqueta = new MyFont( fontName ).changeStyle( MyFont.SEMI_BOLD, 14);
        Recursos.fontLabelTitulo = new MyFont( fontName ).changeStyle( MyFont.BLACK, 36);
        Recursos.fontLabelSubtitulo = new MyFont( fontName ).changeStyle( MyFont.LIGHT, 26);
        Recursos.fontLabelEnlace = new MyFont( fontName ).changeStyle( MyFont.ITALIC, 14);
    }
            
}