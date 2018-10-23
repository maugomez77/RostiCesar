package mx.com.rosti.servlets;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.rosti.processor.RostiAD;
import mx.com.rosti.to.ProductoAcumulado;
import mx.com.rosti.to.ReporteInventario;
import mx.com.rosti.util.Utilerias;
import mx.com.rosti.validations.NumbersValidation;

public class ActualizaPreciosServlet extends HttpServlet {
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
    
	@SuppressWarnings("rawtypes")
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
    	
        if (request.getParameter("action") != null && request.getParameter("action").equals("reporte")) {
        	
        	Utilerias.debug(this, "Entrando . . . .");
        	        	
        	String parameters = request.getParameter("parameters");
        	StringTokenizer st = new StringTokenizer(parameters, "|");
        	String start = st.nextToken();
        	String end = st.nextToken();
        	String cliente = st.nextToken();
        	
        	List lista = rosti.getListInfo("ReporteRemisionFechas", start + "|" + end + "|" + cliente);
        	String remision = "";
        	
        	
        	//primero actualizar los nuevos precios que se han fijado
        	//por el usuario a cada remision.
        	
        	for (int i=0; i < lista.size(); i++) {        		
        		ReporteInventario obj = (ReporteInventario) lista.get(i);        		
        		ProductoAcumulado pAcum = new ProductoAcumulado();        		
    			pAcum.setPrecioUnitario(Utilerias.strToDouble(request.getParameter("get" + i)));
    			
    			//in case just updating prices in acumulados, can happened because if the costo is not show
    			if (request.getParameter("getCosto" + i) != null && NumbersValidation.isDouble(request.getParameter("getCosto" + i))) { 
    				pAcum.setCostoInventario(Utilerias.strToDouble(request.getParameter("getCosto" + i)));
    			} else { 
    				pAcum.setCostoInventario(obj.getCostoUnitario());
    			}
    			
    			pAcum.setEntradaSalida(obj.getSalidaCarga());
    			pAcum.setIdProd(obj.getCodeProd());
    			pAcum.setProv(obj.getProv());
    			int result = rosti.dmlOperations(pAcum, "update");
    			Utilerias.printConsole("Actualizacion: " + result);
    			Utilerias.printConsole("Valores: " + pAcum.getPrecioUnitario() + " " + pAcum.getIdProd() + "  " + pAcum.getProv() + " " + pAcum.getEntradaSalida() + " " + pAcum.getCostoInventario() + " " + pAcum.getCostoProducto());    			    			
    		}    		    		
    		
    		
    		//ahora actualizar los pagos para cada remision.
        	remision = "";
        	
        	for (int i=0; i < lista.size(); i++) {
                
		    	ReporteInventario box = (ReporteInventario) lista.get(i);
		    	if (remision.equals("")) {
		    		remision = box.getRemision();
		    	} else {
		    		//existe cambio de remision
		    		if (!remision.equals(box.getRemision())) {
		    			if (request.getParameter("cantidad" + remision) == null) { 
		    				rosti.actualizarPrecio(remision, request.getParameter("cantidad"));
		    			} else {
			    			rosti.actualizarPrecio(remision, request.getParameter("cantidad" + remision));
			    			//listaParametros += "&cantidad" + remision + "=" + request.getParameter("cantidad" + remision);
		    			}
		    			remision = box.getRemision();
		    		}
		    	}
        	}
        	
        	//atualizar la ultima remision
        	if (request.getParameter("cantidad" + remision) == null) { 
        		rosti.actualizarPrecio(remision, request.getParameter("cantidad"));
        	} else { 
        		rosti.actualizarPrecio(remision, request.getParameter("cantidad" + remision));	
        	}
        	
        	//listaParametros += "&cantidad" + remision + "=" + request.getParameter("cantidad" + remision);
        	
        	Utilerias.debug(this, "Ruta: " + "/RostiCesar/pages/clienteDiaFecha.jsp?sessionid=" + request.getParameter("sessionid") 
    	    + "&start=" + start + "&end=" + end + "&cliente=" + cliente);
    	    //+ listaParametros);
    	    
        	response.sendRedirect("/RostiCesar/pages/clienteDiaFecha.jsp?sessionid=" + request.getParameter("sessionid") 
        	    + "&start=" + start + "&end=" + end + "&cliente=" + cliente);
        	    //+ listaParametros);
        	
        }   
        
        if (request.getParameter("action") != null && request.getParameter("action").equals("devolucion")) {
        	
        	String parameters = request.getParameter("parameters");
        	StringTokenizer st = new StringTokenizer(parameters, "|");
        	String start = st.nextToken();
        	String end = st.nextToken();
        	String cliente = st.nextToken();
        	
        	List lista = rosti.getListInfo("ReporteRemisionFechasItem", start + "|" + end + "|" + cliente);
        	String devol = "";
        	int contadorDevolucionesAplicadas = 0;
        	for (int i=0; i < lista.size(); i++) {
        		ReporteInventario box = (ReporteInventario) lista.get(i);
        		devol = request.getParameter("devolucion" + box.getRemision() + "|" + box.getIdInventario() + "|" + box.getCodeProd() + "|" + box.getProv());
        		Utilerias.debug(this, "Devol: " + i + " " + devol);
        		if (devol.equals("Y")) {
        			contadorDevolucionesAplicadas += rosti.aplicarDevolucion(box.getRemision(), box.getIdInventario(), box.getCodeProd(), box.getProv());
        		}
        	}
        	
        	response.sendRedirect("/RostiCesar/pages/itemDevolucion.jsp?sessionid=" + request.getParameter("sessionid") 
            	+ "&start=" + start + "&end=" + end + "&cliente=" + cliente + "&contadorDev=" + contadorDevolucionesAplicadas);
            	//+ listaParametros);
            	
        }
        
    }    
}
