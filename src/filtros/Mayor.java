package filtros;

import gestiondedatos.TipoDato;

/**
 * La clase 'Mayor' implementa la interfaz 'Filtro'.
 * Sobrescribe el metodo cumple(Object valorCelda, TipoDato tipoDatoCelda): boolean de la interfaz 'Filtro'
 * Este metodo permite identificar si el valor a comparar (Numerico o String) es mayor a el valor que
 * se encuentra dentro del objeto 'Celda'.
 *
 */

public class Mayor implements Filtro {

    private Object valorCompara;

    public Mayor(Object valorCompara){ // Constructor para el valor de comparación
        this.valorCompara = valorCompara;
    }

    @Override
    public boolean cumple(Object valorCelda, TipoDato tipoDatoCelda) {
        // Primero, si es NA, no cumple ninguna condición numérica
        if (tipoDatoCelda == TipoDato.NA){
            return false;
        }
        if (tipoDatoCelda == TipoDato.NUMERICO && valorCelda instanceof Number && valorCompara instanceof Number) {
            return ((Number) valorCelda).doubleValue() > ((Number)valorCompara).doubleValue();
        } else if(tipoDatoCelda == TipoDato.CADENA && valorCelda instanceof String && valorCompara instanceof String){
            boolean resultado = ((String) valorCelda).compareTo((String) valorCompara) > 0;
            return resultado;
        }
        return false;
    }
}