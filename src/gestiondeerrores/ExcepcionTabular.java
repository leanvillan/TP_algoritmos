package gestiondeerrores;

//Excepción base para todas las excepciones específicas de la librería tabular


public abstract class ExcepcionTabular extends RuntimeException {
    private final String mensaje;

    public ExcepcionTabular(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
