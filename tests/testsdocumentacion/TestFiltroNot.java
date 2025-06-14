package testsdocumentacion;

import gestiondedatos.*;
import filtros.*;
import java.util.*;
import java.util.Arrays;

public class TestFiltroNot {





    public static void main(String[] args) {
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Alumno"),
                new EtiquetaString("Nota")
        );

        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO
        );

        Tabla tabla = new Tabla(etiquetas, tipos);
        tabla.agregarFila(Arrays.asList(new Celda<>("Juan", TipoDato.CADENA), new Celda<>(7, TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Ana", TipoDato.CADENA), new Celda<>(9, TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Luis", TipoDato.CADENA), new Celda<>(5, TipoDato.NUMERICO)));

        // Filtrado de alumnos que NO tienen Nota = 7
        Filtro filtroIgual7 = FiltroFactory.crearFiltroComparador(TipoComparador.IGUAL, 7);
        FiltroNOT not = FiltroFactory.crearFiltroNOT(filtroIgual7);

        ControladorFiltro controlador = new ControladorFiltro(tabla);
        controlador.filtrarNOT(new EtiquetaString("Nota"), TipoComparador.IGUAL, 7);



    }

}
