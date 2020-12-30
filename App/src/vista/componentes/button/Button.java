package vista.componentes.button;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import vista.componentes.jpanelbackground.JPanelBackground;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import src.Source;

public class Button extends JPanelBackground implements MouseListener{
    
    // Propiedades del componente
    private String texto;
    private JLabel titulo;
    private final Dimension tamahoDefult = new Dimension(120,30);
    private boolean autoDesactivado = true;
    private boolean botonPresionado = false;
    private String autoEstadoControl = "ModoNormal";
    
    private String imgButtonType = "primary";
    private String imgButtonTheme = Source.styleButtonDefault;
    private final String contenedor = Source.temasContenedor;
    private String imgButtonActivado = null;
    private String imgButtonHover = null;
    
    
    public Button() {
        this.init();
    }
    
    private void init() {
        // * Este metodo define las propiedades por defecto del componente
        
        // Establecer propieades para JLabel
        titulo = new JLabel("Titulo");
        texto = titulo.getText();
        //titulo.setFont( new Font("Arial Rounded MT Bold", 0, 16) );
        titulo.setFont( Source.fontButton );
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setSize( getWidth() , getHeight() );
        
        // Establecer propiedades del JPanel
        imgBackground.setImgRutaInternoActivo(true);
        setBackground(null);
        setOpaque(true);
        setLayout(new GridBagLayout()); 
        addMouseListener(this);
        setSize( tamahoDefult );
        setPreferredSize( tamahoDefult );
        add( titulo );
        
        // Propidades para el boton
        setTemaButton();
        
        
//        imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//        imgBackground.setImgOpacidad(1.0f);
//        repaint();
        setBackgroundButton( imgButtonActivado );
    }
    
    private void setTemaButton(){
        
        // * Verificar, buttom_theme
        switch(imgButtonTheme){
            case "bootstrap": imgButtonTheme = "bootstrap"; break;
            case "bulma": imgButtonTheme = "bulma"; break;
            case "oval": imgButtonTheme = "oval"; break;
            
            default:  imgButtonTheme = "bootstrap"; break;
        }
        
        // * Verificar, button_type
        switch(imgButtonType){
            case "primary": imgButtonType = "primary"; break;
            case "success": imgButtonType = "success"; break;
            case "warning": imgButtonType = "warning"; break;
            case "danger": imgButtonType = "danger"; break;
            case "info": imgButtonType = "info"; break;

            default:  imgButtonType = "primary"; break;
        }
        
        imgButtonActivado = contenedor + imgButtonTheme + "/" + imgButtonType + "/activado.png";
        imgButtonHover = contenedor + imgButtonTheme + "/" + imgButtonType + "/hover.png";
        setBackgroundButton(imgButtonActivado);
        
    }
    
    private void setBackgroundButton(String estado_button){
        imgBackground.setImgOpacidad(1.0f);
        imgBackground.setImgRutaInterno( estado_button );
        repaint();
    }
    
    private void setCambiarEstado(){
        
        // Verificars, si boton ha dejado de ser presionado
        if( botonPresionado == false ){
            if( autoEstadoControl.equals("ModoNormal") ){
                autoEstadoControl = "ModoHover";
                setBackgroundButton(imgButtonHover);

            }else if( autoEstadoControl.equals("ModoHover") ){
                autoEstadoControl = "ModoNormal";
                setBackgroundButton(imgButtonActivado);

            }
        }
        
        repaint();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); //To change body of generated methods, choose Tools | Templates.
        
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            setTemaButton();
            titulo.setForeground(new Color(255,255,255));
            imgBackground.setImgRutaInterno( imgButtonActivado );
            imgBackground.setImgOpacidad(1.0f);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // Verificar, si boton esta deshabilitado
        if( !isEnabled() ){
            setTemaButton();
            titulo.setForeground(new Color(200,200,200));
            imgBackground.setImgRutaInterno( imgButtonHover );
            imgBackground.setImgOpacidad(0.5f);
            addMouseListener(null);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("clickeado");
        
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            if( autoDesactivado ){
//                imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                imgBackground.setImgOpacidad(1.0f);
//                repaint();
                  setBackgroundButton( imgButtonActivado );

            }else{  }
        }
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            botonPresionado = true;
            if( autoDesactivado ){
//                imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/hover.png");
//                imgBackground.setImgOpacidad(1.0f);
//                repaint();
                setBackgroundButton( imgButtonHover );

            }else{  }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            botonPresionado = false;
            if( autoDesactivado ){
//                imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                imgBackground.setImgOpacidad(1.0f);
//                repaint();
                setBackgroundButton( imgButtonActivado );

            }else{ setCambiarEstado(); }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            if( autoDesactivado ){
//                imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/hover.png");
//                imgBackground.setImgOpacidad(1.0f);
//                repaint();
                setBackgroundButton( imgButtonHover );
                
            }else{
                if( autoEstadoControl.equals("ModoNormal") ){
//                    imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/hover.png");
//                    imgBackground.setImgOpacidad(1.0f);
//                    repaint();
                    setBackgroundButton( imgButtonHover );
                    
                }
            }
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            if( autoDesactivado ){

                if(!botonPresionado){
//                    imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                    imgBackground.setImgOpacidad(1.0f);
//                    repaint();
                    setBackgroundButton( imgButtonActivado );
                    
                }

            }else{
                
                if( autoEstadoControl.equals("ModoNormal") ){
//                    imgBackground.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                    imgBackground.setImgOpacidad(1.0f);
//                    repaint();
                    setBackgroundButton( imgButtonActivado );
                    
                }
                
            }
        }
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
        titulo.setText(texto);
    }
    
    public boolean isAutoDesactivado() {
        return autoDesactivado;
    }

    public void setAutoDesactivado(boolean autoDesactivado) {
        this.autoDesactivado = autoDesactivado;
    }

    public String getImgButtonType() {
        return imgButtonType;
    }

    public void setImgButtonType(String imgButtonType) {
        this.imgButtonType = imgButtonType;
        setTemaButton();
    }

    public String getImgButtonTheme() {
        return imgButtonTheme;
    }

    public void setImgButtonTheme(String imgButtonTheme) {
        this.imgButtonTheme = imgButtonTheme;
        setTemaButton();
    }
    
    
    
    
}
