package mx.com.rosti.testing;

import mx.com.rosti.factory.ObjectFactory;
import mx.com.rosti.report.ContainerDataFromDB;
import mx.com.rosti.report.MappingDBJava;
import mx.com.rosti.report.ParametrizedDAOImpl;
import mx.com.rosti.util.Utilerias;


/**
 * Testing.
 */
public final class TestingParametrized {
    /**
     * Constructor privado.
     */
    private TestingParametrized() {
    }

    /**
     * Metodo de prueba.
     * @param args   Argumentos de consola
     */
    public static void main(final String[] args) {
        Utilerias.printConsole("INICIO");

        ParametrizedDAOImpl est = (ParametrizedDAOImpl) ObjectFactory.getObject(
                "ParametrizedDAO");
        est.setLocal(true);

        ContainerDataFromDB cDB = est.getData(false);
        MappingDBJava mDB = new MappingDBJava();
        mDB.print(cDB);

        Utilerias.printConsole("FIN");
    }
}
