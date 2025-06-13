package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.TipoDato;
import java.util.ArrayList;
import java.util.List;

public class OperacionVarianza implements Sumarizador {

    // Calcula la varianza de los valores numéricos en una lista de celdas.
    // Solo considera celdas que contienen valores de tipo Number y no son NA.
    // La varianza se calcula dividiendo la suma de los cuadrados de las diferencias con la
    // media por (n - 1), donde n es el número de elementos válidos.

    @Override
    public Celda<?> sumarizar(List<Celda<?>> celdas) {
        List<Double> valoresNumericos = new ArrayList<>();

        // Recolectar todos los valores numéricos válidos
        for (Celda<?> celda : celdas) {
            // Ignora celdas nulas, de tipo NA, o con valor nulo.
            if (celda == null || celda.getTipoDato() == TipoDato.NA ||
                    celda.getValor() == null) {
                continue;
            }

            // Verifica si el valor de la celda es una instancia de Number.
            if (celda.getValor() instanceof Number) {
                valoresNumericos.add(((Number) celda.getValor()).doubleValue());
            } else {
                // Advertencia si se encuentra una celda con un valor no numérico
                // que no es NA, indicando que se ignorará en el cálculo.
                System.out.println("Advertencia: Celda con valor no numérico ignorada " +
                        "en el cálculo de la varianza: " + celda.getValor());
            }
        }

        // Se necesitan al menos dos valores para calcular la varianza muestral
        // (división por n-1).
        if (valoresNumericos.size() < 2) {
            return new Celda(null, TipoDato.NA); // No hay suficientes datos para
            // calcular la varianza.
        }

        // Calcular la media (promedio) de los valores válidos usando OperacionMedia
        OperacionMedia operacionMedia = new OperacionMedia();
        // Creamos celdas temporales a partir de los valores numéricos para pasarlos a
        // OperacionMedia.
        // Esto es necesario porque OperacionMedia espera List<Celda<?>>, no List<Double>.
        List<Celda<?>> celdasParaMedia = new ArrayList<>();
        for (Double valor : valoresNumericos) {
            celdasParaMedia.add(new Celda(valor, TipoDato.NUMERICO));
        }

        Celda<?> celdaMedia = operacionMedia.sumarizar(celdasParaMedia);

        // Si por alguna razón OperacionMedia devuelve NA (aunque con size >= 2 no debería),
        // la varianza tampoco se puede calcular.
        if (celdaMedia.getTipoDato() == TipoDato.NA || celdaMedia.getValor() == null) {
            return new Celda(null, TipoDato.NA);
        }

        double media = ((Number) celdaMedia.getValor()).doubleValue();

        // Calcular la suma de los cuadrados de las diferencias con la media
        double sumaCuadradosDiferencias = 0.0;
        for (Double valor : valoresNumericos) {
            sumaCuadradosDiferencias += Math.pow(valor - media, 2);
        }

        // Calcular la varianza muestral
        // Se divide por (n - 1) para la varianza muestral.
        double varianza = sumaCuadradosDiferencias / (valoresNumericos.size() - 1);

        // Redondeo a dos decimales
        varianza = Math.round(varianza * 100.0) / 100.0;

        // Retorna una nueva Celda con la varianza calculada (como Double) y el tipo NUMERICO.
        return new Celda(varianza, TipoDato.NUMERICO);
    }
}