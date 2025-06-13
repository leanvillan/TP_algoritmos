package filtros;
import gestiondedatos.TipoDato;
import java.util.Objects; // Necesario para Objects.equals()

/**
 * La clase 'Igual' implementa la interfaz 'Filtro'. Sobrescribe el metodo cumple() y retornara
 * un resultado true o false dependiendo del tipo del valor a comparar.
 *
 */

public class Igual implements Filtro {

    private Object valorCompara;

    public Igual(Object valorCompara) {
        this.valorCompara = valorCompara;
    }

    @Override
    public boolean cumple(Object valorCelda, TipoDato tipoDatoCelda) {
        if (tipoDatoCelda == TipoDato.NA) {
            return false;
        }

        if (tipoDatoCelda == TipoDato.NUMERICO) {
            if (valorCelda instanceof Number && valorCompara instanceof Number) {
                return ((Number) valorCelda).doubleValue() == ((Number) valorCompara).doubleValue();
            }
        } else if (tipoDatoCelda == TipoDato.CADENA) {
            if (valorCelda instanceof String && valorCompara instanceof String) {
                return Objects.equals(valorCelda, valorCompara);
            }
        } else if (tipoDatoCelda == TipoDato.BOOLEANO) {
            if (valorCelda instanceof Boolean && valorCompara instanceof Boolean) {
                return Objects.equals(valorCelda, valorCompara);
            }
        }
        return false;
    }
}

