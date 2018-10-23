<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Clientes" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>

<jsp:useBean id = "cli"
			     scope = "page"
			     class = "mx.com.rosti.to.Clientes" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String id_cliente = "";
            String nombre = "";
            String precio_preferente = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	id_cliente = request.getParameter( "id_cliente" );
             
                if (id_cliente.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave de proveedor");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             cli = (Clientes) rostiAdmin.getData(id_cliente, "Cliente");
		             nombre = cli.getNombre();
		             precio_preferente = Utilerias.intToStr(cli.getPrecioPreferente());		             
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
                readonly = "readonly";
                result = "";
                id_cliente = request.getParameter("id_cliente");
                nombre = request.getParameter("nombre");
            	precio_preferente = request.getParameter("precio_preferente");
            	            	
    			 if (id_cliente == null || id_cliente.equals(""))
                 	result += "- Clave del Cliente -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Cliente -";	
                 
                 if (precio_preferente == null || precio_preferente.equals(""))
                 	result += "- Precio Preferente -";	
                 
                 if (result.equals("")) {
                	cli.setIdCliente(Utilerias.strToInt(id_cliente));
                	cli.setNombre(nombre);
                	cli.setPrecioPreferente(Utilerias.strToInt(precio_preferente));
                	int opera = rostiAdmin.dmlOperations(cli, "update");
                	if ( opera > 0 ) {
                 		id_cliente = nombre = "";
                 	}
                 	result = "Cliente actualizado satisfactoriamente";
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                id_cliente = request.getParameter("id_cliente");                
      			
      			if (id_cliente == null || id_cliente.equals(""))
                 	result += "- Clave del Cliente -";	
                
      			if (result.equals("") && rostiAdmin.checkPrevious(id_cliente, "Cliente")) {
            		result += "Este id de cliente ya esta registrado";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	precio_preferente = request.getParameter("precio_preferente");
            	
            	 	
                if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Cliente -";	
                 
                 if (precio_preferente == null || precio_preferente.equals(""))
                 	result += "- Precio Preferente -";	
                 
                 if (result.equals("")) {
                	cli.setIdCliente(Utilerias.strToInt(id_cliente));
                	cli.setNombre(nombre);
                	cli.setPrecioPreferente(Utilerias.strToInt(precio_preferente));                	
                	int opera = rostiAdmin.dmlOperations(cli, "insert");
                	if (opera > 0) {
                 		id_cliente = nombre = precio_preferente = "";
                 		result = "Cliente actualizado satisfactoriamente";
                 		readonly = "";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   id_cliente = request.getParameter( "id_cliente" );
  			   cli.setIdCliente(Utilerias.strToInt(id_cliente));
               int opera = rostiAdmin.dmlOperations(cli, "delete");
               if (opera > 0) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		id_cliente = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Clientes</h2></p>
		    
		    <form action="modclientes.jsp?sessionid=<%= session.getId() %>" method="post">
		    <p>Clave de Cliente:
		    	<input type="text" name="id_cliente" value="<%= id_cliente %>" <%= readonly %>>
		    </p>
		    <p>Nombre :
		    	<input type="text" name="nombre" value="<%= nombre %>">
		    </p>
		    <p>Precio Preferente :
		    	<select name="precio_preferente">
		    		<option value="0" <% if (request.getParameter("precio_preferente") != null && request.getParameter("precio_preferente").equals("0")) out.println("selected"); %>>Costo
		    		<option value="1" <% if (request.getParameter("precio_preferente") != null && request.getParameter("precio_preferente").equals("1")) out.println("selected"); %>>Precio 1
		    		<option value="2" <% if (request.getParameter("precio_preferente") != null && request.getParameter("precio_preferente").equals("2")) out.println("selected"); %>>Precio 2
		    		<option value="3" <% if (request.getParameter("precio_preferente") != null && request.getParameter("precio_preferente").equals("3")) out.println("selected"); %>>Precio 3
		    		<option value="4" <% if (request.getParameter("precio_preferente") != null && request.getParameter("precio_preferente").equals("4")) out.println("selected"); %>>Precio 4
		    		<option value="5" <% if (request.getParameter("precio_preferente") != null && request.getParameter("precio_preferente").equals("5")) out.println("selected"); %>>Precio 5
		    	</select>
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