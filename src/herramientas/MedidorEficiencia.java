package herramientas;

/**Ejecuta una tarea y mide cuanto tiempo tarda completarse
*/

public class MedidorEficiencia {

    public static void medirTiempo(String descripcion, Runnable tarea) {
        long inicio = System.nanoTime();
        tarea.run();
        long fin = System.nanoTime();
        double tiempo = (fin - inicio) / 1e9;
        System.out.println("Tiempo de " + descripcion + ": " + tiempo + " segundos");
    }

    public static long marcarInicio() {
        return System.currentTimeMillis();
    }

    public static void mostrarTiempoTotal(long inicio) {
        long fin = System.currentTimeMillis();
        double tiempo = (fin - inicio) / 1000.0;
        System.out.println("Tiempo total del programa: " + tiempo + " segundos");
    }

}
