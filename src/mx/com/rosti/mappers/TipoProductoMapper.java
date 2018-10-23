package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.TipoProducto;

/**
 * Clase para mapear periodos.
 */
public class TipoProductoMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public TipoProducto mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	TipoProducto cc = new TipoProducto();
        cc.setIdTipoProducto(resultSet.getInt("id_tipo_producto"));
        cc.setNombre(resultSet.getString("nombre"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final TipoProducto cc) {
        return new Object[] {
            cc.getIdTipoProducto(), cc.getNombre()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final TipoProducto cc) {
        return new Object[] {
            cc.getNombre(), cc.getIdTipoProducto()
        };
    }
}
