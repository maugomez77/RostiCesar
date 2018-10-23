<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Proveedor" %>
<%@ page import="mx.com.rosti.to.Producto" %>
<%@ page import="mx.com.rosti.to.ProductoAcumulado" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="mx.com.rosti.servlets.InfoEtiquetasPDF"%>

<jsp:useBean id = "prov"
			     scope = "page"
			     class = "mx.com.rosti.to.Proveedor" />

<jsp:useBean id = "producto"
			     scope = "page"
			     class = "mx.com.rosti.to.Producto" />

<jsp:useBean id = "productoAcum"
			     scope = "page"
			     class = "mx.com.rosti.to.ProductoAcumulado" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
			     

<% if(request.getParameter("sessionid")!=null){

		if(session.getId().equals(request.getParameter("sessionid"))){//person is logged in, so let him/her in.
			
			String idProv = "";
    		%>
  
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>
                    
          	<div id="contentbox">
    			<p><h2>Creaci&oacute;n de Etiquetas</h2></p>    			
    			    <% if (request.getParameter("action") == null) { %>
    			    
    			    	<form action="/RostiCesar/crear?sessionid=<%= session.getId() %>" method="post">    	    			    
    	    			<p>Proveedor:
    			    		<input type="hidden" name="action" value="Prov">
    	    					<select name="idProv">
			  						<% 
		                               List listaProveedores = rostiAdmin.getListInfo("Proveedores");
		  					           for (int i=0; i < listaProveedores.size(); i++) { 
		  						           prov = (Proveedor) listaProveedores.get(i);  						           
		  						           if (prov.getGeneraCodigo().equals("Y")) { %>
		        						   <option value="<%= prov.getIdProveedor() %>" <% if (idProv.equals(Utilerias.intToStr(prov.getIdProveedor()))) out.println("selected"); %>><%= prov.getNombre() %></option>
			    					   <%  
			    					       }
		  						       }   						       
		  						       %>
			    				</select>
		    				<input type="submit" value="Enviar">
		    			</p>
		    			</form>
		    				
		    			<form action="/RostiCesar/crear?sessionid=<%= session.getId() %>" method="post">    	    			    
    	    				<input type="hidden" name="action" value="ProdProv">
    	    				<p>Producto Proveedor: 
			  					<select name="idProdProv">
			  						<% 
		                               listaProveedores = rostiAdmin.getListInfo("ProductoProveedores", "");
		  					           for (int i=0; i < listaProveedores.size(); i++) { 
		  					        	 productoAcum = (ProductoAcumulado) listaProveedores.get(i);
		  						           %>
		        						      <option value="<%= productoAcum.getIdProd() + "|" + productoAcum.getProv()%>"><%= productoAcum.getDescProd() + " " + productoAcum.getDescProv() + " " + productoAcum.getTipoProdDesc() + " " + productoAcum.getUnidadProdDesc() %></option>
			    					       <%
		  						       }   						       
		  						       %>
			    				</select>
		    				<input type="submit" value="Enviar">
		    				</p>		    				
		    				</form>
		    			
		    						    	    						    			
		    		<% } else if (request.getParameter("action") != null &&
		    			          request.getParameter("action").equals("Proveedor")) { %>
		    			 <form name="forma" action="/RostiCesar/crear?sessionid=<%= session.getId() %>" method="post">		    			    
    	    				<input type="hidden" name="action" value="CrearEtiqueta">    	    		
    	    				<input type="hidden" name="idProv" value="<%= request.getParameter("idProv") %>">
    	    				<input type="hidden" name="DescProv" value="<%= request.getParameter("DescProv") %>">
		    			 	<p>
		    			 	Proveedor: <%= request.getParameter("idProv") + " " + request.getParameter("DescProv")%><br>
		    			       Productos: 
		    			       <select name="idProducto">
			  						<% 
		                               List lista = rostiAdmin.getListInfo("ListaProductosPorProveedor", request.getParameter("idProv"));
		  					           for (int i=0; i < lista.size(); i++) { 
		  						           producto = (Producto) lista.get(i); %>
		  						           
		  						           <option value="<%= producto.getIdProducto() + "|" + producto.getIdProveedor() %>" <% if ((producto.getIdProducto() + Utilerias.intToStr(producto.getIdProveedor())).equals(request.getParameter("selectedProductoProveedor"))) out.println("selected=true"); %>><%= producto.getNombre() + " -- " + producto.getTipoProducto() + " -- " + producto.getUnidades()%></option>
			    					   <%  
			    					   }
		  						       %>
			    				</select><br>	
			    			Cantidad: <input type="text" name="cantidad" value=""> <br>		    			    
		    			 	<input type="submit" value="Añadir Etiqueta">
		    			 	</p>
		    			 	
		    			 	<script type="text/javascript">
		    			 		var textBox = forma.cantidad;
                				textBox.value = '';
                				textBox.focus();                
		    			 	</script>
    	    			</form>
    	    			
    	    			<% } if (session.getAttribute("infoPDF") != null && ((ArrayList) session.getAttribute("infoPDF")).size() > 0) {
    	    			    ArrayList infoPDF = (ArrayList) session.getAttribute("infoPDF");
    	    			    %> 
    	    			    	    				
    	    			    <table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    	    			    <%
    	    			    out.println("    <tr bgcolor=silver>");
    	    			    out.println("        <td>Codigo de Barras</td>");
    	    			    out.println("        <td>Proveedor</td>");
    	    			    out.println("        <td>Clave</td>");
    	    			    out.println("        <td>Cantidad</td>");
    	    			    out.println("        <td>Descripcion</td>");
    	    			    out.println("        <td>Unidades</td>");    	    			    
    	    			    out.println("        <td>Accion</td>");
    	    			    out.println("    </tr>");
    	    			    
    	    			    
    	    			    for (int i=0; i < infoPDF.size(); i++) {
    	    			    	InfoEtiquetasPDF info = (InfoEtiquetasPDF) infoPDF.get(i);
    	    			    	
    	    			    	out.println("<tr>");
    	    			    	out.println("    <td>" + info.getCodBarras() + "</td>");    	    			    	
    	    			    	out.println("    <td>" + info.getProv() + "</td>");
    	    			    	out.println("    <td>" + info.getCodProd() + "</td>");
    	    			    	out.println("    <td>" + info.getEnteros() + "." + info.getDecimales() + "</td>");    	    			    	
    	    			    	out.println("    <td>" + info.getDescProducto() + "</td>");
    	    			    	out.println("    <td>" + info.getUnidad() + "</td>");
    	    			    	out.println("    <td>"); %>
    	    			    	
					    	    			    	<form action="/RostiCesar/crear?sessionid=<%= session.getId() %>" method="post">
					    						        <input type="hidden" name="action" value="eliminate">
					    							    <input type="hidden" name="elemento" value="<%= i %>">
					    							    <input type="hidden" name="idProv" value="<%= request.getParameter("idProv") %>">
	    	    										<input type="hidden" name="DescProv" value="<%= request.getParameter("DescProv") %>">			    			 	
					    							    <input type="submit"  value="Eliminar Caja">
					    							</form>
    						        			</td>
    					    			   </tr>
    	    			    <% } %> 
    	    			    
    	    			    </table>
    	    			    
	    	    			<form action="/RostiCesar/crear?sessionid=<%= session.getId() %>" method="post">
	    	    				<input type="hidden" name="action" value="CrearEtiquetaMultiple">    	    		
	    	    			    <input type="hidden" name="idProv" value="<%= request.getParameter("idProv") %>">
	    	    				<input type="hidden" name="DescProv" value="<%= request.getParameter("DescProv") %>">
			    			 	<input type="submit" value="Generar Documento PDF">
			    			</form> 	    	    			
		    			<% } %>   			    			
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