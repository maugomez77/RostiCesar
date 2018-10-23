package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.ReporteInventario;

/**
 * Clase para mapear periodos.
 */
public class ReporteInventarioMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public ReporteInventario mapRowEntrada(final ResultSet resultSet, final int row)
        throws SQLException {
    	ReporteInventario cc = new ReporteInventario();
        cc.setIdInventario(resultSet.getString("id_inventario"));
        cc.setCodeProd(resultSet.getString("id_producto"));
        cc.setDescCodeProducto(resultSet.getString("nombre_prod"));
        cc.setProv(resultSet.getInt("id_proveedor"));
        cc.setDescProveedor(resultSet.getString("nombre_prov"));
        cc.setTPro(resultSet.getInt("id_tipo_producto"));
        cc.setDescTipoProd(resultSet.getString("nombre_tipo_producto"));
        cc.setUnid(resultSet.getInt("id_unidad"));
        cc.setDescUnid(resultSet.getString("nombre_unidades"));
        cc.setCantidad(resultSet.getDouble("cantidad"));
        cc.setFechaEntrada(resultSet.getDate("fecha_entrada"));
        cc.setHoraEntrada(resultSet.getTime("hora_entrada"));
        cc.setEntradaCarga(resultSet.getString("entrada_carga"));
        if (resultSet.getObject("costo_inventario") != null) {
        	cc.setCostoUnitario(resultSet.getDouble("costo_inventario"));
        } else {
        	cc.setCostoUnitario(0);
        }
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public ReporteInventario mapRowSalida(final ResultSet resultSet, final int row)
        throws SQLException {
    	ReporteInventario cc = new ReporteInventario();
        cc.setIdInventario(resultSet.getString("id_inventario"));
        cc.setCodeProd(resultSet.getString("id_producto"));
        cc.setDescCodeProducto(resultSet.getString("nombre_prod"));
        cc.setProv(resultSet.getInt("id_proveedor"));
        cc.setDescProveedor(resultSet.getString("nombre_prov"));
        cc.setTPro(resultSet.getInt("id_tipo_producto"));
        cc.setDescTipoProd(resultSet.getString("nombre_tipo_producto"));
        cc.setUnid(resultSet.getInt("id_unidad"));
        cc.setDescUnid(resultSet.getString("nombre_unidades"));
        cc.setCantidad(resultSet.getDouble("cantidad"));
        
        try { 
        	cc.setFechaEntrada(resultSet.getDate("fecha_entrada"));
        } catch (Exception e) { }
        
        cc.setFechaSalida(resultSet.getDate("fecha_salida"));
        try { 
        	cc.setHoraEntrada(resultSet.getTime("hora_entrada"));
        } catch (Exception e) { 
        	
        }
        
        cc.setHoraSalida(resultSet.getTime("hora_salida"));
        cc.setRemision(resultSet.getString("remision"));
        cc.setIdCliente(resultSet.getInt("id_cliente"));
        cc.setCliente(resultSet.getString("nombre_cliente"));
        cc.setPrecioUnitario(resultSet.getDouble("precio"));
        cc.setSalidaCarga(resultSet.getString("salida_carga"));
        
        if (resultSet.getObject("costo_inventario") != null) {
        	cc.setCostoUnitario(resultSet.getDouble("costo_inventario"));
        } else {
        	cc.setCostoUnitario(0);
        }        
        
        return cc;
    }
}
