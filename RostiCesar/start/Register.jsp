<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="mx.com.rosti.to.Roles" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

<jsp:useBean id = "roles"
			     scope = "page"
			     class = "mx.com.rosti.to.Roles" />

<jsp:useBean id = "usr"
			     scope = "page"
			     class = "mx.com.rosti.to.Usuario" />
  
<% 
 			String readonly = ""; 
            String result = "";
            String username = "";
            String nombre = "";
            String idRol = "";
            String password = "";
            String confPassword = "";
            String correo_electronico = "";
            String telefono = "";
            boolean  act = false;

    		if ( request.getParameter ( "Agregar" ) != null ) {
            	
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
                 		result = "Usuario agregado satisfactoriamente";
                 	} else {
                 		result = "Problemas al insertar el registro";
                 	}                 	                 	                 	
                 } else {                 
                 	result = "Falta lo siguiente: " + result;
                 }
                 
                 %>                 	                 	                                                    
                                  
                 <script>alert("<%= result %>");</script>
                 
             <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            
            <div id="contentbox">
  				<p><h2>Alta de Usuarios</h2></p>
  
					<form name="form1" method="post" action="Register.jsp">
  					
  					<table width="90%" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
  						<tr>
  							<td>Clave de Usuario:</td> 
  							<td><input type="text" name="username" size="15" maxlength="20" value="<%= username %>" <%= readonly %>></td>
  						</tr>
  						<tr>
  							<td>Nombre(s): </td>
  							<td><input type="text" name="nombre" size="15" maxlength="15" value="<%= nombre %>"></td>
  						</tr>
  						<tr>
  							<td>Password: </td>
  							<td><input type="password" name="password" size="20" maxlength="30" value="<%= password %>"></td>
  						</tr>
  						<tr>
  							<td>Confirmar Password: </td>
  							<td><input type="password" name="confPassword" size="20" maxlength="30" value="<%= confPassword %>"></td>
  						</tr>
  						
  						<tr>
  							<td>Email: </td>
  							<td><input type="text" name="correo_electronico" size="30" maxlength="50" value="<%= correo_electronico %>"></td>
  						</tr>
  						<tr>
  							<td>Telefono:</td>
  							<td><input type="text" name="telefono" size="15" maxlength="20" value="<%= telefono %>"></td>
  						</tr>
  						<tr>
  							<td>Rol del Usuario: </td>
  							<td><select name="idRol">
  							    <% 
  							        List listaRoles = rostiAdmin.getListInfo("Roles");
  									for (int i=0; i < listaRoles.size(); i++) { 
  									    roles = (Roles)listaRoles.get(i);
  									%>
      									<option value="<%= roles.getIdRol() %>" <% if (idRol.equals(Utilerias.intToStr(roles.getIdRol()))) out.println("selected"); %>><%= roles.getNombreRol() %></option>
    							   <% } %>
    							</select>
  							</td>
  						</tr>
  					</table>

					<p align="center">
    					<input type="submit" name="Agregar" value="Agregar">    							
    					<input type="reset" name="Reset" value="Limpiar" <% if (act) out.println("disabled"); %>>
    					</form>
    					<form action="Login.jsp" method="post">
    						<input type="submit" name="Regresar" value="Regresar a Login">
    					</form>
    				</p>    				    				
              				
        			
    	<%= result %>
    
    </div><%@ include file="../admin/Footer.jsp" %></div></body></html>        