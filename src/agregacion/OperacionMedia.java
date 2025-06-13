package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.TipoDato;
import java.util.List;

public class OperacionMedia implements Sumarizador {

    // Calcula la media (promedio) de los valores numéricos en una lista de celdas.
    // Solo considera celdas que contienen valores de tipo Number y no son NA.

    @Override
    public Celda<?> sumarizar(List<Celda<?>> celdas) {
        double suma = 0.0;
        int conteoValoresValidos = 0;

        // Itera sobre cada celda en la lista.
        for (Celda<?> celda : celdas) {
            // Ignora celdas nulas, de tipo NA, o con valor nulo.
            if (celda == null || celda.getTipoDato() == TipoDato.NA ||
                    celda.getValor() == null) {
                continue;
            }

            // Verifica si el valor de la celda es una instancia de Number.
            if (celda.getValor() instanceof Number) {
                // Convierte el valor a double y lo añade a la suma.
                suma += ((Number) celda.getValor()).doubleValue();
                // Incrementa el contador de valores válidos.
                conteoValoresValidos++;
            } else {
                // Advertencia si se encuentra una celda con un valor no numérico
                // que no es NA, indicando que se ignorará en el cálculo.
                System.out.println("Advertencia: Celda con valor no numérico ignorada " +
                        "en el cálculo de la media: " + celda.getValor());
            }
        }

        // Si no se encontró ningún valor numérico válido, la media no puede calcularse.
        if (conteoValoresValidos == 0) {
            return new Celda(null, TipoDato.NA); // Retorna una Celda NA.
        }

        // Calcula la media dividiendo la suma total por el conteo de valores válidos.
        double media = suma / conteoValoresValidos;

        // Redondeo a dos decimales
        media = Math.round(media * 100.0) / 100.0;

        // Retorna una nueva Celda con la media calculada (como Double) y el tipo NUMERICO.
        return new Celda(media, TipoDato.NUMERICO);
    }
}