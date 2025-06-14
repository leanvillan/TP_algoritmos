package gestiondeerrores;

//Se lanza cuando ocurre un error de validación de datos (tipos incorrectos, valores no permitidos, etc)

public class ExcepcionValidacion extends ExcepcionTabular {
    public ExcepcionValidacion(String mensaje) {
        super(mensaje);
    }
}
