package controlador;

import modelo.dao.ConexionDao;
import modelo.dto.ConexionDto;

public class ConexionController extends ConexionDao{
    
    private ConexionDao a = new ConexionDao();
    private ConexionDto cc;
    
    public ConexionController() {
        this.inicializar();
    }
    
    private void inicializar(){
        cc = new ConexionDto(0, "a", "b", "c", "d");
        establecerConexion();
    }
    
    private void establecerConexion(){
        a.escribir(cc);
    }
    
    public void capturar(){
        cc =  a.leer();
    }
    
    public void actualizar(){
        cc.setPuerto(1234);
        a.actualizar(cc);
    }
    
    public void mostrar(){
        cc =  a.leer();
        System.out.println("" + cc.getPuerto());
    } 
    
}
