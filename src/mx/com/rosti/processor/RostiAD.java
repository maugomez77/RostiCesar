package mx.com.rosti.processor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.Session;

import mx.com.rosti.catalogo.ClientesDAOImpl;
import mx.com.rosti.catalogo.InventarioDAOImpl;
import mx.com.rosti.catalogo.ProductoAcumuladoDAOImpl;
import mx.com.rosti.catalogo.ProductoDAOImpl;
import mx.com.rosti.catalogo.ProveedorDAOImpl;
import mx.com.rosti.catalogo.RemisionDAOImpl;
import mx.com.rosti.catalogo.ReporteInventarioDAOImpl;
import mx.com.rosti.catalogo.RolesDAOImpl;
import mx.com.rosti.catalogo.TipoProductoDAOImpl;
import mx.com.rosti.catalogo.UnidadesDAOImpl;
import mx.com.rosti.catalogo.UsuarioDAOImpl;
import mx.com.rosti.catalogo.especifico.DescripcionesGeneralesDAOImpl;
import mx.com.rosti.catalogo.especifico.IntegratorDAOImpl;
import mx.com.rosti.context.ContextEmail;
import mx.com.rosti.email.MailClient;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.processor.constant.RostiADConstant;
import mx.com.rosti.servlets.ElementsGroupedXMLBox;
import mx.com.rosti.servlets.ElementsXMLBox;
import mx.com.rosti.to.Clientes;
import mx.com.rosti.to.Inventario;
import mx.com.rosti.to.Producto;
import mx.com.rosti.to.ProductoAcumulado;
import mx.com.rosti.to.Proveedor;
import mx.com.rosti.to.Roles;
import mx.com.rosti.to.TipoProducto;
import mx.com.rosti.to.Unidades;
import mx.com.rosti.to.Usuario;
import mx.com.rosti.util.Utilerias;
import mx.com.rosti.validations.NumbersValidation;
import mx.com.rosti.validations.StringValidation;

public class RostiAD implements RostiADConstant {
    
	public String connectDB() {
		String str = "";
		try {			
   		    UnidadesDAOImpl est = (UnidadesDAOImpl) 
                ObjectFactory.getObject(UNIDADES);
            est.setLocal(false);
            est.consultar();
            str = "";
		} catch (Exception e) {
			Utilerias.error(this, "Errores: " + e.toString());
			str = "Problems";
		}
		return str;
	}
		
	public boolean checkPrevious(String thing, String type) throws UnsupportedEncodingException {
		
		if (thing == null || type == null) {
			return true;
		}
		
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String valida = "";
		
		if (type.equals("Usuario")) {
			est.setSql(" select id_usuario from usuario where id_usuario = ? ");
			est.setSqlColumn("id_usuario");
			valida = est.executeQueryString(new Object[]{thing});
		}
		
		if (type.equals("Rol")) {
			est.setSql(" select id_rol from roles where id_rol = ? ");
			est.setSqlColumn("id_rol");
			valida = est.executeQueryString(new Object[]{thing});
		}
		
		if (type.equals("Proveedor")) {
			est.setSql(" select id_proveedor from proveedor where id_proveedor = ? ");
			est.setSqlColumn("id_proveedor");
			valida = est.executeQueryString(new Object[]{ Utilerias.strToInt(thing) });
		}
		
		if (type.equals("Cliente")) {
			est.setSql(" select id_cliente from clientes where id_cliente = ? ");
			est.setSqlColumn("id_cliente");
			valida = est.executeQueryString(new Object[]{ Utilerias.strToInt(thing) });
		}
		
		if (type.equals("Producto")) {
			StringTokenizer st = new StringTokenizer(thing, DELIMITADOR);
			est.setSql(" select id_producto from producto where id_producto = ? and id_proveedor = ? ");
			est.setSqlColumn("id_producto");
			valida = est.executeQueryString(new Object[]{ Utilerias.strToInt(st.nextToken()), Utilerias.strToInt(st.nextToken()) });
		}
		
		if (type.equals("TipoProducto")) {
			est.setSql(" select id_tipo_producto from tipo_producto where id_tipo_producto = ? ");
			est.setSqlColumn("id_tipo_producto");
			valida = est.executeQueryString(new Object[]{ Utilerias.strToInt(thing) });
		}
		
		
		if (type.equals("Unidad")) {
			est.setSql(" select id_unidad from unidades where id_unidad = ? ");
			est.setSqlColumn("id_unidad");
			valida = est.executeQueryString(new Object[]{ Utilerias.strToInt(thing) });
		}
		
		if (type.equals("Inventario")) {
			StringTokenizer st = new StringTokenizer(thing, DELIMITADOR);
		    est.setSql(new String((" select id_inventario from inventario where id_inventario = '" + st.nextToken() + "'" + " and id_producto = '" + st.nextToken() + "'" + " and id_proveedor = " + st.nextToken()).getBytes(), "UTF-8"));
		    Utilerias.debug(this, "query: " + est.getSql());
		    est.setSqlColumn("id_inventario");
		    valida = est.executeQueryString();
			Utilerias.debug(this, " valida : " + valida);
		}
				
		if (type.equals("Ventas")) {
			StringTokenizer st = new StringTokenizer(thing, DELIMITADOR);
		    est.setSql(new String((" select id_inventario from inventario where id_inventario = '" + st.nextToken() + "'" + " and id_producto = '" + st.nextToken() + "'" + " and id_proveedor = " + st.nextToken() + " and entrada_salida='Entrada'").getBytes(), "UTF-8"));
		    Utilerias.debug(this, "query: " + est.getSql());
		    est.setSqlColumn("id_inventario");
			valida = est.executeQueryString();
			Utilerias.debug(this, " valida : " + valida);
		}
		if (type.equals("Remision")) {
			est.setSql(" select remision from inventario where remision = ? ");
			est.setSqlColumn("remision");
			valida = est.executeQueryString(new Object[]{  thing});
		}
		return valida.equals("") ? false : true;
	}
	
	public synchronized int generateRemision() {
		
		String sql = ""
		   + " select count(*) cantidad from inventario i";
		
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		
		est.setSql(sql);
		est.setSqlColumn("cantidad");
		
		//este codigo es para crear la primer remision con valor 1
		int numberRemision = est.executeQueryInt();
		if (numberRemision <= 0) {
        	numberRemision = 1;
        } else {
        	
        	//de lo contrario crear aquella maxima mas uno
		    sql = "" 
		        + " select max(remision) + 1 remision "
		        + "     from inventario i";
		    est.setSql(sql);
		    est.setSqlColumn("remision");
		    numberRemision = est.executeQueryInt();
        }
				
 		return numberRemision;		
	}
	
	public boolean validUserPassword(String user, String password) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		
		est.setSql(" select id_usuario from usuario where id_usuario = ? and password = ? ");
		est.setSqlColumn("id_usuario");
		String valida = est.executeQueryString(new Object[]{user, password});

