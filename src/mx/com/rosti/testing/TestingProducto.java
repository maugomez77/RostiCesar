package mx.com.rosti.testing;

import java.util.Iterator;
import java.util.List;

import mx.com.rosti.catalogo.ProductoDAOImpl;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.to.Producto;
import mx.com.rosti.util.Utilerias;

/**
 * Testing.
 */
public final class TestingProducto {
    /**
     * Constructor privado.
     */
    private TestingProducto() {
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

    	ProductoDAOImpl est = (ProductoDAOImpl) 
            ObjectFactory.getObject("ProductoDAO");
        est.setLocal(true);

        List list = est.consultar();

        Iterator iter = list.iterator();
                
        
        while (iter.hasNext()) {
        	Producto per = (Producto) iter.next();
            Utilerias.printConsole(per.toString());
        }
        
        
    }
}
