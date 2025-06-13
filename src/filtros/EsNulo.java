package filtros;

import gestiondedatos.*;
import gestiondedatos.TipoDato;

/**
 * La clase 'Esnulo' implementa la interfaz 'FiltroSinParametro'
 * Esta clase contiene la logica para saber si la celda de una fila y columna especifica cumple
 * con que la celda sea de tipo NA.
 * No necesita de parametros para ser instanciada.
 *
 */

public class EsNulo implements FiltroSinParametro {
    @Override
    public boolean cumpleEnFila(Tabla tabla,Etiqueta etiquetaFila, Etiqueta etiquetaColumna) {
        Celda<?> celda = tabla.getCelda(etiquetaColumna, etiquetaFila);
        return celda.getTipoDato() == TipoDato.NA;

    }
}
