package gestiondedatos;

import java.util.Objects;
import gestiondeerrores.ExcepcionValidacion;

 // Representa una etiqueta de tipo entero (por ejemplo: 0, 1, 2, ...).

public class EtiquetaEntero implements Etiqueta<Integer> {

    private Integer etiqueta;

    // Constructor que recibe un valor entero. No se permite un valor nulo.

    public EtiquetaEntero(Integer etiqueta) {
        if (etiqueta == null) {
            throw new ExcepcionValidacion("La etiqueta de tipo Entero no puede ser nula.");
        }
        this.etiqueta = etiqueta;
    }

    // Devuelve el valor entero de la etiqueta.

    @Override
    public Integer getValor() {
        return etiqueta;
    }

    // Asigna un nuevo valor entero a la etiqueta.

    public void setValor(Integer nuevoValor) {
        if (nuevoValor == null) {
            throw new ExcepcionValidacion("El valor de la etiqueta Entero no puede ser nulo.");
        }
        this.etiqueta = nuevoValor;
    }

    // Devuelve una copia de esta etiqueta.

    @Override
    public Etiqueta<Integer> copiar() {
        return new EtiquetaEntero(this.etiqueta);
    }

    // Compara esta etiqueta con otra para verificar igualdad.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EtiquetaEntero otraEtiqueta = (EtiquetaEntero) obj;
        return this.etiqueta.equals(otraEtiqueta.etiqueta);
    }

    // Calcula el código hash en base al valor.

    @Override
    public int hashCode() {
        return Objects.hash(etiqueta);
    }

    // Representación en texto de la etiqueta.

    @Override
    public String toString() {
        return String.valueOf(etiqueta);
    }
}
