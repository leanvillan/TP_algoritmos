package gestiondeerrores;

//Clase utilitaria para el manejo centralizado de errores y advertencias.

public class GestionErrores {

    public static void logError(Exception ex) {
        System.err.println("[ERROR]: " + ex.getMessage());
    }

    public static void logError(String mensaje) {
        System.err.println("[ERROR]: " + mensaje);
    }

    public static void logAdvertencia(String mensaje) {
        System.err.println("[ADVERTENCIA]: " + mensaje);
    }
}
