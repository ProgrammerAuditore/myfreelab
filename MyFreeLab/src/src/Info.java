package src;

// Aquí se incluye la información del softwares
public class Info {
    public static final String sNombre = "MyFreeLab";
    public static final String sVersionName = "v1.2.0";
    public static final String sVersionNum = "1200";
    public static final String sProduccion = "Beta";
    public static final String Autor = "Victor J. Maximo";
    public static final String Avatar = "ProgrammerAuditore";
    public static final String Mantenedor = "<victorvj098@gmail.com>";
    public static final String Anho = "2021";
    public static final String Copyright = Info.Autor + " (c) " + Info.Anho;
    public static final String NombreSoftware = sNombre + " " + sVersionName + sProduccion;
    public static final String DetalleEsp = 
            "El programa sirve para poder crear, modificar, eliminar y registrar proyectos desarrollados para una empresa en particular." +"\n"+
            "Así también, para poder generar una cotización para cada empresa en particular y calcular el costo del proyecto, entre otras cosas." +"\n"+
            "Tiene la funcionalidad de conexión a una base de datos MYSQL local o remoto para registrar los datos.";
    public static final String DetalleEng = 
            "The program is used to create, modify, delete and register projects developed for a particular company." +"\n"+
            "Also, to be able to generate a quote for each company in particular and calculate the cost of the project, among other things." +"\n"+
            "It has the functionality of connecting to a local or remote MYSQL database to record the data.";
    public static final String Novedades = 
            "* Se agrego propiedades avanzadas en establecer conexión" + "\n" +
            "* Se agrego soporte de inglés y español para las propiedades avanzadas" + "\n" +
            "* Se modifico el modal de establecer conexión" + "\n" +
            "* Se corrigio un error al capturar la contraseña" + "\n" +
            "de la base de datos." + "\n" +
            "* Se corrigio errores" + "\n" +
            "* etc." + "\n";
    
    public static final String SitioWeb = "https://programmerauditore.gitlab.io/myfreelab";
    public static final String PaginaAyuda = "https://programmerauditore.gitlab.io/myfreelab/#/obtener-ayuda";
    public static final String PaginaIssues = "https://github.com/ProgrammerAuditore/myfreelab/issues";
    public static final String LinkVersiones = "https://raw.githubusercontent.com/ProgrammerAuditore/myfreelab/master/source/MyFreeLab.mfl";
    //public static final String LinkVersiones = "https://raw.githubusercontent.com/ProgrammerAuditore/myfreelab/avance/source/MyFreeLab.mfl";
}
