<%@ include file="../admin/Secure.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.to.Clientes" %>

<jsp:useBean id = "rostiAdminData"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     
<jsp:useBean id = "cli"
			     scope = "page"
			     class = "mx.com.rosti.to.Clientes" />
			     			     
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
			String cliente = request.getParameter("cliente");
			if (start == null) {
				start = "";
			}
			
			if (end == null) {
				end = "";
			}
			
			if (cliente == null) {
				cliente = "";
			}
			
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Pagos de Cliente</h2></p>
    			<form method="post" action="/RostiCesar/pages/clienteDiaFecha.jsp?sessionid=<%= session.getId() %>">
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
    			    			
    			<p align="justify">Clientes:
    				<select name="cliente">	
    					<% List listaClientes = rostiAdminData.getListInfo("Clientes");
  					       for (int i=0; i < listaClientes.size(); i++) { 
  						       cli = (Clientes) listaClientes.get(i); %>
  						  	   <option value="<%= cli.getIdCliente() %>"><%= cli.getNombre() %></option>	
        				<% } %>
    				</select>                        
    			</p>
    			
    			<input type="submit" value="Enviar Reporte">
    			</form>
    			
    			<% if (!start.equals("") && !end.equals("") && !cliente.equals("")) { %>
    			    
    			    <jsp:include page="parteReportes/clienteDiaFechaParte.jsp"/>
    				<script type="text/javascript">
    				    function abrirVentana(parameters) {
    						var oNewWin = window.open("/RostiCesar/pages/previewPage.jsp?sessionid=<%= session.getId() %>" + parameters, 
    									"Vista Preeliminar");
    						//oNewWin.moveTo(100, 100);
    					}    					
    					//abrirVentana();
    				</script>    				
    				<br>
    				
    				<% if (request.getParameter("remisionTodas") != null && request.getParameter("remisionTodas").equals("Y")) { %>
    					<% if (request.getParameter("aplicaCosto") != null && request.getParameter("aplicaCosto").equals("N")) { %>
    						<input type="submit" value="Vista Preeliminar" onclick=" return abrirVentana('<%= "&preview=Y&aplicaCosto=N&remisionTodas=Y&reporte=clienteDiaFecha&cliente=" + cliente + "&start=" + start + "&end=" + end %>');">
    					<% } else { %>	
    						<input type="submit" value="Vista Preeliminar" onclick=" return abrirVentana('<%= "&preview=Y&remisionTodas=Y&reporte=clienteDiaFecha&cliente=" + cliente + "&start=" + start + "&end=" + end %>');">
    					<% } %>	
    				<% } else { %>
    				
    					<% if (request.getParameter("aplicaCosto") != null && request.getParameter("aplicaCosto").equals("N")) { %>
    						<input type="submit" value="Vista Preeliminar" onclick=" return abrirVentana('<%= "&preview=Y&aplicaCosto=N&reporte=clienteDiaFecha&cliente=" + cliente + "&start=" + start + "&end=" + end %>');">
    					<% } else { %>
    						<input type="submit" value="Vista Preeliminar" onclick=" return abrirVentana('<%= "&preview=Y&reporte=clienteDiaFecha&cliente=" + cliente + "&start=" + start + "&end=" + end %>');">
    					<% } %>	
    				<% } %>			    					    			
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