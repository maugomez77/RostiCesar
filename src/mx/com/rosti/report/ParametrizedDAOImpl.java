package mx.com.rosti.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.util.Utilerias;


/**
 * Clase para realizar queries especificando solo el query y sus tipos
 * de datos y columnas.
 */
public class ParametrizedDAOImpl extends Conexiones {
    /**
     * Objeto que contiene las propiedades del query Parametrizado.
     */
    private SQLParametrized sqlParam;

    /**
     * DataSource por si se trata de Web.
     */
    private DataSource dataSource;

    /**
     * Variable booleana que indica si es web o acceso local.
     */
    private boolean local;

    /**
     * Metodo para setear la variable booleana local.
     * @param pLocal  Local
     */
    public void setLocal(final boolean pLocal) {
        this.local = pLocal;
    }

    /**
     * Metodo para setear el dataSource tomado del Web.
     * @param pDS  DataSource
     */
    public void setDataSource(final DataSource pDS) {
        this.dataSource = pDS;
    }

    /**
     * Metodo para setear el Objeto de propiedades
     * para la ejecución de queries parametrizados.
     * @param pSqlParam   Parametro de SQL
     */
    public void setSqlParam(final SQLParametrized pSqlParam) {
        this.sqlParam = pSqlParam;
    }

    /**
     * Metodo que realiza la ejecución de un query trayendo los datos.
     * @param  columnTypes            Indica si se calcularan
     *                                los tipos de columna con
     *                                sus tipos de datos
     * @return ContainerDataFromDB
     */
    public ContainerDataFromDB getData(final boolean columnTypes) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        sqlParam.splitter();

        ContainerDataFromDB data = null;
        MappingDBJava mp = new MappingDBJava();
        SettingPreparedStatements pp = new SettingPreparedStatements();

        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sqlParam.getQuery());
            rs = pp.executeQueryPreparedStatement(conn, ps,
                    sqlParam.getValuesType(), sqlParam.getValuesColumn());
            data = mp.mapRow(rs, columnTypes);
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally {
            closeResourcesPreparedResultSet(rs, ps, conn);
        }

        return data;
    }
}
