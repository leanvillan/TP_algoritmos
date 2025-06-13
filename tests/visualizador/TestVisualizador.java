package visualizador;

import gestiondedatos.*;
import herramientas.MedidorEficiencia;
import java.util.*;

public class TestVisualizador {
    public static void main(String[] args) {
        System.out.println("---- Prueba de Visualizador ----");

        //creamos una tabla con datos variados
        Tabla tabla = new Tabla();
        EtiquetaString etCol1 = new EtiquetaString("Nombre");
        EtiquetaString etCol2 = new EtiquetaString("Edad");
        EtiquetaString etCol3 = new EtiquetaString("Descripción");

        Columna colNombre = new Columna(TipoDato.CADENA);
        Columna colEdad = new Columna(TipoDato.NUMERICO);
        Columna colDescripcion = new Columna(TipoDato.CADENA);

        colNombre.agregarCelda(new Celda<>("Mica", TipoDato.CADENA));
        colNombre.agregarCelda(new Celda<>("Lean", TipoDato.CADENA));
        colNombre.agregarCelda(new Celda<>("Ludmi", TipoDato.CADENA));
        colEdad.agregarCelda(new Celda<>(30, TipoDato.NUMERICO));
        colEdad.agregarCelda(new Celda<>(34, TipoDato.NUMERICO));
        colEdad.agregarCelda(new Celda<>(22, TipoDato.NUMERICO));
        colDescripcion.agregarCelda(new Celda<>("Trabaja como profesora", TipoDato.CADENA));
        colDescripcion.agregarCelda(new Celda<>("Es analista de aprendizaje", TipoDato.CADENA));
        colDescripcion.agregarCelda(new Celda<>("Es estudiante de Licenciatura en Ciencia de Datos", TipoDato.CADENA));

        tabla.agregarColumna(etCol1, colNombre);
        tabla.agregarColumna(etCol2, colEdad);
        tabla.agregarColumna(etCol3, colDescripcion);
        tabla.generarEtiquetasFilas();

        //imprimimos la tabla usando el visualizador
        System.out.println("\nTabla completa:");
        MedidorEficiencia.medirTiempo("impresión de tabla con Visualizador", () -> {
            Visualizador.imprimirTabla(tabla);
        });

        //imprimimos la info de la tabla (sin medir tiempo)
        System.out.println("\nInfo de la tabla:");
        Visualizador.info(tabla);
    }
}
