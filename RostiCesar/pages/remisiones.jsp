<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Remisiones</h2></p>
    			
		    		<script type="text/javascript">
    				    function abrirVentana(parameters) {
    						var oNewWin = window.open("/RostiCesar/pages/previewPage.jsp?sessionid=<%= session.getId() %>" + parameters, 
    									"Vista Preeliminar");
    						//oNewWin.moveTo(100, 100);
    					}    					
    					abrirVentana('<%= "&reporte=remisiones" %>');
    				</script>
    				
    				<br>			    				
	    			<input type="submit" value="Ver Reportes Impresion" onclick=" return abrirVentana('<%= "&reporte=remisiones" %>');">			
    			  
    			  			  
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body>
</html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>