package seleccion;
import filtros.TablaParcial;
import gestiondedatos.*;
import visualizador.Visualizador;
import gestiondeerrores.ExcepcionOperacionNoValida;

import java.util.List;
import java.util.ArrayList;

/** La clase 'Seleccion' se instancia ingresando un objeto Tabla en su constructor.
 * Una vez instanciada la clase "Seleccion" se pueden utilizar los metodos:
 *
 * @head(numfilas) -> Muestra las primeras filas de la tabla indicada por parametro.
 *
 * @tail(numfilas) -> Muestra las ultimas filas de la tabla indicada por parametro.
 *
 * @seleccionar(etiquetasFilasSeleccionadas, etiquetasColumnasSeleccionadas) -> Muestra las filas y
 * columnas seleccionadas de acuerdo a las etiquetas ingresadas por parametro.
 */

public class Seleccion {

    private Tabla tabla;

    public Seleccion(Tabla tabla){
        this.tabla = tabla;
    }


    // Muestra las primeras x filas de la tabla
    public void head(int numFilas) {

        int filasExistentes = tabla.contarFilas();

        if (numFilas <= 0) {
            throw new ExcepcionOperacionNoValida("El número de filas debe ser mayor que 0 para head().");
        }
        if (filasExistentes < numFilas) {
            throw new ExcepcionOperacionNoValida("La cantidad de filas ingresada (" + numFilas + ") es mayor a la cantidad de filas actuales en la tabla (" + filasExistentes + ").");
        }

        List<Etiqueta> etiquetasFilas = tabla.getEtiquetasFilas();
        List<Etiqueta> etiquetasFilasSeleccionadas = new ArrayList<>();

        for (int i = 0; i < numFilas; i++) {
            etiquetasFilasSeleccionadas.add(etiquetasFilas.get(i));
        }

        List<Etiqueta> etiquetasColumnasSeleccionadas = tabla.getEtiquetasColumnas();

        Tabla tablaParcial = TablaParcial.crearTablaParcial(this.tabla,etiquetasFilasSeleccionadas,etiquetasColumnasSeleccionadas);
        Visualizador.imprimirTabla(tablaParcial);

    }

    // Muestra ultimas x filas de la tabla
    public void tail(int numFilas) {
        int filasExistentes = tabla.contarFilas();

        if (numFilas <= 0) {
            throw new ExcepcionOperacionNoValida("El número de filas debe ser mayor que 0 para tail().");
        }
        if (filasExistentes < numFilas) {
            throw new ExcepcionOperacionNoValida("La cantidad de filas ingresada (" + numFilas + ") es mayor a la cantidad de filas actuales en la tabla (" + filasExistentes + ").");
        }

        List<Etiqueta> todasLasEtiquetasFilas = tabla.getEtiquetasFilas();
        List<Etiqueta> etiquetasFilasSeleccionadas = new ArrayList<>();

        int inicio = filasExistentes - numFilas;

        for (int i = inicio; i < filasExistentes; i++) {
            etiquetasFilasSeleccionadas.add(todasLasEtiquetasFilas.get(i));
        }

        List<Etiqueta> etiquetasColumnasSeleccionadas = tabla.getEtiquetasColumnas();
        Tabla tablaParcial = TablaParcial.crearTablaParcial(this.tabla, etiquetasFilasSeleccionadas, etiquetasColumnasSeleccionadas);
        Visualizador.imprimirTabla(tablaParcial);
    }

    // Seleccionamos de manera parcial la tabla, a partir listas de etiquetas de filas y columnas
    // retorna una vista de la seleccion parcial

    public void seleccionar(List<Etiqueta> etiquetasFilasSeleccionadas, List<Etiqueta> etiquetasColumnasSeleccionadas) {
        Tabla tablaParcial = TablaParcial.crearTablaParcial(this.tabla, etiquetasFilasSeleccionadas, etiquetasColumnasSeleccionadas);
        Visualizador.imprimirTabla(tablaParcial);
    }
}
