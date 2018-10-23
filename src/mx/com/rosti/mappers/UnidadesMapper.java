package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Unidades;

/**
 * Clase para mapear periodos.
 */
public class UnidadesMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Unidades mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Unidades cc = new Unidades();
        cc.setIdUnidad(resultSet.getInt("id_unidad"));
        cc.setNombre(resultSet.getString("nombre"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Unidades cc) {
        return new Object[] {
            cc.getIdUnidad(), cc.getNombre()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Unidades cc) {
        return new Object[] {
            cc.getNombre(), cc.getIdUnidad()
        };
    }
}
