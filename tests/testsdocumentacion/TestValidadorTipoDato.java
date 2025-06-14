package testsdocumentacion;

import gestiondedatos.*;

public class TestValidadorTipoDato {
    public static void main(String[] args) {
        //Ejemplo de validaciones
        boolean valido1 = ValidadorTipoDato.esValido("texto", TipoDato.CADENA, TipoDato.CADENA);
        boolean valido2 = ValidadorTipoDato.esValido(123, TipoDato.NUMERICO, TipoDato.NUMERICO);
        boolean valido3 = ValidadorTipoDato.esValido(true, TipoDato.BOOLEANO, TipoDato.BOOLEANO);
        boolean valido4 = ValidadorTipoDato.esValido(null, TipoDato.CADENA, TipoDato.NA);
        boolean valido5 = ValidadorTipoDato.esValido("no es numero", TipoDato.NUMERICO, TipoDato.CADENA);

        System.out.println("¿'texto' es válido para CADENA?: " + valido1);      // true
        System.out.println("¿123 es válido para NUMERICO?: " + valido2);        // true
        System.out.println("¿true es válido para BOOLEANO?: " + valido3);       // true
        System.out.println("¿null es válido para NA?: " + valido4);             // true
        System.out.println("¿'no es numero' es válido para NUMERICO?: " + valido5); // false
    }
}
