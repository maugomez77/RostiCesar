package mx.com.rosti.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.mappers.ReporteInventarioMapper;
import mx.com.rosti.to.ReporteInventario;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para accesar al catalogo de periodos.
 */
public class ReporteInventarioDAOImpl extends Conexiones {
    /**
     * Variable para tomar el dataSource local o web booleana.
     */
    private boolean     local;
    /**
     * variable del dataSource.
     */
    private DataSource  dataSource;
    /**
     * Mapeador de rows para cuando se traen los datos de un query.
     */
    private ReporteInventarioMapper pMAPPER = new ReporteInventarioMapper();
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
     * Metodo para realizar consultas.
     * @return List 
     */
    @SuppressWarnings("rawtypes")
	public List consultarEntradas(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReporteInventario> list = new ArrayList<ReporteInventario>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
            	ReporteInventario cl = pMAPPER.mapRowEntrada(rs, rs.getRow());
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
     * Metodo para realizar consultas.
     * @return List 
     */
    @SuppressWarnings("rawtypes")
	public List consultarSalidas(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReporteInventario> list = new ArrayList<ReporteInventario>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
            	ReporteInventario cl = pMAPPER.mapRowSalida(rs, rs.getRow());
                list.add(cl);
            }
        } catch (SQLException e) {
            Utilerias.error(this, e.toString());
        } finally { 
            closeResources(rs, ps, conn);
        }
        return list;
    }
}
