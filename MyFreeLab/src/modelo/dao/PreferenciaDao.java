package modelo.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.dto.PreferenciaDto;
import src.Source;
import modelo.interfaces.keyword_binario;

public class PreferenciaDao implements keyword_binario<PreferenciaDto>{

    @Override
    public PreferenciaDto obtener_conexion() {
        PreferenciaDto db = null;
        
        try(FileInputStream fis = new FileInputStream( Source.dataPreferencias )){
            ObjectInputStream ois;
            
            while(fis.available() > 0){
                ois = new ObjectInputStream(fis);
                db = (PreferenciaDto) ois.readObject();
                
                //System.out.println("" + db);
            }
            
        } catch(Exception e){
            //System.out.println("Error");
            e.printStackTrace();
        }
        
        return db;
    }

    @Override
    public void actualizar_conexion(PreferenciaDto c) {
        try (FileOutputStream fos = new FileOutputStream( Source.dataPreferencias )) {
            
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
    public void regitrar_conexion(PreferenciaDto c) {
        try (FileOutputStream fos = new FileOutputStream( Source.dataPreferencias )) {
            
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
