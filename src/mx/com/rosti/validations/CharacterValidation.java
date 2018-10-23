package mx.com.rosti.validations;


/**
 * Validación de caracteres.
 */
public final class CharacterValidation {
    /**
     * Si un char es character.
     * @param  ch     Character
     * @return boolean
     */
    public static boolean isCharacter(final char ch) {
        return Character.isDigit(ch);
    }

    /**
     * Si un char es letra.
     * @param  ch  Character
     * @return boolean
     */
    public static boolean isLetter(final char ch) {
        return Character.isLetter(ch);
    }

    /**
     * Si un char es letra o digito.
     * @param  ch       Character
     * @return boolean
     */
    public static boolean isLetterOrDigit(final char ch) {
        return Character.isLetterOrDigit(ch);
    }
}
