package mx.com.rosti.report;

import java.util.ArrayList;


/**
 * Clase para contener los datos de un query parametrizado.
 */
public class ContainerDataFromDB {
    /**
     * variable para guardar los tipos de datos de columnas de un query.
     */
    private ArrayList<String> dataTypes;

    /**
     * variable para guardar el nombre de cada columna del query resultante.
     */
    private ArrayList<String> dataColumns;

    /**
     * variable para guardar las tuplas del query resultante.
     */
    private ArrayList<ArrayList<String>> dataValuesRow;

    /**
     * Constructor por default.
     */
    public ContainerDataFromDB() {
        this.dataTypes = null;
        this.dataColumns = null;
        this.dataValuesRow = null;
    }

    /**
     * Metodo para inicializar las variables de la clase especificando.
     * @param sizeColumns    Medida del numero de columnas
     * @param rowsSize       Numero de rows de un query resultante
     */
    public void initializeVariables(final int sizeColumns, final int rowsSize) {
        this.dataTypes = new ArrayList<String>(sizeColumns);
        this.dataColumns = new ArrayList<String>(sizeColumns);
        this.dataValuesRow = new ArrayList<ArrayList<String>>(rowsSize);
    }

    /**
     * Para setear los dataTypes.
     * @param pDT  Tipos de datos
     */
    public void setDataTypes(final ArrayList<String> pDT) {
        this.dataTypes = pDT;
    }

    /**
     * Para setear las dataColumns.
     * @param pDC  Datos de columnas
     */
    public void setDataColumns(final ArrayList<String> pDC) {
        this.dataColumns = pDC;
    }

    /**
     * Para setear los datos del query.
     * @param pT  ArrayList a guardar los datos
     */
    public void setDataValues(final ArrayList<ArrayList<String>> pT) {
        this.dataValuesRow = pT;
    }

    /**
     * Para accesar los dataTypes.
     * @return ArrayList de tipo String
     */
    public ArrayList<String> getDataTypes() {
        return dataTypes;
    }

    /**
     * Para accesar los dataColumns.
     * @return ArrayList de tipo String
     */
    public ArrayList<String> getDataColumns() {
        return dataColumns;
    }

    /**
     * Para accesar los dataValues.
     * @return ArrayList de tipo String
     */
    public ArrayList<ArrayList<String>> getDataValues() {
        return dataValuesRow;
    }
}
