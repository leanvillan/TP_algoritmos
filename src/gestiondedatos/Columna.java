package gestiondedatos;

import java.util.List;
import java.util.ArrayList;
import gestiondeerrores.ExcepcionValidacion;

public class Columna {

    private TipoDato tipoDato; // Tipo de dato común a todas las celdas de la columna
    private List<Celda<?>> listaCeldas; // Lista que contiene las celdas de la columna

    // Constructor que recibe un tipo de dato y una lista inicial de celdas.
    // Valida que todas las celdas sean consistentes con el tipo de dato.

    public Columna() {

        this.tipoDato = TipoDato.NA;
        this.listaCeldas = new ArrayList<>();

    }

    public Columna(TipoDato tipoDato, List<Celda<?>> listaDeCeldas) {
        if (tipoDato == null) {
            throw new ExcepcionValidacion("El tipo de dato de la columna no puede ser nulo.");
        }

        if (tipoDato == TipoDato.NA) {
            throw new ExcepcionValidacion("Una columna no puede ser de tipo NA. NA se" +
                    " utiliza para valores faltantes.");
        }

        if (listaDeCeldas == null) {
            throw new ExcepcionValidacion("La lista inicial de celdas no puede ser nula.");
        }

        this.tipoDato = tipoDato;
        this.listaCeldas = new ArrayList<>(listaDeCeldas);

        // Validación de cada celda al inicializar
        for (Celda<?> celda : this.listaCeldas) {
            if (celda == null) {
                throw new ExcepcionValidacion("La lista de celdas contiene un elemento nulo.");
            }

            if (!ValidadorTipoDato.esValido(celda.getValor(), this.tipoDato, celda.getTipoDato())) {
                throw new ExcepcionValidacion("La celda con valor '" + celda.getValor() +
                        "' y tipo '" + celda.getTipoDato() +
                        "' no es compatible con el tipo de columna '" + this.tipoDato + "'.");
            }
        }
    }

    // Constructor para crear una columna vacía con un tipo definido.

    public Columna(TipoDato tipoDato) {
        if (tipoDato == null) {
            throw new ExcepcionValidacion("El tipo de dato de la columna no puede ser nulo.");
        }

        if (tipoDato == TipoDato.NA) {
            throw new ExcepcionValidacion("Una columna no puede ser de tipo NA.");
        }

        this.tipoDato = tipoDato;
        this.listaCeldas = new ArrayList<>();
    }

    // Métodos públicos

    // Agrega una celda a la columna. Verifica que el tipo del valor coincida con el tipo
    // definido para la columna.

    public void agregarCelda(Celda<?> celda) {
        if (celda == null) {
            throw new ExcepcionValidacion("La celda a agregar no puede ser nula.");
        }

        if (ValidadorTipoDato.esValido(celda.getValor(), this.tipoDato, celda.getTipoDato())) {
            this.listaCeldas.add(celda);
        } else {
            throw new ExcepcionValidacion("No se pudo agregar la celda con valor '" + celda.getValor() +
                    "' y tipo '" + celda.getTipoDato() +
                    "' porque no es compatible con el tipo de columna '" + this.tipoDato + "'.");
        }
    }

    // Elimina una celda en la posición dada (por índice).

    public void eliminarCelda(int indiceCelda) {
        if (indiceCelda < 0 || indiceCelda >= listaCeldas.size()) {
            throw new IndexOutOfBoundsException("Índice de celda fuera de rango: " + indiceCelda +
                    ". Tamaño actual de la columna: " + listaCeldas.size());
        }

        listaCeldas.remove(indiceCelda);
    }

    // Devuelve el tipo de dato definido para esta columna.

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    // Devuelve la lista completa de celdas (acceso total, no copia).

    public List<Celda<?>> getCeldas() {
        return listaCeldas;
    }

    // Devuelve la cantidad de celdas en la columna.

    public int contarCeldas() {
        return listaCeldas.size();
    }

    // Devuelve la celda en el índice indicado.

    public Celda<?> getCelda(int indiceFila) {
        if (indiceFila < 0 || indiceFila >= listaCeldas.size()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + indiceFila);
        }
        return listaCeldas.get(indiceFila);
    }

    // Devuelve una copia profunda de esta columna. Se copian todas las celdas también (no
    // solo referencias).

    public Columna copiar() {
        Columna nuevaColumna = new Columna(this.tipoDato);

        for (Celda<?> celdaOriginal : this.listaCeldas) {
            nuevaColumna.agregarCelda(celdaOriginal.copiar());
        }

        return nuevaColumna;
    }
}
