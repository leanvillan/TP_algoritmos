package manipulacion;

import gestiondedatos.*;
import herramientas.MedidorEficiencia;

import java.util.*;

public class TestOrdenadorDatos {
    public static void main(String[] args) {
        System.out.println("---- Prueba de OrdenadorDatos ----");

        //armamos la tabla con datos desordenados
        Tabla tabla = new Tabla();
        EtiquetaString etColEdad = new EtiquetaString("Edad");
        EtiquetaString etColNombre = new EtiquetaString("Nombre");

        Columna colEdad = new Columna(TipoDato.NUMERICO);
        Columna colNombre = new Columna(TipoDato.CADENA);

        //usamos listas para asegurar alineación Edad-Nombre
        List<Integer> edades = Arrays.asList(22, 34, 20, 30);
        List<String> nombres = Arrays.asList("Ludmi", "Lean", "Santi", "Mica");

        for (int i = 0; i < edades.size(); i++) {
            colEdad.agregarCelda(new Celda<>(edades.get(i), TipoDato.NUMERICO));
            colNombre.agregarCelda(new Celda<>(nombres.get(i), TipoDato.CADENA));
        }

        tabla.agregarColumna(etColEdad, colEdad);
        tabla.agregarColumna(etColNombre, colNombre);
        tabla.generarEtiquetasFilas();

        //mostramos antes de ordenar
        System.out.println("Tabla antes de ordenar:");
        for (Etiqueta etiquetaFila : tabla.getEtiquetasFilas()) {
            Integer edad = (Integer) tabla.getCelda(etColEdad, etiquetaFila).getValor();
            String nombre = (String) tabla.getCelda(etColNombre, etiquetaFila).getValor();
            System.out.println("Edad: " + edad + ", Nombre: " + nombre);
        }

        //ordenamos por Edad y medimos eficiencia
        OrdenadorDatos ordenador = new OrdenadorDatos(Collections.singletonList("Edad"));
        MedidorEficiencia.medirTiempo("ordenamiento por Edad", () -> {
            ordenador.manipular(tabla);
        });

        //mostramos después de ordenar
        System.out.println("\nTabla después de ordenar por Edad (de menor a mayor):");
        for (int i = 0; i < tabla.contarFilas(); i++) {
            Etiqueta etiquetaFila = tabla.getEtiquetasFilas().get(i);
            Integer edad = (Integer) tabla.getCelda(etColEdad, etiquetaFila).getValor();
            String nombre = (String) tabla.getCelda(etColNombre, etiquetaFila).getValor();
            System.out.println("Edad: " + edad + ", Nombre: " + nombre + " (Etiqueta: " + etiquetaFila.getValor() + ")");
        }

        System.out.println("\nComprobación de valores post-sort por índice (no por etiqueta):");
        for (int i = 0; i < tabla.contarFilas(); i++) {
            Integer edad = (Integer) colEdad.getCelda(i).getValor();
            String nombre = (String) colNombre.getCelda(i).getValor();
            System.out.println("Índice: " + i + " Edad: " + edad + ", Nombre: " + nombre);
        }
    }
}
