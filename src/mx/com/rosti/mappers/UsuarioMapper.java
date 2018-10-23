package mx.com.rosti.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.rosti.to.Usuario;

/**
 * Clase para mapear periodos.
 */
public class UsuarioMapper {
    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Usuario mapRow(final ResultSet resultSet, final int row)
        throws SQLException {
    	Usuario cc = new Usuario();
        cc.setIdUsuario(resultSet.getString("id_usuario"));
        cc.setNombre(resultSet.getString("nombre"));
        cc.setIdRol(resultSet.getInt("id_rol"));
        cc.setPassword(resultSet.getString("password"));
        cc.setCorreoElectronico(resultSet.getString("correo_electronico"));
        cc.setTelefono(resultSet.getString("telefono"));
        return cc;
    }

    /**
     * Metodo para mapear atributos.
     * @param  resultSet       ResultSet
     * @param  row             Fila
     * @throws SQLException    Excepcion
     * @return Periodo
     */
    public Usuario mapRowRolDescrip(final ResultSet resultSet, final int row)
        throws SQLException {
    	Usuario cc = new Usuario();
        cc.setIdUsuario(resultSet.getString("id_usuario"));
        cc.setNombre(resultSet.getString("nombre"));
        cc.setIdRol(resultSet.getInt("id_rol"));
        cc.setDescRol(resultSet.getString("rol_descripcion"));
        cc.setPassword(resultSet.getString("password"));
        cc.setCorreoElectronico(resultSet.getString("correo_electronico"));
        cc.setTelefono(resultSet.getString("telefono"));
        return cc;
    }

    
    
    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributes(final Usuario cc) {
        return new Object[] {
            cc.getIdUsuario(), cc.getNombre(), cc.getIdRol(), 
            cc.getPassword(), cc.getCorreoElectronico(), cc.getTelefono()
        };
    }

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final Usuario cc) {
        return new Object[] {
            cc.getNombre(), cc.getIdRol(), cc.getPassword(), 
            cc.getCorreoElectronico(), cc.getTelefono(), cc.getIdUsuario()
        };
    }
}
