package seleccion;

import gestiondedatos.*;
import gestiondeerrores.*;
import java.util.Arrays;

public class MainSeleccionPrueba {
    public static void main(String[] args) {
        try {
            // Armar una tabla simple
            Tabla tabla = new Tabla();
            Columna colNumerica = new Columna(TipoDato.NUMERICO);
            Columna colCadena = new Columna(TipoDato.CADENA);

            tabla.agregarColumna(new EtiquetaString("ID"), colNumerica);
            tabla.agregarColumna(new EtiquetaString("Nombre"), colCadena);

            // Agrego dos filas
            tabla.agregarFila(new EtiquetaString("F1"), Arrays.asList(
                    new Celda<>(1, TipoDato.NUMERICO),
                    new Celda<>("Juan", TipoDato.CADENA)
            ));
            tabla.agregarFila(new EtiquetaString("F2"), Arrays.asList(
                    new Celda<>(2, TipoDato.NUMERICO),
                    new Celda<>("María", TipoDato.CADENA)
            ));

            // PRUEBA 1: HEAD VÁLIDO
            System.out.println("PRUEBA 1: Head válido");
            Seleccion seleccion = new Seleccion(tabla);
            seleccion.head(1);

            // PRUEBA 2: HEAD INVÁLIDO (más filas de las que existen)
            System.out.println("\nPRUEBA 2: Head con demasiadas filas (debe lanzar error)");
            try {
                seleccion.head(5); // Solo hay 2 filas
            } catch (ExcepcionOperacionNoValida e) {
                GestionErrores.logError(e);
            }

            // PRUEBA 3: Seleccionar etiqueta de fila inexistente
            System.out.println("\nPRUEBA 3: Selección con etiqueta de fila inexistente");
            try {
                seleccion.seleccionar(
                        Arrays.asList(new EtiquetaString("NOEXISTE")),
                        tabla.getEtiquetasColumnas()
                );
            } catch (ExcepcionOperacionNoValida e) {
                GestionErrores.logError(e);
            }

            // PRUEBA 4: Selección con columna inexistente
            System.out.println("\nPRUEBA 4: Selección con columna inexistente");
            try {
                seleccion.seleccionar(
                        tabla.getEtiquetasFilas(),
                        Arrays.asList(new EtiquetaString("NOEXISTE"))
                );
            } catch (ExcepcionOperacionNoValida e) {
                GestionErrores.logError(e);
            }

        } catch (Exception e) {
            GestionErrores.logError("Error inesperado: " + e.getMessage());
        }
    }
}
