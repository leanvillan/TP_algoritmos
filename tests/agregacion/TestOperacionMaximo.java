package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestOperacionMaximo {
    public static void main(String[] args) {
        System.out.println("---- Test OperacionMaximo ----");

        OperacionMaximo maximo = new OperacionMaximo();
        List<Celda<?>> celdas = Arrays.asList(
                new Celda<>(10.0, TipoDato.NUMERICO),
                new Celda<>(20.0, TipoDato.NUMERICO),
                new Celda<>(5.0, TipoDato.NUMERICO)
        );
        Celda<?> resultado = maximo.sumarizar(celdas);
        System.out.println("Esperado: 20.0, Real: " + resultado.getValor());
    }
}
