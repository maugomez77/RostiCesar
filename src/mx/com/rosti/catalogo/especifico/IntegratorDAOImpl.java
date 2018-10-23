package mx.com.rosti.catalogo.especifico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.util.Utilerias;


/**
 * Clase para realizar funciones genericas con la base de datos
 * como obtener ids para las tablas, entre otras cosas.
 */
public class IntegratorDAOImpl extends Conexiones {
	/*
	 * 
	 * SOLO LOS PROVEEDORES, TIPO_PRODUCTO, UNIDAD
	 SELECT DISTINCT id_proveedor, id_tipo_producto, id_unidad
	 FROM (

	 SELECT p.id_proveedor, tip.id_tipo_producto, u.id_unidad, pp.id_producto
	 FROM proveedor p, tipo_producto tip, unidades u, producto pp
	 WHERE pp.id_proveedor = p.id_proveedor
	 AND pp.id_tipo_producto = tip.id_tipo_producto
	 AND pp.id_unidad = u.id_unidad
	 )jaja */
	 
	/*
	select p.id_proveedor, tip.id_tipo_producto, u.id_unidad, pp.id_producto
	  from proveedor p, tipo_producto tip, unidades u, producto pp
	  where pp.id_proveedor = p.id_proveedor
	    and pp.id_tipo_producto = tip.id_tipo_producto
	    and pp.id_unidad = u.id_unidad
	*/    
    /**
     * Variable para definir la tabla de las secuencias.
     */
    private static final String SEQUENCE = "secuencias_producto";
    private static final String SEQUENCE_VARIAS = "secuencias_varias";
    /**
     * Variable para realizar el select de una secuencia.
     */
    private static final String SQL_SELECT = "" 
        + " select max(id_sec)+1 id " 
        + "  from  " + SEQUENCE + " sto " 
        + " where sto.id_producto = ? "
        + "   and sto.id_proveedor = ? " 
        + "   and sto.id_tipo_producto = ? "
        + "   and sto.id_unidad = ? ";
    
    private static final String SQL_INSERT_SEQUENCE = ""
        + " insert into secuencias_producto (id_proveedor, id_tipo_producto, id_unidad, id_producto, id_sec) "
        + "  values (?,?,?,?,?) ";
    
    private static final String SQL_SELECT_MAX = "" 
        + " select max(id_inventario)+1 id " 
        + "  from  inventario sto, producto pro " 
        + " where sto.id_producto = ? "
        + "   and sto.id_proveedor = ? " 
        + "   and pro.id_tipo_producto = ? "
        + "   and pro.id_unidad = ? "
        + "   and pro.id_producto = sto.id_producto "
        + "   and pro.id_proveedor = sto.id_proveedor ";
    
    /**
     * Variable para realizar el select de una secuencia.
     */
    private static final String SQL_SELECT_VARIAS = "" 
        + " select max(id_sec)+1 id " 
        + "  from  " + SEQUENCE_VARIAS + " sto " 
        + " where sto.nombre = ? ";        
    
    /**
     * Variable para actualizar los valores de las secuencias.
     */
    private static final String SQL_UPDATE_VARIAS = "" 
        + " update " + SEQUENCE_VARIAS 
        + " set id_sec = ? " 
        + " where nombre = ? ";
    
    /**
     * Variable para actualizar los valores de las secuencias.
     */
    private static final String SQL_UPDATE = "" 
        + " update " + SEQUENCE 
        + " set id_sec = ? " 
        + " where id_producto = ? "
        + "   and id_proveedor = ? " 
        + "   and id_tipo_producto = ? " 
        + "   and id_unidad = ? ";
    /**
     * Variable para identificar de donde viene el dataSource, si local o web.
     */
    private boolean     local;
    /**
     * variable referente al dataSource local.
     */
    private DataSource  dataSource;
    /**
     * Seteo de la variable booleana para el dataSource.
     * @param bLocal    Variable local
     */
    public void setLocal(final boolean bLocal) { 
        this.local = bLocal;
    }
    /**
     * Seteo de la variable del dataSource.
     * @param pDS  DataSource
     */
    public void setDataSource(final DataSource pDS) {
        this.dataSource = pDS;
    }
    
