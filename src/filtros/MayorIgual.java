package filtros;

import gestiondedatos.TipoDato;

/**
 * La clase 'MayorIgual' implementa la interfaz 'Filtro'.
 * Sobrescribe el metodo cumple(Object valorCelda, TipoDato tipoDatoCelda): boolean de la interfaz 'Filtro'
 * Este metodo permite identificar si el valor de la celda es Mayor o Igual al valor ingresado para comparar.
 */


public class MayorIgual implements Filtro {
    private Object valorCompara;

    public MayorIgual(Object valorCompara){
        this.valorCompara = valorCompara;
    }

    @Override
    public boolean cumple(Object valorCelda, TipoDato tipoDatoCelda) {
        // Primero, si es NA, no cumple ninguna condición numérica
        if (tipoDatoCelda == TipoDato.NA){
            return false;
        }
        if (tipoDatoCelda == TipoDato.NUMERICO && valorCelda instanceof Number && valorCompara instanceof Number) {
            return ((Number) valorCelda).doubleValue() >= ((Number) valorCompara).doubleValue(); // MAYOR O IGUAL AL NUMERO INGRESADO
        } else if (tipoDatoCelda == TipoDato.CADENA && valorCelda instanceof String && valorCompara instanceof String){
            boolean resultado = ((String) valorCelda).compareTo((String) valorCompara) >= 0;
            return resultado;
        }

        return false;
    }
}

