package modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;

public class Conexion {
    
    private Connection conn = null;
    public static Conexion instance = null; // Singleton
    
    private Conexion(){
        try {
            ConexionDto objConn = new ConexionDao().obtener_datos();
            
            String url = "jdbc:mysql://" + objConn.getHost() + ":" + objConn.getPuerto() + "/" + objConn.getDatabase()+ "?useSSL=false";
            this.conn = (Connection) DriverManager.getConnection(url, objConn.getUsuario(), objConn.getPass());
            
        } catch (Exception e) {
            //System.out.println("Conexion :: No se establecio la conexión  a la base de datos...");
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
    
    public void destruir_conexion(){
        instance = null;
        conn = null;
    }
}
