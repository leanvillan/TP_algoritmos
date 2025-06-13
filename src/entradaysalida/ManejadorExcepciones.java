package entradaysalida;

/**
 * La clase 'ManejadorExcepciones' proporciona métodos estáticos para imprimir mensajes en consola, 
 * distinguiendo entre errores críticos y advertencias no fatales.
*/

public class ManejadorExcepciones {

    // Método estático que imprime un mensaje de error
    public static void lanzarExcepcion(String mensaje) {
        System.err.println("[ERROR] : " + mensaje);
    }

    // Método estático que imprime un mensaje de advertencia
    public static void lanzarAdvertencia(String mensaje) {
        System.err.println("[ADVERTENCIA] : " + mensaje);
    }
}
