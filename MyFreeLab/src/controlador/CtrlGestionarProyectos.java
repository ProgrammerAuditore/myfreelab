package controlador;

import index.MyFreeLab;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ProyectoDao;
import modelo.dto.ProyectoDto;
import vista.paneles.PanelGestionarProyectos;

public class CtrlGestionarProyectos implements MouseListener{
    
    // * Vistas
    private PanelGestionarProyectos laVista;
    public JDialog modal;
    
    // * Modelos
    private ProyectoDao dao;
    private ProyectoDto dto;
    
    // * Atributos
    private String cmpProyecto;
    private DefaultTableModel modeloTabla = new DefaultTableModel();
    private List<ProyectoDto> proyectos = new ArrayList<>();

    public CtrlGestionarProyectos(PanelGestionarProyectos laVista, ProyectoDto dto, ProyectoDao dao) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
        
        // * Establecer oyentes
        this.laVista.btnBuscar.addMouseListener(this);
        this.laVista.btnCrear.addMouseListener(this);
        this.laVista.btnRemover.addMouseListener(this);
        this.laVista.btnModificar.addMouseListener(this);
        this.laVista.btnRealizado.addMouseListener(this);
        this.laVista.btnRecuperar.addMouseListener(this);
        this.laVista.btnEliminar.addMouseListener(this);
        
        // * Definir el modelo para la tabla
        modeloTabla = (DefaultTableModel) this.laVista.tblProyectos.getModel();
        this.laVista.tblProyectos.getTableHeader().setReorderingAllowed(false);
        this.laVista.tblProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.laVista.tblProyectos.setModel(modeloTabla);
        
