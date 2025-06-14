package demo;

import java.util.ArrayList;
import java.util.List;

import agregacion.OperacionMaximo;
import agregacion.OperacionMinimo;
import copiayconcatenacion.ConcatenacionTabla;
import copiayconcatenacion.CopiaTabla;
import entradaysalida.GestorCSV;
import gestiondedatos.Celda;
import gestiondedatos.Etiqueta;
import gestiondedatos.EtiquetaString;
import gestiondedatos.Tabla;
import manipulacion.OrdenadorDatos;
import visualizador.Visualizador;
import filtros.*;
import manipulacion.*;

public class MainDemo {

    public static void main(String[] args) {
        try {

            // Comienza a medir la eficiencia
            long inicio = System.currentTimeMillis();


            // 1. CARGA DEL CSV
            System.out.println("Cargando archivo CSV...");
            GestorCSV gestor = new GestorCSV();
            gestor.setUsarEncabezado(true);
            gestor.setDelimitador(",");
            Tabla tabla = gestor.cargarCSV("test_datos.csv");
            System.out.println("Archivo cargado exitosamente.\n");

            // 2. VISUALIZACIÓN DE LA TABLA
            System.out.println("Visualizando la tabla...");
            Visualizador.imprimirTabla(tabla);
            System.out.println("Visualización completa.\n");

            // 3. FILTRADO DE LA TABLA (Edad > 25)
            System.out.println("Filtrando las filas donde Edad > 25...");
            Etiqueta etiquetaEdad = new EtiquetaString("Edad");
            Filtro filtroEdad = new Mayor((Object) 25.0); // solo se compara el valor
            Filtrado filtrador = new Filtrado(tabla);
            Tabla tablaFiltrada = filtrador.filtrarComparador(etiquetaEdad, filtroEdad);
            System.out.println("Resultado del filtrado:");
            Visualizador.imprimirTabla(tablaFiltrada);
            System.out.println("Filtrado completo.\n");


            // 4. ORDENAMIENTO POR EDAD ASCENDENTE (sobre copia)
            System.out.println("Ordenando la tabla por Edad (ascendente)...");
            Tabla copiaOrdenada = CopiaTabla.copiarTabla(tabla);  // Hacemos una copia de la tabla original
            OrdenadorDatos ordenador = new OrdenadorDatos(List.of("Edad")); // Criterio fijo
            ordenador.manipular(copiaOrdenada); // Ordena in-place
            System.out.println("Resultado del ordenamiento:");
            Visualizador.imprimirTabla(copiaOrdenada);
            System.out.println("Ordenamiento completo.\n");

            // 5. AGREGACIÓN: Mínimo y Máximo de Edad
            System.out.println("Calculando el valor mínimo y máximo de la columna Edad...");

            List<Celda<?>> celdasEdad = tabla.getColumna(new EtiquetaString("Edad")).getCeldas();

            OperacionMinimo opMin = new OperacionMinimo();
            Object minimo = opMin.sumarizar(celdasEdad).getValor();
            System.out.println("Mínimo de Edad: " + minimo);

            OperacionMaximo opMax = new OperacionMaximo();
            Object maximo = opMax.sumarizar(celdasEdad).getValor();
            System.out.println("Máximo de Edad: " + maximo);

            System.out.println("Agregación completa.\n");

            
            // 6. CONCATENACIÓN DE TABLAS
            System.out.println("Concatenando la tabla original con una tabla extra...");

            String rutaExtra = "test_datos_extra.csv";


            // No se porque no concatena bien los 2 csv

            // // CARGAR Y CONCATENAR
            // GestorCSV gestorExtra = new GestorCSV();
            // gestorExtra.setUsarEncabezado(true);
            // gestorExtra.setDelimitador(",");
            // Tabla tablaExtra = gestorExtra.cargarCSV(rutaExtra);

            // Tabla tablaConcatenada = ConcatenacionTabla.concatenarTablas(tabla, tablaExtra);
            // System.out.println("Resultado de la concatenación:");
            // Visualizador.imprimirTabla(tablaConcatenada);
            // System.out.println("Concatenación completa.\n");


            // 7. MEDICIÓN DE EFICIENCIA GENERAL
            long fin = System.currentTimeMillis();
            System.out.println("Tiempo total de ejecución: " + (fin - inicio) + " ms\n");

            // 8. GUARDADO DE LA TABLA ORDENADA ---> Lo ideal sería guardar la tabla concatenada (cuando se solucione el problema)
            System.out.println("Guardando la tabla ordenada como 'salida_ordenada.csv'...");
            GestorCSV gestorSalida = new GestorCSV();
            gestorSalida.setUsarEncabezado(true);
            gestorSalida.setDelimitador(",");
            gestorSalida.guardarCSV("salida_ordenada.csv", copiaOrdenada);
            System.out.println("Tabla guardada exitosamente.\n");

        } catch (Exception e) {
            System.err.println("Error en la ejecución del programa: " + e.getMessage());
        }
    }
}



