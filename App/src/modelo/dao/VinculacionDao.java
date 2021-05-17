package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProyectoDto;
import modelo.dto.VinculacionDto;
import modelo.interfaces.keyword_query;

public class VinculacionDao {
    
    public List<VinculacionDto> mtdListar(VinculacionDto dto) {
        List<VinculacionDto> vinculaciones = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblasociados WHERE cmpProID = ? ; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getCmpProID() );
            
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
            
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
        
        return vinculaciones;
    }
    
}
