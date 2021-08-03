package modelo.dto;

import java.io.Serializable;

public class PreferenciaDto implements Serializable {
    
    private String idioma;
    private String fuente;
    private String estilo;

    public PreferenciaDto() {
        this.idioma = "Ingles";
        this.fuente = "K2D";
        this.estilo = "Bulma";
    }
    
    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return 
        "PreferenciasDto{ \n"+
                "Idioma: " + idioma + "\n" +
                "Fuentes: " + fuente + "\n" +
                "Estilos: " + estilo + "\n" +
        "}";
    }
    
}
