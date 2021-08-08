package modelo.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.dto.PreferenciaDto;
import src.Recursos;
import modelo.interfaces.keyword_binario;

public class PreferenciaDao implements keyword_binario<PreferenciaDto>{

    @Override
    public PreferenciaDto obtener_datos() {
        PreferenciaDto db = null;
        
        try(FileInputStream fis = new FileInputStream( Recursos.dataPreferencias() )){
            ObjectInputStream ois = null;
            
            while(fis.available() > 0){
                ois = new ObjectInputStream(fis);
                db = (PreferenciaDto) ois.readObject();
                
                //System.out.println("" + db);
            }
            
            ois.close();
            fis.close();
            
        } catch(Exception e){
            //System.out.println("Error");
            Recursos.dataPreferencias().delete();
            //e.printStackTrace();
        }
        
        return db;
    }

    @Override
    public void actualizar_datos(PreferenciaDto c) {
        try (FileOutputStream fos = new FileOutputStream( Recursos.dataPreferencias() )) {
            
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
    public void regitrar_datos(PreferenciaDto c) {
        try (FileOutputStream fos = new FileOutputStream( Recursos.dataPreferencias() )) {
            
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
