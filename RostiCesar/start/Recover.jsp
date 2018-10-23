<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

<%@ page import="mx.com.rosti.util.Utilerias" %>


<%@ include file="../admin/Header.jsp" %>
	
	<div id="nav">
		<ul>
			<li><a onclick="alert('Abriras una nueva pagina no perteneciente a este sitio'); return true;" href="http://www.tol.itesm.mx" target="new">itesm-tol</a></li>
			<li><a onclick="alert('Abriras una nueva pagina no perteneciente a este sitio'); return true;" href="http://mail.itesm.mx" target="new">webmail</a></li>
			<li><a onclick="alert('Abriras una nueva pagina no perteneciente a este sitio'); return true;" href="http://mitec.itesm.mx" target="new">portal tec</a></li>												
			<li><a onclick="alert('Abriras una nueva pagina no perteneciente a este sitio'); return true;" href="http://www.google.com.mx" target="new">google</a></li>
			<li><a onclick="alert('Abriras una nueva pagina no perteneciente a este sitio'); return true;" href="http://isc.tol.itesm.mx" target="new">carrera isc</a></li>			
		</ul>
	</div>
	
	<div id="contentbox">
    	<p><h2>Acceso al Sistema Rosti</h2></p><br>
    	
    	
    	<% if (rostiAdmin.connectDB().contains("Problems")) { %>
		
			<p>No puedes acceder debido a problemas tecnicos: El servidor de base de datos no esta corriendo</p>

		<% } else { 
				
		       if (request.getParameter("Enviar") != null) {
				
						String uname = request.getParameter("uname");
						
						String result = "";
						
						if (uname.equals("") || uname == null)
							result = "Tienes que poner tu usuario";	
						
						if (result.equals("")) {
							
		    			    if (!rostiAdmin.checkPrevious(uname, "Usuario")) {
		    					
		    				 	 result += "-- Este usuario no existe en la Base de Datos --";
		    			    } 
		    			   
		    			    //si no hubo problemas
		    			    if (result.equals("")) {
		                   		
		    			    	if (rostiAdmin.sendEmail(uname)) { 
		    			    		result += "Tu cuenta ha sido enviada a tu correo electronico";
		    			    	} else {
		    			    		result += "No se pudo enviar tu password a tu cuenta de correo electronico";
								}                   		
		                    }														
						} 
						
						%> <script>alert("<%= result %>");</script>
						
				   <%  } %>
		
						<FORM action="Recover.jsp" method="post">
				    		<table align="center">
				  				<tbody>
				    				<tr>
				      					<td><font size="2" face="verdana">Id de Usuario:</font></td>
				      					<td>
				      						<INPUT type="text" name="uname" size="15" maxlength="20">
				      						<b><font size="2" face="verdana">
												<a href="#" class="hintanchor" onMouseover="showhint('Introduce tu id usuario para poder enviarte tu password', this, event, '150px')">[?]</a>
											</font></b>
				      					</td>
				    				</tr>
				    				<tr>
				      					<td><INPUT type="submit" name = "Enviar" value="Enviar"></td>
				      					</form>	      					
				      					<form action="Login.jsp" method="post">
				      					<td><input type="submit" name="" value="Regresar Login"></td>
				      					</form>	      					
				    				</tr>
				  				</tbody>
							</table>					
				</div>
			</div>

		<% } %>
		
<%@ include file="../admin/Footer.jsp" %>

</body>
</html>