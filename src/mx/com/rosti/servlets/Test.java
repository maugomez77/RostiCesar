package mx.com.rosti.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.rosti.catalogo.ProductoDAOImpl;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.to.Producto;
import mx.com.rosti.util.Utilerias;

/**
 * Testing.
 */
public class Test extends HttpServlet {
    /**
     * Version del serializable.
     */
    private static final long serialVersionUID = 5579649615699046802L;

    /**
     * doGet.
     * @param request  Request
     * @param response Response
     * @throws ServletException Excepcion
     * @throws IOException  Excepcion
     */
    @Override
    protected void doGet(final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>");
        out.println("WAS + MySQL Example");
        out.println("</TITLE></HEAD><BODY>");
        out.println("<H1>WAS + MySQL Example</H1>");
        //try {

        //java.util.Properties parms = new java.util.Properties();
        //parms.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
        //parms.put(Context.PROVIDER_URL,"iiop:///");

        //Context ctx = new InitialContext(parms);
        //Context ctx = new InitialContext();
        //DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DataSourceMYSQL");
        Utilerias.debug(this, "INICIO");

        Utilerias.debug(this, "trajo bean " + Utilerias.getTime());

        ProductoDAOImpl est = (ProductoDAOImpl) 
             ObjectFactory.getObject("ProductoDAO");
	    est.setLocal(false);
	
	    List<Producto> list = est.consultar();
	
	    Iterator<Producto> iter = list.iterator();
	
	    while (iter.hasNext()) {
	    	Producto per = (Producto) iter.next();
	        Utilerias.printConsole(per.toString());
	    }
        Utilerias.debug(this, "end " + Utilerias.getTime());
        
        Utilerias.debug(this, "Server name: " + request.getServerName());
        Utilerias.debug(this, "Server port: " + request.getServerPort());
        Utilerias.debug(this, "Server addr: " + request.getLocalAddr());
        Utilerias.debug(this, "Server : " + request.getProtocol());
        Utilerias.debug(this, "Server : " + request.getServletPath());
        Utilerias.debug(this, "Server : " + request.getRequestURI());
        
        
        /*
        Connection connection = ds.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from periodo");
        while (resultSet.next()) {
             String ssn = resultSet.getString("id_periodo");
             out.print(ssn + ": ");
        }*/

        /*}        catch (NamingException e) {
             e.printStackTrace();
        } */
        
        out.println("Path: " + GettingPaths.getPath("repository"));
        
        out.println("</BODY></HTML>");
        out.close();
    }
}
