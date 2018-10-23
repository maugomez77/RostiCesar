package mx.com.rosti.validations;

import java.util.StringTokenizer;

import mx.com.rosti.util.Utilerias;

/**
 * Validación de Strings.
  */
public class StringValidation {
    /**
     * Convierte un String a minusculas.
     * @param  str  String
     * @return String
     */
    public static String getLowerCase(final String str) {
        return str.toLowerCase();
    }

    /**
     * Convierte un String a mayusculas.
     * @param  str  String
     * @return String
     */
    public static String getUpperCase(final String str) {
        return str.toUpperCase();
    }

    /**
     * Checa que un String tenga cierta longitud.
     * @param  str     String
     * @param  length  Longitud
     * @return boolean
     */
    public static boolean checkLengthEqual(final String str, final int length) {
        return str.length() == length;
    }

    /**
     * Checa que un String tenga menor longitud o igual a un parametro.
     * @param  str       String
     * @param  length    Longitud
     * @return boolean
     */
    public static boolean checkLength(final String str, final int length) {
        return str.length() <= length;
    }

    /**
     * Divide String.
     * @param  str    String
     * @param  number Numero para ir cortando el String
     * @return String[]
     */
    public static String[] dividingStrings(final String str, final int number) {
        if ((str == null) || (str.length() <= 0)) {
            return null;
        }

        if (number > str.length()) {
            return new String[] {str};
        }

        int cociente = (int) (Math.ceil((double) str.length() / number));
        String[] arrayStr = new String[cociente];
        int start = -1;
        int end = -1;

        for (int i = 0; i < arrayStr.length; i++) {
            start = i * number;
            end = (i * number) + number;

            if (end > str.length()) {
                end = str.length();
            }

            arrayStr[i] = str.substring(start, end);
        }

        return arrayStr;
    }

    /**
     * Metodo para imprimir un arreglo de String.
     * @param array  Arreglo de String
     */
    public static void printStringArray(final String[] array) {
        for (int i = 0; i < array.length; i++) {
            Utilerias.debug(StringValidation.class, "Array pos " + i + " = " + array[i]);
        }
    }

    /**
     * Metodo para dividir String en base a un delimitador.
     * @param  str       String
     * @param  delim     Delimitador
     * @return String[]
     */
    public static String[] split(final String str, final String delim) {
        if ((str == null) || (str.length() <= 0)) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(str, delim);
        String[] temp = new String[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
            temp[i] = st.nextToken();
            i++;
        }

        return temp;
    }

    /**
     * Pads out a string upto padlen with pad chars.
     * @param  str        String to be padded
     * @param  padlen     Length of pad (+ve = pad on right, -ve pad on left)
     * @param  pad        Character
     * @return String
     */
    private static String pad(final String str, final int padlen, final String pad) {
        String padding = "";
        int len = Math.abs(padlen) - str.length();

        if (len < 1) {
            return str;
        }

        for (int i = 0; i < len; ++i) {
            padding = padding + pad;
        }

        if (padlen < 0) {
            return padding + str;
        }

        return str + padding;
    }

    /**
     * Pad a la izquierda.
     * @param  str    String
     * @param  padlen Longitud de Pad
     * @param  pad    Character
     * @return String
     */
    public static String leftPad(final String str, int padlen, final String pad) {
        if (padlen > 0) {
            padlen = padlen * -1;
        }

        return pad(str, padlen, pad);
    }

    /**
     * Pad a la derecha.
     * @param  str    String
     * @param  padlen Longitud de Pad
     * @param  pad    Character
     * @return String
     */
    public static String rightPad(final String str, int padlen, final String pad) {
        if (padlen < 0) {
            padlen = padlen * -1;
        }

        return pad(str, padlen, pad);
    }
    
    /**
     * Metodo para validar si los String son nullos o 
     * contienen cadenas vacias.
     * @param  str        Cadena a ser validada
     * @return boolean    True si es nullo o vacio, false en caso contrario
     */
    public static boolean validaStringNullo(final String str) { 
        if (str == null) {
            return true;
        }
        if (str.equals("")) {
            return true;
        }
        return false;
    }
    /**
     * Metodo para regresar un string no nullo a partir de un nullo.
     * @param  str     String
     * @return String
     */
    public static String validaString(final String str) {     
        if (str == null) {
            return "";
        }
        return str;
    }
    /**
     * Metodo de pruebas.
     * @param args Argumentos.
     */
    public static void main(final String[] args) {
        String[] ret = StringValidation.dividingStrings("1234567890", 3);
        StringValidation.printStringArray(ret);
        Utilerias.debug(StringValidation.class, StringValidation.rightPad("1234", 10, "0"));
    }
}
