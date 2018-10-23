package mx.com.rosti.util;

/**
 * Interface para definir valores constantes usados por las
 * utilerias y realizar ciertas validaciones.
 */
public interface Constants {
	String FORMAT_DATE = "yyyy-MM-dd";
	
	String FORMAT_DATE_WITH_HOUR = "yyyy-MM-dd HH:mm:ss";
	
	String FORMAT_HOUR = "HH:mm:ss";
	
    /** * Caracteres alfanumericos. <BR/>(a-z)(0-9). */
    char[] ALPHANUMERIC_LOWERCASE = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
        '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /** * Caracteres numericos. <BR/>(0-9). */
    char[] NUMERIC = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /** * Caracteres numericos con decimales. <BR/>(0-9)(.). */
    char[] DECIMAL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};

    /** * Caracteres hexadecimales. <BR/>(0-9)(A-F). */
    char[] HEXADECIMAL = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F'
    };

    /** * Caracteres permitidos para las siglas del contribuyente. 
     * <BR/>(A-Z)(&#xd1; &#x26;). 
     */
    char[] SIGLAS_CONTRIBUYENTE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '\u00d1',
        '\u0026'
    };

    /** 
     * Formatos de fecha. 
     * <BR/>"aaaa", "mmaaaa", "aaaamm", "ddmmaaaa", "aammdd", "aaaammdd", 
     * "mm/aaaa", "aaaa/mm", "dd/mm/aaaa" y "aaaa/mm/dd". 
     * (dd = d&#xed;a)(mm = mes)(aaaa = a&#xf1;o). 
     */
    String[] DATE_FORMATS = {
        "aaaa", "mmaaaa", "aaaamm", "ddmmaaaa", "aammdd", "aaaammdd",
        "mm/aaaa", "aaaa/mm", "dd/mm/aaaa", "aaaa/mm/dd"
    };

    /** 
     * Caracteres permitidos para la homonimia. 
     * <BR/>(A-Z)(0-9)(&#xd1;). 
     */
    char[] HOMONIMIA = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1',
        '2', '3', '4', '5', '6', '7', '8', '9', '\u00d1'
    };

    /** 
     * Caracteres permitidos para el d&#xed;gito verificador. 
     * <BR/>(A-Z)(0-9)(&#xd1;). 
     */
    char[] DIGITO_VERIFICADOR = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1',
        '2', '3', '4', '5', '6', '7', '8', '9', '\u00d1'
    };
    /** 
     * Caracteres alfanumericos. <BR/>(A-Z)(0-9). 
     */
    char[] ALPHANUMERIC_UPPERCASE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1',
        '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /** 
     * Caracteres alfanumericos. <BR/>(A-Z)(a-z)(0-9). 
     */
    char[] ALPHANUMERIC = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9'
    };

    /** 
     * Caracteres alfabeticos. <BR/>(a-z). 
     */
    char[] ALPHABETIC_LOWERCASE = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    /** 
     * Caracteres alfabeticos. <BR/>(A-Z). 
     */
    char[] ALPHABETIC_UPPERCASE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /** 
     * Caracteres alfabeticos. <BR/>(A-Z)(a-z). 
     */
    char[] ALPHABETIC = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
}
