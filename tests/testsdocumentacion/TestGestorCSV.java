
package testsdocumentacion;

import gestiondedatos.*;
import entradaysalida.*;

public class TestGestorCSV {
    public static void main(String[] args) throws Exception {
        GestorCSV gestor = new GestorCSV();
        gestor.setDelimitador(","); //delimitador por defecto
        gestor.setUsarEncabezado(true); //Leer/guardar encabezados

        //cargamos una tabla desde archivo CSV
        Tabla tabla = gestor.cargarCSV("test_datos.csv");

        //visualizamos la tabla cargada
        tabla.visualizarTabla();

        //guardamos la tabla (o una copia) en un nuevo archivo
        gestor.guardarCSV("salida_ordenada.csv", tabla);
    }
}
