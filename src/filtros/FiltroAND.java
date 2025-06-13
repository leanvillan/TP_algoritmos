package filtros;
import gestiondedatos.Celda;
import gestiondedatos.Tabla;
import gestiondedatos.Etiqueta;

/**
 * La clase 'FiltroAND' implementa la interfaz 'FiltroLogico'
 *
 * Para utilizar esta clase, se necesita instanciarla con dos objetos de tipo Filtro.
 * Sobrescribe el metodo 'cumpleEnFila', donde toma las celdas que deben cumplir con los filtros
 *
 */


public class FiltroAND implements FiltroLogico {
    private Filtro filtro1;
    private Filtro filtro2;

    public FiltroAND(Filtro filtro1, Filtro filtro2) {
        this.filtro1 = filtro1;
        this.filtro2 = filtro2;
    }
    @Override
    public boolean cumpleEnFila(Tabla tabla, Etiqueta etiquetaFila, Etiqueta etiquetaColumna1, Etiqueta etiquetaColumna2) {
        Celda<?> celda1 = tabla.getCelda(etiquetaColumna1, etiquetaFila); /// observo en la columna 1
        Celda<?> celda2 = tabla.getCelda(etiquetaColumna2, etiquetaFila); /// observo en la columna 2

        boolean cumpleCelda1 = filtro1.cumple(celda1.getValor(), celda1.getTipoDato());
        boolean cumpleCelda2 = filtro2.cumple(celda2.getValor(), celda2.getTipoDato());
        /// Retornara verdadero si ambas celdas cumplen con la condicion de sus filtros internos.
        return cumpleCelda1 && cumpleCelda2;
    }
}