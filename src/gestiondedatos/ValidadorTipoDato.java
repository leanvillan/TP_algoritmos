package gestiondedatos;

import java.util.HashMap;
import java.util.Map;

public class ValidadorTipoDato {

    private static final Map<TipoDato, TipoValidador> validadores = new HashMap<>();

    static {
        validadores.put(TipoDato.NUMERICO, new ValidadorNumerico());
        validadores.put(TipoDato.CADENA, new ValidadorString());
        validadores.put(TipoDato.BOOLEANO, new ValidadorBooleano());
        validadores.put(TipoDato.NA, new ValidadorNA());
    }

    public static boolean esValido(Object valor, TipoDato tipoDatoColumna,
                                   TipoDato tipoDatoCelda) {

        // Si el valor es nulo:
        if (valor == null) {
            // Retorna true si el tipo de la celda es NA (celda vacía intencionalmente)
            // Retorna false si el tipo de la celda NO es NA (valor nulo inesperado para un tipo definido)
            return tipoDatoCelda == TipoDato.NA;
        }

        if (tipoDatoCelda != TipoDato.NA && tipoDatoCelda != tipoDatoColumna) {
            return false;
        }

        TipoValidador validadorEsperado = validadores.get(tipoDatoColumna);

        if (validadorEsperado == null) {
            return false;
        }

        return validadorEsperado.esValido(valor);
    }
}