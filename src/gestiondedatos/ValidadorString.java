package gestiondedatos;

public class ValidadorString implements TipoValidador {
    @Override
    public boolean esValido(Object valor) {
        return valor instanceof String;
    }
}