package testsdocumentacion;

import gestiondedatos.*;

public class TestColumna {
    public static void main(String[] args) {
        //Creamos una columna numérica
        Columna col = new Columna(TipoDato.NUMERICO);

        //Agregamos una celda numérica
        col.agregarCelda(new Celda<Integer>(20, TipoDato.NUMERICO));
        col.agregarCelda(new Celda<Integer>(35, TipoDato.NUMERICO));

        //Imprimimos el contenido de la columna
        System.out.println("Tipo de dato de la columna: " + col.getTipoDato());
        System.out.println("Cantidad de celdas: " + col.contarCeldas());

        for (Celda<?> celda : col.getCeldas()) {
            System.out.println("Celda: " + celda);
        }
    }
}
