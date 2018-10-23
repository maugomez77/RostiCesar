<%@ include file="../admin/Secure.jsp" %>

<% 		
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control","no-store");  		
  		response.setDateHeader("Expires", 0);  		
  		
%>

<%@ page import="mx.com.rosti.to.Producto" %>
<%@ page import="mx.com.rosti.to.Proveedor" %>
<%@ page import="mx.com.rosti.to.Unidades" %>
<%@ page import="mx.com.rosti.to.TipoProducto" %>
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>

<jsp:useBean id = "pro"
			     scope = "page"
			     class = "mx.com.rosti.to.Producto" />

<jsp:useBean id = "prov"
			     scope = "page"
			     class = "mx.com.rosti.to.Proveedor" />

<jsp:useBean id = "uni"
			     scope = "page"
			     class = "mx.com.rosti.to.Unidades" />

<jsp:useBean id = "tPro"
			     scope = "page"
			     class = "mx.com.rosti.to.TipoProducto" />

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />

  
<% if (request.getParameter("sessionid")!=null) {

		if (session.getId().equals(request.getParameter("sessionid"))) {//person is logged in, so let him/her in.
				
			String readonly = "";
			String result = "";
			
			String id_producto = "";
            String nombre = "";
            String id_tipo_producto = "";
            String id_unidad = "";
            String id_proveedor = "";
            String costo = "";
            String precio1 = "";
            String precio2 = "";
            String precio3 = "";
            String precio4 = "";
            String precio5 = "";
            
			boolean act = false;
						    		
    		    		    		    		
            if ( request.getParameter( "Modificar" ) != null ) {
             
            	id_producto = request.getParameter( "id_producto" );
            	id_proveedor = request.getParameter( "id_proveedor" );
                if (id_producto == null) {
                	id_producto = "";
                }
                if (id_proveedor == null) {
                	id_proveedor = "";
                }
                if (id_producto.equals("") || id_proveedor.equals("")) { %>
                    <script>alert("Necesitas llenar el campo de clave de producto y proveedor");</script>
                <% } else {             
             	     readonly = "readonly";     		             
		             act = true;
		             pro = (Producto) rostiAdmin.getData(id_producto + "|" + id_proveedor, "Producto");
		             nombre = pro.getNombre();
		             id_tipo_producto = Utilerias.intToStr(pro.getIdTipoProducto());
		             id_unidad = Utilerias.intToStr(pro.getIdUnidad());
		             id_proveedor = Utilerias.intToStr(pro.getIdProveedor());
		             costo = Utilerias.doubleToStr(pro.getCosto());
		             precio1 = Utilerias.doubleToStr(pro.getPrecio1());
		             precio2 = Utilerias.doubleToStr(pro.getPrecio2());
		             precio3 = Utilerias.doubleToStr(pro.getPrecio3());
		             precio4 = Utilerias.doubleToStr(pro.getPrecio4());
		             precio5 = Utilerias.doubleToStr(pro.getPrecio5());
	             }
             } else if ( request.getParameter( "Actualizar" ) != null ) {
               
            	 
            	readonly = "readonly";
                result = "";
                id_producto = request.getParameter("id_producto");
                nombre = request.getParameter("nombre");
                id_tipo_producto = request.getParameter("id_tipo_producto");
                id_unidad = request.getParameter("id_unidad");               
                id_proveedor = request.getParameter("id_proveedor");
                costo = request.getParameter("costo");
                precio1 = request.getParameter("precio1");
                precio2 = request.getParameter("precio2");
                precio3 = request.getParameter("precio3");
                precio4 = request.getParameter("precio4");
                precio5 = request.getParameter("precio5");
                            	
    			 if (id_producto == null || id_producto.equals(""))
                 	result += "- Clave del Producto -";	
                 	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Producto -";	
                 
                 if (id_tipo_producto == null || id_tipo_producto.equals(""))
                  	result += "- Tipo de producto -";	
                  
                 if (id_unidad == null || id_unidad.equals(""))
                  	result += "- Unidad del Producto -";	
                  
                                  
                 if (id_proveedor == null || id_proveedor.equals(""))
                  	result += "- Clave del proveedor -";	
                 
                 if (costo == null || costo.equals("")) 
                    result += "- Costo del producto -";
                 
                 if (precio1 == null || precio1.equals("")) 
                    result += "- Precio 1 del producto -";
                 
                 if (precio2 == null || precio2.equals("")) 
                    result += "- Precio 2 del producto -";
                    
                 if (precio3 == null || precio3.equals("")) 
                    result += "- Precio 3 del producto -";
                 
                 if (precio4 == null || precio4.equals("")) 
                    result += "- Precio 4 del producto -";
                 
                 if (precio5 == null || precio5.equals("")) 
                    result += "- Precio 5 del producto -";
                    
                 if (result.equals("")) {
                	pro.setIdProducto(id_producto);
                	pro.setIdProveedor(Utilerias.strToInt(id_proveedor));
                	pro.setNombre(nombre);
                	pro.setIdTipoProducto(Utilerias.strToInt(id_tipo_producto));
                	pro.setIdUnidad(Utilerias.strToInt(id_unidad));
                	pro.setCosto(Utilerias.strToDouble(costo));
                	pro.setPrecio1(Utilerias.strToDouble(precio1));
                	pro.setPrecio2(Utilerias.strToDouble(precio2));
                	pro.setPrecio3(Utilerias.strToDouble(precio3));
                	pro.setPrecio4(Utilerias.strToDouble(precio4));
                	pro.setPrecio5(Utilerias.strToDouble(precio5));
                	
                	int opera = rostiAdmin.dmlOperations(pro, "update");
                	if ( opera > 0 ) {
                 		id_producto = nombre = id_tipo_producto = id_unidad = id_proveedor = costo = precio1 = precio2 = precio3 = precio4 = precio5 = "";
                 		readonly = "";
                 		result = "Producto actualizado satisfactoriamente";
                 	} else {
                 		result = "Problemas al actualizar.";
                 	}
                 	                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 	act = true;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>
       
         <% } else if ( request.getParameter( "Agregar" ) != null ) {
      
      			result = "";
                id_producto = request.getParameter("id_producto");
                id_proveedor = request.getParameter("id_proveedor");
      			if (rostiAdmin.checkPrevious(id_producto + "|" + id_proveedor, "Producto")) {
            		result += "Este id de producto ya esta registrado con este proveedor";	
            	}
            	
            	nombre = request.getParameter("nombre");
            	id_tipo_producto = request.getParameter("id_tipo_producto");
            	id_unidad = request.getParameter("id_unidad");
                costo = request.getParameter("costo");
                precio1 = request.getParameter("precio1");
                precio2 = request.getParameter("precio2");
                precio3 = request.getParameter("precio3");
                precio4 = request.getParameter("precio4");
                precio5 = request.getParameter("precio5");
            	
            	if (id_producto == null || id_producto.equals(""))
                 	result += "- Clave del Producto -";	
                            	
            	if (id_proveedor == null || id_proveedor.equals(""))
                 	result += "- Clave del Proveedor -";	
                 
            	
                 if (nombre == null || nombre.equals(""))
                 	result += "- Nombre del Producto -";	
                 
                 if (id_tipo_producto == null || id_tipo_producto.equals(""))
                 	result += "- Tipo del producto -";	
                 
                 if (id_unidad == null || id_unidad.equals(""))
                  	result += "- Unidad del producto -";	
                 
                 if (costo == null || costo.equals("")) 
                    result += "- Costo del producto -";
                 
                 if (precio1 == null || precio1.equals("")) 
                    result += "- Precio 1 del producto -";
                 
                 if (precio2 == null || precio2.equals("")) 
                    result += "- Precio 2 del producto -";
                    
                 if (precio3 == null || precio3.equals("")) 
                    result += "- Precio 3 del producto -";
                 
                 if (precio4 == null || precio4.equals("")) 
                    result += "- Precio 4 del producto -";
                 
                 if (precio5 == null || precio5.equals("")) 
                    result += "- Precio 5 del producto -";
                  
                 if (result.equals("")) {
                	pro.setIdProducto(id_producto);                	
                	pro.setIdProveedor(Utilerias.strToInt(id_proveedor));
                	pro.setNombre(nombre);
                	pro.setIdTipoProducto(Utilerias.strToInt(id_tipo_producto));
                	pro.setIdUnidad(Utilerias.strToInt(id_unidad));
                	pro.setPiezas(0);
                	
                	pro.setCosto(Utilerias.strToDouble(costo));
                	pro.setPrecio1(Utilerias.strToDouble(precio1));
                	pro.setPrecio2(Utilerias.strToDouble(precio2));
                	pro.setPrecio3(Utilerias.strToDouble(precio3));
                	pro.setPrecio4(Utilerias.strToDouble(precio4));
                	pro.setPrecio5(Utilerias.strToDouble(precio5));
                	
                	int opera = rostiAdmin.dmlOperations(pro, "insert");
                	if (opera > 0) {
                 		id_producto = id_proveedor = nombre = id_tipo_producto = id_unidad = costo = precio1 = precio2 = precio3 = precio4 = precio5 = "";
                 		result = "Producto actualizado satisfactoriamente";
                 		readonly = "";
                 	} else {
                 		result = "Problemas al insertar";
                 	}
                 	
                 	
                 } else {
                 	result = "Falta lo siguiente: " + result;
                 } %>
                                  
                 <script>alert("<%= result %>");</script>


      
          <% } else if ( request.getParameter("Eliminar") != null ) {
  		
  			   id_proveedor = request.getParameter( "id_proveedor" );
  			   id_producto = request.getParameter( "id_producto" );
			   
  			   pro.setIdProveedor(Utilerias.strToInt(id_proveedor));
  			   pro.setIdProducto(id_producto);
  			   
               int opera = rostiAdmin.dmlOperations(pro, "delete");
               if (opera > 0) {
    		  		result = "Registro eliminado satisfactoriamente";
    		  		id_proveedor = "";
    		   } else {               
               		result = "Problemas al eliminar el registro";
               }
                   
            %> <script>alert("<%= result %>");</script>
          <% } %>
    		    			
    		<%@ include file="../admin/Header.jsp" %>
            <%@ include file="../admin/Body.jsp" %>                                    
        
		    <div id="contentbox">
		    <p><h2>Administraci&oacute;n de Producto</h2></p>
		    
		    <form action="modproductos.jsp?sessionid=<%= session.getId() %>" method="post" name="form">
		    <p>Clave de Producto:
		    	<input type="text" name="id_producto" value="<%= id_producto %>" <%= readonly %>>
		    </p>
		    <p>Nombre :
		    	<input type="text" name="nombre" value="<%= nombre %>">
		    </p>

			<script type="text/javascript">
				function checkNumber(num) {
					var checking = false;
    				try { 
    					checking = parseFloat(num).toString() == num;
    				} catch (err) {
    				 	checking = false;
    				}    				
    				return checking;
				}
								
				function calculaCosto() {
					var costo = parseFloat("0");
					if (form.elements["costo"].value == undefined
						|| form.elements["costo"].value == null						 
						|| !checkNumber(form.elements["costo"].value)) {
						
						form.elements["costo"].value = costo;
						form.elements["precio1"].value = costo;
						form.elements["precio2"].value = costo;
						form.elements["precio3"].value = costo;
						form.elements["precio4"].value = costo;
						form.elements["precio5"].value = costo;
						
						alert("El costo tiene que ser numerico y decimal");
						 
					} else {
						costo = parseFloat(form.elements["costo"].value);
					} 
					
					form.elements["precio1"].value = costo + 0.5;
					form.elements["precio2"].value = costo + 1;
					form.elements["precio3"].value = costo + 2;
					form.elements["precio4"].value = costo + 3;
					form.elements["precio5"].value = costo + 4;
										
				}
			</script>

		    <p>Costo :
		    	<input type="text" name="costo" value="<%= costo %>" onchange="javascript:calculaCosto();">
		    </p>
		    
		    <p>Precio 1 :
		    	<input type="text" name="precio1" value="<%= precio1 %>">
		    </p>
		    
		    <p>Precio 2 :
		    	<input type="text" name="precio2" value="<%= precio2 %>">
		    </p>

		    <p>Precio 3 :
		    	<input type="text" name="precio3" value="<%= precio3 %>">
		    </p>

		    <p>Precio 4 :
		    	<input type="text" name="precio4" value="<%= precio4 %>">
		    </p>

		    <p>Precio 5 :
		    	<input type="text" name="precio5" value="<%= precio5 %>">
		    </p>

		    
		    <p>Tipo de Producto :
		       <% if (readonly.equals("readonly")) { %>
		            <input type="text" name="id_tipo_producto" value="<%= id_tipo_producto %>"  <%= readonly %>>
		       <% } else { %>
		    	<select name="id_tipo_producto" >
  					<% List lista = rostiAdmin.getListInfo("TipoProducto");
  					   for (int i=0; i < lista.size(); i++) { 
  					      tPro  = (TipoProducto) lista.get(i);
  					%>
      					<option value="<%= tPro.getIdTipoProducto() %>" <% if (id_tipo_producto.equals(Utilerias.intToStr(tPro.getIdTipoProducto()))) out.println("selected"); %>><%= tPro.getNombre() %></option>
			   <% } %>
    			</select> 
    			<% } %>			
		    </p>
		    
		    <p>Unidades :
		        <% if (readonly.equals("readonly")) { %>
		            <input type="text" name="id_unidad" value="<%= id_unidad %>"  <%= readonly %>>
		        <% } else { %>    
		    	<select name="id_unidad" >
  					<% List lista = rostiAdmin.getListInfo("Unidades");
  					   for (int i=0; i < lista.size(); i++) { 
  					      uni  = (Unidades) lista.get(i);
  					%>
      					<option value="<%= uni.getIdUnidad() %>" <% if (id_unidad.equals(Utilerias.intToStr(uni.getIdUnidad()))) out.println("selected"); %>><%= uni.getNombre() %></option>
			   <% } %>
    			</select> 	
    			<% } %>		
		    </p>
		    
  			
  			<p>Proveedor :
  			    <% if (readonly.equals("readonly")) { %>
  			         <input type="text" name="id_proveedor" value="<%= id_proveedor %>"  <%= readonly %>>
  			    <% } else { %>
		    	<select name="id_proveedor" >
  					<% List lista = rostiAdmin.getListInfo("Proveedores");
  					   for (int i=0; i < lista.size(); i++) { 
  					      prov  = (Proveedor) lista.get(i);
  					%>
      					<option value="<%= prov.getIdProveedor() %>" <% if (id_proveedor.equals(Utilerias.intToStr(prov.getIdProveedor()))) out.println("selected"); %>><%= prov.getNombre() %></option>
			   <% } %>
    			</select> 
    			<% } %>			
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