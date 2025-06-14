package testsdocumentacion;

import gestiondedatos.*;
import filtros.*;
import java.util.*;

public class TestFiltradoCompleto {
    public static void main(String[] args) {
        List<Etiqueta> etiquetas = Arrays.asList(
                new EtiquetaString("Producto"),
                new EtiquetaString("Precio"),
                new EtiquetaString("Stock")
        );
        List<TipoDato> tipos = Arrays.asList(
                TipoDato.CADENA,
                TipoDato.NUMERICO,
                TipoDato.NUMERICO
        );
        Tabla tabla = new Tabla(etiquetas, tipos);

        tabla.agregarFila(Arrays.asList(new Celda<>("Yerba", TipoDato.CADENA), new Celda<>(500, TipoDato.NUMERICO), new Celda<>(50, TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Café", TipoDato.CADENA), new Celda<>(1500, TipoDato.NUMERICO), new Celda<>(0, TipoDato.NUMERICO)));
        tabla.agregarFila(Arrays.asList(new Celda<>("Té", TipoDato.CADENA), new Celda<>(300, TipoDato.NUMERICO), new Celda<>(20, TipoDato.NUMERICO)));

        ControladorFiltro controlador = new ControladorFiltro(tabla);

        //filtrado de productos caros (precio >= 1000) O sin stock (stock = 0)
        controlador.filtroCompuesto(OperadorLogico.OR,
                new EtiquetaString("Precio"), TipoComparador.MAYOR_IGUAL, 1000,
                new EtiquetaString("Stock"), TipoComparador.IGUAL, 0);
    }
}
