package testsdocumentacion;

import gestiondedatos.*;
import java.util.*;

public class EjemploEncadenadoGestionDeDatos {
    public static void main(String[] args) {
        //1ro Definimos las etiquetas de columnas y sus tipos
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Nombre"),
                new EtiquetaString("Edad"),
                new EtiquetaString("Graduado")
        );
        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO,
                TipoDato.BOOLEANO
        );

        //2do Creamos la tabla con esa estructura
        Tabla tabla = new Tabla(etiquetas, tipos);

        //3ro Agregamos filas de datos
        List<Celda<?>> fila1 = Arrays.asList(
                new Celda<String>("Ana", TipoDato.CADENA),
                new Celda<Integer>(22, TipoDato.NUMERICO),
                new Celda<Boolean>(true, TipoDato.BOOLEANO)
        );
        List<Celda<?>> fila2 = Arrays.asList(
                new Celda<String>("Juan", TipoDato.CADENA),
                new Celda<Integer>(27, TipoDato.NUMERICO),
                new Celda<Boolean>(false, TipoDato.BOOLEANO)
        );
        tabla.agregarFila(fila1);
        tabla.agregarFila(fila2);

        //4to Visualización de la tabla
        System.out.println("Tabla original:");
        tabla.visualizarTabla();

        //5to acceso a una celda específica
        Etiqueta colEdad = new EtiquetaString("Edad");
        Etiqueta fila0 = new EtiquetaEntero(0); // Fila "Ana" porque es la primera
        Celda<?> celdaEdadAna = tabla.getCelda(colEdad, fila0);
        System.out.println("\nEdad de Ana: " + celdaEdadAna.getValor());

        //6to modificación de un dato (cambiar la edad de Juan)
        Etiqueta fila1Etiqueta = new EtiquetaEntero(1); // Fila "Juan"
        Celda<Integer> nuevaEdadJuan = new Celda<>(28, TipoDato.NUMERICO);
        tabla.getColumna(colEdad).getCeldas().set(1, nuevaEdadJuan);

        //7mo visualizamos de nuevo para ver el cambio
        System.out.println("\nTabla después de modificar la edad de Juan:");
        tabla.visualizarTabla();

        //8vo creamos una copia profunda e independiente
        Tabla copia = tabla.copiarTabla();
        System.out.println("\nCopia independiente de la tabla:");
        copia.visualizarTabla();

        //9no Eliminamos la fila de Ana en la tabla original
        tabla.eliminarFila(fila0);

        //10mo mostramos ambas tablas para ver que son independientes
        System.out.println("\nTabla original tras eliminar la fila de Ana:");
        tabla.visualizarTabla();
        System.out.println("\nCopia (sin modificaciones):");
        copia.visualizarTabla();
    }
}
