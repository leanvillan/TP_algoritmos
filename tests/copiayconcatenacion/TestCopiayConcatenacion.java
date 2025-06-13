package copiayconcatenacion;

import gestiondedatos.*;

public class TestCopiayConcatenacion {
    public static void main(String[] args) {
        System.out.println("---- Prueba de Copia y Concatenación de Tablas ----");

        //creamos una tabla original
        Tabla tabla1 = new Tabla();
        EtiquetaString colNombre = new EtiquetaString("Nombre");
        EtiquetaString colEdad = new EtiquetaString("Edad");
        Columna columnaNombre = new Columna(TipoDato.CADENA);
        Columna columnaEdad = new Columna(TipoDato.NUMERICO);

        columnaNombre.agregarCelda(new Celda<>("Mica", TipoDato.CADENA));
        columnaNombre.agregarCelda(new Celda<>("Lean", TipoDato.CADENA));
        columnaEdad.agregarCelda(new Celda<>(30, TipoDato.NUMERICO));
        columnaEdad.agregarCelda(new Celda<>(34, TipoDato.NUMERICO));
        tabla1.agregarColumna(colNombre, columnaNombre);
        tabla1.agregarColumna(colEdad, columnaEdad);
        tabla1.generarEtiquetasFilas();

        //copia profunda
        CopiaTabla copiador = new CopiaTabla();
        Tabla tablaCopia = copiador.copiarTabla(tabla1);

        System.out.println("Tabla original:");
        for (Etiqueta et : tabla1.getEtiquetasFilas()) {
            System.out.println("- " + tabla1.getCelda(colNombre, et).getValor() + ", " + tabla1.getCelda(colEdad, et).getValor());
        }
        System.out.println("Tabla copia:");
        for (Etiqueta et : tablaCopia.getEtiquetasFilas()) {
            System.out.println("- " + tablaCopia.getCelda(colNombre, et).getValor() + ", " + tablaCopia.getCelda(colEdad, et).getValor());
        }

        //modificamos la copia reemplazando una celda
        Columna colNombreCopia = tablaCopia.getColumna(colNombre);
        colNombreCopia.getCeldas().set(0, new Celda<>("No es Mica", TipoDato.CADENA));

        System.out.println("Después de modificar la copia...");
        System.out.println("Copia primer valor: " + tablaCopia.getCelda(colNombre, tablaCopia.getEtiquetasFilas().get(0)).getValor());
        System.out.println("Original primer valor: " + tabla1.getCelda(colNombre, tabla1.getEtiquetasFilas().get(0)).getValor());

        //creamos otra tabla con la misma estructura para concatenar
        Tabla tabla2 = new Tabla();
        Columna columnaNombre2 = new Columna(TipoDato.CADENA);
        Columna columnaEdad2 = new Columna(TipoDato.NUMERICO);
        columnaNombre2.agregarCelda(new Celda<>("Ludmi", TipoDato.CADENA));
        columnaEdad2.agregarCelda(new Celda<>(22, TipoDato.NUMERICO));
        tabla2.agregarColumna(colNombre, columnaNombre2);
        tabla2.agregarColumna(colEdad, columnaEdad2);
        tabla2.generarEtiquetasFilas();

        //concatenación
        ConcatenacionTabla concatenador = new ConcatenacionTabla();
        Tabla tablaConcatenada = concatenador.concatenarTablas(tabla1, tabla2);

        System.out.println("\nTabla concatenada:");
        for (Etiqueta et : tablaConcatenada.getEtiquetasFilas()) {
            System.out.println("- " + tablaConcatenada.getCelda(colNombre, et).getValor() + ", " + tablaConcatenada.getCelda(colEdad, et).getValor());
        }
    }
}
