package testsdocumentacion;

import gestiondedatos.*;
import visualizador.*;
import java.util.*;

public class TestVisualizador {
    public static void main(String[] args) {
        //Creamos una tabla sencilla
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Producto"),
                new EtiquetaString("Precio"),
                new EtiquetaString("Stock")
        );
        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO,
                TipoDato.NUMERICO
        );
        Tabla tabla = new Tabla(etiquetas, tipos);

        tabla.agregarFila(Arrays.asList(
                new Celda<>("Café en grano 1kg", TipoDato.CADENA),
                new Celda<>(4100, TipoDato.NUMERICO),
                new Celda<>(20, TipoDato.NUMERICO)
        ));

        tabla.agregarFila(Arrays.asList(
                new Celda<>("Yerba 500g saborizada", TipoDato.CADENA),
                new Celda<>(2000, TipoDato.NUMERICO),
                new Celda<>(15, TipoDato.NUMERICO)
        ));

        //hacemos la visualización principal
        Visualizador.imprimirTabla(tabla);

        //mostramos información básica
        Visualizador.info(tabla);
    }
}
