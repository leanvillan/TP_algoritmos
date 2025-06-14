package entradaysalida;

import gestiondedatos.*;
import java.io.*;
import java.util.*;
import gestiondeerrores.*;

/**
 * La clase 'GestorCSV' permite cargar y guardar archivos CSV en una estructura de tabla.
 * 
 * Al cargar un archivo, infiere automáticamente el tipo de dato de cada columna (numérico, booleano o texto) 
 * y contruye una instancia de 'Tabla'.
 * También maneja celdas vacías como valores faltantes (NA) y lanza advertencias si se encuentran valores 
 * inconsistentes con el tipo inferido.
 * 
 * Al guardar la tabla en formato CSV, se puede incluir o no una fila de encabezado, 
 * y se utiliza un delimitador configurable (por defecto, una coma).
 * 
 * Esta clase es utilizada para la entrada y salida de datos tabulares en el sistema.
 */

public class GestorCSV {

    private String delimitador = ",";
    private boolean usarEncabezado = true;


    // Constructor por defecto
    public GestorCSV() {
    }

    public void setDelimitador(String delimitador) {
        this.delimitador = delimitador;
    }

    public void setUsarEncabezado(boolean usarEncabezado) {
        this.usarEncabezado = usarEncabezado;
    }

    
    // Carga un archivo CSV y lo convierte en una Tabla
    public Tabla cargarCSV(String rutaArchivo) throws Exception {

        Tabla tabla = new Tabla(); 

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esPrimeraLinea = true; 
            List<Etiqueta> etiquetas = new ArrayList<>();
            List<List<String>> valoresPorColumna = new ArrayList<>();

            while ((linea = br.readLine()) != null) {

                String[] valores = linea.split(delimitador);
                if (esPrimeraLinea) {
                    for (int i = 0; i < valores.length; i++) {
                        Etiqueta etiqueta;
                        if (usarEncabezado) {
                            etiqueta = new EtiquetaString(valores[i].trim());
                        } else {
                            etiqueta = new EtiquetaEntero(i);
                        }
                        etiquetas.add(etiqueta);
                        valoresPorColumna.add(new ArrayList<>());
                    }

                    if (!usarEncabezado) {
                        for (int i = 0; i < valores.length; i++) {
                            valoresPorColumna.get(i).add(valores[i].trim());
                        }
                    }

                    esPrimeraLinea = false;

                } else {
                    for (int i = 0; i < etiquetas.size(); i++) {
                        String valor = i < valores.length ? valores[i].trim() : ""; // Si hay menos valores que columnas, usamos un string vacío
                        valoresPorColumna.get(i).add(valor);
                    }
                }
            }

            // Inferencia de tipos y creación de columnas
            for (int i = 0; i < etiquetas.size(); i++) {
                TipoDato tipo = inferirTipo(valoresPorColumna.get(i));
                Columna columna = new Columna(tipo);
                tabla.agregarColumna(etiquetas.get(i), columna);
            }

            // Carga filas
            int cantidadFilas = valoresPorColumna.get(0).size();  
            for (int fila = 0; fila < cantidadFilas; fila++) {
                List<Celda<?>> filaCeldas = new ArrayList<>(); 

                for (int col = 0; col < etiquetas.size(); col++) { 
                    String valor = valoresPorColumna.get(col).get(fila).trim();
                    TipoDato tipo = tabla.getColumna(etiquetas.get(col)).getTipoDato();

                    if (valor.isEmpty()) {
                        filaCeldas.add(new Celda<>());

                    } else if (tipo == TipoDato.NUMERICO) {
                        try {
                            filaCeldas.add(new Celda<>(Double.parseDouble(valor), tipo));
                        } catch (NumberFormatException e) {
                            filaCeldas.add(new Celda<>());
                            GestionErrores.logAdvertencia("Valor no numérico en columna '" + etiquetas.get(col) + "': " + valor);
                        }

                    } else if (tipo == TipoDato.BOOLEANO) {
                        if (valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false")) {
                            filaCeldas.add(new Celda<>(Boolean.parseBoolean(valor.toLowerCase()), tipo));
                        } else {
                            filaCeldas.add(new Celda<>());
                            GestionErrores.logAdvertencia("Valor no booleano en columna '" + etiquetas.get(col) + "': " + valor);
                        }

                    } else {
                        if (esNumerico(valor) || esBooleano(valor)) {
                            GestionErrores.logAdvertencia("Valor numérico o booleano en columna de texto '" + etiquetas.get(col) + "': " + valor);
                        }
                        filaCeldas.add(new Celda<>(valor, tipo));
                    }
                }
                tabla.agregarFila(filaCeldas);
                
            }

        } catch (IOException e) {
            throw new gestiondeerrores.ExcepcionCargaDatos("Error al leer el archivo CSV: " + e.getMessage());
        }

        return tabla;
    }

    private boolean esNumerico(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esBooleano(String valor) {
        return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false");
    }

    // Determina el tipo de dato más adecuado para una lista de valores
    private TipoDato inferirTipo(List<String> valores) {
        boolean todosNumericos = true;
        boolean todosBooleanos = true;

        for (String v : valores) {
            if (v.isEmpty()) continue;
            if (!esNumerico(v)) todosNumericos = false;
            if (!esBooleano(v)) todosBooleanos = false;
        }

        if (todosNumericos) return TipoDato.NUMERICO;
        if (todosBooleanos) return TipoDato.BOOLEANO;
        return TipoDato.CADENA;
    }

    // Guarda una Tabla como archivo CSV
    public void guardarCSV(String rutaArchivo, Tabla tabla) throws Exception {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) { 
            if (usarEncabezado) {  
                List<Etiqueta> etiquetas = tabla.getEtiquetasColumnas();   

                for (int i = 0; i < etiquetas.size(); i++) {  
                    String nombre = etiquetas.get(i).toString();    
                    bw.write(nombre);   
                    if (i < etiquetas.size() - 1) {
                        bw.write(delimitador);  
                    }
                }
                bw.newLine();  
            }

            // Escribimos las filas de la tabla
            int cantidadFilas = tabla.contarFilas();   
            List<Etiqueta> etiquetas = tabla.getEtiquetasColumnas();  

            for (int fila = 0; fila < cantidadFilas; fila++) {    

                for (int col = 0; col < etiquetas.size(); col++) {   
                    Columna columna = tabla.getColumna(etiquetas.get(col));   
                    Celda<?> celda = columna.getCelda(fila);              
                    String valor = celda.isNA() ? "" : celda.toString();  
                    bw.write(valor);  
                    if (col < etiquetas.size() - 1) {
                        bw.write(delimitador); 
                    }
                }
                bw.newLine();
            }

        } catch (IOException e) {
            throw new gestiondeerrores.ExcepcionCargaDatos("Error al guardar el archivo CSV: " + e.getMessage());
        }
    }
}
