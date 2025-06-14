package filtros;
import gestiondedatos.*;

/**
 * La interfaz Filtro indica que todas aquellas clases que implementen 'Filtro'
 * deben sobreescribir el metodo cumple(Object valorCelda, TipoDato tipoDatoCelda):boolean
 *
 *
 */

public interface Filtro {
    public boolean cumple(Object valorCelda, TipoDato tipoDatoCelda);
}