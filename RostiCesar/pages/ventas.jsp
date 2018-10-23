<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.servlets.ElementsXMLBox"%>
<%@ page import="mx.com.rosti.to.Proveedor"%>
<%@ page import="mx.com.rosti.to.Producto"%>
<%@ page import="mx.com.rosti.to.TipoProducto" %>
<%@ page import="mx.com.rosti.to.Unidades" %>
<%@ page import="mx.com.rosti.to.Clientes" %>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     
<jsp:useBean id = "tipoProducto"
			     scope = "page"
			     class = "mx.com.rosti.to.TipoProducto" />

<jsp:useBean id = "unidades"
			     scope = "page"
			     class = "mx.com.rosti.to.Unidades" />

<jsp:useBean id = "proveedor"
			     scope = "page"
			     class = "mx.com.rosti.to.Proveedor" />

<jsp:useBean id = "producto"
			     scope = "page"
			     class = "mx.com.rosti.to.Producto" />

<jsp:useBean id = "cli"
			     scope = "page"
			     class = "mx.com.rosti.to.Clientes" />

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
			
			%>
  
    		<%@ include file="../admin/Header.jsp" %>
    		
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Administraci&oacute;n de Ventas</h2></p>
    			
    			<% if (session.getAttribute("remision") == null || 
    				   request.getParameter("remisionError") != null ) { %>
    			
	    			<p>
	    			    <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" name="forma" method="post">
	    			    <input type="hidden" name="action" value="createRemission">
	    			    
	    			Cliente :
	    			     
		  				<select name="Cliente">
		  					<% List listaT = rostiAdmin.getListInfo("Clientes");
		  					   for (int i=0; i < listaT.size(); i++) { 
		  					       cli = (Clientes) listaT.get(i);
		  					%>
		      					<option value="<%= cli.getIdCliente() %>"><%= cli.getNombre() %></option>
		    				   <% } %>
		    			</select>
		    			<input type="submit" value="Crear Remision">
		    			</form>
		    		</p>	
		    		
    			
	   			    <%
	   			        if (request.getParameter("remisionError") != null) { %>	   			        	
	   			        	<script>alert("Esta remisiòn ya ha sido creada, favor de insertar una nueva remisiòn.");</script>  	   			        	
	   			        <% }
	   			    %>
    			
    			
    			<% } else { %>
    			    			
	    			<p><h3>Escaneo de cajas por Código para la Remision <%= session.getAttribute("remision") %> </h3></p>
	    			<p><h3>Datos del Cliente de la Remision: 
	    				<% cli = (Clientes) rostiAdmin.getData(session.getAttribute("clienteRemision").toString(), "Cliente"); %>
	    				<%= cli.getIdCliente() + " " + cli.getNombre() %> 
	    			    </h3>
	    			    <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post" >
	    			    	<input type="hidden" name="action" value="deshacerRemision">
	    			    	<input type="submit" value="Deshacer Remisión Junto con Articulos Escaneados">
	    			    </form>
	    			    
	    			</p>
	    			<form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" name="forma" method="post" onsubmit="return false;">
	    			    <input type="hidden" name="action" value="create">
	    			    <input type="text" name="myinputid" maxlength="33" size="33" onkeyup="return validateBoxVentas('<%= session.getId() %>', event);">
	    			    <input type="reset" value="Limpiar" onclick="return focusText();">
	    			</form>
	    			
	    			<p><h3>Escaneo de cajas por Piezas</h3></p>
	    			
	    			<% if (request.getParameter("action") == null) { %>
	    			
		    			<p>Tipo de Producto:
		    			    <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">
		    			    <input type="hidden" name="action" value="TipoProducto"> 
			  				<select name="TipoProducto">
			  					<% List listaT = rostiAdmin.getListInfo("TipoProducto");
			  					   for (int i=0; i < listaT.size(); i++) { 
			  					       tipoProducto = (TipoProducto) listaT.get(i);
			  					%>
			      					<option value="<%= tipoProducto.getIdTipoProducto() %>"><%= tipoProducto.getNombre() %></option>
			    				   <% } %>
			    			</select>
			    			<input type="submit" value="Enviar">
			    			</form>
			    		</p>
	    			    			
	    			<%  } 
	    			
	    			    if (request.getParameter("action") != null && request.getParameter("action").equals("TipoProducto")) { %>
	    			    	<p>Tipo de Producto Seleccionado: <%= request.getParameter("TipoProducto")%> <%= request.getParameter("DescTipoProducto") %>
	    	    			    <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">    	    			    
	    	    			    <input type="hidden" name="action" value="Unidades">
	    	    			    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">
	    	    			    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    	    			    
	    	    			   Unidades:   
	    		  				<select name="Unidades">
	    		  					<% List listaT = rostiAdmin.getListInfo("UnidadesTipoProductoNoCode", request.getParameter("TipoProducto"));
	    		  					   for (int i=0; i < listaT.size(); i++) { 
	    		  					       unidades = (Unidades) listaT.get(i);
	    		  					%>
	    		      					<option value="<%= unidades.getIdUnidad() %>"><%= unidades.getNombre() %></option>
	    		    				   <% } %>
	    		    			</select>
	    		    			<input type="submit" value="Enviar">
	    		    			</form>
	    		    			
	    		    			
	    		    			<form action="/RostiCesar/pages/ventas.jsp?sessionid=<%= session.getId() %>" method="post">    	    			    
	    	    			    <input type="submit" value="Regresar">
	    		    			</form>
	    		    			
	    		    		</p>
	    			    <% }
	    			    
	    			    if (request.getParameter("action") != null && request.getParameter("action").equals("Unidades")) { %>
	    			        <p>Tipo de Producto Seleccionado: <%= request.getParameter("TipoProducto")%> <%= request.getParameter("DescTipoProducto") %><br>
	    			           Unidades Seleccionadas: <%= request.getParameter("Unidades")%> <%= request.getParameter("DescUnidades") %><br>
	    			            <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">    	    			    
	    	    			    <input type="hidden" name="action" value="Proveedores">
	    	    			    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">
	    	    			    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    	    			    <input type="hidden" name="Unidades" value="<%= request.getParameter("Unidades") %>">
	    	    			    <input type="hidden" name="DescUnidades" value="<%= request.getParameter("DescUnidades") %>">
	    	    			    
	    	    			    Proveedores:   
	    		  				<select name="Proveedores">
	    		  					<% List listaT = rostiAdmin.getListInfo("ProveedoresUnidadesTipoProductoVentas", request.getParameter("Unidades") + "|" + request.getParameter("TipoProducto"));
	    		  					   for (int i=0; i < listaT.size(); i++) { 
	    		  					       proveedor = (Proveedor) listaT.get(i);
	    		  					%>
	    		      					<option value="<%= proveedor.getIdProveedor() %>"><%= proveedor.getNombre() %></option>
	    		    				   <% } %>
	    		    			</select>
	    		    			<input type="submit" value="Enviar">
	    		    			</form>
	    		    			
	    		    			<form action="/RostiCesar/pages/ventas.jsp?sessionid=<%= session.getId() %>" method="post">
	    		    			<input type="hidden" name="action" value="TipoProducto">
	    		    		    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">    		    			    	    			    
	    		    		    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    	    			    <input type="submit" value="Regresar">
	    		    			</form>
	    		    			
	    			    <% } 
	    			    
	    			       if (request.getParameter("action") != null && request.getParameter("action").equals("Proveedores")) { %>
	    			       
	    			        <p>Tipo de Producto Seleccionado: <%= request.getParameter("TipoProducto")%> <%= request.getParameter("DescTipoProducto") %><br>
	    			           Unidades Seleccionadas: <%= request.getParameter("Unidades")%> <%= request.getParameter("DescUnidades") %><br>
	    			           Proveedor Seleccionado: <%= request.getParameter("Proveedores")%> <%= request.getParameter("DescProveedores") %><br>
	    			            <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">    	    			    
	    	    			    <input type="hidden" name="action" value="Kilogramos">
	    	    			    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">
	    	    			    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    	    			    <input type="hidden" name="Unidades" value="<%= request.getParameter("Unidades") %>">
	    	    			    <input type="hidden" name="DescUnidades" value="<%= request.getParameter("DescUnidades") %>">
	    	    			    <input type="hidden" name="Proveedores" value="<%= request.getParameter("Proveedores") %>">
	    	    			    <input type="hidden" name="DescProveedores" value="<%= request.getParameter("DescProveedores") %>">
	    	    			    
	    	    			    Productos con critero seleccionado:   
	    		  				<select name="Productos">
	    		  					<% List listaT = rostiAdmin.getListInfo("ListaProductosVentas", 
	    		  							request.getParameter("Proveedores") + "|" + request.getParameter("Unidades") + "|" + request.getParameter("TipoProducto"));
	    		  					   for (int i=0; i < listaT.size(); i++) { 
	    		  					       producto = (Producto) listaT.get(i);
	    		  					%>
	    		      					<option value="<%= producto.getIdProducto() + "|" + producto.getIdProveedor()%>"><%= producto.getIdProducto() + " - " + producto.getNombre() %></option>
	    		    				   <% } %>
	    		    			</select>
	    		    			
	    	    			    <input type="submit" value="Enviar">
	    		    			</form>
	    		    			
	    		    			<form action="/RostiCesar/pages/ventas.jsp?sessionid=<%= session.getId() %>" method="post">
	    		    			<input type="hidden" name="action" value="Unidades">
	    		    		    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">    		    			    	    			    
	    		    		    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    		    		    <input type="hidden" name="Unidades" value="<%= request.getParameter("Unidades") %>">
	    		    		    <input type="hidden" name="DescUnidades" value="<%= request.getParameter("DescUnidades") %>">
	    	    			    <input type="submit" value="Regresar">
	    		    			</form>
	    		    			
	    			    <% }   
	    			       
	    			       if (request.getParameter("action") != null && request.getParameter("action").equals("Kilogramos")) { %>
	    			           
	    			           <p>Tipo de Producto Seleccionado: <%= request.getParameter("TipoProducto")%> <%= request.getParameter("DescTipoProducto") %><br>
	    			              Unidades Seleccionadas: <%= request.getParameter("Unidades")%> <%= request.getParameter("DescUnidades") %><br>
	    			              Proveedor Seleccionado: <%= request.getParameter("Proveedores")%> <%= request.getParameter("DescProveedores") %><br>
	    			              Producto Seleccionado: <%= request.getParameter("Productos")%> <%= request.getParameter("DescProductos") %><br>
	    			            <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">    	    			    
	    	    			    <input type="hidden" name="action" value="AgregarItem">
	    	    			    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">
	    	    			    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    	    			    <input type="hidden" name="Unidades" value="<%= request.getParameter("Unidades") %>">
	    	    			    <input type="hidden" name="DescUnidades" value="<%= request.getParameter("DescUnidades") %>">
	    	    			    <input type="hidden" name="Proveedores" value="<%= request.getParameter("Proveedores") %>">
	    	    			    <input type="hidden" name="DescProveedores" value="<%= request.getParameter("DescProveedores") %>">
	    	    			    <input type="hidden" name="Productos" value="<%= request.getParameter("Productos") %>">
	    	    			    <input type="hidden" name="DescProductos" value="<%= request.getParameter("DescProductos") %>">
	    	    			    
	    	    			    Introduce la cantidad númerica a agregar:
	    	    			    <input type="text" name="numerica">   
	    		  				<input type="submit" value="Enviar">
	    		    			</form>
	    		    			
	    		    			<form action="/RostiCesar/pages/ventas.jsp?sessionid=<%= session.getId() %>" method="post">
	    		    			<input type="hidden" name="action" value="Proveedores">
	    		    		    <input type="hidden" name="TipoProducto" value="<%= request.getParameter("TipoProducto") %>">    		    			    	    			    
	    		    		    <input type="hidden" name="DescTipoProducto" value="<%= request.getParameter("DescTipoProducto") %>">
	    		    		    <input type="hidden" name="Unidades" value="<%= request.getParameter("Unidades") %>">
	    		    		    <input type="hidden" name="DescUnidades" value="<%= request.getParameter("DescUnidades") %>">
	    		    		    <input type="hidden" name="Proveedores" value="<%= request.getParameter("Proveedores") %>">
	    		    		    <input type="hidden" name="DescProveedores" value="<%= request.getParameter("DescProveedores") %>">
	    		    		    
	    	    			    <input type="submit" value="Regresar">
	    		    			</form>
	    		    			
	    			    <% } 
	    			       
	    			       if (request.getParameter("wrongBox") != null && request.getParameter("wrongBox").equals("Y")) { %>
	    			    	   <script>alert("No se agregar dicha caja debido a que ya no existe mas inventario.");</script>
	    			       <% }
	    			       
	    			       if (request.getParameter("addedOtherTrans") != null && request.getParameter("addedOtherTrans").equals("Y")) { 
	    			       
	    			    	   List errors = (List) session.getAttribute("errors");
	    			           String msg = "";
	    			    	   for (int i=0; i < errors.size(); i++) {
	    			        	   msg += errors.get(i).toString();
	    			           }
	    			           
	    			           session.removeAttribute("errors");
	    			           %>
    			    	       <script>alert("Pasaron los siguiente problemas: " + <%= msg %>);</script>
    			           <% }
    			       	    			       
	    			       if (request.getParameter("Added") != null && request.getParameter("Added").equals("Good")) { %>
	    			    	<script>alert("Se ha agreagado exitosamente el inventario");</script>
	    			    <% }
	    			
	    			    if (session.getAttribute("listaCajasVentas") != null) {
		    			    List lista = (List) session.getAttribute("listaCajasVentas"); 
		    			    
		    			    if (lista.size() > 0) { %> 
		    			    
		    			    <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">
		    			        <input type="hidden" name="action" value="registerVenta">
	 		    			    <input type="submit"  value="Registrar Venta">
			    			</form>
		    			    		    			    
		    			    <br><b><font color=black size=2 face=verdana>Tienes <%= lista.size() %> articulo(s).</font></b>
		    			    
		    			    <table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
		    			    <%
		    			    out.println("    <tr bgcolor=silver>");
		    			    out.println("        <td>SKU</td>");
		    			    out.println("        <td>Clave</td>");
		    			    out.println("        <td>Cantidad</td>");		    			    
		    			    out.println("        <td>Descripcion</td>");
		    			    out.println("        <td>Prov</td>");
		    			    out.println("        <td>Tipo Producto</td>");
		    			    out.println("        <td>Unidad</td>");
		    			    out.println("        <td>Accion</td>");
		    			    out.println("    </tr>");
		    			    
		    			    } 
		    			    
		    			    for (int i=0; i < lista.size(); i++) {
		    			    	ElementsXMLBox box = (ElementsXMLBox) lista.get(i);
		    			    	
		    			    	out.println("<tr>");
		    			    	out.println("    <td>" + box.getStorage() + "</td>");
		    			    	out.println("    <td>" + box.getCodeProduct() + "</td>");
		    			    	out.println("    <td>" + box.getCantidad() + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getDescProduct() + "</td>");
		    			    	out.println("    <td>" + box.getDescProveedor() + "</td>");
		    			    	out.println("    <td>" + box.getTipoProductoDesc() + "</td>");
		    			    	out.println("    <td>" + box.getUnidadMedidaDesc() + "</td>");
		    			    	
		    			    	out.println("    <td>");
		    			    	%>
		    			    	
		    			    	
							    			    	<form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>" method="post">
							    			    	    <input type="hidden" name="action" value="eliminate">
								    			        <input type="hidden" name="elemento" value="<%= i %>">
								    			        <input type="submit"  value="Eliminar Caja">
								    			    </form>
							    			    </td>
						    			   </tr>
		    			    <% }  
		    			    
		    			    
		    			    if (lista.size() > 0) { %>
		    			    
		    			    </table>
		    			    
	    			    <% } 
	    			  }
	    	    } %> 
    			                   
                 <script type="text/javascript">
                    var textBox = forma.myinputid;
                    textBox.focus();
                 </script>  			
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