package visualizador;

import gestiondedatos.*;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * Clase que se encarga de imprimir una representación legible de una Tabla en consola.
 * Ajusta el ancho de columnas, limita la cantidad de columnas y filas mostradas,
 * y aplica formato.
 */

public class Visualizador {

    // Constantes de configuración visual

    private static final int PADDING = 4;                         // Espacio adicional por celda
    private static final int MAX_ANCHO_CELDA = 15;                // Ancho máximo de contenido visible por celda
    private static final String TRUNCATE_SUFFIX = "...";          // Indicador de truncamiento

    private static final int ANCHO_MAXIMO_CONSOLA = 100;          // Ancho simulado de consola
    private static final String ELLIPSIS_COLUMN_INDICATOR = "...";// Indicador visual de columnas ocultas
    private static final int ANCHO_FIJO_ELLIPSIS_COL =
            ELLIPSIS_COLUMN_INDICATOR.length() + PADDING + 2;     // Ancho reservado para el indicador

    private static final int MAX_FILAS_A_MOSTRAR = 20;            // Límite de filas visibles

    // Calcula el ancho óptimo de cada columna basado en el contenido de sus celdas y etiquetas.
    // El ancho se limita a MAX_ANCHO_CELDA.

    public static Map<Etiqueta, Integer> calcularAnchos(Tabla tabla,
                                                        List<Etiqueta> etiquetasColumnas) {
        Map<Etiqueta, Integer> anchosColumnas = new LinkedHashMap<>();

        for (Etiqueta etiquetaColumna : etiquetasColumnas) {
            int maxAncho = String.valueOf(etiquetaColumna.getValor()).length();

            Columna columna = tabla.getColumna(etiquetaColumna);
            if (columna != null) {
                for (int i = 0; i < columna.contarCeldas(); i++) {
                    Celda<?> celda = columna.getCelda(i);
                    maxAncho = Math.max(maxAncho, celda.toString().length());
                }
            }

            anchosColumnas.put(etiquetaColumna, Math.min(maxAncho, MAX_ANCHO_CELDA));
        }

        return anchosColumnas;
    }

    // Impresión de tabla principal

    public static void imprimirTabla(Tabla tabla) {
        if (tabla == null) {
            System.out.println("La tabla es nula y no puede ser visualizada.");
            return;
        }
        if (tabla.contarColumnas() == 0 && tabla.contarFilas() == 0) {
            System.out.println("La tabla está vacía.");
            return;
        }

        List<Etiqueta> columnas = tabla.getEtiquetasColumnas();

        // Cálculo de ancho máximo para la columna "FILA"
        int anchoEtiquetaFila = "FILA".length();
        for (Etiqueta etFila : tabla.getEtiquetasFilas()) {
            anchoEtiquetaFila = Math.max(anchoEtiquetaFila,
                    String.valueOf(etFila.getValor()).length());
        }

        int anchoEtiquetaFilaConPadding = anchoEtiquetaFila + PADDING;

        // Calcular anchos óptimos para todas las columnas
        Map<Etiqueta, Integer> anchos = calcularAnchos(tabla, columnas);

        // Determinar columnas que entran en el ancho máximo permitido
        List<Etiqueta> columnasVisibles = new ArrayList<>();
        int anchoAcumulado = anchoEtiquetaFilaConPadding;
        boolean hayColumnasOmitidas = false;

        for (Etiqueta et : columnas) {
            int anchoCol = anchos.get(et) + PADDING;
            if (anchoAcumulado + anchoCol + (hayColumnasOmitidas ? 0 : ANCHO_FIJO_ELLIPSIS_COL)
                    > ANCHO_MAXIMO_CONSOLA) {
                hayColumnasOmitidas = true;
                break;
            }
            columnasVisibles.add(et);
            anchoAcumulado += anchoCol;
        }

        // Imprimir cabecera
        imprimirCabecera(anchoEtiquetaFila, anchos, columnasVisibles, hayColumnasOmitidas);

        // Determinar cantidad de filas a imprimir
        int filasVisibles = Math.min(tabla.contarFilas(), MAX_FILAS_A_MOSTRAR);
        boolean hayFilasOmitidas = tabla.contarFilas() > MAX_FILAS_A_MOSTRAR;

        // Imprimir filas de datos
        for (int i = 0; i < filasVisibles; i++) {
            Etiqueta etFila = tabla.getEtiquetasFilas().get(i);
            System.out.printf("%-" + anchoEtiquetaFilaConPadding + "s",
                    formatearContenidoCelda(etFila.getValor().toString(), anchoEtiquetaFila));

            for (Etiqueta etCol : columnasVisibles) {
                int ancho = anchos.get(etCol);
                Celda<?> celda = tabla.getCelda(etCol, etFila);
                System.out.printf("%-" + (ancho + PADDING) + "s",
                        formatearContenidoCelda(celda.toString(), ancho));
            }

            if (hayColumnasOmitidas) {
                System.out.printf("%-" + ANCHO_FIJO_ELLIPSIS_COL + "s",
                        ELLIPSIS_COLUMN_INDICATOR);
            }
            System.out.println();
        }

        // Imprimir truncado si hay filas omitidas
        if (hayFilasOmitidas) {
            System.out.printf("%-" + anchoEtiquetaFilaConPadding + "s", TRUNCATE_SUFFIX);
            for (Etiqueta etCol : columnasVisibles) {
                int ancho = anchos.get(etCol);
                System.out.printf("%-" + (ancho + PADDING) + "s", "");
            }
            if (hayColumnasOmitidas) {
                System.out.printf("%-" + ANCHO_FIJO_ELLIPSIS_COL + "s", "");
            }
            System.out.println();
            System.out.println("... (" + (tabla.contarFilas() - MAX_FILAS_A_MOSTRAR) +
                    " filas más)");
        }

        System.out.println("\n");
    }

