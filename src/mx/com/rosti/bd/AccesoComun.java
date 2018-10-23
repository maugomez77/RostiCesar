package mx.com.rosti.bd;


/**
 * Clase de seteo para realización de queries provenientes de los
 * archivos de XML definidos en el paquete mx.com.hsbc.impuestos.resources.
 *
 */
public class AccesoComun {
    /**
     * Variable String para realizar un query seleccionando
     * todos los datos de una tabla.
     */
    private String findAll;

    /**
     * Variable String para realizar un query seleccionado
     * solo los valores unicos por un id.
     */
    private String findUnique;

    /**
     * Variable String para realizar un query seleccionado un rango entre Id.
     */
    private String findRange;

    /**
     * Variable String para realizar un insert a la base de datos.
     */
    private String sqlInsert;

    /**
     * Variable String para realizar un delete a la base de datos.
     */
    private String sqlDelete;

    /**
     * Variable String para realizar un update a la base de datos.
     */
    private String sqlUpdate;

    /**
     * Constructor default para seteo de variables a espacio
     * en blanco.
     */
    public AccesoComun() {
        this.findAll = "";
        this.findUnique = "";
        this.findRange = "";
        this.sqlInsert = "";
        this.sqlDelete = "";
        this.sqlUpdate = "";
    }

    /**
     * Constructor para setear las variables a un valor
     * especificado en los parametros.
     *
     * @param fAll     Variable String para realizar un query seleccionando
     *                    todos los datos de una tabla
     * @param fUni     Variable String para realizar un query seleccionado
     *                    solo los valores unicos por un id
     * @param fRang   Variable String para realizar un query seleccionado
     *                    un rango entre Id
     * @param sqlI   Variable String para realizar un insert
     *                    a la base de datos
     * @param sqlD   Variable String para realizar un delete
     *                    a la base de datos
     * @param sqlU   Variable String para realizar un update
     *                    a la base de datos
     */
    public AccesoComun(final String fAll, final String fUni,
        final String fRang, final String sqlI, final String sqlD,
        final String sqlU) {
        this.findAll = fAll;
        this.findUnique = fUni;
        this.findRange = fRang;
        this.sqlInsert = sqlI;
        this.sqlDelete = sqlD;
        this.sqlUpdate = sqlU;
    }

    /**
     * Metodo de acceso a la variable findAll de tipo String.
     * @return String   findAll
     */
    public String getFindAll() {
        return findAll;
    }

    /**
     * Metodo de mutación a la variable findAll de tipo String.
     * @param fAll        Variable de fAll.
     */
    public void setFindAll(final String fAll) {
        this.findAll = fAll;
    }

    /**
     * Metodo de acceso a la variable findRange de tipo String.
     * @return String  findRange
     */
    public String getFindRange() {
        return findRange;
    }

    /**
     * Metodo de mutación a la variable findRange de tipo String.
     * @param fRang         Variable para rango.
     */
    public void setFindRange(final String fRang) {
        this.findRange = fRang;
    }

    /**
     * Metodo de acceso a la variable findUnique de tipo String.
     * @return String  findUnique
     */
    public String getFindUnique() {
        return findUnique;
    }

    /**
     * Metodo de mutación a la variable findUnique de tipo String.
     * @param fUni  Variable para unique.
     */
    public void setFindUnique(final String fUni) {
        this.findUnique = fUni;
    }

    /**
     * Metodo de acceso a la variable sqlDelete de tipo String.
     * @return String  sqlDelete
     */
    public String getSqlDelete() {
        return sqlDelete;
    }

    /**
     * Metodo de mutación a la variable sqlDelete de tipo String.
     * @param sqlD   Variable para sqlDelete.
     */
    public void setSqlDelete(final String sqlD) {
        this.sqlDelete = sqlD;
    }

    /**
     * Metodo de acceso a la variable sqlInsert de tipo String.
     * @return String  sqlInsert
     */
    public String getSqlInsert() {
        return sqlInsert;
    }

    /**
     * Metodo de mutación a la variable sqlInsert de tipo String.
     * @param sqlIns           Variable para sqlInsert
     */
    public void setSqlInsert(final String sqlIns) {
        this.sqlInsert = sqlIns;
    }

    /**
     * Metodo de acceso a la variable sqlUpdate de tipo String.
     * @return String  sqlUpdate
     */
    public String getSqlUpdate() {
        return sqlUpdate;
    }

    /**
     * Metodo de mutación a la variable sqlUpdate de tipo String.
     * @param sqlU          Variable para sqlUpd
     */
    public void setSqlUpdate(final String sqlU) {
        this.sqlUpdate = sqlU;
    }
}
