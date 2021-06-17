/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.paneles;

import java.awt.Dimension;
import src.Info;
import src.Source;

/**
 *
 * @author victo
 */
public class PanelAcercaDe extends javax.swing.JPanel {
    
    
    
    /**
     * Creates new form panel_acerca_de
     */
    public PanelAcercaDe() {
        initComponents();
    
        // Establecer propiedades para el panel
        Dimension tamahno = new Dimension(Source.tamDialogInfo);
        setSize(tamahno);
        setPreferredSize(tamahno);
        btnAceptar.setEnabled(false);
        
        // Establecer información para la licencia
        String licencia = cmpLicencia.getText();
        etqSoftware.setText( Info.NombreSoftware );
        etqLicencia.setText( Info.Licencia );
        msgAutor.setText( Info.Autor );
        licencia = licencia.replace("<name of author>", Info.Autor );
        licencia = licencia.replace("<name of program>", Info.sNombre );
        licencia = licencia.replace("<version of program>", Info.sVersionName + Info.sProduccion );
        licencia = licencia.replace("<year>", Info.Anho );
        licencia = licencia.replace("<description>", Info.Descripcion );
        licencia = licencia.replace("<details>", Info.Detalle);
        cmpLicencia.setText(licencia);
        cmpLicencia.setCaretPosition(0);
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAceptar = new vista.componentes.boton.Boton();
        contendor_licencia = new javax.swing.JScrollPane();
        cmpLicencia = new javax.swing.JTextArea();
        etqSoftware = new vista.componentes.etiqueta.Etiqueta();
        etqLicencia = new vista.componentes.etiqueta.Enlace();
        msgAutor = new vista.componentes.etiqueta.Mensaje();

        btnAceptar.setImgButtonType("danger");
        btnAceptar.setTexto("Aceptar");
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAceptarMouseReleased(evt);
            }
        });

        cmpLicencia.setEditable(false);
        cmpLicencia.setColumns(20);
        cmpLicencia.setFont(new java.awt.Font("Consolas", 0, 10)); // NOI18N
        cmpLicencia.setLineWrap(true);
        cmpLicencia.setRows(5);
        cmpLicencia.setText("Copyright (C) <year>  <name of author>\n\n<name of program> <version of program>\n\n<description>\n<details>\n\nEste programa es software libre: puede redistribuirlo y/o modificarlo bajo los términos de la \nLicencia General Pública de GNU publicada por la Free Software Foundation, \nya sea la versión 3 de la Licencia, o (a su elección) cualquier versión posterior.\n\nEste programa se distribuye con la esperanza de que sea útil pero SIN\nNINGUNA GARANTÍA; incluso sin la garantía implícita de MERCANTIBILIDAD o\nCALIFICADA PARA UN PROPÓSITO EN PARTICULAR. Vea la Licencia General Pública\nde GNU para más detalles.\n\nUsted ha debido de recibir una copia de la Licencia General Pública\nde GNU junto con este programa. Si no, vea <http://www.gnu.org/licenses/>.");
        cmpLicencia.setWrapStyleWord(true);
        cmpLicencia.setCaretPosition(0);
        cmpLicencia.setMargin(new java.awt.Insets(10, 10, 10, 10));
        contendor_licencia.setViewportView(cmpLicencia);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etqSoftware, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(msgAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 247, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contendor_licencia)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 172, Short.MAX_VALUE)
                                .addComponent(etqLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqSoftware, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(msgAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(etqLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contendor_licencia, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public vista.componentes.boton.Boton btnAceptar;
    public javax.swing.JTextArea cmpLicencia;
    public javax.swing.JScrollPane contendor_licencia;
    private vista.componentes.etiqueta.Enlace etqLicencia;
    private vista.componentes.etiqueta.Etiqueta etqSoftware;
    private vista.componentes.etiqueta.Mensaje msgAutor;
    // End of variables declaration//GEN-END:variables
}