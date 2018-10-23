
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.to.ReporteInventario"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     		
		<p><h4>Entradas y Salidas Diarias de Hoy Unificado </h4></p>
    			<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
	    			    <%
	    			    
	    			    String headerTable = ""
	    			        + "        <td>SKU</td>"
	    			        + "        <td>Clave</td>"
	    			        + "        <td>Cantidad</td>"
	    			        + "        <td>Descripcion</td>"
	    			        + "        <td>Unidad</td>"	    			        
	    			        + "        <td>Prov</td>"
	    			        + "        <td>Fecha</td>";
	    			        
	    			    out.println("    <tr bgcolor=silver>");
	    			    out.println(headerTable);
	    			    out.println("    </tr>");
	    			    	    			
	    			    double contadorKG = 0;
	    			    double contadorPiezas = 0;
	    			    
	    			    List lista = rostiAdmin.getListInfo("ReporteInventarioUnificadoEntradaSalida", "");
	    			    for (int i=0; i < lista.size(); i++) {
	    			    	ReporteInventario box = (ReporteInventario) lista.get(i);
	    			    	
	    			    	out.println("<tr>");
	    			    	out.println("    <td>" + box.getIdInventario() + "</td>");
	    			    	out.println("    <td>" + box.getCodeProd() + "</td>");
	    			    	out.println("    <td>" + box.getCantidad() + "</td>");	    			    	
	    			    	out.println("    <td>" + box.getDescCodeProducto() + "</td>");
	    			    	out.println("    <td>" + box.getDescUnid() + "</td>");	    			    	
	    			    	out.println("    <td>" + box.getDescProveedor() + "</td>");
	    			    	out.println("    <td>" + Utilerias.getDate(box.getFechaEntrada(), "yyyy-MM-dd") + "</td>");
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
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
	    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>