package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestOperacionMinimo {
    public static void main(String[] args) {
        System.out.println("---- Test OperacionMinimo ----");

        OperacionMinimo minimo = new OperacionMinimo();
        List<Celda<?>> celdas = Arrays.asList(
                new Celda<>(10.0, TipoDato.NUMERICO),
                new Celda<>(20.0, TipoDato.NUMERICO),
                new Celda<>(5.0, TipoDato.NUMERICO)
        );
        Celda<?> resultado = minimo.sumarizar(celdas);
        System.out.println("Esperado: 5.0, Real: " + resultado.getValor());
    }
}
