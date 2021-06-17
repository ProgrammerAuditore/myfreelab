package modelo.dto;

public class VinculacionDto {
    
    private int cmpID;
    private int cmpProID;
    private int cmpEmpID;
    private String cmpProNombre;
    private String cmpEmpNombre;

    public int getCmpID() {
        return cmpID;
    }

    public void setCmpID(int cmpID) {
        this.cmpID = cmpID;
    }

    public int getCmpProID() {
        return cmpProID;
    }

    public void setCmpProID(int cmpProID) {
        this.cmpProID = cmpProID;
    }

    public int getCmpEmpID() {
        return cmpEmpID;
    }

    public void setCmpEmpID(int cmpEmpID) {
        this.cmpEmpID = cmpEmpID;
    }

    public String getCmpProNombre() {
        return cmpProNombre;
    }

    public void setCmpProNombre(String cmpProNombre) {
        this.cmpProNombre = cmpProNombre;
    }

    public String getCmpEmpNombre() {
        return cmpEmpNombre;
    }

    public void setCmpEmpNombre(String cmpEmpNombre) {
        this.cmpEmpNombre = cmpEmpNombre;
    }
    
}
