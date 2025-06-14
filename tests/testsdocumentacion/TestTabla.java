package testsdocumentacion;

import gestiondedatos.*;
import java.util.*;

//Test básico de creación y visualización de tabla
public class TestTabla {
    public static void main(String[] args) {
        //Creación de una tabla con 2 columnas: "Nombre" (cadena) y "Edad" (numérico)
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Nombre"),
                new EtiquetaString("Edad")
        );
        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO
        );
        Tabla tabla = new Tabla(etiquetas, tipos);

        //Agregado de una fila
        List<Celda<?>> fila = Arrays.asList(
                new Celda<String>("Ana", TipoDato.CADENA),
                new Celda<Integer>(22, TipoDato.NUMERICO)
        );
        tabla.agregarFila(fila);

        //Visualización
        tabla.visualizarTabla();
    }
}
