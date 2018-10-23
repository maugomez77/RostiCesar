<%@page import="java.util.Date"%>
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="mx.com.rosti.to.ProductoAcumulado"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if (request.getParameter("salida") == null && request.getParameter("reporte") == null) {  %>
	<form action="../pages/reportes.jsp?sessionid=<%= session.getId() %>" method="post">
	    <input type="hidden" name="salida" value="Y">
		<input type="submit" value="Ver Salidas">
	</form>					
<% } %>
<% 
  // Variables Globales 
  
String headerTableEntrada = ""
     + "    <tr bgcolor=silver>"
     + "        <td>Clave</td>"
     + "        <td>Cantidad</td>"
     + "        <td>Cajas</td>"
     + "        <td>Descripcion</td>"
     + "        <td>Unidad</td>"
     + "        <td>Prov</td>"
     + "        <td>Inventario</td>"
     + "    </tr>";

String headerTableSalida = ""
         + "    <tr bgcolor=silver>"
         + "        <td>Clave</td>"
         + "        <td>Cantidad</td>"
         + "        <td>Cajas</td>"
         + "        <td>Descripcion</td>"
         + "        <td>Unidad</td>"
         + "        <td>Prov</td>"         
         + "    </tr>";

double contadorKG = 0;
double contadorPiezas = 0;
int contadorCajas = 0;
List lista = null;
	    
%>

<% if (request.getParameter("salida") == null) { %>

	<p><h4>Existencia al D&iacute;a</h4></p>
	<% out.println("<b>Usuario: " + session.getAttribute("name")  + "</b><br/>"); %>
	<% 
	        	
		out.println("<br/>Fecha: " + Utilerias.getDate(Utilerias.FORMAT_DATE)); 
		out.println("<br/>Hora: " + Utilerias.getTime(Utilerias.FORMAT_HOUR)); %>
    			<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
	    			    <%
	    			    
	    			    
	    			       
	    			    out.println(headerTableEntrada);   
	    			    	    			
	    			    contadorKG = 0;
	    			    contadorPiezas = 0;
	    			    contadorCajas = 0;
	    			    lista = rostiAdmin.getListInfo("ListaProductosListaAcumulado", "");
	    			    for (int i=0; i < lista.size(); i++) {
	    			    	ProductoAcumulado box = (ProductoAcumulado) lista.get(i);
	    			    	
	    			    	out.println("<tr>");
	    			    	out.println("    <td>" + box.getIdProd() + "</td>");
	    			    	out.println("    <td>" + Utilerias.getDecimalFormat().format(box.getCantidad()) + "</td>");
	    			    	int cajasActuales = rostiAdmin.calculaCajas(box.getIdProd(), box.getProv(), "Entrada");
	    			    	contadorCajas += cajasActuales;
	    			    	out.println("    <td>" + cajasActuales + "</td>");							
	    			    	out.println("    <td>" + box.getDescProd() + "</td>");
	    			    	out.println("    <td>" + box.getUnidadProdDesc() + "</td>");
	    			    	out.println("    <td>" + box.getDescProv() + "</td>"); %>
	    			    	
	    			        <form action="checarInventarioPorCodigo.jsp?sessionid=<%= session.getId() %>" method="post">
	    			        	<td>
		    			    		<input type="hidden" name="producto" value="<%= box.getIdProd() %>">	
									<input type="hidden" name="proveedor" value="<%= box.getProv() %>">
							    	<input type="submit" name="inventario_individual" value="Inventario">							
								</td>    
							</form>
	    			    	
	    			    	<%
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
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorCajas %> caja(s) en total.</font></b>

<% } %>


<% if (request.getParameter("salida") != null) { %>

	    		<p><h4>Salidas en el Rango de 15 Días atras</h4></p>
    			<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
	    			    <%
	    			    
	    			    out.println(headerTableSalida);
	    			    contadorKG = 0;
	    			    contadorPiezas = 0;
	    			    
	    			    lista = rostiAdmin.getListInfo("ListaProductosListaSalidaAcumulado", "");
	    			    for (int i=0; i < lista.size(); i++) {
	    			    	ProductoAcumulado box = (ProductoAcumulado) lista.get(i);
	    			    	
	    			    	out.println("<tr>");
	    			    	out.println("    <td>" + box.getIdProd() + "</td>");
	    			    	out.println("    <td>" + Utilerias.getDecimalFormat().format(box.getCantidad()) + "</td>");
	    			    	int cajasActuales = rostiAdmin.calculaCajas(box.getIdProd(), box.getProv(), "Salida");
	    			    	contadorCajas += cajasActuales;
	    			    	out.println("    <td>" + cajasActuales + "</td>");							
	    			    	out.println("    <td>" + box.getDescProd() + "</td>");
	    			    	out.println("    <td>" + box.getUnidadProdDesc() + "</td>");
	    			    	out.println("    <td>" + box.getDescProv() + "</td>");
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
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= Utilerias.getDecimalFormat().format(contadorKG) %> kilogramo(s) en total.</font></b>
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= Utilerias.getDecimalFormat().format(contadorPiezas) %> pieza(s) en total.</font></b>
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= Utilerias.getDecimalFormat().format(contadorCajas) %> caja(s) en total.</font></b>
	    		
<% } %>
	    					    				