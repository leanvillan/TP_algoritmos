package manipulacion;

import gestiondedatos.*;
import java.util.*;

/**
 * tests para MuestreadorDatos.
 * toma un porcentaje de filas de la tabla y elimina el resto
 * probamos que el número de filas final sea correcto y que los datos estén mezclados
 */

public class TestMuestreadorDatos {
    public static void main(String[] args) {
        System.out.println("---- Prueba de MuestreadorDatos ----");

        //creamos una tabla con 6 filas
        Tabla tabla = new Tabla();
        EtiquetaString etColId = new EtiquetaString("ID");
        EtiquetaString etColNombre = new EtiquetaString("Nombre");

        Columna colId = new Columna(TipoDato.NUMERICO);
        Columna colNombre = new Columna(TipoDato.CADENA);

        for (int i = 1; i <= 6; i++) {
            colId.agregarCelda(new Celda<>(i, TipoDato.NUMERICO));
            colNombre.agregarCelda(new Celda<>("Persona" + i, TipoDato.CADENA));
        }

        tabla.agregarColumna(etColId, colId);
        tabla.agregarColumna(etColNombre, colNombre);
        tabla.generarEtiquetasFilas();

        //mostramos antes de muestrear
        System.out.println("Tabla original:");
        for (int i = 0; i < tabla.contarFilas(); i++) {
            System.out.print("Fila " + i + ": ");
            System.out.print(tabla.getCelda(etColId, tabla.getEtiquetasFilas().get(i)) + " - ");
            System.out.println(tabla.getCelda(etColNombre, tabla.getEtiquetasFilas().get(i)));
        }

        //muestreo (quedarse con el 50%)
        double porcentaje = 0.5;
        MuestreadorDatos muestreador = new MuestreadorDatos(porcentaje);
        muestreador.manipular(tabla);

        //mostramos después de muestrear
        System.out.println("\nTabla después de muestrear (" + (int)(porcentaje * 100) + "% de las filas):");
        for (int i = 0; i < tabla.contarFilas(); i++) {
            System.out.print("Fila " + i + ": ");
            System.out.print(tabla.getCelda(etColId, tabla.getEtiquetasFilas().get(i)) + " - ");
            System.out.println(tabla.getCelda(etColNombre, tabla.getEtiquetasFilas().get(i)));
        }

        System.out.println("\nCantidad final de filas: " + tabla.contarFilas());
    }
}
