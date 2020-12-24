package modelo.dto;

import java.io.Serializable;

public class ConexionDto implements Serializable{
    private int puerto;
    private String host;
    private String database;
    private String usuario;
    private String pass;

    public ConexionDto() {
    }

    public ConexionDto(int puerto, String host, String database, String usuario, String pass) {
        this.puerto = puerto;
        this.host = host;
        this.database = database;
        this.usuario = usuario;
        this.pass = pass;
    }
    
    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "ConexionDto{" + "puerto=" + puerto + ", host=" + host + ", database=" + database + ", usuario=" + usuario + ", pass=" + pass + '}';
    }
    
}
