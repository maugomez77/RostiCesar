<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.to.ReporteInventario"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

			     
				<p><h4>Remisiones de todos los clientes</h4></p>
				
				<%  
				
			    String headerTable = ""
			        + "        <td>Remision</td>"
			        + "        <td>Clave</td>"
			        + "        <td>Cantidad</td>"
			        + "        <td>Descripcion</td>"
			        + "        <td>Unidad</td>"
			        + "        <td>Prov</td>"
			        + "        <td>Cajas</td>"
			        + "        <td>Precio</td>"
			        + "        <td>Total</td>"
			        + "        <td>Fecha</td>";
			        
			    	    			
			    double contadorKG = 0;
			    double contadorPiezas = 0;
			    double precio = 0;
			    			    
			    String remision = "";
			    
			    double contadorKGCliente = 0;
			    double contadorPiezasCliente = 0;
			    double precioCliente = 0;
			    
			    String clienteActual = "";

			    List lista = null;
			    lista = rostiAdmin.getListInfo("ReporteRemisionFechasClienteUnificado", request.getParameter("start") + "|" + request.getParameter("end"));		    			    		    			     												
			    
			    for (int i=0; i < lista.size(); i++) {			    	
			    	ReporteInventario box = (ReporteInventario) lista.get(i); 			    	
			    				    	
			    	if (i == 0) { %>
			    		<% out.println("Cliente: " + box.getCliente()); %>
			    		
			    		<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
				    	
				    	<% 
					    out.println("    <tr bgcolor=silver>");
					    out.println(headerTable);
					    out.println("    </tr>");

		    			clienteActual = box.getCliente();
		    		} else { 
		    			//si ya es otro cliente
		    			if (!clienteActual.equals(box.getCliente())) { %>
		    		 		
		    				</table> 
		    			    			
		    				<b><font color=black size=2 face=verdana>Tienes <%= contadorKGCliente %> kilogramo(s) en total en este cliente.</font></b>
		    		 		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezasCliente %> pieza(s) en total en el cliente.</font></b>
		    		   		<br><b><font color=black size=2 face=verdana>Cobraste <%= Utilerias.getCurrency().format(precioCliente) %> pesos.</font></b>
							<br/><br/>
							
							
							<% out.println("Cliente: " + box.getCliente()); %>										    	
		    		   		<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
		    			    		
		    		   		<%
		    			
		    				out.println("    <tr bgcolor=silver>");
		    			    out.println(headerTable);
		    			    out.println("    </tr>");
		    		   			    
		    			    contadorKGCliente = 0;
		    			    contadorPiezasCliente = 0;
		    			    precioCliente = 0;
		    		    			    
		    			    clienteActual = box.getCliente();
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
		    		out.println("    <td>" + box.getPrecioUnitario() + "</td>");
		    		out.println("    <td>" + Utilerias.getCurrency().format(box.getPrecioUnitario() * box.getCantidad()) + "</td>");		    			    	
		    		out.println("    <td>" + Utilerias.getDate(box.getFechaSalida(), "yyyy-MM-dd") + "</td>");
		    		out.println("</tr>");
		    			    	
		    			    	
		    		if (box.getUnid() == 1 || box.getUnid() == 2) {
		    			contadorKG += box.getCantidad();
		    			contadorKGCliente += box.getCantidad();
		    		} 
		    			    	
		    		if (box.getUnid() == 3) {
		    			contadorPiezas += box.getCantidad();
		    			contadorPiezasCliente =  box.getCantidad();
		    		}
		    			    	
		    		precio += box.getPrecioUnitario() * box.getCantidad();
		    		precioCliente += box.getPrecioUnitario() * box.getCantidad();
		   	    }
		    	%>
		    	</table>		    						    				    			   
			    	
			    <% 
			    	
			    	// se imprimen los elementos restantes del ultimo dato. 			    	
			    	
			    	%>		
			    	
			    	<b><font color=black size=2 face=verdana>Tienes <%= contadorKGCliente %> kilogramo(s) en total en este cliente.</font></b>
		    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezasCliente %> pieza(s) en total en el cliente.</font></b>
		    		<br><b><font color=black size=2 face=verdana>Cobraste <%= Utilerias.getCurrency().format(precioCliente) %> pesos.</font></b>
		    				   
		    		<br/><br/>		    				    		
		    			    		
		    		<b><font color=black size=2 face=verdana>Totales respecto a esta consulta.</font></b>
		    		
		    		<br/>
		    		
			    	<b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
		    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>
		    		<br><b><font color=black size=2 face=verdana>Cobraste en total <%= Utilerias.getCurrency().format(precio) %> pesos.</font></b>
		    		
		    		