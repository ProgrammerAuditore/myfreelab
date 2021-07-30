package modelo.dao;

import controlador.CtrlHiloConexion;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProyectoDto;
import modelo.interfaces.keyword_query;
import modelo.interfaces.keyword_proyectos;

public class ProyectoDao implements keyword_query<ProyectoDto>, keyword_proyectos<ProyectoDto>{

    @Override
    public boolean mtdInsetar(ProyectoDto proyecto_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblproyectos"
                + " ( cmpNombre, cmpFechaInicial, cmpFechaFinal, cmpCtrlEstado, cmpCreadoEn, cmpActualizadoEn  ) "
                + " VALUES ( ?, ?, ?, ?, ?, ? ); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, proyecto_dto.getCmpNombre());
            ps.setString(2, proyecto_dto.getCmpFechaInicial());
            ps.setString(3, proyecto_dto.getCmpFechaFinal());
            ps.setInt(4, proyecto_dto.getCmpCtrlEstado());
            ps.setString(5, proyecto_dto.getCmpCreadoEn());
            ps.setString(6, proyecto_dto.getCmpCreadoEn());
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdActualizar(ProyectoDto proyecto_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "UPDATE tblproyectos "
                + "SET cmpNombre = ?, cmpFechaInicial = ?, cmpFechaFinal = ?, cmpCostoEstimado = ?, "
                + "cmpMontoAdelantado = ?, cmpCtrlEstado = ?, cmpCreadoEn = ?, cmpActualizadoEn = ? "
                + "WHERE cmpID = ? ; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, proyecto_dto.getCmpNombre());
            ps.setString(2, proyecto_dto.getCmpFechaInicial());
            ps.setString(3, proyecto_dto.getCmpFechaFinal());
            ps.setDouble(4, proyecto_dto.getCmpCostoEstimado());
            ps.setDouble(5, proyecto_dto.getCmpMontoAdelantado());
            ps.setInt(6, proyecto_dto.getCmpCtrlEstado());
            ps.setString(7, proyecto_dto.getCmpCreadoEn());
            ps.setString(8, proyecto_dto.getCmpActualizadoEn());
            ps.setInt(9, proyecto_dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public List<ProyectoDto> mtdListar() {
        List<ProyectoDto> proyectos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpCtrlEstado BETWEEN 0 AND 100 ORDER BY cmpActualizadoEn DESC, cmpCtrlEstado DESC; ";
        
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
                proyecto.setCmpCtrlEstado(rs.getInt("cmpCtrlEstado") );
                proyecto.setCmpActualizadoEn(rs.getString("cmpActualizadoEn") );
                proyecto.setCmpCreadoEn(rs.getString("cmpCreadoEn") );
                
                proyectos.add(proyecto);
            }
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return proyectos;
    }

    @Override
    public boolean mtdComprobar(ProyectoDto proyecto_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpNombre = ? ; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString( 1, proyecto_dto.getCmpNombre() );
            ResultSet rs = ps.executeQuery();
            int filas = 0;
            
            while ( rs.next() ) {                
                filas++;
            }
            
            if( filas > 0 )
            return true;
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdConsultar(ProyectoDto proyecto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpID = ? ;";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, proyecto.getCmpID());
            ResultSet rs = ps.executeQuery();
            
            while ( rs.next() ) {                
                proyecto.setCmpID(rs.getInt("cmpID" ) );
                proyecto.setCmpNombre( rs.getString( "cmpNombre" ) );
                proyecto.setCmpFechaInicial( rs.getString( "cmpFechaInicial" ) );
                proyecto.setCmpFechaFinal( rs.getString( "cmpFechaFinal" ) );
                proyecto.setCmpCostoEstimado( rs.getDouble( "cmpCostoEstimado" ) );
                proyecto.setCmpMontoAdelantado( rs.getDouble( "cmpMontoAdelantado" ) );
                proyecto.setCmpCtrlEstado(rs.getInt("cmpCtrlEstado") );
                proyecto.setCmpActualizadoEn(rs.getString("cmpActualizadoEn") );
                proyecto.setCmpCreadoEn(rs.getString("cmpCreadoEn") );
                
            }
            
            return true;
                    
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public List<ProyectoDto> mtdListar(ProyectoDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProyectoDto> mtdListarProyectoEnProceso() {
        List<ProyectoDto> proyectos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpCtrlEstado BETWEEN 1 AND 50 ;";
        
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
                proyecto.setCmpCtrlEstado(rs.getInt("cmpCtrlEstado") );
                proyecto.setCmpActualizadoEn(rs.getString("cmpActualizadoEn") );
                proyecto.setCmpCreadoEn(rs.getString("cmpCreadoEn") );
                
                proyectos.add(proyecto);
            }
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return proyectos;
    }

    @Override
    public List<ProyectoDto> mtdListarProyectoEliminados() {
        List<ProyectoDto> proyectos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpCtrlEstado = 1  ;";
        
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
            //System.out.println("" + e.getMessage());
        }
        
        return proyectos;
    }

    @Override
    public List<ProyectoDto> mtdListarProyectoRealizados() {
        List<ProyectoDto> proyectos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpCtrlEstado = 1  ;";
        
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
            //System.out.println("" + e.getMessage());
        }
        
        return proyectos;
    }

    @Override
    public boolean mtdRemover(ProyectoDto proyecto_dto) {
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
                //System.out.println("" + e.getMessage());        
        }
            
        return false;
    }
    
    @Override
    public boolean mtdEliminar(ProyectoDto proyecto_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "UPDATE tblproyectos "
                + "SET cmpCtrlEstado = ?, cmpActualizadoEn = ? "
                + "WHERE cmpID = ?; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, proyecto_dto.getCmpCtrlEstado());
            ps.setString(2, proyecto_dto.getCmpActualizadoEn());
            ps.setInt(3, proyecto_dto.getCmpID());
            int resp = ps.executeUpdate();
            
            if( resp > 0 )
            return true;
            
        } catch (Exception e) {
            //System.out.println("" + e.getMessage());
        }
        return false;
    }
    
