package testsdocumentacion;

import gestiondedatos.*;

public class TestCelda {
    public static void main(String[] args) {
        //Creamos una celda numérica
        Celda<Integer> celdaEdad = new Celda<>(25, TipoDato.NUMERICO);

        //Imprimimos información de la celda
        System.out.println("Valor: " + celdaEdad.getValor());
        System.out.println("Tipo de dato: " + celdaEdad.getTipoDato());
        System.out.println("¿Es NA?: " + celdaEdad.isNA());
        System.out.println("toString(): " + celdaEdad);
    }
}
