package filtros;
import gestiondedatos.Etiqueta;
import gestiondedatos.Tabla;

/**
 * Las clases que implementen la interfaz 'FiltroLogico' deberan sobreescribir
 * el metodo cumpleEnFila (Tabla tabla, Etiqueta etiquetaFila, Etiqueta etiquetaColumna1, Etiqueta etiquetaColumna2): boolean
 *
 * El proposito del metodo es evaluar que celdas de una misma fila cumplan con cierta condicion
 *
 */

public interface FiltroLogico {
    public boolean cumpleEnFila(Tabla tabla, Etiqueta etiquetaFila, Etiqueta etiquetaColumna1, Etiqueta etiquetaColumna2);
}
