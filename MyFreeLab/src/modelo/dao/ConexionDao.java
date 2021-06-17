package modelo.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.dto.ConexionDto;
import modelo.interfaces.keyword_conexion;
import src.Source;

public class ConexionDao implements keyword_conexion<ConexionDto>{

    @Override
    public ConexionDto obtener_conexion() {
        ConexionDto db = null;
        
        try(FileInputStream fis = new FileInputStream( Source.dataConexion )){
            ObjectInputStream ois;
            
            while(fis.available() > 0){
                ois = new ObjectInputStream(fis);
                db = (ConexionDto) ois.readObject();
                
                // Verificar si la contraseña es null
                if(db.getPass().length() == 0){
                    db.setPass("");
                }
                
                //System.out.println("" + db);
            }
            
        } catch(Exception e){
            //System.out.println("Error");
            e.printStackTrace();
        }
        
        return db;
    }

    @Override
    public void actualizar_conexion(ConexionDto c) {
        try (FileOutputStream fos = new FileOutputStream( Source.dataConexion )) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            //System.out.println("Successfully actualizar data to the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void regitrar_conexion(ConexionDto c) {
        try (FileOutputStream fos = new FileOutputStream( Source.dataConexion )) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            //System.out.println("Successfully escribir data to the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}