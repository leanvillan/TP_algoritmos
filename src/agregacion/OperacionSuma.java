package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.TipoDato;
import java.util.List;

public class OperacionSuma implements Sumarizador {

    // Calcula la suma de los valores numéricos de una lista de celdas.
    // Solo considera celdas que contienen valores de tipo Number y no son NA.

    @Override
    public Celda<?> sumarizar(List<Celda<?>> celdas) {
        // Inicializa la suma a 0.0 para acumular los valores numéricos.
        double suma = 0.0;
        // Indica si se encontró al menos un valor numérico válido.
        boolean hayValorValido = false;

        // Itera sobre cada celda en la lista.
        for (Celda<?> celda : celdas) {
            // Ignora celdas nulas, de tipo NA, o con valor nulo.
            if (celda == null || celda.getTipoDato() == TipoDato.NA ||
                    celda.getValor() == null) {
                continue;
            }

            // Verifica si el valor de la celda es una instancia de Number.
            if (celda.getValor() instanceof Number) {
                // Convierte el valor de la celda a double y lo agrega a la suma.
                suma += ((Number) celda.getValor()).doubleValue();
                hayValorValido = true;
            } else {
                // Advertencia si se encuentra una celda con un valor no numérico
                // que no es NA, indicando que se ignorará en el cálculo.
                System.out.println("Advertencia: Celda con valor no numérico ignorada " +
                        "en la suma: " + celda.getValor());
            }
        }

        // Si no se encontró ningún valor numérico válido para sumar, la celda resultante
        // es de tipo NA.
        if (!hayValorValido) {
            return new Celda(null, TipoDato.NA);
        }

        // Si se encontró al menos un valor numérico válido, se retorna una nueva Celda
        // con la suma calculada (como Double) y el tipo NUMERICO.
        return new Celda(suma, TipoDato.NUMERICO);
    }
}