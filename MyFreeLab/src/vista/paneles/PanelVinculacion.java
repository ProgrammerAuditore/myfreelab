/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.paneles;

import index.MyFreeLab;
import java.awt.Color;
import src.Recursos;

/**
 *
 * @author victo
 */
public class PanelVinculacion extends javax.swing.JPanel {

    /**
     * Creates new form p_mis_datos
     */
    public PanelVinculacion() {
        initComponents();
        
        // * Definir tamaños por defecto 
        this.setSize(Recursos.tamDialogModal );
        this.setPreferredSize(Recursos.tamDialogModal );
        
        // * Definir el background
        bkgAside.setImgBackgroundEnabled(true);
        bkgAside.setImgRutaInternoActivo(true);
        bkgAside.setImgRutaInterno(Recursos.bkgAside );
        mtdEstablecerIdioma();
        
    }
    
    private void mtdEstablecerIdioma(){
        
        // * Etiquetas
        this.etqTitulo.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqTitulo"));
        this.etqAsociar.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqAsociar"));
        this.etqDesvincular.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqDesvincular"));
        this.etqProyectos.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqProyectos"));
        this.etqEmpresas.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqEmpresas"));
        this.etqEmpresasAsociadas.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqEmpresasAsociadas"));
        this.etqInstruccion1.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqInstruccion1"));
        this.etqInstruccion2.setText(MyFreeLab.idioma.getProperty("panelVinculacion.etqInstruccion2"));
        
        // * Botones
        this.btnCancelar.setTexto(MyFreeLab.idioma.getProperty("panelVinculacion.btnCancelar"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        enlace1 = new vista.componentes.etiqueta.Enlace();
        bkgAside = new vista.componentes.jpanelbackground.JPanelBackground();
        jPanel1 = new javax.swing.JPanel();
        etqTitulo = new vista.componentes.etiqueta.Titulo();
        jPanel2 = new javax.swing.JPanel();
        etqProyectos = new vista.componentes.etiqueta.Etiqueta();
        etqEmpresasAsociadas = new vista.componentes.etiqueta.Etiqueta();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstEmpresas = new javax.swing.JList<>();
        cmbProyectos = new javax.swing.JComboBox<>();
        etqInstruccion1 = new javax.swing.JLabel();
        etqAsociar = new vista.componentes.etiqueta.Enlace();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstEmpresasAsociadas = new javax.swing.JList<>();
        etqDesvincular = new vista.componentes.etiqueta.Enlace();
        etqEmpresas = new vista.componentes.etiqueta.Etiqueta();
        etqInstruccion2 = new javax.swing.JLabel();
        btnCancelar = new vista.componentes.boton.Boton();

        bkgAside.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout bkgAsideLayout = new javax.swing.GroupLayout(bkgAside);
        bkgAside.setLayout(bkgAsideLayout);
        bkgAsideLayout.setHorizontalGroup(
            bkgAsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );
        bkgAsideLayout.setVerticalGroup(
            bkgAsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        etqTitulo.setForeground(new java.awt.Color(255, 255, 255));
        etqTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqTitulo.setText("VINCULACION");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(MyFreeLab.idioma.getProperty("panelVinculacion.etqTituloPanel")));

        etqProyectos.setText("Selecciona un proyecto");

        etqEmpresasAsociadas.setText("Empresas asociadas");

        lstEmpresas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Ninguno" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lstEmpresas);

        cmbProyectos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno" }));
        cmbProyectos.setToolTipText("Lista de proyectos creados");

        etqInstruccion1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        etqInstruccion1.setText("Selecciona una empresa para asociar a un proyecto.");

        etqAsociar.setText("Asociar");

        lstEmpresasAsociadas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Ninguno" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstEmpresasAsociadas.setFocusable(false);
        jScrollPane1.setViewportView(lstEmpresasAsociadas);

        etqDesvincular.setText("Desvincular");

        etqEmpresas.setText("Lista de empresas");

        etqInstruccion2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        etqInstruccion2.setText("Seleciona una empresa para desvincular.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbProyectos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etqAsociar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etqInstruccion1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etqProyectos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etqEmpresas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(etqInstruccion2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(etqDesvincular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(etqEmpresasAsociadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqProyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etqEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etqEmpresasAsociadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etqInstruccion1)
                    .addComponent(etqInstruccion2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etqAsociar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etqDesvincular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnCancelar.setImgButtonType("secondary");
        btnCancelar.setTexto("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bkgAside, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkgAside, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private vista.componentes.jpanelbackground.JPanelBackground bkgAside;
    public vista.componentes.boton.Boton btnCancelar;
    public javax.swing.JComboBox<String> cmbProyectos;
    private vista.componentes.etiqueta.Enlace enlace1;
    public vista.componentes.etiqueta.Enlace etqAsociar;
    public vista.componentes.etiqueta.Enlace etqDesvincular;
    private vista.componentes.etiqueta.Etiqueta etqEmpresas;
    private vista.componentes.etiqueta.Etiqueta etqEmpresasAsociadas;
    private javax.swing.JLabel etqInstruccion1;
    private javax.swing.JLabel etqInstruccion2;
    private vista.componentes.etiqueta.Etiqueta etqProyectos;
    private vista.componentes.etiqueta.Titulo etqTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JList<String> lstEmpresas;
    public javax.swing.JList<String> lstEmpresasAsociadas;
    // End of variables declaration//GEN-END:variables
}
