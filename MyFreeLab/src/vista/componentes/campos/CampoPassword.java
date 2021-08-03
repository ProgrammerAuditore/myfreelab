package vista.componentes.campos;

import index.MyFreeLab;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import src.Recursos;

public class CampoPassword extends JPasswordField implements FocusListener{
    
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
    
    public CampoPassword() {
        super(14);
        this.iniciar_propiedades();
    }
    
    private void iniciar_propiedades() {
        
        // * Personalizar los colores al seleccionar el texto
        setSelectionColor(Color.LIGHT_GRAY);
        setSelectedTextColor(Color.WHITE);
        setEchoChar((char)0);
        
        // Establecer texto de ayuda
        setToolTip();
        
        // Fuentes
        setFont(Recursos.fontTextField );
        //setHorizontalAlignment(javax.swing.JTextField.CENTER);
        //setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 204, 255), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), null));
        setBorderMargin( borderColor );
        setSize(tamahno);
        setPreferredSize(tamahno);
        getEstiloTextEstablecido();
        addFocusListener(this);

        //System.out.println("Texto = " + String.valueOf(getPassword()));
        //System.out.println("Placeholder = " + getPlaceholder());
        
    }
    
    // Métodos custom
    private void getEstiloTextEmpty(){
        setEchoChar((char)0);
        setBackground( backgroundColor );
        setBorderMargin( Color.RED );
        setForeground( placeholderColor );
        setCaretColor( cursorColor );
    }
    
    private void getEstiloTextEscritura(){
        setEchoChar('*');
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
    
    public void getEstiloAprobado(){
        setBackground( backgroundColor );
        setBorderMargin( Color.GREEN );
        setForeground( placeholderColor );
        setCaretColor( cursorColor );
    }
    
    public void getEstiloNoAprobado(){
        setBackground( backgroundColor );
        setBorderMargin( Color.RED );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    private void setBorderMargin(Color c){
        setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder( c ), BorderMargin ) );
    }
    
    private void setToolTip(){
        String aqui= MyFreeLab.idioma.getProperty("componente.setToolTip.aqui");
        String EstiloToolTip = "<html><b><font color=white>" + getPlaceholder() + " "+aqui+ "</font></b></html>" ;
        setToolTipText( EstiloToolTip );
    }
    
    public void aceptarCampo(){
        getEstiloTextEstablecido();
    }
    
    public void rechazarCampo(){
        getEstiloTextEmpty();
        setText(getPlaceholder());
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
        getEstiloTextEscritura();
        if( String.valueOf(getPassword()).equals( getPlaceholder() ) ){
            setText(null);
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        if( String.valueOf(getPassword()).isEmpty() ){
            setText(getPlaceholder());
            getEstiloTextEmpty();
        }else{
            getEstiloAprobado();
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