    // Métodos auxiliares de impresión

    // Imprime la fila de cabecera, incluyendo el nombre de las columnas visibles.
    public static void imprimirCabecera(int anchoEtiquetaFila,
                                        Map<Etiqueta, Integer> anchos,
                                        List<Etiqueta> columnasVisibles,
                                        boolean hayColumnasOmitidas) {

        System.out.printf("%-" + (anchoEtiquetaFila + PADDING) + "s", "FILA");

        for (Etiqueta et : columnasVisibles) {
            int ancho = anchos.get(et);
            System.out.printf("%-" + (ancho + PADDING) + "s",
                    formatearContenidoCelda(et.getValor().toString(), ancho));
        }

        if (hayColumnasOmitidas) {
            System.out.printf("%-" + ANCHO_FIJO_ELLIPSIS_COL + "s", ELLIPSIS_COLUMN_INDICATOR);
        }

        System.out.println();
    }

    // Recorta el contenido si excede el ancho máximo permitido y añade sufijo de truncado.
    public static String formatearContenidoCelda(String contenido, int anchoMaximo) {
        if (contenido == null) return "NA";

        if (contenido.length() > anchoMaximo) {
            int cortarEn = anchoMaximo - TRUNCATE_SUFFIX.length();
            if (cortarEn < 0) {
                return TRUNCATE_SUFFIX.substring(0, Math.min(anchoMaximo,
                        TRUNCATE_SUFFIX.length()));
            }
            return contenido.substring(0, cortarEn) + TRUNCATE_SUFFIX;
        }

        return contenido;
    }

    // Información general de la tabla

    // Imprime información básica de la tabla: cantidad de filas, columnas, y tipos por columna.
    public static void info(Tabla tabla) {
        if (tabla == null) {
            System.out.println("La tabla es nula. No contiene información.");
            return;
        }

        System.out.println("----- Información de la Tabla -----");
        System.out.println("Cantidad de Filas: " + tabla.contarFilas());
        System.out.println("Cantidad de Columnas: " + tabla.contarColumnas());
        System.out.println();

        for (Etiqueta et : tabla.getEtiquetasColumnas()) {
            TipoDato tipo = tabla.getTipoDatoColumna(et);
            System.out.println("- " + et.getValor() + ": " + tipo);
        }
    }
}
