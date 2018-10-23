package mx.com.rosti.catalogoi;

import java.util.List;

/**
 * Interfaz para definir las operaciones que se realizaran con los catalogos
 * generales.
 */
public interface ConsultasDAO {
    /**
     * Interfaz encargada de eliminar un registro.
     * @param  pObj  Objeto a ser eliminado
     * @return int  Resultado de la operación
     */
    int delete(final Object pObj);
    /**
     * Interfaz encargada de insertar un registro.
     * @param   pObj   Objeto a ser insertado
     * @return  int   Resultado de la operación
     */
    int insert(final Object pObj);
    /**
     * Interfaz encargada de actualizar un registro.
     * @param  pObj  Objecto a ser actualizado
     * @return int  Resultado de la operación
     */
    int update(final Object pObj);
    /**
     * Interfaz para consultar todos los registros
     * de una tabla en particular.
     * @return List  Regresa una lista de objectos
     */
    List<?> consultar();
    /**
     * Interfaz para consultar todos los registros
     * de una tabla en particular regresando.
     * una Lista de SelectItems
     * @return  List  Lista de SelectItems
     */
    List<?> consultarSelectItem();
    /**
     * Interfaz para consultar ciertos registros
     * definidos en los objetos que son pasados
     * como parametros.
     * @param   pValues  Arreglo de objetos a ser consultados
     * @return  List    Lista de objetos
     */
    List<?> consultarRango(final Object[] pValues);
    /**
     * Interfaz para consultar a un objecto en particular
     * con su Id.
     * @param  pObj     Objeto a ser consultado
     * @return Object  Regresa un objeto con los parametros
     *                 tomados de la consulta a la BD
     */
    Object consultarEspecifica(final Object pObj);
}
