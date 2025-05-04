/**
 * SISTEMA DE GESTIÓN DE BODEGA
 * Permite administrar el inventario de vinos
 * */
public class Botiga {

    // <><><><><><> CONSTANTES Y ATRIBUTOS <><><><><><>
    /**
     * Número máximo de vinos por defecto
     */
    private static final int DEFAULT_MAX_VINS = 10;

    /**
     * Almacén de vinos (array)
     */
    private Vi[] vins;

    /**
     * Índice para recorrer la colección
     */
    private int recorregut = 0;

    // <><><><><><> CONSTRUCTORES <><><><><><>
    /**
     * Constructor por defecto Inicializa la bodega con capacidad estándar
     */
    public Botiga() {
        vins = new Vi[DEFAULT_MAX_VINS];
    }

    /**
     * Constructor con parámetros Permite especificar la capacidad máxima
     *
     * @param maxVins Capacidad máxima de la bodega
     */
    public Botiga(int maxVins) {
        if (maxVins < 1) {
            // Si la capacidad es inválida, usar la predeterminada
            vins = new Vi[DEFAULT_MAX_VINS];
            return; // Importante: salir para evitar la siguiente asignación
        }
        vins = new Vi[maxVins];
    }

    // <><><><><><> OPERACIONES DE INVENTARIO <><><><><><>
    /**
     * Añade un vino a la bodega si hay espacio y es válido
     *
     * @param vi Vino a añadir
     * @return El vino añadido o null si no se pudo añadir
     */
    public Vi afegeix(Vi vi) {
        // █ VALIDACIÓN PRELIMINAR █
        // Comprobar que el vino y sus propiedades no sean nulas o vacías
        if (vi == null
                || vi.getRef() == null || vi.getRef().trim().isEmpty()
                || vi.getNom() == null || vi.getNom().trim().isEmpty()
                || vi.getLloc() == null || vi.getLloc().trim().isEmpty()
                || vi.getOrigen() == null || vi.getOrigen().trim().isEmpty()
                || vi.getTipus() == null || vi.getTipus().trim().isEmpty()
                || vi.getCollita() == null || vi.getCollita().trim().isEmpty()) {
            return null; // Datos incompletos - rechazado
        }

        // Verificar validez del vino según sus reglas internas
        if (!vi.esValid()) {
            return null; // El vino no cumple con las reglas de validación
        }

        // █ VERIFICACIÓN DE DUPLICADOS █
        // Buscar por nombre
        Vi trobaVi = cerca(vi.getNom());
        if (trobaVi != null) {
            return null; // Ya existe un vino con ese nombre
        }

        // Buscar por referencia
        for (int i = 0; i < vins.length; i++) {
            if (vins[i] != null && vi.getRef().equalsIgnoreCase(vins[i].getRef())) {
                return null; // Ya existe un vino con la misma referencia
            }
        }

        // █ BÚSQUEDA DE ESPACIO LIBRE █
        for (int i = 0; i < vins.length; i++) {
            if (vins[i] == null) {
                vins[i] = vi;     // Almacenar el vino en la primera posición libre
                return vins[i];    // Devolver el vino almacenado
            }
        }
        return null; // No hay espacio disponible
    }

    /**
     * Elimina un vino de la bodega por su referencia Solo permite eliminar si
     * no tiene stock
     *
     * @param ref Referencia del vino a eliminar
     * @return El vino eliminado o null si no se encontró o tiene stock
     */
    public Vi elimina(String ref) {
        // Normalizar la referencia para búsqueda (minúsculas y sin espacios extras)
        ref = Vi.normalitzaString(ref.toLowerCase());

        // Recorrer la colección buscando la coincidencia
        for (int i = 0; i < vins.length; i++) {
            // Cuando encontramos el vino con la referencia
            if (vins[i] != null && ref.equals(vins[i].getRef().toLowerCase())) {
                // No se permite eliminar vinos con existencias
                if (vins[i].getEstoc() > 0) {
                    return null;  // Rechazar eliminación por tener stock
                }

                // Guardar referencia para devolverla
                Vi viEliminat = vins[i];
                vins[i] = null;  // Eliminar el vino
                return viEliminat;  // Devolver el vino eliminado
            }
        }

        return null;  // El vino no fue encontrado
    }

