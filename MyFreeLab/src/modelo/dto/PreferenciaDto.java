package modelo.dto;

import java.io.Serializable;

public class PreferenciaDto implements Serializable {
    
    private String idioma;
    private String fuentes;
    private String estilos;

    public PreferenciaDto() {
        this.idioma = "esp";
        this.fuentes = "K2D";
        this.estilos = "bulma";
    }
    
    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFuentes() {
        return fuentes;
    }

    public void setFuentes(String fuentes) {
        this.fuentes = fuentes;
    }

    public String getEstilos() {
        return estilos;
    }

    public void setEstilos(String estilos) {
        this.estilos = estilos;
    }

    @Override
    public String toString() {
        return 
        "PreferenciasDto{ \n"+
                "Idioma: " + idioma + "\n" +
                "Fuentes: " + fuentes + "\n" +
                "Estilos: " + estilos + "\n" +
        "}";
    }
    
}
