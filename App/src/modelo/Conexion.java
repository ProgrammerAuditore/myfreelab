package modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    private int puerto = 3306;
    private String host = "localhost";
    private String database = "freenlab";
    private String usuario = "root";
    private String pass = "";
    private Connection conn = null;
    public static Conexion instance = null; // Singleton
    
    private Conexion(){
        try {
            
            String url = "jdbc:mysql://" + this.host + ":" + this.puerto + "/" + this.database + "?useSSL=false";
            this.conn = (Connection) DriverManager.getConnection(url, this.usuario, this.pass);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public synchronized static Conexion estado_conexion() {
        if( instance == null ){
            instance = new Conexion();
        }
        return instance;
    }
        
    public Connection getConn() {
        return conn;
    }

    public void cerrar_conexion(){
        instance = null;
    }
    
}
