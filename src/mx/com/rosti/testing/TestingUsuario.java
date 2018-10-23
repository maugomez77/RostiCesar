package mx.com.rosti.testing;

import java.util.Iterator;
import java.util.List;

import mx.com.rosti.catalogo.UsuarioDAOImpl;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.to.Usuario;
import mx.com.rosti.util.Utilerias;

/**
 * Testing.
 */
public final class TestingUsuario {
    /**
     * Constructor privado.
     */
    private TestingUsuario() {
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

    	UsuarioDAOImpl est = (UsuarioDAOImpl) 
            ObjectFactory.getObject("UsuarioDAO");
        est.setLocal(true);

        List list = est.consultar();

        Iterator iter = list.iterator();
                
        
        while (iter.hasNext()) {
        	Usuario per = (Usuario) iter.next();
            Utilerias.printConsole(per.toString());
        }
        
        
    }
}
