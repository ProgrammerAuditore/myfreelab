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

public class ObjVersionesXml {
    
    private List<String> info = new ArrayList<>();
    private String path_archivo;
    private File archivoXml;
    
    public HashMap<String, String> mtdMapearUltimaVersion(){
        HashMap<String, String> info = new HashMap<String, String>();
        
        try {
            
            SAXBuilder builder = new SAXBuilder();
            File xml = archivoXml;
            
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
            info.put("app_name_version", campo.getChildTextTrim("app_name_version") );
            info.put("app_num_version", campo.getChildTextTrim("app_num_version") );
            info.put("app_link_exe", campo.getChildTextTrim("app_link_exe") );
            info.put("app_link_deb", campo.getChildTextTrim("app_link_deb") );
            
            // Cargar los nodos de app_novedades
            List<Element> listar_app_novedades = campo.getChildren("app_novedades");
            
            // Capturar la etiqueta app_novedades y todos los nodos dentro
            Element app_novedades = listar_app_novedades.get(0);
            //System.out.println("etiqueta :: " + app_novedades.getName());
            
            // Cargar los hijos nodos de app_novedades
            List<Element> listar_novedades = app_novedades.getChildren();
            
            // Capturar las etiquetas novedad (Cada Una)
            String novedades_trim = "";
            for (Element novedad : listar_novedades) {
                //System.out.println( novedad.getName() + " " + novedad.getTextTrim());
                novedades_trim += novedad.getTextTrim() + "\n"; 
            }
            
            info.put("app_novedades", novedades_trim );
            
        } catch (JDOMException ex) {
            Logger.getLogger(ObjVersionesXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObjVersionesXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return info;
    }
    
    public HashMap<String, String> mtdMapearUltimaVersionInterno(){
        HashMap<String, String> info = new HashMap<String, String>();
        
        try {
            
            SAXBuilder builder = new SAXBuilder();
            File xml = archivoXml;
            
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
            info.put("app_name_version", campo.getChildTextTrim("app_name_version") );
            info.put("app_num_version", campo.getChildTextTrim("app_num_version") );
            info.put("app_link_exe", campo.getChildTextTrim("app_link_exe") );
            info.put("app_link_deb", campo.getChildTextTrim("app_link_deb") );
            
            // Cargar los nodos de app_novedades
            List<Element> listar_app_novedades = campo.getChildren("app_novedades");
            
            // Capturar la etiqueta app_novedades y todos los nodos dentro
            Element app_novedades = listar_app_novedades.get(0);
            //System.out.println("etiqueta :: " + app_novedades.getName());
            
            // Cargar los hijos nodos de app_novedades
            List<Element> listar_novedades = app_novedades.getChildren();
            
            // Capturar las etiquetas novedad (Cada Una)
            String novedades_trim = "";
            for (Element novedad : listar_novedades) {
                //System.out.println( novedad.getName() + " " + novedad.getTextTrim());
                novedades_trim += novedad.getTextTrim() + "\n"; 
            }
            
            info.put("app_novedades", novedades_trim );
            
        } catch (JDOMException ex) {
            Logger.getLogger(ObjVersionesXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObjVersionesXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return info;
    }

    public String getPath_archivo() {
        return path_archivo;
    }

    public void setPath_archivo(String path_archivo) {
        this.path_archivo = path_archivo;
    }

    public File getArchivoXml() {
        return archivoXml;
    }

    public void setArchivoXml(File archivoXml) {
        this.archivoXml = archivoXml;
    }
    
}
