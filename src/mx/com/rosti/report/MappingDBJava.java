package mx.com.rosti.report;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;

import mx.com.rosti.util.Utilerias;

/**
 * Clase para mapear los datos de la BD a objetos Java.
 */
public class MappingDBJava {
    /**
     * Variable booleana para saber si se calcularan
     * las columnas con sus tipos de datos.
     */
    private boolean getColumnsTypes = false;

    /**
     * Metodo para ejecutar el query trayendo tipos de columnas.
     * con sus tipos de datos y las tuplas del query
     * @param  resultSet            ResultSet
     * @param  columnsTypes         Variable booleana para saber
     *                              si se calculan las columnas y
     *                              sus tipos de datos,
     *                              es decir el nombre
     * @return ContainerDataFromDB  Variable para guardar los datos
     *                              resultantes del query
     * @throws SQLException         Excepcion de base de datos
     */
    public ContainerDataFromDB mapRow(final ResultSet resultSet,
        final boolean columnsTypes) throws SQLException {
        ResultSetMetaData meta = resultSet.getMetaData();
        int size = meta.getColumnCount();
        resultSet.last();

        int count = resultSet.getRow();
        resultSet.beforeFirst();

        ContainerDataFromDB cts = new ContainerDataFromDB();
        cts.initializeVariables(size, count);
        this.getColumnsTypes = columnsTypes;

        if (getColumnsTypes) {
            for (int i = 0; i < size; i++) {
                cts.getDataColumns().add(meta.getColumnName(i + 1));
                cts.getDataTypes().add(meta.getColumnTypeName(i + 1));
            }
        }

        while (resultSet.next()) {
            ArrayList<String> temporal = new ArrayList<String>(size);

            for (int i = 0; i < size; i++) {
                temporal.add(resultSet.getString(i + 1));
            }

            cts.getDataValues().add(temporal);
        }

        return cts;
    }

    /**
     * Metodo para imprimir los valores del objeto que
     * contiene las columnas, los tipos de columna y los
     * valores de tuplas.
     * @param cont    Objeto ContainerDataFromDB
     */
    public void print(final ContainerDataFromDB cont) {
        int size = cont.getDataTypes().size();

        if (getColumnsTypes) {
            for (int i = 0; i < size; i++) {
                Utilerias.debug(this,
                    cont.getDataTypes().get(i) + "  " 
                    + cont.getDataColumns().get(i));
            }
        }

        size = cont.getDataValues().size();

        for (int i = 0; i < size; i++) {
            ArrayList<String> element = cont.getDataValues().get(i);
            int size2 = element.size();

            for (int j = 0; j < size2; j++) {
                Utilerias.debug(this, element.get(j));
            }
        }
    }
}
