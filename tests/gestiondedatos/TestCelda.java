package gestiondedatos;

/**
 * Esta es una clase de pruebas manuales para la clase Celda<T>.
 * con esto verificamos el correcto funcionamiento de la creación, consulta y copia de celdas.
 */

public class TestCelda {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Celda ----");

        //prueba 1: Crear una celda numérica (no nula)
        //creamos una celda con valor 10 y tipo NUMERICO.
        Celda<Integer> celdaNum = new Celda<>(10, TipoDato.NUMERICO);
        System.out.println("Valor: " + celdaNum.getValor());             //debería mostrar 10
        System.out.println("Tipo: " + celdaNum.getTipoDato());           //debería mostrar NUMERICO
        System.out.println("isNA: " + celdaNum.isNA());                  //debería mostrar false por no ser NA

        //prueba 2: Crea una celda NA (sin valor)
        //se usa el constructor sin parámetros, que crea una celda vacía (tipo NA, valor null).
        //El comodín <?> es porque no nos importa el tipo de dato (puede ser cualquiera o ninguno)
        Celda<?> celdaNA = new Celda<>();
        System.out.println("Valor: " + celdaNA.getValor());              //debería mostrar null
        System.out.println("Tipo: " + celdaNA.getTipoDato());            //debería mostrar NA
        System.out.println("isNA: " + celdaNA.isNA());                   //debería mostrar true

        //prueba 3: Copia de una celda ya existente
        //llamamos al método copiar() sobre una celda numérica
        //crea una nueva celda con el mismo valor y tipo que la original, pero independiente
        Celda<Integer> copia = celdaNum.copiar();
        System.out.println("Copia - Valor: " + copia.getValor() + " Tipo: " + copia.getTipoDato());
        //debería mostrar el mismo valor (10) y tipo (NUMERICO) que la original
    }
}
