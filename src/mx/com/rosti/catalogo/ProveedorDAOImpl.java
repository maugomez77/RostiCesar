package mx.com.rosti.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mx.com.rosti.bd.AccesoComun;
import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.catalogoi.ConsultasDAO;
import mx.com.rosti.mappers.ProveedorMapper;
import mx.com.rosti.to.Proveedor;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para accesar al catalogo de periodos.
 */
public class ProveedorDAOImpl extends Conexiones implements ConsultasDAO {
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
    private ProveedorMapper pMAPPER = new ProveedorMapper();
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
    public List<Proveedor> consultar() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Proveedor> list = new ArrayList<Proveedor>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(accesoComun.getFindAll());
            rs = ps.executeQuery();
            while (rs.next()) {
            	Proveedor cl = pMAPPER.mapRow(rs, rs.getRow());
                list.add(cl);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
    
	public List<Proveedor> consultarProvUnidades(String sql) {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Proveedor> list = new ArrayList<Proveedor>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
            	Proveedor cl = pMAPPER.mapRow(rs, rs.getRow());
                list.add(cl);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
    
    /**
     * Metodo para realizar una consulta especifica.
     * @param  pObj     Objeto que trae ciertos campos para 
     *                  realizar una consulta
     * @return Object   Objeto con nuevos campos
     */
    public Object consultarEspecifica(final Object pObj) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Proveedor cc = (Proveedor) pObj;
        try {
            conn = takeConnection(local, dataSource);
            Utilerias.debug(this, "Query: " + accesoComun.getFindUnique());
            ps = conn.prepareStatement(accesoComun.getFindUnique());
            ps.setInt(1, cc.getIdProveedor());
            rs = ps.executeQuery();
            while (rs.next()) {
                cc = pMAPPER.mapRow(rs, rs.getRow());
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally {
            closeResources(rs, ps, conn);
        }
        return cc;
    }
    /**
     * Metodo para realizar una consulta por rango de valores.
     * @param  pValues   Valroes
     * @return List      Lista
     */
    public List<Proveedor> consultarRango(final Object[] pValues) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Proveedor> list = new ArrayList<Proveedor>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(accesoComun.getFindRange());
            setAttributes(ps, pValues);
            rs = ps.executeQuery();
            while (rs.next()) {
            	Proveedor cc = pMAPPER.mapRow(rs, rs.getRow());
                list.add(cc);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
    /** 
     * Metodo para eliminar un registro. 
     * @param  pObj     Objeto a ser eliminado
     * @return int      Resultado de la eliminación
     */
    public int delete(final Object pObj) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = -1;
        Proveedor cc = (Proveedor) pObj;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(accesoComun.getSqlDelete());
            ps.setInt(1, cc.getIdProveedor());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResourcesPrepared(ps, conn);
        }
        return result;
    }
    /**
     * Método para realizar la inserción de un objeto.
     * @param  pObj     Objeto a insertar
     * @return int      Resultado de la inserción
     */
    public int insert(final Object pObj) {
        Connection conn = null; 
        PreparedStatement ps = null;
        int result = -1;
        Proveedor cc = (Proveedor) pObj;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(accesoComun.getSqlInsert());
            setAttributes(ps, pMAPPER.mapAttributes(cc));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally {
            closeResourcesPrepared(ps, conn);
        }
        return result; 
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
        Proveedor cc = (Proveedor) pObj;
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
    public int dmlOperations(final String operacion, final Proveedor cc) {
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
