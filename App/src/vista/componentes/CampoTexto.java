/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import static java.awt.SystemColor.text;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author victo
 */
public class CampoTexto extends JTextField implements FocusListener{
    
    
    // * Propiedadades
    private Shape shape;
    private String Placeholder = "Establezca un placeholder";
    
    public CampoTexto() {
        super(14);
        this.iniciar_propiedades();
    }
    
    private void iniciar_propiedades() {
        
        // * Personalizar los colores al seleccionar el texto
        setSelectionColor(Color.LIGHT_GRAY);
        setSelectedTextColor(Color.WHITE);
        
        String EstiloToolTip = "<html><b><font color=red>" + getToolTipText() + "</font></b></html>" ;
        setToolTipText( EstiloToolTip );
        
        // * Personalizar el campo de texto
        setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        setHorizontalAlignment(javax.swing.JTextField.CENTER);
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 204, 255), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), null));
        setMargin(new java.awt.Insets(2, 20, 2, 20));
        setPreferredSize(new Dimension(160, 27));
        getEstiloTextEstablecido();
        addFocusListener(this);

        System.out.println("Texto = " + getText());
        System.out.println("Placeholder = " + getPlaceholder());
        
    }
    
    // Métodos custom
    private void getEstiloTextEmpty(){
        setBackground(Color.RED);
        setForeground(Color.WHITE);
        setCaretColor(Color.YELLOW);
    }
    
    private void getEstiloTextEscritura(){
        setBackground(Color.WHITE);
        setForeground(Color.DARK_GRAY);
        setCaretColor(Color.RED);
    }
    
    private void getEstiloTextEstablecido(){
        setBackground(Color.DARK_GRAY);
        setForeground(Color.WHITE);
        setCaretColor(Color.RED);
    }
       
    // Setters y Getters
    public String getPlaceholder() {
        return Placeholder;
    }

    public void setPlaceholder(String Placeholder) {
        this.Placeholder = Placeholder;
        setText(Placeholder);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("Escribe");
        
        getEstiloTextEscritura();
        if( getText().equals( getPlaceholder() ) ){
            setText(null);
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        System.out.println("Ya no puedes escribir");
        
        
        if( getText().isEmpty() ){
            setText(getPlaceholder());
            getEstiloTextEmpty();
        }else{
            getEstiloTextEstablecido();
        }
    }
    
    
    
    
}