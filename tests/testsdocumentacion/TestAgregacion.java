package testsdocumentacion;

import gestiondedatos.*;
import agregacion.*;
import visualizador.Visualizador;
import java.util.*;

public class TestAgregacion {
    public static void main(String[] args) {
        //creamos etiquetas para las columnas
        EtiquetaString etCurso = new EtiquetaString("Curso");
        EtiquetaString etNota = new EtiquetaString("Nota");
        EtiquetaString etEdad = new EtiquetaString("Edad");

        //creamos las columnas correspondientes
        Columna colCurso = new Columna(TipoDato.CADENA);
        Columna colNota = new Columna(TipoDato.NUMERICO);
        Columna colEdad = new Columna(TipoDato.NUMERICO);

        // cargamos los datos: 6 alumnos de distintos cursos
        // Curso, Nota, Edad
        Object[][] datos = {
                {"1A", 8.0, 18},
                {"1A", 6.5, 19},
                {"2B", 7.0, 21},
                {"2B", 6.6, 20},
                {"1A", 7.2, 20},
                {"2B", 6.8, 22}
        };

        for (Object[] fila : datos) {
            colCurso.agregarCelda(new Celda<>(fila[0], TipoDato.CADENA));
            colNota.agregarCelda(new Celda<>(fila[1], TipoDato.NUMERICO));
            colEdad.agregarCelda(new Celda<>(fila[2], TipoDato.NUMERICO));
        }

        // armamos la tabla
        Tabla tabla = new Tabla();
        tabla.agregarColumna(etCurso, colCurso);
        tabla.agregarColumna(etNota, colNota);
        tabla.agregarColumna(etEdad, colEdad);

        // generamos las etiquetas de filas (numéricas por defecto)
        tabla.generarEtiquetasFilas();

        // mostramos la tabla original
        System.out.println("Tabla original:");
        Visualizador.imprimirTabla(tabla);

        // agrupamos por curso y calculamos la nota promedio por curso
        AgregadorTabla agregador = new AgregadorTabla();
        TablaAgrupada agrupada = agregador.groupBy(tabla, Arrays.asList(etCurso));
        Tabla tablaResumen = agregador.summarize(agrupada, Arrays.asList(etNota), TipoOperacion.MEDIA);

        // Mostramos la tabla resumen
        System.out.println("Promedio de nota por curso:");
        Visualizador.imprimirTabla(tablaResumen);
    }
}
