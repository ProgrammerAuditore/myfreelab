package controlador;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import modelo.dao.EmpresaDao;
import modelo.dao.ProyectoDao;
import vista.componentes.boton.Boton;
import vista.ventanas.VentanaPrincipal;

abstract class CtrlPaginacionAbstracto{
    public abstract void mtdBorrar();
    public abstract void mtdActualizar();
}

public class CtrlPaginacion {
    
    // * Vista
    JPanel laVista;
    
    // * Modelo
    EmpresaDao daoE;
    ProyectoDao daoP;
    
    // * Atributos
    CtrlPaginacionAbstracto mtd;
    int ctrlNumTotalDeRegistros;
    private int ctrlPaginacionInicio;
    private int ctrlPaginacionFin;
    private int ctrlPaginacionSeleccion;
    private int numPaginacionActual;
    private boolean cargarRegistros;
    public int ctrlRegistrosPorPagina;
    public int ctrlNumRegistrosDesplazados;
    
    public CtrlPaginacion(JPanel lavista, EmpresaDao daoE, ProyectoDao daoP) {
        this.laVista = lavista;
        this.daoE = daoE;
        this.daoP = daoP;
        
        mtdInit();
    }
    
    private void mtdInit(){
        ctrlNumTotalDeRegistros = 0;
    }
    
