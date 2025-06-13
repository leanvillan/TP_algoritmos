package seleccion;
import gestiondedatos.*;
import visualizador.Visualizador;
import gestiondeerrores.ExcepcionOperacionNoValida;

import java.util.List;
import java.util.ArrayList;

public class Seleccion {

    private Tabla tabla;

    public Seleccion(Tabla tabla){
        this.tabla = tabla;
    }

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

        seleccionar(etiquetasFilasSeleccionadas, etiquetasColumnasSeleccionadas);
    }

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
        seleccionar(etiquetasFilasSeleccionadas, etiquetasColumnasSeleccionadas);
    }

    public void seleccionar(List<Etiqueta> etiquetasFilasSeleccionadas, List<Etiqueta> etiquetasColumnasSeleccionadas) {
        // Validaciones iniciales para las etiquetas
        if (etiquetasFilasSeleccionadas == null || etiquetasFilasSeleccionadas.isEmpty()) {
            throw new ExcepcionOperacionNoValida("No se especificaron etiquetas de fila para la selección. No se puede realizar la operación.");
        }
        if (etiquetasColumnasSeleccionadas == null || etiquetasColumnasSeleccionadas.isEmpty()) {
            throw new ExcepcionOperacionNoValida("No se especificaron etiquetas de columna para la selección. No se puede realizar la operación.");
        }

        for (Etiqueta etiqueta : etiquetasFilasSeleccionadas) {
            if (!(tabla.getEtiquetasFilas().contains(etiqueta))) {
                throw new ExcepcionOperacionNoValida("La etiqueta de fila (" + etiqueta.getValor() + ") no existe en la tabla.");
            }
        }

        for (Etiqueta etiqueta : etiquetasColumnasSeleccionadas) {
            if (!(tabla.getEtiquetasColumnas().contains(etiqueta))) {
                throw new ExcepcionOperacionNoValida("La etiqueta de columna (" + etiqueta.getValor() + ") no existe en la tabla.");
            }
        }

        Tabla tablaParcial = new Tabla(); // creamos una tablaParcial, agregamos los datos a visualizar en consola

        for (Etiqueta etiquetaColumna : etiquetasColumnasSeleccionadas) {
            TipoDato tipo = tabla.getTipoDatoColumna(etiquetaColumna); //obtengo el tipo de dato en esa columna

            List<Celda<?>> celdasColumnas = new ArrayList<>();

            for (Etiqueta etiquetaFila : etiquetasFilasSeleccionadas) {
                Celda<?> celda = tabla.getCelda(etiquetaColumna, etiquetaFila);
                //deberia agregarse a una lista de celdas
                celdasColumnas.add(celda); //agregamos todas las celdas que necesitamos en nuestra columna, de acuerdo a las filas especificadas
            }

            Columna columna = new Columna(tipo, celdasColumnas); /// hago una columna, "acortada" por las celdas seleccionadas
            tablaParcial.agregarColumna(etiquetaColumna, columna); /// agrego nueva columna, continua con el proceso para la siguiente columna
        }
        tablaParcial.agregarEtiquetasFila(etiquetasFilasSeleccionadas); /// Siempre coloco las etiquetas, para que no se generen automaticamente
        Visualizador.imprimirTabla(tablaParcial);
    }
}
