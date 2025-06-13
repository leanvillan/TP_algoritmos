package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.TipoDato;
import java.util.List;

public class OperacionCuenta implements Sumarizador {

    // Cuenta el número de celdas en la lista que contienen un valor no nulo y no de tipo NA.

    @Override
    public Celda<?> sumarizar(List<Celda<?>> celdas) {
        // Inicializa el contador a 0.0.
        double cuenta = 0.0;

        // Itera sobre cada celda en la lista proporcionada.
        for (Celda<?> celda : celdas) {
            // Un valor se considera válido para el conteo si la celda no es nula,
            // su tipo de dato no es NA y su valor interno no es nulo.
            if (celda != null && celda.getTipoDato() != TipoDato.NA &&
                    celda.getValor() != null) {
                // Incrementa el contador por cada celda válida encontrada.
                cuenta++;
            }
        }

        // Retorna una nueva Celda con la cuenta total (convertida a Double) y el tipo NUMERICO.
        return new Celda(cuenta, TipoDato.NUMERICO);
    }
}