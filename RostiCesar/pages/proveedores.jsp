<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Proveedor" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "pro"
			     scope = "page"
			     class = "mx.com.rosti.to.Proveedor" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Proveedores</h2></p>
    			<form action="modproveedores.jsp?sessionid=<%= session.getId() %>" method="post">
    				<input type="submit" name="" value="Administrar Proveedores"><br>
    			</form>
    			
    				<table border="1"style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    					<tr bgcolor="silver">
    						<td>Clave de Proveedor</td>
    						<td>Nombre</td>
    						<td>Descripcion</td>
    					</tr>
                        <% 
                           List listaProveedores = rostiAdmin.getListInfo("Proveedores");
  					       for (int i=0; i < listaProveedores.size(); i++) { 
  						       pro = (Proveedor) listaProveedores.get(i);
  						   %>
        				<tr>
        				    <td><%= pro.getIdProveedor() %></td>
      						<td><%= pro.getNombre() %></td>
      						<td><%= pro.getDescripcion() %></td>
      						<td><form action="modproveedores.jsp?sessionid=<%= session.getId() %>" method="post">
      								<input type="hidden" name="id_proveedor" value="<%= pro.getIdProveedor() %>">	
      						    	<input type="submit" name="Modificar" value="Modificar">
      						    </form>
      						</td>    
      					</tr>
    					<% } %>
    				</table>
    			
    			<br><b><font color="black" size="2" face="verdana">Tienes <%= listaProveedores.size() %> proveedor(es) en el sistema.</font></b>
    			
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>