package mx.com.rosti.testing;

import java.util.Iterator;
import java.util.List;

import mx.com.rosti.catalogo.TipoProductoDAOImpl;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.to.TipoProducto;
import mx.com.rosti.util.Utilerias;

/**
 * Testing.
 */
public final class TestingTipoProducto {
    /**
     * Constructor privado.
     */
    private TestingTipoProducto() {
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

    	TipoProductoDAOImpl est = (TipoProductoDAOImpl) 
            ObjectFactory.getObject("TipoProductoDAO");
        est.setLocal(true);

        List list = est.consultar();

        Iterator iter = list.iterator();
                
        
        while (iter.hasNext()) {
        	TipoProducto per = (TipoProducto) iter.next();
            Utilerias.printConsole(per.toString());
        }
        
        
    }
}
