package visualizador;

import gestiondedatos.*;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * La clase Visualizador se encarga de imprimir una representación de una Tabla
 * en la consola, ajustando el ancho de las columnas y el número de columnas
 * y filas mostradas según un ancho máximo de consola y un límite de filas predefinidos.
 */

public class Visualizador {

    // Espacio fijo de relleno a la derecha para cada celda, mejora la legibilidad.
    private static final int PADDING = 4;
    // Ancho MÁXIMO deseado para el contenido de una celda.
    // Las columnas serán tan angostas como su contenido lo permita, pero nunca superarán este
    // límite máximo.
    private static final int MAX_ANCHO_CELDA = 15;
    // Sufijo utilizado para indicar que el contenido de una celda fue truncado.
    private static final String TRUNCATE_SUFFIX = "...";

    // Ancho máximo estimado de la ventana de la consola en caracteres.
    // Se utiliza para determinar cuántas columnas se pueden mostrar horizontalmente.
    private static final int ANCHO_MAXIMO_CONSOLA = 100;
    // Indicador visual para señalar que existen más columnas no mostradas debido a la
    // limitación de ancho de la consola.
    private static final String ELLIPSIS_COLUMN_INDICATOR = "...";
    // Ancho fijo asignado a la columna que muestra el indicador ELLIPSIS_COLUMN_INDICATOR.
    // Incluye la longitud del indicador, el padding y un espacio extra para asegurar su
    // visibilidad.
    private static final int ANCHO_FIJO_ELLIPSIS_COL = ELLIPSIS_COLUMN_INDICATOR.length() +
            PADDING + 2;

    // Cantidad máxima de filas a mostrar en la tabla. Si la tabla tiene más filas,
    // se truncará y se mostrará un indicador.
    private static final int MAX_FILAS_A_MOSTRAR = 20;

    // Calcula el ancho óptimo para cada columna de la tabla. El ancho se determina encontrando
    // la longitud máxima del contenido dentro de cada columna (considerando la etiqueta de la
    // cabecera y todas sus celdas), limitado a MAX_ANCHO_CELDA.

    public static Map<Etiqueta, Integer> calcularAnchos(Tabla tabla, List<Etiqueta>
            etiquetasColumnas) {
        Map<Etiqueta, Integer> anchosColumnas = new LinkedHashMap<>();

        for (Etiqueta etiquetaColumna : etiquetasColumnas) {
            // Inicializa el ancho máximo necesario con la longitud de la etiqueta de la columna.
            int maxAnchoNecesarioEnColumna = String.valueOf(etiquetaColumna.getValor()).length();

            Columna columna = tabla.getColumna(etiquetaColumna);
            if (columna != null) { // Asegura que la columna exista para evitar NullPointerException.
                // Itera sobre todas las celdas de la columna para encontrar la longitud máxima
                // de su contenido.
                for (int i = 0; i < columna.contarCeldas(); i++) {
                    Celda<?> celda = columna.getCelda(i);
                    maxAnchoNecesarioEnColumna = Math.max(maxAnchoNecesarioEnColumna,
                            celda.toString().length());
                }
            }

            // El ancho final de la columna será el mínimo entre el ancho óptimo necesario
            // y el MAX_ANCHO_CELDA. Esto permite que las columnas sean tan angostas como
            // su contenido lo permita, pero nunca más anchas que el máximo definido.
            int anchoFinalColumna = Math.min(maxAnchoNecesarioEnColumna, MAX_ANCHO_CELDA);
            anchosColumnas.put(etiquetaColumna, anchoFinalColumna);
        }
        return anchosColumnas;
    }

    // Imprime la fila de cabecera de la tabla, incluyendo la etiqueta "FILA" y las etiquetas
    // de las columnas visibles.

    public static void imprimirCabecera(int anchoEtiquetaFila, Map<Etiqueta, Integer>
                                                anchosColumnas, List<Etiqueta> etiquetasColumnasVisibles,
                                        boolean hayColumnasOmitidas) {

        // Imprime la etiqueta de la columna de fila con su ancho y padding.
        System.out.printf("%-" + (anchoEtiquetaFila + PADDING) + "s", "FILA");

        // Imprime las etiquetas de las columnas que son visibles.
        for (Etiqueta etiquetaColumna : etiquetasColumnasVisibles) {
            int ancho = anchosColumnas.get(etiquetaColumna);
            // Formatea el contenido de la cabecera también, para aplicar truncado si es necesario.
            System.out.printf("%-" + (ancho + PADDING) + "s",
                    formatearContenidoCelda(String.valueOf(etiquetaColumna.getValor()), ancho));
        }

        // Si hay columnas que fueron omitidas, imprime el indicador '...'.
        if (hayColumnasOmitidas) {
            System.out.printf("%-" + ANCHO_FIJO_ELLIPSIS_COL + "s", ELLIPSIS_COLUMN_INDICATOR);
        }
        System.out.println(); // Salto de línea al final de la cabecera.
    }

