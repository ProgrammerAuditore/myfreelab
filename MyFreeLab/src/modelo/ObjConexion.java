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
            
            // Estableciendo los parametros avanzados
            String param = "?autoReconnect=";
            param += "&createDatabaseIfNotExist=" + datos.isCreateDatabaseIfNotExist();
            param += "&useSSL=" + datos.isUseSSL();
            //param +="&verifyServerCertificate=false";
            //param +="&serverTimezone=UTC";
            //param +="&characterEncoding=UTF-8";
            //param +="&useUnicode=yes";
            
            String conexion_mysql = "jdbc:mysql://" + datos.getHost() + ":" + datos.getPuerto() + "/" + datos.getDatabase() +"?"+ param;
            //System.out.println("\n" + datos.toString());
            //System.out.println("MYSQL X = " + conexion_mysql);
            
            Class.forName("com.mysql.jdbc.Driver").getInterfaces();
            conn = DriverManager.getConnection(conexion_mysql, datos.getUsuario(), datos.getPass());
            
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
