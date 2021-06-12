package modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ObjXml {
    
    private List<String> info = new ArrayList<>();
    private String path_archivo;
    
    public int mtdObtenerCantidadVersiones(){
        int cantidad = 0;
        
        try {
            
            SAXBuilder builder = new SAXBuilder();
            File xml =  new File( path_archivo );
            
            Document documento = builder.build(xml);
            Element root = documento.getRootElement();
            
            List<Element> versiones = root.getChildren("MyFreeLab");
            
            for (int i = 0; i < versiones.size(); i++) {
                cantidad++;
            }
                
        } catch (JDOMException ex) {
            Logger.getLogger(ObjXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObjXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cantidad + 1;
    }
    
    public List<String> mtdListarUltimaVersion(){
        List<String> info = new ArrayList<>();
        
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

            info.add( campo.getChildTextTrim("app_autor") );
            info.add( campo.getChildTextTrim("app_contacto") );
            info.add( campo.getChildTextTrim("app_version") );
            info.add( campo.getChildTextTrim("app_novedades") );
            info.add( campo.getChildTextTrim("app_link_descarga") );
            
        } catch (JDOMException ex) {
            Logger.getLogger(ObjXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObjXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return info;
    }
    
    public HashMap<String, String> mtdMapearUltimaVersion(){
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

            info.put("app_autor" , campo.getChildTextTrim("app_autor") );
            info.put("app_contacto", campo.getChildTextTrim("app_contacto") );
            info.put("app_version", campo.getChildTextTrim("app_version") );
            info.put("app_novedades", campo.getChildTextTrim("app_novedades") );
            info.put("app_link_descarga", campo.getChildTextTrim("app_link_descarga") );
            
        } catch (JDOMException ex) {
            Logger.getLogger(ObjXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObjXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return info;
    }

    public String getPath_archivo() {
        return path_archivo;
    }

    public void setPath_archivo(String path_archivo) {
        this.path_archivo = path_archivo;
    }
    
}
