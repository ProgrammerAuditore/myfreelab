package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.VinculacionDto;
import modelo.interfaces.keywork_vinculacion;

public class VinculacionDao implements keywork_vinculacion<VinculacionDto>{
    
    @Override
    public List<VinculacionDto> mtdListar(VinculacionDto dto) {
        List<VinculacionDto> vinculaciones = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblasociados WHERE cmpProID = ? ; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt( 1, dto.getCmpProID() );
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                VinculacionDto vinculo = new VinculacionDto();
                
                vinculo.setCmpID( rs.getInt("cmpID") );
                vinculo.setCmpProID(rs.getInt("cmpProID") );
                vinculo.setCmpEmpID(rs.getInt("cmpEmpID") );
                vinculo.setCmpProNombre( rs.getString("cmpProNombre") );
                vinculo.setCmpEmpNombre( rs.getString("cmpEmpNombre") );
                
                vinculaciones.add(vinculo);
                
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return vinculaciones;
    }
    
    @Override
    public boolean mtdVincular(VinculacionDto dto){
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblasociados (cmpProID, cmpEmpID, cmpProNombre, cmpEmpNombre) "
                + "VALUES (?,?,?,?); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt( 1, dto.getCmpProID());
            ps.setInt( 2, dto.getCmpEmpID());
            ps.setString( 3, dto.getCmpProNombre());
            ps.setString( 4, dto.getCmpEmpNombre());
            int rs =  ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean mtdEliminar(VinculacionDto dto){
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "DELETE FROM tblasociados WHERE cmpID = ? ; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt( 1, dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
}
