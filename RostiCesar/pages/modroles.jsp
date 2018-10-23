<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Roles" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>

<jsp:useBean id = "roles"
			     scope = "page"
			     class = "mx.com.rosti.to.Roles" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String id_rol = "";
            String nombre = "";
            String descripcion = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	id_rol = request.getParameter( "id_rol" );
             
                if (id_rol.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave de rol");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             roles = (Roles) rostiAdmin.getData(id_rol, "Roles");
		             nombre = roles.getNombreRol();
		             descripcion = roles.getDescripcion();
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
                readonly = "readonly";
                result = "";
                id_rol = request.getParameter("id_rol");
                nombre = request.getParameter("nombre");
                descripcion = request.getParameter("descripcion");
            	            	
    			 if (id_rol == null || id_rol.equals(""))
                 	result += "- Clave del Rol -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Rol -";	
                 
                 if (descripcion == null || descripcion.equals(""))
                  	result += "- Descripcion del Rol -";	
                  
                 if (result.equals("")) {
                	roles.setIdRol(Utilerias.strToInt(id_rol));
                	roles.setNombreRol(nombre);
                	roles.setDescripcion(descripcion);
                	
                	int opera = rostiAdmin.dmlOperations(roles, "update");
                	if ( opera > 0 ) {
                 		id_rol = nombre = descripcion = "";
                 	}
                 	result = "Rol actualizado satisfactoriamente";
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                id_rol = request.getParameter("id_rol");
      			if (rostiAdmin.checkPrevious(id_rol, "Rol")) {
            		result += "Este id de rol ya esta registrado";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	descripcion = request.getParameter("descripcion");
            	
            	
            	if (id_rol == null || id_rol.equals(""))
                 	result += "- Clave del Rol -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Rol -";	
                 
                 if (descripcion == null || descripcion.equals(""))
                  	result += "- Descripcion del Rol -";	
                  
                 if (result.equals("")) {
                	roles.setIdRol(Utilerias.strToInt(id_rol));
                	roles.setNombreRol(nombre);
                	roles.setDescripcion(descripcion);
                	
                	int opera = rostiAdmin.dmlOperations(roles, "insert");
                	if (opera > 0) {
                 		id_rol = nombre = descripcion = "";
                 		readonly = "";
                 		result = "Rol actualizado satisfactoriamente";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   id_rol = request.getParameter( "id_rol" );
  			   roles.setIdRol(Utilerias.strToInt(id_rol));
               int opera = rostiAdmin.dmlOperations(roles, "delete");
               if (opera > 0) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		id_rol = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Roles</h2></p>
		    
		    <form action="modroles.jsp?sessionid=<%= session.getId() %>" method="post">
		    <p>Clave de Rol:
		    	<input type="text" name="id_rol" value="<%= id_rol %>" <%= readonly %>>
		    </p>
		    <p>Nombre :
		    	<input type="text" name="nombre" value="<%= nombre %>">
		    </p>
		    
		    <p>Descripcion :
		    	<input type="text" name="descripcion" value="<%= descripcion %>">
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