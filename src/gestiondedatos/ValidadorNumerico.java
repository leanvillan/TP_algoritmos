package gestiondedatos;

public class ValidadorNumerico implements TipoValidador {
    @Override
    public boolean esValido(Object valor) {
        return valor instanceof Number;
    }
}