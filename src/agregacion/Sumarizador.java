package agregacion;

import gestiondedatos.Celda;
import java.util.List;

/**
 * Interfaz que define el contrato para aplicar una operación de agregación
 * sobre una lista de celdas numéricas.
 *
 * Cada implementación concreta (suma, media, varianza, etc.) define cómo
 * se resume la lista y devuelve una única celda como resultado.
 */
public interface Sumarizador {

    // Aplica la operación de agregación sobre una lista de celdas y devuelve el resultado en una celda.

    Celda<?> sumarizar(List<Celda<?>> celdas);
}

