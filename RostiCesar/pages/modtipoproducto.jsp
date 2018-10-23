<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.TipoProducto" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>

<jsp:useBean id = "tPro"
			     scope = "page"
			     class = "mx.com.rosti.to.TipoProducto" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String id_tipo_producto = "";
            String nombre = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	id_tipo_producto = request.getParameter( "id_tipo_producto" );
             
                if (id_tipo_producto.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave de tipo de producto");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             tPro = (TipoProducto) rostiAdmin.getData(id_tipo_producto, "TipoProducto");
		             nombre = tPro.getNombre();
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
                readonly = "readonly";
                result = "";
                id_tipo_producto = request.getParameter("id_tipo_producto");
                nombre = request.getParameter("nombre");
            	            	
    			 if (id_tipo_producto == null || id_tipo_producto.equals(""))
                 	result += "- Clave del Tipo Producto -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Rol -";	
                                   
                 if (result.equals("")) {
                	tPro.setIdTipoProducto(Utilerias.strToInt(id_tipo_producto));
                	tPro.setNombre(nombre);
                	
                	int opera = rostiAdmin.dmlOperations(tPro, "update");
                	if ( opera > 0 ) {
                 		id_tipo_producto = nombre = "";
                 	}
                 	result = "Tipo Producto actualizado satisfactoriamente";
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                id_tipo_producto = request.getParameter("id_tipo_producto");
      			if (rostiAdmin.checkPrevious(id_tipo_producto, "TipoProducto")) {
            		result += "Este id de rol ya esta registrado";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	
            	if (id_tipo_producto == null || id_tipo_producto.equals(""))
                 	result += "- Clave del Tipo Producto -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Tipo Producto -";	
                                   
                 if (result.equals("")) {
                	tPro.setIdTipoProducto(Utilerias.strToInt(id_tipo_producto));
                	tPro.setNombre(nombre);
                	
                	int opera = rostiAdmin.dmlOperations(tPro, "insert");
                	if (opera > 0) {
                 		id_tipo_producto = nombre = "";
                 		readonly = "";
                 		result = "Tipo Producto actualizado satisfactoriamente";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   id_tipo_producto = request.getParameter( "id_tipo_producto" );
  			   tPro.setIdTipoProducto(Utilerias.strToInt(id_tipo_producto));
               int opera = rostiAdmin.dmlOperations(tPro, "delete");
               if (opera > 0) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		id_tipo_producto = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Tipo de Producto</h2></p>
		    
		    <form action="modtipoproducto.jsp?sessionid=<%= session.getId() %>" method="post">
		    <p>Clave de Tipo Producto:
		    	<input type="text" name="id_tipo_producto" value="<%= id_tipo_producto %>" <%= readonly %>>
		    </p>
		    <p>Nombre :
		    	<input type="text" name="nombre" value="<%= nombre %>">
		    </p>
		    
		    <p align="center">
		    		<input type="submit" name="Agregar" value="Agregar" <% if (act) out.println("disabled"); %>>		    		
		    		<% if (!act) {  %>
		    				<input type="submit" name="Modificar" value="Modificar">
		    		<% } else { %>
		    				<input type="submit" name="Actualizar" value="Actualizar">
		    		<% } %>		    		
		    		<input type="submit" name="Eliminar" value="Eliminar" <% if (act) out.println("disabled"); %>>    		    		
			</p>        
			</form>	        	        
		    
		    <%= result %>
		    
		    </div></div><%@ include file="../admin/Footer.jsp" %></body></html>
        	
	  	<% } else { //session has expired.
		
				response.sendRedirect("../start/sessionexpired.jsp");
		}
			
	} else {//then trying to access page without logging in.

		session.invalidate();
		response.sendRedirect("../index.jsp");
	}  %>