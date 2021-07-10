package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.EmpresaDto;
import modelo.interfaces.keyword_query;

public class EmpresaDao implements keyword_query<EmpresaDto>{

    @Override
    public boolean mtdInsetar(EmpresaDto empresa_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblempresas (cmpNombre, cmpDireccion, cmpCorreo, cmpTMovil) "
                + "VALUES (?, 'Desconocido', 'Desconocido', 'Desconocido')";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, empresa_dto.getCmpNombre());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdActualizar(EmpresaDto empresa_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "UPDATE tblempresas "
                + "SET cmpNombre = ?, cmpDireccion = ?, cmpCorreo = ?, cmpTMovil = ? "
                + "WHERE cmpID = ?; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, empresa_dto.getCmpNombre());
            ps.setString(2, empresa_dto.getCmpDireccion());
            ps.setString(3, empresa_dto.getCmpCorreo());
            ps.setString(4, empresa_dto.getCmpTMovil());
            ps.setInt(5, empresa_dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdConsultar(EmpresaDto empresa_dto) {
        return false;
    }

    @Override
    public List<EmpresaDto> mtdListar() {
        List<EmpresaDto> empresas = new ArrayList<EmpresaDto>();
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblempresas ;";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while( rs.next() ){
                EmpresaDto empresa = new EmpresaDto();
                
                empresa.setCmpID(rs.getInt( "cmpID" ));
                empresa.setCmpNombre(rs.getString( "cmpNombre" ));
                empresa.setCmpDireccion(rs.getString( "cmpDireccion" ));
                empresa.setCmpCorreo(rs.getString( "cmpCorreo" ));
                empresa.setCmpTMovil(rs.getString( "cmpTMovil" ));
                
                empresas.add(empresa);
            }
            
        } catch (Exception e) {
            System.out.println("" + e.getCause());
        }
        
        return empresas;
    }

    @Override
    public boolean mtdComprobar(EmpresaDto empresa_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblempresas WHERE cmpNombre = ? ; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString( 1 , empresa_dto.getCmpNombre());
            ResultSet rs = ps.executeQuery();
            int filas = 0;
            
            while (rs.next()) {                
                filas++;
            }
            
            // System.out.println("Filas  : " + filas);
            if( filas > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public List<EmpresaDto> mtdListar(EmpresaDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdRemover(EmpresaDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn  = CtrlHiloConexion.getConexion();
        String sql = "DELETE FROM tblempresas WHERE cmpID = ?; ";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, obj_dto.getCmpID());
            int rs = ps.executeUpdate();
            
            if( rs > 0)
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean mtdEliminar(EmpresaDto empresa_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public long mtdRowCount() {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT COUNT(*) FROM tblempresas; ";
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
    
}