    /**
     * Busca un vino por su referencia
     *
     * @param ref Referencia del vino a buscar
     * @return El vino encontrado o null si no existe
     */
    public Vi cerca(String ref) {
        // Normalizar la referencia para búsqueda (minúsculas y sin espacios extras)
        ref = Vi.normalitzaString(ref.toLowerCase());

        // Recorrer todos los vinos buscando coincidencia
        for (int i = 0; i < vins.length; i++) {
            // Comprobar coincidencia con referencia (ignorando mayúsculas/minúsculas)
            if (vins[i] != null && vins[i].getRef() != null
                    && ref.equals(vins[i].getRef().toLowerCase())) {
                return vins[i];  // Vino encontrado
            }
        }
        return null;  // No se encontró ningún vino con esa referencia
    }

    // <><><><><><> NAVEGACIÓN POR EL CATÁLOGO <><><><><><>
    /**
     * Reinicia el índice de recorrido al inicio de la colección
     */
    public void iniciaRecorregut() {
        recorregut = 0;  // Volver al primer elemento
    }

    /**
     * Obtiene el siguiente vino disponible en la colección Ignora posiciones
     * vacías automáticamente
     *
     * @return El siguiente vino o null si no quedan más
     */
    public Vi getSeguent() {
        // Avanzar mientras queden elementos por revisar
        while (recorregut < vins.length) {
            // Guardar el elemento actual y avanzar el índice
            Vi actual = vins[recorregut];
            recorregut++;  // Preparar para la próxima llamada

            // Devolver sólo si es un elemento válido
            if (actual != null) {
                return actual;  // Vino encontrado
            }
            // Si es nulo, el bucle continuará buscando
        }
        return null;  // No quedan más vinos
    }

    /**
     * Busca un vino que coincida con los criterios especificados en la
     * plantilla Cada propiedad de la plantilla actúa como filtro (AND lógico)
     *
     * - Las propiedades nulas o vacías no se consideran en la búsqueda - Para
     * precio y stock se busca que sean mayores o iguales
     *
     * @param plantilla Vino con las propiedades a buscar
     * @return El primer vino que cumple todos los criterios o null si no hay
     * coincidencias
     */
    public Vi cerca(Vi plantilla) {
        // ▓▓▓ BÚSQUEDA POR CRITERIOS MÚLTIPLES ▓▓▓
        for (Vi vi : vins) {
            // Ignorar posiciones vacías
            if (vi == null) {
                continue;
            }

            // ►► FILTROS DE TEXTO (comparación ignorando mayúsculas/minúsculas) ◄◄
            // Filtro por referencia
            if (plantilla.getRef() != null
                    && !plantilla.getRef().isEmpty()
                    && !plantilla.getRef().equalsIgnoreCase(vi.getRef())) {
                continue; // La referencia no coincide
            }

            // Filtro por nombre
            if (plantilla.getNom() != null
                    && !plantilla.getNom().isEmpty()
                    && !plantilla.getNom().equalsIgnoreCase(vi.getNom())) {
                continue; // El nombre no coincide
            }

            // ►► FILTROS NUMÉRICOS ◄◄
            // Filtro por precio (mayor o igual)
            if (plantilla.getPreu() >= 0 && plantilla.getPreu() < vi.getPreu()) {
                continue; // El precio es mayor que el buscado
            }

            // Filtro por stock (debe tener al menos la cantidad solicitada)
            if (plantilla.getEstoc() >= 0 && plantilla.getEstoc() > vi.getEstoc()) {
                continue; // El stock es menor que el solicitado
            }

            // ►► MÁS FILTROS DE TEXTO ◄◄
            // Filtro por lugar
            if (plantilla.getLloc() != null
                    && !plantilla.getLloc().isEmpty()
                    && !plantilla.getLloc().equalsIgnoreCase(vi.getLloc())) {
                continue; // El lugar no coincide
            }

            // Filtro por denominación de origen
            if (plantilla.getOrigen() != null
                    && !plantilla.getOrigen().isEmpty()
                    && !plantilla.getOrigen().equalsIgnoreCase(vi.getOrigen())) {
                continue; // La denominación de origen no coincide
            }

            // Filtro por tipo
            if (plantilla.getTipus() != null
                    && !plantilla.getTipus().isEmpty()
                    && !plantilla.getTipus().equalsIgnoreCase(vi.getTipus())) {
                continue; // El tipo no coincide
            }

            // Filtro por cosecha
            if (plantilla.getCollita() != null
                    && !plantilla.getCollita().isEmpty()
                    && !plantilla.getCollita().equalsIgnoreCase(vi.getCollita())) {
                continue; // La cosecha no coincide
            }

            // Si llegamos aquí, el vino cumple todos los criterios
            return vi;
        }
        return null; // No se encontró ningún vino que cumpla todos los criterios
    }
}