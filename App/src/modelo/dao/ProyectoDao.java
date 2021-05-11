package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProyectoDto;
import modelo.interfaces.keyword_proyecto;

public class ProyectoDao implements keyword_proyecto<ProyectoDto>{

    @Override
    public boolean mtdInsetar(ProyectoDto proyecto_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblproyectos ( cmpNombre ) VALUES ( ? );";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, proyecto_dto.getCmpNombre());
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdActualizar(ProyectoDto proyecto_dto) {
        return false;
    }

    @Override
    public List<ProyectoDto> mtdConsultar() {
        List<ProyectoDto> proyectos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos ;";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ProyectoDto proyecto = new ProyectoDto();
                
                proyecto.setCmpID(rs.getInt("cmpID" ) );
                proyecto.setCmpNombre( rs.getString( "cmpNombre" ) );
                proyecto.setCmpFechaInicial( rs.getString( "cmpFechaInicial" ) );
                proyecto.setCmpFechaFinal( rs.getString( "cmpFechaFinal" ) );
                proyecto.setCmpCostoEstimado( rs.getDouble( "cmpCostoEstimado" ) );
                proyecto.setCmpMontoAdelantado( rs.getDouble( "cmpMontoAdelantado" ) );
                
                proyectos.add(proyecto);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return proyectos;
    }

    @Override
    public boolean mtdComprobar(ProyectoDto proyecto_dto) {
        return false;
    }

    @Override
    public boolean mtdEliminar(ProyectoDto proyecto_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "DELETE FROM tblproyectos WHERE cmpID = ?; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, proyecto_dto.getCmpID());
            int resp = ps.executeUpdate();
            
            if( resp > 0 )
                return true;
            
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
        return false;
    }
    
}
