<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in. %>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Lectura de Etiquetas</h2></p>    			
    				<script type="text/javascript">
    				
    				    function abrirVentana() {
    						var oNewWin = window.open("/RostiCesar/ReadPDFServlet?archivo=<%= request.getParameter("archivo") %>", 
    									"Ventana PDF", 
    									"height=600,width=1200,top=100,left=10,resizable=yes");
    						oNewWin.moveTo(100, 100);
    						//oNewWin.resizeTo(200, 200);
    					}
    					
    					abrirVentana();
    				</script>
    				
    				<a href="javascript:abrirVentana();">Dar click aqui si no abre la ventana automaticamente.</a>
    						    			
    		</div>
    		
    		</div>
    	<%@ include file="../admin/Footer.jsp" %>
    </body>
</html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>