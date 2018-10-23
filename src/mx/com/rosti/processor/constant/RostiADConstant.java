package mx.com.rosti.processor.constant;

public interface RostiADConstant {
    String UNIDADES = "UnidadesDAO";
    String DESCRIPCIONES = "DescripcionesGeneralesCatalog";
    String USUARIO = "UsuarioDAO";
    String PROVEEDOR = "ProveedorDAO";
    String ROLES = "RolesDAO";
    String TIPO_PRODUCTO = "TipoProductoDAO";
    String CLIENTES = "ClientesDAO"; 
    String INVENTARIO = "InventarioDAO";
    String PRODUCTO = "ProductoDAO";
    String INTEGRATOR = "IntegratorDAO";
    String REPORTE = "ReporteDAO";
    String PRODUCTO_ACUMULADO = "ProductoAcumuladoDAO";
    String REMISION = "RemisionDAO";
    String USER_ADMIN = "mauricio.gomez.77@gmail.com";    
    String SQL_USUARIOS_ROL_DESCRIP = "" 
    	+ " select u.id_usuario, u.nombre, u.id_rol, r.nombre_rol rol_descripcion, " 
    	+ "        u.password, u.correo_electronico, u.telefono " 
    	+ "   from usuario u, roles r " 
    	+ "  where u.id_rol = r.id_rol ";
    
    String SQL_PRODUCTOS_LISTA = "" 
        + " select p.id_producto, p.nombre, p.id_tipo_producto, p.id_unidad, "
        + "        p.fecha_alta, p.id_proveedor, t.nombre tipo_producto, u.nombre unidades, "
        + "        pro.nombre proveedor, p.costo, p.precio1, p.precio2, p.precio3, p.precio4, p.precio5 " 
        + "   from producto p, tipo_producto t, unidades u, proveedor pro "
        + "  where p.id_tipo_producto = t.id_tipo_producto " 
        + "    and p.id_unidad = u.id_unidad " 
        + "    and p.id_proveedor = pro.id_proveedor";
    
    String DELIMITADOR = "|";
        
}
