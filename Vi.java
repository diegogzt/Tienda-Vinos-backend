
/** *************************************************
 * REGISTRO DE VINOS - SISTEMA DE ALMACENAMIENTO
 ************************************************** */
public class Vi {

    /* ---- PROPIEDADES DEL VINO ---- */
    private final String nom;
    /* Nombre del vino - No modificable */
    private int preu = -1;
    /* Precio - Valor -1 indica "sin precio" */
    private int estoc = 0;
    /* Inventario disponible */

 /* Características descriptivas */
    private String ref;
    /* Código de referencia único */
    private String lloc;
    /* Lugar de elaboración */
    private String origen;
    /* Denominación de origen */
    private String tipus;
    /* Tipo o variedad */
    private String collita;

    /* Año de cosecha */

 /* Constructor - Inicializa un nuevo objeto de tipo Vi */
    public Vi(String ref, String nom, int preu, int estoc, String lloc, String origen, String tipus, String collita) {
        /* Procesamiento de cadenas para normalizar formato */
        this.ref = normalitzaString(ref);
        this.nom = normalitzaString(nom);

        /* Control de valores negativos en precio */
        if (preu < 0) {
            preu = -1;
        }
        this.preu = preu;

        /* Control de valores negativos en inventario */
        if (estoc < 0) {
            estoc = -1;
        }
        this.estoc = estoc;

        /* Asignación de propiedades descriptivas */
        this.lloc = normalitzaString(lloc);
        this.origen = normalitzaString(origen);
        this.tipus = normalitzaString(tipus);
        this.collita = normalitzaString(collita);
    }

    /* ---------- MÉTODOS ACCESORES ---------- */
 /* Devuelve el nombre del vino */
    public String getNom() {
        return nom;
    }

    /* Devuelve el precio actual */
    public int getPreu() {
        return preu;
    }

    /* Devuelve la cantidad en inventario */
    public int getEstoc() {
        return estoc;
    }

    /* Devuelve el código de referencia */
    public String getRef() {
        return ref;
    }

    /* Devuelve el lugar de elaboración */
    public String getLloc() {
        return lloc;
    }

    /* Devuelve la denominación de origen */
    public String getOrigen() {
        return origen;
    }

    /* Devuelve el tipo de vino */
    public String getTipus() {
        return tipus;
    }

    /* Devuelve el año de cosecha */
    public String getCollita() {
        return collita;
    }

    /* ---------- MÉTODOS MODIFICADORES ---------- */
 /* Actualiza el precio si es válido (≥0) */
    public void setPreu(int preu) {
        if (preu >= 0) {
            this.preu = preu;
        }
    }

    /* Actualiza el inventario si es válido (≥0) */
    public void setEstoc(int estoc) {
        if (estoc >= 0) {
            this.estoc = estoc;
        }
    }

    /* Actualiza el lugar de elaboración */
    public void setLloc(String lloc) {
        if (lloc != null && !lloc.isBlank()) {
            this.lloc = normalitzaString(lloc);
        }
    }

    /* ---------- VALIDACIÓN ---------- */
 /* Comprueba si todos los datos son válidos */
    public boolean esValid() {
        // Verificación 1: Ningún campo es nulo
        if (ref == null
                || nom == null
                || lloc == null
                || origen == null
                || tipus == null
                || collita == null) {
            return false;
        }

        // Verificación 2: Ningún campo está vacío
        if (ref.isBlank()
                || nom.isBlank()
                || lloc.isBlank()
                || origen.isBlank()
                || tipus.isBlank()
                || collita.isBlank()) {
            return false;
        }

        // Verificación 3: Valores numéricos válidos
        if (preu < 0 || estoc < 0) {
            return false;
        }

        return true;
    }

    /* ---------- UTILIDADES ---------- */
 /* Elimina espacios redundantes y normaliza cadenas */
    public static String normalitzaString(String nom) {
        // Validación preliminar
        String nouNom;
        if (nom == null || nom.isBlank()) {
            return null;
        }

        // Inicialización 
        String nomNormalitzat = "";
        nouNom = nom.trim();
        boolean primerEspai = true;

        // Procesamiento carácter a carácter
        for (int i = 0; i < nouNom.length(); i++) {
            char charNom = nouNom.charAt(i);
            if (Character.isWhitespace(charNom)) {
                if (primerEspai) {
                    nomNormalitzat += charNom;
                    primerEspai = false;
                }
            } else {
                primerEspai = true;
                nomNormalitzat += charNom;
            }
        }
        return nomNormalitzat;
    }

    @Override

    public String toString() {
        return String.format("%n    Ref: %s%n    Nom: %s%n    Preu: %d%n    Estoc: %d%n    Lloc: %s%n    D.O.: %s%n    Tipus: %s%n    Collita: %s%n", ref, nom, preu, estoc, lloc, origen, tipus, collita);
    }

    /* Convierte los atributos a un array de Strings */
    public String[] aArrayString() {
        // Creación del contenedor
        String[] valors = new String[8];

        // Conversión y asignación
        valors[0] = this.ref;
        valors[1] = this.nom;
        valors[2] = Integer.toString(this.preu);
        valors[3] = Integer.toString(this.estoc);
        valors[4] = this.lloc;
        valors[5] = this.origen;
        valors[6] = this.tipus;
        valors[7] = this.collita;

        return valors;
    }

    /* Crea una instancia de Vi a partir de un array de Strings */
    public static Vi deArrayString(String[] valors) {
        // Validación del array de entrada
        if (valors == null || valors.length != 8) {
            return null;
        }

        // Extracción de datos textuales
        String ref = valors[0];
        String nom = valors[1];

        // Extracción y validación de datos numéricos
        int preu;
        int estoc;
        if (!EsEnter.esEnter(valors[2]) || !EsEnter.esEnter(valors[3])) {
            return null;
        }

        // Conversión a tipos numéricos
        preu = Integer.parseInt(valors[2]);
        estoc = Integer.parseInt(valors[3]);

        // Extracción de datos descriptivos
        String lloc = valors[4];
        String origen = valors[5];
        String tipus = valors[6];
        String collita = valors[7];

        // Creación y validación del objeto
        Vi vi = new Vi(ref, nom, preu, estoc, lloc, origen, tipus, collita);
        if (!vi.esValid()) {
            return null;
        }

        return vi;
    }
}
