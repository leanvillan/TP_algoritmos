package agregacion;

import gestiondedatos.*;
import java.util.*;

public class TestOperacionMedia {
    public static void main(String[] args) {
        System.out.println("---- Test OperacionMedia ----");

        OperacionMedia media = new OperacionMedia();
        List<Celda<?>> celdas = Arrays.asList(
                new Celda<>(10.0, TipoDato.NUMERICO),
                new Celda<>(20.0, TipoDato.NUMERICO),
                new Celda<>(30.0, TipoDato.NUMERICO)
        );
        Celda<?> resultado = media.sumarizar(celdas);
        System.out.println("Esperado: 20.0, Real: " + resultado.getValor());
    }
}