		return valida.equals("") ? false : true;
		
	}
	
	public Object getData(String idThing, String type) {
		
		if (type.equals("Inventario")) {
			Utilerias.debug(this, "IdThing: " + idThing);
			StringTokenizer st = new StringTokenizer(idThing, DELIMITADOR);
			InventarioDAOImpl dao = (InventarioDAOImpl) ObjectFactory.getObject(INVENTARIO);
			dao.setLocal(false);
			Inventario inv = new Inventario();
			inv.setIdInventario(st.nextToken());
			inv.setIdProducto(st.nextToken());
			inv.setIdProveedor(Utilerias.strToInt(st.nextToken()));
			inv = (Inventario) dao.consultarEspecifica(inv);
			return inv;
		}
		if (type.equals("Usuario")) {
			UsuarioDAOImpl dao = (UsuarioDAOImpl) ObjectFactory.getObject(USUARIO);
			dao.setLocal(false);
			Usuario us = new Usuario();
			us.setIdUsuario(idThing);
			us = (Usuario)dao.consultarEspecifica(us);
			return us;
		}
		
	    if (type.equals("Proveedor")) {
	    	ProveedorDAOImpl dao = (ProveedorDAOImpl) ObjectFactory.getObject(PROVEEDOR);
			dao.setLocal(false);
			Proveedor pro = new Proveedor();
			pro.setIdProveedor(Utilerias.strToInt(idThing));
			pro = (Proveedor) dao.consultarEspecifica(pro);
			return pro;
	    }
	    
	    if (type.equals("Cliente")) {
	    	ClientesDAOImpl dao = (ClientesDAOImpl) ObjectFactory.getObject(CLIENTES);
			dao.setLocal(false);
			Clientes cli = new Clientes();
			cli.setIdCliente(Utilerias.strToInt(idThing));
			cli = (Clientes) dao.consultarEspecifica(cli);
			return cli;
	    }
	    
	    if (type.equals("Producto")) {
	    	StringTokenizer st = new StringTokenizer(idThing, DELIMITADOR);
	    	
	    	ProductoDAOImpl dao = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			dao.setLocal(false);
			Producto pro = new Producto();
			pro.setIdProducto(st.nextToken());
			pro.setIdProveedor(Utilerias.strToInt(st.nextToken()));
			pro = (Producto) dao.consultarEspecifica(pro);
			return pro;
	    }
	    
	    if (type.equals("Roles")) {
	    	RolesDAOImpl dao = (RolesDAOImpl) ObjectFactory.getObject(ROLES);
			dao.setLocal(false);
			Roles rol = new Roles();
			rol.setIdRol(Utilerias.strToInt(idThing));
			rol = (Roles) dao.consultarEspecifica(rol);
			return rol;
	    }
	    
	    if (type.equals("TipoProducto")) {
	    	TipoProductoDAOImpl dao = (TipoProductoDAOImpl) ObjectFactory.getObject(TIPO_PRODUCTO);
			dao.setLocal(false);
			TipoProducto tPro = new TipoProducto();
			tPro.setIdTipoProducto(Utilerias.strToInt(idThing));
			tPro = (TipoProducto) dao.consultarEspecifica(tPro);
			return tPro;
	    }
	    
	    if (type.equals("Unidades")) {
	    	UnidadesDAOImpl dao = (UnidadesDAOImpl) ObjectFactory.getObject(UNIDADES);
			dao.setLocal(false);
			Unidades uni = new Unidades();
			uni.setIdUnidad(Utilerias.strToInt(idThing));
			uni = (Unidades) dao.consultarEspecifica(uni);
			return uni;
	    }
	    
		return null;
	}
	
	public String generarEtiqueta(String idProducto, String idProv, String idUni, String idTipo, String enteros, String decimales) {
		String storage = generateStorageUnit(idProducto, idProv, idUni, idTipo);
		Utilerias.debug(this, "Storage generado: " + storage + " " + storage.length());
		//metodo para crear el codigo
		//Primeras    5 posiciones PROVEEDOR
		//Siguientes 10 posiciones COD PRODUCTO
		//SIGUIENTES  3 posiciones KILOS enteros
		// PENDIENTE   siguiente   1 posicion   punto (.)
		//siguientes  2 posicoines kilos decimales
		//SIGUIENTES 12 POSICIONES CODIGO
		
		String etiqueta = ""
		+ StringValidation.leftPad(idProv, 5, "0")
	    + StringValidation.leftPad(idProducto, 10, "#")
	    + StringValidation.leftPad(enteros, 3, "0")
	    //+ "."
	    + StringValidation.leftPad(decimales, 2, "0")
	    + StringValidation.leftPad(storage, 12, "0");
		
		Utilerias.debug(this, "Etiqueta generado: " + etiqueta + " " + etiqueta.length());
		
		return etiqueta;
	}
	public String generarEtiquetaExcepcion(final String idProducto, String idProv) {
		return StringValidation.leftPad(idProv, 5, "0") 
		    + StringValidation.leftPad(idProducto, 15, "0");
		
	}
	public boolean sendEmail(String user) { 
		Usuario us = (Usuario) getData(user, "Usuario");
		return sendEmail(USER_ADMIN, us.getCorreoElectronico(), "Envio de Cuenta Rosticeria", 
			   "Este es tu password: " + us.getPassword() + ". Saludos. ", null);
	}
	
	public boolean sendEmail(String from, String to, String subject, String messageBody, String[] attach) { 
		boolean flag = false;
		try {
			MailClient mail = new MailClient();
			Utilerias.debug(this, "Mesae");
			Session ses = ContextEmail.getSessionEmail(false, true);
			Utilerias.debug(this, "Mesae  2222");
			mail.setSession(ses);
			mail.sendMail(from, to, subject, messageBody, attach);
		    flag = true;
		} catch (Exception e) {
			Utilerias.error(this, "Errores: " + e.toString());
			flag = false;
		}
		return flag;
	}
	
	public int dmlOperations(Object o, String operacion) {
		int result = -1;
		
		if (o instanceof ProductoAcumulado) {
			ProductoAcumuladoDAOImpl dao = (ProductoAcumuladoDAOImpl) ObjectFactory.getObject(PRODUCTO_ACUMULADO);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (ProductoAcumulado) o);
		}
		
		if (o instanceof Clientes) {
			ClientesDAOImpl dao = (ClientesDAOImpl) ObjectFactory.getObject(CLIENTES);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (Clientes) o);
		}
		
		if (o instanceof Inventario) {
			InventarioDAOImpl dao = (InventarioDAOImpl) ObjectFactory.getObject(INVENTARIO);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (Inventario) o);
		}
		
		if (o instanceof Producto) {
			ProductoDAOImpl dao = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			dao.setLocal(false);
			Producto temp = (Producto) o;
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			temp.setFechaAlta(date);
			result = dao.dmlOperations(operacion, (Producto) o);
			
			//secuencia			
			IntegratorDAOImpl daoDos = (IntegratorDAOImpl) ObjectFactory.getObject(INTEGRATOR);
			daoDos.setLocal(false);
			daoDos.insertMaxId(temp.getIdProducto(), temp.getIdProveedor(), temp.getIdUnidad(), temp.getIdTipoProducto());			
		}
		
		if (o instanceof Proveedor) {
			ProveedorDAOImpl dao = (ProveedorDAOImpl) ObjectFactory.getObject(PROVEEDOR);
			dao.setLocal(false);			
			result = dao.dmlOperations(operacion, (Proveedor) o);
		}
		
		if (o instanceof Roles) {
			RolesDAOImpl dao = (RolesDAOImpl) ObjectFactory.getObject(ROLES);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (Roles) o);
		}
		
		if (o instanceof TipoProducto) {
			TipoProductoDAOImpl dao = (TipoProductoDAOImpl) ObjectFactory.getObject(TIPO_PRODUCTO);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (TipoProducto) o);
		}
		
		if (o instanceof Unidades) {
			UnidadesDAOImpl dao = (UnidadesDAOImpl) ObjectFactory.getObject(UNIDADES);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (Unidades) o);
		}
		
		if (o instanceof Usuario)  {
			UsuarioDAOImpl dao = (UsuarioDAOImpl) ObjectFactory.getObject(USUARIO);
			dao.setLocal(false);
			result = dao.dmlOperations(operacion, (Usuario) o); 
		}
        
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getListInfo(String who) { 
		
		List listaCompleta = null;
		
		if (who.equals("Usuarios")) {
			UsuarioDAOImpl user = (UsuarioDAOImpl) ObjectFactory.getObject(USUARIO);
			user.setLocal(false);
			listaCompleta = user.consultarRolDescrip(SQL_USUARIOS_ROL_DESCRIP);
		}
		
		if (who.equals("Roles")) {
			RolesDAOImpl roles = (RolesDAOImpl)ObjectFactory.getObject(ROLES);
			roles.setLocal(false);
			listaCompleta = roles.consultar();
		}
		
		if (who.equals("Proveedores")) {
			ProveedorDAOImpl pro = (ProveedorDAOImpl) ObjectFactory.getObject(PROVEEDOR);
			pro.setLocal(false);
			listaCompleta = pro.consultar();
		}
				
		if (who.equals("Clientes")) {
			ClientesDAOImpl cli = (ClientesDAOImpl) ObjectFactory.getObject(CLIENTES);
			cli.setLocal(false);
			listaCompleta = cli.consultar();
		}
		
		if (who.equals("Productos")) {
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			listaCompleta = pro.consultarPorDesc(SQL_PRODUCTOS_LISTA);
		}
		
		if (who.equals("TipoProducto")) {
			TipoProductoDAOImpl pro = (TipoProductoDAOImpl) ObjectFactory.getObject(TIPO_PRODUCTO);
			pro.setLocal(false);
			listaCompleta = pro.consultar();
		}
				
		if (who.equals("Unidades")) {
			UnidadesDAOImpl uni = (UnidadesDAOImpl) ObjectFactory.getObject(UNIDADES);
			uni.setLocal(false);
			listaCompleta = uni.consultar();
		}
		
		return listaCompleta;
	}
	
	@SuppressWarnings("rawtypes")
	public List getListInfo(String who, String parameters) {
				
		List listaCompleta = null;
		
		if (who.equals("ProductoProveedores")) {
			ProductoDAOImpl dao = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			dao.setLocal(false);
			String sql = ""
			    + " select pro.id_producto, pro.nombre nombre_prod, pro.id_proveedor, prov.nombre nombre, pro.id_tipo_producto, tip.nombre nombre_tpro, pro.id_unidad, u.nombre nombre_uni, 0 cantidad, 0 precio_unitario, 0 costo_producto, 0 costo_inventario, 0 precio1, 0 precio2, 0 precio3, 0 precio4, 0 precio5 "
			    + "   from producto pro, proveedor prov, unidades u, tipo_producto tip "
			    + "  where pro.id_unidad <> 1 "
			    + "    and pro.id_proveedor = prov.id_proveedor "
			    + "    and prov.genera_codigo = 'N' "
			    + "    and pro.id_unidad = u.id_unidad "
			    + "    and pro.id_tipo_producto = tip.id_tipo_producto "
			    + "  order by pro.nombre";
			        
			listaCompleta = dao.consultarAcumulados(sql);
			
		}
		
		if (who.equals("RemisionNumero")) {
			RemisionDAOImpl dao = (RemisionDAOImpl) ObjectFactory.getObject(REMISION);
			dao.setLocal(false);
			String sql = "" 
			    + " select distinct i.remision remision, c.nombre nombre_cliente " 
			    + "   from clientes c, inventario i "
			    + "  where c.id_cliente = i.id_cliente "
			    + "  order by i.fecha_salida desc ";
			listaCompleta = dao.consultarNumero(sql);			
		}
		
		
		if (who.equals("remisionNumero")) {
			RemisionDAOImpl dao = (RemisionDAOImpl) ObjectFactory.getObject(REMISION);
			dao.setLocal(false);
			
			String sql = "" 
			    + " select i.remision, i.id_cliente cliente, c.nombre nombre_cliente, "
			    + "        i.id_proveedor, prov.nombre nombre_prov, i.id_producto, p.nombre nombre_producto, "
			    + "        i.fecha_entrada, i.hora_entrada, i.fecha_salida, i.hora_salida, i.id_inventario, i.cantidad, "
			    + "        i.precio_unitario precio, i.precio_unitario * i.cantidad precioTotal "
			    + "   from inventario i, clientes c, proveedor prov, producto p " 
			    + "  where c.id_cliente = i.id_cliente "
			    + "    and prov.id_proveedor = i.id_proveedor"
			    + "    and prov.id_proveedor = p.id_proveedor "
			    + "    and i.id_producto = p.id_producto "
			    + "    and i.remision = " + parameters
			    + "  order by i.fecha_salida desc ";
			
			listaCompleta = dao.consultarTodas(sql);
			
		}
		
		if (who.equals("remisionCliente")) {
			RemisionDAOImpl dao = (RemisionDAOImpl) ObjectFactory.getObject(REMISION);
			dao.setLocal(false);
			
			String sql = "" 
			    + " select i.remision, i.id_cliente cliente, c.nombre nombre_cliente, "
			    + "        i.id_proveedor, prov.nombre nombre_prov, i.id_producto, p.nombre nombre_producto, "
			    + "        i.fecha_entrada, i.hora_entrada, i.fecha_salida, i.hora_salida, i.id_inventario, i.cantidad, "
			    + "        i.precio_unitario precio, i.precio_unitario * i.cantidad precioTotal "
			    + "   from inventario i, clientes c, proveedor prov, producto p " 
			    + "  where c.id_cliente = i.id_cliente "
			    + "    and prov.id_proveedor = i.id_proveedor"
			    + "    and prov.id_proveedor = p.id_proveedor "
			    + "    and i.id_producto = p.id_producto "
			    + "    and i.id_cliente = " + parameters
			    + "  order by i.fecha_salida desc ";
			
			listaCompleta = dao.consultarTodas(sql);
			
		}
		
		if (who.equals("RemisionCliente")) {
			RemisionDAOImpl dao = (RemisionDAOImpl) ObjectFactory.getObject(REMISION);
			dao.setLocal(false);
			String sql = "" 
			    + " select distinct c.id_cliente id_cliente, c.nombre nombre_cliente " 
			    + "   from clientes c, inventario i "
			    + "  where c.id_cliente = i.id_cliente "
			    + "  order by i.fecha_salida desc ";
			
			listaCompleta = dao.consultarCliente(sql);			
		}
		
		
		if (who.equals("RemisionTodas")) {
			RemisionDAOImpl dao = (RemisionDAOImpl) ObjectFactory.getObject(REMISION);
			dao.setLocal(false);
			
			String sql = "" 
			    + " select i.remision, i.id_cliente cliente, c.nombre nombre_cliente, "
			    + "        i.id_proveedor, prov.nombre nombre_prov, i.id_producto, p.nombre nombre_producto, "
			    + "        i.fecha_entrada, i.hora_entrada, i.fecha_salida, i.hora_salida, i.id_inventario, i.cantidad, "
			    + "        i.precio_unitario precio, i.precio_unitario * i.cantidad precioTotal "
			    + "   from inventario i, clientes c, proveedor prov, producto p " 
			    + "  where c.id_cliente = i.id_cliente "
			    + "    and prov.id_proveedor = i.id_proveedor"
			    + "    and prov.id_proveedor = p.id_proveedor "
			    + "    and i.id_producto = p.id_producto "
			    + "  order by i.fecha_salida desc ";
			
			listaCompleta = dao.consultarTodas(sql);
			
		}
		
				
		
		
		if (who.equals("ReporteInventarioUnificadoEntradaSalida")) { 
			String query = 
				  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
				+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
				+ "        inv.cantidad, inv.fecha_entrada, inv.hora_entrada, inv.id_entrada_carga entrada_carga, inv.costo_unitario costo_inventario "
				+ "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv "
		        + "  where p.id_producto = inv.id_producto "  
		        + "    and p.id_proveedor  = inv.id_proveedor "
		        + "    and p.id_tipo_producto = t.id_tipo_producto " 
		        + "    and p.id_unidad = u.id_unidad "
		        + "    and p.id_proveedor = prov.id_proveedor "
		        + "    and inv.fecha_entrada = curdate()"
		        + "    and inv.fecha_entrada = inv.fecha_salida "
		        + " order by inv.id_producto asc, cast(inv.id_inventario as signed) asc ";
	
			ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
			reporte.setLocal(false);
			listaCompleta = reporte.consultarEntradas(query);
		}
		
		if (who.equals("ReporteInventarioUnificadoEntradaSalidaAcumulado")) { 
			String query = 
				  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
				+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
				+ "        (count(inv.cantidad) * inv.cantidad) cantidad, inv.fecha_entrada, inv.hora_entrada, inv.id_entrada_carga entrada_carga, "
				+ "        inv.costo_unitario costo_inventario "
				+ "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv "
		        + "  where p.id_producto = inv.id_producto "  
		        + "    and p.id_proveedor  = inv.id_proveedor "
		        + "    and p.id_tipo_producto = t.id_tipo_producto " 
		        + "    and p.id_unidad = u.id_unidad "
		        + "    and p.id_proveedor = prov.id_proveedor "
		        + "    and inv.fecha_entrada = curdate()"
		        + "    and inv.fecha_entrada = inv.fecha_salida "
		        + " group by inv.id_producto, prov.id_proveedor  "
		        + " order by inv.id_producto asc, cast(inv.id_inventario as signed) asc ";
	
			ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
			reporte.setLocal(false);
			listaCompleta = reporte.consultarEntradas(query);
		}
		if (who.equals("ReporteInventarioEntrada")) {
			String query = 
				  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
				+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
				+ "        inv.cantidad, inv.fecha_entrada, inv.hora_entrada, inv.id_entrada_carga entrada_carga, inv.costo_unitario costo_inventario "
				+ "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv "
		        + "  where p.id_producto = inv.id_producto "  
		        + "    and p.id_proveedor  = inv.id_proveedor "
		        + "    and p.id_tipo_producto = t.id_tipo_producto " 
		        + "    and p.id_unidad = u.id_unidad "
		        + "    and p.id_proveedor = prov.id_proveedor "
		        + "    and inv.entrada_salida = 'Entrada' "
		        + " order by inv.fecha_entrada desc ";
	
			ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
			reporte.setLocal(false);
			listaCompleta = reporte.consultarEntradas(query);			
			
		}
		
		if (who.equals("ReporteInventarioEntradaDia")) {
			String query = 
				  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
				+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
				+ "        inv.cantidad, inv.fecha_entrada, inv.hora_entrada, inv.id_entrada_carga entrada_carga, inv.costo_unitario costo_inventario "
				+ "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv "
		        + "  where p.id_producto = inv.id_producto "  
		        + "    and p.id_proveedor  = inv.id_proveedor "
		        + "    and p.id_tipo_producto = t.id_tipo_producto " 
		        + "    and p.id_unidad = u.id_unidad "
		        + "    and p.id_proveedor = prov.id_proveedor "
		        + "    and ( (inv.entrada_salida = 'Entrada' and inv.fecha_entrada = curdate()) " 
		        //+ "           or "
		        //+ "          (inv.entrada_salida = 'Salida'  and inv.fecha_entrada = curdate())"
		        + "        ) "
		        + " order by inv.fecha_entrada desc ";
	
			ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
			reporte.setLocal(false);
			listaCompleta = reporte.consultarEntradas(query);			
			
		}
		
		if (who.equals("ReporteInventarioEntradaDiaParametrizado")) {
			
			String firstDate = parameters;
			try {
				String query = 
					  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
					+ "        sum(inv.cantidad) cantidad, inv.fecha_entrada, inv.hora_entrada, inv.id_entrada_carga entrada_carga, " 
					+ "        inv.costo_unitario costo_inventario "
					+ "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and ( (inv.entrada_salida = 'Entrada' and inv.fecha_entrada = '" + firstDate + "' ) "
			        + "           or "
			        + "          (inv.entrada_salida = 'Salida' and inv.fecha_entrada = '" + firstDate + "' ) "
			        + "           or "
			        + "          (inv.fecha_entrada = inv.fecha_salida and fecha_entrada = '" + firstDate + "' and entrada_salida = 'Salida') "
			        + "        ) "
			        + "  group by p.id_producto, prov.id_proveedor, t.id_tipo_producto, u.id_unidad "
			        + "  order by p.id_producto, prov.id_proveedor, t.id_tipo_producto, u.id_unidad ";
					
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				Utilerias.debug(this, "Query: " + query);
				listaCompleta = reporte.consultarEntradas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}			
		}
		
		if (who.equals("ReporteInventarioSalidaDiaParametrizado")) {
			
			StringTokenizer paramParser = new StringTokenizer(parameters, "|");
			String firstDate = paramParser.nextToken();
			String secondDate = paramParser.nextToken();
			
			try {
				String query = 
					" select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, "
					+ "        inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, "
					+ "        inv.fecha_entrada, inv.hora_entrada, "
					+ "        cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, "
					+ "        sum(inv.cantidad) cantidad,  inv.precio_unitario precio, inv.costo_unitario costo_inventario "
					+ "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and inv.id_cliente = cli.id_cliente "
			        + "    and ( (inv.entrada_salida = 'Salida' and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "' ) "
			        + "        ) "
			        + "  group by p.id_producto, prov.id_proveedor, t.id_tipo_producto, u.id_unidad "
			        + "  order by p.id_producto, prov.id_proveedor, t.id_tipo_producto, u.id_unidad ";
					
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				Utilerias.debug(this, "Query: " + query);
				listaCompleta = reporte.consultarSalidas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}			
		}
		
		if (who.equals("ReporteInventarioEntradaFechas")) {
			try { 
				StringTokenizer paramParser = new StringTokenizer(parameters, "|");
				String firstDate = paramParser.nextToken();
				String secondDate = paramParser.nextToken();
				String query = 
					  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
					+ "        inv.cantidad, inv.fecha_entrada, inv.hora_entrada, inv.id_entrada_carga entrada_carga, inv.costo_unitario costo_inventario "
			        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and ( (inv.entrada_salida = 'Entrada' and inv.fecha_entrada between '" + firstDate + "' and '" + secondDate + "' ) " 
			        //+ "           or "
			        //+ "          (inv.entrada_salida = 'Salida'  and inv.fecha_entrada between '" + firstDate + "' and '" + secondDate + "' )"
			        + "        ) "
			        + " order by inv.fecha_entrada desc ";
		
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				listaCompleta = reporte.consultarEntradas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}			
		}
		
		if (who.equals("ReporteInventarioSalida")) {
			String query = 
				  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
				+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
				+ "        inv.cantidad, inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, inv.precio_unitario precio, " 
				+ "        cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, inv.costo_unitario costo_inventario "
		        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
		        + "  where p.id_producto = inv.id_producto "  
		        + "    and p.id_proveedor  = inv.id_proveedor "
		        + "    and p.id_tipo_producto = t.id_tipo_producto " 
		        + "    and p.id_unidad = u.id_unidad "
		        + "    and p.id_proveedor = prov.id_proveedor "
		        + "    and cli.id_cliente = inv.id_cliente "
		        + "    and inv.fecha_salida between curdate() - 15 and curdate() "
		        + "    and inv.entrada_salida = 'Salida' "
		        + "  order by inv.fecha_salida desc ";
				
			ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
			reporte.setLocal(false);
			Utilerias.debug(this, "Query: " + query);
			listaCompleta = reporte.consultarSalidas(query);			
			
		}
		
		if (who.equals("ReporteInventarioSalidaDia")) {
			String query = 
				  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
				+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
				+ "        inv.cantidad, inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, inv.precio_unitario precio, " 
				+ "        cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, inv.costo_unitario costo_inventario "
		        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
		        + "  where p.id_producto = inv.id_producto "  
		        + "    and p.id_proveedor  = inv.id_proveedor "
		        + "    and p.id_tipo_producto = t.id_tipo_producto " 
		        + "    and p.id_unidad = u.id_unidad "
		        + "    and p.id_proveedor = prov.id_proveedor "
		        + "    and cli.id_cliente = inv.id_cliente "
		        + "    and inv.entrada_salida = 'Salida' "
		        + "    and inv.fecha_salida = curdate() "
		        + "  order by inv.fecha_salida desc ";
				
			ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
			reporte.setLocal(false);
			Utilerias.debug(this, "Query: " + query);
			listaCompleta = reporte.consultarSalidas(query);			
			
		}
		
		if (who.equals("ReporteInventarioSalidaFechas")) {
			try { 
				StringTokenizer paramParser = new StringTokenizer(parameters, "|");
				String firstDate = paramParser.nextToken();
				String secondDate = paramParser.nextToken();
				
				
				String query = 
					  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
					+ "        inv.cantidad, inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, inv.precio_unitario precio, " 
					+ "        cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, inv.costo_unitario costo_inventario "
			        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and cli.id_cliente = inv.id_cliente "
			        + "    and inv.entrada_salida = 'Salida' "
			        + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'"
			        + "  order by inv.fecha_salida desc, prov.id_proveedor, inv.id_producto, inv.id_inventario ";
					
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				Utilerias.debug(this, "Query: " + query);
				listaCompleta = reporte.consultarSalidas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}
			
		}
		
		if (who.equals("ReporteRemisionFechas")) {
			
			try { 
				StringTokenizer paramParser = new StringTokenizer(parameters, "|");
				String firstDate = paramParser.nextToken();
				String secondDate = paramParser.nextToken();	
				String cliente = paramParser.nextToken();
				
				String query = 
					  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
					+ "        sum(inv.cantidad) cantidad, inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, " 
					+ "        inv.precio_unitario precio, cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, inv.costo_unitario costo_inventario "
			        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and cli.id_cliente = inv.id_cliente "
			        + "    and inv.entrada_salida = 'Salida' "
				    + "    and inv.id_cliente = " + cliente
				    + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'"
			        + "  group by inv.remision, p.id_producto, p.nombre, prov.nombre, t.nombre, u.nombre "
			        + "  order by inv.remision, inv.fecha_salida desc, cli.id_cliente, prov.id_proveedor, inv.id_producto, inv.id_inventario ";
					
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				Utilerias.debug(this, "Query: " + query);
				listaCompleta = reporte.consultarSalidas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}
		}
		
		if (who.equals("ReporteRemisionFechasClienteUnificado")) {
			
			try { 
				StringTokenizer paramParser = new StringTokenizer(parameters, "|");
				String firstDate = paramParser.nextToken();
				String secondDate = paramParser.nextToken();	
				
				String query = 
					  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, "
					+ "        inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, "
					+ "        cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, "
					+ "        sum(inv.cantidad) cantidad,  inv.precio_unitario precio, inv.costo_unitario costo_inventario "
			        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and cli.id_cliente = inv.id_cliente "
			        + "    and inv.entrada_salida = 'Salida' "
				    + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'"
			        + "  group by cli.id_cliente, inv.remision, p.id_producto, prov.id_proveedor "
			        + "  order by cli.id_cliente, inv.remision, p.id_producto, prov.id_proveedor, inv.fecha_salida asc ";
					
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				Utilerias.debug(this, "Query: " + query);
				listaCompleta = reporte.consultarSalidas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}
		}
		
		if (who.equals("ReporteRemisionFechasItem")) {
			
			try { 
				StringTokenizer paramParser = new StringTokenizer(parameters, "|");
				String firstDate = paramParser.nextToken();
				String secondDate = paramParser.nextToken();	
				String cliente = paramParser.nextToken();
				
				String query = 
					  " select inv.id_inventario, inv.id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre_prov, " 
					+ "        t.id_tipo_producto, t.nombre nombre_tipo_producto, u.id_unidad, u.nombre nombre_unidades, " 
					+ "        inv.cantidad cantidad, inv.fecha_salida, inv.hora_salida, inv.remision, inv.id_cliente, " 
					+ "        inv.precio_unitario precio, cli.nombre nombre_cliente, inv.id_salida_carga salida_carga, inv.costo_unitario costo_inventario "
			        + "   from producto p, tipo_producto t, unidades u, proveedor prov, inventario inv, clientes cli "
			        + "  where p.id_producto = inv.id_producto "  
			        + "    and p.id_proveedor  = inv.id_proveedor "
			        + "    and p.id_tipo_producto = t.id_tipo_producto " 
			        + "    and p.id_unidad = u.id_unidad "
			        + "    and p.id_proveedor = prov.id_proveedor "
			        + "    and cli.id_cliente = inv.id_cliente "
			        + "    and inv.entrada_salida = 'Salida' "
			        + "    and inv.id_cliente = " + cliente 
			        + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'"
			        + "  order by inv.fecha_salida desc, prov.id_proveedor, inv.id_producto, inv.id_inventario ";
					
				ReporteInventarioDAOImpl reporte = (ReporteInventarioDAOImpl) ObjectFactory.getObject(REPORTE);
				reporte.setLocal(false);
				Utilerias.debug(this, "Query: " + query);
				listaCompleta = reporte.consultarSalidas(query);			
			} catch (Exception e) {
				listaCompleta = new ArrayList(0);
			}
		}

		if (who.equals("ListaProductos")) {
			StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
						" select p.id_producto, p.nombre, p.id_tipo_producto, p.id_unidad, "
				        + "        p.fecha_alta, p.id_proveedor, t.nombre tipo_producto, u.nombre unidades, "
				        + "        pro.nombre proveedor, p.costo, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5 " 
				        + "   from producto p, tipo_producto t, unidades u, proveedor pro "
				        + "  where p.id_tipo_producto = t.id_tipo_producto " 
				        + "    and p.id_unidad = u.id_unidad " 
				        + "    and p.id_proveedor = pro.id_proveedor"
				        + "    and p.id_proveedor = " + st.nextToken()
			            + "    and p.id_unidad = " + st.nextToken()
				        + "    and p.id_tipo_producto = " + st.nextToken();				        
				        
	        
	        listaCompleta = pro.consultarPorDesc(query);
			
		}
		
		if (who.equals("ListaProductosPorProveedor")) {
			StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
						" select p.id_producto, p.nombre, p.id_tipo_producto, p.id_unidad, "
				        + "        p.fecha_alta, p.id_proveedor, t.nombre tipo_producto, u.nombre unidades, "
				        + "        pro.nombre proveedor, p.costo, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5 "
				        + "   from producto p, tipo_producto t, unidades u, proveedor pro "
				        + "  where p.id_tipo_producto = t.id_tipo_producto " 
				        + "    and p.id_unidad = u.id_unidad " 
				        + "    and p.id_proveedor = pro.id_proveedor"
				        + "    and p.id_proveedor = " + st.nextToken();
	        listaCompleta = pro.consultarPorDesc(query);
			
		}
		if (who.equals("ListaProductosVentas")) {
			StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
						" select   distinct pro.id_producto, pro.nombre, t.id_tipo_producto, pro.id_unidad, "
				        + "        pro.fecha_alta, pro.id_proveedor, t.nombre tipo_producto, u.nombre unidades, "
				        + "        prov.nombre proveedor, pro.costo, pro.precio1, pro.precio2, pro.precio3, pro.precio4, pro.precio5 " 
				        + "   from producto pro, tipo_producto t, unidades u, proveedor prov, inventario inv "
				        + "  where pro.id_tipo_producto = t.id_tipo_producto " 
				        + "    and pro.id_unidad = u.id_unidad " 
				        + "    and pro.id_proveedor = prov.id_proveedor" 
				        + "    and pro.id_proveedor = " + st.nextToken()
				        + "    and pro.id_unidad = " + st.nextToken()
				        + "    and pro.id_tipo_producto = " + st.nextToken()				        				        
			            + "    and inv.id_proveedor = prov.id_proveedor "
			            + "    and inv.id_producto = pro.id_producto "
			            + "    and inv.entrada_salida = 'Entrada' ";
	        
			Utilerias.debug(this, "Query: " + query);
	        listaCompleta = pro.consultarPorDesc(query);
			
		}
		
		if (who.equals("ListaProductosLista")) {
			//StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
				          " select p.id_producto id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre, tpro.id_tipo_producto id_tipo_producto, tpro.nombre nombre_tpro, " 
				        + "        u.id_unidad id_unidad, u.nombre nombre_uni, i.precio_unitario precio_unitario, i.costo_unitario costo_inventario, p.costo costo_producto, p.precio1, p.precio2, p.precio3, p.precio3, p.precio4, p.precio5, "
				        + "        sum(i.cantidad) cantidad "
				        + "   from inventario i, producto p, tipo_producto tpro, unidades u, proveedor prov "
				        + "  where i.id_entrada_carga = " + parameters
				        + "    and i.id_producto = p.id_producto "
				        + "    and i.id_proveedor = p.id_proveedor "
				        + "    and i.id_proveedor = prov.id_proveedor "
				        + "    and p.id_tipo_producto = tpro.id_tipo_producto "
				        + "    and p.id_unidad = u.id_unidad "
				        + "  group by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre, i.precio_unitario "
				        + "  order by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre ";
	        
	        listaCompleta = pro.consultarAcumulados(query);
		}
		
		
		if (who.equals("ListaProductosListaAcumulado")) {
			//StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
				          " select p.id_producto id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre, tpro.id_tipo_producto id_tipo_producto, tpro.nombre nombre_tpro, " 
				        + "        u.id_unidad id_unidad, u.nombre nombre_uni, i.precio_unitario precio_unitario, i.costo_unitario costo_inventario, p.costo costo_producto, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5, " 
				        + "        sum(i.cantidad) cantidad, count(*) cantidadCajas "
				        + "   from inventario i, producto p, tipo_producto tpro, unidades u, proveedor prov "
				        + "  where i.id_producto = p.id_producto "
				        + "    and i.id_proveedor = p.id_proveedor "
				        + "    and p.id_tipo_producto = tpro.id_tipo_producto "
				        + "    and p.id_unidad = u.id_unidad "
				        + "    and p.id_proveedor = prov.id_proveedor "
				        + "    and i.entrada_salida = 'Entrada' "
				        + "  group by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre "
				        + "  order by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre ";
	        
	        listaCompleta = pro.consultarAcumulados(query);
		}
		
		
		if (who.equals("ListaProductosListaSalida")) {
			//StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
				          " select p.id_producto id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre, tpro.id_tipo_producto id_tipo_producto, tpro.nombre nombre_tpro, " 
				        + "        u.id_unidad id_unidad, u.nombre nombre_uni, i.precio_unitario precio_unitario, i.costo_unitario costo_inventario, p.costo costo_producto, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5, sum(i.cantidad) cantidad "
				        + "   from inventario i, producto p, tipo_producto tpro, unidades u, proveedor prov "
				        + "  where i.id_salida_carga = " + parameters
				        + "    and i.id_producto = p.id_producto "
				        + "    and i.id_proveedor = p.id_proveedor "
				        + "    and i.id_proveedor = prov.id_proveedor " 
				        + "    and p.id_tipo_producto = tpro.id_tipo_producto "
				        + "    and p.id_unidad = u.id_unidad "
				        + "  group by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre, i.precio_unitario "
				        + "  order by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre ";
	        
	        listaCompleta = pro.consultarAcumulados(query);
		}
		
		if (who.equals("ListaProductosListaSalidaIndividual")) {
			//StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
				          " select p.id_producto id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre, tpro.id_tipo_producto id_tipo_producto, tpro.nombre nombre_tpro, " 
				        + "        u.id_unidad id_unidad, u.nombre nombre_uni, i.precio_unitario precio_unitario, i.costo_unitario costo_inventario, p.costo costo_producto, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5, i.cantidad cantidad "
				        + "   from inventario i, producto p, tipo_producto tpro, unidades u, proveedor prov "
				        + "  where i.id_salida_carga = " + parameters
				        + "    and i.id_producto = p.id_producto "
				        + "    and i.id_proveedor = p.id_proveedor "
				        + "    and i.id_proveedor = prov.id_proveedor " 
				        + "    and p.id_tipo_producto = tpro.id_tipo_producto "
				        + "    and p.id_unidad = u.id_unidad "
				        + "  order by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre ";
	        
	        listaCompleta = pro.consultarAcumulados(query);
		}
		if (who.equals("ListaProductosListaSalidaAcumulado")) {
			//StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			//tipo producto, unidades, proveedores
			ProductoDAOImpl pro = (ProductoDAOImpl) ObjectFactory.getObject(PRODUCTO);
			pro.setLocal(false);
			
			String query = 
				          " select p.id_producto id_producto, p.nombre nombre_prod, prov.id_proveedor, prov.nombre nombre, tpro.id_tipo_producto id_tipo_producto, tpro.nombre nombre_tpro, " 
				        + "        u.id_unidad id_unidad, u.nombre nombre_uni, i.precio_unitario precio_unitario, p.costo costo_unitario, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5, " 
				        + "        sum(i.cantidad) cantidad "
				        + "   from inventario i, producto p, tipo_producto tpro, unidades u, proveedor prov "
				        + "  where i.id_producto = p.id_producto "
				        + "    and i.id_proveedor = p.id_proveedor "
				        + "    and p.id_tipo_producto = tpro.id_tipo_producto "
				        + "    and p.id_unidad = u.id_unidad "
				        + "    and p.id_proveedor = prov.id_proveedor "
				        + "    and i.entrada_salida = 'Salida'"
				        + "    and i.fecha_salida between curdate() - 15 and curdate() "
				        + "  group by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre "
				        + "  order by p.id_producto, p.nombre, prov.nombre, tpro.nombre, u.nombre ";
	        
	        listaCompleta = pro.consultarAcumulados(query);
		}
		
		//aquellas que no traigan el tipo de Unidad de CODIGO
		if (who.equals("UnidadesTipoProductoNoCode")) {
			UnidadesDAOImpl uni = (UnidadesDAOImpl) ObjectFactory.getObject(UNIDADES);
			uni.setLocal(false);
			String query = 
			      "     select distinct u.id_unidad, u.nombre "
			    + "       from unidades u, tipo_producto tip, producto pro "
			    + "      where pro.id_unidad = u.id_unidad " 
			    + "        and u.id_unidad <> 1 " 
			    + "        and pro.id_tipo_producto = tip.id_tipo_producto "
			    + "        and pro.id_tipo_producto = " + parameters;
			    
			listaCompleta = uni.consultarUnidadesTipoProducto(query);
			
		}
		if (who.equals("ProveedoresUnidadesTipoProducto")) {
			StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			ProveedorDAOImpl pro = (ProveedorDAOImpl) ObjectFactory.getObject(PROVEEDOR);
			pro.setLocal(false);
			String sql = " " 
			    + "     select distinct prov.id_proveedor, prov.genera_codigo, prov.nombre, prov.descripcion "
			    + "       from proveedor prov, producto pro, unidades u, tipo_producto tip "
			    + "      where pro.id_unidad = u.id_unidad " 
			    + "        and pro.id_tipo_producto = tip.id_tipo_producto "
			    + "        and pro.id_unidad = " + st.nextToken() 
			    + "        and pro.id_tipo_producto = " + st.nextToken()
			    + "        and pro.id_proveedor = prov.id_proveedor ";
			listaCompleta = pro.consultarProvUnidades(sql);
		}
			
		if (who.equals("ProveedoresUnidadesTipoProductoVentas")) {
			StringTokenizer st = new StringTokenizer(parameters, DELIMITADOR);
			ProveedorDAOImpl pro = (ProveedorDAOImpl) ObjectFactory.getObject(PROVEEDOR);
			pro.setLocal(false);
			String sql = " " 
			    + "     select distinct prov.id_proveedor, prov.genera_codigo, prov.nombre, prov.descripcion "
			    + "       from proveedor prov, producto pro, unidades u, tipo_producto tip, inventario inv"
			    + "      where pro.id_unidad = u.id_unidad " 
			    + "        and pro.id_tipo_producto = tip.id_tipo_producto "
			    + "        and pro.id_unidad = " + st.nextToken() 
			    + "        and pro.id_tipo_producto = " + st.nextToken()
			    + "        and pro.id_proveedor = prov.id_proveedor "
			    + "        and inv.id_proveedor = prov.id_proveedor "
			    + "        and inv.id_producto = pro.id_producto " 
			    + "        and inv.entrada_salida = 'Entrada' ";
			listaCompleta = pro.consultarProvUnidades(sql);
		}
		
		
		return listaCompleta;
		
	}
	public String getDescriptionProducto(String code, String proveedor) { 

		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		
		est.setSql(" select concat(p.id_producto, '" + DELIMITADOR + "', p.id_proveedor, '" + DELIMITADOR + "', p.id_tipo_producto, '" + DELIMITADOR + "', p.id_unidad, '" + DELIMITADOR + "', p.nombre) nombre " +
				   "   from producto p where p.id_producto like '%" + code + "%' and p.id_proveedor = " + proveedor);
		est.setSqlColumn("nombre");
		String valida = est.executeQueryString();

		return valida;
	}
	
	public void addRemisionSinStorage(ElementsGroupedXMLBox element, String remision, String idCliente, long idEntrada, long idSalida) {
		
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		
		
		String sql = ""
			+ "   select concat(i.id_inventario, '" + DELIMITADOR + "', i.id_producto, '" + DELIMITADOR + "', " 
			+ "                 i.id_proveedor, '" + DELIMITADOR + "', sum(cantidad), '" + DELIMITADOR + "', " 
			+ "                 i.fecha_entrada, '" + DELIMITADOR + "', i.hora_entrada) total " 
			+ "     from inventario i, producto pro "
			+ "    where i.id_producto = '" + element.getCodeProduct() + "'"
		    + "      and i.id_proveedor = " + element.getProveedor()
		    + "      and i.id_producto = pro.id_producto "
		    + "      and i.id_proveedor = pro.id_proveedor " 
		    + "      and pro.id_tipo_producto = " + element.getTipoProducto()
		    + "      and pro.id_unidad = " + element.getUnidadMedida()
		    + "      and i.entrada_salida = 'Entrada' "
		    + "      and i.remision = 0 "
		    + " group by i.id_inventario, i.id_producto, i.id_proveedor "
		    + " order by sum(cantidad) desc ";

		
		est.setSql(sql);
		est.setSqlColumn("total");
		
		List<String> listaRemision = est.executeQueryListString();
		
		StringTokenizer st = null;
		
		String idInventario = "";
		String idProducto = "";
		String idProveedor = "";
		String fechaEntrada = "";
		String horaEntrada = "";
		
		double cantidadBaseDatos = 0;
		double cantidadTransaccionInventario = Utilerias.strToDouble(element.getCantidad());
		
		for (int i=0; i < listaRemision.size(); i++) {
			st = new StringTokenizer(listaRemision.get(i).toString(), DELIMITADOR);
			idInventario = st.nextToken();
			idProducto = st.nextToken();
			idProveedor = st.nextToken();
			cantidadBaseDatos = Utilerias.strToDouble(st.nextToken());
			fechaEntrada = st.nextToken();
			horaEntrada = st.nextToken();
				
			if (cantidadTransaccionInventario == 0) {
				break;
			}
			
			if (cantidadBaseDatos <= cantidadTransaccionInventario) {
				//registrar cantidad que pone el usuario como salida
				tomarCantidadInventario(idInventario, idProducto, idProveedor, cantidadBaseDatos, idSalida, remision, idCliente);
				
				//actualizar los montos
				cantidadTransaccionInventario = cantidadTransaccionInventario - cantidadBaseDatos;
			} else {
				
				//se realiza un corte
				double generarNuevoStorage = cantidadBaseDatos - cantidadTransaccionInventario;
				
				//se registra lo que esta tomando el usuario
				
				tomarCantidadInventario(idInventario, idProducto, idProveedor, cantidadTransaccionInventario, idSalida, remision, idCliente);
				
				//se genera la nueva storage con la cantidad tomada de otro articulo
				tomarCantidadInventarioCompletarOp(idProducto, 
												   idProveedor, 
												   Utilerias.intToStr(element.getUnidadMedida()), 
												   Utilerias.intToStr(element.getTipoProducto()), 
												   generarNuevoStorage, idEntrada, 
												   fechaEntrada, horaEntrada);
				
				cantidadTransaccionInventario = 0;
			}
		}
	}
	
	public void tomarCantidadInventarioCompletarOp(String idProducto, String idProveedor, String idUnidad, 
		String idTipoProducto, double cantidad, long idEntrada, 
		String fechaEntrada, String horaEntrada) {
		
		Utilerias.debug(this, "tomarCantidadInventarioCompletarOp");
		Inventario inv = new Inventario();
		String storageUnit = generateStorageUnit(idProducto, idProveedor, idUnidad, idTipoProducto);
		
		inv.setIdInventario(storageUnit);
		inv.setIdProducto(idProducto);
		inv.setIdProveedor(Utilerias.strToInt(idProveedor));
		inv.setCantidad(cantidad);
		inv.setEntradaSalida("Entrada");
		inv.setIdEntradaCarga(idEntrada);
		inv.setIdSalidaCarga(0);
		inv.setRemision(0);
		inv.setIdCliente(0);		
		
		Utilerias.debug(this, "FechaEntrada: " + fechaEntrada);
		Utilerias.debug(this, "HoraEntrada: " + horaEntrada);
		
		Calendar cal = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(horaEntrada, ":");
		cal.set(Calendar.HOUR_OF_DAY, Utilerias.strToInt(st.nextToken()));
		cal.set(Calendar.MINUTE, Utilerias.strToInt(st.nextToken()));
		cal.set(Calendar.SECOND, Utilerias.strToInt(st.nextToken()));
		
		java.sql.Time time = Utilerias.getHoraSQL(cal);
		//java.sql.Time time = new java.sql.Time( cal.getTime().getTime());
		inv.setHoraEntrada(time);
		inv.setHoraSalida(time);
						
		java.sql.Date date = Utilerias.getDateSQL(fechaEntrada, "yyyy-MM-dd");
		
		//java.sql.Date date = new java.sql.Date(cal.getTime().getTime());
		inv.setFechaEntrada(date);
		inv.setFechaSalida(date);
		
		
		dmlOperations(inv, "insert");
		Utilerias.debug(this, "tomarCantidadInventarioCompletarOp   out");
	}
	
	public void tomarCantidadInventario(String idInventario, String idProducto, String idProveedor, double cantidad, long salidaCarga, String remision, String idCliente) { 
		
		Utilerias.debug(this, "tomarCantidadInventario");
		Inventario inv = new Inventario();
		inv.setIdInventario(idInventario);
		Utilerias.debug(this, "tomarCantidadInventario  before get data");
		inv = (Inventario) getData(idInventario + DELIMITADOR + idProducto + DELIMITADOR + idProveedor, "Inventario");
		//inv.setIdProducto(idProducto);
		//inv.setIdProveedor(Utilerias.strToInt(idProveedor));
		inv.setCantidad(cantidad);
		inv.setEntradaSalida("Salida");
		inv.setIdSalidaCarga(salidaCarga);
		inv.setRemision(Utilerias.strToLong(remision));
		inv.setIdCliente(Utilerias.strToInt(idCliente));
		Calendar cal = Calendar.getInstance();		
		java.sql.Time time = new java.sql.Time( cal.getTime().getTime());
		//inv.setHoraEntrada(time);
		inv.setHoraSalida(time);
		java.sql.Date date = new java.sql.Date(cal.getTime().getTime());
		inv.setFechaSalida(date);
		Utilerias.debug(this, "tomarCantidadInventario  before update");
		dmlOperations(inv, "update");		
		Utilerias.debug(this, "tomarCantidadInventario  out");
	}
	
	public void addRemisionConStorage(ElementsGroupedXMLBox element, String remision, String idCliente, long idEntrada, long idSalida) {
		tomarCantidadInventario(element.getStorage(), 
								element.getCodeProduct(), 
								Utilerias.intToStr(element.getProveedor()), 
								Utilerias.strToDouble(element.getCantidad()), 
								idSalida, 
								remision, 
								idCliente);
	}
	
	public String generateStorageUnit(String prod, String prov, String unidad, String tipoProducto) {
		
		IntegratorDAOImpl est = (IntegratorDAOImpl) ObjectFactory.getObject(INTEGRATOR);
		est.setLocal(false);
		long secuencia = est.getId(prod, prov, unidad, tipoProducto, false);
		return Utilerias.longToStr(secuencia);
	}
	
	public String generateMaxStorageUnit(String prod, String prov, String unidad, String tipoProducto) {
		IntegratorDAOImpl est = (IntegratorDAOImpl) ObjectFactory.getObject(INTEGRATOR);
		est.setLocal(false);
		long secuencia = est.getMaxId(prod, prov, unidad, tipoProducto, false);
		return Utilerias.longToStr(secuencia);
	} 
	
	public long getIdVarios(String name) {
		IntegratorDAOImpl est = (IntegratorDAOImpl) ObjectFactory.getObject(INTEGRATOR);
		est.setLocal(false);
		long secuencia = est.getIdVarios(name, false);
		return secuencia;
    }
	
	
	@SuppressWarnings("rawtypes")
	public boolean checkEnoughInventoryForBox(String codeProduct, String proveedor, String unidadMedida, String tipoProducto, String cantidad, List elementData) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = ""
		    + "   select sum(cantidad) cantidadDisponible "
		    + "     from inventario i, producto pro "
		    + "    where i.id_producto = '" + codeProduct + "'"
		    + "      and i.id_proveedor = " + proveedor
		    + "      and i.id_producto = pro.id_producto "
		    + "      and i.id_proveedor = pro.id_proveedor " 
		    + "      and pro.id_tipo_producto = " + tipoProducto
		    + "      and pro.id_unidad = " + unidadMedida
		    + "      and i.entrada_salida = 'ENTRADA' "
		    + "      and i.remision = 0 ";

		est.setSql(sql);
		est.setSqlColumn("cantidadDisponible");
		String cantidadDisponibleQuery = est.executeQueryString();

		double cantidadLista = 0;
		for (int i=0; i < elementData.size(); i++) { 
			ElementsXMLBox element = (ElementsXMLBox) elementData.get(i);
			if (element.getCodeProduct().equals(codeProduct)
				&& element.getProveedor() == Utilerias.strToInt(proveedor)
				&& element.getTipoProducto() == Utilerias.strToInt(tipoProducto)
				&& element.getUnidadMedida() == Utilerias.strToInt(unidadMedida)) {
				
				cantidadLista += Utilerias.strToDouble(element.getCantidad());
				
			}
		}
		
		Utilerias.debug(this, "Cantidad en inventario: " + cantidadDisponibleQuery);
		Utilerias.debug(this, "Cantidad en Lista actual de este transacciòn: " + cantidadLista);
		
		Utilerias.debug(this, "Cantidad requerida: " + cantidad);
		
		double cantidadLibre = Utilerias.strToDouble(cantidadDisponibleQuery) - cantidadLista;
		
		if (Utilerias.strToDouble(cantidad) <= cantidadLibre) {
			return true;
		}
		
		return false;
	}	
	
	
	@SuppressWarnings("rawtypes")
	public List checkEnoughInventoryForList(List elementData) {
		
		List<String> errors = new ArrayList<String>();
		
		for (int i=0; i < elementData.size(); i++) {
			
			ElementsGroupedXMLBox box = (ElementsGroupedXMLBox) elementData.get(i);
		
			DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
			est.setLocal(false);
			String sql = ""
			    + "   select sum(cantidad) cantidadDisponible "
			    + "     from inventario i, producto pro "
			    + "    where i.id_producto = '" + box.getCodeProduct() + "'"
			    + "      and i.id_proveedor = " + box.getProveedor()
			    + "      and i.id_producto = pro.id_producto "
			    + "      and i.id_proveedor = pro.id_proveedor " 
			    + "      and pro.id_tipo_producto = " + box.getTipoProducto()
			    + "      and pro.id_unidad = " + box.getUnidadMedida()
			    + "      and i.entrada_salida = 'ENTRADA' "
			    + "      and i.remision = 0 ";
	
			est.setSql(sql);
			est.setSqlColumn("cantidadDisponible");
			String cantidadDisponibleQuery = est.executeQueryString();
	
			
			Utilerias.debug(this, "Cantidad en inventario: " + cantidadDisponibleQuery);
			Utilerias.debug(this, "Cantidad requerida: " + box.getCantidad());
						
			if (Utilerias.strToDouble(box.getCantidad()) > Utilerias.strToDouble(cantidadDisponibleQuery)) {
				errors.add("Para el codigo de producto " + box.getDescProduct() + " ya no existe cantidad en inventario");
			}
		}
		
		return errors;
	}
	//
	//Este metodo solo funciona para
	//los valores acumulados
	//no probarlo para mas cosas
	//se utiliza unicamente en reportes.jsp.
	public int calculaCajas(String idProd, int prov, final String status) {
		//utilizado unicamente en reportes.jsp
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select count(*) cuenta " 
		    + "   from inventario i "
		    + "  where i.id_producto = '" + idProd + "'"
		    + "    and i.id_proveedor = " + prov
		    + "    and i.entrada_salida = '" + status + "'";
		est.setSql(sql);
		est.setSqlColumn("cuenta");
		int cuenta = est.executeQueryInt();
		return cuenta;
	}
	
	public int calculaCajasHistorico(String idProd, int prov, final String firstDate) {
		//utilizado unicamente en reportes.jsp
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl) ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select count(*) cuenta " 
		    + "   from inventario inv "
		    + "  where inv.id_producto = '" + idProd + "'"
		    + "    and inv.id_proveedor = " + prov
		    + "    and ( (inv.entrada_salida = 'Entrada' and inv.fecha_entrada = '" + firstDate + "' ) "
	        + "           or "
	        + "          (inv.entrada_salida = 'Salida' and inv.fecha_entrada = '" + firstDate + "' ) "
	        + "           or "
	        + "          (inv.fecha_entrada = inv.fecha_salida and fecha_entrada = '" + firstDate + "' and entrada_salida = 'Salida') "
	        + "        ) ";
	    	    
		est.setSql(sql);
		est.setSqlColumn("cuenta");
		int cuenta = est.executeQueryInt();
		return cuenta;
	}
	
	public int calculaCajasHistoricoSalida(String idProd, int prov, final String firstDate, final String secondDate) {
		//utilizado unicamente en reportes.jsp
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl) ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select count(*) cuenta " 
		    + "   from inventario inv "
		    + "  where inv.id_producto = '" + idProd + "'"
		    + "    and inv.id_proveedor = " + prov
		    + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "' "
	        + "    and inv.entrada_salida = 'Salida'";
	    	    
		est.setSql(sql);
		est.setSqlColumn("cuenta");
		int cuenta = est.executeQueryInt();
		return cuenta;
	}
	
	public int calculaCajasEntrada(String idProd, int prov, final String status, final String entradaCarga) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select count(*) cuenta " 
		    + "   from inventario i "
		    + "  where i.id_producto = '" + idProd + "'"
		    + "    and i.id_proveedor = " + prov
		    + "    and i.entrada_salida = '" + status + "'"
		    + "    and i.id_entrada_carga = " + entradaCarga;
		est.setSql(sql);
		est.setSqlColumn("cuenta");
		int cuenta = est.executeQueryInt();
		return cuenta;
	}
	
	public int calculaCajasSalida(String idProd, int prov, final String status, final String salidaCarga) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select count(*) cuenta " 
		    + "   from inventario i "
		    + "  where i.id_producto = '" + idProd + "'"
		    + "    and i.id_proveedor = " + prov
		    + "    and i.entrada_salida = '" + status + "'"
		    + "    and i.id_salida_carga = " + salidaCarga;
		est.setSql(sql);
		est.setSqlColumn("cuenta");
		int cuenta = est.executeQueryInt();
		return cuenta;
	}
	
	public List<String> tomarClienteEntradaSalidaRemision(String idSalidaCarga) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select cli.id_cliente, cli.nombre nombre, cli.precio_preferente, i.remision remision, i.fecha_salida fecha_salida, i.hora_salida "		    		    	
		    + "   from inventario i, clientes cli "
		    + "  where i.id_cliente = cli.id_cliente "
		    + "    and i.id_salida_carga = " + idSalidaCarga;
		est.setSql(sql);		
		String[] arrayParameters = { "id_cliente", "nombre", "precio_preferente", "remision", "fecha_salida", "hora_salida"};
		List<String> listaDatos = est.executeQueryListOneRow(arrayParameters);
		/*
		java.sql.Date date = new java.sql.Date(
                Calendar.getInstance().getTime().getTime());
        
		listaDatos.add(Utilerias.getDate(date, "yyyy-MM-dd"));
		*/
		return listaDatos;
	}
	
	public List<String> tomarClienteEntrada(String idSalidaCarga) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select i.fecha_entrada fecha_entrada, i.hora_entrada hora_entrada "		    		    	
		    + "   from inventario i"
		    + "  where i.id_entrada_carga = " + idSalidaCarga;
		est.setSql(sql);
		String[] arrayParameters = { "fecha_entrada", "hora_entrada"};
		List<String> listaDatos = est.executeQueryListOneRow(arrayParameters);
		return listaDatos;
	}
	
	@SuppressWarnings("rawtypes")
	public List<List> tomarInventarioPorCodigo(String producto, String proveedor) {
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl) ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select i.id_inventario inventario, i.id_producto clave_producto, prod.nombre nombre_producto, i.cantidad cantidad, prov.nombre nombre_proveedor "		    		    	
		    + "   from inventario i, proveedor prov, producto prod "
		    + "  where i.id_producto = '" + producto + "'"
		    + "    and i.id_proveedor = '" + proveedor + "'"
		    + "    and prov.id_proveedor = i.id_proveedor "
		    + "    and prod.id_producto = i.id_producto "
		    + "    and prod.id_proveedor = i.id_proveedor "
		    + "    and i.entrada_salida = 'Entrada' ";
		
		est.setSql(sql);		
		String[] arrayParameters = { "inventario", "clave_producto", "nombre_producto", "cantidad", "nombre_proveedor"};
		
		List<List> listaDatos = est.executeQueryListMultipleRows(arrayParameters);
		/*
		java.sql.Date date = new java.sql.Date(
                Calendar.getInstance().getTime().getTime());
        
		listaDatos.add(Utilerias.getDate(date, "yyyy-MM-dd"));
		*/
		return listaDatos;
	}
	public double traerCantidadTotalRemisiones(String startEndRemision) {
		double cuenta = -1;
		try { 
			StringTokenizer st = new StringTokenizer(startEndRemision, "|");
			
			String firstDate = st.nextToken();
			String secondDate = st.nextToken();
			String cliente = st.nextToken();
			
			DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
			est.setLocal(false);
			String sql = "" 
			    + " select sum(inv.cantidad * inv.precio_unitario) total " 
			    + "   from inventario inv "
			    + "  where inv.id_cliente = " + cliente 
	            + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'";	        	        
			est.setSql(sql);
			est.setSqlColumn("total");
			cuenta = est.executeQueryDouble();
		} catch (Exception e) { 
			cuenta = -1;
		}
		return cuenta;
	}	
	
	public double traerCantidadCostoTotalRemisiones(String startEndRemision) {
		double cuenta = -1;
		try { 
			StringTokenizer st = new StringTokenizer(startEndRemision, "|");
			
			String firstDate = st.nextToken();
			String secondDate = st.nextToken();
			String cliente = st.nextToken();
			
			DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
			est.setLocal(false);
			String sql = "" 
			    + " select sum(inv.cantidad * inv.costo_unitario) total " 
			    + "   from inventario inv "
			    + "  where inv.id_cliente = " + cliente 
	            + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'";	        	        			
			est.setSql(sql);
			est.setSqlColumn("total");
			cuenta = est.executeQueryDouble();
		} catch (Exception e) { 
			cuenta = -1;
		}
		return cuenta;
	}
	public double traerCantidadTotalRemision(String idRemision) { 
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select sum(i.cantidad * i.precio_unitario) total " 
		    + "   from inventario i "
		    + "  where i.remision = " + idRemision;
		est.setSql(sql);
		est.setSqlColumn("total");
		double cuenta = est.executeQueryDouble();
		return cuenta;
	}
	
	public double traerCantidadCostoTotalRemision(String idRemision) { 
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select sum(i.cantidad * i.costo_unitario) total " 
		    + "   from inventario i "
		    + "  where i.remision = " + idRemision;
		est.setSql(sql);
		est.setSqlColumn("total");
		double cuenta = est.executeQueryDouble();
		return cuenta;
	}
	
	public double traerPagadoRemisiones(String startEndRemision) {
		double cuenta = -1;
		try { 
			StringTokenizer st = new StringTokenizer(startEndRemision, "|");
			String firstDate = st.nextToken();
			String secondDate = st.nextToken();
			String cliente = st.nextToken();
			DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
			est.setLocal(false);
			String sql = "" 
			    + " select sum(tabla.pagada) pagada " 
			    + "   from ( " 
			    + " select distinct remision, inv.cantidad_pagada pagada " 
			    + "   from inventario inv "
			    + "  where inv.id_cliente = " + cliente
			    + "    and inv.fecha_salida between '" + firstDate + "' and '" + secondDate + "'"
			    + "        ) tabla";
			est.setSql(sql);
			est.setSqlColumn("pagada");
			cuenta = est.executeQueryDouble();
		} catch (Exception e) {
			cuenta = -1;
		}
		return cuenta;
	}
	
	public double traerPagadoRemision(String idRemision) { 
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " select i.cantidad_pagada pagada " 
		    + "   from inventario i "
		    + "  where i.remision = " + idRemision;
		est.setSql(sql);
		est.setSqlColumn("pagada");
		double cuenta = est.executeQueryDouble();
		return cuenta;
	}
	
	public int actualizarPrecio(String remision, String cantidad) {
		if (NumbersValidation.isDouble(cantidad)) {
			
			DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
			est.setLocal(false);
			
			//checar si nos estamos pasando el valor total de una sola remisiòn
			//solo actualizar el maximo de la remision.
			String sqlCheck = "" 
			    + " select sum(cantidad * precio_unitario) cantidad_total " 
		        + "   from inventario " 
		        + "  where remision = " + remision;
			est.setSql(sqlCheck);
			est.setSqlColumn("cantidad_total");
			double valorRemision = est.executeQueryDouble();
			if (valorRemision < Utilerias.strToDouble(cantidad)) { 
				cantidad = "" + valorRemision;
			}
			
			String sql = "" 
			    + " update inventario set cantidad_pagada = " + cantidad 
			    + " where remision = " + remision;
			est.setSql(sql);
			int result = est.executeStatement(null);
			return result;
		} else {
			return -1;
		}
	}	
	
	public int aplicarDevolucion(String remision, String idInventario, String codeProd, int prov) throws UnsupportedEncodingException {
		
		DescripcionesGeneralesDAOImpl est = (DescripcionesGeneralesDAOImpl)ObjectFactory.getObject(DESCRIPCIONES);
		est.setLocal(false);
		String sql = "" 
		    + " update inventario " 
		    + "    set entrada_salida = 'Entrada', "
		    + "        id_salida_carga = 0, "
		    + "        remision = '0', "
		    + "        id_cliente = 0, "
		    + "        precio_unitario = 0, "
		    + "        cantidad_pagada = 0"
		    + "  where remision = " + remision
		    + "    and id_inventario = '" + idInventario + "'"
		    + "    and id_producto = '" + codeProd + "'"
		    + "    and id_proveedor = " + prov;
		est.setSql(new String(sql.getBytes(), "UTF-8"));
		int result = est.executeStatement(null);
		return result;
	}	
}
