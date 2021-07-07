package controlador;

import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ProyectoDao;
import modelo.dto.ProyectoDto;
import src.Source;
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
        
        modal.setTitle("Gestionar proyectos");
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
            JOptionPane.showMessageDialog(laVista, "El proyecto `"+ cmpProyecto +"` no existe.");
            
        }
        
    }
    
    private void mtdCrearProyecto(){
       
        if( mtdValidarCampo() ){
            ////System.out.println("Crear proyectos");
            dto.setCmpNombre( cmpProyecto );
            dto.setCmpFechaInicial( fncObtenerFecha(0) );
            dto.setCmpFechaFinal( fncObtenerFecha(6) );
            dto.setCmpCreadoEn( fncObtenerFechaYHora(0) );
            dto.setCmpCtrlEstado(1);
            
            if( !dao.mtdComprobar(dto) ){
            
                if(dao.mtdInsetar(dto)){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` se creó exitosamente.");
                }
                
            } else
            JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` ya existe.");
            
        }
        
    }
    
    private void mtdModificarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            ////System.out.println("Modificar proyectos");
            String[] msg = new String[2];
            dto = mtdObtenerProyecto(seleccionado);
            dto.setCmpActualizadoEn( fncObtenerFechaYHora(0) );
            
            ////System.out.println("FF = " + dto.getCmpFechaFinal() + " FI = " + dto.getCmpFechaInicial());
            if( mtdFormatoFecha(dto.getCmpFechaInicial()) && mtdFormatoFecha(dto.getCmpFechaFinal())  ){
                msg[1] = "Modificar proyecto";
                msg[0] = "¿Seguro que deseas modificar el proyecto seleccionado?";
                int opc = JOptionPane.showConfirmDialog(laVista, msg[0], msg[1], JOptionPane.YES_NO_OPTION);

                if( opc == JOptionPane.YES_OPTION){

                    if(dao.mtdActualizar(dto)){
                        // * Notificar al controlador principal
                        CtrlPrincipal.modificacionesCard = true;
                        mtdRellenarTabla();
                        JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` se modificó exitosamente.");
                    }

                }
            } else
            JOptionPane.showMessageDialog(laVista, "El formato de fecha es incorrecto, debe ser dd-mm-aaaa o dd/mm/aaaa.");
                
        } else
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para modificar un proyecto.");
        
    }
    
    private void mtdRemoverProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            String[] msg =  new String[2];
            ////System.out.println("Remover proyectos");
            
            msg[0] = "Remover proyecto";
            msg[1] = "¿Seguro que deseas remover el proyecto seleccionado?";
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                if( dao.mtdRemover(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    modeloTabla.removeRow(seleccionado);
                    JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` se removió exitosamente.");
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para eliminar un proyecto.");
        
    }
    
    private void mtdEliminarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            String[] msg =  new String[2];
            msg[0] = "Eliminar proyecto";
            msg[1] = "¿Seguro que deseas eliminar el proyecto seleccionado?";
            
            // * Verificar si el proyecto está eliminado
            if( dto.getCmpCtrlEstado() == 0 ){
                JOptionPane.showMessageDialog(null, "El proyecto seleccionado está eliminado.");
                return;
            }
            
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                dto.setCmpCtrlEstado(0);
                dto.setCmpActualizadoEn(Source.fechayHora);
                
                if( dao.mtdActualizar(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` se eliminó exitosamente.");
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para eliminar un proyecto.");
    
    }
    
    private void mtdRecuperarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            
            String[] msg =  new String[2];
            msg[0] = "Recuperar proyecto";
            msg[1] = "¿Seguro que deseas recuperar el proyecto seleccionado?";
            
            // * Verificar si el proyecto está en proceso
            if( dto.getCmpCtrlEstado() > 0 && dto.getCmpCtrlEstado() <= 50 ){
                JOptionPane.showMessageDialog(laVista, "El proyecto seleccionado está en proceso.");
                return;
            }
            
            int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                dto.setCmpCtrlEstado(50);
                dto.setCmpActualizadoEn(Source.fechayHora);
                
                if( dao.mtdActualizar(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` se recupero exitosamente.");
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para eliminar un proyecto.");
    
    }
    
    private void mtdFinalizarProyecto(){
        int seleccionado = laVista.tblProyectos.getSelectedRow();
        
        if( seleccionado >= 0 ){
            dto = mtdObtenerProyecto(seleccionado);
            
            String[] msg =  new String[3];
            msg[0] = "Finalizar proyecto";
            msg[1] = "¿El proyecto no tiene un costo estimado\nseguro que deseas continuar?";
            msg[2] = "¿Seguro que deseas finalizar el proyecto seleccionado?";
            
            // * Verificar si se puede finalizar
            if(dto.getCmpCtrlEstado() > 50){
                JOptionPane.showMessageDialog(laVista, "El proyecto seleccionado está finalizado.");
                return;
            }else
            if(dto.getCmpCostoEstimado() == 0){
                int opc = JOptionPane.showConfirmDialog(laVista, msg[1] , msg[0], JOptionPane.YES_NO_OPTION);
                if( opc == JOptionPane.NO_OPTION) return;
            }
            
            int opc = JOptionPane.showConfirmDialog(laVista, msg[2] , msg[0], JOptionPane.YES_NO_OPTION);
            
            if( opc == JOptionPane.YES_OPTION ){
                dto.setCmpCtrlEstado(100);
                dto.setCmpActualizadoEn(Source.fechayHora);
                
                if( dao.mtdActualizar(dto) ){
                    // * Notificar al controlador principal
                    CtrlPrincipal.modificacionesCard = true;
                    mtdRellenarTabla();
                    JOptionPane.showMessageDialog(laVista, "El proyecto `" + dto.getCmpNombre() + "` finalizó exitosamente.");
                }
            }
            
        } else
        JOptionPane.showMessageDialog(laVista, "Selecciona una fila para eliminar un proyecto.");
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
            JOptionPane.showMessageDialog(laVista, "Verifica que el campo sea un dato valido.");
            return false;
        } else if( cmp.length() > 30 ) {
            JOptionPane.showMessageDialog(laVista, "El campo tiene que ser menor a 30 caracteres.");
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
            
        ////System.out.println("Resultado :" + formato + " : " + entero + " :: " + cmpFecha.length());
        return ( (formato + entero) == cmpFecha.length()  );
    }
    
    private String fncObtenerFecha(int N){
        Calendar fechaActual = Calendar.getInstance();
        String cadenaFecha = String.format("%02d/%02d/%04d",
          fechaActual.get(Calendar.DAY_OF_MONTH),
          fechaActual.get(Calendar.MONTH)+(1+ N),
          fechaActual.get(Calendar.YEAR));
        
        return cadenaFecha;
    }
    
    private String fncObtenerFechaYHora(int N){
        Calendar fechaActual = Calendar.getInstance();
        String cadenaFecha = String.format("%02d/%02d/%04d",
          fechaActual.get(Calendar.YEAR),
          fechaActual.get(Calendar.MONTH)+(1+ N),
          fechaActual.get(Calendar.DAY_OF_MONTH));
        
        Calendar a = Calendar.getInstance();
        String horaActual = String.format("%02d:%02d:%02d",
          fechaActual.get(Calendar.HOUR_OF_DAY),
          fechaActual.get(Calendar.MINUTE),
          fechaActual.get(Calendar.SECOND));
        
        return cadenaFecha+" "+horaActual+"";
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
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
