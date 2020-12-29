/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes.campotexto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author victo
 */
public class CampoTexto extends JTextField implements FocusListener{
    
    
    // * Propiedadades
    public String Placeholder = "Establezca un placeholder";
    private final Border BorderMargin = BorderFactory.createEmptyBorder(0, 10, 0, 10);
    private final Color borderColor = new Color(192, 192, 192);
    private final Color backgroundColor = new Color(255, 255, 255);
    private final Color cursorColor = new Color(255, 255, 0);
    private final Color textoColor = new Color(64, 64, 64);
    private final Color placeholderColor = new Color(192, 192, 192);
    private final Dimension tamahno = new Dimension(280, 27);
    public JLabel componenteDidireccional;
    
    public CampoTexto() {
        super(14);
        this.iniciar_propiedades();
    }
    
    private void iniciar_propiedades() {
        
        // * Personalizar los colores al seleccionar el texto
        setSelectionColor(Color.LIGHT_GRAY);
        setSelectedTextColor(Color.WHITE);
        
        // Establecer texto de ayuda
        setToolTip();
        
        // Fuentes
        // *  Times New Roman --- Links
        // *  Times New Roman --- Links
        // *  Times New Roman --- Links
        // * Personalizar el campo de texto
        setFont(new java.awt.Font("Candara", 0, 16)); // NOI18N
        //setHorizontalAlignment(javax.swing.JTextField.CENTER);
        //setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 204, 255), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), null));
        setBorderMargin( borderColor );
        setSize(tamahno);
        setPreferredSize(tamahno);
        getEstiloTextEstablecido();
        addFocusListener(this);

        System.out.println("Texto = " + getText());
        System.out.println("Placeholder = " + getPlaceholder());
        
    }
    
    // Métodos custom
    private void getEstiloTextEmpty(){
        setBackground( backgroundColor );
        setBorderMargin( Color.RED );
        setForeground( placeholderColor );
        setCaretColor( cursorColor );
    }
    
    private void getEstiloTextEscritura(){
        setBackground( backgroundColor );
        setBorderMargin( borderColor );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    private void getEstiloTextEstablecido(){
        setBackground( backgroundColor );
        setBorderMargin( borderColor );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    private void setBorderMargin(Color c){
        setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder( c ), BorderMargin ) );
    }
    
    private void setToolTip(){
        String EstiloToolTip = "<html><b><font color=white>" + getPlaceholder() + " aquí" + "</font></b></html>" ;
        setToolTipText( EstiloToolTip );
    }
       
    // Setters y Getters
    public String getPlaceholder() {
        return Placeholder;
    }

    public void setPlaceholder(String Placeholder) {
        this.Placeholder = Placeholder;
        setText(Placeholder);
        setToolTip();
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

    public JLabel getComponenteDidireccional() {
        return componenteDidireccional;
    }

    public void setComponenteDidireccional(JLabel componenteDidireccional) {
        this.componenteDidireccional = componenteDidireccional;
        setPlaceholder(componenteDidireccional.getText());
    }
    
}