package hilos;

import controlador.CtrlHiloConexion;
import index.MyFreeLab;
import modelo.dao.EjecucionDao;
import modelo.dao.ConexionDao;
import modelo.dao.MyFreeLabDao;
import modelo.dao.PreferenciaDao;
import modelo.dto.ConexionDto;
import modelo.dto.PreferenciaDto;
import src.Info;
import src.Recursos;
import vista.splash.Splash;

public class HiloSplash extends Thread{
    
    private Splash splash;
    private int src;
    private int avance;
    private int pause;
    
    @Override
    public void run() {
        
        // * Splash de bienvenida
        this.setName("HiloSplash");
        splash = new Splash();
        splash.setIconImage(Recursos.imgIconoDefault());
        splash.setTitle(Info.NombreSoftware);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        src = 9; avance = 0; pause = src;
        
        mtdVerificarID();
        mtdCargarConfiguracion();
        mtdCargarDatosDeConexion();
        mtdEstablecerConexion();
        mtdCargarBaseDeDatos();
        mtdCargarTablaDatosPersonales();
        mtdCargarTablaProyectos();
        mtdCargarTablaEmpresas();
        mtdCargarTablaRequisitos();
        mtdCargarTablaAsociados();
        
        splash.pbProgreso.setValue(100);
        splash.etqCarga.setText("100%");
                
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        
        splash.setVisible(false);
        splash.dispose();
    }
    
    private void mtdVerificarID(){
        // ***** FASE 2 | Verificar ID
        //System.out.println("***** FASE 2 | Verificar ID");
        MyFreeLab.mtdVerificarID();
    }
    
    private void mtdCargarConfiguracion(){
        // * Cargar los datos de conexion..
        Recursos.dataConexion().exists();
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarConfiguracion.msg1"));
        
        // Proceso de carga
        if( new PreferenciaDao().obtener_datos() != null ){
            splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarConfiguracion.msg2"));
            //System.out.println("Datos de conexión cargado. [!]");
        }else {
            PreferenciaDto pre = new PreferenciaDto();
            new PreferenciaDao().regitrar_datos(pre);
            splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarConfiguracion.msg3"));
            //System.out.println("Datos de conexión creado. [!]");
        }
        
        mtdTiempoDeEspera();
    }
    
    private void mtdCargarDatosDeConexion(){
        // * Cargar los datos de conexion..
        Recursos.dataConexion().exists();
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarDatosDeConexion.msg1"));

        // Proceso de carga
        if( new ConexionDao().obtener_datos() != null ){
            splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarDatosDeConexion.msg2"));
            //System.out.println("Datos de conexión cargado. [!]");
        }else {
            ConexionDto conec = new ConexionDto("0", "", "", "", "");
            new ConexionDao().regitrar_datos(conec);
            splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarDatosDeConexion.msg3"));
            //System.out.println("Datos de conexión creado. [!]");
        }

        mtdTiempoDeEspera();
    }
    
    private void mtdEstablecerConexion(){
        // * Establecer conexion..
        CtrlHiloConexion.ctrlDatos = new ConexionDao().obtener_datos();
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdEstablecerConexion.msg1"));
        
        // Proceso de carga
        if(CtrlHiloConexion.mtdEstablecer())
            splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.on"));
        else
            splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    private void mtdCargarBaseDeDatos() {
        // * Cargar tabla datos personales..
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarBaseDeDatos.msg1"));

        // Proceso de carga
        if( CtrlHiloConexion.checkConexion() ){
            if( !MyFreeLabDao.mtdCrearBaseDeDatos())
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarBaseDeDatos.msg2"));
            else
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarBaseDeDatos.msg3"));
        } else
        splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    
    private void mtdCargarTablaDatosPersonales(){
        // * Cargar tabla datos personales..
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaDatosPersonales.msg1"));

        // Proceso de carga
        if( CtrlHiloConexion.checkConexion() ){
            if( !MyFreeLabDao.mtdCrearTablaDatosPersonales() )
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaDatosPersonales.msg2"));
            else
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaDatosPersonales.msg3"));
        } else
        splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    private void mtdCargarTablaProyectos(){
        // * Cargar tabla proyectos..
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaProyectos.msg1"));

        // Proceso de carga
        if( CtrlHiloConexion.checkConexion() ){
            if( !MyFreeLabDao.mtdCrearTablaProyectos() )
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaProyectos.msg2"));
            else
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaProyectos.msg3"));
        } else
        splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    private void mtdCargarTablaEmpresas() {
        // * Cargar tabla datos personales..
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaEmpresas.msg1"));

        // Proceso de carga
        if( CtrlHiloConexion.checkConexion() ){
            if( !MyFreeLabDao.mtdCrearTablaEmpresas())
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaEmpresas.msg2"));
            else
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaEmpresas.msg3"));
        } else
        splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    private void mtdCargarTablaRequisitos() {
        // * Cargar tabla requisitos..
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaRequisitos.msg1"));

        // Proceso de carga
        if( CtrlHiloConexion.checkConexion() ){
            if( !MyFreeLabDao.mtdCrearTablaRequisitos())
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaRequisitos.msg2"));
            else
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaRequisitos.msg3"));
        } else
        splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    private void mtdCargarTablaAsociados() {
        // * Cargar tabla requisitos..
            
        // Titulo - Carga
        mtdCargarTitulo(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaAsociados.msg1"));

        // Proceso de carga
        if( CtrlHiloConexion.checkConexion() ){
            if( !MyFreeLabDao.mtdCrearTablaAsociados())
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaAsociados.msg2"));
            else
                splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.mtdCargarTablaAsociados.msg3"));
        } else
        splash.etqMensaje.setText(MyFreeLab.idioma
                .getProperty("hiloSplash.conexion.off"));

        mtdTiempoDeEspera();
    }
    
    private void mtdCargarTitulo(String titulo){
        splash.etqMensaje.setText(titulo);
        try {  Thread.sleep( avance * pause ); } catch (Exception e) {}
    } 
    
    private void mtdTiempoDeEspera(){
        avance += (100 / src);
        splash.pbProgreso.setValue(avance);
        splash.etqCarga.setText("" + avance + "%");

        try {  Thread.sleep( avance * pause ); } catch (Exception e) {}
    }
    
}
