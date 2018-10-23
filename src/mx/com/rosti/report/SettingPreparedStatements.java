package mx.com.rosti.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import mx.com.rosti.util.Utilerias;


/**
 * Clase para setear parametros para ejecutar los queries parametrizados.
 */
public class SettingPreparedStatements {
    /**
     * Metodo para setear valores al PreparedStatement.
     * @param  ps              PreparedStatement
     * @param  dataType        Tipos de Datos
     * @param  dataColumn      Tipos de Columnas
     * @throws SQLException    Excepcion de bd
     */
    public void setValues(final PreparedStatement ps,
        final ArrayList<String> dataType, final ArrayList<String> dataColumn)
        throws SQLException {
        if ((dataType == null) || (dataColumn == null) 
            || (dataType.size() <= 0) || (dataColumn.size() <= 0)) {
            return;
        }

        int size = dataType.size();

        for (int i = 0; i < size; i++) {
            setPreparedStatement(ps, dataType.get(i), dataColumn.get(i), i + 1);
        }
    }

    /**
     * Ejecuta una actualización de dml a la bd.
     * @param  con         Conexion a la BD
     * @param  ps          PreparedStatement
     * @param  dataType    Tipos de datos
     * @param  dataColumn  Tipos de columnas
     * @return int         Regresa si la actualización fue exitosa
     */
    public int executeUpdatePreparedStatement(final Connection con,
        final PreparedStatement ps, final ArrayList<String> dataType,
        final ArrayList<String> dataColumn) {
        int result = -1;

        try {
            setValues(ps, dataType, dataColumn);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }

        return result;
    }

    /**
     * Ejecuta una consulta a la bd.
     * @param  con          Conexion a la bd
     * @param  ps           PreparedStatement
     * @param  dataType     Tipos de datos
     * @param  dataColumn   Tipos de columna
     * @return ResultSet    Tuplas regresadas de la bd
     */
    public ResultSet executeQueryPreparedStatement(final Connection con,
        final PreparedStatement ps, final ArrayList<String> dataType,
        final ArrayList<String> dataColumn) {
        ResultSet rs = null;

        try {
            setValues(ps, dataType, dataColumn);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        }

        return rs;
    }

    /**
     * Seteo los campos en el preparedStatement dependiendo de su tipo.
     * @param  ps            PreparedStatement
     * @param  dataType      Tipos de datos
     * @param  dataColumn    Tipos de columna
     * @param  value         Indice en el preparedStement de campo
     * @throws SQLException  Excepcion de la bd
     */
    public void setPreparedStatement(final PreparedStatement ps,
        final String dataType, final String dataColumn, final int value)
        throws SQLException {
    
        if (dataType.equals("String")) {
            ps.setString(value, dataColumn);
            return;
        } 
        
        if (dataType.equals("Integer")) {
            ps.setInt(value, Integer.parseInt(dataColumn));
            return;
        } 
        
        if (dataType.equals("Double")) {
            ps.setDouble(value, Double.parseDouble(dataColumn));
            return;
        } 
        
        if (dataType.equals("Long")) {
            ps.setLong(value, Long.parseLong(dataColumn));
            return;
        } 
        
        if (dataType.equals("Float")) {
            ps.setFloat(value, Float.parseFloat(dataColumn));
            return;
        } 
        
        if (dataType.equals("Date")) {
            //ps.setDate(value, new java.sql.Date(Long.parseLong(dataColumn)));
            //format for the date is YYYY-MM-DD
            ps.setDate(value, java.sql.Date.valueOf(dataColumn));
            return;
        } 
        
        if (dataType.equals("Time")) {
            ps.setTime(value, java.sql.Time.valueOf(dataColumn));
            return;
        }
    }
}
