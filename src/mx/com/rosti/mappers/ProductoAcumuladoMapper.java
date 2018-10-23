package mx.com.rosti.mappers;

import mx.com.rosti.to.ProductoAcumulado;

/**
 * Clase para mapear periodos.
 */
public class ProductoAcumuladoMapper {

    /**
     * Metodo para mapear atributos.
     * @param  cc       Objeto
     * @return Object[]
     */
    public Object[] mapAttributesUpdate(final ProductoAcumulado cc) {
        return new Object[] {
            cc.getPrecioUnitario(), cc.getCostoInventario(), cc.getIdProd(), cc.getProv(), cc.getEntradaSalida()
        };
    }
}
