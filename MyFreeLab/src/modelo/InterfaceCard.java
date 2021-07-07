package modelo;

import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import vista.paneles.PanelCardProyectos;

public abstract class InterfaceCard {
    protected  String tipoTarjeta;
    protected  String tituloTarjeta;
    protected  Integer estadoTarjeta;
    
    // * Obtener las tarjetas disponibles 
    public abstract PanelCardProyectos mtdTarjetaDeProyecto();
    
    // * Obtener propiedades de la tarjeta
    public abstract GridBagConstraints mtdObtenerDimensionesTarjetas();
    public abstract String mtdObtenerTituloTarjeta();
    public abstract String mtdObtenerTipoTarjeta();
    public abstract Integer mtdObtenerEstadoTarjeta();
}
