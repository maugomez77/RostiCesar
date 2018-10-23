<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in. %>
				                  
          	<div id="contentbox">    			
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("ESDiaFecha")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/ESDiaFechaParte.jsp"/>
    				
    			<% } %>
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("ventasFinalParte")) { %>
    				<p><h2>Venta</h2></p>
    				<jsp:include page="parteReportes/ventasFinalParte.jsp"/>
    			     			 
    			<%  }  %>
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("inventarioFinalParte")) { %>
    				<p><h2>Entrada</h2></p>
    				<jsp:include page="parteReportes/inventarioFinalParte.jsp"/>
    			     			 
    			<%  }  %>
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("reportes")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/reportesParte.jsp"/>
    				
    			<%  }  %>    	
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("ESParte")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/ESParte.jsp"/>
    					
    			<%  }  %>    	
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("ESDiaParte")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/ESDiaParte.jsp"/>
    					
    			<%  }  %>    	
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("remisiones")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/remisionesParte.jsp"/>
    					
    			<%  }  %>    	
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("clienteDiaFecha")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/clienteDiaFechaParte.jsp"/>    					
    			
    			<%  }  %>    	
    			    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("clienteTotalDiaFecha")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/clienteTotalDiaFechaParte.jsp"/>    					
    			
    			<%  }  %>    	    			    			    		
    			    			    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("ESParteUnificado")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/ESParteUnificado.jsp"/>    					
    			
    			<%  }  %>    	    			    		
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("ESGeneralUnificado")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/ESGeneralUnificado.jsp"/>
    					
    			<%  }  %>    	
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("entradaPorDia")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/entradaPorDiaParte.jsp"/>
    					
    			<%  }  %>    	
    			
    			<% if (request.getParameter("reporte") != null && request.getParameter("reporte").equals("salidaPorDia")) { %>
    				<p><h2>Administraci&oacute;n de Reportes</h2></p>
    				<jsp:include page="parteReportes/salidaPorDiaParte.jsp"/>
    					
    			<%  }  %>    	
    			
	    		<br>
	    		<input type="submit" value="Imprimir" onclick="JavaScript:window.print();">	    	
	    		<input type="submit" value="Cerrar Pagina" onclick="JavaScript:window.close();">
    		</div>
    		
    		</div></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>