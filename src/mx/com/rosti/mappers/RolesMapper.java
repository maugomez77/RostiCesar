package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Roles;

/**
 * Clase para mapear periodos.
 */
public class RolesMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Roles mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Roles cc = new Roles();
        cc.setIdRol(resultSet.getInt("id_rol"));
        cc.setNombreRol(resultSet.getString("nombre_rol"));
        cc.setDescripcion(resultSet.getString("descripcion"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Roles cc) {
        return new Object[] {
            cc.getIdRol(), cc.getNombreRol(), cc.getDescripcion()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Roles cc) {
        return new Object[] {
            cc.getNombreRol(), cc.getDescripcion(), cc.getIdRol()
        };
    }
}
