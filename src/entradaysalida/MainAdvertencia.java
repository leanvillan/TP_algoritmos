package entradaysalida;
import visualizador.Visualizador;
import gestiondedatos.*;
import gestiondeerrores.ExcepcionTabular;
import gestiondeerrores.GestionErrores;

public class MainAdvertencia {

    public static void main(String[] args) {
        try {
            // Crear gestor
            GestorCSV gestor = new GestorCSV();
            gestor.setUsarEncabezado(true);

            // Ruta del CSV que contiene errores de tipo
            String rutaEntrada = "D:\\Users\\Santino Silvetti\\Documents\\!1 UNSAM\\Algoritmos I\\!1TP FINAL\\errores_tipo.csv";    // Poner un csv con valores faltantes o errores de tipo

            // Cargar tabla desde archivo CSV
            Tabla tabla = gestor.cargarCSV(rutaEntrada);
            System.out.println("\n Tabla cargada desde: " + rutaEntrada);

            // Mostrar tabla cargada en consola
            Visualizador.imprimirTabla(tabla);

            // Guardar tabla en nuevo archivo
            String rutaSalida = "D:\\Users\\Santino Silvetti\\Documents\\!1 UNSAM\\Algoritmos I\\!1TP FINAL\\salida_con_errores.csv";
            gestor.guardarCSV(rutaSalida, tabla);

            System.out.println("\n Tabla guardada en: " + rutaSalida);

        } catch (ExcepcionTabular e) {
            GestionErrores.logError(e); // Imprime [ERROR]: y tu mensaje custom
        } catch (Exception e) {
            GestionErrores.logError("Error inesperado: " + e.getMessage());
        }
    }
}
