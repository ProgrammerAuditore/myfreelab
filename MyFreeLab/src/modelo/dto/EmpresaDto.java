package modelo.dto;

public class EmpresaDto {
    
    private int cmpID;
    private String cmpNombre;
    private String cmpDireccion;
    private String cmpCorreo;
    private String cmpTMovil;

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

    public String getCmpDireccion() {
        return cmpDireccion;
    }

    public void setCmpDireccion(String cmpDireccion) {
        this.cmpDireccion = cmpDireccion;
    }

    public String getCmpCorreo() {
        return cmpCorreo;
    }

    public void setCmpCorreo(String cmpCorreo) {
        this.cmpCorreo = cmpCorreo;
    }

    public String getCmpTMovil() {
        return cmpTMovil;
    }

    public void setCmpTMovil(String cmpTMovil) {
        this.cmpTMovil = cmpTMovil;
    }
    
}
