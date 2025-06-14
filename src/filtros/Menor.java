package filtros;

import gestiondedatos.TipoDato;

/**
 * La clase 'Menor' implementa la interfaz 'Filtro'.
 * Sobrescribe el método cumple(Object valorCelda, TipoDato tipoDatoCelda): boolean de la interfaz 'Filtro'
 * Este metodo permite identificar si el valor de la celda es menor al valor ingresado para comparar.
 *
 */


public class Menor implements Filtro {
    private Object valorCompara;

    public Menor(Object valorCompara) {
        this.valorCompara = valorCompara;
    }

    @Override
    public boolean cumple(Object valorCelda, TipoDato tipoDatoCelda){
        // Primero, si es NA, no cumple ninguna condición numérica
        if (tipoDatoCelda == TipoDato.NA){
            return false;
        }
        if (tipoDatoCelda == TipoDato.NUMERICO && valorCelda instanceof Number){
            return ((Number) valorCelda).doubleValue() < ((Number) valorCompara).doubleValue();
        } else if(tipoDatoCelda == TipoDato.CADENA && valorCelda instanceof String && valorCompara instanceof String){
            // Utilizamos el metedo compareTo de la clase String para saber si cumple con la condicion
            boolean resultado = ((String) valorCelda).compareTo((String) valorCompara) < 0;
            return resultado;
        }
        return false;
    }
}