    /**
     * Metodo para obtener ids para ser usados en las tablas.
     * @param  table   Nombre de la tabla a obtener el id
     * @return long
     */
    public long getId(String prod, String prov, String unidad, String tipoProducto, boolean putTime) { 
        long idGeneric = -1;
        Connection conn = takeConnection(local, dataSource);
        long timeMilliseconds = -1; 
        if (putTime) {
        	getTimeMilliseconds();      
        }
        
        idGeneric = getIdDatos(conn, prod, prov, unidad, tipoProducto, timeMilliseconds, putTime);
        return idGeneric; 
    }
    
    /**
     * Metodo para obtener ids para ser usados en las tablas.
     * @param  table   Nombre de la tabla a obtener el id
     * @return long
     */
    public long getMaxId(String prod, String prov, String unidad, String tipoProducto, boolean putTime) { 
        long idGeneric = -1;
        Connection conn = takeConnection(local, dataSource);
        long timeMilliseconds = -1; 
        if (putTime) {
        	getTimeMilliseconds();      
        }
        
        idGeneric = getMaxIdDatos(conn, prod, prov, unidad, tipoProducto, timeMilliseconds, putTime);
        return idGeneric; 
    }
    
    public int insertMaxId(final String prod, int prov, int unidad, final int tipoProducto) {
    	Connection conn = takeConnection(local, dataSource);
    	return insertMaxId(conn, prod, prov, unidad, tipoProducto);    	
    }
    public long getIdVarios(String name, boolean putTime) { 
    	long idGeneric = -1;
    	long timeMilliseconds = -1; 
        if (putTime) {
        	getTimeMilliseconds();      
        }
        Connection conn = takeConnection(local, dataSource);
        idGeneric = getIdVarios(conn, name, timeMilliseconds, putTime);
    	return idGeneric;
    }
    /**
     * Obtiene la hora en formato long. 
     * @return long
     */
    private long getTimeMilliseconds() {
        java.util.Date d = new java.util.Date();
        return d.getTime();
    }
    /**
     * Metodo para obtener un id referente a la tabla de datos pago.
     * @param  conn              Conexion hacia la bd
     * @param  table             Tabla de donde obtener el id
     * @param  timeMilliseconds  Valor de milisegundos 
     *                           previamente obtenido referente a la hora
     * @return long 
     */
    private synchronized long getIdVarios(Connection conn, 
                                         String name,
                                         long timeMilliseconds, 
                                         boolean putTime) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        long idLong = -1;
        try {
        	Utilerias.debug(this, "Name: " + name);
        	ps = conn.prepareStatement(IntegratorDAOImpl.SQL_SELECT_VARIAS);
            ps.setString(1, name);
            rs = ps.executeQuery();            
            long dbTake = -1;
            StringBuffer sb = new StringBuffer();
            if (rs.next()) {
                dbTake = rs.getLong("id");
                if (putTime) {
                	sb.append(timeMilliseconds + "" + dbTake);
                } else {
                	sb.append(dbTake);
                }
            }
            idLong = Long.parseLong(sb.toString());
            ps = conn.prepareStatement(IntegratorDAOImpl.SQL_UPDATE_VARIAS);
            ps.setLong(1, dbTake);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return idLong;
    }
    /**
     * Metodo para obtener un id referente a la tabla de datos pago.
     * @param  conn              Conexion hacia la bd
     * @param  table             Tabla de donde obtener el id
     * @param  timeMilliseconds  Valor de milisegundos 
     *                           previamente obtenido referente a la hora
     * @return long 
     */
    private synchronized int insertMaxId(Connection conn, final String prod, 
        final int prov, final int unidad, final int tipoProducto) {
    	
        PreparedStatement ps = null;
        int result = -1;
        try {
        	Utilerias.debug(this, "Prod: " + prod);
        	Utilerias.debug(this, "Prov: " + prov);
        	Utilerias.debug(this, "Tipo Prod: " + tipoProducto);
        	Utilerias.debug(this, "Unidad: " + unidad);
            ps = conn.prepareStatement(IntegratorDAOImpl.SQL_INSERT_SEQUENCE);
            ps.setInt(1, prov);
            ps.setInt(2, tipoProducto);
            ps.setInt(3, unidad);
            ps.setString(4, prod);
            ps.setInt(5, 1);
            result = ps.executeUpdate();
            
            if (result <= 0) {
            	getIdDatos(conn, prod, Utilerias.intToStr(prov), Utilerias.intToStr(unidad), Utilerias.intToStr(tipoProducto), 0, false);
            }            
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
        	closeResourcesPrepared(ps, conn);
        }
        
        return result;
    }
    /**
     * Metodo para obtener un id referente a la tabla de datos pago.
     * @param  conn              Conexion hacia la bd
     * @param  table             Tabla de donde obtener el id
     * @param  timeMilliseconds  Valor de milisegundos 
     *                           previamente obtenido referente a la hora
     * @return long 
     */
    private synchronized long getIdDatos(Connection conn, 
                                         String prod,
                                         String prov, 
                                         String unidad, 
                                         String tipoProducto,
                                         long timeMilliseconds, 
                                         boolean putTime) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        long idLong = -1;
        try {
        	Utilerias.debug(this, "Prod: " + prod);
        	Utilerias.debug(this, "Prov: " + prov);
        	Utilerias.debug(this, "Tipo Prod: " + tipoProducto);
        	Utilerias.debug(this, "Unidad: " + unidad);
            ps = conn.prepareStatement(IntegratorDAOImpl.SQL_SELECT);
            ps.setString(1, prod);
            ps.setInt(2, Utilerias.strToInt(prov));
            ps.setInt(3, Utilerias.strToInt(tipoProducto));
            ps.setInt(4, Utilerias.strToInt(unidad));
            rs = ps.executeQuery();            
            long dbTake = -1;
            StringBuffer sb = new StringBuffer();
            if (rs.next()) {
                dbTake = rs.getLong("id");
                if (putTime) {
                	sb.append(timeMilliseconds + "" + dbTake);
                } else {
                	sb.append(dbTake);
                }
            }
            idLong = Long.parseLong(sb.toString());
            ps = conn.prepareStatement(IntegratorDAOImpl.SQL_UPDATE);
            ps.setLong(1, dbTake);
            ps.setString(2, prod);
            ps.setInt(3, Utilerias.strToInt(prov));
            ps.setInt(4, Utilerias.strToInt(tipoProducto));
            ps.setInt(5, Utilerias.strToInt(unidad));
            ps.executeUpdate();
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return idLong;
    }
    
