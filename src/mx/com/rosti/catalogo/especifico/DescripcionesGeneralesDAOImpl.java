package mx.com.rosti.catalogo.especifico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para realizar funciones en descripciones generales.
 */
public class DescripcionesGeneralesDAOImpl extends Conexiones {
    /**
     * Variable para tomar el dataSource local o web booleana.
     */
    private boolean     local;
    /**
     * variable del dataSource.
     */
    private DataSource  dataSource;
    /**
     * variable para guardar la consulta a ejecutar.
     */
    private String      sql;
    /**
     * variable para guardar la columna que se traera de la consulta del query.
     */
    private String      sqlColumn;
    /**
     * Seteo de la variable sql.
     * @param pSql   Parametro de entrada.
    */
    public void setSql(final String pSql) { 
        this.sql = pSql;
    }
    public String getSql() {
    	return this.sql;
    }
    /**
     * Seteo de la variable local.
     * @param bLocal   Local
     */
    public void setLocal(final boolean bLocal) { 
        this.local = bLocal;
    }
    /**
     * Seteo de la variable dataSource.
     * @param pDS    DataSource
     */
    public void setDataSource(final DataSource pDS) {
        this.dataSource = pDS;
    }
    /**
     * Seteo de la variable de sqlColumn.
     * @param psqlC    Parametro
     */
    public void setSqlColumn(final String psqlC) { 
        this.sqlColumn = psqlC;
    }
    /**
     * Metodo para ejecutar una consulta de un impuesto.
     * @param  obj           Objeto para el query
     * @return StringBuffer
     */
    public StringBuffer getImpuestoDescripcion(final Object[] obj) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer value = new StringBuffer();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            setAttributes(ps, obj);
            rs = ps.executeQuery();
            if (rs.next()) {
                value.append(rs.getString(sqlColumn));
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Metodo para ejecutar una consulta de un impuesto.
     * @param  obj           Objeto para el query
     * @return StringBuffer
     */
    public int executeStatement(final Object[] obj) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = -1;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            setAttributes(ps, obj);
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            Utilerias.error(this, e.toString() + " sql: " + sql);
        } finally { 
        	closeResourcesPrepared(ps, conn);
        }
        return result;
    }
    /**
     * Ejecuta un query y regresa su valor en int.
     * @return int
     */
    public int executeQueryInt() { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;  
        int value = -1;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);            
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getInt(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y regresa su valor en int.
     * @param  obj   Si se desea ejecutar con parametros en query
     * @return int
     */
    public int executeQueryInt(final Object[] obj) { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;  
        int value = -1;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql); 
            setAttributes(ps, obj);
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getInt(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y regresa su valor en double.
     * @return double
     */
    public double executeQueryDouble() { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;  
        double value = -1;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);            
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getDouble(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y regresa su valor en double.
     * @param  obj   Si se desea ejecutar con parametros en query
     * @return double
     */
    public double executeQueryDouble(final Object[] obj) { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;  
        double value = -1;
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql); 
            setAttributes(ps, obj);
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getDouble(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y regresa su valor en String.
     * @return String
     */
    public String executeQueryString() { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String value = "";
        try {
            conn = takeConnection(local, dataSource);
            Utilerias.debug(this, "Sql: " + sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getString(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y regresa su valor en long.
     * @return long
     */
    public long executeQueryLong() { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long value = -1;
        try {
            conn = takeConnection(local, dataSource);
            Utilerias.debug(this, "Sql: " + sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getLong(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y regresa su valor en String.
     * @param  obj       Arreglo de objetos a setear en query
     * @return String
     */
    public String executeQueryString(final Object[] obj) { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String value = "";
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            setAttributes(ps, obj);
            rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getString(sqlColumn);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return value;
    }
    /**
     * Ejecuta un query y trae una lista referente de estos.
     * @return  List 
     */
    public List<Integer> executeQueryListInteger() { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(sqlColumn));
            } 
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
    /**
     * Ejecuta un query y trae una lista referente de estos.
     * @return  List 
     */
    public List<String> executeQueryListString() { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<String>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(sqlColumn));
            } 
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
    
    public List<String> executeQueryListOneRow(final String[] obj) { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<String>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);            
            //setAttributes(ps, obj);
            rs = ps.executeQuery();              
            while (rs.next()) {
            	for (int i=0; i < obj.length; i++) {
            		list.add(rs.getString(obj[i]));            		
            	}            	
            } 
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
    
    @SuppressWarnings("rawtypes")
	public List<List> executeQueryListMultipleRows(final String[] obj) { 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<List> list = new ArrayList<List>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);            
            //setAttributes(ps, obj);
            rs = ps.executeQuery();              
            while (rs.next()) {
            	
            	List<String> elementos = new ArrayList<String>();
            	
            	for (int i=0; i < obj.length; i++) {
            		elementos.add(rs.getString(obj[i]));            		
            	}            	
            	
            	list.add(elementos);
            } 
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
}
