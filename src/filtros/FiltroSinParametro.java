package filtros;

import gestiondedatos.Etiqueta;
import gestiondedatos.Tabla;

/**
 * La interfaz 'FiltroSinParemtro' esta diseñada para ser implementada
 * por filtros cuya instanciacion no requiere pasar por parametro filtros internos o valores a comparar.
 *
 */

public interface FiltroSinParametro {

    public boolean cumpleEnFila(Tabla tabla, Etiqueta etiquetaFila, Etiqueta etiquetaColumna);
}
