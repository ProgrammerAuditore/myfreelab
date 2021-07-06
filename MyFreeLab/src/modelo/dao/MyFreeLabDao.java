/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/**
 *
 * @author victo
 */
public class MyFreeLabDao{
    
    public static boolean mtdCrearBaseDeDatos(){
        PreparedStatement ps = null;
        String dbname = CtrlHiloConexion.ctrlDatos.getDatabase();
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = " Create Database If Not Exists "+ dbname +" Default Character Set 'utf8' Collate 'utf8_general_ci' ; ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true; 
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    public static boolean mtdChecarTablas(){
        Connection conn = CtrlHiloConexion.getConexion();
        String[] tables = {"tblproyectos", "tbldatospersonales", "tblempresas", "tblrequisitos", "tblasociados" };
        int tablas_existentes = 0;
        
        // Verificar la conexion a la base de datos
        try {

            DatabaseMetaData metadata = conn.getMetaData();
            for(int i=0; i< tables.length; i++) {
                ResultSet rs = metadata.getTables(null, null, tables[i], null);
                // Verficcar si existe o no la tabla
                if(!rs.next()) {
                    tablas_existentes--;
                }else tablas_existentes++;

            }   


        } catch (SQLException ex) {
            //System.out.println("" + ex.getMessage());
        }
        
        return ( tablas_existentes == tables.length );
    } 
    
    public static boolean mtdCrearTablaDatosPersonales(){
        PreparedStatement ps = null;
        String dbname = CtrlHiloConexion.ctrlDatos.getDatabase();
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "Create Table "+dbname+".tbldatospersonales ( ";
               sql += " cmpID int not null, Primary Key(cmpID), ";
               sql += " cmpNombres varchar(30) null default 'Desconocido',";
               sql += " cmpApellidos varchar(30) null default 'Desconocido',";
               sql += " cmpDireccion varchar(60) null default 'Desconocido',";
               sql += " cmpCorreo varchar(60) null default 'Desconocido',";
               sql += " cmpTMovil varchar(12) null default '0000000000'";
               sql += " ); ";
               
        String sql2 = "Insert Into "+dbname+".tbldatospersonales (cmpID, cmpNombres, cmpApellidos) ";
               sql2 += "Values (" + "1, 'Desconocido', 'Desconocido'" + "); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.execute();
            
            ps = conn.prepareStatement(sql2);
            ps.execute();
            
            return true; 
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    public static boolean mtdCrearTablaProyectos(){
        PreparedStatement ps = null;
        String dbname = CtrlHiloConexion.ctrlDatos.getDatabase();
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "Create Table "+dbname+".tblproyectos ( ";
               sql += " cmpID int not null auto_increment, Primary Key(cmpID), ";
               sql += " cmpNombre varchar(30) not null,";
               sql += " cmpFechaInicial varchar(12) null default 'Desconocido',";
               sql += " cmpFechaFinal varchar(12) null default 'Desconocido',";
               sql += " cmpCostoEstimado double null default 0.0,";
               sql += " cmpMontoAdelantado double null default 0.0,";
               sql += " cmpCreadoEn datetime not null default '1000-01-01 00:00:00',";
               sql += " cmpActualizadoEn datetime not null default '1000-01-01 00:00:00',";
               sql += " cmpCtrlEstado int not null default 1";
               sql += " ); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.execute();
            
            return true; 
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    public static boolean mtdCrearTablaEmpresas(){
        PreparedStatement ps = null;
        String dbname = CtrlHiloConexion.ctrlDatos.getDatabase();
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "Create Table "+dbname+".tblempresas ( ";
               sql += " cmpID int not null auto_increment, Primary Key(cmpID), ";
               sql += " cmpNombre varchar(30) not null,";
               sql += " cmpDireccion varchar(60) null default 'Desconocido',";
               sql += " cmpCorreo varchar(60) null default 'Desconocido',";
               sql += " cmpTMovil varchar(12) null default 'Desconocido',";
               sql += " cmpCreadoEn datetime not null default '1000-01-01 00:00:00',";
               sql += " cmpActualizadoEn datetime not null default '1000-01-01 00:00:00',";
               sql += " cmpCtrlEstado int not null default 1";
               sql += " ); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.execute();
            
            return true; 
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    public static boolean mtdCrearTablaRequisitos() {
        PreparedStatement ps = null;
        String dbname = CtrlHiloConexion.ctrlDatos.getDatabase();
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "Create Table "+dbname+".tblrequisitos ( ";
               sql += " cmpID int not null auto_increment, Primary Key(cmpID), ";
               sql += " cmpProID int not null, ";
               sql += " Foreign Key(cmpProID) References tblproyectos(cmpID) ON DELETE CASCADE, ";
               sql += " cmpNombre varchar(30) not null, ";
               sql += " cmpCosto double not null ";
               sql += " ); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.execute();
            
            return true; 
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }
    
    public static boolean mtdCrearTablaAsociados() {
        PreparedStatement ps = null;
        String dbname = CtrlHiloConexion.ctrlDatos.getDatabase();
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "Create Table "+dbname+".tblasociados ( ";
               sql += " cmpID int not null auto_increment, Primary Key(cmpID), ";
               sql += " cmpProID int not null, ";
               sql += " Foreign Key(cmpProID) References tblproyectos(cmpID) ON DELETE CASCADE, ";
               sql += " cmpEmpID int not null, ";
               sql += " Foreign Key(cmpEmpID) References tblempresas(cmpID) ON DELETE CASCADE, ";
               sql += " cmpProNombre varchar(30) not null, ";
               sql += " cmpEmpNombre varchar(30) not null ";
               sql += " ); ";
        
        try {
            
            ps = conn.prepareStatement(sql);
            ps.execute();
            
            return true; 
            
        } catch (SQLException e) {
            //System.out.println("" + e.getMessage());
        }
        
        return false;
    }

}
