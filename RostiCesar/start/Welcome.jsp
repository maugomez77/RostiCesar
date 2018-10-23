<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Usuario" %>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

<jsp:useBean id = "userData"
			     scope = "page"
			     class = "mx.com.rosti.to.Usuario" />

<%                     
          String uname = request.getParameter( "uname" );
          String passwd = request.getParameter( "passwd" );
                   
          if (session.getAttribute("uname") != null && request.getParameter("home") == null) { %>
          
            <%@ include file="../admin/Header.jsp" %>
            <div id="contentbox">
    
          	<% response.setHeader("Refresh", "3; URL=Login.jsp"); %>
			
			<p>Solo puedes tener una sesion abierta por Cliente<br>
			   Las conexiones que tenias abiertas se cerraran, espera para redireccionarte. . .</p>			
			<p>Puedes tener a dos usuarios conectados siempre y cuando sean navegadores diferentes: <br>Ejemplo: Firefox y Explorer</p>
			<p>Pero es preferible tener solo un usuario conectado por cliente</p>
			
			<% session.invalidate(); %>						
			
          	
          <% } else if ( rostiAdmin.validUserPassword(uname, passwd ) ) {     
                
                userData = (Usuario) rostiAdmin.getData(uname, "Usuario");  
        	  
            	session.setAttribute("uname", userData.getIdUsuario());
            	session.setAttribute("passwd", userData.getPassword());
            	session.setAttribute("name", userData.getNombre());
            	session.setAttribute("tipo", userData.getIdRol());
            	//falta cerrar un div 
            %>
            
            <%@ include file="../admin/Header.jsp"  %>
            <%@ include file="../admin/Body.jsp"  %>
                        
        	<div id="contentbox">
        
        		<h1>Sistema de Inventarios</h1>
            	<h2>Bienvenido(a) <%= userData.getNombre() %></h2>
            	<p align="justify"><font face="verdana" size=2>El sistema te permite administrar lo siguiente: </p>
            		<li>Inventarios</li>
            		<li>Usuarios</li>
            		<li>Proveedores</li>
            		<li>Reportes</li>            	
            		</font>
            	<p align="justify">Comentarios favor de hacerlos llegar al siguiente
            		correo: <a href="mailto:mauricio.gomez.77@gmail.com">Mauricio Gomez</a></p>
            		
            	<%   %>		
            
        <% } else { %>
          	
          	<%@ include file="../admin/Header.jsp" %>
          	<div id="contentbox">
          	<h2>Acceso Denegado debido a contraseña o usuario incorrecto, intente de nuevo</h2>
          	<a href="../start/Login.jsp">Regresar</a>
          	
        <% } %>
        
  			</div>   		
  		</div>
  	
  	<%@ include file="../admin/Footer.jsp" %>
  	
  	</body></html>