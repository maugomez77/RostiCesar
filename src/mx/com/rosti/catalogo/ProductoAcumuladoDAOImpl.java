package mx.com.rosti.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import mx.com.rosti.bd.AccesoComun;
import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.catalogoi.ConsultasDAO;
import mx.com.rosti.mappers.ProductoAcumuladoMapper;
import mx.com.rosti.to.ProductoAcumulado;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para accesar al catalogo de periodos.
 */
public class ProductoAcumuladoDAOImpl extends Conexiones implements ConsultasDAO {
    /**
     * Variable para tomar el dataSource local o web booleana.
     */
    private boolean     local;
    /**
     * variable del dataSource.
     */
    private DataSource  dataSource;
    /**
     * variable que contiene acceso a bd.
     */
    private AccesoComun accesoComun;
    /**
     * Mapeador de rows para cuando se traen los datos de un query.
     */
    private ProductoAcumuladoMapper pMAPPER = new ProductoAcumuladoMapper();
    /**
     * Metodo para setear la variable local.
     * @param bLocal   Local
     */
    public void setLocal(final boolean bLocal) { 
        this.local = bLocal;
    }
    /**
     * Metodo para setear el dataSource.
     * @param pDS   DataSource
     */
    public void setDataSource(final DataSource pDS) {
        this.dataSource = pDS;
    }
    /**
     * Metodo para setear el acceso Comun.
     * @param pAC  Acceso Comun
     */
    public void setAccesoComun(final AccesoComun pAC) {
        this.accesoComun = pAC;
    }
    /**
     * Metodo para realizar consultas.
     * @return List 
     */
    public List<ProductoAcumulado> consultar() {
        return null;
    }
    /**
     * Metodo para realizar una consulta especifica.
     * @param  pObj     Objeto que trae ciertos campos para 
     *                  realizar una consulta
     * @return Object   Objeto con nuevos campos
     */
    public Object consultarEspecifica(final Object pObj) {
        return null;
    }
    /**
     * Metodo para realizar una consulta por rango de valores.
     * @param  pValues   Valroes
     * @return List      Lista
     */
    public List<ProductoAcumulado> consultarRango(final Object[] pValues) {
        return null;
    }
    /** 
     * Metodo para eliminar un registro. 
     * @param  pObj     Objeto a ser eliminado
     * @return int      Resultado de la eliminación
     */
    public int delete(final Object pObj) {
        return -1;
    }
    /**
     * Método para realizar la inserción de un objeto.
     * @param  pObj     Objeto a insertar
     * @return int      Resultado de la inserción
     */
    public int insert(final Object pObj) {
        return -1; 
    }
    /**
     * Metodo para realizar actualización de un objeto.
     * @param  pObj   Objeto a ser actualizado
     * @return int      Resultado de la actualización
     */
    public int update(final Object pObj) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = -1;
        ProductoAcumulado cc = (ProductoAcumulado) pObj;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(accesoComun.getSqlUpdate());
            setAttributes(ps, pMAPPER.mapAttributesUpdate(cc));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally {
            closeResourcesPrepared(ps, conn);
        }
        return result;
    }
    /**
     * Metodo para realizar operaciones de DML.
     * @param  operacion   Operación a realizar
     * @param  cc          Objeto 
     * @return int         Resultado de la operación
     */
    public int dmlOperations(final String operacion, final ProductoAcumulado cc) {
        int result = -1;
        if (operacion.compareTo("insert") == 0) {
            result = insert(cc);
        }    
        if (operacion.compareTo("update") == 0) {
            result = update(cc);
        }    
        if (operacion.compareTo("delete") == 0) {
            result = delete(cc);
        }    
        return result;
    }
	public List<?> consultarSelectItem() {
		// TODO Auto-generated method stub
		return null;
	}
}
