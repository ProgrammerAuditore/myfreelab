package hilos;

import controlador.CtrlHiloConexion;
import static controlador.CtrlHiloConexion.ctrlConexion;
import static controlador.CtrlHiloConexion.ctrlConn;

// * Este hilo se sincroniza con el Hilo Conexion
public class HiloPrincipal extends Thread {

    public HiloPrincipal() {
        this.setName("HiloPrincipal");
    }

    @Override
    public void run() {

        // Ejecutar el hilo en segundo plano
        while (true) {
            synchronized (CtrlHiloConexion.ctrlHiloC) {

                while (CtrlHiloConexion.ctrlEstado == false) {
                    try {
                        // * Esperaré para establecer la conexion
                        //System.out.println("HiloPrincipal :: Wait... ");
                        //System.out.println(" Esperaré para establecer la conexion : Estado " + CtrlHiloConexion.ctrlEstado);
                        CtrlHiloConexion.ctrlHiloC.wait();
                    } catch (Exception e) {
                    }
                }

                // * Conexion establecida
                //System.out.println("HiloPrincipal :: Funcionando... ");
                //System.out.println("...");
                try {

                    // Verificando cada 1s Si la conexin está cerrada 
                    if (ctrlConn == null && ctrlConexion == null) {
                        //System.out.println("La conexion esta cerrada");
                        CtrlHiloConexion.ctrlEstado = false;
                        CtrlHiloConexion.ctrlHiloC.notify();
                    } else {

                        // Validar la conexión actual (?) 
                        /// Si Conexion Es Falso : Se cierra la conexion
                        if (CtrlHiloConexion.ctrlConexion.isValid(1000) == false) {
                            CtrlHiloConexion.ctrlEstado = false;
                            CtrlHiloConexion.ctrlHiloC.notify();
                        }

                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }
        }
    }

}
