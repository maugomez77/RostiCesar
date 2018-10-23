<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Unidades" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>

<jsp:useBean id = "uni"
			     scope = "page"
			     class = "mx.com.rosti.to.Unidades" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String id_unidad = "";
            String nombre = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	id_unidad = request.getParameter( "id_unidad" );
             
                if (id_unidad.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave de unidad");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             uni = (Unidades) rostiAdmin.getData(id_unidad, "Unidades");
		             nombre = uni.getNombre();
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
                readonly = "readonly";
                result = "";
                id_unidad = request.getParameter("id_unidad");
                nombre = request.getParameter("nombre");
            	            	
    			 if (id_unidad == null || id_unidad.equals(""))
                 	result += "- Clave del Unidad -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del la Unidad -";	
                 
                 if (result.equals("")) {
                	uni.setIdUnidad(Utilerias.strToInt(id_unidad));
                	uni.setNombre(nombre);
                	
                	int opera = rostiAdmin.dmlOperations(uni, "update");
                	if ( opera > 0 ) {
                 		id_unidad = nombre = "";
                 	}
                 	result = "Rol actualizado satisfactoriamente";
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                id_unidad = request.getParameter("id_unidad");
      			if (rostiAdmin.checkPrevious(id_unidad, "Unidad")) {
            		result += "Este id de unidad ya esta registrado";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	
            	
            	if (id_unidad == null || id_unidad.equals(""))
                 	result += "- Clave del Unidad -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Unidad -";	
                 
                  
                 if (result.equals("")) {
                	uni.setIdUnidad(Utilerias.strToInt(id_unidad));
                	uni.setNombre(nombre);
                	
                	int opera = rostiAdmin.dmlOperations(uni, "insert");
                	if (opera > 0) {
                 		id_unidad = nombre = "";
                 		readonly = "";
                 		result = "Unidad actualizada satisfactoriamente";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   id_unidad = request.getParameter( "id_unidad" );
  			   uni.setIdUnidad(Utilerias.strToInt(id_unidad));
               int opera = rostiAdmin.dmlOperations(uni, "delete");
               if (opera > 0) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		id_unidad = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Unidades</h2></p>
		    
		    <form action="modunidades.jsp?sessionid=<%= session.getId() %>" method="post">
		    <p>Clave de Unidad:
		    	<input type="text" name="id_unidad" value="<%= id_unidad %>" <%= readonly %>>
		    </p>
		    <p>Nombre de la Unidad:
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