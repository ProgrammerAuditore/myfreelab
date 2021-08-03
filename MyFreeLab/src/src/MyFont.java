/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author victo
 */
public class MyFont {
    
    // Establecer estilos de fuente
    public static final int PLAIN = 0; // Regular
    public static final int BOLD = 1; // Bold
    public static final int ITALIC = 2; // Italic
    public static final int SEMI_BOLD = 3; // SemiBold
    public static final int BLACK = 4; // Black
    public static final int LIGHT = 5; // Light
    
    // Propiedades
    private Font font = null;
    private String fuente_path = null;
    private String fuente_name = null;
    private String fuente_style = null;
    
    // Constructor
    public MyFont(String name) {
        fuente_name = name;
        fuente_style = "Regular";
        initFuente();
    }

    // Métodos
    private void initFuente() {
        fuente_path = Recursos.fuentesContenedor + fuente_name + "/" +fuente_name+ "-"+fuente_style+".ttf";
        ////System.out.println("fuente_path = " + fuente_path);
        try {
            
            InputStream is = getClass().getResourceAsStream(fuente_path);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(Font.PLAIN, 12);
            
        } catch (FontFormatException | IOException e) {
            //System.err.println("Warining: La siguiente fuente no se pudo cargar [\"" +fuente_name+ "-"+fuente_style+".ttf" + "\"]");
            font = new Font("Arial", Font.PLAIN, 12);
        }
    }

    public Font changeStyle(int estilo, int tamanho) {
        ////System.out.println("Nombre del fuente = " + font.getName());
        
        if( tamanho <= 0 ){ tamanho = 12; }
        
        if( font.getName().contains("Arial") ){
            return new Font("Arial", estilo, tamanho);
        }else{
            
            switch(estilo){
                case MyFont.PLAIN: fuente_style = "Regular"; break;
                case MyFont.BOLD: fuente_style = "Bold"; break;
                case MyFont.ITALIC: fuente_style = "Italic"; break;
                case MyFont.SEMI_BOLD: fuente_style = "SemiBold"; break;
                case MyFont.BLACK: fuente_style = "Black"; break;
                case MyFont.LIGHT: fuente_style = "Light"; break;
                default: fuente_style = "Regular"; break;
            }
            
            initFuente();
            return font.deriveFont(estilo, tamanho);
        }
    }

    public Font getFont() {
        return font;
    }
    
}
