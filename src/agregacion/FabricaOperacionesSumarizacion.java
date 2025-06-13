package agregacion;

/**
 * Fábrica que retorna una estrategia de sumarización según el tipo de operación solicitado.
 */

public class FabricaOperacionesSumarizacion {

    // Devuelve la implementación de sumarizador correspondiente al tipo de operación.

    public Sumarizador obtenerEstrategia(TipoOperacion tipoOperacion) {
        switch (tipoOperacion) {
            case SUMA:
                return new OperacionSuma();
            case MAXIMO:
                return new OperacionMaximo();
            case MINIMO:
                return new OperacionMinimo();
            case CUENTA:
                return new OperacionCuenta();
            case MEDIA:
                return new OperacionMedia();
            case VARIANZA:
                return new OperacionVarianza();
            case DESVIO_ESTANDAR:
                return new OperacionDesvioEstandar();
            default:
                throw new IllegalArgumentException("Operación de sumarización no soportada: "
                        + tipoOperacion);
        }
    }
}