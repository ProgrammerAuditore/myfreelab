package modelo.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.EjecucionDto;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import src.Recursos;

public class EjecucionDao {
    
    public boolean mtdRegistrarDatos(EjecucionDto datos) {
        
        try {
            Element application = new Element("RunTime");
            Document doc = new Document(application);

            Element root = new Element("MyFreeLab");
            application.addContent(root);

            Element app = new Element("App");
            root.addContent(app);
            
            Element id = new Element("app_id");
            id.setText("" + datos.getId());
            app.addContent(id);
            
            Element pid = new Element("app_pid");
            pid.setText("" + datos.getPid());
            app.addContent(pid);
            
            Element estado = new Element("app_estado");
            estado.setText("" + datos.getEstado());
            app.addContent(estado);
            
            Element tiempo_inicial = new Element("tiempo_inicial");
            tiempo_inicial.setText("" + datos.getTiempo_inicial() );
            app.addContent(tiempo_inicial);
            
            Element tiempo_ejecucion = new Element("tiempo_ejecucion");
            tiempo_ejecucion.setText("" + datos.getTiempo_ejecucion());
            app.addContent(tiempo_ejecucion);
            
            Element tiempo_final = new Element("tiempo_final");
            tiempo_final.setText("" + datos.getTiempo_final() );
            app.addContent(tiempo_final);

            XMLOutputter xmlRun = new XMLOutputter();
            xmlRun.setFormat(Format.getPrettyFormat());
            xmlRun.output(doc, new FileWriter(Recursos.dataRun().getAbsoluteFile()));
            
            return true;
        } catch (IOException ex) {
            System.out.println("Archivo .run no creado...\n\n" + ex.getMessage());
        }

        return false;
    }
    
    public EjecucionDto mtdObetenerDatos() {
        EjecucionDto datos = null;

        File xml = new File(Recursos.dataRun().getAbsolutePath());
        InputStream is = null;
        BufferedInputStream bis = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(xml);
            is = new BufferedInputStream(fis);
            datos = new EjecucionDto();

            SAXBuilder builder = new SAXBuilder();
            Document documento = builder.build(is);
            Element root = documento.getRootElement();

            List<Element> versiones = root.getChildren("MyFreeLab");

            // * Obtener la primera etiqueta Versions
            Element version = versiones.get(0);

            //  * Obtener los elementos dentro de la etiqueta Versions
            List<Element> valores = version.getChildren();

            // * Obtener el primer nodo
            Element campo = valores.get(0);
            datos.setId( Long.parseLong(campo.getChildTextTrim("app_id")) );
            datos.setPid( Long.parseLong(campo.getChildTextTrim("app_pid")) );
            datos.setEstado( Integer.parseInt(campo.getChildTextTrim("app_estado")) );
            datos.setTiempo_inicial( Long.parseLong(campo.getChildTextTrim("tiempo_inicial")) );
            datos.setTiempo_ejecucion(Long.parseLong(campo.getChildTextTrim("tiempo_ejecucion")) );
            datos.setTiempo_final( Long.parseLong(campo.getChildTextTrim("tiempo_final")) );
            
        }catch (Exception ex) {
            Logger.getLogger(EjecucionDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                }
            }
        }

        return datos;
    }
    

}
