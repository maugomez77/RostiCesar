<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="mx.com.rosti.to.Producto" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "pro"
			     scope = "page"
			     class = "mx.com.rosti.to.Producto" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
				
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Productos</h2></p>
    			<form action="modproductos.jsp?sessionid=<%= session.getId() %>" method="post">
    				<input type="submit" name="" value="Administrar Productos"><br>
    			</form>
    			
    			
					<table border="1"style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    					<tr bgcolor="silver">
    						<td>Clave de Producto</td>
    						<td>Nombre</td>
    						<td>Tipo Producto</td>
    						<td>Unidades</td>
    						<td>Fecha Alta</td>
    						<td>Proveedor</td>
    						<td>Costo</td>
    						<td>Precio 1</td>
    						<td>Precio 2</td>
    						<td>Precio 3</td>
    						<td>Precio 4</td>
    						<td>Precio 5</td>
    					</tr>
                        <% 
                           List lista = rostiAdmin.getListInfo("Productos");
  					       for (int i=0; i < lista.size(); i++) { 
  						       pro = (Producto) lista.get(i);
  						   %>
        				<tr>
        				    <td><%= pro.getIdProducto() %></td>
      						<td><%= pro.getNombre() %></td>
      						<td><%= pro.getTipoProducto() %></td>
      						<td><%= pro.getUnidades() %></td>
      						<td><%= Utilerias.getDate(pro.getFechaAlta(), "yyyy-MM-dd") %>
      						<td><%= pro.getProveedor() %></td>
      						<td><%= Utilerias.getCurrency().format(pro.getCosto()) %></td>
      						<td><%= Utilerias.getCurrency().format(pro.getPrecio1()) %></td>
      						<td><%= Utilerias.getCurrency().format(pro.getPrecio2()) %></td>
      						<td><%= Utilerias.getCurrency().format(pro.getPrecio3()) %></td>
      						<td><%= Utilerias.getCurrency().format(pro.getPrecio4()) %></td>
      						<td><%= Utilerias.getCurrency().format(pro.getPrecio5()) %></td>
      						<td><form action="modproductos.jsp?sessionid=<%= session.getId() %>" method="post">
      								<input type="hidden" name="id_producto" value="<%= pro.getIdProducto() %>">
      								<input type="hidden" name="id_proveedor" value="<%= pro.getIdProveedor()%>">
      						    	<input type="submit" name="Modificar" value="Mod">
      						    </form>
      						</td>    
      					</tr>
    					<% } %>
    				</table>
    			</center>
    			
    			<br><b><font color="black" size="2" face="verdana">Tienes <%= lista.size() %> producto(s) en el sistema.</font></b>
    			
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>