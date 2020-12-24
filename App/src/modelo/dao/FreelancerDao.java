/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Conexion;
import modelo.dto.FreelancerDto;
import modelo.interfaces.keyword_query;

/**
 *
 * @author victo
 */
public class FreelancerDao implements keyword_query<FreelancerDto> {

    private final static String query_consultar = "SELECT * FROM freelancer WHERE idFreelancer = ?";
    private final Conexion conexion = Conexion.estado_conexion();
    
    @Override
    public boolean agregar(FreelancerDto c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(FreelancerDto c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FreelancerDto consultar(Object key) {
        FreelancerDto freelancer = null;
        PreparedStatement pst;
        ResultSet query;
        
        try {
            pst = this.conexion.getConn().prepareStatement(query_consultar);
            pst.setInt(1, Integer.parseInt(( String.valueOf(key) )));
            query = pst.executeQuery();
            
            if( query.next() ){
                freelancer = new FreelancerDto();
                freelancer.setIdFreelancer( query.getInt(1) );
                freelancer.setStrNombre( query.getString(2) );
                freelancer.setStrDireccion( query.getString(3) );
                freelancer.setStrTelefono( query.getString(4) );
                freelancer.setStrCorreo( query.getString(5) );
            }
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.conexion.cerrar_conexion();
        }
        
        return freelancer;
    }

    @Override
    public boolean eliminar(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FreelancerDto> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
