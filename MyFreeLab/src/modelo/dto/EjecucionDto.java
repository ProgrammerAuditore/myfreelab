package modelo.dto;

public class EjecucionDto {
    
    private long id;
    private long pid;
    private int estado;
    private long tiempo_inicial;
    private long tiempo_ejecucion;
    private long tiempo_final;

    public EjecucionDto() {
        this.id = 0;
        this.pid = 0;
        this.estado = 0;
        this.tiempo_ejecucion = 0;
        this.tiempo_final = 0;
        this.tiempo_inicial = 0;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public long getTiempo_inicial() {
        return tiempo_inicial;
    }

    public void setTiempo_inicial(long tiempo_inicial) {
        this.tiempo_inicial = tiempo_inicial;
    }

    public long getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public void setTiempo_ejecucion(long tiempo_ejecucion) {
        this.tiempo_ejecucion = tiempo_ejecucion;
    }

    public long getTiempo_final() {
        return tiempo_final;
    }

    public void setTiempo_final(long tiempo_final) {
        this.tiempo_final = tiempo_final;
    }
    
}
