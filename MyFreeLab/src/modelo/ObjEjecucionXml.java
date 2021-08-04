package modelo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import src.Recursos;

public class ObjEjecucionXml {

    private String path_archivo;
    private long estado;
    private boolean agregarTiempoInicial;
    private boolean agregarTiempoFinal;

    public ObjEjecucionXml() {
        estado = 0;
        agregarTiempoInicial=false;
        agregarTiempoFinal=false;
    }

    public HashMap<String, String> mtdMapearXmlRun() {
        HashMap<String, String> info = new HashMap<String, String>();

        File xml = new File(path_archivo);
        InputStream is = null;
        BufferedInputStream bis = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(xml);
            is = new BufferedInputStream(fis);

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

            info.put("app_pid", campo.getChildTextTrim("app_pid"));
            info.put("app_estado", campo.getChildTextTrim("app_estado"));
            info.put("tiempo_inicial", campo.getChildTextTrim("tiempo_inicial"));
            info.put("tiempo_final", campo.getChildTextTrim("tiempo_final"));
            System.out.println("Archivo leido jajaj xD");

        } catch (JDOMException ex) {
            //Logger.getLogger(ObjEjecucionXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(ObjEjecucionXml.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                }
            }
        }

        return info;
    }

    public boolean mtdGenerarXmlRun() {
        try {
            Element application = new Element("RunTime");
            Document doc = new Document(application);

            Element root = new Element("MyFreeLab");
            application.addContent(root);

            Element app = new Element("App");
            root.addContent(app);

            Element pid = new Element("app_pid");
            pid.setText("" + Recursos.PID);
            app.addContent(pid);
            
            Element estado = new Element("app_estado");
            estado.setText("" + this.estado);
            app.addContent(estado);
            
            Element tiempo_inicial = new Element("tiempo_inicial");
            tiempo_inicial.setText("" + (agregarTiempoInicial ? System.nanoTime() : 0 ) );
            app.addContent(tiempo_inicial);
            
            Element tiempo_final = new Element("tiempo_final");
            tiempo_final.setText("" + (agregarTiempoFinal ? System.nanoTime() : 0 ) );
            app.addContent(tiempo_final);

            XMLOutputter xmlRun = new XMLOutputter();
            xmlRun.setFormat(Format.getPrettyFormat());
            xmlRun.output(doc, new FileWriter(Recursos.dataRun().getAbsoluteFile()));
            
            System.out.println("Archivo creado ajajaja xD");
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

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    public boolean isAgregarTiempoFinal() {
        return agregarTiempoFinal;
    }

    public void setAgregarTiempoFinal(boolean agregarTiempoFinal) {
        this.agregarTiempoFinal = agregarTiempoFinal;
    }

    public boolean isAgregarTiempoInicial() {
        return agregarTiempoInicial;
    }

    public void setAgregarTiempoInicial(boolean agregarTiempoInicial) {
        this.agregarTiempoInicial = agregarTiempoInicial;
    }

}
