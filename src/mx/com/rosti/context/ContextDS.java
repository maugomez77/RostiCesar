package mx.com.rosti.context;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import mx.com.rosti.util.Utilerias;


/**
 * Clase para tomar el contexto local, además de que
 * el servidor local tiene que estar corriendo para accesar 
 * al dataSource.
 */
public final class ContextDS implements ContextConstant {
    /**
     * Variable para guardar el dataSource local.
     */
    private static DataSource ds;
    /**
     * Constructor declarado por default private para no ser accesado 
     * por alguien mas.
     *
     */
    private ContextDS() {
    
    }
    /**
     * Metodo para obtener el dataSource, ya sea para local o 
     * web.
     * @return  DataSource          Regresa el objecto DataSource a ser usado en
     *                              la conexión a la BD
     * @throws  NamingException     Por si en caso de local context no se 
     *                              encuentra el DataSource
     */
    private static DataSource getDB() throws NamingException {
        if (ContextDS.ds == null) {
        	
        	//http://wiki.eclipse.org/EclipseLink/Examples/JPA/Tomcat_Web_Tutorial
        		
        	try { 
        		
	        	System.setProperty(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
	        	System.setProperty(Context.URL_PKG_PREFIXES, URL_PKGS);
	        	
	        	InitialContext ic = new InitialContext();
	
	        	ic.createSubcontext("java:");
	        	ic.createSubcontext("java:/comp");
	        	ic.createSubcontext("java:/comp/env");
	        	ic.createSubcontext("java:/comp/env/jdbc");
	            
	        	MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
	        	ds.setUrl(URL_MYSQL);
	        	ds.setUser(USER);
	        	ds.setPassword(PASSWORD);
	        	
	//        	 Globally scoped DataSource
	        	ic.bind(BIND_MYSQL, ds);
	            
	        	Context envCtx = (Context) ic.lookup(ENV_JAVA);
	    		ContextDS.ds = (DataSource)
	    		  envCtx.lookup(JDBC_DATABASE);
        	
            } catch (Exception e) { // refactor
        	  Utilerias.error(ContextDS.class, e.toString());
        	  return null;
            }
        }
        
        return ContextDS.ds;
    }
    /**
     * Regresa un objecto Connection a la BD.
     * @return  Connection   Objeto conexion a la BD
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = getDB().getConnection();
        	//conn = getDB().getConnection();
        } catch (Exception e) {
            Utilerias.error(ContextDS.class, e.toString());
        }
        return conn;
    }
}
