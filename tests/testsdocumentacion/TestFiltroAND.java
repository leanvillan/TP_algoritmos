package testsdocumentacion;

import gestiondedatos.*;
import filtros.*;
import java.util.*;
import java.util.Arrays;


public class TestFiltroAND {

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

        //filtrado de alumnos con Nota >= 7 Y nombre alfabéticamente después de "H"
        Filtro notaAlta = FiltroFactory.crearFiltroComparador(TipoComparador.MAYOR_IGUAL, 7);
        Filtro nombreDespuesH = FiltroFactory.crearFiltroComparador(TipoComparador.MAYOR, "H");

        FiltroLogico and = FiltroFactory.crearFiltroLogico(OperadorLogico.AND, notaAlta, nombreDespuesH);

        ControladorFiltro controlador = new ControladorFiltro(tabla);
        controlador.filtroCompuesto(OperadorLogico.AND,
                new EtiquetaString("Nota"), TipoComparador.MAYOR_IGUAL, 7,
                new EtiquetaString("Alumno"), TipoComparador.MAYOR, "H");



    }
}
