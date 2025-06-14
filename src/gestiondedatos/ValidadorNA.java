package gestiondedatos;

public class ValidadorNA implements TipoValidador {
    @Override
    public boolean esValido(Object valor) {
        return valor == null;
    }
}
