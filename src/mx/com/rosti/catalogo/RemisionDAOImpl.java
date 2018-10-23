package mx.com.rosti.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mx.com.rosti.bd.Conexiones;
import mx.com.rosti.mappers.RemisionMapper;
import mx.com.rosti.to.Remision;
import mx.com.rosti.util.Utilerias;

/**
 * Clase para accesar al catalogo de periodos.
 */
public class RemisionDAOImpl extends Conexiones {
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
    private RemisionMapper pMAPPER = new RemisionMapper();
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
    public List<Remision> consultarCliente(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Remision> list = new ArrayList<Remision>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
            	Remision cl = pMAPPER.mapRowCliente(rs, rs.getRow());
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
    public List<Remision> consultarNumero(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Remision> list = new ArrayList<Remision>();
        try {
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
            	Remision cl = pMAPPER.mapRowNumero(rs, rs.getRow());
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
    public List<Remision> consultarTodas(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Remision> list = new ArrayList<Remision>();
        try {
        	Utilerias.debug(this, "Sql: " + sql);
            conn = takeConnection(local, dataSource);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
            	Remision cl = pMAPPER.mapRow(rs, rs.getRow());
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
