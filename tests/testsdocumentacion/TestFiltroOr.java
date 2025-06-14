package testsdocumentacion;

import gestiondedatos.*;
import filtros.*;
import java.util.*;
import java.util.Arrays;

public class TestFiltroOr {


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

        //Filtrado de alumnos con Nota < 6 O Nota > 8
        Filtro notaBaja = FiltroFactory.crearFiltroComparador(TipoComparador.MENOR, 6);
        Filtro notaAlta = FiltroFactory.crearFiltroComparador(TipoComparador.MAYOR, 8);

        FiltroLogico or = FiltroFactory.crearFiltroLogico(OperadorLogico.OR, notaBaja, notaAlta);

        ControladorFiltro controlador = new ControladorFiltro(tabla);
        controlador.filtroCompuesto(OperadorLogico.OR,
                new EtiquetaString("Nota"), TipoComparador.MENOR, 6,
                new EtiquetaString("Nota"), TipoComparador.MAYOR, 8);


    }
}
