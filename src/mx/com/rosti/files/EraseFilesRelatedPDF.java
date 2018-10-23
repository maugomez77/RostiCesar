package mx.com.rosti.files;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mx.com.rosti.servlets.GettingPaths;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para borrar archivos referentes a los reportes pdf.
 */
public class EraseFilesRelatedPDF {
    /**
     * Metodo para realizar limpieza de archivos referentes a los reportes pdf.
     * @param  directoryName       Nombre del directorio
     */
    public void limpiezaArchivos(final String directoryName) {
        //String directoryName = 
            //"C://Workspace RAD//Impuestos//WebContent//repository//";
            //"C://Workspace RAD//.metadata//.plugins//
            //org.eclipse.wst.server.core//tmp0//Impuestos//repository//";
        UtilitiesOnFiles iFiles = new UtilitiesOnFiles();
        File[] listFiles = iFiles.listFiles(directoryName);
        
        java.sql.Date date = new java.sql.Date(
            Calendar.getInstance().getTime().getTime());
        
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 1);
        
        java.sql.Date dateToday = new java.sql.Date(cal.getTime().getTime());
        
        for (int i = 0; i < listFiles.length; i++) {
        
            date.setTime(getFileNameTime(listFiles[i].getName()));
            Utilerias.debug(this, "Fecha del archivo: " 
                + Utilerias.getDate(date, "yyyy-MM-dd HH:mm:ss"));            
            Utilerias.debug(this, "Fecha consideración: " 
                + Utilerias.getDate(dateToday, "yyyy-MM-dd HH:mm:ss"));
            
            // borrara el archivo  
            // si la fecha del archivo es menor a la fecha de hoy
            // a la 1 de la mañana
            if (date.compareTo(dateToday) < 0) {
                Utilerias.debug(this, "Erasing the file: " 
                    + listFiles[i].getAbsolutePath());
                iFiles.deleteFile(listFiles[i].getAbsolutePath());
            }
        }
    }
    /**
     * Metodo para traer el nombre del system en milisegundos.
     * @param  filePath    Ruta del archivo
     * @return String      
     */
    public long getFileNameTime(final String filePath) {
    
        return Long.parseLong(
                filePath.substring(GettingPaths.getPrefixNamePDF().length(), 
                filePath.indexOf(GettingPaths.getPostfixNamePDF())));
    }
    /**
     * Metodo para pruebas de la clase.
     * @param args  Argumentos de consola
     */
    public static void main(final String[] args) {
        EraseFilesRelatedPDF pdf = new EraseFilesRelatedPDF();        
        pdf.limpiezaArchivos("C://Workspace RAD//.metadata//" 
            + ".plugins//org.eclipse.wst.server.core//" 
            + "tmp0//Impuestos//repository//");
    }
}
