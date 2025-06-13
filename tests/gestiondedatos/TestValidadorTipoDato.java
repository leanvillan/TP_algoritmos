package gestiondedatos;

/**
 * estas son pruebas manuales para los validadores de tipo de dato
 * Se testea que cada validador acepte solo el tipo de valor esperado.
 * sirva para los validadores (ValidadorNumerico, ValidadorString,
 * ValidadorBooleano, ValidadorNA, ValidadorTipoDato)
 */
public class TestValidadorTipoDato {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Validadores de TipoDato ----");

        //ValidadorNumerico
        ValidadorNumerico valNum = new ValidadorNumerico();
        System.out.println("Numerico acepta 10: " + valNum.esValido(10)); // true
        System.out.println("Numerico acepta 3.14: " + valNum.esValido(3.14)); // true
        System.out.println("Numerico acepta 'texto': " + valNum.esValido("texto")); // false

        //validadorString
        ValidadorString valStr = new ValidadorString();
        System.out.println("String acepta 'hola': " + valStr.esValido("hola")); // true
        System.out.println("String acepta 42: " + valStr.esValido(42)); // false

        //ValidadorBooleano
        ValidadorBooleano valBool = new ValidadorBooleano();
        System.out.println("Booleano acepta true: " + valBool.esValido(true)); // true
        System.out.println("Booleano acepta false: " + valBool.esValido(false)); // true
        System.out.println("Booleano acepta 1: " + valBool.esValido(1)); // false

        //ValidadorNA
        ValidadorNA valNA = new ValidadorNA();
        System.out.println("NA acepta null: " + valNA.esValido(null)); // true
        System.out.println("NA acepta 'algo': " + valNA.esValido("algo")); // false

        //ValidadorTipoDato (centralizado)
        System.out.println("ValidadorTipoDato NUMERICO, valor 10: " +
                ValidadorTipoDato.esValido(10, TipoDato.NUMERICO, TipoDato.NUMERICO)); // true
        System.out.println("ValidadorTipoDato CADENA, valor 'abc': " +
                ValidadorTipoDato.esValido("abc", TipoDato.CADENA, TipoDato.CADENA)); // true
        System.out.println("ValidadorTipoDato BOOLEANO, valor false: " +
                ValidadorTipoDato.esValido(false, TipoDato.BOOLEANO, TipoDato.BOOLEANO)); // true
        System.out.println("ValidadorTipoDato NA, valor null: " +
                ValidadorTipoDato.esValido(null, TipoDato.NUMERICO, TipoDato.NA)); // true

        //Caso incorrecto
        System.out.println("ValidadorTipoDato NUMERICO, valor 'no es numero': " +
                ValidadorTipoDato.esValido("no es numero", TipoDato.NUMERICO, TipoDato.CADENA)); // false

        //caso de prueba para tipo de celda y columna no coinciden (y no es NA)
        System.out.println("ValidadorTipoDato CADENA vs NUMERICO: " +
                ValidadorTipoDato.esValido("abc", TipoDato.NUMERICO, TipoDato.CADENA)); // false
    }
}
