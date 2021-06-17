package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ObjConexion;
import modelo.dto.ConexionDto;

public class CtrlHiloConexion{
    
    // Valores sin instanciar
    public static ConexionDto ctrlDatos;
    public static ObjConexion ctrlConn;
    public static Connection ctrlConexion;
    public static boolean ctrlEstado = false;
    public static String ctrlHiloC = new String();
    private static int ctrlTime = 250;
    
    public static boolean mtdEstablecer(){
        
        try {
            
            ctrlConn = new ObjConexion(ctrlDatos);
            
            ctrlConn.mtdEstablecerConexion();
            ctrlConexion = ctrlConn.getConexion();
            
            if( ctrlConexion.isValid( CtrlHiloConexion.ctrlTime ) ){
                
                ////System.out.println("Conexion establecida.");
                //ctrlEstado = true;
                return true;
                
            }else{
                //ctrlEstado = false;
            }
            
            //return true;
        } catch (SQLException ex) {
            //Logger.getLogger(CtrlHiloConexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            //Logger.getLogger(CtrlHiloConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static boolean mtdCerrar(){
        
        if( ctrlConn != null && ctrlConexion != null ){
            
            try {
                if( ctrlConexion.isValid( CtrlHiloConexion.ctrlTime  ) ){
                    ctrlConexion.close();
                    ctrlConn = null;
                    ctrlConexion = null;
                    //ctrlEstado = false;
                    ////System.out.println("Conexion cerrada.");
                    
                    return true;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(CtrlHiloConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return false;
    }
    
    
    public static boolean checkConexion(){
         if( ctrlConn != null && ctrlConexion != null ){
            
            try {
                if( ctrlConexion.isValid( CtrlHiloConexion.ctrlTime ) ) return true;
            } catch (SQLException ex) {
                //Logger.getLogger(CtrlHiloConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return false;
    }
    
    public static Connection getConexion(){
        if( ctrlConn != null && ctrlConexion != null ){
            return ctrlConexion;
        }
        
        return null;
    }
    
}
