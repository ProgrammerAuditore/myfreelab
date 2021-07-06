package modelo.dto;

public class ProyectoDto {
    
    private Integer cmpID;
    private String cmpNombre;
    private String cmpFechaInicial;
    private String cmpFechaFinal;
    private Double cmpCostoEstimado;
    private Double cmpMontoAdelantado;
    private Integer cmpCtrlEstado;
    private String cmpCreadoEn;
    private String cmpActualizadoEn;

    public Integer getCmpID() {
        return cmpID;
    }

    public void setCmpID(Integer cmpID) {
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

    public Double getCmpCostoEstimado() {
        return cmpCostoEstimado;
    }

    public void setCmpCostoEstimado(Double cmpCostoEstimado) {
        this.cmpCostoEstimado = cmpCostoEstimado;
    }

    public Double getCmpMontoAdelantado() {
        return cmpMontoAdelantado;
    }

    public void setCmpMontoAdelantado(Double cmpMontoAdelantado) {
        this.cmpMontoAdelantado = cmpMontoAdelantado;
    }

    public Integer getCmpCtrlEstado() {
        return cmpCtrlEstado;
    }

    public void setCmpCtrlEstado(Integer cmpCtrlEstado) {
        this.cmpCtrlEstado = cmpCtrlEstado;
    }

    public String getCmpCreadoEn() {
        return cmpCreadoEn;
    }

    public void setCmpCreadoEn(String cmpCreadoEn) {
        this.cmpCreadoEn = cmpCreadoEn;
    }

    public String getCmpActualizadoEn() {
        return cmpActualizadoEn;
    }

    public void setCmpActualizadoEn(String cmpActualizadoEn) {
        this.cmpActualizadoEn = cmpActualizadoEn;
    }

    @Override
    public String toString() {
        String info = "\n"
                + "cmpID : " + cmpID + "\n"
                + "cmpNombre : " + cmpNombre + "\n"
                + "cmpCostoEstimado : " + cmpCostoEstimado + "\n"
                + "cmpFechaFinal : " + cmpFechaFinal + "\n"
                + "cmpFechaInicial : " + cmpFechaInicial + "\n"
                + "cmpCtrlEstado : " + cmpCtrlEstado + "\n"
                + "cmpActualizadoEn : " + cmpActualizadoEn + "\n"
                + "cmpCreadoEn : " + cmpCreadoEn + "\n";
        return info;
    }
    
}
