package modelo.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.dto.ConexionDto;
import src.Recursos;
import modelo.interfaces.keyword_binario;

public class ConexionDao implements keyword_binario<ConexionDto>{

    @Override
    public ConexionDto obtener_datos() {
        ConexionDto db = null;
        
        try(FileInputStream fis = new FileInputStream( Recursos.dataConexion() )){
            ObjectInputStream ois = null;
            
            while(fis.available() > 0){
                ois = new ObjectInputStream(fis);
                db = (ConexionDto) ois.readObject();
                
                // Verificar si la contraseña es null
                if(db.getPass().length() == 0){
                    db.setPass("");
                }
                
                //System.out.println("" + db);
            }
            
            ois.close();
            fis.close();
            
        } catch(Exception e){
            //System.out.println("Error");
            Recursos.dataConexion().delete();
            //e.printStackTrace();
        }
        
        return db;
    }

    @Override
    public void actualizar_datos(ConexionDto c) {
        try (FileOutputStream fos = new FileOutputStream( Recursos.dataConexion() )) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            //System.out.println("Successfully actualizar data to the file");
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void regitrar_datos(ConexionDto c) {
        try (FileOutputStream fos = new FileOutputStream( Recursos.dataConexion() )) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            //System.out.println("Successfully escribir data to the file");
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
}
