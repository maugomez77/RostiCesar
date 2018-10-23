  	<div id="nav">
  	    <font face="Verdana" size="2">
  		<ul>
  		<% 
    
         // ****************************************** 
         // Admin Menu 
         // Supervisor
         if ( session.getAttribute("tipo").toString().equals( "1" ) || 
        	  session.getAttribute("tipo").toString().equals( "2" ) ) { %>
    		
        	<li><a href="../pages/usuarios.jsp?sessionid=<%= session.getId() %>">Usuarios</a></li>
        	<li><a href="../pages/proveedores.jsp?sessionid=<%= session.getId() %>">Proveedores</a></li>
        	<li><a href="../pages/crearEtiqueta.jsp?sessionid=<%= session.getId() %>">Crear Etiqueta</a></li>
        	<li><a href="../pages/inventario.jsp?sessionid=<%= session.getId() %>">Compras</a></li>
        	<li><a href="../pages/ventas.jsp?sessionid=<%= session.getId() %>">Ventas</a></li>
        	<li><a href="../pages/clientes.jsp?sessionid=<%= session.getId() %>">Clientes</a></li>
        	<li><a href="../pages/productos.jsp?sessionid=<%= session.getId() %>">Productos</a></li>        	
        	<li><a href="../pages/roles.jsp?sessionid=<%= session.getId() %>">Roles</a></li>
        	<% if (session.getAttribute("tipo").toString().equals("1")) { %>
        	<li><a href="../pages/tipoproducto.jsp?sessionid=<%= session.getId() %>">Categorias</a></li>
        	<li><a href="../pages/unidades.jsp?sessionid=<%= session.getId() %>">Unidades</a></li>
        	<% } %>
        	<li><a href="../pages/reportes.jsp?sessionid=<%= session.getId() %>">Inventario</a></li>
        	<li><a href="../pages/ES.jsp?sessionid=<%= session.getId() %>">Diario General</a></li>  
        	<li><a href="../pages/ESDia.jsp?sessionid=<%= session.getId() %>">EYS Hoy</a></li>
        	<li><a href="../pages/ESDiaUnificado.jsp?sessionid=<%= session.getId() %>">EYS (Unif) Hoy</a></li>
  			<li><a href="../pages/ESDiaGeneralUnificado.jsp?sessionid=<%= session.getId() %>">EYS (Unif) GENERAL</a></li>        	      
        	<li><a href="../pages/ESDiaFecha.jsp?sessionid=<%= session.getId() %>">EYS Por Fecha</a></li>
        	<li><a href="../pages/clienteDiaFecha.jsp?sessionid=<%= session.getId() %>">Acumulados</a></li>
        	<li><a href="../pages/clienteTotalDiaFecha.jsp?sessionid=<%= session.getId() %>">Acumulados Total</a></li>
        	<li><a href="../pages/entradaPorDia.jsp?sessionid=<%= session.getId() %>">Compra Diaria</a></li>
        	<li><a href="../pages/salidaPorDia.jsp?sessionid=<%= session.getId() %>">Venta Diaria</a></li>
        	<li><a href="../pages/remisiones.jsp?sessionid=<%= session.getId() %>">Remisiones</a></li>
         <% } %>         		
        </ul>
        </font>
    </div>       