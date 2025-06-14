package filtros;

import gestiondedatos.*;

/**
 * La clase 'FiltroOR' implementa FiltroLogico. Toma dos objetos 'Filtro' para ser instanciada.
 * Sobrescribe el método cumpleEnFila().Este mismo método se encarga de indentificar si en un fila , ambas celdas seleccionadas
 * cumplen o no con la condición de sus filtros internos.
 *
 */

public class FiltroOR implements FiltroLogico {
    private Filtro filtro1;
    private Filtro filtro2;

    public FiltroOR(Filtro filtro1, Filtro filtro2) {
        this.filtro1 = filtro1;
        this.filtro2 = filtro2;
    }

    @Override
    public boolean cumpleEnFila(Tabla tabla, Etiqueta etiquetaFila, Etiqueta etiquetaColumna1, Etiqueta etiquetaColumna2) {
        Celda<?> celda1 = tabla.getCelda(etiquetaColumna1, etiquetaFila);
        Celda<?> celda2 = tabla.getCelda(etiquetaColumna2, etiquetaFila);

        boolean cumpleCelda1 = filtro1.cumple(celda1.getValor(), celda1.getTipoDato());
        boolean cumpleCelda2 = filtro2.cumple(celda2.getValor(), celda2.getTipoDato());

        return cumpleCelda1 || cumpleCelda2; // si cumple 1 o cumple 2, resultado TRUE
    }
}