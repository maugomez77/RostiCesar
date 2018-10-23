
<%@ page import="mx.com.rosti.util.Utilerias" %>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

<% if (session.getAttribute("uname") != null) { 
	    response.sendRedirect("Logout.jsp");
} %>

<%@ include file="../admin/Header.jsp" %>
	
	<div id="nav">
		<ul>
			<li><a onclick="alert('Abriras una nueva pagina no perteneciente a este sitio'); return true;" href="http://www.google.com" target="new">Google</a></li>
		</ul>
	</div>
	
	<div id="contentbox">
    	<p align="left"><h2>Acceso al Rosti Cesar </h2></p><br>
    	
    	
    	<% if (rostiAdmin.connectDB().contains("Problems")) { %>
		
			<p>No puedes acceder debido a problemas tecnicos: El servidor de base de datos no esta corriendo</p>

		<% } else if (!session.isNew()) {   //recuerda 	que solo puedes tener una sesion abierta 
		%>
			<% response.setHeader("Refresh", "3; URL=Login.jsp"); %>
			
			<p>Solo puedes tener una sesion abierta por Cliente<br>
			   Las conexiones que tenias abiertas se cerraran, espera para redireccionarte. . .</p>			
			
			<p>Puedes tener a dos usuarios conectados siempre y cuando sean navegadores diferentes: <br>Ejemplo: Firefox y Explorer</p>
			<p>Pero es preferible tener solo un usuario conectado por cliente</p>
			<p><br>Si no redirecciona en 3 segundos recarga la pagina</p>
			
			<% session.invalidate(); %>						
			
		<%	
		} else { 
		
			String array = "uname,passwd";
			String appears = "User Name,Password";
			
			array = Utilerias.replaceComma(array);
			appears = Utilerias.replaceComma(appears);							
			
		%>	
			
			<script type="text/javascript" src="../js/Validator.js"></script>
		    	
			<FORM action="Welcome.jsp" method="post" onsubmit="return formCheck(this);">
	    		<table align="center">
	  				<tbody>
	    				<tr>
	      					<td><font size="2" face="verdana">Login:</font></td>
	      					<td>
	      						<INPUT type="text" name="uname" size="15" maxlength="20">
	      						<b><font size="2" face="verdana">
									<a href="#" class="hintanchor" onMouseover="showhint('Introduce tu usuario', this, event, '150px')">[?]</a>
								</font></b>
	      					</td>
	      					
	    				</tr>
	    				<tr>
	      					<td><font size="2" face="verdana">Password:</font></td>
	      					<td>
	      						<INPUT type="password" name="passwd" size="15" maxlength="20">
	      						<b><font size="2" face="verdana">
									<a href="#" class="hintanchor" onMouseover="showhint('Introduce tu password.', this, event, '150px')">[?]</a>
								</font></b>
	      					</td>
	      					
	    				</tr>
	    				<tr>
	      					<td><INPUT type="submit" value="Login"></td>
	      	</FORM>
	      					
	      					<form action="Recover.jsp" method="post">
	      					<td><input type="submit" name="" value="Olvidaste Password"></td>
	      					</form>	      					
	      						      					
	    				</tr>
	  				</tbody>
				</table>
				
				<center><a href="Register.jsp"><font size="-1" face="verdana"><b>Usuario Nuevo??</b></font></a></center>
	</div>
</div> <% } %>

<%@ include file="../admin/Footer.jsp" %>

</body>
</html>