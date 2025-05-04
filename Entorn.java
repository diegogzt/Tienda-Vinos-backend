/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   SISTEMA DE GESTIÓN DE BODEGA - INTERFAZ PRINCIPAL
   "Celler La Bona Estrella" - Módulo de interacción
   
   Este módulo implementa la interfaz de usuario para
   gestionar la bodega de vinos, permitiendo realizar
   operaciones como búsqueda, añadir, modificar y eliminar vinos.
  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/

 /* Importaciones necesarias para la gestión de archivos */
import java.io.BufferedReader;
/* Para lectura eficiente de archivos */
import java.io.BufferedWriter;
/* Para escritura eficiente de archivos */
import java.io.FileReader;
/* Para lectura de ficheros */
import java.io.FileWriter;
/* Para escritura de ficheros */
import java.io.IOException;
/* Para manejo de excepciones de E/S */
import java.io.File;

/* Para manejo de ficheros */

 /* Clase principal que maneja la interfaz de usuario para interactuar con la bodega */
public class Entorn {

    /* Instancia de la tienda que contiene los vinos - Núcleo del sistema */
    private final Botiga botiga = new Botiga();

    /*┌────────────────────────────────────────────┐
      │  MÉTODO PRINCIPAL - PUNTO DE ENTRADA       │
      │  Inicia el programa y maneja el ciclo      │
      │  principal de interacción con el usuario   │
      └────────────────────────────────────────────┘*/
    public static void main(String[] args) throws IOException {
        /* Muestra mensaje inicial al usuario */
        mostraBenvinguda();

        /* Configuración del archivo para persistencia de datos */
        String archiu = "botiga.csv";
        /* Nombre del archivo CSV */
        File fitxer = new File(archiu);
        /* Objeto para manipular el archivo */
        int referencies = 0;
        /* Contador de vinos cargados */

 /* Creación de la instancia principal del entorno */
        Entorn entorn = new Entorn();

        /* Carga de vinos desde archivo CSV si existe */
        if (fitxer.exists()) {
            /* Preparar el lector del archivo */
            BufferedReader read = new BufferedReader(new FileReader(fitxer));
            String linia;

            /* Procesar cada línea del archivo */
            while ((linia = read.readLine()) != null) {
                /* Separar los campos por punto y coma */
                String[] valors = linia.split(";");

                /* Convertir los valores a un objeto Vi */
                Vi vi = Vi.deArrayString(valors);

                /* Si la conversión fue exitosa, añadir el vino */
                if (vi != null) {
                    entorn.botiga.afegeix(vi);
                    /* Guardar en la bodega */
                    referencies++;
                    /* Incrementar contador */
                }
            }
            /* Cerrar el archivo tras la lectura */
            read.close();
        }

        /* Notificar al usuario cuántos vinos se cargaron */
        System.out.printf("Referències llegides: %d%n", referencies);

        /* CICLO PRINCIPAL DEL PROGRAMA - Procesa comandos del usuario */
        while (true) {
            /* Mostrar indicador de entrada y leer comando */
            mostraPrompt();
            String comanda = Entrada.readLine().strip();
            /* Leer eliminando espacios */

 /* Si no se introdujo ningún comando, volver a solicitar */
            if (comanda.isEmpty()) {
                continue;
            }

            /* Comando para salir de la aplicación */
            if (comanda.equals("surt")) {
                break;
                /* Salir del bucle principal */
            }

            /* Procesamiento de los diferentes comandos */
            switch (comanda) {
                case "ajuda":
                    /* Mostrar ayuda al usuario */
                    mostraAjuda();
                    break;
                case "afegeix":
                    /* Añadir un nuevo vino */
                    entorn.processaAfegeix();
                    break;
                case "cerca":
                    /* Buscar vinos con diferentes criterios */
                    entorn.processaCerca();
                    break;
                case "modifica":
                    /* Modificar un vino existente */
                    entorn.processaModifica();
                    break;
                case "elimina":
                    /* Eliminar un vino del inventario */
                    entorn.processaElimina();
                    break;
                default:
                    /* Comando no reconocido */
                    mostraErrorComandaDesconeguda();
            }
        }

        /* Despedirse del usuario antes de terminar */
        entorn.mostraComiat();
    }

