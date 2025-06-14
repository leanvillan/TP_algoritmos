package gestiondedatos;

/**
 * esta es una clase de pruebas manuales para la clase Columna.
 * ejecutamos pruebas básicas de creación, agregado, validación y copia de columnas.
 */

public class TestColumna {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Columna ----");

        //prueba 1: Creación de una columna vacía de tipo NUMERICO
        Columna colNum = new Columna(TipoDato.NUMERICO);
        System.out.println("Tipo columna: " + colNum.getTipoDato()); // NUMERICO
        System.out.println("Cantidad de celdas: " + colNum.contarCeldas()); // 0

        //prueba 2: Agregamos celdas válidas (números)
        colNum.agregarCelda(new Celda<>(15, TipoDato.NUMERICO));
        colNum.agregarCelda(new Celda<>(20, TipoDato.NUMERICO));
        System.out.println("Celdas luego de agregar dos valores: " + colNum.contarCeldas()); // daría 2

        //prueba 3_ Agregamos celda NA (debe ser aceptada)
        colNum.agregarCelda(new Celda<>());
        System.out.println("Celdas después de agregar NA: " + colNum.contarCeldas()); // daría 3

        //prueba 4: intentamos agregar celda de tipo incompatible (por ejemplo, String)
        try {
            colNum.agregarCelda(new Celda<>("No es número", TipoDato.CADENA));
            System.out.println("ERROR: Se agregó una celda inválida.");
        } catch (gestiondeerrores.ExcepcionValidacion e) {
            System.out.println("OK: No se pudo agregar celda de tipo incorrecto. Mensaje: " + e.getMessage());
        }

        //prueba 5: Copiar columna
        Columna copiaCol = colNum.copiar();
        System.out.println("Copia - Tipo: " + copiaCol.getTipoDato() + ", Cantidad de celdas: " + copiaCol.contarCeldas());

        //prueba6: eliminar una celda por índice
        copiaCol.eliminarCelda(1);
        System.out.println("Copia tras eliminar celda en índice 1. Cantidad de celdas: " + copiaCol.contarCeldas());

        //prueba 7: intentar eliminar una celda fuera de rango
        try {
            copiaCol.eliminarCelda(10);
            System.out.println("ERROR: Se eliminó una celda fuera de rango.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("OK: No se pudo eliminar fuera de rango. Mensaje: " + e.getMessage());
        }
    }
}
