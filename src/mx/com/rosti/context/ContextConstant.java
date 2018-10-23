package mx.com.rosti.context;
/**
 * Interfaz de conexión local para el dataSource con acceso a factories.
 */
public interface ContextConstant {
    /**
     * Initial factory.
     */
    String INITIAL_CONTEXT_FACTORY = 
        "org.apache.naming.java.javaURLContextFactory";
    /**
     * Initial Factory
     */
    String URL_PKGS = "org.apache.naming";
    String URL_MYSQL = "jdbc:mysql://127.0.0.1/rosti";
    String BIND_MYSQL = "java:/comp/env/jdbc/RostiDB";    
    /**
     * 
     */
    String ENV_JAVA = "java:/comp/env";
    /**
     * JNDI para la BD.
     */
    String JDBC_DATABASE = "jdbc/RostiDB";
    
    String EMAIL_SESSION = "mail/Session";
    /**
     * Usuario nepe de BD.
     */
    String USER = "rosti";
    /**
     * Password nepe de BD.
     */
    String PASSWORD = "rosti";
    
    String sSMTPServer = "localhost";
    
    
    String USER_EMAIL = "a00743525@itesm.mx";
    
    String PASSWORD_EMAIL = "mauricio";
}
