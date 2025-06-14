package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.TipoDato;
import java.util.List;

public class OperacionDesvioEstandar implements Sumarizador {

    // Calcula el desvío estándar de los valores numéricos en una lista de celdas.
    // Solo considera celdas que contienen valores de tipo Number y no son NA.
    // El desvío estándar se calcula como la raíz cuadrada de la varianza muestral.

    @Override
    public Celda<?> sumarizar(List<Celda<?>> celdas) {
        // Calcular la varianza utilizando la OperacionVarianza.
        // Se crea una instancia de OperacionVarianza.
        OperacionVarianza operacionVarianza = new OperacionVarianza();

        // Se llama al metodo sumarizar de OperacionVarianza, pasándole la lista de celdas.
        Celda<?> celdaVarianza = operacionVarianza.sumarizar(celdas);

        // Verificar el resultado de la varianza:
        // Si la varianza no se pudo calcular (por ejemplo, por insuficiencia de datos o solo NA),
        // el desvío estándar tampoco se puede calcular.
        if (celdaVarianza.getTipoDato() == TipoDato.NA || celdaVarianza.getValor() == null) {
            return new Celda(null, TipoDato.NA); // Retorna una Celda NA.
        }

        // Obtener el valor de la varianza de la celda resultante.
        double varianza = ((Number) celdaVarianza.getValor()).doubleValue();

        // Calcular el desvío estándar:
        // El desvío estándar es la raíz cuadrada de la varianza.
        double desvioEstandar = Math.sqrt(varianza);

        // Redondeo a dos decimales
        desvioEstandar = Math.round(desvioEstandar * 100.0) / 100.0;

        // Se retorna una nueva Celda con el desvío estándar calculado (como Double)
        // y el tipo de dato NUMERICO.
        return new Celda(desvioEstandar, TipoDato.NUMERICO);
    }
}