        // * Inicializar
        //mtdInit();
    }

    public void mtdInit() {
        //modal = new JDialog();
        CtrlPrincipal.cambiosModalGestionarProyectos = false;
        
        modal.setTitle(MyFreeLab.idioma.getProperty("ctrlGestionarProyecto.mtdInit.titulo"));
        //modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize( laVista.getSize() );
        modal.setContentPane(laVista);
        
        // Verificar si hay conexion al servidor de base datos
        if( CtrlHiloConexion.ctrlEstado == true ){
           mtdRellenarTabla();
        }
        
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.setVisible(false);
                modal.dispose();
            }
        });
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        
        if( e.getSource() == laVista.btnBuscar )
            mtdBuscarProyecto();
        
        if( e.getSource() == laVista.btnCrear )
            mtdCrearProyecto();
        
        if( e.getSource() == laVista.btnModificar )
            mtdModificarProyecto();
        
        if( e.getSource() == laVista.btnRemover )
            mtdRemoverProyecto();
        
        if( e.getSource() == laVista.btnEliminar )
            mtdEliminarProyecto();
         
        if( e.getSource() == laVista.btnRealizado )
            mtdFinalizarProyecto();
        
        if( e.getSource() == laVista.btnRecuperar )
            mtdRecuperarProyecto();
    
    }
    
    private void mtdBuscarProyecto(){
        
        if( mtdValidarCampo() ){
            ////System.out.println("Buscar proyectos");
            int p = modeloTabla.getRowCount();
            boolean encontrado = false;
            
            for (int i = 0; i < p; i++) {
                String pr = String.valueOf( modeloTabla.getValueAt(i, 0) );
                
                if( pr.equals(cmpProyecto ) || modeloTabla.getValueAt(i, 1).equals( cmpProyecto ) ){
                    laVista.tblProyectos.setRowSelectionInterval(i, i);
                    encontrado = true;
                }
                
            }
            
            if( !encontrado )
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdBuscarProyecto.msg1")
                    .replace("<Proyecto>",  cmpProyecto));
            
        }
        
    }
    
    private void mtdCrearProyecto(){
       
        if( mtdValidarCampo() ){
            dto.setCmpNombre( cmpProyecto );
            dto.setCmpFechaInicial( fncObtenerFechaYHora(0) );
            dto.setCmpFechaFinal( fncObtenerFechaYHora(6) );
            dto.setCmpCreadoEn( fncObtenerFechaYHoraActual() );
            dto.setCmpCtrlEstado(1);
                
            if( !dao.mtdComprobar(dto) ){

                if(dao.mtdInsetar(dto)){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdCrearProyecto.msg1")
                    .replace("<Proyecto>", dto.getCmpNombre())
                    );
                }
                
            } else
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdCrearProyecto.msg2")
                    .replace("<Proyecto>",  dto.getCmpNombre())
            );
            
        }
        
    }
    
    private void mtdModificarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            ////System.out.println("Modificar proyectos");
            String[] msg = new String[2];
            dto = mtdObtenerProyecto(seleccionado);
            
            ////System.out.println("FF = " + dto.getCmpFechaFinal() + " FI = " + dto.getCmpFechaInicial());
            if( !mtdValidarDatos(dto) ){
                return;
            }
            
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdModificarProyecto.msg1") + " | " + dto.getCmpNombre();
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdModificarProyecto.msg2");
            int opc = JOptionPane.showConfirmDialog(laVista, msg[0], msg[1], JOptionPane.YES_NO_OPTION);

            if( opc == JOptionPane.YES_OPTION){
                dto.setCmpActualizadoEn( fncObtenerFechaYHoraActual() );

                if(dao.mtdActualizar(dto)){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdModificarProyecto.msg3")
                    .replace("<Proyecto>", dto.getCmpNombre())
                    );
                }

            }
                
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdModificarProyecto.msg4"));
        
    }
    
    private void mtdRemoverProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            
            if( !mtdValidarDatos(dto) ){
                return;
            }
            
            String[] msg =  new String[2];
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRemoverProyecto.msg1")+" | "+ dto.getCmpNombre();
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRemoverProyecto.msg2");
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdRemover(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    modeloTabla.removeRow(seleccionado);
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRemoverProyecto.msg3")
                    .replace("<Proyecto>", dto.getCmpNombre())
                    );
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRemoverProyecto.msg4"));
        
    }
    
    private void mtdEliminarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            
            if( !mtdValidarDatos(dto) ){
                return;
            }
            
            String[] msg =  new String[2];
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdEliminarProyecto.msg1")+" | "+ dto.getCmpNombre();
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdEliminarProyecto.msg2");
            
            // * Verificar si el proyecto está eliminado
            if( dto.getCmpCtrlEstado() == 0 ){
                JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdEliminarProyecto.msg3"));
                return;
            }
            
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                dto.setCmpCtrlEstado(0);
                dto.setCmpActualizadoEn( fncObtenerFechaYHoraActual() );
                
                if( dao.mtdActualizar(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdEliminarProyecto.msg4")
                    .replace("<Proyecto>", dto.getCmpNombre())
                    );
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdEliminarProyecto.msg5"));
    
    }
    
    private void mtdRecuperarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            
            if( !mtdValidarDatos(dto) ){
                return;
            }
            
            String[] msg =  new String[2];
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRecuperarProyecto.msg1")+" | "+ dto.getCmpNombre();
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRecuperarProyecto.msg2");
            
            // * Verificar si el proyecto está en proceso
            if( dto.getCmpCtrlEstado() > 0 && dto.getCmpCtrlEstado() <= 50 ){
                JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRecuperarProyecto.msg3"));
                return;
            }
            
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                dto.setCmpCtrlEstado(50);
                dto.setCmpActualizadoEn( fncObtenerFechaYHoraActual() );
                
                if( dao.mtdActualizar(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRecuperarProyecto.msg4")
                    .replace("<Proyecto>", dto.getCmpNombre())
                    );
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdRecuperarProyecto.msg5"));
    
    }
    
    private void mtdFinalizarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            
            if( !mtdValidarDatos(dto) ){
                return;
            }
            
            String[] msg =  new String[3];
            msg[0] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdFinalizarProyecto.msg1")+" | "+ dto.getCmpNombre();
            msg[1] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdFinalizarProyecto.msg2");
            msg[2] = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdFinalizarProyecto.msg3");
            
            // * Verificar si se puede finalizar
            if(dto.getCmpCtrlEstado() > 50){
                JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdFinalizarProyecto.msg4"));
                return;
            }else
            if(dto.getCmpCostoEstimado() == 0){
                int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
                if( opc == JOptionPane.NO_OPTION) return;
            }
            
            int opc = JOptionPane.showConfirmDialog(laVista, msg[2] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                dto.setCmpCtrlEstado(100);
                dto.setCmpActualizadoEn( fncObtenerFechaYHoraActual() );
                
                if( dao.mtdActualizar(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdFinalizarProyecto.msg5")
                    .replace("<Proyecto>", dto.getCmpNombre())
                    );
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdFinalizarProyecto.msg6"));
    }
    
    private ProyectoDto mtdObtenerProyecto(int fila){
        ProyectoDto p = proyectos.get(fila);
        
        p.setCmpID( Integer.parseInt( laVista.tblProyectos.getValueAt(fila, 0).toString() ) );
        p.setCmpNombre( String.valueOf( laVista.tblProyectos.getValueAt(fila, 1)) );
        p.setCmpFechaInicial(String.valueOf( laVista.tblProyectos.getValueAt(fila, 2)) );
        p.setCmpFechaFinal(String.valueOf( laVista.tblProyectos.getValueAt(fila, 3)) );
        p.setCmpMontoAdelantado(Double.parseDouble( laVista.tblProyectos.getValueAt(fila, 4).toString() ) );
        p.setCmpCostoEstimado( Double.parseDouble( laVista.tblProyectos.getValueAt(fila, 5).toString() ) );
        
        return p;
    }
    
    private void mtdRellenarTabla() {
        mtdVaciarTabla();
        proyectos = dao.mtdListar();
            
        if( proyectos.size() > 0 )
            mtdAgregarProyectos();
    }
    
    private void mtdVaciarTabla(){
        while( modeloTabla.getRowCount() > 0){
            modeloTabla.removeRow(0);
        }
    }
    
    private void mtdAgregarProyectos(){
        int num_filas = proyectos.size();

        for (int fila = 0; fila < num_filas; fila++) {
            mtdAgregarProyecto();
        }
    }
    
    private void mtdAgregarProyecto(){
        int fila = modeloTabla.getRowCount();
        modeloTabla.setNumRows( fila + 1 );
        
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpID() , fila, 0);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpNombre(), fila, 1);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpFechaInicial(), fila, 2);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpFechaFinal(), fila, 3);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpMontoAdelantado(), fila, 4);
        laVista.tblProyectos.setValueAt(proyectos.get(fila).getCmpCostoEstimado(), fila, 5);
        laVista.tblProyectos.setValueAt(mtdObtenerEstado(proyectos.get(fila).getCmpCtrlEstado()), fila, 6);
        
    }
    
    private boolean mtdValidarCampo(){
        String cmp = laVista.cmpProyecto.getText().trim();

        if( laVista.cmpProyecto.isAprobado() == false || cmp.isEmpty() ){
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarCampo.msg1"));
            return false;
        } else if( cmp.length() > 30 ) {
            JOptionPane.showMessageDialog(laVista, MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarCampo.msg2"));
            return false;
        }
        
        cmpProyecto = cmp;
        laVista.cmpProyecto.setText(null);
        return true;
    }
    
    private boolean mtdFormatoFecha(String cmpFecha){
        int formato = 0;
        int entero = 0;
        
        // dd-mm-aaaa
        if( cmpFecha.length() > 10 )
            return false;
        
        for (int i = 0; i < cmpFecha.length(); i++) {
            Boolean flag = Character.isDigit(cmpFecha.charAt(i));

            if( (cmpFecha.charAt(i) == '/' || cmpFecha.charAt(i) == '-') && i == 2 )
                formato++;
                    
            if( (cmpFecha.charAt(i) == '/' || cmpFecha.charAt(i) == '-') && i == 5  )
                formato++;

            if( flag ) entero++;
            
        }
            
        //System.out.println("Resultado :" + formato + " : " + entero + " :: " + cmpFecha.length());
        return ( (formato + entero) == 10  );
    }

    private String fncObtenerFechaYHora(int N){
        LocalDate fecha = LocalDate.now().plusMonths(N);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateado = fecha.format(formato);
        
        // Formato : dd/MM/YYYY
        return fechaFormateado;
    }
    
    private String mtdObtenerEstado(int estado){
        if( estado <= 0 ){
            return "Eliminado";
        }else
        if( estado >= 100 ){
            return "Realizado";
        }else
        return "En Proceso";
    }
    
    private boolean mtdValidarDatos(ProyectoDto proyecto){  
        int errores = 0;
        String msg = MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg1");
        
        //System.out.println("" + proyecto.toString());
        //System.out.println("" + mtdFormatoFecha(proyecto.getCmpFechaInicial()));
        if( mtdFormatoFecha(proyecto.getCmpFechaInicial()) == false ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg2");
            
        }
        
        //System.out.println("" + mtdFormatoFecha(proyecto.getCmpFechaFinal()));
        if( mtdFormatoFecha(proyecto.getCmpFechaFinal()) == false ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg3");
        
        }
        
        if( errores == 0 ){
            String fechaInicial = mtdFormatearFechas(proyecto.getCmpFechaInicial());
            String fechaFinal = mtdFormatearFechas(proyecto.getCmpFechaFinal());
            long diffDias = fncObtenerDiferenciasEnDias(fechaInicial, fechaFinal);
            //System.out.println( fechaInicial );
            //System.out.println( fechaFinal );
            //System.out.println( diffDias );
            
            if( diffDias < 0 ){
                errores++;
                msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg4");
            }
            
        }
        
        if( proyecto.getCmpNombre().isEmpty() ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg5");
            
        } else if( proyecto.getCmpNombre().length() > 30 ) {
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg6");
            
        }
        
        if( proyecto.getCmpMontoAdelantado() < 0 ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg7");
        
        }
        
        if( proyecto.getCmpMontoAdelantado() < 0 ){
            errores++;
            msg += MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg8");
        
        }
        
        //System.out.println("Errores : " + errores );
        if( errores > 0 ){
            JOptionPane.showMessageDialog(laVista, msg, 
                    MyFreeLab.idioma
                    .getProperty("ctrlGestionarProyecto.mtdValidarDatos.msg9")
                    + " | "+proyecto.getCmpNombre(), JOptionPane.YES_OPTION);
            return false;
        }
        
        return true;
    }
    
    private long fncObtenerDiferenciasEnDias(String fechaInicial, String fechaFinal){
        long difference_In_Days = 0;
        SimpleDateFormat sdf
            = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");
        
        try {
            Date d1 = sdf.parse(fechaInicial);
            Date d2 = sdf.parse(fechaFinal);
            
            // * Calcular el tiempo en milisegundos
            long difference_In_Time
                = d2.getTime() - d1.getTime();
            
            // * Calculadndo la diferencia en dias 
            difference_In_Days
                = TimeUnit
                      .MILLISECONDS
                      .toDays(difference_In_Time)
                  % 365;
            
        } catch (ParseException e) {
            //System.out.println("Mensaje de Error \n****\n" + e.getMessage()+"***\n");
        }
        
        return difference_In_Days;
    }
    
    private String mtdFormatearFechas(String fecha){
        // 01/12/2022
        String dia = fecha.substring(0, 2);
        String mes = fecha.substring(3, 5);
        String anho =  fecha.substring(6, 10);
        String horario = "00:00:00";
        return dia+"-"+mes+"-"+anho+" "+horario;
    }
    
    private String fncObtenerFechaYHoraActual(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
        
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
