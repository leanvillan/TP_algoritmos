package filtros;
import gestiondedatos.*;
import gestiondeerrores.ExcepcionOperacionNoValida;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase 'TablaParcial' tiene un método 'static' crearTablaParcial, quien recibe una lista de etiquetas de filas,
 * una lista de etiquetas de columnas y un objeto 'Tabla' (donde provienen estas etiquetas).
 *
 * Retorna una tabla, que contiene una seleccion parcial de filas y columnas originales.
 */

public class TablaParcial {

    public static Tabla crearTablaParcial(Tabla tablaOriginal, List<Etiqueta> etiquetasFilasSeleccionadas, List<Etiqueta> etiquetasColumnasSeleccionadas) {

        if(tablaOriginal == null){
            throw new ExcepcionOperacionNoValida("La tabla ingresada no puede ser nula");
        }
        if (etiquetasFilasSeleccionadas == null || etiquetasFilasSeleccionadas.isEmpty()) {
            throw new ExcepcionOperacionNoValida("No se especificaron etiquetas de fila para la selección. No se puede realizar la operación.");
        }
        if (etiquetasColumnasSeleccionadas == null || etiquetasColumnasSeleccionadas.isEmpty()) {
            throw new ExcepcionOperacionNoValida("No se especificaron etiquetas de columna para la selección. No se puede realizar la operación.");
        }

        Tabla tablaParcial = new Tabla();

        for (Etiqueta etiquetaColumna : etiquetasColumnasSeleccionadas) {

            if (!tablaOriginal.getEtiquetasColumnas().contains(etiquetaColumna)) {
                throw new ExcepcionOperacionNoValida("La etiqueta de columna ('" + etiquetaColumna.getValor() + "') no existe en la tabla original.");
            }

            Columna columnaOriginal = tablaOriginal.getColumna(etiquetaColumna);
            TipoDato tipoColumna = columnaOriginal.getTipoDato();
            List<Celda<?>> celdasColumnaNueva = new ArrayList<>();

            for (Etiqueta etiquetaFila : etiquetasFilasSeleccionadas) {

                if (!(tablaOriginal.getEtiquetasFilas().contains(etiquetaFila))) {
                    throw new ExcepcionOperacionNoValida("La etiqueta de fila ('" + etiquetaFila.getValor() + "') no existe en la tabla original.");
                }
                Celda<?> celdaOriginal = tablaOriginal.getCelda(etiquetaColumna, etiquetaFila);
                celdasColumnaNueva.add(celdaOriginal);
            }
            tablaParcial.agregarColumna(etiquetaColumna, new Columna(tipoColumna, celdasColumnaNueva));
        }

        tablaParcial.agregarEtiquetasFila(new ArrayList<>(etiquetasFilasSeleccionadas));
        return tablaParcial;
    }
}