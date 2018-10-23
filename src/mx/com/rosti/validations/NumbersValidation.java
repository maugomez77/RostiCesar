package mx.com.rosti.validations;


/**
 * Validación de numeros.
 */
public final class NumbersValidation {
    /**
     * Si es numero un String.
     * @param  str  String
     * @return boolean
     */
    public static boolean isNumber(final String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Si es Double un String.
     * @param  str  String
     * @return boolean
     */
    public static boolean isDouble(final String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
