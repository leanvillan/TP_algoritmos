package gestiondedatos;

import gestiondeerrores.ExcepcionValidacion;

public class Celda<T> {

    private T valor;              // Valor real de la celda (puede ser null si es NA)
    private TipoDato tipoDato;   // Tipo de dato que representa esta celda

    // Constructor completo. Valida la consistencia entre el valor y el tipo de dato.

    public Celda(T valor, TipoDato tipoDato) {
        if (tipoDato == null) {
            throw new ExcepcionValidacion("El tipo de dato de la celda no puede ser nulo.");
        }

        // Caso 1: valor nulo → tipoDato debe ser NA
        if (valor == null) {
            if (tipoDato != TipoDato.NA) {
                throw new ExcepcionValidacion("Si el valor es nulo, el tipo debe ser NA.");
            }
        }
        // Caso 2: valor no nulo → tipoDato no puede ser NA
        else {
            if (tipoDato == TipoDato.NA) {
                throw new ExcepcionValidacion("Si el valor no es nulo, el tipo no puede ser NA.");
            }
        }

        this.valor = valor;
        this.tipoDato = tipoDato;
    }

    // Constructor por defecto: crea una celda vacía (NA).

    public Celda() {
        this(null, TipoDato.NA);
    }

    // Getters

    // Devuelve el valor almacenado en la celda.

    public T getValor() {
        return valor;
    }

    // Devuelve el tipo de dato declarado para esta celda.

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    // Funciones auxiliares

    // Indica si esta celda representa un valor faltante (NA).

    public boolean isNA() {
        return this.tipoDato == TipoDato.NA;
    }

    // Crea una copia profunda de la celda.

    public Celda<T> copiar() {
        return new Celda<>(this.valor, this.tipoDato);
    }

    // Devuelve el valor como texto. Si es NA, imprime "NA".

    @Override
    public String toString() {
        return isNA() ? "NA" : String.valueOf(valor);
    }
}
