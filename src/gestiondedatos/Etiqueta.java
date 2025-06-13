package gestiondedatos;

public interface Etiqueta<T> {

    // Devuelve el valor el valor asociado a esta etiqueta.
    // Puede ser un número (Integer) o un texto (String), según implementación.
    T getValor();

    // Devuelve una copia independiente de la etiqueta.
    Etiqueta<T> copiar();
}