    /*┌────────────────────────────────────────────┐
      │  MÉTODOS DE INTERFAZ Y MENSAJES           │
      │  Gestión de la comunicación con usuario   │
      └────────────────────────────────────────────┘*/
 /* Muestra el mensaje de bienvenida al iniciar el programa */
    public static void mostraBenvinguda() {
        System.out.println("Celler La Bona Estrella. Escriviu ajuda per veure opcions.");
    }

    /* Muestra el indicador de entrada para que el usuario sepa que puede escribir */
    public static void mostraPrompt() {
        System.out.print("botiga> ");
        /* Indicador de línea de comandos */
    }

    /* Muestra la lista de comandos disponibles para el usuario */
    public static void mostraAjuda() {
        System.out.printf("Comandes disponibles:\n"
                + "ajuda\n" /* Mostrar ayuda */
                + "cerca\n" /* Buscar vinos */
                + "surt\n");
        /* Salir del programa */
 /* Nota: algunos comandos como afegeix, modifica y elimina 
                   no se muestran porque están temporalmente deshabilitados */
    }

    /*┌────────────────────────────────────────────┐
      │  PROCESADORES DE COMANDOS                 │
      │  Métodos que implementan cada comando     │
      └────────────────────────────────────────────┘*/
 /* 
     * Procesa el comando para agregar un nuevo vino
     * Actualmente desactivado, sólo muestra un mensaje informativo 
     */
    public void processaAfegeix() {
        /* Esta función está deshabilitada temporalmente */
        System.out.println("Comanda temporalment no disponible");

        /* Código de implementación futura:
           - Solicitaría los datos del vino (nombre, precio, stock, etc.)
           - Validaría los datos introducidos
           - Crearía un objeto Vi con los datos
           - Lo añadiría a la bodega mediante botiga.afegeix()
         */
    }

