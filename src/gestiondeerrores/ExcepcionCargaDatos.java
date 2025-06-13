package gestiondeerrores;

//Se lanza cuando ocurre un error al cargar o parsear datos desde archivos externos

public class ExcepcionCargaDatos extends ExcepcionTabular {
    public ExcepcionCargaDatos(String mensaje) {
        super(mensaje);
    }
}
