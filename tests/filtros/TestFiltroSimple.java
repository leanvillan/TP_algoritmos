package filtros;

import gestiondedatos.*;
import herramientas.MedidorEficiencia; 

import java.util.Arrays;

/**
 * Prueba manual de los filtros simples: Mayor, Menor, Igual, Distinto, etc.
 */
public class TestFiltroSimple {
    public static void main(String[] args) {
        System.out.println("---- Pruebas para Filtros Simples ----");

        // Construimos una columna numérica de ejemplo
        Columna colNum = new Columna(TipoDato.NUMERICO);
        colNum.agregarCelda(new Celda<>(10, TipoDato.NUMERICO));
        colNum.agregarCelda(new Celda<>(20, TipoDato.NUMERICO));
        colNum.agregarCelda(new Celda<>(30, TipoDato.NUMERICO));
        colNum.agregarCelda(new Celda<>());

        // Testeo Mayor(15)
        Filtro mayor15 = new Mayor(15);
        MedidorEficiencia.medirTiempo("aplicación de filtro Mayor(15)", () -> {
            System.out.println("Mayor(15):");
            for (Celda<?> celda : colNum.getCeldas()) {
                System.out.println("Valor: " + celda + " -> " + mayor15.cumple(celda.getValor(), celda.getTipoDato()));
            }
        });

        // Testeo MenorIgual(20)
        Filtro menorIgual20 = new MenorIgual(20);
        MedidorEficiencia.medirTiempo("aplicación de filtro MenorIgual(20)", () -> {
            System.out.println("MenorIgual(20):");
            for (Celda<?> celda : colNum.getCeldas()) {
                System.out.println("Valor: " + celda + " -> " + menorIgual20.cumple(celda.getValor(), celda.getTipoDato()));
            }
        });

        // Testeo Igual(20)
        Filtro igual20 = new Igual(20);
        MedidorEficiencia.medirTiempo("aplicación de filtro Igual(20)", () -> {
            System.out.println("Igual(20):");
            for (Celda<?> celda : colNum.getCeldas()) {
                System.out.println("Valor: " + celda + " -> " + igual20.cumple(celda.getValor(), celda.getTipoDato()));
            }
        });

        // Testeo Distinto(30)
        Filtro distinto30 = new Distinto(30);
        MedidorEficiencia.medirTiempo("aplicación de filtro Distinto(30)", () -> {
            System.out.println("Distinto(30):");
            for (Celda<?> celda : colNum.getCeldas()) {
                System.out.println("Valor: " + celda + " -> " + distinto30.cumple(celda.getValor(), celda.getTipoDato()));
            }
        });

        // Filtro sobre strings
        Columna colStr = new Columna(TipoDato.CADENA);
        colStr.agregarCelda(new Celda<>("Ana", TipoDato.CADENA));
        colStr.agregarCelda(new Celda<>("Juan", TipoDato.CADENA));
        colStr.agregarCelda(new Celda<>("Ana", TipoDato.CADENA));
        colStr.agregarCelda(new Celda<>());

        Filtro igualAna = new Igual("Ana");
        MedidorEficiencia.medirTiempo("aplicación de filtro Igual('Ana')", () -> {
            System.out.println("Igual('Ana'):");
            for (Celda<?> celda : colStr.getCeldas()) {
                System.out.println("Valor: " + celda + " -> " + igualAna.cumple(celda.getValor(), celda.getTipoDato()));
            }
        });
    }
}
