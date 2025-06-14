package gestiondeerrores;
/**
 * tests para ManejadorExcepciones.
 * chequeamos que los mensajes se imprimen correctamente
 */
public class TestGestionDeErrores {
    public static void main(String[] args) {
        System.out.println("---- Prueba de Gestión de Errores ----");

        //test mensaje de error
        System.out.println("Lanzando un error:");
        GestionErrores.logError("Este es un mensaje de error de prueba.");

        //test mensaje de advertencia
        System.out.println("Lanzando una advertencia:");
        GestionErrores.logAdvertencia("Este es un mensaje de advertencia de prueba.");

        //test ExcepcionValidacion personalizada
        System.out.println("Lanzando excepción personalizada:");
        try {
            throw new ExcepcionValidacion("Error de validación lanzado manualmente.");
        } catch (ExcepcionValidacion e) {
            System.out.println("Se capturó la ExcepcionValidacion: " + e.getMessage());
        }

        //test ExcepcionOperacionNoValida personalizada
        System.out.println("Lanzando excepción de operación no válida:");
        try {
            throw new ExcepcionOperacionNoValida("Operación no válida lanzada manualmente.");
        } catch (ExcepcionOperacionNoValida e) {
            System.out.println("Se capturó la ExcepcionOperacionNoValida: " + e.getMessage());
        }
    }
}
