package gestiondedatos;

/**
 * estas son pruebas manuales para la clase Tabla
 * testeamos la creación, agregado de columnas/filas, acceso a datos, eliminación y manejo de excepciones
 */
import java.util.*;
import herramientas.MedidorEficiencia;

public class TestTabla {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Tabla ----");

        // ✅ Medimos el tiempo de creación y operaciones básicas sobre la tabla
        MedidorEficiencia.medirTiempo("creación y operaciones básicas de Tabla", () -> {

            // 1 - creacion de tabla vacía
            Tabla tabla = new Tabla();
            System.out.println("Cantidad de columnas al crear: " + tabla.contarColumnas()); // 0
            System.out.println("Cantidad de filas al crear: " + tabla.contarFilas()); // 0

            //2 - Agregar columnas con etiquetas personalizadas
            Columna colNum = new Columna(TipoDato.NUMERICO);
            Columna colTexto = new Columna(TipoDato.CADENA);
            EtiquetaString etColNum = new EtiquetaString("Edad");
            EtiquetaString etColTexto = new EtiquetaString("Nombre");

            tabla.agregarColumna(etColNum, colNum);
            tabla.agregarColumna(etColTexto, colTexto);
            System.out.println("Cantidad de columnas tras agregar dos: " + tabla.contarColumnas()); // 2

            //3 - Agregar filas
            List<Celda<?>> fila1 = Arrays.asList(
                    new Celda<>(30, TipoDato.NUMERICO),
                    new Celda<>("Juan", TipoDato.CADENA)
            );
            tabla.agregarFila(fila1);
            List<Celda<?>> fila2 = Arrays.asList(
                    new Celda<>(25, TipoDato.NUMERICO),
                    new Celda<>("Ana", TipoDato.CADENA)
            );
            tabla.agregarFila(new EtiquetaString("Fila2"), fila2);

            System.out.println("Cantidad de filas tras agregar dos: " + tabla.contarFilas()); // 2

            //4 - Acceso a celdas por etiqueta
            Celda<?> celda = tabla.getCelda(etColNum, tabla.getEtiquetasFilas().get(0));
            System.out.println("Valor de Edad en fila 0: " + celda.getValor()); // 30

            //5 - eliminar fila existente
            tabla.eliminarFila(tabla.getEtiquetasFilas().get(0));
            System.out.println("Cantidad de filas tras eliminar una: " + tabla.contarFilas()); // 1
        });

        //6 - Prueba de error al eliminar fila inexistente
        try {
            Tabla tabla = new Tabla(); // se crea una nueva tabla vacía para este test
            tabla.eliminarFila(new EtiquetaString("NoExiste"));
            System.out.println("ERROR: Se eliminó una fila inexistente.");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: No se pudo eliminar fila inexistente. Mensaje: " + e.getMessage());
        }

        //7 - Prueba de error al agregar columna duplicada
        try {
            Tabla tabla = new Tabla();
            EtiquetaString etColNum = new EtiquetaString("Edad");
            tabla.agregarColumna(etColNum, new Columna(TipoDato.NUMERICO));
            tabla.agregarColumna(etColNum, new Columna(TipoDato.NUMERICO)); // duplicada
            System.out.println("ERROR: Se agregó columna duplicada.");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: No se pudo agregar columna duplicada. Mensaje: " + e.getMessage());
        }

        //8 - Prueba de error al pedir celda fuera de rango
        try {
            Tabla tabla = new Tabla();
            tabla.getFila(10);
            System.out.println("ERROR: Se obtuvo fila fuera de rango.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("OK: No se pudo obtener fila fuera de rango. Mensaje: " + e.getMessage());
        }
    }
}
