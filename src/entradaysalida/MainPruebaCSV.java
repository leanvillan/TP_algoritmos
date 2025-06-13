package entradaysalida;

import gestiondedatos.*;
import visualizador.Visualizador;
import gestiondeerrores.ExcepcionTabular;
import gestiondeerrores.GestionErrores;


/// /home/tareas/Documents/ModuloEntradaySalida/completo.csv
public class MainPruebaCSV {
    public static void main(String[] args) {
        try {
            GestorCSV gestor = new GestorCSV();
            gestor.setUsarEncabezado(true);

            Tabla tabla = gestor.cargarCSV("/home/tareas/Documents/ModuloEntradaySalida/completo.csv");     // Poner un csv sin valores faltantes

            System.out.println("COLUMNAS:");
            for (Etiqueta<?> etiqueta : tabla.getEtiquetasColumnas()) {
                Columna columna = tabla.getColumna(etiqueta);
                System.out.println("- " + etiqueta + " (tipo: " + columna.getTipoDato() + ")");
            }

            System.out.println("\nTABLA:");
            Visualizador.imprimirTabla(tabla);

        } catch (ExcepcionTabular e) {
            GestionErrores.logError(e);
        } catch (Exception e) {
            GestionErrores.logError("Error inesperado: " + e.getMessage());
        }
    }
}
