package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestAgregacionAvanzado {
    public static void main(String[] args) {
        System.out.println("---- Test avanzado de Agregación ----\n");

        //etiquetas de columnas
        Etiqueta colNombre  = new EtiquetaString("Nombre");
        Etiqueta colMateria = new EtiquetaString("Materia");
        Etiqueta colNota    = new EtiquetaString("Nota");

        //creacion de tabla y columnas
        Tabla tabla = new Tabla();
        tabla.agregarColumna(colNombre,  new Columna(TipoDato.CADENA));
        tabla.agregarColumna(colMateria, new Columna(TipoDato.CADENA));
        tabla.agregarColumna(colNota,    new Columna(TipoDato.NUMERICO));

        //agregamos filas (alumnos, materias, notas)
        tabla.agregarFila(Arrays.asList(new Celda<>("Lean",  TipoDato.CADENA), new Celda<>("Algoritmos", TipoDato.CADENA), new Celda<>(10.0, TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Lean",  TipoDato.CADENA), new Celda<>("Análisis",   TipoDato.CADENA), new Celda<>(8.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Lean",  TipoDato.CADENA), new Celda<>("IAA",        TipoDato.CADENA), new Celda<>(7.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Mica",  TipoDato.CADENA), new Celda<>("Algoritmos", TipoDato.CADENA), new Celda<>(9.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Mica",  TipoDato.CADENA), new Celda<>("ICD",        TipoDato.CADENA), new Celda<>(6.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Mica",  TipoDato.CADENA), new Celda<>("Análisis",   TipoDato.CADENA), new Celda<>(8.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Ludmi", TipoDato.CADENA), new Celda<>("ICD",        TipoDato.CADENA), new Celda<>(9.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Ludmi", TipoDato.CADENA), new Celda<>("Algoritmos", TipoDato.CADENA), new Celda<>(7.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Ludmi", TipoDato.CADENA), new Celda<>("IAA",        TipoDato.CADENA), new Celda<>(8.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Santi", TipoDato.CADENA), new Celda<>("Algoritmos", TipoDato.CADENA), new Celda<>(8.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Santi", TipoDato.CADENA), new Celda<>("ICD",        TipoDato.CADENA), new Celda<>(9.0,  TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Santi", TipoDato.CADENA), new Celda<>("Análisis",   TipoDato.CADENA), new Celda<>(10.0, TipoDato.NUMERICO)));

        //primero mostramos la tabla original completa
        System.out.println("Tabla original completa:");
        tabla.visualizarTabla();

        // --- Agrupamiento y suma de notas por materia ---
        AgregadorTabla agregador = new AgregadorTabla();
        List<Etiqueta> grupoPorMateria = Arrays.asList(colMateria);
        TablaAgrupada agrupadaPorMateria = agregador.groupBy(tabla, grupoPorMateria);
        Tabla sumasPorMateria = agregador.summarize(agrupadaPorMateria, Arrays.asList(colNota), TipoOperacion.SUMA);

        System.out.println("\nSuma de notas por materia:");
        sumasPorMateria.visualizarTabla();

        // --- Agrupamiento y media de notas por alumno ---
        List<Etiqueta> grupoPorNombre = Arrays.asList(colNombre);
        TablaAgrupada agrupadaPorNombre = agregador.groupBy(tabla, grupoPorNombre);
        Tabla mediaPorAlumno = agregador.summarize(agrupadaPorNombre, Arrays.asList(colNota), TipoOperacion.MEDIA);

        System.out.println("\nMedia de notas por alumno:");
        mediaPorAlumno.visualizarTabla();

        // --- Agrupamiento doble: alumno y materia, máximo por grupo ---
        List<Etiqueta> grupoNombreMateria = Arrays.asList(colNombre, colMateria);
        TablaAgrupada agrupadaDoble = agregador.groupBy(tabla, grupoNombreMateria);
        Tabla maxPorGrupo = agregador.summarize(agrupadaDoble, Arrays.asList(colNota), TipoOperacion.MAXIMO);

        System.out.println("\nMáximo por (Alumno, Materia):");
        maxPorGrupo.visualizarTabla();
    }
}
