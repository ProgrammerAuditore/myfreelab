package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                + "VALUES (?,?,?,?)";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, proyecto_dto.getCmpNombre());
            ps.setString(2, proyecto_dto.getCmpDireccion());
            ps.setString(3, proyecto_dto.getCmpCorreo());
            ps.setString(4, proyecto_dto.getCmpTMovil());
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
        
        return empresas;
    }

    @Override
    public boolean mtdComprobar(EmpresaDto proyecto_dto) {
        return false;
    }
    
}
