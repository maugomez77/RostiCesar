package mx.com.rosti.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.rosti.pdf.EtiquetasCesar;
import mx.com.rosti.processor.RostiAD;
import mx.com.rosti.to.Producto;
import mx.com.rosti.to.Proveedor;
import mx.com.rosti.to.Unidades;
import mx.com.rosti.util.Cantidad;
import mx.com.rosti.util.Utilerias;

public class CrearEtiquetasServlet extends HttpServlet {
    String DELIMITADOR = "|";
    /**
	 * 
	 */
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
    	        
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
            
	    
    	HttpSession session = request.getSession();
    	if (! session.getId().equals(request.getParameter("sessionid"))) {
    		Utilerias.debug(this, session.getId());
    		Utilerias.debug(this, request.getParameter("sessionid"));
    		
    		session.invalidate();
    		response.sendRedirect("/RostiCesar/index.jsp");
    	}
    	
    	RostiAD rosti = new RostiAD();
    	
        if (request.getParameter("action") != null && request.getParameter("action").equals("Prov")) {
        	Proveedor prov = (Proveedor) rosti.getData(request.getParameter("idProv"), "Proveedor");
        	response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId() + "&action=Proveedor&idProv=" 
        	    + prov.getIdProveedor() + "&DescProv=" + prov.getNombre());
        } 
        
        if (request.getParameter("action") != null && request.getParameter("action").equals("CrearEtiqueta")) {
        	
        	Cantidad cant = Utilerias.validarCantidad(request.getParameter("cantidad"));
            
            if (cant == null) {
            	Proveedor prov = (Proveedor) rosti.getData(request.getParameter("idProv"), "Proveedor");
            	response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId() + "&action=Proveedor&idProv=" 
                	    + prov.getIdProveedor() + "&DescProv=" + prov.getNombre());
            } else { 
            	
            	List<InfoEtiquetasPDF> infoPDF = (ArrayList) session.getAttribute("infoPDF");
            	
            	if (infoPDF == null) {
            		infoPDF = new ArrayList<InfoEtiquetasPDF>();
            	}
            	
            	
            	String prodProv = request.getParameter("idProducto");
            	Producto pro = (Producto) rosti.getData(prodProv, "Producto");
            	Proveedor prov = (Proveedor) rosti.getData(request.getParameter("idProv"), "Proveedor");
            	//TipoProducto tipProd = (TipoProducto) rosti.getData(Utilerias.intToStr(pro.getIdTipoProducto()), "TipoProducto");
            	Unidades uni = (Unidades) rosti.getData(Utilerias.intToStr(pro.getIdUnidad()), "Unidades");
            	
            	String codigoEtiqueta = 
            	    rosti.generarEtiqueta(pro.getIdProducto(), Utilerias.intToStr(pro.getIdProveedor()), Utilerias.intToStr(pro.getIdUnidad()), Utilerias.intToStr(pro.getIdTipoProducto()), cant.getEnteros(), cant.getDecimales());
            	
            	InfoEtiquetasPDF info = new InfoEtiquetasPDF();
            	info.setProv(prov.getNombre());
            	info.setDescProducto(pro.getNombre());
            	info.setUnidad(uni.getNombre());
            	info.setEnteros(cant.getEnteros());
            	info.setDecimales(cant.getDecimales());
            	info.setCodProd(pro.getIdProducto());
            	info.setCodBarras(codigoEtiqueta);
            	            	
            	infoPDF.add(info);
            	
            	session.setAttribute("infoPDF", infoPDF);
            	
            	response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId() + "&action=Proveedor&idProv=" 
                	    + prov.getIdProveedor() + "&DescProv=" + prov.getNombre()
                	    + "&selectedProductoProveedor=" + pro.getIdProducto() + prov.getIdProveedor());
            	            	
            	
            	
            }
        }
        
        if (request.getParameter("action") != null && request.getParameter("action").equals("ProdProv")) {
        	
        	ArrayList<InfoEtiquetasPDF> infoPDF = (ArrayList) session.getAttribute("infoPDF");
        	
        	if (infoPDF == null) {
        		infoPDF = new ArrayList<InfoEtiquetasPDF>();
        	}
        	
        	String prodProv = request.getParameter("idProdProv");
        	Producto pro = (Producto) rosti.getData(prodProv, "Producto");        	
        	Proveedor prov = (Proveedor) rosti.getData(Utilerias.intToStr(pro.getIdProveedor()), "Proveedor");
        	Unidades uni = (Unidades) rosti.getData(Utilerias.intToStr(pro.getIdUnidad()), "Unidades");
        	
        	String codigoEtiqueta = 
        	    rosti.generarEtiquetaExcepcion(pro.getIdProducto(), Utilerias.intToStr(pro.getIdProveedor()));
        	
        	InfoEtiquetasPDF info = new InfoEtiquetasPDF();
        	info.setProv(prov.getNombre());
        	info.setDescProducto(pro.getNombre());
        	info.setUnidad(uni.getNombre());
        	info.setEnteros("000");
        	info.setDecimales("00");
        	info.setCodProd(pro.getIdProducto());
        	info.setCodBarras(codigoEtiqueta);
        	            	
        	infoPDF.add(info);
        	
        	session.setAttribute("infoPDF", infoPDF);
        	
        	response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId());
        	            	
        }
        
        if (request.getParameter("action") != null && request.getParameter("action").equals("CrearEtiquetaMultiple")) {
        	
        	ArrayList<InfoEtiquetasPDF> infoPDF = (ArrayList) session.getAttribute("infoPDF");
        	
        	if (infoPDF == null) {
        		
        		Proveedor prov = (Proveedor) rosti.getData(request.getParameter("idProv"), "Proveedor");
            	response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId() + "&action=Proveedor&idProv=" 
            	    + prov.getIdProveedor() + "&DescProv=" + prov.getNombre());        		
        		
        	} else {
        		
        		EtiquetasCesar pdf = new EtiquetasCesar();
                //String rutaArchivo = pdf.crearEtiquetas(GettingPaths.getPath("repository"), GettingPaths.generateFiles(), prov.getNombre(), pro.getNombre(), uni.getNombre(), cant.getEnteros(), cant.getDecimales(), pro.getIdProducto(), codigoEtiqueta);
                String rutaArchivo = pdf.crearEtiquetas(GettingPaths.getPath("repository"), GettingPaths.generateFiles(), infoPDF);
            	Utilerias.printConsole("Ruta: " + rutaArchivo);
            	
            	session.removeAttribute("infoPDF");
            	
            	response.sendRedirect("/RostiCesar/pages/leerEtiqueta.jsp?sessionid=" + session.getId() + "&archivo=" + rutaArchivo);
            	            	
        	}        	        	
        }   
        
        if (request.getParameter("action") != null && request.getParameter("action").equals("eliminate")) {
        	
        	Utilerias.debug(this, "Entrando a eliminar caja numero: " + request.getParameter("elemento"));
        	ArrayList<InfoEtiquetasPDF> infoPDF = (ArrayList) session.getAttribute("infoPDF");
        	Proveedor prov = (Proveedor) rosti.getData(request.getParameter("idProv"), "Proveedor");
        	
        	if (infoPDF == null) {
        		Utilerias.debug(this, "Entre en null");
        		response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId() + "&action=Proveedor&idProv=" 
            	    + prov.getIdProveedor() + "&DescProv=" + prov.getNombre());        		
        	} else {
        		infoPDF.remove(Utilerias.strToInt(request.getParameter("elemento")));
        	    Utilerias.debug(this, "Entre en no null indice " +  infoPDF.size());
        	    session.removeAttribute("infoPDF");
        	    session.setAttribute("infoPDF", infoPDF);
        	    
        	    if (prov.getIdProveedor() == -1) {
	        	    response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId());
        	    } else {
        	    	response.sendRedirect("/RostiCesar/pages/crearEtiqueta.jsp?sessionid=" + session.getId() + "&action=Proveedor&idProv=" 
	                	    + prov.getIdProveedor() + "&DescProv=" + prov.getNombre());
        	    }
        	}
        }        
        
    }
	
    public void setHeaderPage(HttpServletResponse response) {
    	response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
    }
    
    public void writeHeaderXML(HttpServletResponse response) { 
    
		try {
			response.getWriter().write("<data version=\"1.0\">");
		} catch (IOException e) {
			Utilerias.error(this, e.toString());
		}
		
    }
}