    /**
     * Metodo para obtener un id referente a la tabla de datos pago.
     * @param  conn              Conexion hacia la bd
     * @param  table             Tabla de donde obtener el id
     * @param  timeMilliseconds  Valor de milisegundos 
     *                           previamente obtenido referente a la hora
     * @return long 
     */
    private synchronized long getMaxIdDatos(Connection conn, 
                                         String prod,
                                         String prov, 
                                         String unidad, 
                                         String tipoProducto,
                                         long timeMilliseconds, 
                                         boolean putTime) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        long idLong = -1;
        try {
        	Utilerias.debug(this, "Prod: " + prod);
        	Utilerias.debug(this, "Prov: " + prov);
        	Utilerias.debug(this, "Tipo Prod: " + tipoProducto);
        	Utilerias.debug(this, "Unidad: " + unidad);
            ps = conn.prepareStatement(IntegratorDAOImpl.SQL_SELECT_MAX);
            ps.setString(1, prod);
            ps.setInt(2, Utilerias.strToInt(prov));
            ps.setInt(3, Utilerias.strToInt(tipoProducto));
            ps.setInt(4, Utilerias.strToInt(unidad));
            rs = ps.executeQuery();            
            long dbTake = -1;
            StringBuffer sb = new StringBuffer();
            if (rs.next()) {
                dbTake = rs.getLong("id");
                if (putTime) {
                	sb.append(timeMilliseconds + "" + dbTake);
                } else {
                	sb.append(dbTake);
                }
            }
            idLong = Long.parseLong(sb.toString());
            ps = conn.prepareStatement(IntegratorDAOImpl.SQL_UPDATE);
            ps.setLong(1, dbTake);
            ps.setString(2, prod);
            ps.setInt(3, Utilerias.strToInt(prov));
            ps.setInt(4, Utilerias.strToInt(tipoProducto));
            ps.setInt(5, Utilerias.strToInt(unidad));
            ps.executeUpdate();
        } catch (SQLException e) { 
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return idLong;
    }
    
    /**
     * Metodo para realizar pruebas locales a la clase.
     * @param args   Argumentos de consola
     */
    public static void main(final String[] args) {
        IntegratorDAOImpl est = (IntegratorDAOImpl) 
                                  ObjectFactory.getObject("IntegratorDAO");
        est.setLocal(true);
        Utilerias.debug(est, "INICIO");
    }
}
