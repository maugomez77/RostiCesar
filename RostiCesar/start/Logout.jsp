<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<% if(!session.getId().equals(request.getParameter("sessionid"))){//then trying to log out w/o having logged in.

		session.invalidate();
		response.sendRedirect("../index.jsp");
		
	} else {//person is logged in, so let person log out.
		
		session.removeAttribute("user");
		session.removeAttribute("passwd");
		session.removeAttribute("tipo");
		session.removeAttribute("name");
		
		session.invalidate();				
		
		response.setContentType("text/html"); 		
		response.sendRedirect("Login.jsp");
		
		%>

		<HTML>
		<BODY BGCOLOR=lightgreen>
		<BR>
		<CENTER>
		<FONT SIZE=5>Te has deslogeado exitosamente.</FONT><BR><BR>
		<a href="index.jsp">Log In</a>
		</CENTER>
		</BODY>
		</HTML>
		
	<% } %>