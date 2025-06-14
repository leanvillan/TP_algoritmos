package seleccion;

import gestiondedatos.*;
import herramientas.MedidorEficiencia;

import java.util.*;

public class TestSeleccion {
    public static void main(String[] args) {
        System.out.println("---- Prueba de Seleccion ----");

        //creamos una tabla con 5 filas
        Tabla tabla = new Tabla();
        EtiquetaString etCol1 = new EtiquetaString("Nombre");
        EtiquetaString etCol2 = new EtiquetaString("Edad");

        Columna colNombre = new Columna(TipoDato.CADENA);
        Columna colEdad = new Columna(TipoDato.NUMERICO);

        List<String> nombres = Arrays.asList("Ana", "Juan", "Sofía", "Pedro", "Laura");
        List<Integer> edades = Arrays.asList(25, 31, 22, 40, 28);

        for (int i = 0; i < nombres.size(); i++) {
            colNombre.agregarCelda(new Celda<>(nombres.get(i), TipoDato.CADENA));
            colEdad.agregarCelda(new Celda<>(edades.get(i), TipoDato.NUMERICO));
        }
        tabla.agregarColumna(etCol1, colNombre);
        tabla.agregarColumna(etCol2, colEdad);
        tabla.generarEtiquetasFilas();

        Seleccion seleccion = new Seleccion(tabla);

        System.out.println("\nPrimeras 3 filas (head):");
        seleccion.head(3);

        System.out.println("\nÚltimas 2 filas (tail):");
        seleccion.tail(2);

        //seleccionar filas 1 y 3, columnas "Nombre" solamente
        List<Etiqueta> filasSeleccionadas = Arrays.asList(tabla.getEtiquetasFilas().get(1), tabla.getEtiquetasFilas().get(3));
        List<Etiqueta> columnasSeleccionadas = Collections.singletonList(etCol1);
        System.out.println("\nFilas 1 y 3, columna Nombre:");

        // Medimos eficiencia solo de la selección
        MedidorEficiencia.medirTiempo("selección de filas 1 y 3, columna 'Nombre'", () -> {
            seleccion.seleccionar(filasSeleccionadas, columnasSeleccionadas);
        });
    }
}
