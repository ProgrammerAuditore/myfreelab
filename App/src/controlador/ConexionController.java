package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import src.Source;
import vista.paneles.PanelConexion;

public class ConexionController {

    // Atributos o campos
    private PanelConexion panel; // Vista
    private ConexionDao modelo; // Modelo
    private ConexionDto conn;

    // Constructores
    public ConexionController() {
    }

    public ConexionController(PanelConexion mi_panel) {
        this.panel = mi_panel;
    }

    // Métodos
    public void init() {
        /**
         * * Configuración por defecto del panel *
         */
        fncLookCerrarConexion();

        // Establecer todo los eventos para establecer conexión
        panel.btnEstablecerConexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (panel.btnEstablecerConexion.isEnabled()) {
                    fncEstablecerConexion();
                }
            }
        });

        // Establecer todo los eventos para cerrar conexión
        panel.btnCerrarConexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (panel.btnCerrarConexion.isEnabled()) {
                    fncCerrarConexion();
                }
            }
        });
    }

    public void fncCargarDatosConexion() {
        //System.out.println("Cargando los datos...");
        try {
            ConexionDao conexion = new ConexionDao();
            conn = conexion.obtener_conexion();

            panel.cmpUsuario.setText(conn.getUsuario());
            panel.cmpHost.setText(conn.getHost());
            panel.cmpDatabase.setText(conn.getDatabase());
            panel.cmpPuerto.setText("" + conn.getPuerto());
            panel.cmpContrasenha.setText(conn.getPass());

            if (conn.getPass().length() == 0) {
                panel.cmpContrasenha.setEditable(false);
                panel.cmpContrasenha.setEnabled(false);
                panel.cmpNull.setSelected(true);
            }

            if (Source.conn.getConn() != null) {
                Source.conn = Conexion.estado_conexion();
            }

            // ** Verificar el estado de conexión
            // ** Para habilitar el botón para establecer conexión o
            // ** Para habilitar el botón para cerrar conexión
            fncEstadoConexion();

        } catch (Exception e) {
            System.out.println("El archivo db.dat no tiene propiedades o no existe.");
        }
    }

    private void fncEstablecerConexion() {
        String password = String.valueOf(panel.cmpContrasenha.getPassword());

        if (panel.cmpDatabase.isAprobado() && panel.cmpHost.isAprobado()
                && panel.cmpPuerto.isAprobado() && panel.cmpUsuario.isAprobado()) {

            int puerto;
            try {
                puerto = Integer.parseInt(panel.cmpPuerto.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Verifica el puerto, por favor.");
                return;
            }

            String database = panel.cmpDatabase.getText();

            String host = panel.cmpHost.getText();
            String usuario = panel.cmpUsuario.getText();

            if (panel.cmpNull.isSelected()) {
                password = "";
            }

            // Guardar los datos en el fichero .dat
            ConexionDto conexion = new ConexionDto(puerto, host, database, usuario, password);
            modelo = new ConexionDao();
            modelo.regitrar_conexion(conexion);

            // * Obtener el estado de conexión
            Source.conn = Conexion.estado_conexion();
            fncEstadoConexion();

            // * Verificar la conexión 
            if (Source.conn.getConn() == null) {

                // * En caso de no ser exitoso
                JOptionPane.showMessageDialog(null, "Conexión no establecido.\nVerifique que los datos sean correctos.\nEl servidor esté en ejecución.");
                fncLookCerrarConexion();

            } else {

                // * En caso de ser exitoso
                fncLookEstablecerConexion();

            }

        } else {
            JOptionPane.showMessageDialog(null, "Verifica los campos, por favor.");
        }
    }

    private void fncCerrarConexion() {
        /* Cerrar la conexión */

        try {
            // Verificar si existe una conexion actual
            if (Source.conn.getConn() != null) {

                // Destruir la conexion actual
                Source.conn.destruir_conexion();

            }

            // Verificar que la conexión se cerro exitosamente
            if (Source.conn.getConn() == null) {
                JOptionPane.showMessageDialog(null, "La conexión se cerro exitosamente.");
                fncLookCerrarConexion();
            }

        } catch (Exception e) {
        }

    }

    private void fncEstadoConexion() {
        if (Source.conn.getConn() == null) {
            System.out.println("conn null = " + Source.conn.getConn());
            fncLookCerrarConexion();
        } else {
            System.out.println("conn abierto = " + Source.conn.getConn());
            fncLookEstablecerConexion();
        }

        Source.conn.cerrar_conexion();
    }

    public void fncLookCerrarConexion() {
        // * Habilitar el botón para establecer conexión
        panel.btnEstablecerConexion.setEnabled(true);
        fncCtlCampos(true);

        // * Deshabilitar los sig. componentes
        panel.panel_estado.setBackground(Color.red);
        panel.btnCerrarConexion.addMouseListener(null);
        panel.btnCerrarConexion.setEnabled(false);
    }

    public void fncLookEstablecerConexion() {
        // * Habilitar el botón para cerrar conexión
        panel.btnCerrarConexion.setEnabled(true);

        // * Deshabilitar los sig. componentes
        fncCtlCampos(false);
        panel.panel_estado.setBackground(Color.green);
        panel.btnEstablecerConexion.addMouseListener(null);
        panel.btnEstablecerConexion.setEnabled(false);
    }

    public void fncCtlCampos(boolean estado) {
        // * Deshabilitar / Habilitar todos los campos
        panel.cmpContrasenha.setEnabled(estado);
        panel.cmpDatabase.setEnabled(estado);
        panel.cmpHost.setEnabled(estado);
        panel.cmpPuerto.setEnabled(estado);
        panel.cmpUsuario.setEnabled(estado);
        panel.cmpNull.setEnabled(estado);
    }

    // Getters y Setters
    public PanelConexion getPanel() {
        return panel;
    }

    public void setPanel(PanelConexion panel) {
        this.panel = panel;
    }

}
