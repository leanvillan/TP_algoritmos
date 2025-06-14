package manipulacion;

import gestiondedatos.*;

/**
 * estas son pruebas manuales para ImputadorDatos
 * y verifica que los valores NA se reemplacen correctamente por un valor compatible
 */

import java.util.*;

public class TestImputadorDatos {
    public static void main(String[] args) {
        System.out.println("---- Prueba de ImputadorDatos ----");

        //creamos una tabla con dos columnas, una con algunos valores NA
        Tabla tabla = new Tabla();
        EtiquetaString etColNum = new EtiquetaString("Puntaje");
        EtiquetaString etColStr = new EtiquetaString("Nombre");

        Columna colNum = new Columna(TipoDato.NUMERICO);
        colNum.agregarCelda(new Celda<>(10, TipoDato.NUMERICO));
        colNum.agregarCelda(new Celda<>());
        colNum.agregarCelda(new Celda<>(7, TipoDato.NUMERICO));
        colNum.agregarCelda(new Celda<>());

        Columna colStr = new Columna(TipoDato.CADENA);
        colStr.agregarCelda(new Celda<>("Ana", TipoDato.CADENA));
        colStr.agregarCelda(new Celda<>("Luis", TipoDato.CADENA));
        colStr.agregarCelda(new Celda<>());
        colStr.agregarCelda(new Celda<>());

        tabla.agregarColumna(etColNum, colNum);
        tabla.agregarColumna(etColStr, colStr);
        tabla.generarEtiquetasFilas();

        //mostramos tabla antes de imputar
        System.out.println("Tabla antes de imputar:");
        for (int i = 0; i < tabla.contarFilas(); i++) {
            System.out.print("Fila " + i + ": ");
            System.out.print(tabla.getCelda(etColNum, tabla.getEtiquetasFilas().get(i)) + " - ");
            System.out.println(tabla.getCelda(etColStr, tabla.getEtiquetasFilas().get(i)));
        }

        //imputamos NA de Puntaje con 0
        ImputadorDatos imputadorNum = new ImputadorDatos(0);
        imputadorNum.manipular(tabla);

        //imputamos NA de Nombre con "Desconocido"
        ImputadorDatos imputadorStr = new ImputadorDatos("Desconocido");
        imputadorStr.manipular(tabla);

        //mostramos tabla después de imputar
        System.out.println("\nTabla después de imputar:");
        for (int i = 0; i < tabla.contarFilas(); i++) {
            System.out.print("Fila " + i + ": ");
            System.out.print(tabla.getCelda(etColNum, tabla.getEtiquetasFilas().get(i)) + " - ");
            System.out.println(tabla.getCelda(etColStr, tabla.getEtiquetasFilas().get(i)));
        }
    }
}
