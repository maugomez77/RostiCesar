
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.to.Remision"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     		
		<p><h4>Busqueda por: </h4></p>
    			<form method="post" action="previewPage.jsp?sessionid=<%= session.getId() %>">
    			<input type="hidden" name="reporte" value="remisiones">
    			<select name="busqueda">
    			    <option value="none">Seleccionar opcion
    			    <option value="todas">Mostrar todas las remisiones
    			    <option value="numero">Numero de Remision
    				<option value="cliente">Cliente de la remision    				
    			</select>
    			<input type="submit" value="Enviar">
    			</form>
    			
    			<% 
    			    boolean data = false;
    			    List datos = null;
    			    if (request.getParameter("busqueda") != null && request.getParameter("busqueda").equals("todas")) {     			    
    			        datos = rostiAdmin.getListInfo("RemisionTodas", "");
    			        data = true;
    			    } else if (request.getParameter("remisionCliente") != null) {
    			    	datos = rostiAdmin.getListInfo("remisionCliente", request.getParameter("remisionCliente").toString());
    			    	data = true;    			    	
    			    }  else if (request.getParameter("remisionNumero") != null) {
    			    	datos = rostiAdmin.getListInfo("remisionNumero", request.getParameter("remisionNumero").toString());
    			    	data = true;    			    	
    			    } else if (request.getParameter("busqueda") != null && request.getParameter("busqueda").equals("cliente")) { %>
    			    	<form method="post" action="previewPage.jsp?sessionid=<%= session.getId() %>">
    			    	<input type="hidden" name="reporte" value="remisiones"> 
    			        <select name="remisionCliente">
    			        <% datos = rostiAdmin.getListInfo("RemisionCliente", "");
    			           for (int i=0; i < datos.size(); i++) {
    		 	        	   Remision rem = (Remision) datos.get(i);
    			           %>
         					<option value="<%= rem.getIdCliente() %>"><%= rem.getIdCliente() + "   " +  rem.getNombreCliente() %></option>
    			    	<% } %>
		    			</select>
		    			<input type="submit" value="Enviar">
		    			</form>
		    			
    			    <% } else if (request.getParameter("busqueda") != null && request.getParameter("busqueda").equals("numero")) { %>
    			    	<form method="post" action="previewPage.jsp?sessionid=<%= session.getId() %>">
    			    	<input type="hidden" name="reporte" value="remisiones"> 
    			        <select name="remisionNumero">
    			        <% datos = rostiAdmin.getListInfo("RemisionNumero", "");
    			           for (int i=0; i < datos.size(); i++) {
    		 	        	   Remision rem = (Remision) datos.get(i);
    			           %>
         					<option value="<%= rem.getIdRemision() %>"><%= rem.getIdRemision() + "   " +  rem.getNombreCliente() %></option>
    			    	<% } %>
		    			</select>
		    			<input type="submit" value="Enviar">
		    			</form>
    			    <% }
    			    
    			    if (data) { %>
    			
    					<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
	    			    <%
	    			    out.println("    <tr bgcolor=silver>");	    	
	    			    out.println("        <td>Remision</td>");
	    			    out.println("        <td>Cliente</td>");
	    			    out.println("        <td>Prov</td>");
	    			    out.println("        <td>Cantidad</td>");	    			    
	    			    out.println("        <td>Clave</td>");
	    			    out.println("        <td>Fecha Entrada</td>");
	    			    out.println("        <td>Fecha Salida</td>");
	    			    out.println("        <td>Precio Unitario</td>");
	    			    out.println("        <td>Precio Total</td>");
	    			    out.println("        <td>SKU</td>");
	    			    out.println("    </tr>");
	    			    	    			
	    			    double contadorKG = 0;
	    			    double contadorPiezas = 0;
	    			    
	    			    
	    			    for (int i=0; i < datos.size(); i++) {
	    			    	Remision box = (Remision) datos.get(i);
	    			    	
	    			    	out.println("<tr>");
	    			    	out.println("    <td>" + box.getIdRemision() + "</td>");
	    			    	out.println("    <td>" + box.getNombreCliente() + "</td>");
	    			    	out.println("    <td>" + box.getNombreProv() + "</td>");
	    			    	out.println("    <td>" + box.getCantidad() + "</td>");
	    			    	out.println("    <td>" + box.getNombreProducto() + "</td>");
	    			    	out.println("    <td>" + Utilerias.getDate(box.getFechaEntrada(), "yyyy-MM-dd") + "</td>");
	    			    	out.println("    <td>" + Utilerias.getDate(box.getFechaSalida(), "yyyy-MM-dd") + "</td>");	    			    	
	    			    	out.println("    <td>" + box.getPrecioUnitario() + "</td>");
	    			    	out.println("    <td>" + box.getPrecioTotal() + "</td>");
	    			    	out.println("    <td>" + box.getIdInventario() + "</td>");
							out.println("</tr>");
	    			    	    			    	
	    			    }
	    			    
	    				%>
	    		</table>
	    		
	    		<% }  %>	