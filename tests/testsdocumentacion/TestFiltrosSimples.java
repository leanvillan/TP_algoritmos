package testsdocumentacion;

import gestiondedatos.*;
import filtros.*;
import java.util.*;

public class TestFiltrosSimples {
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

        // filtrado simple (notas mayores o iguales a 7)
        ControladorFiltro controlador = new ControladorFiltro(tabla);
        controlador.filtroSimple(new EtiquetaString("Nota"), TipoComparador.MAYOR_IGUAL, 7);


    }
}
