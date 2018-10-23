
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="mx.com.rosti.to.ProductoAcumulado"%>
<%@ page import="mx.com.rosti.validations.NumbersValidation"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
<%
    			    if (request.getParameter("entradaSalida") != null) {
	    			    
    			    	String eSalida = (String) request.getParameter("entradaSalida"); 
	    			        			    		 
	    			    List<String> datosCliente = null;    			    		    			    
	    			    if (!eSalida.equals("")) { 
	    			    
	    			        datosCliente = rostiAdmin.tomarClienteEntradaSalidaRemision(eSalida);
	    			        	    			        
	    			        out.println("<b>Usuario: " + session.getAttribute("name")  + "</b><br/>");							   
	    			        out.println("<b>Cliente: " + datosCliente.get(0) + " " + datosCliente.get(1) + "</b>");
	    			        out.println("<br><b>Remision: " + datosCliente.get(3) + "</b>");
	    			        out.println("<br><b>Fecha de Remision: " + datosCliente.get(4) + "</b>");
	    			        out.println("<br><b>Hora de Remision: " + datosCliente.get(5) + "</b>");
	    			    %>
	    			    
	    			    <form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>&entradaSalida=<%= eSalida %>" method="post">
	    			    
	    			    <table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
	    			    <%
	    			    
	    			    String openBold = "<b><font color=black size=2 face=verdana>";
	    			    
	    			    String closeBold = "</font></b>";
	    			    out.println("    <tr bgcolor=silver>");
	    			    out.println("        <td>" + openBold + "Clave" + closeBold + "</td>");
	    			    out.println("        <td>" + openBold + "Cantidad" + closeBold + "</td>");	    			    
	    			    //out.println("        <td>" + openBold + "Descripcion" + closeBold + "</td>");
	    			    //out.println("        <td>" + openBold + "Tipo Producto" + closeBold + "</td>");
	    			    //out.println("        <td>" + openBold + "Unidad" + closeBold + "</td>");
	    			    if (request.getParameter("individual") == null) {
	    			    	out.println("        <td>" + openBold + "Cajas" + closeBold + "</td>");
	    			    }
	    			    if (request.getParameter("preview") == null) { 
	    			    	out.println("        <td>" + openBold + "Costo" + closeBold + "</td>");
	    			    }
	    			    out.println("        <td>" + openBold + "Precio" + closeBold + "</td>");	    			    
	    			    out.println("        <td>" + openBold + "Total" + closeBold + "</td>");
	    			    out.println("    </tr>");
	    			    	    			
	    			    double contadorKG = 0;
	    			    double contadorPiezas = 0;
	    			    
	    			    List lista = null;
	    			    if (request.getParameter("individual") == null) {
	    			    	lista = rostiAdmin.getListInfo("ListaProductosListaSalida", eSalida);
	    			    } 
	    			    
	    			    if (request.getParameter("individual") != null && request.getParameter("individual").equals("Y")) {
	    			    	lista = rostiAdmin.getListInfo("ListaProductosListaSalidaIndividual", eSalida);
	    			    }
	    			    
	    			    double precioTotal = 0; 
	    			    for (int i=0; i < lista.size(); i++) {
	    			    	ProductoAcumulado box = (ProductoAcumulado) lista.get(i);
	    			    	
	    			    	out.println("<tr>");
	    			    	out.println("    <td>" + openBold + box.getIdProd() + closeBold + "</td>");
	    			    	out.println("    <td>" + openBold + box.getCantidad() + closeBold + "</td>");
	    			    	//out.println("    <td>" + openBold + box.getDescProd() + closeBold + "</td>");
	    			    	//out.println("    <td>" + box.getTipoProdDesc() + "</td>");
	    			    	//out.println("    <td>" + box.getUnidadProdDesc() + "</td>"); 
	    			    	
	    			        if (request.getParameter("individual") == null) {
		    			    	int cajasActuales = rostiAdmin.calculaCajasSalida(box.getIdProd(), box.getProv(), "Salida", eSalida);
		    			    	out.println("    <td>" + openBold + cajasActuales + closeBold + "</td>");
	    			    	}
	    			    	
	    			    	if (request.getParameter("reporte") == null && request.getParameter("individual") == null) { %>
	    			    	                 <td>
	    			    	                 	<% double costoBD = box.getCostoInventario(); 
	    			    	                 	   if (costoBD == 0) { 
	    			    	                 	   	   costoBD = box.getCostoProducto();
	    			    	                 	   }
	    			    	                 	%>
	    			    	                 	<input type="text" name="getCosto<%= i %>" value=<%= request.getParameter("getCosto" + i) != null && NumbersValidation.isDouble(request.getParameter("getCosto" + i)) ? request.getParameter("getCosto" + i): costoBD %>>
	    			    	                 </td>

	    			    	                 <td>
	    			    	                 	<% 
	    			    	                 		double precioPreferenteCliente = 0;
	    			    	                 		if (datosCliente != null && datosCliente.get(2).equals("0")) {
	    			    	                 			precioPreferenteCliente = box.getCostoProducto();
	    			    	                 		} else if (datosCliente != null && datosCliente.get(2).equals("1")) {
	    			    	                 			precioPreferenteCliente = box.getPrecio1();
	    			    	                 		} else if (datosCliente != null && datosCliente.get(2).equals("2")) {
	    			    	                 		 	precioPreferenteCliente = box.getPrecio2();
	    			    	                 		} else if (datosCliente != null && datosCliente.get(2).equals("3")) {
	    			    	                 			precioPreferenteCliente = box.getPrecio3();
	    			    	                 		} else if (datosCliente != null && datosCliente.get(2).equals("4")) {
	    			    	                 			precioPreferenteCliente = box.getPrecio4();
	    			    	                 		} else if (datosCliente != null && datosCliente.get(2).equals("5")) {
	    			    	                 			precioPreferenteCliente = box.getPrecio5();
	    			    	                 		}
	    			    	                 	%>		
	    			    	                 	<input type="text" name="get<%= i %>" value=<%= request.getParameter("get" + i) != null && NumbersValidation.isDouble(request.getParameter("get" + i)) ? request.getParameter("get" + i): precioPreferenteCliente %>>
	    			    	                 </td>
	    			    	                 <td><% if (request.getParameter("get" + i) != null && NumbersValidation.isDouble(request.getParameter("get" + i))) {	    			    	                 			
	    			    	                	 		double precio = Utilerias.strToDouble(request.getParameter("get" + i).toString());
	    			    	                	 		precio = precio * box.getCantidad();
	    			    	                	 		precioTotal += precio;	    			    	                	 		
	    			    	                	 		out.println(openBold + Utilerias.getCurrency().format(precio) + closeBold);
	    			    	                 		} else { 
	    			    	                 			double precio = precioPreferenteCliente * box.getCantidad();
	    			    	                	 		precioTotal += precio;	    			    	                	 		
	    			    	                	 		out.println(openBold + Utilerias.getCurrency().format(precio) + closeBold);
	    			    	                 		}
	    			    	                 	%>
	    			    	                 </td>
	    			    	<% } else { 
	    			    	        if (request.getParameter("preview") == null) {
	    			    	        	out.println("   <td>" + openBold + Utilerias.getCurrency().format(box.getCostoInventario()) + closeBold + "</td>");
	    			    	        }
	    			    	        out.println("   <td>" + openBold + Utilerias.getCurrency().format(box.getPrecioUnitario()) + closeBold + "</td>");
	    			    	        out.println("   <td>" + openBold + Utilerias.getCurrency().format(box.getCantidad() * box.getPrecioUnitario()) + closeBold + "</td>");
	    			    	        precioTotal +=  box.getCantidad() * box.getPrecioUnitario();
 	    			    	   }
	    			    	   
	    			    	   out.println("</tr>");
	    			    	
	    			    	if (box.getUnidadProd() == 1 || box.getUnidadProd() == 2) {
	    			    		contadorKG += box.getCantidad();
	    			    	} 
	    			    	
	    			    	if (box.getUnidadProd() == 3) {
	    			    		contadorPiezas += box.getCantidad();
	    			    	}
	    			    }
	    			    
	    				%>
	    				</table>
	    				<% if (request.getParameter("reporte") == null && request.getParameter("individual") == null) { %>
	    				    <input type="hidden" name="action" value="updatePrices"> 
	    					<input type="submit" value="Calcular Importe">
	    				<% } %>	 
	    				</form>	    				
	    				
	    				<% if (request.getParameter("individual") == null && request.getParameter("updateDone") != null) { %>
		    				<form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>&entradaSalida=<%= eSalida %>" method="post">
		    				<% if (request.getParameter("reporte") != null) { %>
		    					<input type="hidden" name="reporte" value="<%= request.getParameter("reporte") %>">
		    				<% } %>
		    			    	<input type="hidden" name="action" value="callingIndividualCases"> 
		    					<input type="submit" value="Ver Venta por Articulo">
		    				</form>
		    			<% } %>	
		    			
		    			<% if (request.getParameter("individual") != null) { %>
		    				<form action="/RostiCesar/scanVentas?sessionid=<%= session.getId() %>&entradaSalida=<%= eSalida %>" method="post">
		    				<% if (request.getParameter("reporte") != null) { %>
		    					<input type="hidden" name="reporte" value="<%= request.getParameter("reporte") %>">
		    				<% } %>
		    			    	<input type="hidden" name="action" value="callingNormal"> 
		    					<input type="submit" value="Ver Venta Acumulada">
		    				</form>
		    			<% } %>
	    				
	    				<br><b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
	    				<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>
	    				<br><b><font color=black size=2 face=verdana>Tienes una venta de <%= Utilerias.getCurrency().format(precioTotal) %> pesos en total.</font></b>
	    					    				
    			  <% } 
	    			    
    			  } %> 