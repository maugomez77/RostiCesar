<%@ include file="../admin/Secure.jsp" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="mx.com.rosti.to.ReporteInventario"%>
<jsp:useBean id = "rostiAdmin"
			 scope = "page"
			 class = "mx.com.rosti.processor.RostiAD" />
			     
<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in. %>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h4>Remisiones pertenecientes al cliente por artículo</h4></p>
				<% out.println("<b>Cliente: " + rostiAdmin.getData(request.getParameter("cliente"), "Cliente") + "</b>"); %>
				<form action="/RostiCesar/ActualizaPrecios?sessionid=<%= session.getId() %>&parameters=<%= request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente")%>" method="post">
				<input type="hidden" name="action" value="devolucion">
	    		<table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
		    			    <%
		    			    
		    			    String headerTable = ""
		    			        + "        <td>Remision</td>"
		    			        + "        <td>Clave</td>"
		    			        + "        <td>Cantidad</td>"
		    			        + "        <td>Descripcion</td>"
		    			        + "        <td>Unidad</td>"		    			        
		    			        + "        <td>Proveedor</td>"
		    			        + "        <td>Precio</td>"
		    			        + "        <td>Total</td>"
		    			        + "        <td>Fecha</td>"
		    			        + "        <td>Devolucion</td>";
			    			    
		    			    out.println("    <tr bgcolor=silver>");
		    			    out.println(headerTable);
		    			    out.println("    </tr>");
		    			    	    			
		    			    double contadorKG = 0;
		    			    double contadorPiezas = 0;
		    			    List lista = null;
							lista = rostiAdmin.getListInfo("ReporteRemisionFechasItem", request.getParameter("start") + "|" + request.getParameter("end") + "|" + request.getParameter("cliente"));
		    			    		    			     
		    			    String remision = "";
		    			    for (int i=0; i < lista.size(); i++) {
	                            
		    			    	ReporteInventario box = (ReporteInventario) lista.get(i);
		    			    	out.println("<tr>");
		    			    	out.println("    <td>" + box.getRemision() + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getCodeProd() + "</td>");
		    			    	out.println("    <td>" + box.getCantidad() + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getDescCodeProducto() + "</td>");
		    			    	out.println("    <td>" + box.getDescUnid() + "</td>");
		    			    	out.println("    <td>" + box.getDescProveedor() + "</td>");		    			    	
		    			    	out.println("    <td>" + box.getPrecioUnitario() + "</td>");
		    			    	out.println("    <td>" + box.getPrecioUnitario() * box.getCantidad() + "</td>");		    			    	
		    			    	out.println("    <td>" + Utilerias.getDate(box.getFechaSalida(), "yyyy-MM-dd") + "</td>");
		    			        %>
		    			        <td>
			    			        <select name="devolucion<%= box.getRemision() + "|" + box.getIdInventario() + "|" + box.getCodeProd() + "|" + box.getProv() %>">
			    			        	<option value="Y">Devolver
			    			        	<option value="N" selected="yes">No Devolver
			    			        </select>
			    			    </td> 
		    			        
		    			        <% out.println("</tr>");
		    			    	
		    			    	
		    			    	if (box.getUnid() == 1 || box.getUnid() == 2) {
		    			    		contadorKG += box.getCantidad();
		    			    	} 
		    			    	
		    			    	if (box.getUnid() == 3) {
		    			    		contadorPiezas += box.getCantidad();
		    			    	}
		    			    }
		    			    %>
		    		</table>			
		    		<br/>
		    		<input type="submit" value="Aplicar Devoluciones">
		    		</form>
		    		
		    		<% if (request.getParameter("contadorDev") != null) { %>
		    			<br><b><font color=black size=2 face=verdana>Devoluciones aplicadas: <%= request.getParameter("contadorDev") %></font></b>
		    		<% } %>
		    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
		    		<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>
		    		    			
    		</div>
    		
    		</div>
    	<%@ include file="../admin/Footer.jsp" %>
    </body>
</html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>