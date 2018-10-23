<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Usuario" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "usr"
			     scope = "page"
			     class = "mx.com.rosti.to.Usuario" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Usuarios</h2></p>
    			<form action="modusuarios.jsp?sessionid=<%= session.getId() %>" method="post">
    				<input type="submit" name="" value="Administrar Usuarios"><br>
    			</form>
    			
    				<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    					<tr bgcolor="silver">
    						<td>Clave de Usuario</td>
    						<td>Nombre</td>
    						<td>Rol</td>
    						<td>Correo Electronico</td>
    						<td>Telefono</td>
    					</tr>
                        <% 
                           List listaUsuarios = rostiAdmin.getListInfo("Usuarios");
  					       for (int i=0; i < listaUsuarios.size(); i++) { 
  						       usr = (Usuario) listaUsuarios.get(i);
  						   %>
        				<tr>
        				    <td><%= usr.getIdUsuario() %></td>
      						<td><%= usr.getNombre() %></td>
      						<td><%= usr.getDescRol() %></td>
      						<td><%= usr.getCorreoElectronico() %></td>
      						<td><%= usr.getTelefono() %></td>
      						<td><form action="modusuarios.jsp?sessionid=<%= session.getId() %>" method="post">
      								<input type="hidden" name="username" value="<%= usr.getIdUsuario() %>">	
      						    	<input type="submit" name="Modificar" value="Modificar">
      						    </form>
      						</td>    
      					</tr>
    					<% } %>
    				</table>
    			
    			<br><b><font color="black" size="2" face="verdana">Tienes <%= listaUsuarios.size() %> usuario(s) en el sistema.</font></b>
    			
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>