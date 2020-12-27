/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ventanas;

import javax.swing.JPanel;
import src.Source;
import vista.componentes.ImagenBackground;

/**
 *
 * @author victo
 */
public class Base extends javax.swing.JFrame {

    /**
     * Creates new form Base
     */
    
    public Base(  ) {
    }   

    public Base(JPanel panel) {
        initComponents();
        
        // Posicionarlo al centro
        this.setLocationRelativeTo(null);
        
        //
        panel.setBounds(0, 0, this.panelContenedor.getWidth(), this.panelContenedor.getHeight());
        this.panelContenedor.add(panel );
        this.panelContenedor.validate();
        this.panelContenedor.repaint();
        this.panelContenedor = panel;
        
        // Establecer el fondo de la ventana
        this.panel_background.setImagen(Source.bkgLoggin );
        
        // ** Testing **
        System.out.println("Tamaño de la ventana = " + this.getBounds());
        System.out.println("Tamaño del panel = " + this.panelContenedor.getBounds());

    }   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_background = new vista.componentes.PanelBackground();
        panelContenedor = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panel_background.setBackground(new java.awt.Color(255, 255, 255));

        panelContenedor.setBackground(new java.awt.Color(255, 255, 255));
        panelContenedor.setOpaque(false);

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        panel_background.add(panelContenedor);
        panelContenedor.setBounds(40, 80, 940, 530);

        btnVolver.setText("Volver");
        panel_background.add(btnVolver);
        btnVolver.setBounds(889, 20, 100, 25);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_background, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_background, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Base().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnVolver;
    private javax.swing.JPanel panelContenedor;
    private vista.componentes.PanelBackground panel_background;
    // End of variables declaration//GEN-END:variables

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }
    
}