    public long mtdRowCount() {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT COUNT(*) FROM tblproyectos; ";
        long filas = 0;
        
        try {
            
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            filas = rs.getInt(1);
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return filas;
    }
    
    public long mtdRowCount(int estado) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT COUNT(*) FROM tblproyectos WHERE cmpCtrlEstado = ?; ";
        long filas = 0;
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, estado);
            ResultSet rs = ps.executeQuery();
            rs.next();
            filas = rs.getInt(1);
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return filas;
    }
    
    public BigDecimal mtdSumarCostoEstimado(int min, int max) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT SUM(cmpCostoEstimado) FROM tblproyectos WHERE cmpCtrlEstado BETWEEN ? AND ?; ";
        BigDecimal monto = null;
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            rs.next();
            monto = new BigDecimal(rs.getDouble(1)).setScale(2, RoundingMode.HALF_EVEN);
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return monto;
    }
    
    public BigDecimal mtdSumarMontoAdelantado(int min, int max) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT SUM(cmpMontoAdelantado) FROM tblproyectos WHERE cmpCtrlEstado BETWEEN ? AND ?; ";
        BigDecimal monto = null;
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            rs.next();
            monto = new BigDecimal(rs.getDouble(1)).setScale(2, RoundingMode.HALF_EVEN);
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return monto;
    }
    
    public List<ProyectoDto> mtdListar(int inicio, int fin) {
        List<ProyectoDto> proyectos = new ArrayList<>();
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblproyectos WHERE cmpCtrlEstado "
                + "BETWEEN 0 AND 100 "
                + "ORDER BY cmpActualizadoEn DESC, cmpCtrlEstado DESC "
                + "LIMIT ? OFFSET ?; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, inicio);
            ps.setInt(2, fin);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ProyectoDto proyecto = new ProyectoDto();
                
                //System.out.println("" + rs.getString( "cmpNombre" ));
                proyecto.setCmpID(rs.getInt("cmpID" ) );
                proyecto.setCmpNombre( rs.getString( "cmpNombre" ) );
                proyecto.setCmpFechaInicial( rs.getString( "cmpFechaInicial" ) );
                proyecto.setCmpFechaFinal( rs.getString( "cmpFechaFinal" ) );
                proyecto.setCmpCostoEstimado( rs.getDouble( "cmpCostoEstimado" ) );
                proyecto.setCmpMontoAdelantado( rs.getDouble( "cmpMontoAdelantado" ) );
                proyecto.setCmpCtrlEstado(rs.getInt("cmpCtrlEstado") );
                proyecto.setCmpActualizadoEn(rs.getString("cmpActualizadoEn") );
                proyecto.setCmpCreadoEn(rs.getString("cmpCreadoEn") );
                
                proyectos.add(proyecto);
            }
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return proyectos;
    }

}
