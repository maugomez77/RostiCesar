package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Clientes;

/**
 * Clase para mapear periodos.
 */
public class ClientesMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Clientes mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Clientes cc = new Clientes();
        cc.setIdCliente(resultSet.getInt("id_cliente"));
        cc.setNombre(resultSet.getString("nombre"));
        cc.setPrecioPreferente(resultSet.getInt("precio_preferente"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Clientes cc) {
        return new Object[] {
            cc.getIdCliente(), cc.getNombre(), cc.getPrecioPreferente()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Clientes cc) {
        return new Object[] {
            cc.getNombre(), cc.getPrecioPreferente(), cc.getIdCliente() 
        };
    }
}
