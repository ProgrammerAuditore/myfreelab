package controlador;

import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;
import vista.paneles.p_conexion;

public class ConnController{
    
    // Atributos o campos
    private p_conexion mi_panel; // Vista
    private ConexionDao modelo; // Modelo
    private ConexionDto conn;

    public ConnController() {
    }

    public ConnController(p_conexion mi_panel) {
        this.mi_panel = mi_panel;
    }
    
    
    
}
