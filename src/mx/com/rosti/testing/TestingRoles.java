package mx.com.rosti.testing;

import java.util.Iterator;
import java.util.List;

import mx.com.rosti.catalogo.RolesDAOImpl;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.to.Roles;
import mx.com.rosti.util.Utilerias;

/**
 * Testing.
 */
public final class TestingRoles {
    /**
     * Constructor privado.
     */
    private TestingRoles() {
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

    	RolesDAOImpl est = (RolesDAOImpl) 
            ObjectFactory.getObject("RolesDAO");
        est.setLocal(true);

        List list = est.consultar();

        Iterator iter = list.iterator();
                
        
        while (iter.hasNext()) {
        	Roles per = (Roles) iter.next();
            Utilerias.printConsole(per.toString());
        }
        
        
    }
}
