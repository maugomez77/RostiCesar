package mx.com.rosti.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;


/**
 * Clase de utilerias basicas para la aplicación.
 */
public class Utilerias implements Constants {
    /**
     * Metodo para imprimir debugs de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void debug(final Object className, final String msg) {
        Logging.debug(className, msg);
    }
    /**
     * Metodo para imprimir errors de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void error(final Object className, final String msg) {
        Logging.error(className, msg);
    }
    /**
     * Metodo para imprimir fatal de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void fatal(final Object className, final String msg) {
        Logging.fatal(className, msg);
    }
    /**
     * Metodo para imprimir trace de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void trace(final Object className, final String msg) {
        Logging.trace(className, msg);
    }
    /**
     * Metodo para imprimir info de la aplicación.
     * @param className    Objeto de la clase
     * @param msg          Mensaje a ser desplegado
     */
    public static void info(final Object className, final String msg) {
        Logging.info(className, msg);
    }
    /**
     * Metodo para imprimir en consola mensajes de texto.
     * @param msg   Mensaje a ser impreso
     */
    public static void printConsole(final String msg) {
        Logging.printConsole(msg);
    }
    
    public static String replaceComma(String str) {
		/****  hola, hola2, hola3 ****/	
		str = str.replace(",", "\",\"");
		return "\"" + str + "\"";
				
	}
    
