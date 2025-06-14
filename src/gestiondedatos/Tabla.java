package gestiondedatos;

import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import gestiondeerrores.ExcepcionValidacion;
import gestiondeerrores.ExcepcionOperacionNoValida;
import manipulacion.*;
import visualizador.*;
import seleccion.*;
import copiayconcatenacion.*;
import filtros.*;


public class Tabla {

    private Map<Etiqueta, Columna> columnas;   // Mapa de columnas indexadas por etiqueta
    private List<Etiqueta> etiquetasFilas;     // Lista de etiquetas para cada fila
    private List<Etiqueta> etiquetasColumnas;  // Lista de etiquetas de columnas

    // Constructor por defecto. Inicializa las estructuras internas vacías.

    public Tabla() {
        this.columnas = new LinkedHashMap<>();
        this.etiquetasFilas = new ArrayList<>();
        this.etiquetasColumnas = new ArrayList<>();
    }

    //este constructor recibe listas de etiquetas de columnas y tipos de datos para cada columna
    public Tabla(List<Etiqueta> etiquetasColumnas, List<TipoDato> tiposColumnas) {
        this.columnas = new LinkedHashMap<>();
        this.etiquetasFilas = new ArrayList<>();
        this.etiquetasColumnas = new ArrayList<>(etiquetasColumnas);

        if (etiquetasColumnas.size() != tiposColumnas.size()) {
            throw new IllegalArgumentException("Las listas de etiquetas y tipos de columnas deben tener el mismo tamaño.");
        }

        for (int i = 0; i < etiquetasColumnas.size(); i++) {
            Etiqueta etiqueta = etiquetasColumnas.get(i);
            TipoDato tipo = tiposColumnas.get(i);
            Columna columna = new Columna(tipo); // Columna vacía pero con el tipo correcto
            this.columnas.put(etiqueta, columna);
        }
    }


    // Métodos de columnas

    // Agrega una columna a la tabla con una etiqueta explícita.
    // Verifica duplicación y validez de la columna.

    public void agregarColumna(Etiqueta etiquetaColumna, Columna columna) {
        if (columna == null) {
            throw new ExcepcionValidacion("La columna a agregar no puede ser nula.");
        }

        if (columnas.containsKey(etiquetaColumna)) {
            throw new IllegalArgumentException("Ya existe una columna con la etiqueta: " +
                    etiquetaColumna.getValor());
        }

        columnas.put(etiquetaColumna, columna);
        etiquetasColumnas.add(etiquetaColumna);
    }

    // Agrega una columna con una etiqueta numérica automática.

    public void agregarColumna(Columna columna) {
        EtiquetaEntero nuevaEtiqueta = new EtiquetaEntero(this.contarColumnas());
        agregarColumna(nuevaEtiqueta, columna);
    }

    // Devuelve una columna a partir de su etiqueta. Lanza excepción si no existe.

    public Columna getColumna(Etiqueta etiquetaColumna) {
        if (!columnas.containsKey(etiquetaColumna)) {
            throw new ExcepcionOperacionNoValida("No existe la columna: " +
                    etiquetaColumna.getValor());
        }
        return columnas.get(etiquetaColumna);
    }

    // Devuelve el tipo de dato de una columna específica.

    public TipoDato getTipoDatoColumna(Etiqueta etiquetaColumna) {
        return columnas.get(etiquetaColumna).getTipoDato();
    }

    // Elimina una columna de la tabla. Retorna true si se elimina exitosamente.

    public boolean eliminarColumna(Etiqueta etiquetaColumna) {
        if (!columnas.containsKey(etiquetaColumna)) {
            throw new ExcepcionOperacionNoValida("No existe la columna: " + etiquetaColumna.getValor());
        }

        etiquetasColumnas.remove(etiquetaColumna);
        return columnas.remove(etiquetaColumna) != null;
    }

    // Devuelve la lista de etiquetas de columnas.

    public List<Etiqueta> getEtiquetasColumnas() {
        return etiquetasColumnas;
    }

    // Devuelve la cantidad de columnas de la tabla.

    public int contarColumnas() {
        return columnas.size();
    }

    // Devuelve el mapa completo de columnas.

    public Map<Etiqueta, Columna> getColumnasMap() {
        return columnas;
    }

