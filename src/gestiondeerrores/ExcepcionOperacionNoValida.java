package gestiondeerrores;

//Se lanza cuando se intenta realizar una operación no permitida sobre la estructura

public class ExcepcionOperacionNoValida extends ExcepcionTabular {
    public ExcepcionOperacionNoValida(String mensaje) {
        super(mensaje);
    }
}
