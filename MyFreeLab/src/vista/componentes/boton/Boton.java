package vista.componentes.boton;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import vista.componentes.jpanelbackground.JPanelBackground;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import src.Recursos;

public class Boton extends JPanelBackground implements MouseListener{
    
    // Propiedades del componente
    private String texto;
    private JLabel titulo;
    private final Dimension tamahoDefult = new Dimension(120,30);
    private boolean autoDesactivado = true;
    private boolean botonPresionado = false;
    private String autoEstadoControl = "ModoNormal";
    
    private String imgButtonType = "primary";
    private String imgButtonTheme = Recursos.styleButtonDefault;
    private final String contenedor = Recursos.botonesContenedor;
    private String imgButtonActivado = null;
    private String imgButtonHover = null;
    
    
    public Boton() {
        this.init();
    }
    
    private void init() {
        // * Este metodo define las propiedades por defecto del componente
        super.setImgBackgroundEnabled(true);
        super.setImgRutaInternoActivo(true);
        
        // Establecer propieades para JLabel
        titulo = new JLabel("Titulo");
        texto = titulo.getText();
        //titulo.setFont( new Font("Arial Rounded MT Bold", 0, 16) );
        titulo.setFont(Recursos.fontButton );
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setSize( getWidth() , getHeight() );
        
        // Establecer propiedades del JPanel
        setBackground(new Color(0, 0, 0));
        setOpaque(false);
        //setLayout(new GridBagLayout()); 
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
            case "Bootstrap": imgButtonTheme = "Bootstrap"; break;
            case "Bulma": imgButtonTheme = "Bulma"; break;
            case "Oval": imgButtonTheme = "Oval"; break;
            
            default:  imgButtonTheme = "Bootstrap"; break;
        }
        
        // * Verificar, button_type
        switch(imgButtonType){
            case "primary": imgButtonType = "primary"; break;
            case "success": imgButtonType = "success"; break;
            case "warning": imgButtonType = "warning"; break;
            case "danger": imgButtonType = "danger"; break;
            case "info": imgButtonType = "info"; break;
            case "dark": imgButtonType = "dark"; break;
            case "peace": imgButtonType = "peace"; break;
            default:  imgButtonType = "primary"; break;
        }
        
        imgButtonActivado = contenedor + imgButtonTheme + "/" + imgButtonType + "/activado.png";
        imgButtonHover = contenedor + imgButtonTheme + "/" + imgButtonType + "/hover.png";
        setBackgroundButton(imgButtonActivado);
        
    }
    
    private void setBackgroundButton(String estado_button){
        super.setImgOpacidad(1.0f);
        super.setImgRutaInterno( estado_button );
        
        //System.out.println("Repintando en  Boton :: setBackgroundButton");
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
        
        //System.out.println("Repintando en  Boton :: setCambiarEstado");
        repaint();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); //To change body of generated methods, choose Tools | Templates.
        
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            setTemaButton();
            titulo.setForeground(new Color(255,255,255));
            super.setImgRutaInterno( imgButtonActivado );
            super.setImgOpacidad(1.0f);
            
        } else{
            setTemaButton();
            titulo.setForeground(new Color(220,220,220));
            super.setImgRutaInterno( imgButtonHover );
            super.setImgOpacidad(0.6f);
            addMouseListener(null);
            
        }
    }

    @Override
    public void paint(Graphics g) {
        // Este se comporta como si fuera un hilo
        super.paint(g);
        
        
        // Verificar, si boton esta deshabilitado
        if( !isEnabled() ){
            //setTemaButton();
            titulo.setForeground(new Color(220,220,220));
            super.setImgRutaInterno( imgButtonHover );
            super.setImgOpacidad(0.6f);
            addMouseListener(null);
        }
        
        
        //System.out.println("paint");
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        ////System.out.println("clickeado");
        
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            if( autoDesactivado ){
//                super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                super.setImgOpacidad(1.0f);
//                repaint();
                  setBackgroundButton( imgButtonActivado );

            }else{  }
        }
        
        //System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            botonPresionado = true;
            if( autoDesactivado ){
//                super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/hover.png");
//                super.setImgOpacidad(1.0f);
//                repaint();
                setBackgroundButton( imgButtonHover );

            }else{  }
        }
        
        //System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            botonPresionado = false;
            if( autoDesactivado ){
//                super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                super.setImgOpacidad(1.0f);
//                repaint();
                setBackgroundButton( imgButtonActivado );

            }else{ setCambiarEstado(); }
        }
        
        //System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            if( autoDesactivado ){
//                super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/hover.png");
//                super.setImgOpacidad(1.0f);
//                repaint();
                setBackgroundButton( imgButtonHover );
                
            }else{
                if( autoEstadoControl.equals("ModoNormal") ){
//                    super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/hover.png");
//                    super.setImgOpacidad(1.0f);
//                    repaint();
                    setBackgroundButton( imgButtonHover );
                    
                }
            }
        }
        
        //System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Verificar, si boton esta habilitado
        if( isEnabled() ){
            if( autoDesactivado ){

                if(!botonPresionado){
//                    super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                    super.setImgOpacidad(1.0f);
//                    repaint();
                    setBackgroundButton( imgButtonActivado );
                    
                }

            }else{
                
                if( autoEstadoControl.equals("ModoNormal") ){
//                    super.setImgRutaInterno("/shared/themes/bootstrap/buttons/info/activado.png");
//                    super.setImgOpacidad(1.0f);
//                    repaint();
                    setBackgroundButton( imgButtonActivado );
                    
                }
                
            }
        }
        
        //System.out.println("mouseExited");
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
