package filtros;

import gestiondedatos.TipoDato;
import java.util.Objects;

/**
 * La clase Distinto implementa la interfaz 'Filtro'.
 * Esta clase contiene la lógica para evaluar si el valor de un objeto 'Celda'
 * es distinto del valor con el cual debe compararse.
 */

public class Distinto implements Filtro {   ///  !=

private Object valorCompara;

    public Distinto(Object valorCompara) {
        this.valorCompara = valorCompara;
    }

    @Override
    public boolean cumple(Object valorCelda, TipoDato tipoDatoCelda) {

        ///  Una celda de tipo 'NA' no puede compararse. Su valor es 'null' y no podemos decir que es 'distinto'.
        if (tipoDatoCelda == TipoDato.NA) {
            return false;
        }
        if (tipoDatoCelda == TipoDato.NUMERICO) {
            if (valorCelda instanceof Number && valorCompara instanceof Number) {
                return ((Number) valorCelda).doubleValue() != ((Number) valorCompara).doubleValue();
            }
        } else if (tipoDatoCelda == TipoDato.CADENA) {
            if (valorCelda instanceof String && valorCompara instanceof String) {
                return !Objects.equals(valorCelda, valorCompara);
            }
        } else if (tipoDatoCelda == TipoDato.BOOLEANO) {
            if (valorCelda instanceof Boolean && valorCompara instanceof Boolean) {
                return !Objects.equals(valorCelda, valorCompara);
            }
        }
        return false;
    }
}