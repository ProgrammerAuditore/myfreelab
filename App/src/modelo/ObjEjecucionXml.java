package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import src.Source;

public class ObjEjecucionXml {
    
    private List<String> info = new ArrayList<>();
    private String path_archivo;
    
    public HashMap<String, String> mtdMapearXmlRun(){
        HashMap<String, String> info = new HashMap<String, String>();
        
        try {
            
            SAXBuilder builder = new SAXBuilder();
            File xml =  new File( path_archivo );
            
            Document documento = builder.build(xml);
            Element root = documento.getRootElement();
            
            List<Element> versiones = root.getChildren("MyFreeLab");
            
            // * Obtener la primera etiqueta Versions
            Element version = versiones.get(0);
                
            //  * Obtener los elementos dentro de la etiqueta Versions
            List<Element> valores = version.getChildren();
                
            // * Obtener el primer nodo
            Element campo = valores.get(0);

            info.put("app_pid" , campo.getChildTextTrim("app_pid") );
            
        } catch (JDOMException ex) {
            //Logger.getLogger(ObjEjecucionXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(ObjEjecucionXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return info;
    }
    
    public boolean mtdGenerarXmlRun(){
        try {
            Element application = new Element("RunTime");
            Document doc = new  Document(application);
            
            Element root = new Element("MyFreeLab");
            application.addContent(root);
            
            Element app = new Element("App");
            root.addContent(app);
            
            Element pid = new Element("app_pid");
            pid.setText(""+Source.PID);
            app.addContent(pid);
            
            XMLOutputter xmlRun = new XMLOutputter();
            xmlRun.setFormat(Format.getPrettyFormat());
            xmlRun.output(doc, new FileWriter( Source.dataRun.getAbsoluteFile() ));
            
            return true;
        } catch (IOException ex) {
            System.out.println("Archivo .run no creado...\n\n" + ex.getMessage());
        }
        
        return false;
    }

    public String getPath_archivo() {
        return path_archivo;
    }

    public void setPath_archivo(String path_archivo) {
        this.path_archivo = path_archivo;
    }
    
}
