<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.to.ReporteInventario"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

			     
				<p><h4>Remisiones pertenecientes al cliente</h4></p>
				<% out.println("<b>Usuario: " + session.getAttribute("name")  + "</b><br/>"); %>
				
				<% out.println("<b>Cliente: " + rostiAdmin.getData(request.getParameter("cliente"), "Cliente") + "</b>"); %>
				<form action="/RostiCesar/ActualizaPrecios?sessionid=<%= session.getId() %>&parameters=<%= request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente")%>" method="post">
				<input type="hidden" name="action" value="reporte">
	    		<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
		    			    <%
		    			    
		    			    boolean aplicaCosto = true;
		    			    
		    			    if (request.getParameter("aplicaCosto") != null && request.getParameter("aplicaCosto").equals("N")) {
		    			    	 aplicaCosto = false;
		    			    }
		    			    
		    			    String headerTable = ""
		    			        + "        <td>Remision</td>"
		    			        + "        <td>Clave</td>"
		    			        + "        <td>Cantidad</td>"
		    			        + "        <td>Descripcion</td>"
		    			        + "        <td>Unidad</td>"		    			        
		    			        + "        <td>Prov</td>"
		    			        + "        <td>Cajas</td>"
		    			        + "        <td>PV</td>"
		    			        + "        <td>Importe</td>"
		    			        + "        <td>Fecha</td>";
		    			    
		    			    String headerTableCosto = ""
		    			        + "        <td>Remision</td>"
		    			        + "        <td>Clave</td>"
		    			        + "        <td>Cantidad</td>"
		    			        + "        <td>Descripcion</td>"
		    			        + "        <td>Unidad</td>"		    			        
		    			        + "        <td>Prov</td>"
		    			        + "        <td>Cajas</td>"
		    			        + "        <td>PV</td>"
		    			        + "        <td>CU</td>"
		    			        + "        <td>Importe</td>"
		    			        + "        <td>Total Costo</td>"
		    			        + "        <td>Fecha</td>";
		    			        
		    			    out.println("    <tr bgcolor=silver>");
		    			    if (aplicaCosto) { 
		    			    	out.println(headerTableCosto);
		    			    } else {
		    			        out.println(headerTable);
		    			    }	
		    			    out.println("    </tr>");
		    			    	    			
		    			    double contadorKG = 0;
		    			    double contadorPiezas = 0;
		    			    boolean flagRemisionTodas = false;
		    			    if (request.getParameter("remisionTodas") != null && request.getParameter("remisionTodas").equals("Y")) {
		    			    	flagRemisionTodas = true;
		    			    }
		    			    List lista = null;
							lista = rostiAdmin.getListInfo("ReporteRemisionFechas", request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente"));
		    			    		    			     
		    			    String remision = "";
		    			    for (int i=0; i < lista.size(); i++) {
	                            
		    			    	ReporteInventario box = (ReporteInventario) lista.get(i);
		    			    	if (remision.equals("")) {
		    			    		remision = box.getRemision();
		    			    	} else {
		    			    		//existe cambio de remision
		    			    		//para el caso de todas las remisiones
		    			    		//con la bandera no tendrà porque entrar a este flujo de datos.
		    			    		if (!remision.equals(box.getRemision()) && !flagRemisionTodas) {
		    			    			out.println("</table>"); 
		    			    			
		    			    			double total = rostiAdmin.traerCantidadTotalRemision(remision);
		    			    			double costoTotal = rostiAdmin.traerCantidadCostoTotalRemision(remision);
		    			    			double pagado = rostiAdmin.traerPagadoRemision(remision);
		    			    			//para el caso cuando se pago por remisiones el valor (agrupadas) y sobrepasa el 
		    			    			//limite de una sola remisión.
		    			    			//if (total >= pagado) {
		    			    			//	pagado = total;
		    			    			//}
		    			    			out.println("<br/>Importe de la Venta: " + total);
		    			    			if (aplicaCosto) { 
		    			    			 	out.println("<br/>Costo: " + costoTotal);
		    			    			}
		    			    			
		    			    			if (request.getParameter("reporte") == null) { %>
		    			    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cantidad Pagada: <input type="text" name="cantidad<%= remision %>" value=<%= pagado %>>
		    			    			<% } else {   
		    			    				out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cantidad Pagada: " + pagado);
		    			    			   }
		    			    			   remision = box.getRemision(); %>
		    			    			
		    			    			<br/>
		    			    			<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
		    			    			<%
		    			    			out.println("    <tr bgcolor=silver>");
		    			    			if (aplicaCosto) { 	    	
		    			    				out.println(headerTableCosto);
		    			    			} else {
		    			    			 	out.println(headerTable);
		    			    			}
		    		    			    out.println("    </tr>");
		    			    			
		    			    		}
		    			    	}
		    			    	
		    			    	out.println("<tr>");
		    			    	out.println("    <td>" + box.getRemision() + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getCodeProd() + "</td>");
		    			    	out.println("    <td>" + Utilerias.getDecimalFormat().format(box.getCantidad()) + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getDescCodeProducto() + "</td>");
		    			    	out.println("    <td>" + box.getDescUnid() + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getDescProveedor() + "</td>");
		    			    	int cajasActuales = rostiAdmin.calculaCajasSalida(box.getCodeProd(), box.getProv(), "Salida", box.getSalidaCarga());
		    			    	out.println("    <td>" + cajasActuales + "</td>"); 
		    			    	
		    			    	if (request.getParameter("reporte") == null) { %>
		    			    		<td><input type="text" name="get<%= i %>" value=<%= box.getPrecioUnitario() %>></td>
		    			    		<% if (aplicaCosto) { %>
		    			    			<td><input type="text" name="getCosto<%= i %>" value=<%= box.getCostoUnitario() %>></td>
		    			    		<% } %>	  	
		    			    	<%} else {
		    			    		out.println("    <td>" + box.getPrecioUnitario() + "</td>");
		    			    		if (aplicaCosto) { 
		    			    			out.println("    <td>" + box.getCostoUnitario() + "</td>");
		    			    		}
		    			    	  }
		    			    	
		    			    	out.println("    <td>" + Utilerias.getCurrency().format(box.getPrecioUnitario() * box.getCantidad()) + "</td>");
		    			    	if (aplicaCosto) { 
		    			    		out.println("    <td>" + Utilerias.getCurrency().format(box.getCostoUnitario() * box.getCantidad()) + "</td>");
		    			    	}
		    			    	out.println("    <td>" + Utilerias.getDate(box.getFechaSalida(), "yyyy-MM-dd") + "</td>");
		    			    	out.println("</tr>");
		    			    	
		    			    	
		    			    	if (box.getUnid() == 1 || box.getUnid() == 2) {
		    			    		contadorKG += box.getCantidad();
		    			    	} 
		    			    	
		    			    	if (box.getUnid() == 3) {
		    			    		contadorPiezas += box.getCantidad();
		    			    	}
		    			    }
		    			    %>
		    		</table>
		    		<%
		    		    double total = 0;
		    		    double pagado = 0;
		    		    double costoCantidad = 0;
		    		    if (flagRemisionTodas) {
		    		    	total = rostiAdmin.traerCantidadTotalRemisiones(request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente"));
		    		    	costoCantidad = rostiAdmin.traerCantidadCostoTotalRemisiones(request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente"));
		    			    pagado = rostiAdmin.traerPagadoRemisiones(request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente"));
		    			    out.println("<br/>Importe de la Venta: " + total);
		    			    if (aplicaCosto) { 
		    			    	out.println("<br/>Costo: " + costoCantidad);
		    			    }		    			    
		    		    } 
		    		    
		    		    if (!remision.equals("") && !flagRemisionTodas){
		    		    	total = rostiAdmin.traerCantidadTotalRemision(remision);
		    			    costoCantidad = rostiAdmin.traerCantidadCostoTotalRemision(remision);
		    			    pagado = rostiAdmin.traerPagadoRemision(remision);
		    			    out.println("<br/>Importe de la Venta: " + total);
		    			    if (aplicaCosto) { 
		    			    	out.println("<br/>Costo: " + costoCantidad);
		    			    }
		    		    }
		    		    //aqui no tendrìa porque checar los mayores para la remisiòn en cuanto a las
		    		    //cantidades, porque serìa para los totales del a remisiòn.
		    			
			    		if (request.getParameter("reporte") == null) { %>
			    		    <% if (flagRemisionTodas) { %>
			    		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cantidad Pagada: <input type="text" name="cantidad" value=<%= pagado %>>       
			    		    <% } else { %>
			    		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cantidad Pagada: <input type="text" name="cantidad<%= remision %>" value=<%= pagado %>>
			    		    <% } %>
		    			<% } else {   
		    				out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cantidad Pagada: " + pagado);
		    			} %>
			    		
			    	<% if (request.getParameter("reporte") == null) { %>
			    		<input type="submit" value="Actualizar Pagos">
			    	<% } %>	
			    	
			    	</form>
			    	
			    	<% if (request.getParameter("aplicaCosto") == null) { %>
				    	<form action="../pages/clienteDiaFecha.jsp?sessionid=<%= session.getId() %>" method="post">
							<input type="hidden" name="start" value=<%= request.getParameter("start") %>>
							<input type="hidden" name="end" value=<%= request.getParameter("end") %>>
							<input type="hidden" name="cliente" value=<%= request.getParameter("cliente") %>>
							<input type="hidden" name="aplicaCosto" value="N">
							<% if (flagRemisionTodas) { %>
								<input type="hidden" name="remisionTodas" value="Y">
							<% } %>
							<% if (request.getParameter("preview") == null) { %>
								<input type="submit" value="Ocultar Costo" name="ocultarCosto">
							<% } %>		
						</form>
					<% } %>
					
					<% if (request.getParameter("aplicaCosto") != null && request.getParameter("aplicaCosto").equals("N")) { %>
						<form action="../pages/clienteDiaFecha.jsp?sessionid=<%= session.getId() %>" method="post">
							<input type="hidden" name="start" value=<%= request.getParameter("start") %>>
							<input type="hidden" name="end" value=<%= request.getParameter("end") %>>
							<input type="hidden" name="cliente" value=<%= request.getParameter("cliente") %>>
							<% if (flagRemisionTodas) { %>
								<input type="hidden" name="remisionTodas" value="Y">
							<% } %>
							<% if (request.getParameter("preview") == null) { %>
								<input type="submit" value="Mostrar Costo" name="mostrarCosto">
							<% } %>	
						</form>
					<% } %>
					
			    	<% if (request.getParameter("reporte") == null) { %>
				    	<form action="../pages/clienteDiaFecha.jsp?sessionid=<%= session.getId() %>" method="post">
							<input type="hidden" name="action" value="reporte">
							<input type="hidden" name="start" value=<%= request.getParameter("start") %>>
							<input type="hidden" name="end" value=<%= request.getParameter("end") %>>
							<input type="hidden" name="cliente" value=<%= request.getParameter("cliente") %>>
							<input type="hidden" name="remisionTodas" value="Y">
							<input type="submit" value="Todas las remisiones">
						</form>
						
						<!-- 
						<form action="../pages/itemDevolucion.jsp?sessionid=<%= session.getId() %>" method="post">
							<input type="hidden" name="start" value=<%= request.getParameter("start") %>>
							<input type="hidden" name="end" value=<%= request.getParameter("end") %>>
							<input type="hidden" name="cliente" value=<%= request.getParameter("cliente") %>>
							<input type="submit" value="Devolución por Item">
						</form>
						 -->
					<% } %>
	    					    	
			    			
			    	<b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
		    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>
		    		
		    		