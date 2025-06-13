package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.TipoDato;
import java.util.List;


public class OperacionMaximo implements Sumarizador {

    // Calcula el valor numérico máximo de una lista de celdas.
    // Solo considera celdas que contienen valores de tipo Number y no son NA.

    @Override
    public Celda<?> sumarizar(List<Celda<?>> celdas) {
        // Inicializa 'maximo' a null. Se usa Double (clase wrapper) para almacenar null.
        Double maximo = null;
        // Indica si se encontró al menos un valor numérico válido.
        boolean encontradoPrimerValorValido = false;

        // Itera sobre cada celda en la lista.
        for (Celda<?> celda : celdas) {
            // Ignora celdas nulas, de tipo NA, o con valor nulo.
            if (celda == null || celda.getTipoDato() == TipoDato.NA ||
                    celda.getValor() == null) {
                continue;
            }

            // Verifica si el valor de la celda es una instancia de Number.
            if (celda.getValor() instanceof Number) {
                // Convierte el valor de la celda a double para facilitar las comparaciones.
                double valorActual = ((Number) celda.getValor()).doubleValue();

                // Si es el primer valor numérico válido encontrado:
                if (!encontradoPrimerValorValido) {
                    // Se asigna como el máximo inicial.
                    maximo = valorActual;
                    encontradoPrimerValorValido = true;
                } else {
                    // Si ya se encontró un valor válido, se compara con el actual.
                    if (valorActual > maximo) {
                        // Si el valor actual es mayor, se actualiza como el nuevo máximo.
                        maximo = valorActual;
                    }
                }
            } else {
                // Advertencia si se encuentra una celda con un valor no numérico
                // que no es NA, indicando que se ignorará en el cálculo.
                System.out.println("Advertencia: Celda con valor no numérico ignorada en " +
                        "la búsqueda del máximo: " + celda.getValor());
            }
        }

        // Si no se encontró ningún valor numérico válido en la lista de celdas de entrada,
        // la celda resultante es de tipo NA.
        if (!encontradoPrimerValorValido) {
            return new Celda(null, TipoDato.NA);
        }

        // Si se encontró al menos un valor numérico válido, se retorna una nueva Celda
        // con el valor máximo calculado (como Double) y el tipo NUMERICO.
        return new Celda(maximo, TipoDato.NUMERICO);
    }
}