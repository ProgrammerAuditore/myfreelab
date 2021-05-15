/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ventanas;

import java.awt.Color;
import src.Source;

/**
 *
 * @author victor
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VistaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        // * Convertir en transparente el contendor de proyectos
        this.pnlContenedor.setOpaque(false);
        this.scpContenedor.setOpaque(false);
        this.scpContenedor.getViewport().setOpaque(false);
        
        // * Eliminar los bordes del contendor de proyectos
        this.pnlContenedor.setBorder(null);
        this.scpContenedor.setBorder(null);
        this.scpContenedor.getViewport().setBorder(null);
        
        // * Establece logo
        this.pnlLogo.setImgRutaInterno(Source.bkgLogo);
        
        // * Establece portada
        this.pnlPortada.setImgRutaInterno(Source.bkgPortada);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogo = new vista.componentes.jpanelbackground.JPanelBackground();
        campoTexto1 = new vista.componentes.campos.CampoTexto();
        jToolBar1 = new javax.swing.JToolBar();
        cmpProyectos = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmpEmpresas = new javax.swing.JLabel();
        scpContenedor = new javax.swing.JScrollPane();
        pnlContenedor = new javax.swing.JPanel();
        pnlPortada = new vista.componentes.jpanelbackground.JPanelBackground();
        titulo1 = new vista.componentes.etiqueta.Titulo();
        pnlOpciones = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        bntSalir = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        btnDatosPersonales = new javax.swing.JMenuItem();
        btnGestionarProyectos = new javax.swing.JMenuItem();
        btnGestionarEmpresas = new javax.swing.JMenuItem();
        btnGestionarRequisitos = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnConexion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlLogo.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogo.setImgBackgroundEnabled(true);
        pnlLogo.setImgBackgroundIn_Ex(false);
        pnlLogo.setImgRutaExterno(new java.io.File("C:\\Program Files\\NetBeans 8.2\\<Not Set>"));
        pnlLogo.setImgRutaInterno("");

        campoTexto1.setPlaceholder("Buscar proyectos y empresas");

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoTexto1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoLayout.createSequentialGroup()
                .addContainerGap(203, Short.MAX_VALUE)
                .addComponent(campoTexto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jToolBar1.setBackground(new java.awt.Color(255, 255, 204));
        jToolBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jToolBar1.setFloatable(false);

        cmpProyectos.setText("Proyectos : #");
        jToolBar1.add(cmpProyectos);

        jLabel1.setText("  |  ");
        jToolBar1.add(jLabel1);

        cmpEmpresas.setText("Empresa : #");
        jToolBar1.add(cmpEmpresas);

        javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
        pnlContenedor.setLayout(pnlContenedorLayout);
        pnlContenedorLayout.setHorizontalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1097, Short.MAX_VALUE)
        );
        pnlContenedorLayout.setVerticalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );

        scpContenedor.setViewportView(pnlContenedor);

        pnlPortada.setImgBackgroundEnabled(true);
        pnlPortada.setImgBackgroundIn_Ex(true);

        titulo1.setForeground(new java.awt.Color(255, 255, 255));
        titulo1.setText("INICIO");

        javax.swing.GroupLayout pnlPortadaLayout = new javax.swing.GroupLayout(pnlPortada);
        pnlPortada.setLayout(pnlPortadaLayout);
        pnlPortadaLayout.setHorizontalGroup(
            pnlPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPortadaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        pnlPortadaLayout.setVerticalGroup(
            pnlPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPortadaLayout.createSequentialGroup()
                .addContainerGap(185, Short.MAX_VALUE)
                .addComponent(titulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlOpciones.setBackground(new java.awt.Color(211, 211, 211));
        pnlOpciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnlOpcionesLayout = new javax.swing.GroupLayout(pnlOpciones);
        pnlOpciones.setLayout(pnlOpcionesLayout);
        pnlOpcionesLayout.setHorizontalGroup(
            pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );
        pnlOpcionesLayout.setVerticalGroup(
            pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        jMenu1.setText("Archivo");

        bntSalir.setText("Sallir");
        bntSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalirActionPerformed(evt);
            }
        });
        jMenu1.add(bntSalir);

        menuBar.add(jMenu1);

        menuEditar.setText("Editar");
        menuEditar.setEnabled(false);

        btnDatosPersonales.setText("Mis datos personales");
        menuEditar.add(btnDatosPersonales);

        btnGestionarProyectos.setText("Gestionar proyectos");
        menuEditar.add(btnGestionarProyectos);

        btnGestionarEmpresas.setText("Gestionar empresas");
        menuEditar.add(btnGestionarEmpresas);

        btnGestionarRequisitos.setText("Gestionar requisitos");
        menuEditar.add(btnGestionarRequisitos);

        menuBar.add(menuEditar);

        jMenu2.setText("Configurar");

        btnConexion.setText("Conexion");
        jMenu2.add(btnConexion);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1204, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addComponent(pnlOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpContenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnlPortada, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPortada, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scpContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalirActionPerformed
    }//GEN-LAST:event_bntSalirActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem bntSalir;
    public javax.swing.JMenuItem btnConexion;
    public javax.swing.JMenuItem btnDatosPersonales;
    public javax.swing.JMenuItem btnGestionarEmpresas;
    public javax.swing.JMenuItem btnGestionarProyectos;
    public javax.swing.JMenuItem btnGestionarRequisitos;
    private vista.componentes.campos.CampoTexto campoTexto1;
    public javax.swing.JLabel cmpEmpresas;
    public javax.swing.JLabel cmpProyectos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JMenuBar menuBar;
    public javax.swing.JMenu menuEditar;
    public javax.swing.JPanel pnlContenedor;
    private vista.componentes.jpanelbackground.JPanelBackground pnlLogo;
    private javax.swing.JPanel pnlOpciones;
    private vista.componentes.jpanelbackground.JPanelBackground pnlPortada;
    public javax.swing.JScrollPane scpContenedor;
    private vista.componentes.etiqueta.Titulo titulo1;
    // End of variables declaration//GEN-END:variables
}
