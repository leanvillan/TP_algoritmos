package gestiondedatos;

public class ValidadorBooleano implements TipoValidador {
    @Override
    public boolean esValido(Object valor) {
        return valor instanceof Boolean;
    }
}