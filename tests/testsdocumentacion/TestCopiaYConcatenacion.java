package testsdocumentacion;

import gestiondedatos.*;
import copiayconcatenacion.*;
import visualizador.*;
import java.util.*;

public class TestCopiaYConcatenacion {
    public static void main(String[] args) {
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Nombre"),
                new EtiquetaString("Edad")
        );
        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO
        );

        //Creamos la tabla original
        Tabla tablaOriginal = new Tabla(etiquetas, tipos);
        tablaOriginal.agregarFila(Arrays.asList(new Celda<>("Carlos", TipoDato.CADENA), new Celda<>(25, TipoDato.NUMERICO)));
        tablaOriginal.agregarFila(Arrays.asList(new Celda<>("María", TipoDato.CADENA), new Celda<>(30, TipoDato.NUMERICO)));

        // Copiamos la tabla original
        Tabla tablaCopia = CopiaTabla.copiarTabla(tablaOriginal);
        Visualizador.imprimirTabla(tablaCopia);

        // Creamos otra tabla compatible para concatenación
        Tabla otraTabla = new Tabla(etiquetas, tipos);
        otraTabla.agregarFila(Arrays.asList(new Celda<>("Ana", TipoDato.CADENA), new Celda<>(22, TipoDato.NUMERICO)));
        otraTabla.agregarFila(Arrays.asList(new Celda<>("Luis", TipoDato.CADENA), new Celda<>(28, TipoDato.NUMERICO)));

        // Concatenamos las dos tablas
        Tabla tablaConcatenada = ConcatenacionTabla.concatenarTablas(tablaOriginal, otraTabla);
        Visualizador.imprimirTabla(tablaConcatenada);
    }
}
