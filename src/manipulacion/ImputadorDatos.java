package manipulacion;
import gestiondedatos.*;

//implementa la imputación de NA
//imputa todos los valores NA de la tabla por el valor dado, si es compatible

public class ImputadorDatos extends ManipuladorDatos {
    private Object valorImputacion;

    public ImputadorDatos(Object valorImputacion) {
        this.valorImputacion = valorImputacion;
    }

    @Override
    public void manipular(Tabla tabla) {
        for (Etiqueta etiquetaCol : tabla.getEtiquetasColumnas()) {
            Columna columna = tabla.getColumna(etiquetaCol);

            for (int i = 0; i < columna.contarCeldas(); i++) {
                Celda<?> celda = columna.getCelda(i);

                if (celda.isNA()) {
                    //Acá reemplaza solo si el valor es compatible
                    TipoDato tipoEsperado = columna.getTipoDato();

                    if (ValidadorTipoDato.esValido(valorImputacion, tipoEsperado, tipoEsperado)) {
                        columna.getCeldas().set(i, new Celda<>(valorImputacion, tipoEsperado));
                    }
                }
            }
        }
    }
}
