package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.dto.DatosPersonalesDto;
import modelo.interfaces.keyword_datos_personales;

public class DatosPersonalesDao implements keyword_datos_personales<DatosPersonalesDto> {
    
    @Override
    public boolean mtdConsultar(DatosPersonalesDto datos_dto) {
        
        PreparedStatement ps= null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tbldatospersonales WHERE cmpID = 1;";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while( rs.next() ){
                
                datos_dto.setCmpID( rs.getInt("cmpID") );
                datos_dto.setCmpNombres( rs.getString("cmpNombres") );
                datos_dto.setCmpApellidos( rs.getString("cmpApellidos")  );
                datos_dto.setCmpDireccion( rs.getString("cmpDireccion")  );
                datos_dto.setCmpCorreo( rs.getString("cmpCorreo")  );
                datos_dto.setCmpTMovil( rs.getString("cmpTMovil")  );
                
                return true;
            }
            
            
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        
        
        return false;
    }
    
    @Override
    public boolean mtdInsertar(DatosPersonalesDto datos_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        
        try {
            
            String sql = "INSERT INTO tbldatospersonales (cmpID, cmpNombres, cmpApellidos, cmpDireccion, cmpTMovil, cmpCorreo) "
                    + "VALUES(?, ?, ?, ?, ?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, datos_dto.getCmpID());
            ps.setString(2, datos_dto.getCmpNombres());
            ps.setString(3, datos_dto.getCmpApellidos());
            ps.setString(4, datos_dto.getCmpDireccion());
            ps.setString(5, datos_dto.getCmpTMovil());
            ps.setString(6, datos_dto.getCmpCorreo());
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdActualizarDatos(DatosPersonalesDto datos_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "UPDATE tbldatospersonales "
                + "SET cmpNombres = ?, cmpApellidos = ?, cmpDireccion = ?, cmpTMovil = ?, cmpCorreo = ? "
                + "WHERE cmpID = ?; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, datos_dto.getCmpNombres());
            ps.setString(2, datos_dto.getCmpApellidos());
            ps.setString(3, datos_dto.getCmpDireccion());
            ps.setString(4, datos_dto.getCmpTMovil());
            ps.setString(5, datos_dto.getCmpCorreo());
            ps.setInt(6, datos_dto.getCmpID());
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        
        return false;
    }
    
}
