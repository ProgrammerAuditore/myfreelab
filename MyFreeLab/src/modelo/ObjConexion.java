package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import modelo.dto.ConexionDto;

public class ObjConexion {
    
    private Connection conn;
    private ConexionDto datos;

    public ObjConexion(ConexionDto datos) {
        this.datos = datos;
    }
    
    public void mtdEstablecerConexion() throws SQLException, ClassCastException{
        
        // Crear una una conexion
        try {
            
            String param = "?autoReconnect=true";
            String dm = "jdbc:mysql://" + datos.getHost() + ":" + datos.getPuerto() + "/" + datos.getDatabase() + param;
            Class.forName("com.mysql.jdbc.Driver").getInterfaces();
            conn = DriverManager.getConnection(dm, datos.getUsuario(), datos.getPass());
            
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
        }
            
    }
    
    public Connection getConexion(){
        return conn;
    }
    
    public void setCerrarConexion() throws SQLException{
        if( conn != null  )
            conn.close();
    }
    
}
