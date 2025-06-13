package filtros;

import gestiondedatos.*;
import herramientas.MedidorEficiencia;
import filtros.FiltroAND;

import java.util.*;

/**
 * Testeos de los filtros lógicos (AND, OR, NOT) combinando filtros simples
 */
public class TestFiltroLogico {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Filtros Lógicos (AND / OR / NOT) ----");

        // Creamos la tabla con dos columnas: Edad (numérico) y Activo (booleano)
        Tabla tabla = new Tabla();
        EtiquetaString etEdad = new EtiquetaString("Edad");
        EtiquetaString etActivo = new EtiquetaString("Activo");

        Columna colEdad = new Columna(TipoDato.NUMERICO);
        Columna colActivo = new Columna(TipoDato.BOOLEANO);

        // Cargamos datos
        colEdad.agregarCelda(new Celda<>(25, TipoDato.NUMERICO));
        colEdad.agregarCelda(new Celda<>(19, TipoDato.NUMERICO));
        colEdad.agregarCelda(new Celda<>(33, TipoDato.NUMERICO));
        colEdad.agregarCelda(new Celda<>(null, TipoDato.NA)); // NA

        colActivo.agregarCelda(new Celda<>(true, TipoDato.BOOLEANO));
        colActivo.agregarCelda(new Celda<>(false, TipoDato.BOOLEANO));
        colActivo.agregarCelda(new Celda<>(true, TipoDato.BOOLEANO));
        colActivo.agregarCelda(new Celda<>(null, TipoDato.NA)); // NA

        tabla.agregarColumna(etEdad, colEdad);
        tabla.agregarColumna(etActivo, colActivo);
        tabla.generarEtiquetasFilas();
        List<Etiqueta> filas = tabla.getEtiquetasFilas();

        // Filtro1: Edad > 20
        Filtro f1 = new Mayor(20);

        // Filtro2: Activo == true
        Filtro f2 = new Igual(true);

        // Filtro AND (Edad > 20 y Activo == true)
        FiltroAND filtroAnd = new FiltroAND(f1, f2);
        MedidorEficiencia.medirTiempo("evaluación del filtro AND (Edad>20 y Activo==true)", () -> {
            System.out.println("Filtro AND (Edad>20 y Activo==true):");
            for (Etiqueta fila : filas) {
                boolean cumple = filtroAnd.cumpleEnFila(tabla, fila, etEdad, etActivo);
                System.out.println("Fila " + fila + ": " + cumple);
            }
        });

        // Filtro OR (Edad > 30 o Activo == false)
        FiltroOR filtroOr = new FiltroOR(new Mayor(30), new Igual(false));
        MedidorEficiencia.medirTiempo("evaluación del filtro OR (Edad>30 o Activo==false)", () -> {
            System.out.println("\nFiltro OR (Edad>30 o Activo==false):");
            for (Etiqueta fila : filas) {
                boolean cumple = filtroOr.cumpleEnFila(tabla, fila, etEdad, etActivo);
                System.out.println("Fila " + fila + ": " + cumple);
            }
        });

        // Filtro NOT (NO Activo == true)
        FiltroNOT filtroNot = new FiltroNOT(f2);
        MedidorEficiencia.medirTiempo("evaluación del filtro NOT (NO Activo==true)", () -> {
            System.out.println("\nFiltro NOT (NO Activo==true):");
            for (Etiqueta fila : filas) {
                boolean cumple = filtroNot.cumpleEnFila(tabla, fila, etActivo);
                System.out.println("Fila " + fila + ": " + cumple);
            }
        });
    }
}
