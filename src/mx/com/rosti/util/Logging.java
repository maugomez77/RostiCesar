package mx.com.rosti.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase para imprimir en pantalla.
 */
public final class Logging {
    /**
     * Logger.
     */
    private static Log logger = LogFactory.getLog(Logging.class);
    /**
     * Nombre de la clase java.lang.Class.
     */
    private static String nameClass = "java.lang.Class";
    /**
     * Para los  logs
     */
    private static boolean LOG_DEBUG = true;
    /**
     * Constructor privado.
     */
    private Logging() {     
    }
    /**
     * Metodo para imprimir debugs de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void debug(final Object className, final String msg) { 
    	
    	if (LOG_DEBUG) {
	        if (className.getClass().getName().equals(Logging.nameClass)) { 
	            printConsole("DEBUG: " + className + " : " + msg);
	            Logging.logger.debug(className + " : " + msg);
	        } else { 
	            printConsole("DEBUG: " + className.getClass().getName() 
	                + " : " + msg);
	            Logging.logger.debug(className.getClass().getName() + " : " + msg);
	        }
    	}
    }
    /**
     * Metodo para imprimir errors de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void error(final Object className, final String msg) {
        if (className.getClass().getName().equals(Logging.nameClass)) { 
            printConsole("ERROR: " + className + " : " + msg);
            Logging.logger.error(className + " : " + msg);
        } else { 
            printConsole("ERROR: " + className.getClass().getName() 
                + " : " + msg);
            Logging.logger.error(className.getClass().getName() + " : " + msg);
        }
    }
    /**
     * Metodo para imprimir fatal de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void fatal(final Object className, final String msg) {
        if (className.getClass().getName().equals(Logging.nameClass)) { 
            printConsole("FATAL: " + className + " : " + msg);
            Logging.logger.fatal(className + " : " + msg);
        } else { 
            printConsole("FATAL: " + className.getClass().getName() 
                + " : " + msg);
            Logging.logger.fatal(className.getClass().getName() + " : " + msg);
        }
    }
    /**
     * Metodo para imprimir trace de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void trace(final Object className, final String msg) {
        if (className.getClass().getName().equals(Logging.nameClass)) { 
            printConsole("TRACE: " + className + " : " + msg);
            Logging.logger.trace(className + " : " + msg);
        } else { 
            printConsole("TRACE: " + className.getClass().getName() 
                + " : " + msg);
            Logging.logger.trace(className.getClass().getName() + " : " + msg);
        }
    }
    /**
     * Metodo para imprimir info de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void info(final Object className, final String msg) {
        if (className.getClass().getName().equals(Logging.nameClass)) { 
            printConsole("INFO: " + className + " : " + msg);
            Logging.logger.info(className + " : " + msg);
        } else { 
            printConsole("INFO: " + className.getClass().getName() 
                + " : " + msg);
            Logging.logger.info(className.getClass().getName() + " : " + msg);
        }
    }
    /**
     * Metodo para imprimir en consola mensajes de texto.
     * @param msg   Mensaje a ser impreso
     */
    public static void printConsole(final String msg) {
        System.out.println(msg);
    }
}
