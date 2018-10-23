package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Proveedor;

/**
 * Clase para mapear periodos.
 */
public class ProveedorMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Proveedor mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Proveedor cc = new Proveedor();
        cc.setIdProveedor(resultSet.getInt("id_proveedor"));
        cc.setNombre(resultSet.getString("nombre"));
        cc.setDescripcion(resultSet.getString("descripcion"));
        cc.setGeneraCodigo(resultSet.getString("genera_codigo"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Proveedor cc) {
        return new Object[] {
            cc.getIdProveedor(), cc.getNombre(), cc.getDescripcion(), cc.getGeneraCodigo()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Proveedor cc) {
        return new Object[] {
            cc.getNombre(), cc.getDescripcion(), cc.getGeneraCodigo(), cc.getIdProveedor()
        };
    }
}
