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
    			<p><h2>Inventario por Codigo</h2></p>    			
    			
					<table border="1"style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    					<tr bgcolor="silver">
    						<td>SKU</td>
    						<td>Clave de Producto</td>    						
    						<td>Nombre</td>
    						<td>Cantidad</td>
    						<td>Proveedor</td>
    					</tr>
                        <% 
                        
                           String producto = (String) request.getParameter("producto");
                           String proveedor = (String) request.getParameter("proveedor");
                        
                           List<List> datosInventarioPorCodigoList = rostiAdmin.tomarInventarioPorCodigo(producto, proveedor);
                           
                           for (int i=0; i < datosInventarioPorCodigoList.size(); i++) { 
                        	   List<String> datosInventarioPorCodigo =  datosInventarioPorCodigoList.get(i);           
                        %>
        				<tr>
        				    <td><%= datosInventarioPorCodigo.get(0) %></td>
        				    <td><%= datosInventarioPorCodigo.get(1) %></td>
      						<td><%= datosInventarioPorCodigo.get(2) %></td>
      						<td><%= datosInventarioPorCodigo.get(3) %></td>
      						<td><%= datosInventarioPorCodigo.get(4) %></td>      						      						      						  
      					</tr>
    					<% } %>
    				</table>
    			</center>    			    		
    			
    		</div>
    		
    		</div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
  		<% } else { //session has expired.
	
			response.sendRedirect("../start/sessionexpired.jsp");
		}
			
} else {//then trying to access page without logging in.

	session.invalidate();
	response.sendRedirect("../index.jsp");
	
}  %>