package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Producto;
import mx.com.rosti.to.ProductoAcumulado;

/**
 * Clase para mapear periodos.
 */
public class ProductoMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Producto mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Producto cc = new Producto();
        cc.setIdProducto(resultSet.getString("id_producto"));
        cc.setNombre(resultSet.getString("nombre"));
        cc.setIdTipoProducto(resultSet.getInt("id_tipo_producto"));
        cc.setIdUnidad(resultSet.getInt("id_unidad"));
        cc.setFechaAlta(resultSet.getDate("fecha_alta"));
        cc.setIdProveedor(resultSet.getInt("id_proveedor"));
        
        cc.setCosto(resultSet.getDouble("costo"));
	    cc.setPrecio1(resultSet.getDouble("precio1"));
	    cc.setPrecio2(resultSet.getDouble("precio2"));
	    cc.setPrecio3(resultSet.getDouble("precio3"));
	    cc.setPrecio4(resultSet.getDouble("precio4"));
	    cc.setPrecio5(resultSet.getDouble("precio5"));
	    
        return cc;
    }
    public Producto mapRowDesc(final ResultSet resultSet, final int row)
    	throws SQLException {

    	Producto cc = new Producto();
	    cc.setIdProducto(resultSet.getString("id_producto"));
	    cc.setNombre(resultSet.getString("nombre"));
	    cc.setIdTipoProducto(resultSet.getInt("id_tipo_producto"));
	    cc.setIdUnidad(resultSet.getInt("id_unidad"));
	    cc.setFechaAlta(resultSet.getDate("fecha_alta"));
	    cc.setIdProveedor(resultSet.getInt("id_proveedor"));
	    
	    //los no comunes.
	    cc.setTipoProducto(resultSet.getString("tipo_producto"));
	    cc.setUnidades(resultSet.getString("unidades"));
	    cc.setProveedor(resultSet.getString("proveedor"));
	    
	    cc.setCosto(resultSet.getDouble("costo"));
	    cc.setPrecio1(resultSet.getDouble("precio1"));
	    cc.setPrecio2(resultSet.getDouble("precio2"));
	    cc.setPrecio3(resultSet.getDouble("precio3"));
	    cc.setPrecio4(resultSet.getDouble("precio4"));
	    cc.setPrecio5(resultSet.getDouble("precio5"));
	    
	    return cc;
	}

    public ProductoAcumulado mapRowAcumulado(final ResultSet resultSet, final int row)
	throws SQLException {
    	
    	ProductoAcumulado acum = new ProductoAcumulado();
        acum.setIdProd(resultSet.getString("id_producto"));
        acum.setDescProd(resultSet.getString("nombre_prod"));
        acum.setProv(resultSet.getInt("id_proveedor"));
        acum.setDescProv(resultSet.getString("nombre"));
        acum.setTipoProd(resultSet.getInt("id_tipo_producto"));
        acum.setTipoProdDesc(resultSet.getString("nombre_tpro"));
        acum.setUnidadProd(resultSet.getInt("id_unidad"));
        acum.setUnidadProdDesc(resultSet.getString("nombre_uni"));
        acum.setCantidad(resultSet.getDouble("cantidad"));
        acum.setPrecioUnitario(resultSet.getDouble("precio_unitario"));
        
        if (resultSet.getObject("costo_producto") != null && resultSet.getObject("costo_inventario") != null) {
        	acum.setCostoProducto(resultSet.getDouble("costo_producto"));
        	acum.setCostoInventario(resultSet.getDouble("costo_inventario"));
	        acum.setPrecio1(resultSet.getDouble("precio1"));
	        acum.setPrecio2(resultSet.getDouble("precio2"));
	        acum.setPrecio3(resultSet.getDouble("precio3"));
	        acum.setPrecio4(resultSet.getDouble("precio4"));
	        acum.setPrecio5(resultSet.getDouble("precio5"));
        } else {
        	acum.setCostoProducto(0);
        	acum.setCostoInventario(0);
	        acum.setPrecio1(0);
	        acum.setPrecio2(0);
	        acum.setPrecio3(0);
	        acum.setPrecio4(0);
	        acum.setPrecio5(0);
        }
    	
        return acum;
    }
    
    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Producto cc) {
        return new Object[] {
            cc.getIdProducto(), cc.getNombre(), cc.getIdTipoProducto(), 
            cc.getIdUnidad(), cc.getFechaAlta(), cc.getIdProveedor(), 
            cc.getPiezas(), cc.getCosto(), cc.getPrecio1(), cc.getPrecio2(), 
            cc.getPrecio3(), cc.getPrecio4(), cc.getPrecio5()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Producto cc) {
        return new Object[] {
            cc.getNombre(), cc.getIdTipoProducto(), cc.getIdUnidad(), 
            cc.getFechaAlta(), cc.getCosto(), cc.getPrecio1(), cc.getPrecio2(), 
            cc.getPrecio3(), cc.getPrecio4(), cc.getPrecio5(), 
            cc.getIdProducto(), cc.getIdProveedor()
        };
    }
}
