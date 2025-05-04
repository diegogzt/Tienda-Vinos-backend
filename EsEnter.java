
/**
 * Utilitat per comprovar si una cadena és un enter
 */
public class EsEnter {

    /**
     * Comprova si la cadena es pot convertir a un enter
     *
     * @param text la cadena a comprovar
     * @return true si la cadena representa un enter, false en cas contrari
     */
    public static boolean esEnter(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        // Comprova si comença amb signe negatiu
        int inici = 0;
        if (text.charAt(0) == '-') {
            if (text.length() == 1) {
                return false; // Només té el signe negatiu
            }
            inici = 1;
        }

        // Comprova que tots els caràcters siguin dígits
        for (int i = inici; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
