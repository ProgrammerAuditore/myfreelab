package hilos;

import controlador.CtrlHiloConexion;
import modelo.dao.ConexionDao;
import modelo.dao.MyFreeLabDao;
import modelo.dto.ConexionDto;
import src.Source;
import vista.splash.Bienvenida;

public class HiloSplash extends Thread{

    @Override
    public void run() {
        
        // * Splash de bienvenida
        Bienvenida splash = new Bienvenida();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        String[] msg = new String[3];
        int src = 4, avance = 0, pause = 2;
        
        // * Cargar los datos de conexion..
        Source.dataConexion.exists();
        for (int i = 1; i > 0; i--) {
            
            splash.etqMensaje.setText("Cargando datos de conexion..");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
            
            // Proceso de carga
            if( new ConexionDao().obtener_conexion() != null ){
                splash.etqMensaje.setText("Datos de conexión cargado.");
                System.out.println("Datos de conexión cargado. [!]");
            }else {
                ConexionDto conec = new ConexionDto("0", "", "", "", "");
                new ConexionDao().regitrar_conexion(conec);
                splash.etqMensaje.setText("Datos de conexión creado.");
                System.out.println("Datos de conexión creado. [!]");
            }
            
            avance += (100 / src);
            splash.pbProgreso.setValue(avance);
            splash.etqCarga.setText("" + avance + "%");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
        }
        
        // * Establecer conexion..
        CtrlHiloConexion.ctrlDatos = new ConexionDao().obtener_conexion();
        for (int i = 1; i > 0; i--) {
            
            splash.etqMensaje.setText("Estableciendo conexion..");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
            
            // Proceso de carga
            if(CtrlHiloConexion.mtdEstablecer())
                splash.etqMensaje.setText("Conexion establecienda.");
            else
                splash.etqMensaje.setText("Conexion no establecida.");
            
            avance += (100 / src);
            splash.pbProgreso.setValue(avance);
            splash.etqCarga.setText("" + avance + "%");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
        }

        // * Cargar tabla proyectos..
        for (int i = 1; i > 0; i--) {
            
            splash.etqMensaje.setText("Cargando tabla proyectos del SGBD..");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
            
            // Proceso de carga
            if( CtrlHiloConexion.checkConexion() ){
                if( !MyFreeLabDao.mtdCrearTablaProyectos() )
                    splash.etqMensaje.setText("Tabla proyectos cargado.");
                else
                    splash.etqMensaje.setText("Tabla proyectos creado.");
            } else
                splash.etqMensaje.setText("Conexion no establecida.");
            
            avance += (100 / src);
            splash.pbProgreso.setValue(avance);
            splash.etqCarga.setText("" + avance + "%");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
        }
        
        // * Cargar tabla datos personales..
        for (int i = 1; i > 0; i--) {
            
            splash.etqMensaje.setText("Cargando tabla datos personales del SGBD..");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
            
            // Proceso de carga
            if( CtrlHiloConexion.checkConexion() ){
                if( !MyFreeLabDao.mtdCrearTablaDatosPersonales() )
                    splash.etqMensaje.setText("Tabla datos personales cargado.");
                else
                    splash.etqMensaje.setText("Tabla datos personales creado.");
            } else
                splash.etqMensaje.setText("Conexion no establecida.");
            
            avance += (100 / src);
            splash.pbProgreso.setValue(avance);
            splash.etqCarga.setText("" + avance + "%");
            try {
                Thread.sleep( avance * pause );
            } catch (Exception e) {}
        }
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        
        splash.setVisible(false);
        splash.dispose();
    }
    
}
