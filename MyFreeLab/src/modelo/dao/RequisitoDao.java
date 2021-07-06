package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProyectoDto;
import modelo.dto.RequisitoDto;
import modelo.interfaces.keyword_query;

public class RequisitoDao implements keyword_query<RequisitoDto>{

    @Override
    public boolean mtdInsetar(RequisitoDto requisito_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblrequisitos (cmpProID, cmpNombre, cmpCosto) "
                + "VALUES (?, ?, ?); ";        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, requisito_dto.getCmpProID());
            ps.setString(2, requisito_dto.getCmpNombre());
            ps.setDouble(3, requisito_dto.getCmpCosto());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean mtdActualizar(RequisitoDto requisito_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "UPDATE tblrequisitos "
                + "SET cmpNombre = ?, cmpCosto = ? "
                + "WHERE cmpID = ?; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, requisito_dto.getCmpNombre());
            ps.setDouble(2, requisito_dto.getCmpCosto());
            ps.setInt(3, requisito_dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0)
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdConsultar(RequisitoDto requisito_dto) {
        return false;
    }

    @Override
    public boolean mtdComprobar(RequisitoDto requisito_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblrequisitos WHERE cmpNombre = ? and cmpProID = ?; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString( 1, requisito_dto.getCmpNombre());
            ps.setInt(2, requisito_dto.getCmpProID() );
            ResultSet rs = ps.executeQuery();
            int filas = 0;
            
            while ( rs.next() ) {                
                filas++;
            }
            
            if( filas > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public List<RequisitoDto> mtdListar() {
        List<RequisitoDto> requisitos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblrequisitos ; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while( rs.next() ){
                RequisitoDto requisito = new RequisitoDto();
                requisito.setCmpID( rs.getInt( "cmpID" ) );
                requisito.setCmpNombre(rs.getString( "cmpNombre" ) );
                requisito.setCmpCosto( rs.getDouble( "cmpCosto" ) );
                
                requisitos.add(requisito);
            }
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return requisitos;
    }
    
    public List<RequisitoDto> mtdListar(RequisitoDto dto) {
        List<RequisitoDto> requisitos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblrequisitos WHERE cmpProID= ?; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt( 1, dto.getCmpProID() );
            ResultSet rs = ps.executeQuery();
            
            while( rs.next() ){
                RequisitoDto requisito = new RequisitoDto();
                requisito.setCmpID( rs.getInt( "cmpID" ) );
                requisito.setCmpNombre(rs.getString( "cmpNombre" ) );
                requisito.setCmpCosto( rs.getDouble( "cmpCosto" ) );
                
                requisitos.add(requisito);
            }
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return requisitos;
    }
    
    public int mtdObtenerCantidadReq( int id ){
        int totalReq = 0;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblrequisitos WHERE cmpProID = ? ; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while( rs.next() ){
                totalReq++;
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return totalReq;
    }
    
    public double mtdObtenerCostoEstimado( int id ){
        double costo = 0;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblrequisitos WHERE cmpProID = ? ; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while( rs.next() ){
                costo += rs.getDouble("cmpCosto");
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return costo;
    }

    @Override
    public boolean mtdRemover(RequisitoDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "DELETE FROM tblrequisitos WHERE cmpID = ?; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, obj_dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0)
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean mtdEliminar(RequisitoDto requisito_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
