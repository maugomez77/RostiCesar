<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Usuario" %>
<%@ page import="mx.com.rosti.to.Roles" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "usr"
			     scope = "page"
			     class = "mx.com.rosti.to.Usuario" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

<jsp:useBean id = "roles"
			     scope = "page"
			     class = "mx.com.rosti.to.Roles" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String username = "";
            String nombre = "";
            String idRol = "";
            String password = "";
            String confPassword = "";
            String correo_electronico = "";
            String telefono = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	username = request.getParameter( "username" );
             
                if (username.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave usuario");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             usr = (Usuario) rostiAdmin.getData(username, "Usuario");
		             nombre = usr.getNombre();
		             idRol = Utilerias.intToStr(usr.getIdRol());
		             password = usr.getPassword();
		             confPassword = usr.getPassword();
		             correo_electronico = usr.getCorreoElectronico();
		             telefono = usr.getTelefono();
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
                readonly = "readonly";
                result = "";
                username = request.getParameter("username");
                nombre = request.getParameter("nombre");
            	idRol = request.getParameter("idRol");
            	password = request.getParameter("password");
            	confPassword = request.getParameter("confPassword");
            	correo_electronico = request.getParameter("correo_electronico");
            	telefono = request.getParameter("telefono");
            	
            	if (!password.equals(confPassword)) {
            		result += "- Los password no coinciden, favor de verificar -";
            	}
            	
    			 if (username == null || username.equals(""))
                 	result += "- Clave del Usuario -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Usuario -";	
                 
                 if (idRol == null || idRol.equals(""))
                 	result += "- El rol no es correcto -";	
                 
                 if (password == null || password.equals(""))
                 	result += "- Contraseña no introducida-";	
                 
                 if (correo_electronico == null || correo_electronico.equals(""))
                 	result += "- Correo eletrònico no introducido -";	
                 
                 if (telefono == null || telefono.equals(""))
                 	result += "- Telefono no introducido -";	
                 	

                 if (result.equals("")) {
                	usr.setIdUsuario(username);
                	usr.setNombre(nombre);
                	usr.setIdRol(Utilerias.strToInt(idRol));
                	usr.setPassword(password);
                	usr.setCorreoElectronico(correo_electronico);
                	usr.setTelefono(telefono);
                	 
                 	int opera = rostiAdmin.dmlOperations(usr, "update");
                	if (opera > 0) {
                 		username = nombre = idRol = password = confPassword = correo_electronico = telefono = "";
                 		result = "Usuario actualizado satisfactoriamente";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                username = request.getParameter("username");
      			
                if (rostiAdmin.checkPrevious(username, "Usuario")) {
            		result += "Este id de usuario ya esta registrado";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	idRol = request.getParameter("idRol");
            	password = request.getParameter("password");
            	confPassword = request.getParameter("confPassword");
            	correo_electronico = request.getParameter("correo_electronico");
            	telefono = request.getParameter("telefono");
            	
            	if (!password.equals(confPassword)) {
            		result += "- Los password no coinciden, favor de verificar -";
            	}
            	
    			 if (username == null || username.equals(""))
                 	result += "- Clave del Usuario -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Usuario -";	
                 
                 if (idRol == null || idRol.equals(""))
                 	result += "- El rol no es correcto -";	
                 
                 if (password == null || password.equals(""))
                 	result += "- Contraseña no introducida-";	
                 
                 if (correo_electronico == null || correo_electronico.equals(""))
                 	result += "- Correo eletrònico no introducido -";	
                 
                 if (telefono == null || telefono.equals(""))
                 	result += "- Telefono no introducido -";	
                 	

                 if (result.equals("")) {
                	usr.setIdUsuario(username);
                 	usr.setNombre(nombre);
                 	usr.setIdRol(Utilerias.strToInt(idRol));
                 	usr.setPassword(password);
                 	usr.setCorreoElectronico(correo_electronico);
                 	usr.setTelefono(telefono);
                	 
                 	int opera = rostiAdmin.dmlOperations(usr, "insert");
                	if (opera > 0) {
                 		username = nombre = idRol = password = confPassword = correo_electronico = telefono = "";
                 		readonly = "";
                 		result = "Usuario insertado satisfactoriamente";	
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   username = request.getParameter( "username" );
               usr.setIdUsuario(username);
  			   
               int opera = rostiAdmin.dmlOperations(usr, "delete");
               if (opera > 0 ) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		username = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Usuarios</h2></p>
		    
		    <form action="modusuarios.jsp?sessionid=<%= session.getId() %>" method="post">
		    <p>Clave de Usuario:
		    	<input type="text" name="username" value="<%= username %>" <%= readonly %>>
		    </p>
		    <p>Nombre (s):
		    	<input type="text" name="nombre" value="<%= nombre %>">
		    </p>
		    
		    <p>Password: 
  				<input type="password" name="password" size="20" maxlength="30" value="<%= password %>">
  			</p>	
  			
  			<p>Confirmar Password: 
  				<input type="password" name="confPassword" size="20" maxlength="30" value="<%= confPassword %>">
  			</p>		
  			
  			<p>Email:
  				<input type="text" name="correo_electronico" size="30" maxlength="50" value="<%= correo_electronico %>">
  			</p>
  				
  			<p>Telefono:
  				<input type="text" name="telefono" size="15" maxlength="20" value="<%= telefono %>">
  			</p>
  			
  			<p>Rol del Usuario: 
  				<select name="idRol">
  					<% List listaRoles = rostiAdmin.getListInfo("Roles");
  					   for (int i=0; i < listaRoles.size(); i++) { 
  					       roles = (Roles) listaRoles.get(i);
  					%>
      					<option value="<%= roles.getIdRol() %>" <% if (idRol.equals(Utilerias.intToStr(roles.getIdRol()))) out.println("selected"); %>><%= roles.getNombreRol() %></option>
    				   <% } %>
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