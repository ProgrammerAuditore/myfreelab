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
    public boolean mtdInsetar(EmpresaDto proyecto_dto) {
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblempresas (cmpNombre, cmpDireccion, cmpCorreo, cmpTMovil) "
                + "VALUES (?, 'Desconocido', 'Desconocido', 'Desconocido')";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, proyecto_dto.getCmpNombre());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdEliminar(EmpresaDto proyecto_dto) {
        return false;
    }

    @Override
    public boolean mtdActualizar(EmpresaDto proyecto_dto) {
        return false;
    }

    @Override
    public boolean mtdConsultar(EmpresaDto proyecto_dto) {
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
    public boolean mtdComprobar(EmpresaDto proyecto_dto) {
        return false;
    }
    
}
