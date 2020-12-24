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
import java.util.ArrayList;
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

    private final static String query_agregar = "INSERT INTO freelancer(Nombre, Direccion, Telefono, Correo) VALUES (?,?,?,?)";
    private final static String query_actualizar = "UPDATE freelancer SET Nombre=?, Direccion=?, Telefono=?, Correo=?  WHERE idFreelancer = ?";
    private final static String query_consultar = "SELECT * FROM freelancer WHERE idFreelancer = ?";
    private final static String query_eliminar = "DELETE FROM freelancer WHERE idFreelancer = ?";
    private final static String query_listar = "SELECT * FROM freelancer";
    
    private final Conexion conexion = Conexion.estado_conexion();
    
    @Override
    public boolean agregar(FreelancerDto c) {
        PreparedStatement pst;
        
        try {
            pst = this.conexion.getConn().prepareStatement(query_agregar);
            pst.setString(1, c.getStrNombre() );
            pst.setString(2, c.getStrDireccion());
            pst.setString(3, c.getStrTelefono());
            pst.setString(4, c.getStrCorreo());
            
            if( pst.executeUpdate() > 0)
                return true;
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.conexion.cerrar_conexion();
        }
        
        return false;
    }

    @Override
    public boolean actualizar(FreelancerDto c) {
        PreparedStatement pst;
        
        try {
            pst = this.conexion.getConn().prepareStatement(query_actualizar);
            pst.setString(1, c.getStrNombre() );
            pst.setString(2, c.getStrDireccion());
            pst.setString(3, c.getStrTelefono());
            pst.setString(4, c.getStrCorreo());
            pst.setInt(5, c.getIdFreelancer());
            
            if( pst.executeUpdate() > 0)
                return true;
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.conexion.cerrar_conexion();
        }
        
        return false;
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
            
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
        } finally {
            this.conexion.cerrar_conexion();
        }
        
        return freelancer;
    }

    @Override
    public boolean eliminar(Object key) {
        PreparedStatement pst;
        
        try {
            pst = this.conexion.getConn().prepareStatement(query_eliminar);
            pst.setInt(1, Integer.parseInt(( String.valueOf(key) )));
            
            if( pst.executeUpdate() > 0 )
                return true;
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.conexion.cerrar_conexion();
        }
        
        return false;
    }

    @Override
    public List<FreelancerDto> listar() {
        ArrayList<FreelancerDto> freelancers = new ArrayList<FreelancerDto>();
        PreparedStatement pst;
        ResultSet query;
        try {
            
            pst = this.conexion.getConn().prepareStatement(query_listar);
            query = pst.executeQuery();
            
            while(query.next()){
                freelancers.add( new FreelancerDto(
                        query.getInt("idFreelancer"), 
                        query.getString("Nombre"), 
                        query.getString("Direccion"),
                        query.getString("Telefono"),
                        query.getString("Correo"))
                );
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return freelancers;
    }
    
}
