package mx.com.rosti.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.rosti.util.Utilerias;

public class ReadPDFServlet extends HttpServlet {
	private static final long serialVersionUID = 4274649656561673504L;
	@SuppressWarnings("unused")
	private ServletContext context;
    //private HashMap accounts = new HashMap();
    
    // Initialize the "accounts" hashmap.  For the sake of this exercise,
    // two accounts are created with names "greg" and "duke" during
    // initialization of the Servlet.
    //
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
        //accounts.put("greg","account data");
        //accounts.put("duke","account data");
    }
    
    public  void doGet(HttpServletRequest request, HttpServletResponse  response)
    throws IOException, ServletException {
        
    	Utilerias.debug(this, "Entrando doGet");
    	doPost(request, response);
    	        
    }
    
	public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        		
		ServletOutputStream  outServ = response.getOutputStream ();
		
		//---------------------------------------------------------------
		// Set the output data's mime type
		//---------------------------------------------------------------
		    response.setContentType( "application/pdf" );  // MIME type for pdf doc
		//---------------------------------------------------------------
		// create an input stream from fileURL
		//---------------------------------------------------------------
		    //String fileURL = 
		    //    "http://www.adobe.com/aboutadobe/careeropp/pdfs/adobeapp.pdf";
		//------------------------------------------------------------
		    //res.setHeader("Content-disposition",
		    //              "attachment; filename=" +=
		    //              "Example.pdf" );
		
		BufferedInputStream  bis = null; 
		BufferedOutputStream bos = null;
		InputStream is = null;
		File file = null;
		     try {
		    	file = new File(GettingPaths.getPath("repository") + request.getParameter("archivo"));
		    	is = new FileInputStream(file);
		        // Use Buffered Stream for reading/writing.
		        bis = new BufferedInputStream(is);
		        bos = new BufferedOutputStream(outServ);
		        byte[] buff = new byte[(int)file.length()];
		        int bytesRead;
		        // Simple read/write loop.
		        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		            bos.write(buff, 0, bytesRead);
		        }
		    } catch(final Exception e) {
		        System.out.println ( "IOException." + e.toString());
		    } finally {
		    	if (is != null)
		        	is.close();    				        
		    	if (bis != null)
		            bis.close();
		        if (bos != null)
		            bos.close();
		    }
	}
}
    	