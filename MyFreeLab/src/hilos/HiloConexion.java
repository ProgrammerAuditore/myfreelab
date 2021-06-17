package hilos;

import controlador.CtrlHiloConexion;

// * Este hilo se sincroniza con el Hilo Principal
public class HiloConexion extends Thread {

    public HiloConexion() {
        this.setName("HiloConexion");
    }
    
    @Override
    public void run() {

        // Ejecutar el hilo en segundo plano
        while (true) {
            synchronized (CtrlHiloConexion.ctrlHiloC) {

                while (CtrlHiloConexion.ctrlEstado == true) {
                    try {
                        // * Esperaré a terminar la conexion
                        //System.out.println("HiloConexion :: Wait... ");
                        //System.out.println(" Esperaré a terminar la conexion ");
                        CtrlHiloConexion.ctrlHiloC.wait();
                    } catch (Exception e) {
                    }
                }

                // * Conexion cerrada
                //System.out.println("HiloConexion :: Funcionando... ");
                try {

                    // Verificando cada 1s Si la conexin está establecida 
                    if (CtrlHiloConexion.ctrlConexion != null) {
                        if (CtrlHiloConexion.ctrlConexion.isValid(1000)) {
                            CtrlHiloConexion.ctrlEstado = true;
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
