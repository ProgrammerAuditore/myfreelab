package index;

import src.Source;

public class Index {
    
    public static void main(String[] args) {
        // * Inicializar el programa
        //System.out.println("Inicializando programa...");
        MyFreeLab programa  = new MyFreeLab();
        
        switch (args.length) {
            case 0:
                if( !Source.SistemaOs.contains("win") )
                    programa.mtdVerificarPIDLinux();
                
                programa.mtdInit();
                break;
            case 1:
                switch( args[0] ){
                    case "--pid" : programa.mtdObtenerPID(); break;
                    case "--test" : programa.mtdTesting();  break;
                    case "-h" :
                    case "--help" : programa.mtdAyuda();  break;
                    default: programa.mtdAyuda(); break;
                }   break;
            default:
                programa.mtdAyuda();
                break;
        }
        
    }
    
}
