
<%@ page import="mx.com.rosti.util.Utilerias" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.*" %>
<%@ page import="java.io.*" %>
<%@ page import="mx.com.rosti.to.ProductoAcumulado"%>
<%@ page import="mx.com.rosti.validations.NumbersValidation"%>

<jsp:useBean id = "rostiAdmin"
			     scope = "page"
			     class = "mx.com.rosti.processor.RostiAD" />
<%
if (request.getParameter("entradaSalida") != null) {
    String eSalida = (String) request.getParameter("entradaSalida"); 
    
    if (!eSalida.equals("")) { 

	List<String> datosCliente = rostiAdmin.tomarClienteEntrada(eSalida);
	
	out.println("<b>Usuario: " + session.getAttribute("name")  + "</b><br/>");
	out.println("<b>Fecha de entrada: " + datosCliente.get(0) + "</b><br/>");
	out.println("<b>Hora de entrada: " + datosCliente.get(1) + "</b>");
	

	%>
    <table border="1" style="border-bottom-style : dashed; border-left-style : dashed; border-right-style : dashed; border-top-style : dashed; font-size : 12px;">
    <%
    out.println("    <tr bgcolor=silver>");
    out.println("        <td>Clave</td>");
    out.println("        <td>Cantidad</td>");	    			    
    out.println("        <td>Descripcion</td>");
    out.println("        <td>Tipo Producto</td>");
    out.println("        <td>Unidad</td>");
    out.println("        <td>Cajas</td>");
    out.println("    </tr>");
    	    			
    double contadorKG = 0;
    double contadorPiezas = 0;
    
    
    List lista = rostiAdmin.getListInfo("ListaProductosLista", eSalida);       
    
    for (int i=0; i < lista.size(); i++) {
    	
    	ProductoAcumulado box = (ProductoAcumulado) lista.get(i);
    	
    	out.println("<tr>");
    	out.println("    <td>" + box.getIdProd() + "</td>");
    	out.println("    <td>" + box.getCantidad() + "</td>");	    			    	
    	out.println("    <td>" + box.getDescProd() + "</td>");
    	out.println("    <td>" + box.getTipoProdDesc() + "</td>");
    	out.println("    <td>" + box.getUnidadProdDesc() + "</td>");
    	int cajasActuales = rostiAdmin.calculaCajasEntrada(box.getIdProd(), box.getProv(), "Entrada", eSalida);
    	out.println("    <td>" + cajasActuales + "</td>");
    	
    	out.println("</tr>");
    	
    	if (box.getUnidadProd() == 1 || box.getUnidadProd() == 2) {
    		contadorKG += box.getCantidad();
    	} 
    	
    	if (box.getUnidadProd() == 3) {
    		contadorPiezas = box.getCantidad();
    	}
    }
    
	out.println("</table>"); %>
		    				
	<br><b><font color=black size=2 face=verdana>Tienes <%= contadorKG %> kilogramo(s) en total.</font></b>
	<br><b><font color=black size=2 face=verdana>Tienes <%= contadorPiezas %> pieza(s) en total.</font></b>
	
<% } 
    
}%> 	
    	 	    			        