package mx.com.rosti.report;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Clase para guardar campos en queries parametrizados.
 */
public class SQLParametrized {
    /**
     * Separador de campos por default, se puede sobreescribir.
     */
    private String separator = "-";

    /**
     * Query a ser ejecutado.
     */
    private String query;

    /**
     * String para guardar los tipos de datos con su separador respectivo.
     */
    private String dataType;

    /**
     * String para guardar los tipos de columna con su separador respectivo.
     */
    private String dataColumn;

    /**
     * Arreglo en donde se guardan los tipos de datos que arroja el query.
     */
    private ArrayList<String> valuesType;

    /**
     * Arreglo en donde se guardan los tipos de columna que arroja el query.
     */
    private ArrayList<String> valuesColumn;

    /**
     * Metodo para realizar el split, convertir de string hacia los arreglos los
     * tipos de columnma y de valor.
     */
    public void splitter() {
        if (valuesType == null) {
            this.valuesType = new ArrayList<String>();
        }

        if (valuesColumn == null) {
            this.valuesColumn = new ArrayList<String>();
        }

        if ((dataType == null) || (dataColumn == null)) {
            return;
        }

        if ((valuesType.size() > 0) && (valuesColumn.size() > 0)) {
            return;
        }

        StringTokenizer st = new StringTokenizer(dataType, separator);

        while (st.hasMoreTokens()) {
            valuesType.add(st.nextToken());
        }

        st = new StringTokenizer(dataColumn, separator);

        while (st.hasMoreTokens()) {
            valuesColumn.add(st.nextToken());
        }
    }

    /**
     * Metodo para accesar al separador.
     * @return String
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Metodo para setear el separador.
     * @param pSep   Separador
     */
    public void setSeparator(final String pSep) {
        this.separator = pSep;
    }

    /**
     * Metodo para accesar al query.
     * @return String
     */
    public String getQuery() {
        return query;
    }

    /**
     * Metodo para setear al query.
     * @param pQuery   Query
     */
    public void setQuery(final String pQuery) {
        this.query = pQuery;
    }

    /**
     * Metodo para accesar los dataColumn.
     * @return String
     */
    public String getDataColumn() {
        return dataColumn;
    }

    /**
     * Metodo para setear los dataColumn.
     * @param pDataColumn   DataColumn
     */
    public void setDataColumn(final String pDataColumn) {
        this.dataColumn = pDataColumn;
    }

    /**
     * Metodo para obtener los dataType.
     * @return String
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Metodo para setear los DataType.
     * @param pDataType   Tipo de dato
     */
    public void setDataType(final String pDataType) {
        this.dataType = pDataType;
    }

    /**
     * Metodo para obtener el arreglo de valuesColumn.
     * @return String
     */
    public ArrayList<String> getValuesColumn() {
        return valuesColumn;
    }

    /**
     * Metodo para setear el arreglo de valuesColumn.
     * @param pValuesColumn   Valores de Columna
     */
    public void setValuesColumn(final ArrayList<String> pValuesColumn) {
        this.valuesColumn = pValuesColumn;
    }

    /**
     * Metodo para obtener los valuesType.
     * @return String
     */
    public ArrayList<String> getValuesType() {
        return valuesType;
    }

    /**
     * Metodo para setear los valuesType.
     * @param pValuesType   Tipo de valores
     */
    public void setValuesType(final ArrayList<String> pValuesType) {
        this.valuesType = pValuesType;
    }
}
