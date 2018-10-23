package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Inventario;

/**
 * Clase para mapear periodos.
 */
public class InventarioMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Inventario mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Inventario cc = new Inventario();
        cc.setIdInventario(resultSet.getString("id_inventario"));
        cc.setCantidad(resultSet.getDouble("cantidad"));
        cc.setEntradaSalida(resultSet.getString("entrada_salida"));
        cc.setFechaEntrada(resultSet.getDate("fecha_entrada"));
        cc.setFechaSalida(resultSet.getDate("fecha_salida"));
        cc.setHoraEntrada(resultSet.getTime("hora_entrada"));
        cc.setHoraSalida(resultSet.getTime("hora_salida"));
        cc.setIdProducto(resultSet.getString("id_producto"));
        cc.setIdProveedor(resultSet.getInt("id_proveedor"));
        cc.setIdEntradaCarga(resultSet.getLong("id_entrada_carga"));
        cc.setIdSalidaCarga(resultSet.getLong("id_salida_carga"));
        cc.setRemision(resultSet.getLong("remision"));
        cc.setIdCliente(resultSet.getInt("id_cliente"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Inventario cc) {
        return new Object[] {
            cc.getIdInventario(), cc.getCantidad(), cc.getEntradaSalida(), 
            cc.getFechaEntrada(), cc.getFechaSalida(), cc.getHoraEntrada(), cc.getHoraSalida(), cc.getIdProducto(), cc.getIdProveedor(), 
            cc.getIdEntradaCarga(), cc.getIdSalidaCarga(), cc.getRemision(), cc.getIdCliente()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Inventario cc) {
        return new Object[] {
            cc.getCantidad(), cc.getEntradaSalida(), cc.getIdSalidaCarga(), cc.getRemision(), 
            cc.getIdCliente(), cc.getHoraSalida(), cc.getFechaSalida(), cc.getIdInventario(), cc.getIdProducto(), cc.getIdProveedor()            
        };
    }
}