    // Métodos de filas

    // Agrega una fila completa a la tabla con etiqueta.
    // Valida que la cantidad de celdas coincida con las columnas existentes.

    public void agregarFila(Etiqueta etiquetaFila, List<Celda<?>> fila) {
        if (fila == null) {
            throw new IllegalArgumentException("La fila a agregar no puede ser nula.");
        }

        if (fila.size() != this.contarColumnas()) {
            throw new IllegalArgumentException("El número de celdas (" + fila.size() +
                    ") no coincide con el número de columnas (" + this.contarColumnas() + ").");
        }

        int i = 0;
        for (Columna columna : columnas.values()) {
            columna.agregarCelda(fila.get(i));
            i++;
        }

        etiquetasFilas.add(etiquetaFila);
    }

    // Agrega una fila sin etiqueta explícita. Se genera una etiqueta automática (entero).

    public void agregarFila(List<Celda<?>> fila) {
        EtiquetaEntero nuevaEtiqueta = new EtiquetaEntero(this.contarFilas());
        agregarFila(nuevaEtiqueta, fila);
    }

    // Devuelve una fila a partir de su etiqueta.

    public List<Celda<?>> getFila(Etiqueta etiquetaFila) {
        int indice = etiquetasFilas.indexOf(etiquetaFila);
        List<Celda<?>> fila = new ArrayList<>();

        for (Columna columna : columnas.values()) {
            fila.add(columna.getCelda(indice));
        }

        return fila;
    }

    // Devuelve una fila a partir de su índice numérico.

    public List<Celda<?>> getFila(int indiceFila) {
        if (indiceFila < 0 || indiceFila >= contarFilas()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + indiceFila);
        }

        List<Celda<?>> fila = new ArrayList<>();
        for (Columna columna : columnas.values()) {
            fila.add(columna.getCelda(indiceFila));
        }

