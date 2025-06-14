package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestOperacionVarianza {
    public static void main(String[] args) {
        System.out.println("---- Test OperacionVarianza ----");

        OperacionVarianza varianza = new OperacionVarianza();
        List<Celda<?>> celdas = Arrays.asList(
                new Celda<>(2.0, TipoDato.NUMERICO),
                new Celda<>(4.0, TipoDato.NUMERICO),
                new Celda<>(4.0, TipoDato.NUMERICO),
                new Celda<>(4.0, TipoDato.NUMERICO),
                new Celda<>(5.0, TipoDato.NUMERICO),
                new Celda<>(5.0, TipoDato.NUMERICO),
                new Celda<>(7.0, TipoDato.NUMERICO),
                new Celda<>(9.0, TipoDato.NUMERICO)
        );
        Celda<?> resultado = varianza.sumarizar(celdas);
        System.out.println("Esperado: 4.0, Real: " + resultado.getValor());
    }
}
