package mx.com.rosti.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import mx.com.rosti.context.ContextDS;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para obtener conexiones a la base de datos y cerrar los recursos
 * relacionados a esta.
 */
public class Conexiones {
    /**
     * Metodo que regresa una conexion a la base de datos.
     *
     * @param local        Variable para indicar si es ambiente local o en web,
     *                     para ambos se necesita el servidor corriendo para
     *                     obtener el dataSource
     * @param dataSource   El dataSource esta presente localmente en la clase
     *                     ContextDS y si es Web, es tomada de archivos de 
     *                     configuracion
     *                     definidos en los resources
     * @return Connection  Conexion a la bd
     */
    protected Connection takeConnection(final boolean local, 
                                     final DataSource dataSource) {
        Connection conn = null;  
        try {
            if (local) {
                conn = ContextDS.getConnection();
            } else {
                conn = dataSource.getConnection();
            }
        } catch (Exception e) { 
            Utilerias.error(this, e.toString());
        }        
        return conn;
    }

    /**
     * Metodo para cerrar una conexion a la bd.
     * @param conn   Conexión a la bd
     */
    protected void closeConnection(final Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }
    }

    /**
     * Metodo para cerrar un statement y una conexion a la bd.
     * @param stmt   Statement a la bd
     * @param conn   Conexion a la bd
     */
    protected void closeStatement(final Statement stmt, final Connection conn) {
        try {
            stmt.close();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }
        closeConnection(conn);
    }

    /**
     * Metodo para cerrar un resultSet, Statement y Conexion a la bd.
     * @param rs    ResultSet a ser cerrado
     * @param stmt  Statement a ser cerrado
     * @param conn  Conexion a ser cerrada
     */
    protected void closeResources(final ResultSet rs, final Statement stmt, 
                               final Connection conn) {
        try {
            rs.close();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }
        closeStatement(stmt, conn);
    }

    /**
     * Metodo para cerrar un PreparedStatement y Conexion a la BD.
     * @param ps    PreparedStatement a ser cerrado
     * @param conn  Conexion a ser cerrada
     */
    protected void closeResourcesPrepared(final PreparedStatement ps, 
                                       final Connection conn) {
        try {
            ps.close();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }
        closeConnection(conn);
    }

    /**
     * Metodo para cerrar un ResultSet, PreparedStatement y Conexion a la BD.
     * @param rs   ResultSet a ser cerrado
     * @param ps   PreparedStatement a ser cerrado
     * @param conn Conexion a ser cerrada
     */
    protected void closeResourcesPreparedResultSet(final ResultSet rs,
                                                final PreparedStatement ps, 
                                                final Connection conn) {
        try {
            rs.close();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }
        closeResourcesPrepared(ps, conn);
    }

    /**
     * Metodo para setear atributos que se utilizan en un preparedStatement.
     * @param ps   PreparedStatement en donde se esta trabajando
     * @param obj  Arreglo de Objetos a ser seteados en el preparedStatement
     * @throws UnsupportedEncodingException 
     */
    protected void setAttributes(final PreparedStatement ps, final Object[] obj) {
        try {
        	if (obj == null) {
        		return;
        	}
            for (int i = 0; i < obj.length; i++) {
                setPreparedStatement(ps, obj[i], i + 1);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }
    }

    /**
     * Metodo para setear un valor especifico de un preparedStatement 
     * identificando el tipo de objecto del cual se trata.
     * @param  ps            PreparedStatement donde se setearan los valores
     * @param  o             Objecto a ser checado e insertado al 
     *                       PreparedStatement
     * @param  value         Valor de la columna en donde se insertara 
     *                       dentro del PreparedStatement
     * @throws SQLException  Excepción que pudiera suscitarse
     * @throws UnsupportedEncodingException 
     */
    protected void setPreparedStatement(final PreparedStatement ps, 
                                     final Object o, final int value)
        throws SQLException {
    
        if (o instanceof java.lang.String) {
        	try {
        		ps.setString(value, new String(((String) o).getBytes(), "UFT-8"));
        	} catch (Exception e) { 
        		ps.setString(value, (String) o);
        	}
            return;
        } 
    
        if (o instanceof Integer) {
            ps.setInt(value, (Integer) o);
            return;
        } 
    
        if (o instanceof Double) {
            ps.setDouble(value, (Double) o);
            return;
        } 
    
        if (o instanceof Long) {
            ps.setLong(value, (Long) o);
            return;
        } 
    
        if (o instanceof Float) {
            ps.setFloat(value, (Float) o);
            return;
        } 
    
        if (o instanceof java.sql.Date) {
            //ps.setDate(value, new java.sql.Date(Long.parseLong(dataColumn)));
            //format for the date is YYYY-MM-DD
            ps.setDate(value, (java.sql.Date) o); // .valueOf((String)o));
            return;
        } 
    
        if (o instanceof java.sql.Time) {
            ps.setTime(value, (java.sql.Time) o);
            return;
        }
    }
}
