package mx.com.rosti.testing;

import java.util.Iterator;
import java.util.List;

import mx.com.rosti.catalogo.UnidadesDAOImpl;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.to.Unidades;
import mx.com.rosti.util.Utilerias;

/**
 * Testing.
 */
public final class TestingUnidades {
    /**
     * Constructor privado.
     */
    private TestingUnidades() {
    }

    /**
     * Metodo de prueba.
     * @param args  Argumentos de consola
     */
    @SuppressWarnings("unchecked")
	public static void main(final String[] args) {
        
    	 //Properties prop = System.getProperties();
         //prop.list(System.out);
         
         //System.out.println("Que pex: " + prop.getProperty("java.naming.factory.initial"));
         //System.out.println("Que pex 2: " + prop.getProperty("java.naming.provider.url"));
                  
    	Utilerias.printConsole("INICIO");

    	UnidadesDAOImpl est = (UnidadesDAOImpl) 
            ObjectFactory.getObject("UnidadesDAO");
        est.setLocal(true);

        List list = est.consultar();

        Iterator iter = list.iterator();
                
        
        while (iter.hasNext()) {
        	Unidades per = (Unidades) iter.next();
            Utilerias.printConsole(per.toString());
        }
        
        
    }
}
