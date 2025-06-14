package gestiondedatos;

import java.util.Objects;
import gestiondeerrores.ExcepcionValidacion;

 // Representa una etiqueta de tipo String (por ejemplo: "nombre", "edad", etc).

public class EtiquetaString implements Etiqueta<String> {

    private String etiqueta;

    // Constructor que recibe un texto como etiqueta. Valida que no sea nulo ni vacío.

    public EtiquetaString(String etiqueta) {
        if (etiqueta == null || etiqueta.trim().isEmpty()) {
            throw new ExcepcionValidacion("La etiqueta de tipo String no puede ser nula o vacía.");
        }
        this.etiqueta = etiqueta;
    }

    // Devuelve el valor de la etiqueta.

    @Override
    public String getValor() {
        return etiqueta;
    }

    // Asigna un nuevo valor a la etiqueta.

    public void setValor(String nuevoValor) {
        if (nuevoValor == null || nuevoValor.trim().isEmpty()) {
            throw new ExcepcionValidacion("El valor de la etiqueta String no puede ser" +
                    " nulo o vacío.");
        }
        this.etiqueta = nuevoValor;
    }

    // Devuelve una copia de la etiqueta.

    @Override
    public Etiqueta<String> copiar() {
        return new EtiquetaString(this.etiqueta);
    }

    // Compara esta etiqueta con otra para verificar igualdad.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EtiquetaString otraEtiqueta = (EtiquetaString) obj;
        return this.etiqueta.equals(otraEtiqueta.etiqueta);
    }

    // Calcula el código hash de la etiqueta.

    @Override
    public int hashCode() {
        return Objects.hash(etiqueta);
    }

    // Devuelve la representación textual de la etiqueta.

    @Override
    public String toString() {
        return etiqueta;
    }
}