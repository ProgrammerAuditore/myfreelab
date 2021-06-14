package src;

// Aquí se incluye la información del softwares
public class Info {
    public static final String Autor = "ProgrammerAuditore";
    public static final String Anho = "2021";
    public static final String Descripcion = "Gestión de proyectos para freelancer.";
    public static final String Detalle = 
            "El programa sirve para poder crear, modificar, eliminar y registrar proyectos desarrollados para una empresa en particular." +"\n"+
            "Asi también, para poder generar una cotizacion para cada empresa en particular y calcular el costo del proyecto." +"\n"+
            "Tiene la funcionalidad de conexion a una base de datos MYSQL local o remoto para registrar los datos.";
    public static final String Licencia="Licencia GPL.";
    public static final String sNombre = "MyFreeLab";
    public static final String sVersion = "v0.9.3";
    public static final String sProduccion = "Alpha";
    public static final String NombreSoftware = sNombre + " " + sVersion + sProduccion;
    
    public static final String dirTemp = System.getProperty("java.io.tmpdir");
    public static final String Homepage = "https://www.myfreelab.com";
}