    /**
     * Es o no aceptado un numero de credito.
     * Si se recibe un valor nulo en el argumento,
     * regresar false como resultado.
     * @param  creditNumber Numero de credito.
     * @return boolean      True si el numero de credito es aceptado,
     *                      false en caso contrario.
    */
    public static boolean isCreditNumberAccept(final String creditNumber) {
    
        String digit = getVerificationDigit(creditNumber);

        if ((creditNumber != null) && (creditNumber.length() == 14) 
            && isStringAccept(creditNumber, NUMERIC)) {
            if ((digit != null) 
                && digit.equals(creditNumber.substring(13, 14))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Obtener el digito verificador de un numero de credito.
     * Si la longitud del argumento es distinta de 14
     * caracteres o es un valor nulo,
     * regresar null como resultado.
     * @param  creditNumber Numero de credito.
     * @return String       Digito verificador.
     */
    public static String getVerificationDigit(final String creditNumber) {
        if ((creditNumber != null) 
            && (creditNumber.length() == 14) 
            && isStringAccept(creditNumber, NUMERIC)) {
            
            String numeroControl = creditNumber.substring(0, 13);
            long ponderacion = 0;

            for (int cont1 = 1; cont1 <= numeroControl.length(); cont1++) {
                ponderacion += (getLongValue(numeroControl.substring(cont1 - 1,
                        cont1)) * (14 - cont1));
            }

            long residuo = ponderacion % 11;

            if ((residuo == 0) || (residuo == 1)) {
                return "" + residuo;
            } else {
                long codigo = 48 + (11 - residuo);

                if ((codigo >= 48) && (codigo <= 57)) {
                    if (codigo == 48) {
                        return "0";
                    } else if (codigo == 49) {
                        return "1";
                    } else if (codigo == 50) {
                        return "2";
                    } else if (codigo == 51) {
                        return "3";
                    } else if (codigo == 52) {
                        return "4";
                    } else if (codigo == 53) {
                        return "5";
                    } else if (codigo == 54) {
                        return "6";
                    } else if (codigo == 55) {
                        return "7";
                    } else if (codigo == 56) {
                        return "8";
                    } else if (codigo == 57) {
                        return "9";
                    }
                }
            }
        }

        return null;
    }

    /**
    * Es o no aceptado un texto de acuerdo a un arreglo de caracteres posibles.
    * Si se recibe un valor nulo o un arreglo vacio en los argumentos,
    * regresar false como resultado.
    * @param  stringValue  Texto a comparar contra un arreglo de caracteres.
    * @param  charValues   Arreglo de tipo char.
    * @return boolean      True si el texto solo contiene caracteres que estan
    *                      contenidos dentro del arreglo de caracteres posibles,
    *                      false en caso contrario.
    */
    public static boolean isStringAccept(final String stringValue,
        final char[] charValues) {
        if ((stringValue != null) && (stringValue.length() != 0) 
            && (charValues != null) && (charValues.length != 0)) {
            for (int cont1 = 0; cont1 < stringValue.length(); cont1++) {
                if (!isCharacterAccept(stringValue.charAt(cont1), charValues)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /**
    * Es o no aceptado un caracter de acuerdo a un arreglo de 
    * caracteres posibles.
    * Si se recibe un valor nulo o un arreglo vacio en los argumentos,
    * regresar false como resultado.
    * @param  charValue    Char a comparar contra un arreglo de 
    *                      caracteres posibles.
    * @param  charValues   Arreglo de tipo char.
    * @return boolean      True si el caracter esta contenido en el arreglo,
    *                      false en caso contrario.
    */
    public static boolean isCharacterAccept(final char charValue, 
            final char[] charValues) {
        if ((charValues != null) && (charValues.length != 0)) {
            for (int cont1 = 0; cont1 < charValues.length; cont1++) {
                if (charValue == charValues[cont1]) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Es o no aceptado un texto de acuerdo a un arreglo de objetos
     * String permitidos. Si se recibe un valor nulo o un arreglo
     * que contiene algun elemento nulo en los argumentos,
     * regresar false como resultado.
     * @param  stringValue   Texto a comparar contra un arreglo
     *                       de objetos String.
     * @param  stringValues  Arreglo de objetos String.
     * @return boolean       true si el texto esta contenido en el arreglo,
     *                       false en caso contrario.
     */
    public static boolean isStringAccept(final String stringValue,
        final String[] stringValues) {
        if ((stringValue != null) && !containsNullValues(stringValues)) {
            for (int cont1 = 0; cont1 < stringValues.length; cont1++) {
                if (stringValue.equals(stringValues[cont1])) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Es o no aceptada una fecha de acuerdo a un formato de fecha.
     * Si se recibe un valor nulo o incorrecto en los argumentos,
     * regresar false como resultado.
     * @param  dateValue  Fecha.
     * @param  dateFormat Formato de fecha.
     *                    Puede ser alguno de los valores contenidos
     *                    en DATE_FORMATS.
     * @return boolean    true si la fecha es correcta, false en caso contrario.
     */
    public static boolean isDateAccept(final String dateValue, String dateFormat) {
        if ((dateValue != null) && isDateFormatAccept(dateFormat)) {
            if ((dateValue.length() != dateFormat.length()) 
                 && (dateFormat.equals("aaaa") || dateFormat.equals("mmaaaa") 
                     || dateFormat.equals("aaaamm") 
                     || dateFormat.equals("ddmmaaaa") 
                     || dateFormat.equals("aammdd") 
                     || dateFormat.equals("aaaammdd"))) {
                return false;
            }

            dateFormat = dateFormat.replace('a', 'y').replace('m', 'M');

            try {
                SimpleDateFormat simpleDateFormat = 
                    new SimpleDateFormat(dateFormat);
                simpleDateFormat.setLenient(false);

                if (simpleDateFormat.parse(dateValue) != null) {
                    return true;
                }
            } catch (Exception ex) {
                return false;
            }
        }

        return false;
    }

    /**
     * Es o no aceptado un formato de fecha.
     * Si se recibe un valor nulo o incorrecto en los argumentos,
     * regresar false como resultado.
     * @param  dateFormat Formato de fecha.
     *                    Puede ser alguno de los valores contenidos
     *                    en DATE_FORMATS.
     * @return boolean    True si el formato de fecha es aceptado,
     *                    false en caso contrario.
     */
    public static boolean isDateFormatAccept(final String dateFormat) {
        if (isStringAccept(dateFormat, DATE_FORMATS)) {
            return true;
        }

        return false;
    }

    /**
    * Es o no un arreglo de objetos String que contiene algun elemento nulo.
    * Si se recibe un valor nulo o vacio, regresar true como resultado.
    * @param  stringValues   Arreglo de objetos String.
    * @return true           Si el arreglo de objetos String
    *                        contiene algun elemento nulo,
    *                        false en caso contrario.
    */
    public static boolean containsNullValues(final String[] stringValues) {
        if ((stringValues == null) || (stringValues.length == 0)) {
            return true;
        }

        for (int cont1 = 0; cont1 < stringValues.length; cont1++) {
            if (stringValues[cont1] == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Obtener un long asociado al texto.
     * Si no es posible obtener un valor con el argumento,
     * regresar cero como resultado.
     * @param  stringValue   Texto a convertir en tipo long.
     * @return long          asociado al texto.
     */
    public static long getLongValue(final String stringValue) {
        try {
            return (new BigInteger(stringValue)).longValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Metodo para checar si dos String contienen el mismo contenido.
     * @param  oldStr    Primer String
     * @param  newStr    Segundo String
     * @return boolean   True si son iguales, false de otra forma
     */
    public static boolean equalsString(final String oldStr, final String newStr) {
        return oldStr.equals(newStr);
    }

    /**
     * Obtener la cifra verificadora a partir del nUmero de operaciOn.
     * Si se recibe un valor nulo o un valor incorrecto en los argumentos,
     * regresar null como resultado.
     * @param  operationNumber   Numero de operacion.
     * @return String            Cifra verificadora.
     */
    public static String getVerificationNumber(final String operationNumber) {
        if ((operationNumber != null) && (operationNumber.length() <= 10)) {
            try {
                BigInteger cifraVerificadora = 
                      new BigInteger(operationNumber, 16);

                return cifraVerificadora.multiply(new BigInteger("327337"))
                                        .mod(new BigInteger("524287"))
                                        .mod(new BigInteger("10000")).toString();
            } catch (Exception ex) {
                return null;
            }
        }

        return null;
    }
    /**
     * Metodo para regresar un String a partir de un double.
     * @param  val   Valor
     * @return String
     */
    public static String doubleToStr(final double val) {
        return String.valueOf(val);
    }
    
    /**
     * Metodo para convertir un String a entero.
     * @param  str    Strintr
     * @return double
     */    
    public static double strToDouble(final String str) { 
        return strToDouble(str, -1);
    }
    /**
     * Metodo para convertir un String a double.
     * @param  str   String
     * @param  pred  Valor pred
     * @return double
     */
    public static double strToDouble(final String str, final double pred) {
        try { 
            return Double.parseDouble(str);
        } catch (Exception e) {}
        return pred;
    }
    /**
     * Metodo para convertir un String a entero.
     * @param  str   String
     * @return int
     */
    public static int strToInt(final String str) { 
        return strToInt(str, -1);
    }
    public static long strToLong(final String str) {
    	return strToLong(str, -1);
    }
    public static long strToLong(final String str, long pred) {
    	try { 
    		return Long.parseLong(str);
    	} catch (Exception e) {}
    	return pred;
    }
    /**
     * Metodo para regresar un String a partir de un int.
     * @param  val    Valor
     * @return String
     */
    public static String intToStr(final int val) { 
        return String.valueOf(val);
    }
    /**
     * Metodo para convertir un string a entero, 
     * y tambien tiene un valor predeterminado.
     * @param  str   String
     * @param  pred  Valor predeterminado 
     * @return int
     */
    public static int strToInt(final String str, final int pred) { 
        try { 
            return Integer.parseInt(str);
        } catch (Exception e) {}
        return pred;
    }
    /**
     * Metodo para regresar un String a partir de un long.
     * @param  val    Valor
     * @return String
     */
    public static String longToStr(final long val) { 
        return String.valueOf(val);
    }
    
    /**
     * Obtiene un reemplazo de strings. 
     * 
     * Aqui un ejemplo: 
     *     src = "Hola <mundo> 123 "
     *     comodin = "<mundo>";
     *     newWordComoding = "alfonso";
     *     result = "Hola alfonso 123";
     * @param  src                String fuente
     * @param  comodin            Comodin a utilizar
     * @param  newWordComodin     Nueva palabra de reemplazo
     * @return String
     */
    public String getMessageReplace(final String src, 
        final String comodin, final String newWordComodin) {
    
        return src.replace(comodin, newWordComodin);
    }
    /**
     * Metodo para reemplazar caracteres.
     * @param  cadena    Cadena original
     * @param  oldChar   Caracteres viejos 
     * @param  newChar   Caracteres nuevos
     * @return String
     */
    public static String replaceCharacters(final String cadena, 
        final String oldChar, final String newChar) {
    
        return cadena.replace(oldChar, newChar);
    }
    /**
     * Metodo que regresa la hora actual en formato de String.
     * @return  String   Hora actual
     */
    public static String getTime() {
        Calendar calendario = new GregorianCalendar();
                
        return "Start: " + calendario.get(Calendar.HOUR) + ":" 
            + calendario.get(Calendar.MINUTE) + ":" 
            + calendario.get(Calendar.SECOND) + ":" 
            + calendario.get(Calendar.MILLISECOND);
    }

    public static String getTime(final String format) {
    	
       DateFormat dateFormat = new SimpleDateFormat(format);
	          
	   //get current date time with Calendar()
	   Calendar cal = Calendar.getInstance();
	   
	   return dateFormat.format(cal.getTime());
    }
    
    /**
     * Metodo para formatear un java.sql.Date.
     * @param  date    Fecha a formatear
     * @param  format  String de formato
     * @return String
     */
    public static String getDate(final java.sql.Date date, final String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        if (date == null) {
        	return "";
        }
        return formatter.format(date);
    }

    /**
     * Metodo para formatear un java.sql.Date.
     * @param  date    Fecha a formatear
     * @param  format  String de formato
     * @return String
     */
    public static String getDate(final String format) {
    	java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        
        return formatter.format(date);
    }
    /**
     * Metodo para formatear un java.sql.Time.
     * @param  time    Tiempo
     * @param  format  String de formato
     * @return String
     */
    public static String getTime(final java.sql.Time time, final String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if (time == null) {
        	return "";
        }
        return formatter.format(time);
    }

    /**
     * Metodo para obtener la hora en formato de SQL.
     * @return  Time
     */
    @SuppressWarnings("static-access")
    public static Time getHora() {
        Calendar cal = Calendar.getInstance();
        java.sql.Time jsqlT = java.sql.Time.valueOf(cal.get(cal.HOUR_OF_DAY) +
                ":" + cal.get(cal.MINUTE) + ":" + cal.get(cal.SECOND));

        return jsqlT;
    }

    /**
     * Metodo para obtener la hora en formato de SQL.
     * @return  Time
     */
    @SuppressWarnings("static-access")
    public static Time getHoraSQL(Calendar cal) {
        java.sql.Time jsqlT = java.sql.Time.valueOf(cal.get(cal.HOUR_OF_DAY) +
                ":" + cal.get(cal.MINUTE) + ":" + cal.get(cal.SECOND));
        return jsqlT;
    }

    /**
     * Metodo para regresar un objeto java.sql.Date a partir de una
     * cadena de string y un formato especificado.
     * @param  date    Valor de fecha
     * @param  format  Formato de la fecha
     * @return java.sql.Date
     */
    public static java.sql.Date getDateSQL(final String date, final String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date effectFrom = null;

        try {
            effectFrom = new java.sql.Date(formatter.parse(date).getTime());
        } catch (ParseException e) {
            Utilerias.error(Utilerias.class, e.toString());
        }

        return effectFrom;
    }
    public static String getFourCifrasDecimal(String str) { 
    	Utilerias.debug(Utilerias.class, "Cifras decimales primera: " + str.substring(0, 2));
    	Utilerias.debug(Utilerias.class, "Cifras decimales segunda: " + str.substring(2, 4));
    	return str.substring(0, 2) + "." + str.substring(2, 4);
    }
    public static String removeCharactersLeft(String str, String chac) {
    	String uno = "";
    	try {
	    	String dos = new String((chac.charAt(0) + "").getBytes(), "UTF-8");
	    	for (int i=0; i < str.length(); i++) {
	    		uno = new String((str.charAt(i) + "").getBytes(), "UTF-8");
	    		if (!uno.equals(dos)) {
	    			return str.substring(i);
	    		}
	    	}
    	} catch (UnsupportedEncodingException e) {
    		Utilerias.error(Utilerias.class, "Error: " + e.toString());
    	}
    	return ""; 
    }
    
    public static Cantidad validarCantidad(final String cantidad) {
		
		Cantidad cant = null;
		if (cantidad.contains(".")) {
			try { 
				cant = new Cantidad();
			    StringTokenizer st = new StringTokenizer(cantidad, ".");
			    cant.setEnteros(st.nextToken());
			    cant.setDecimales(st.nextToken());
			    if (cant.getEnteros().length() > 3 
			    	|| cant.getDecimales().length() > 2) {
			    	cant = null;
			    }
			    Integer.parseInt(cant.getEnteros());
			    Integer.parseInt(cant.getDecimales());
			} catch (Exception e) { 
				Utilerias.printConsole("Consola 1: " + e.toString());
				cant = null;
			}
			
		} else {
			if (cantidad.length() > 3) { 
				return cant;
			} else {
				try { 
					cant = new Cantidad();
					Integer.parseInt(cantidad);
					cant.setEnteros(cantidad);
					cant.setDecimales("00");
				} catch (Exception e) {
					Utilerias.printConsole("Consola 2: " + e.toString());
					cant = null;
				}
			}
		}		
		return cant;
	}
    
    public static NumberFormat getCurrency() { 
    	NumberFormat currencyFormatter =
            NumberFormat.getCurrencyInstance();
    	
    	return currencyFormatter;
    }    
    
    public static DecimalFormat getDecimalFormat() {
    	DecimalFormat formateador = 
        	new DecimalFormat("########.##");
    	return formateador;
    }
    /**
     * Metodo para realizar ciertas pruebas referentes a la clase en particular.
     * @param args  Argumentos de consola
     */
    public static void main(final String[] args) {
                //String creditNumber = "12345678901238";
        //String creditNumber = "12345678912320";
        //Utilerias.debug(Utilerias.class,
        //    "valor resultado = " 
        //        + Utilerias.isCreditNumberAccept(creditNumber));
        
        //Utilerias.debug(Utilerias.class, Utilerias.replaceCharacters("mauricio,gomez,torres", ",", ""));
        
        Utilerias.debug(Utilerias.class, Utilerias.removeCharactersLeft("???????80502450000000000000", "?"));
        //Utilerias.debug(Utilerias.class, Utilerias.getFourCifrasDecimal("00004234234000"));
    }        
}
