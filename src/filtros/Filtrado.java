package filtros;

import gestiondedatos.Celda;
import gestiondedatos.Columna;
import gestiondedatos.Etiqueta;
import gestiondedatos.Tabla;
import gestiondeerrores.ExcepcionValidacion;

import java.util.ArrayList;
import java.util.List;

/**
 -  La clase 'Filtrado' contiene los metodos que filtran a la tabla.
 -
 -  Cada uno del los metodos de la clase recibe un tipo especifico de filtro y columna/s,  para poder
 -  obtener las filas que cumplen. Devuelve una tabla cuyas filas cumplen las condiciones del filtro.
 -
 -
 */

public class Filtrado {

    private Tabla tabla;

    public Filtrado(Tabla tabla) {
        this.tabla = tabla;
    }

    // Este metodo se utiliza para filtrar las tablas, tomando una columna a la vez y evaluando sus filas de acuerdo
    // a un unico Filtro comparador.

    public Tabla filtrarComparador(Etiqueta etiquetaColumna, Filtro comparador) {

        if (!tabla.getEtiquetasColumnas().contains(etiquetaColumna)) {
            throw new ExcepcionValidacion("La columna '" + etiquetaColumna.getValor() + "' NO existe en la tabla.");
        }
        if (comparador == null) {
            throw new ExcepcionValidacion("El comparador NO puede ser nulo.");
        }

        Columna columna = tabla.getColumna(etiquetaColumna);
        List<Etiqueta> etiquetaFilas = tabla.getEtiquetasFilas();
        List<Etiqueta> etiquetasFilasSeleccionadas = new ArrayList<>();

        for (int i = 0; i < columna.getCeldas().size(); i++) {
            Celda<?> celda = columna.getCeldas().get(i);
            Etiqueta etiquetaFila = etiquetaFilas.get(i);

            if (comparador.cumple(celda.getValor(), celda.getTipoDato())) {
                etiquetasFilasSeleccionadas.add(etiquetaFila);
            }
        }

        return TablaParcial.crearTablaParcial(tabla,etiquetasFilasSeleccionadas, tabla.getEtiquetasColumnas());
    }

    // Este metodo se encarga de obtener las filas de la tabla que cumplan con dos condiciones al mismo tiempo
    public Tabla filtrarLogico(FiltroLogico filtroLogico, Etiqueta etiquetaColumna1, Etiqueta etiquetaColumna2) {

        if (!tabla.getEtiquetasColumnas().contains(etiquetaColumna1) || !tabla.getEtiquetasColumnas().contains(etiquetaColumna2)) {
            throw new ExcepcionValidacion("Una o ambas columnas especificadas NO existen en la tabla.");
        }

        if (filtroLogico == null) {
            throw new ExcepcionValidacion("El filtro Lógico NO puede ser nulo.");
        }

        List<Etiqueta> etiquetasFilas = tabla.getEtiquetasFilas();
        List<Etiqueta> etiquetasFilasSeleccionadas = new ArrayList<>();

        for (Etiqueta etiquetaFila : etiquetasFilas) {
            boolean cumple = filtroLogico.cumpleEnFila(tabla, etiquetaFila, etiquetaColumna1, etiquetaColumna2);

            if (cumple) {
                etiquetasFilasSeleccionadas.add(etiquetaFila);
            }
        }
        return TablaParcial.crearTablaParcial(tabla, etiquetasFilasSeleccionadas, tabla.getEtiquetasColumnas());
    }

    // Este metodo se encarga obtener las filas cuya condicion sea negada, por el filtro "FiltroNot"

    public Tabla filtrarNot(FiltroNOT filtroNot, Etiqueta etiquetaColumna){
        if (!tabla.getEtiquetasColumnas().contains(etiquetaColumna)) {
            throw new ExcepcionValidacion("La columna '" + etiquetaColumna.getValor() + "' NO existe en la tabla.");
        }

        if (filtroNot == null) {
            throw new ExcepcionValidacion("El filtro NOT NO puede ser nulo.");
        }

        List<Etiqueta> etiquetasFilas = tabla.getEtiquetasFilas();
        List<Etiqueta> etiquetasFilasSeleccionadas = new ArrayList<>();

        for (Etiqueta etiquetaFila : etiquetasFilas) {
            boolean cumple = cumple = filtroNot.cumpleEnFila(tabla, etiquetaFila, etiquetaColumna);
            if (cumple) {
                etiquetasFilasSeleccionadas.add(etiquetaFila);
            }
        }
        return TablaParcial.crearTablaParcial(tabla, etiquetasFilasSeleccionadas, tabla.getEtiquetasColumnas());
    }

    // Este metodo filtra tablas segun filtros que no requieren de un pasaje de valor por parametro para instanciar el filtro

    public Tabla filtrarSinParametros(FiltroSinParametro filtroSinParametro,Etiqueta etiquetaColumna){

        if (!tabla.getEtiquetasColumnas().contains(etiquetaColumna)){
            throw new ExcepcionValidacion("La columna especificada "+ etiquetaColumna.getValor() +" NO existen en la tabla.");
        }

        if (filtroSinParametro == null) {
            throw new ExcepcionValidacion("El filtro Lógico NO puede ser nulo.");
        }

        List<Etiqueta> etiquetasFilas = tabla.getEtiquetasFilas();
        List<Etiqueta> etiquetasFilasSeleccionadas = new ArrayList<>();

        for (Etiqueta etiquetaFila : etiquetasFilas) {
            boolean cumple = filtroSinParametro.cumpleEnFila(tabla, etiquetaFila, etiquetaColumna);

            if (cumple) {
                etiquetasFilasSeleccionadas.add(etiquetaFila);
            }
        }
        return TablaParcial.crearTablaParcial(tabla, etiquetasFilasSeleccionadas, tabla.getEtiquetasColumnas());
    }
}
