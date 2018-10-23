<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Clientes" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "cli"
			     scope = "page"
			     class = "mx.com.rosti.to.Clientes" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Clientes</h2></p>
    			<form action="modclientes.jsp?sessionid=<%= session.getId() %>" method="post">
    				<input type="submit" name="" value="Administrar Clientes"><br>
    			</form>
    			
    				<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    					<tr bgcolor="silver">
    						<td>Clave de Cliente</td>
    						<td>Nombre</td>
    						<td>Precio Preferente</td>
    					</tr>
                        <% 
                           List listaClientes = rostiAdmin.getListInfo("Clientes");
  					       for (int i=0; i < listaClientes.size(); i++) { 
  						       cli = (Clientes) listaClientes.get(i);
  						   %>
        				<tr>
        				    <td><%= cli.getIdCliente() %></td>
      						<td><%= cli.getNombre() %></td>
      						<td><%= cli.getPrecioPreferente() %></td>
      						<td><form action="modclientes.jsp?sessionid=<%= session.getId() %>" method="post">
      								<input type="hidden" name="precio_preferente" value="<%= cli.getPrecioPreferente() %>">
      								<input type="hidden" name="id_cliente" value="<%= cli.getIdCliente() %>">	
      						    	<input type="submit" name="Modificar" value="Modificar">
      						    </form>
      						</td>    
      					</tr>
    					<% } %>
    				</table>
    			
    			<br><b><font color="black" size="2" face="verdana">Tienes <%= listaClientes.size() %> cliente(s) en el sistema.</font></b>
    			
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>