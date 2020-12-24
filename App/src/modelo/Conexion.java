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
    
    public Connection getConexion(){
        
        try {
            
            String url = "jdbc:mysql://" + this.host + ":" + this.puerto + "/" + this.database + "?useSSL=false";
            this.conn = (Connection) DriverManager.getConnection(url, this.usuario, this.pass);
            
            return this.conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public void cerrar_conexion(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
