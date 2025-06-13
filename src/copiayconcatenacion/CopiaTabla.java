package copiayconcatenacion;

import gestiondedatos.*;

// Clase que permite realizar una copia profunda de una tabla.

public class CopiaTabla {

    public static Tabla copiarTabla(Tabla tablaOriginal) {
        if (tablaOriginal == null) {
            throw new IllegalArgumentException("La tabla original no puede ser nula " +
                    "para realizar una copia.");
        }

        Tabla tablaCopia = new Tabla();

        // Copiar etiquetas de filas (copias independientes)
        for (Etiqueta etiquetaFilaOriginal : tablaOriginal.getEtiquetasFilas()) {
            tablaCopia.getEtiquetasFilas().add(etiquetaFilaOriginal.copiar());
        }

        // Copiar columnas (estructura + celdas internas)
        for (Etiqueta etiquetaColumnaOriginal : tablaOriginal.getEtiquetasColumnas()) {
            Columna columnaOriginal = tablaOriginal.getColumna(etiquetaColumnaOriginal);

            if (columnaOriginal != null) {
                Columna nuevaColumna = columnaOriginal.copiar();
                tablaCopia.agregarColumna(etiquetaColumnaOriginal.copiar(), nuevaColumna);
            }
        }

        return tablaCopia;
    }
}
