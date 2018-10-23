package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Remision;

/**
 * Clase para mapear periodos.
 */
public class RemisionMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Remision mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
        Remision cc = new Remision();
        cc.setIdRemision(resultSet.getLong("remision"));
    	cc.setIdCliente(resultSet.getInt("cliente"));
    	cc.setNombreCliente(resultSet.getString("nombre_cliente"));
    	cc.setIdProveedor(resultSet.getInt("id_proveedor"));
    	cc.setNombreProv(resultSet.getString("nombre_prov"));
    	cc.setIdProducto(resultSet.getString("id_producto"));
    	cc.setNombreProducto(resultSet.getString("nombre_producto"));
    	cc.setFechaEntrada(resultSet.getDate("fecha_entrada"));
    	cc.setHoraEntrada(resultSet.getTime("hora_entrada"));
    	cc.setFechaSalida(resultSet.getDate("fecha_salida"));
    	cc.setHoraSalida(resultSet.getTime("hora_salida"));    	
    	cc.setCantidad(resultSet.getDouble("cantidad"));
    	cc.setIdInventario(resultSet.getString("id_inventario"));    	
    	cc.setPrecioUnitario(resultSet.getDouble("precio"));
    	cc.setPrecioTotal(resultSet.getDouble("precioTotal"));
        return cc;
    }
    
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Remision mapRowCliente(final ResultSet resultSet, final int row)
        throws SQLException {
        Remision cc = new Remision();
        cc.setIdCliente(resultSet.getInt("id_cliente"));
        cc.setNombreCliente(resultSet.getString("nombre_cliente"));
    	return cc;
    }
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Remision mapRowNumero(final ResultSet resultSet, final int row)
        throws SQLException {
        Remision cc = new Remision();
        cc.setIdRemision(resultSet.getInt("remision"));
        cc.setNombreCliente(resultSet.getString("nombre_cliente"));
    	return cc;
    }
}
