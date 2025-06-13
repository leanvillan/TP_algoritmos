package agregacion;

public class FabricaOperacionesSumarizacion {
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
