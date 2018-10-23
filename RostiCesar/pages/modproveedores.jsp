<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Proveedor" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>

<jsp:useBean id = "pro"
			     scope = "page"
			     class = "mx.com.rosti.to.Proveedor" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String id_proveedor = "";
            String nombre = "";
            String descripcion = "";
            String generaCodigo = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	id_proveedor = request.getParameter( "id_proveedor" );
             
                if (id_proveedor.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave de proveedor");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             pro = (Proveedor) rostiAdmin.getData(id_proveedor, "Proveedor");
		             nombre = pro.getNombre();
		             descripcion = pro.getDescripcion();
		             generaCodigo = pro.getGeneraCodigo();
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
                readonly = "readonly";
                result = "";
                id_proveedor = request.getParameter("id_proveedor");
                nombre = request.getParameter("nombre");
            	descripcion = request.getParameter("descripcion");
            	generaCodigo = request.getParameter("genera_codigo"); 
            	
    			 if (id_proveedor == null || id_proveedor.equals(""))
                 	result += "- Clave del Proveedor -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Proveedor -";	
                 
                 if (descripcion == null || descripcion.equals(""))
                 	result += "- Descripcion del proveedor -";	
                 
                 if (result.equals("")) {
                	pro.setIdProveedor(Utilerias.strToInt(id_proveedor));
                	pro.setNombre(nombre);
                	pro.setDescripcion(descripcion);
                	pro.setGeneraCodigo(generaCodigo);
                 	
                	int opera = rostiAdmin.dmlOperations(pro, "update");
                	if ( opera > 0 ) {
                 		id_proveedor = nombre = descripcion = "";
                 	}
                 	result = "Proveedor actualizado satisfactoriamente";
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                id_proveedor = request.getParameter("id_proveedor");
      			if (rostiAdmin.checkPrevious(id_proveedor, "Proveedor")) {
            		result += "Este id de proveedor ya esta registrado";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	descripcion = request.getParameter("descripcion");
            	
            	if (id_proveedor == null || id_proveedor.equals(""))
                 	result += "- Clave del Proveedor -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Proveedor -";	
                 
                 if (descripcion == null || descripcion.equals(""))
                 	result += "- Descripcion del proveedor -";	
                 
                 if (result.equals("")) {
                	pro.setIdProveedor(Utilerias.strToInt(id_proveedor));
                	pro.setNombre(nombre);
                	pro.setDescripcion(descripcion);
                 	pro.setGeneraCodigo("N");
                	int opera = rostiAdmin.dmlOperations(pro, "insert");
                	if (opera > 0) {
                 		id_proveedor = nombre = descripcion = "";
                 		readonly = "";
                 		result = "Usuario actualizado satisfactoriamente";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   id_proveedor = request.getParameter( "id_proveedor" );
  			   pro.setIdProveedor(Utilerias.strToInt(id_proveedor));
               int opera = rostiAdmin.dmlOperations(pro, "delete");
               if (opera > 0) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		id_proveedor = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Proveedores</h2></p>
		    
		    <form action="modproveedores.jsp?sessionid=<%= session.getId() %>" method="post">
		    <p>Clave de Proveedor:
		    	<input type="text" name="id_proveedor" value="<%= id_proveedor %>" <%= readonly %>>
		    </p>
		    <p>Nombre :
		    	<input type="text" name="nombre" value="<%= nombre %>">
		    </p>
		    
		    <p>Descripcion del Proveedor: 
  				<input type="text" name="descripcion" size="20" maxlength="30" value="<%= descripcion %>">
  			</p>	
  			    											
  			    <input type="hidden" name="genera_codigo" size="1" maxlength="1" value="<%= generaCodigo %>">
  			    											
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