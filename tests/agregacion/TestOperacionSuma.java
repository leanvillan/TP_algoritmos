package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestOperacionSuma {
    public static void main(String[] args) {
        System.out.println("---- Test OperacionSuma ----");

        OperacionSuma suma = new OperacionSuma();
        List<Celda<?>> celdas = Arrays.asList(
                new Celda<>(10.0, TipoDato.NUMERICO),
                new Celda<>(15.0, TipoDato.NUMERICO),
                new Celda<>(5.0, TipoDato.NUMERICO)
        );
        Celda<?> resultado = suma.sumarizar(celdas);
        System.out.println("Esperado: 30.0, Real: " + resultado.getValor());
    }
}