        return fila;
    }

    // Elimina una fila de la tabla usando su etiqueta.
    // También elimina las celdas correspondientes de todas las columnas.

    public void eliminarFila(Etiqueta etiquetaFila) {
        int indice = etiquetasFilas.indexOf(etiquetaFila);

        if (indice == -1) {
            throw new IllegalArgumentException("La fila con la etiqueta '" +
                    etiquetaFila.getValor() + "' no existe.");
        }

        etiquetasFilas.remove(indice);

        for (Columna columna : columnas.values()) {
            columna.eliminarCelda(indice);
        }
    }

    // Agrega etiquetas personalizadas a las filas.
    // Muestra advertencia si no coincide la cantidad.

    public void agregarEtiquetasFila(List<Etiqueta> nuevasEtiquetasFilas) {
        int cantidadActual = contarFilas();
        int cantidadNueva = nuevasEtiquetasFilas.size();

        if (cantidadNueva > cantidadActual) {
            System.out.println("Cantidad de etiquetas ingresada (" + cantidadNueva + ") " +
                    "es MAYOR a la cantidad de filas (" + cantidadActual + ")");
        } else if (cantidadNueva < cantidadActual) {
            System.out.println("Cantidad de etiquetas ingresada (" + cantidadNueva + ") es" +
                    " MENOR a la cantidad de filas (" + cantidadActual + ")");
        } else {
            this.etiquetasFilas = nuevasEtiquetasFilas;
        }
    }

    // Genera etiquetas numéricas automáticamente para las filas si no existen.

    public void generarEtiquetasFilas() {
        if (etiquetasFilas.isEmpty()) {
            for (int i = 0; i < contarFilas(); i++) {
                etiquetasFilas.add(new EtiquetaEntero(i));
            }
        } else {
            System.out.println("Ya existen etiquetas. No se pueden generar automáticamente.");
        }
    }

    // Devuelve la lista de etiquetas de filas.

    public List<Etiqueta> getEtiquetasFilas() {
        return etiquetasFilas;
    }

    // Devuelve la cantidad de filas. Se basa en la primera columna para calcular.

    public int contarFilas() {
        if (columnas.size() > 0) {
            Etiqueta clave = etiquetasColumnas.get(0);
            return columnas.get(clave).contarCeldas();
        }
        return 0;
    }

    // Acceso a celdas individuales

    // Devuelve una celda específica a partir de su etiqueta de columna y fila.
    // Valida la existencia de ambas.

    public Celda<?> getCelda(Etiqueta etiquetaColumna, Etiqueta etiquetaFila) {
        if (!(etiquetasFilas.contains(etiquetaFila) &&
                etiquetasColumnas.contains(etiquetaColumna))) {
            if (!etiquetasFilas.contains(etiquetaFila)) {
                throw new IllegalArgumentException("Etiqueta de fila inválida: " +
                        etiquetaFila);
            } else {
                throw new IllegalArgumentException("Etiqueta de columna inválida: " +
                        etiquetaColumna);
            }
        }

        int indiceFila = etiquetasFilas.indexOf(etiquetaFila);
        Columna columna = columnas.get(etiquetaColumna);
        return columna.getCelda(indiceFila);
    }

    /// --- Herramientas que puede utilizar 'Tabla' una vez instanciada ---

    /// ------------- Modulo 'Visualizador' ---------------
    public void visualizarTabla() {
        Visualizador.imprimirTabla(this);
    }

    public void infoTabla() {
        Visualizador.info(this);
    }

    /// ---------- Modulo 'Copia y Concatenación' -----------
    public Tabla copiarTabla() {
        return CopiaTabla.copiarTabla(this);
    }

    public Tabla concatenerTablas(Tabla tablaExterior) {
        return ConcatenacionTabla.concatenarTablas(this, tablaExterior);
    }

    /// --------- Modulo 'Manipulación' ----------------------

    public void imputarDato(Object valorImputacion) {
        ImputadorDatos imputar = new ImputadorDatos(valorImputacion);
        imputar.manipular(this);
    }

    public void tomarMuestra(double porcentaje) {
        MuestreadorDatos muestra = new MuestreadorDatos(porcentaje);
        muestra.manipular(this);
    }

    public void ordenarDatos(List<String> criterioOrden) {
        OrdenadorDatos orden = new OrdenadorDatos(criterioOrden);
        orden.manipular(this);
    }

    ///  --------- Modulo 'Selección' ------------------------------

    public void seleccionarFilasColumnas(List<Etiqueta> etiquetasFilasSeleccionadas, List<Etiqueta> etiquetasColumnasSeleccionadas) {
        Seleccion selec = new Seleccion(this);
        selec.seleccionar(etiquetasFilasSeleccionadas, etiquetasColumnasSeleccionadas);
    }
    public void head(int cantFilas){
        Seleccion selec = new Seleccion(this);
        selec.head(cantFilas);
    }
    public void tail(int cantFilas){
        Seleccion selec = new Seleccion(this);
        selec.tail(cantFilas);
    }

    /// ----------- Modulo 'Filtros' --------------------------

    public void filtroSimple(Etiqueta etiquetaColumna, TipoComparador tipoComparador, Object valorAComparar){
        ControladorFiltro controlador = new ControladorFiltro(this);
        controlador.filtroSimple(etiquetaColumna, tipoComparador, valorAComparar);
    }
    public void filtroCompuesto(OperadorLogico operadorLogico,
                                Etiqueta etiquetaColumna1, TipoComparador tipoComparador1, Object valor1,
                                Etiqueta etiquetaColumna2, TipoComparador tipoComparador2, Object valor2){

        ControladorFiltro controlador = new ControladorFiltro(this);
        controlador.filtroCompuesto(operadorLogico,etiquetaColumna1,tipoComparador1,valor1,
                etiquetaColumna2,tipoComparador2,valor2);
    }

    public void negarFiltro(Etiqueta etiquetaColumna, TipoComparador tipoComparador, Object valor){
        ControladorFiltro controlador = new ControladorFiltro(this);
        controlador.filtrarNOT(etiquetaColumna,tipoComparador,valor);
    }

    public void filtroSinParametro(TipoSinParametro tipo, Etiqueta etiquetaColumna){
        ControladorFiltro controlador = new ControladorFiltro(this);
        controlador.filtrarSinParametros(tipo,etiquetaColumna);
    }

    public List<List<Celda<?>>> getFilas() {
        List<List<Celda<?>>> filas = new ArrayList<>();
        for (int i = 0; i < contarFilas(); i++) {
            filas.add(getFila(i));
        }
        return filas;
    }

}
