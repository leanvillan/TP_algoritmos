package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestAgregadorTabla {
    public static void main(String[] args) {
        System.out.println("---- Test AgregadorTabla: groupBy y summarize ----");

        //Creamos una tabla simple para el test
        Tabla tabla = new Tabla();
        Etiqueta col1 = new EtiquetaString("Vendedor");
        Etiqueta col2 = new EtiquetaString("Ventas");

        Columna ciudad = new Columna(TipoDato.CADENA);
        Columna ventas = new Columna(TipoDato.NUMERICO);

        ciudad.agregarCelda(new Celda<>("Ludmi", TipoDato.CADENA));
        ciudad.agregarCelda(new Celda<>("Ludmi", TipoDato.CADENA));
        ciudad.agregarCelda(new Celda<>("Mica", TipoDato.CADENA));
        ventas.agregarCelda(new Celda<>(100.0, TipoDato.NUMERICO));
        ventas.agregarCelda(new Celda<>(150.0, TipoDato.NUMERICO));
        ventas.agregarCelda(new Celda<>(200.0, TipoDato.NUMERICO));

        tabla.agregarColumna(col1, ciudad);
        tabla.agregarColumna(col2, ventas);

        //testeamos el groupBy
        AgregadorTabla agregador = new AgregadorTabla();
        List<Etiqueta> grupo = Arrays.asList(col1);
        TablaAgrupada agrupada = agregador.groupBy(tabla, grupo);
        System.out.println("Cantidad de grupos esperada: 2, real: " + agrupada.getGrupos().size());

        //testeamos summarize (suma por ciudad)
        Tabla tablaResumida = agregador.summarize(agrupada, Arrays.asList(col2), TipoOperacion.SUMA);

        System.out.println("Resultado de resumen:");
        for (int i = 0; i < tablaResumida.contarFilas(); i++) {
            List<Celda<?>> fila = tablaResumida.getFila(i);
            System.out.print("Ciudad: " + fila.get(0).getValor());
            System.out.println(" | Suma Ventas: " + fila.get(1).getValor());
        }
    }
}
