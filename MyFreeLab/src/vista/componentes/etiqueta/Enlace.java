/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes.etiqueta;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import src.Recursos;

/**
 *
 * @author victo
 */
public class Enlace extends JLabel implements MouseListener{
    
    // Propiedades
    private boolean Clickeado = false;
    private Color colorClickeado = new Color(153, 0, 77);
    private Color colorHover = new Color(0, 102, 204);
    private Color colorNormal = new Color(0,0,153);
    private boolean linkAutoEnabled = false;
    
    // * Constructor
    public Enlace() {
        this.init();
    }
    
    // * Métodos
    private void init() {
        setText("Enlace");
        setForeground( colorNormal );
        setFont(Recursos.fontLabelEnlace );
        addMouseListener(this);
    }
    
    public void reset(){
        setForeground( colorNormal );
        setFont(Recursos.fontLabelEnlace );
        repaint();
    }

    // * Métodos sobreescritos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //this.setFont( Recursos.fontLabelEtiqueta.deriveFont( getFont().getStyle() , getFont().getSize() ) );
        
    }

    @Override
    public void setFont(Font font) {
        super.setFont(Recursos.fontLabelEtiqueta.deriveFont(font.getStyle(), font.getSize()) );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if( !isLinkAutoEnabled() ){
            setForeground(colorClickeado);
        }else{
            Clickeado = true;
            setForeground(colorClickeado);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setForeground(colorHover);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if( !isLinkAutoEnabled() ){
            if( Clickeado ){
                Clickeado = false;
                setForeground(colorNormal);
            }else{
                Clickeado = true;
                setForeground(colorClickeado);
            }
        }else{
            Clickeado = true;
            setForeground(colorClickeado);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if( !Clickeado )
            setForeground(colorHover);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if( !Clickeado )
            setForeground(colorNormal);
    }
    
    // * Getters y Setters
    public boolean isLinkAutoEnabled() {
        return linkAutoEnabled;
    }

    public void setLinkAutoEnabled(boolean linkAutoEnabled) {
        this.linkAutoEnabled = linkAutoEnabled;
    }
    
}