    /* 
     * Procesa el comando para buscar vinos - Implementa dos modos:
     * 1. Búsqueda directa por referencia
     * 2. Búsqueda por múltiples criterios
     */
    public void processaCerca() {
        /* •·•·•·•·•· MODO 1: BÚSQUEDA POR REFERENCIA •·•·•·•·•· */
        System.out.print("ref> ");
        /* Solicitar referencia */
        String nomRef = Entrada.readLine().trim();

        /* Si se proporciona una referencia, buscar por ese criterio */
        if (!nomRef.isBlank()) {
            /* Buscar el vino en la bodega por su referencia */
            Vi cercat = botiga.cerca(nomRef);

            /* Si no se encuentra, salir silenciosamente */
            if (cercat == null) {
                return;
                /* No mostrar mensaje de error */
            }

            /* Mostrar el vino encontrado */
            System.out.print("Trobat:" + cercat);
            return;
            /* Finalizar búsqueda */
        }

        /* •·•·•·•·•· MODO 2: BÚSQUEDA POR CRITERIOS MÚLTIPLES •·•·•·•·•· */
 /* Definir los parámetros que se pueden usar como filtro */
        String[] nomParametres = {
            "nom> ", /* Nombre del vino */
            "preu max.> ", /* Precio máximo */
            "estoc min.> ", /* Stock mínimo disponible */
            "lloc> ", /* Lugar de origen */
            "D.O.> ", /* Denominación de origen */
            "tipus> ", /* Tipo de vino */
            "collita> " /* Año de cosecha */};

        /* Array para almacenar los valores de los filtros */
        String[] valorParametres = new String[7];
        String nomEntrada;

        /* Solicitar cada uno de los criterios de búsqueda */
        for (int i = 0; i < nomParametres.length; i++) {
            System.out.print(nomParametres[i]);
            /* Mostrar prompt */
            nomEntrada = Entrada.readLine();
            /* Leer entrada */

 /* '!' cancela la búsqueda por criterios */
            if (nomEntrada.equals("!")) {
                break;
                /* Salir del bucle de solicitud */
            }

            /* Si no se introduce valor, no aplicar este filtro */
            if (nomEntrada.isBlank()) {
                valorParametres[i] = null;
                /* Marcar criterio como no aplicable */
                continue;
            }

            /* Validar que los valores numéricos (precio y stock) sean enteros */
            if (i == 1 || i == 2) {
                /* Posiciones para precio y stock */
                if (!EsEnter.esEnter(nomEntrada)) {
                    System.out.println("ERROR: el valor ha de ser un enter positiu");
                    return;
                    /* Cancelar búsqueda si hay error */
                }
            }

            /* Guardar el criterio introducido */
            valorParametres[i] = nomEntrada;
        }

        /* Establecer valores predeterminados para los filtros numéricos */
        if (valorParametres[1] == null) {
            valorParametres[1] = "-1";
            /* Precio predeterminado: cualquiera */
        }
        if (valorParametres[2] == null) {
            valorParametres[2] = "-1";
            /* Stock predeterminado: cualquiera */
        }

        /* Crear un vino plantilla con los criterios de búsqueda */
        Vi plantilla = new Vi(
                "", /* Referencia (vacía para no filtrar por ella) */
                valorParametres[0], /* Nombre */
                Integer.parseInt(valorParametres[1]), /* Precio máximo */
                Integer.parseInt(valorParametres[2]), /* Stock mínimo */
                valorParametres[3], /* Lugar */
                valorParametres[4], /* Denominación de origen */
                valorParametres[5], /* Tipo */
                valorParametres[6] /* Cosecha */
        );

        /* Ejecutar la búsqueda con la plantilla de criterios */
        Vi cercat = botiga.cerca(plantilla);

        /* Si no hay resultados, salir silenciosamente */
        if (cercat == null) {
            return;
            /* No mostrar mensaje de error */
        }

        /* Mostrar el vino encontrado que cumple todos los criterios */
        System.out.print("Trobat:" + cercat);
    }

    /* 
     * Procesa el comando para modificar un vino existente
     * Actualmente desactivado, sólo muestra un mensaje informativo
     */
    public void processaModifica() {
        /* Esta función está deshabilitada temporalmente */
        System.out.println("Comanda temporalment no disponible");

        /* Código de implementación futura:
           - Solicitaría la referencia del vino a modificar
           - Buscaría el vino en la bodega
           - Permitiría cambiar sus datos (precio, stock, etc.)
           - Actualizaría el vino en la bodega
         */
    }

    /* 
     * Procesa el comando para eliminar un vino
     * Actualmente desactivado, sólo muestra un mensaje informativo
     */
    public void processaElimina() {
        /* Esta función está deshabilitada temporalmente */
        System.out.println("Comanda temporalment no disponible");

        /* Código de implementación futura:
           - Solicitaría la referencia del vino a eliminar
           - Verificaría que el vino no tenga stock (para permitir eliminación)
           - Eliminaría el vino de la bodega mediante botiga.elimina()
           - Informaría al usuario del resultado
         */
    }

    /* 
     * Muestra un mensaje de error cuando el comando no es reconocido
     * e indica cómo obtener ayuda
     */
    public static void mostraErrorComandaDesconeguda() {
        System.out.println("ERROR: comanda no reconeguda. "
                + "Escriviu 'ajuda' per ajuda");
    }

    /* 
     * Muestra un mensaje de despedida al finalizar el programa
     * También podría realizar tareas de limpieza y guardado de datos
     */
    public void mostraComiat() throws IOException {
        /* Mensaje de despedida */
        System.out.println("adéu");

        /* Aquí se podrían implementar:
           - Guardado de datos en archivo CSV
           - Liberación de recursos
           - Estadísticas de sesión
         */
    }
}
