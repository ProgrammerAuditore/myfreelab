package modelo.dto;

public class DatosPersonalesDto {
    
    private String cmpNombres;
    private String cmpApellidos;
    private String cmpDireccion;
    private String cmpTMovil;
    private String cmpCorreo;
    private int cmpID;

    public DatosPersonalesDto() {
    }

    public DatosPersonalesDto(String cmpNombres, String cmpApellidos, String cmpDireccion, String cmpTMovil, String cmpCorreo, int cmpID) {
        this.cmpNombres = cmpNombres;
        this.cmpApellidos = cmpApellidos;
        this.cmpDireccion = cmpDireccion;
        this.cmpTMovil = cmpTMovil;
        this.cmpCorreo = cmpCorreo;
        this.cmpID = cmpID;
    }

    public String getCmpNombres() {
        return cmpNombres;
    }

    public void setCmpNombres(String cmpNombres) {
        this.cmpNombres = cmpNombres;
    }

    public String getCmpApellidos() {
        return cmpApellidos;
    }

    public void setCmpApellidos(String cmpApellidos) {
        this.cmpApellidos = cmpApellidos;
    }

    public String getCmpDireccion() {
        return cmpDireccion;
    }

    public void setCmpDireccion(String cmpDireccion) {
        this.cmpDireccion = cmpDireccion;
    }

    public String getCmpTMovil() {
        return cmpTMovil;
    }

    public void setCmpTMovil(String cmpTMovil) {
        this.cmpTMovil = cmpTMovil;
    }

    public String getCmpCorreo() {
        return cmpCorreo;
    }

    public void setCmpCorreo(String cmpCorreo) {
        this.cmpCorreo = cmpCorreo;
    }

    public int getCmpID() {
        return cmpID;
    }

    public void setCmpID(int cmpID) {
        this.cmpID = cmpID;
    }

    @Override
    public String toString() {
        return "DatosPersonalesDto{" + "cmpNombres=" + cmpNombres + ", cmpApellidos=" 
                + cmpApellidos + ", cmpDireccion=" + cmpDireccion + ", cmpTMovil=" + cmpTMovil 
                + ", cmpCorreo=" + cmpCorreo + ", cmpID=" + cmpID + '}';
    }
    
}
