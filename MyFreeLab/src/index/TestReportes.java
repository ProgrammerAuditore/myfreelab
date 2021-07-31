package index;

import controlador.CtrlPrincipal;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class TestReportes {

    public void mtdGenerarInforme() {

        try {
            // Imprimir o mostrar el reporte generado
            JasperPrint jp = mtdCargarJasperReports();

            if (jp.getPages().isEmpty()) {
                System.out.println("No hay páginas para mostrar");
            } else {

                // Mostar el reporte de Cotización
                JasperViewer jviewer = new JasperViewer(jp, false);
                jviewer.setTitle("GGG");
                jviewer.setVisible(true);
                //JasperViewer.viewReport(jp);

            }
        } catch (Exception e) {
            // El archivo no existe
            //System.out.println("" + e.getMessage());
            CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdCotizarProyecto.msg3"));
        }
    }

    public JasperPrint mtdCargarJasperReports() {
        JasperPrint jp = null;
        //String path="/home/Windows10/Documents/NetBeansProjects/Proyectos/netbeans-freelancer-software/MyFreeLab/shared/";
        String path = "P:\\Documents\\NetBeansProjects\\Proyectos\\netbeans-freelancer-software\\MyFreeLab\\shared\\";

        List<ChartDatos> datos = new ArrayList<>();
        datos.add(new ChartDatos("Barcelona", "Madrid", 200));

        try {
            String pathReporteCotizacion = new File(path + "Grafica.jrxml").getAbsolutePath();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("SubReportDir", path);
            parametros.put("repoTitulo", "Reporte de pruebas");
            parametros.put("GraficaX", new JRBeanCollectionDataSource(datos));

            JasperReport jr = JasperCompileManager.compileReport(pathReporteCotizacion);

            jp = JasperFillManager.fillReport(jr, parametros, new JRBeanCollectionDataSource(datos));

        } catch (JRException ex) {
            // Error en la base de datos
            //Logger.getLogger(CtrlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            CtrlPrincipal.mensajeCtrlPrincipal(MyFreeLab.idioma
                    .getProperty("ctrlTarjetaProyecto.mtdGenerarReporte.msg1"));
        }

        return jp;
    }

    private class ChartDatos {

        private String Series;
        private String Leyendas;
        private Integer Valores;

        public ChartDatos(String Series, String Leyendas, Integer Valores) {
            super();
            this.Series = Series;
            this.Leyendas = Leyendas;
            this.Valores = Valores;
        }

        public String getSeries() {
            return Series;
        }

        public void setSeries(String Series) {
            this.Series = Series;
        }

        public String getLeyendas() {
            return Leyendas;
        }

        public void setLeyendas(String Leyendas) {
            this.Leyendas = Leyendas;
        }

        public Integer getValores() {
            return Valores;
        }

        public void setValores(Integer Valores) {
            this.Valores = Valores;
        }

    }

    ////////////////////////////////////////////
    ////////////////
    ////////////////
    ////////////////   Reporte 1
    ////////////////
    ////////////////
    ///////////////////////////////////////////
    /*
    public void mtdReporte1() {
        String sourceFileName
                = "C://tools/jasperreports-5.0.1/test/jasper_report_template.jasper";
        //String path="/home/Windows10/Documents/NetBeansProjects/Proyectos/netbeans-freelancer-software/MyFreeLab/shared/";
        String path = "P:\\Documents\\NetBeansProjects\\Proyectos\\netbeans-freelancer-software\\MyFreeLab\\shared\\";
        String report = path+"Template.jasper"; 

        DataBeanList DataBeanList = new DataBeanList();
        ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();

        JRBeanCollectionDataSource beanColDataSource
                = new JRBeanCollectionDataSource(dataList);

        Map parameters = new HashMap();
    
        parameters.put("ReportTitle", "List of Contacts");
        parameters.put("Author", "Prepared By Manisha");

        try {
            JasperFillManager.fillReportToFile(
                    report, parameters, beanColDataSource);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private class DataBean {

        private String name;
        private String country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    private class DataBeanList {

        public ArrayList<DataBean> getDataBeanList() {
            ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();

            dataBeanList.add(produce("Manisha", "India"));
            dataBeanList.add(produce("Dennis Ritchie", "USA"));
            dataBeanList.add(produce("V.Anand", "India"));
            dataBeanList.add(produce("Shrinath", "California"));

            return dataBeanList;
        }

        private DataBean produce(String name, String country) {
            DataBean dataBean = new DataBean();
            dataBean.setName(name);
            dataBean.setCountry(country);

            return dataBean;
        }
    }
     */
    ////////////////////////////////////////////
    ////////////////
    ////////////////
    ////////////////   Reporte 2
    ////////////////
    ////////////////
    ///////////////////////////////////////////    
    public void mtdReporte2() {
        //String sourceFileName = 
        //"C://tools/jasperreports-5.0.1/test/jasper_report_template.jasper";
        String path = "P:\\Documents\\NetBeansProjects\\Proyectos\\netbeans-freelancer-software\\MyFreeLab\\shared\\";
        String report = path + "Chart.jasper";

        DataBeanList DataBeanList = new DataBeanList();
        ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);

        Map parameters = new HashMap();

        try {
            JasperFillManager.fillReportToFile(report,
                    parameters, beanColDataSource);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public class DataBean {

        private String subjectName;
        private Integer marks;

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public Integer getMarks() {
            return marks;
        }

        public void setMarks(Integer marks) {
            this.marks = marks;
        }

    }

    public class DataBeanList {

        public ArrayList<DataBean> getDataBeanList() {
            ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();

            dataBeanList.add(produce("English", 58));
            dataBeanList.add(produce("SocialStudies", 68));
            dataBeanList.add(produce("Maths", 38));
            dataBeanList.add(produce("Hindi", 88));
            dataBeanList.add(produce("Scince", 78));

            return dataBeanList;
        }

        /*
    * This method returns a DataBean object, with subjectName ,
    * and marks set in it.
         */
        private DataBean produce(String subjectName, Integer marks) {
            DataBean dataBean = new DataBean();

            dataBean.setSubjectName(subjectName);
            dataBean.setMarks(marks);

            return dataBean;
        }
    }

}
