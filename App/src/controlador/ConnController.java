package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import vista.paneles.p_conexion;

public class ConnController{
    
    // Atributos o campos
    private p_conexion mi_panel; // Vista
    private ConexionDao modelo; // Modelo
    private ConexionDto conn;
    
    // Constructores
    public ConnController() {
    }

    public ConnController(p_conexion mi_panel) {
        this.mi_panel = mi_panel;
    }
    
    // Métodos
    public void init() {
        // Establecer todo los eventos para Configurar conexión
        mi_panel.btnEstablecerConexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fncEstablecerConexion();
            }
        });
    }
    
    private void fncEstablecerConexion(){
        System.out.println("Iniciando... establecer conexión");
    }
    
    // Getters y Setters
    public p_conexion getMi_panel() {
        return mi_panel;
    }

    public void setMi_panel(p_conexion mi_panel) {
        this.mi_panel = mi_panel;
    }

}
