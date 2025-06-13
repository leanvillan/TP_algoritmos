package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestOperacionCuenta {
    public static void main(String[] args) {
        System.out.println("---- Test OperacionCuenta ----");

        OperacionCuenta cuenta = new OperacionCuenta();
        List<Celda<?>> celdas = Arrays.asList(
                new Celda<>(10.0, TipoDato.NUMERICO),
                new Celda<>(15.0, TipoDato.NUMERICO),
                new Celda<>(5.0, TipoDato.NUMERICO)
        );
        Celda<?> resultado = cuenta.sumarizar(celdas);
        System.out.println("Esperado: 3, Real: " + resultado.getValor());
    }
}
