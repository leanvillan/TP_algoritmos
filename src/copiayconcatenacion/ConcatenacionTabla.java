package copiayconcatenacion;

import gestiondedatos.*;
import gestiondeerrores.ExcepcionValidacion;
import java.util.List;
import java.util.ArrayList;

// Clase que permite concatenar dos tablas en forma vertical.
// Se asegura de que las columnas coincidan en estructura, etiquetas y tipo.

public class ConcatenacionTabla {

    public static Tabla concatenarTablas(Tabla tabla1, Tabla tabla2) {
        if (tabla1 == null) {
            throw new ExcepcionValidacion("La primera tabla no puede ser nula para la concatenación.");
        }
        if (tabla2 == null) {
            throw new ExcepcionValidacion("La segunda tabla no puede ser nula para la concatenación.");
        }

        // Validar que ambas tablas tengan la misma estructura de columnas
        if (!columnasCoinciden(tabla1, tabla2)) {
            throw new ExcepcionValidacion("Las columnas de ambas tablas no coinciden. No se puede concatenar.");
        }

        Tabla tablaResultante = new Tabla();

        // Crear columnas vacías con las etiquetas de la primera tabla
        for (Etiqueta etCol : tabla1.getEtiquetasColumnas()) {
            TipoDato tipo = tabla1.getTipoDatoColumna(etCol);
            Columna columnaVacia = new Columna(tipo);
            tablaResultante.agregarColumna(etCol, columnaVacia);
        }

        // Agregar todas las filas de ambas tablas
        copiarFilasEnTabla(tabla1, tablaResultante, 0);
        copiarFilasEnTabla(tabla2, tablaResultante, tabla1.contarFilas());

        return tablaResultante;
    }

    // Copia filas desde una tabla origen hacia una tabla destino, aplicando copia profunda.
    // También genera etiquetas numéricas si es necesario.
    private static void copiarFilasEnTabla(Tabla tablaOrigen, Tabla tablaDestino,
                                           int indiceInicial) {
        int indiceActual = indiceInicial;

        for (Etiqueta etiquetaFilaOriginal : tablaOrigen.getEtiquetasFilas()) {
            List<Celda<?>> filaOriginal = tablaOrigen.getFila(etiquetaFilaOriginal);

            // Crear copia profunda de la fila
            List<Celda<?>> filaCopia = new ArrayList<>();
            for (Celda<?> celda : filaOriginal) {
                filaCopia.add(celda.copiar());
            }

            // Usar la misma etiqueta si es String, o generar una nueva si es Entero
            Etiqueta etiquetaParaAgregar = (etiquetaFilaOriginal instanceof EtiquetaString)
                    ? etiquetaFilaOriginal
                    : new EtiquetaEntero(indiceActual);

            tablaDestino.agregarFila(etiquetaParaAgregar, filaCopia);
            indiceActual++;
        }
    }

    // Verifica que dos tablas tengan columnas compatibles: misma cantidad, mismo orden,
    // mismas etiquetas y mismos tipos de datos.
    private static boolean columnasCoinciden(Tabla tabla1, Tabla tabla2) {
        if (tabla1.contarColumnas() != tabla2.contarColumnas()) {
            System.out.println("La cantidad de columnas debe ser la misma para concatenar (" +
                    tabla1.contarColumnas() + " vs " + tabla2.contarColumnas() + ").");
            return false;
        }

        List<Etiqueta> etiquetasCol1 = tabla1.getEtiquetasColumnas();
        List<Etiqueta> etiquetasCol2 = tabla2.getEtiquetasColumnas();

        for (int i = 0; i < etiquetasCol1.size(); i++) {
            Etiqueta etiq1 = etiquetasCol1.get(i);
            Etiqueta etiq2 = etiquetasCol2.get(i);

            if (!etiq1.equals(etiq2)) {
                System.out.println("Las etiquetas de columna en la posición " + i +
                        " no coinciden: '" + etiq1 + "' vs '" + etiq2 + "'.");
                return false;
            }

            TipoDato tipo1 = tabla1.getTipoDatoColumna(etiq1);
            TipoDato tipo2 = tabla2.getTipoDatoColumna(etiq2);
            if (tipo1 != tipo2) {
                System.out.println("Tipos de datos incompatibles en columna '" + etiq1 +
                        "': " + tipo1 + " vs " + tipo2);
                return false;
            }
        }

        return true;
    }
}
