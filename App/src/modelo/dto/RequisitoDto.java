package modelo.dto;

public class RequisitoDto {
    
    private int cmpID;
    private String cmpNombre;
    private double cmpPrecio;

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

    public double getCmpPrecio() {
        return cmpPrecio;
    }

    public void setCmpPrecio(double cmpPrecio) {
        this.cmpPrecio = cmpPrecio;
    }
    
}