    public void mtdCrearPaginacion(){
        mtdVaciarPaginacion();
        ctrlRegistrosPorPagina = 25;
        ctrlNumTotalDeRegistros = 0;
        ctrlPaginacionInicio = 1;
        ctrlPaginacionFin = 0;
        ctrlPaginacionSeleccion = 0;
        
        CtrlPrincipal.ctrlBarraEstadoNumEmpresas = (int) daoE.mtdRowCount();
        ctrlNumTotalDeRegistros += CtrlPrincipal.ctrlBarraEstadoNumEmpresas;
        
        CtrlPrincipal.ctrlBarraEstadoNumProyectos = (int)  daoP.mtdRowCount();
        ctrlNumTotalDeRegistros += CtrlPrincipal.ctrlBarraEstadoNumProyectos;
        
        // Registros 150 <-> 15 botones 
        ctrlPaginacionFin = ctrlNumTotalDeRegistros / ctrlRegistrosPorPagina;
        
        //System.out.println("Total de registros: " + ctrlNumTotalDeRegistros);
        //System.out.println("SubTotal de paginas: " + ctrlPaginacionFin);
        //System.out.println("Registros de sobra: " + (ctrlNumTotalDeRegistros%10));
        
        if( (ctrlNumTotalDeRegistros%ctrlRegistrosPorPagina) > 0 ){
            ctrlPaginacionFin = ctrlPaginacionFin + 1;
        }
        
        //ctrlPaginacionFin = 20;
        //System.out.println("Total de paginas: " + ctrlPaginacionFin);
        for (int i = 0; i < ctrlPaginacionFin; i++) {
            
            String numeracion = "" + i;
            Boton btn = new Boton();
            btn.setImgButtonType("peace");
            btn.setTexto(numeracion);
            
            if( i == 0){
                    btn.setTexto("Home");
                    btn.setImgButtonType("primary");
                    numPaginacionActual = i;
                    ctrlPaginacionInicio = 0;
                    ctrlNumRegistrosDesplazados = (int) (i * ctrlRegistrosPorPagina);
                    
                    if( (ctrlPaginacionFin-1) == 0 ){
                        VentanaPrincipal.etqPaginacion.setText( null );
                    }else{
                        VentanaPrincipal.etqPaginacion.setText( "Home "+" / Pág. "+(ctrlPaginacionFin-1) );
                    }
            }
            
            if( ctrlPaginacionFin > 11 ){
                if( i == 11 ){
                    btn.setTexto(">>");
                    btn.setImgButtonType("danger");
                }
            }
            
            if( i > 11 ){
                //System.out.println("Pagina "+i+" omitido");
                continue;
            }
            
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mtdEventoPressed(btn, e);
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    mtdEventoReleased(btn, e);
                }
                
            });
            
            Dimension tam = new Dimension(50, 25);
            btn.setSize(tam);
            btn.setPreferredSize(tam);
            btn.setLocation(i*52, 0);
            btn.setVisible(true);
            laVista.add(btn);
            
        }
        
        laVista.validate();
        laVista.revalidate();
        laVista.repaint();
        laVista.setVisible(true);
        
    }

    public void mtdVaciarPaginacion() {
        VentanaPrincipal.etqPaginacion.setText(null);
        laVista.removeAll();
        laVista.validate();
        laVista.revalidate();
        laVista.repaint();
    }

    public void mtdActualizarPaginacion(){
        
        Runnable actualizar = () -> {
            Component[] botones = laVista.getComponents();
            
            // * Actualizar la númeración
            mtdActualizarNumeracion(botones);
            
            // * Actualizar estilos
            mtdActualizarEstilos(botones);
        };
        
        Thread HiloActualizarPaginacion = new Thread(actualizar);
        HiloActualizarPaginacion.setName("");
        HiloActualizarPaginacion.setPriority(9);
        HiloActualizarPaginacion.run();
    
    }
    
    private void mtdActualizarNumeracion(Component[] botones){
        if( ctrlPaginacionSeleccion > 9 ){
            int contador = ctrlPaginacionSeleccion;
            for (int i = botones.length; i > 0; i--) {
                Boton a = (Boton) botones[i-1];
                //System.out.println(a.getTexto());

                if( a.getTexto().equals("End") ){
                    a.setTexto(">>");
                }else if( a.getTexto().equals("Home") ){
                    a.setTexto("<<");
                }

                if(a.getTexto().equals("<<") || a.getTexto().equals(">>") ){
                    continue;
                }

                a.setTexto("" + (contador));
                contador--;
            }
        }
    }
    
    private void mtdActualizarEstilos(Component[] botones){
        for( Component link : botones ){
            Boton a = (Boton) link;
            if( a.getTexto().equals( ""+ctrlPaginacionSeleccion )){
                a.setImgButtonType("dark");
            }else{
                a.setImgButtonType("peace");
            }
            
            if( a.getTexto().equals("<<") || a.getTexto().equals("Home") ){
                a.setImgButtonType("primary");
            }

            if( a.getTexto().equals(">>") || a.getTexto().equals("End")  ){
                a.setImgButtonType("danger");
            }            
        }
    }
    
    private void mtdEventoPressed(Boton btn, MouseEvent e) {
        synchronized( e ){
            if( btn.getTexto().equals("<<")){
                ctrlPaginacionSeleccion = numPaginacionActual - 1;
            }else
            if( btn.getTexto().equals(">>")){
                ctrlPaginacionSeleccion = numPaginacionActual + 1;
            }else
            if( btn.getTexto().equals("Home")){
                ctrlPaginacionSeleccion = 0;
            }else if( btn.getTexto().equals("End") ){
                ctrlPaginacionSeleccion = ctrlPaginacionFin;
            }else {
                ctrlPaginacionSeleccion = Integer.parseInt(btn.getTexto());
            }

            if( ctrlPaginacionSeleccion == numPaginacionActual || ctrlPaginacionSeleccion < 0  ){
                    e.consume();
                    return;
            }

            Runnable paginacionVaciar = () -> {
                mtd.mtdBorrar();
            };

            Thread HiloPaginacionVaciar = new Thread(paginacionVaciar);
            HiloPaginacionVaciar.setName("HiloPaginacionVaciar");
            HiloPaginacionVaciar.setPriority(9);
            HiloPaginacionVaciar.run();
        }
    }
    
    private void mtdEventoReleased(Boton btn, MouseEvent e) {
        synchronized( e ){
            //System.out.println("Cargar registros: " + cargarRegistros);

            // * Verificar si la página ya fue cargando
            if( ctrlPaginacionSeleccion == numPaginacionActual || ctrlPaginacionSeleccion < 0  ){
                e.consume();
                return;
            }

            mtdActualizarPaginacion();
            ctrlNumRegistrosDesplazados = (int) (ctrlPaginacionSeleccion * ctrlRegistrosPorPagina);
            //System.out.println("Cargando registros = " + numMostrarTotalRegistros +
            //" : " + ctrlNumRegistrosDesplazados);

            Runnable paginacion = () -> {
                //System.out.println("Paginar: " + ctrlPaginacionSeleccion);
                if( ctrlPaginacionSeleccion == 0 ){
                        btn.setTexto("Home");
                        VentanaPrincipal.etqPaginacion.setText( "Home "+" / Pág. "+(ctrlPaginacionFin-1) );
                }else{
                    VentanaPrincipal.etqPaginacion.setText( "Pág. "+ctrlPaginacionSeleccion+" / Pág. "+(ctrlPaginacionFin-1) );
                }

                if( ctrlPaginacionFin > 11 ){
                    if( ctrlPaginacionSeleccion == ctrlPaginacionFin ){
                        btn.setTexto("End");
                        VentanaPrincipal.etqPaginacion.setText( "End "+" / Pág. "+(ctrlPaginacionFin-1) );
                    }
                }

                mtd.mtdActualizar();
                cargarRegistros = true;
                numPaginacionActual = ctrlPaginacionSeleccion;
                e.notify();
            };


            Thread HiloPaginacionPaginar = new Thread(paginacion);
            HiloPaginacionPaginar.setName("HiloPaginacionPaginar");
            HiloPaginacionPaginar.setPriority(9);
            HiloPaginacionPaginar.run();

        }
    }
    
    public void mtdEventos_Y_Acciones(CtrlPaginacionAbstracto mtd){
        this.mtd = mtd;
    }

}
