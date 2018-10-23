<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Roles" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "roles"
			     scope = "page"
			     class = "mx.com.rosti.to.Roles" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Roles</h2></p>
    			<form action="modroles.jsp?sessionid=<%= session.getId() %>" method="post">
    				<input type="submit" name="" value="Administrar Roles"><br>
    			</form>
    			
    				<table border="1"style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    					<tr bgcolor="silver">
    						<td>Clave de Rol</td>
    						<td>Nombre</td>
    						<td>Descripcion</td>
    					</tr>
                        <% 
                           List lista = rostiAdmin.getListInfo("Roles");
  					       for (int i=0; i < lista.size(); i++) { 
  						       roles = (Roles) lista.get(i);
  						   %>
        				<tr>
        				    <td><%= roles.getIdRol() %></td>
      						<td><%= roles.getNombreRol() %></td>
      						<td><%= roles.getDescripcion() %></td>
      						<td><form action="modroles.jsp?sessionid=<%= session.getId() %>" method="post">
      								<input type="hidden" name="id_rol" value="<%= roles.getIdRol() %>">	
      						    	<input type="submit" name="Modificar" value="Modificar">
      						    </form>
      						</td>    
      					</tr>
    					<% } %>
    				</table>
    			
    			<br><b><font color="black" size="2" face="verdana">Tienes <%= lista.size() %> role(s) en el sistema.</font></b>
    			
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>