    // Formatea el contenido de una celda o etiqueta para asegurar que no exceda un ancho máximo
    // especificado, truncando y añadiendo un sufijo si es necesario.

    public static String formatearContenidoCelda(String contenido, int anchoMaximo) {
        if (contenido == null) {
            contenido = "NA"; // Reemplaza null con "NA".
        }
        // Si el contenido excede el ancho máximo, se trunca y se añade el sufijo.
        if (contenido.length() > anchoMaximo) {
            int cortarEn = anchoMaximo - TRUNCATE_SUFFIX.length();
            // Asegura que no se intente cortar en un índice negativo si el ancho es muy pequeño

            if (cortarEn < 0) {
                // Si el ancho máximo es menor que la longitud del sufijo, solo muestra una parte
                // del sufijo.
                return TRUNCATE_SUFFIX.substring(0, Math.min(anchoMaximo, TRUNCATE_SUFFIX.length()));
            }
            return contenido.substring(0, cortarEn) + TRUNCATE_SUFFIX;
        }
        return contenido;
    }

    // Imprime una tabla completa en la consola, ajustándose al ANCHO_MAXIMO_CONSOLA y limitando
    // la cantidad de filas a MAX_FILAS_A_MOSTRAR.

    public static void imprimirTabla(Tabla tabla) {
        if (tabla == null) {
            System.out.println("La tabla es nula y no puede ser visualizada.");
            return;
        }
        if (tabla.contarColumnas() == 0 && tabla.contarFilas() == 0) {
            System.out.println("La tabla está vacía.");
            return;
        }

        List<Etiqueta> etiquetasColumnasOriginales = tabla.getEtiquetasColumnas();

        // Calcula el ancho máximo necesario para la columna de etiquetas de fila ("FILA").
        int anchoEtiquetaFila = "FILA".length();
        if (tabla.contarFilas() > 0) {
            for (Etiqueta etiquetaFila : tabla.getEtiquetasFilas()) {
                anchoEtiquetaFila = Math.max(anchoEtiquetaFila,
                        String.valueOf(etiquetaFila.getValor()).length());
            }
        }
        // Incluye el padding para la columna de etiquetas de fila.
        int anchoEtiquetaFilaConPadding = anchoEtiquetaFila + PADDING;

        // Calcula los anchos óptimos para todas las columnas de datos de la tabla.
        Map<Etiqueta, Integer> anchosCalculadosDeTodasLasColumnas =
                calcularAnchos(tabla, etiquetasColumnasOriginales);

        // Lógica para determinar qué columnas se imprimirán basándose en el ANCHO_MAXIMO_CONSOLA.
        List<Etiqueta> etiquetasColumnasAImprimir = new ArrayList<>();
        // El ancho acumulado comienza con el espacio que ocupa la columna "FILA".
        int anchoActualAcumulado = anchoEtiquetaFilaConPadding;
        boolean hayColumnasOmitidas = false; // Indica si se omitieron columnas (horizontalmente).

        // Ancho fijo para el indicador de columnas omitidas.
        int anchoEllipsisCol = ANCHO_FIJO_ELLIPSIS_COL;

        // Itera sobre las columnas originales para seleccionar cuáles se mostrarán.
        for (Etiqueta etiquetaColumna : etiquetasColumnasOriginales) {
            // Calcula el ancho de la columna actual incluyendo su padding.
            int anchoDeEstaColumnaConPadding =
                    anchosCalculadosDeTodasLasColumnas.get(etiquetaColumna) + PADDING;

            // Comprueba si agregar esta columna (y el indicador '...' si todavía no se agregó)
            // excedería el ancho máximo de la consola. El 'hayColumnasOmitidas ? 0 : anchoEllipsisCol'
            // asegura que el espacio para '...' se considere solo una vez.
            if (anchoActualAcumulado + anchoDeEstaColumnaConPadding + (hayColumnasOmitidas ? 0 :
                    anchoEllipsisCol) > ANCHO_MAXIMO_CONSOLA) {
                hayColumnasOmitidas = true; // Marca que hay columnas omitidas.
                break; // Deja de añadir columnas, ya no hay más espacio.
            }

            etiquetasColumnasAImprimir.add(etiquetaColumna); // Añade la columna a la lista
            // de las que se imprimirán.
            anchoActualAcumulado += anchoDeEstaColumnaConPadding; // Actualiza el ancho acumulado.
        }

        // IMPRESIÓN DE LA CABECERA DE LA TABLA.
        imprimirCabecera(anchoEtiquetaFila, anchosCalculadosDeTodasLasColumnas,
                etiquetasColumnasAImprimir, hayColumnasOmitidas);

        // IMPRESIÓN DE LAS FILAS DE DATOS CON LÍMITE.
        // Calcula cuántas filas se deben imprimir, aplicando el límite MAX_FILAS_A_MOSTRAR.
        int filasAImprimir = Math.min(tabla.contarFilas(), MAX_FILAS_A_MOSTRAR);
        // Indica si hay filas adicionales que no se muestran (truncado vertical).
        boolean hayFilasOmitidas = tabla.contarFilas() > MAX_FILAS_A_MOSTRAR;

        for (int i = 0; i < filasAImprimir; i++) {
            Etiqueta etiquetaFila = tabla.getEtiquetasFilas().get(i);

            // Imprime la etiqueta de la fila actual.
            System.out.printf("%-" + anchoEtiquetaFilaConPadding + "s",
                    formatearContenidoCelda(String.valueOf(etiquetaFila.getValor()),
                            anchoEtiquetaFila));

            // Itera a través de las celdas de las columnas visibles para imprimirlas.
            for (int j = 0; j < etiquetasColumnasAImprimir.size(); j++) {
                Etiqueta etiquetaColumnaVisible = etiquetasColumnasAImprimir.get(j);
                int anchoCelda = anchosCalculadosDeTodasLasColumnas.get(etiquetaColumnaVisible);

                // Obtiene la celda directamente de la columna en el índice de fila 'i'.
                Celda<?> celda = tabla.getCelda(etiquetaColumnaVisible, etiquetaFila);

                // Formatea y imprime el contenido de la celda.
                String contenidoCelda = formatearContenidoCelda(celda.toString(), anchoCelda);
                System.out.printf("%-" + (anchoCelda + PADDING) + "s", contenidoCelda);
            }

            // Si hay columnas que fueron omitidas, imprime el indicador '...'.
            if (hayColumnasOmitidas) {
                System.out.printf("%-" + ANCHO_FIJO_ELLIPSIS_COL + "s",
                        ELLIPSIS_COLUMN_INDICATOR);
            }
            System.out.println(); // Salto de línea después de cada fila.
        }

        // Si se omitieron filas, imprime un indicador y el número de filas restantes.
        if (hayFilasOmitidas) {
            // Imprime un indicador "..." alineado bajo la columna "FILA" para mantener la estructura.
            System.out.printf("%-" + anchoEtiquetaFilaConPadding + "s", TRUNCATE_SUFFIX);
            // Rellena el resto de la fila con espacios para mantener la alineación de la tabla.
            for (int k = 0; k < etiquetasColumnasAImprimir.size(); k++) {
                System.out.printf("%-" + (anchosCalculadosDeTodasLasColumnas.get(etiquetasColumnasAImprimir.get(k)) + PADDING) + "s", "");
            }
            // Si también hay columnas omitidas horizontalmente, se deja el espacio para
            // el indicador de columnas.
            if (hayColumnasOmitidas) {
                System.out.printf("%-" + ANCHO_FIJO_ELLIPSIS_COL + "s", "");
            }
            System.out.println(); // Salto de línea después de la línea de puntos.
            // Mensaje informativo sobre la cantidad de filas omitidas.
            System.out.println("... (" + (tabla.contarFilas() - MAX_FILAS_A_MOSTRAR) +
                    " filas más)");
        }
        System.out.println("\n");
    }

    public static void info(Tabla tabla) {
        if (tabla == null) {
            System.out.println("La tabla es nula. No contiene información.");
            return;
        }

        // cantidad de filas
        // cantidad de columnas
        // etiquetas de columnas + tipo de dato

        List<Etiqueta> etiquetaColumnas = tabla.getEtiquetasColumnas();

        int filas = tabla.contarFilas();
        int columnas = tabla.contarColumnas();
        TipoDato tipo;
        System.out.println("-----Información de la Tabla-----");
        System.out.println("Cantidad de Filas: " + filas);
        System.out.println("Cantidad de Columnas: " + columnas);

        System.out.println("\n");

        for (Etiqueta etiquetaColumna : etiquetaColumnas) {
            tipo = tabla.getTipoDatoColumna(etiquetaColumna); // obtenemos el tipo de dato de esa columna por esa etiqueta
            System.out.println("-"+etiquetaColumna.getValor() + ": " + tipo);
        }
    }
}