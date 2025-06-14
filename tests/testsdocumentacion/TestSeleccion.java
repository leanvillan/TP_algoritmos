package testsdocumentacion;

import gestiondedatos.*;
import seleccion.*;
import java.util.*;

public class TestSeleccion {
    public static void main(String[] args) {
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Ciudad"),
                new EtiquetaString("Habitantes"),
                new EtiquetaString("País")
        );
        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO,
                TipoDato.CADENA
        );

        Tabla tabla = new Tabla(etiquetas, tipos);
        tabla.agregarFila(Arrays.asList(new Celda<>("Madrid", TipoDato.CADENA), new Celda<>(3200000, TipoDato.NUMERICO), new Celda<>("España", TipoDato.CADENA)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Roma", TipoDato.CADENA), new Celda<>(2800000, TipoDato.NUMERICO), new Celda<>("Italia", TipoDato.CADENA)));
        tabla.agregarFila(Arrays.asList(new Celda<>("París", TipoDato.CADENA), new Celda<>(2140000, TipoDato.NUMERICO), new Celda<>("Francia", TipoDato.CADENA)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Berlín", TipoDato.CADENA), new Celda<>(3800000, TipoDato.NUMERICO), new Celda<>("Alemania", TipoDato.CADENA)));

        Seleccion seleccion = new Seleccion(tabla);

        //Mostrar las primeras 2 filas
        seleccion.head(2);

        //Mostrar las últimas 2 filas
        seleccion.tail(2);

        //Selección específica de filas y columnas
        List<Etiqueta> filasSeleccionadas = Arrays.asList(new EtiquetaEntero(0), new EtiquetaEntero(2)); // Madrid y París
        List<Etiqueta> columnasSeleccionadas = Arrays.asList(new EtiquetaString("Ciudad"), new EtiquetaString("País"));

        seleccion.seleccionar(filasSeleccionadas, columnasSeleccionadas);
    }
}
