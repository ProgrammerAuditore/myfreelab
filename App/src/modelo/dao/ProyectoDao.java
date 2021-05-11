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
        String sql = "INSERT INTO tblproyectos ( cmpNombre, cmpFechaInicial, cmpFechaFinal ) VALUES ( ?, 'Desconocido', 'Desconodio' );";
        
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
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "UPDATE tblproyectos "
                + "SET cmpNombre = ?, cmpFechaInicial = ?, cmpFechaFinal = ?, cmpCostoEstimado = ?, cmpMontoAdelantado = ? "
                + "  WHERE cmpID = ? ; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, proyecto_dto.getCmpNombre());
            ps.setString(2, proyecto_dto.getCmpFechaInicial());
            ps.setString(3, proyecto_dto.getCmpFechaFinal());
            ps.setDouble(4, proyecto_dto.getCmpCostoEstimado());
            ps.setDouble(5, proyecto_dto.getCmpMontoAdelantado());
            ps.setInt(6, proyecto_dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public List<ProyectoDto> mtdListar() {
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

    @Override
    public boolean mtdConsultar(ProyectoDto proyecto_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpID = ? ;";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, proyecto_dto.getCmpID());
            ResultSet rs = ps.executeQuery();
            
            while ( rs.next() ) {                
                proyecto_dto.setCmpID( rs.getInt( "cmpID" ) );
                proyecto_dto.setCmpNombre(rs.getString( "cmpNombre" ) );
                proyecto_dto.setCmpFechaInicial( rs.getString( "cmpFechaInicial" ) );
                proyecto_dto.setCmpFechaFinal( rs.getString( "cmpFechaFinal" ) );
                proyecto_dto.setCmpCostoEstimado( rs.getDouble( "cmpCostoEstimado" ) );
                proyecto_dto.setCmpMontoAdelantado( rs.getDouble( "cmpCostoAdelantado" ) );
            }
            
            return true;
                    
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
}
