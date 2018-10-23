  <html>  
  <head>
  	<script type="text/javascript" src="../js/datetimepicker.js"></script>
  	<script type="text/javascript" src="../js/ComentarioContador.js"></script>
  	<script type="text/javascript" src="../js/ComentarioContador.js"></script>
  	<script type="text/javascript" src="../js/Hint.js"></script>
  	<script type="text/javascript" src="../js/ajaxCommonLibraries.js"></script>
  	<script type="text/javascript" src="../js/ajaxInventario.js"></script>
  	<script type="text/javascript" src="../js/ajaxVentas.js"></script>
  	<title>..:: Avicola Santa Cruz ::..</title>  	
  </head>
  <link rel="stylesheet" href="../css/fade2blog.css" />
  <body>  

  <div id="bigbox">

    <div id="head">
  		<p align="center"><strong>.:: Avicola Santa Cruz ::. &nbsp;&nbsp;&nbsp;</strong><% if (session.getAttribute("name") != null) out.println(session.getAttribute("name").toString()); %></p>
  	</div>  	  	   	  	
  	
  	<p align="center">
  		
  			<font color="black" size="-2" face="verdana">Rosti Soft v1.2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-- Hoy <%= new java.util.Date().toString() %> -- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  		
  			<% if (session.getAttribute("uname") != null && session.getAttribute("passwd") != null)  { %>
  				<a href="../start/Welcome.jsp?home=yes&sessionid=<%= session.getId() %>&uname=<%= session.getAttribute("uname").toString() %>&passwd=<%= session.getAttribute("passwd").toString() %>">home</a> <a href="../start/Logout.jsp?sessionid=<%= session.getId() %>">logout</a>
  			<% } else { %>  				  				
  				Bienvenido
  			<% } %>
  			</font>
  	</p>
  	