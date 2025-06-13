package filtros;

import gestiondedatos.*;
import gestiondeerrores.ExcepcionOperacionNoValida;
import visualizador.Visualizador;
import filtros.*;


/**
 * La clase 'ControladorFiltro' se inicializa con un objeto 'Tabla' y la instanciacion de un objeto 'Filtrado'
 *
 * Esta clase se encarga de controlar todos los tipos de filtros existentes, de acuerdo a los parametros que ingresados
 * en los métodos void :
 * 'filtroSimple()'
 * 'filtroCompuesto()'
 * 'filtroNOT()'
 * 'filtrarSinParametros()'
 *
 *
 */


public class ControladorFiltro {

    private Tabla tabla;
    private Filtrado filtroCondiciones;

    public ControladorFiltro(Tabla tabla) {
        if (tabla == null) {
            throw new ExcepcionOperacionNoValida("La tabla no puede ser nula");
        }
        this.tabla = tabla;
        this.filtroCondiciones = new Filtrado(tabla);
    }

    public void filtroSimple(Etiqueta etiquetaColumna, TipoComparador tipoComparador, Object valorAComparar) {

        /// Creamos el filtro comparador
        Filtro comparador = FiltroFactory.crearFiltroComparador(tipoComparador, valorAComparar);
        /// Obtenemos la tabla filtrada , visualizamos posteriormente.
        Tabla tablaFiltrada = filtroCondiciones.filtrarComparador(etiquetaColumna, comparador);
        Visualizador.imprimirTabla(tablaFiltrada);

    }

    public void filtroCompuesto(OperadorLogico operadorLogico,
                                Etiqueta etiquetaColumna1, TipoComparador tipoComparador1, Object valor1,
                                Etiqueta etiquetaColumna2, TipoComparador tipoComparador2, Object valor2) {

        /// Creamos los filtros internos, utilizando tipos de comparadores y valores por parametro
        Filtro filtroInterno1 = FiltroFactory.crearFiltroComparador(tipoComparador1, valor1);
        Filtro filtroInterno2 = FiltroFactory.crearFiltroComparador(tipoComparador2, valor2);

        /// Creamos el filtro logico, tomando los filtros internos creados anteriormente
        FiltroLogico filtroLogico = FiltroFactory.crearFiltroLogico(operadorLogico, filtroInterno1, filtroInterno2);

        /// Obtenemos la tabla filtrada , visualizamos posteriormente.

        Tabla tablaFiltrada = filtroCondiciones.filtrarLogico(filtroLogico, etiquetaColumna1, etiquetaColumna2);
        Visualizador.imprimirTabla(tablaFiltrada);
    }

    public void filtrarNOT(Etiqueta etiquetaColumna, TipoComparador tipoComparador, Object valor) {

        ///  Creamos el unico filtro logico que recibe FiltroNOT
        Filtro filtroInterno = FiltroFactory.crearFiltroComparador(tipoComparador, valor);
        ///  Creamos el filtroNOT
        FiltroNOT filtroNot = FiltroFactory.crearFiltroNOT(filtroInterno);
        /// Obtenemos la tabla filtrada , visualizamos posteriormente.
        Tabla tablaFiltrada = filtroCondiciones.filtrarNot(filtroNot, etiquetaColumna);
        Visualizador.imprimirTabla(tablaFiltrada);
    }

    public void filtrarSinParametros(TipoSinParametro tipo, Etiqueta etiquetaColumna){

        /// Creamos el FiltroSinParametros identificado por su tipo de filtro.
        FiltroSinParametro filtro = FiltroFactory.crearFiltroSinParametros(tipo);
        /// Obtenemos la tabla filtrada , visualizamos posteriormente.
        Tabla tablaFiltrada = filtroCondiciones.filtrarSinParametros(filtro,etiquetaColumna);
        Visualizador.imprimirTabla(tablaFiltrada);
    }
}
