<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			
			if (start == null) {
				start = "";
			}
			
			if (end == null) {
				end = "";
			}
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Reportes</h2></p>
    			<form method="post" action="/RostiCesar/pages/ESDiaFecha.jsp?sessionid=<%= session.getId() %>">
    			<p align="justify">Fecha de Inicio:
    				<input type="text" name="start" id="start" maxlength="10" size="10" value="<%= start %>" readonly="true">
    				<a href="javascript:NewCal('start','ddmmyyyy')">
    					<img src="../css/cal.gif" width="16" height="16" border="0" alt="Selecciona una fecha">
    				</a>
    			</p>
    			
    			<p align="justify">Fecha Final:
    				<input type="text" name="end" id="end" maxlength="10" size="10" value="<%= end %>" readonly="true">
    				<a href="javascript:NewCal('end','ddmmyyyy')">
    					<img src="../css/cal.gif" width="16" height="16" border="0" alt="Selecciona una fecha">
    				</a>
    			</p>
    			
    			<input type="submit" value="Enviar Reporte">
    			
    			</form>
    			
    			<% if (request.getParameter("start") != null && request.getParameter("end") != null) { %>
    			    
    			    <jsp:include page="parteReportes/ESDiaFechaParte.jsp"/>
    			    		    		
		    		<script type="text/javascript">
    				    function abrirVentana(parameters) {
    						var oNewWin = window.open("/RostiCesar/pages/previewPage.jsp?sessionid=<%= session.getId() %>" + parameters, 
    									"Vista Preeliminar");
    						//oNewWin.moveTo(100, 100);
    					}    					
    					//abrirVentana();
    				</script>
    				
    				<br>			    				
	    			<input type="submit" value="Vista Preeliminar" onclick=" return abrirVentana('<%= "&reporte=ESDiaFecha&start=" + start + "&end=" + end %>');">

	    		 <% } %> 
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>