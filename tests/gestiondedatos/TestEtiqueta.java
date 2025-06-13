package gestiondedatos;

/**
 * estas son pruebas manuales para la clase Etiqueta (String y Entero).
 * acá probamos creación, comparación, copia y métodos básicos
 */
public class TestEtiqueta {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Etiqueta ----");


        System.out.println("1er prueba: crear EtiquetaString");
        //1er prueba: crear EtiquetaString
        EtiquetaString et1 = new EtiquetaString("ColumnaA");
        System.out.println("EtiquetaString: " + et1.getValor());

        System.out.println("2da prueba: crear EtiquetaEntero");
        //2da prueba: crear EtiquetaEntero
        EtiquetaEntero et2 = new EtiquetaEntero(5);
        System.out.println("EtiquetaEntero: " + et2.getValor());

        System.out.println("3er prueba: comparar dos EtiquetasString iguales");
        //3er prueba: comparar dos EtiquetasString iguales
        EtiquetaString et3 = new EtiquetaString("ColumnaA");
        System.out.println("¿et1.equals(et3)? " + et1.equals(et3)); // true

        System.out.println("prueba 4: copiar EtiquetaString");
        //prueba 4: copiar EtiquetaString
        EtiquetaString copiaEt1 = (EtiquetaString) et1.copiar();
        System.out.println("Copia EtiquetaString: " + copiaEt1.getValor() + " ¿Igual original? " + copiaEt1.equals(et1));

        System.out.println("5ta prueba: hashCode");
        //5ta prueba: hashCode
        System.out.println("hashCode et1: " + et1.hashCode() + ", hashCode copia: " + copiaEt1.hashCode());

        System.out.println("6ta prueba: toString");
        //6ta prueba: toString
        System.out.println("toString et1: " + et1.toString());
        System.out.println("toString et2: " + et2.toString());


        System.out.println("7ma prueba:  Intentamos crear EtiquetaString vacía (debería lanzar una excepción)");
        //7ma prueba:  Intentamos crear EtiquetaString vacía (debería lanzar una excepción)
        try {
            EtiquetaString etVacia = new EtiquetaString("");
            System.out.println("ERROR: Se creó una etiqueta vacía.");
        } catch (gestiondeerrores.ExcepcionValidacion e) {
            System.out.println("OK: No se pudo crear EtiquetaString vacía. Mensaje: " + e.getMessage());
        }


        System.out.println("8va prueba: intentamos crear EtiquetaEntero nula (debería lanzar excepción)");
        //8va prueba: intentamos crear EtiquetaEntero nula (debería lanzar excepción)
        try {
            EtiquetaEntero etNull = new EtiquetaEntero(null);
            System.out.println("ERROR: Se creó una etiqueta entero nula.");
        } catch (gestiondeerrores.ExcepcionValidacion e) {
            System.out.println("OK: No se pudo crear EtiquetaEntero nula. Mensaje: " + e.getMessage());
        }
    }
}
