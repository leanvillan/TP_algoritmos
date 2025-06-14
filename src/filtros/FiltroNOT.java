package filtros;
import gestiondedatos.*;

/**
 * La clase 'FiltroNOT' toma un filtro interno y se encarga de indentificar si una celda
 * cumple con NO cumplir el filtro interno.
 *
 */


public class FiltroNOT{
    private Filtro filtroInterno;

    public FiltroNOT(Filtro filtroInterno) {
        this.filtroInterno = filtroInterno;
    }

    public boolean cumpleEnFila(Tabla tabla, Etiqueta etiquetaFila, Etiqueta etiquetaColumna) {
        Celda<?> celda = tabla.getCelda(etiquetaColumna, etiquetaFila);

        return !filtroInterno.cumple(celda.getValor(), celda.getTipoDato());
    }
}

