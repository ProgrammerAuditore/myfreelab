package modelo.dto;

public class ProyectoDto {
    
    private int cmpID;
    private String cmpNombre;
    private String cmpFechaInicial;
    private String cmpFechaFinal;
    private double cmpCostoEstimado;
    private double cmpMontoAdelantado;

    public int getCmpID() {
        return cmpID;
    }

    public void setCmpID(int cmpID) {
        this.cmpID = cmpID;
    }

    public String getCmpNombre() {
        return cmpNombre;
    }

    public void setCmpNombre(String cmpNombre) {
        this.cmpNombre = cmpNombre;
    }

    public String getCmpFechaInicial() {
        return cmpFechaInicial;
    }

    public void setCmpFechaInicial(String cmpFechaInicial) {
        this.cmpFechaInicial = cmpFechaInicial;
    }

    public String getCmpFechaFinal() {
        return cmpFechaFinal;
    }

    public void setCmpFechaFinal(String cmpFechaFinal) {
        this.cmpFechaFinal = cmpFechaFinal;
    }

    public double getCmpCostoEstimado() {
        return cmpCostoEstimado;
    }

    public void setCmpCostoEstimado(double cmpCostoEstimado) {
        this.cmpCostoEstimado = cmpCostoEstimado;
    }

    public double getCmpMontoAdelantado() {
        return cmpMontoAdelantado;
    }

    public void setCmpMontoAdelantado(double cmpMontoAdelantado) {
        this.cmpMontoAdelantado = cmpMontoAdelantado;
    }
    
}
