package modelo.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import modelo.dto.ConexionDto;
import modelo.interfaces.keyword_conexion;

public class ConexionDao implements keyword_conexion<ConexionDto>{

    private String path = getClass().getResource("/source/config/db.dat").getPath();
    protected ConexionDto db = null;
    
    @Override
    public ConexionDto leer() {
        ConexionDto db = null;
        File file = new File( path ); 
        
        try(FileInputStream fis = new FileInputStream(file)){
            ObjectInputStream ois;
            
            while(fis.available() > 0){
                ois = new ObjectInputStream(fis);
                db = (ConexionDto) ois.readObject();
                System.out.println("" + db);
            }
            
        } catch(Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
        
        return db;
    }

    @Override
    public void actualizar(ConexionDto c) {
        File file = new File( path ); 
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            System.out.println("Successfully actualizar data to the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void escribir(ConexionDto c) {
        File file = new File( path ); 
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            System.out.println("Successfully escribir data to the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
