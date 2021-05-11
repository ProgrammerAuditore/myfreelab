package hilos;

import controlador.CtrlHiloConexion;
import modelo.dao.ConexionDao;
import vista.splash.Bienvenida;

public class HiloSplash extends Thread{

    @Override
    public void run() {
        
        // * Splash de bienvenida
        Bienvenida splash = new Bienvenida();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        String[] msg = new String[3];
        int src = 2, avance = 0;
        
        CtrlHiloConexion.ctrlDatos = new ConexionDao().obtener_conexion();
        
        for (int i = 1; i > 0; i--) {
            
            splash.etqMensaje.setText("Estableciendo conexion..");
            if(CtrlHiloConexion.mtdEstablecer()){
                splash.etqMensaje.setText("Conexion establecienda.");
            }else {
                splash.etqMensaje.setText("Conexion no establecida.");
            }
            
            avance += (100 / src);
            splash.pbProgreso.setValue(avance);
            splash.etqCarga.setText("" + avance + "%");
        }
        
        for (int i = 1; i > 0; i--) {
            
            splash.etqMensaje.setText("Estableciendo conexion..");
            if(CtrlHiloConexion.mtdEstablecer()){
                splash.etqMensaje.setText("Conexion establecienda.");
            }else {
                splash.etqMensaje.setText("Conexion no establecida.");
            }
            
            avance += (100 / src);
            splash.pbProgreso.setValue(avance);
            splash.etqCarga.setText("" + avance + "%");
        }
        
        splash.setVisible(false);
        splash.dispose();
    }
    
}
