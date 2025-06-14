package entradaysalida;

import gestiondedatos.*;
import herramientas.MedidorEficiencia;

public class TestGestorCSV {
    public static void main(String[] args) {
        System.out.println("---- Prueba de GestorCSV ----");

        GestorCSV gestor = new GestorCSV();

        String rutaEntrada = "test_datos.csv";   //usamos un archivo CSV de prueba en el directorio raiz pero fuera de tests y src
        String rutaSalida = "test_salida.csv";

        try {
            //Leemos el archivo CSV
            MedidorEficiencia.medirTiempo("lectura CSV desde " + rutaEntrada, () -> {
                try {
                    Tabla tabla = gestor.cargarCSV(rutaEntrada);
                    System.out.println("Tabla leída desde CSV:");
                    imprimirTabla(tabla);

                    //guardamos la tabla en otro archivo CSV a la par de test_datos.csv
                    MedidorEficiencia.medirTiempo("guardado CSV en " + rutaSalida, () -> {
                        try {
                            gestor.guardarCSV(rutaSalida, tabla);
                            System.out.println("Tabla guardada en: " + rutaSalida);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    //leemos de nuevo el archivo guardado
                    MedidorEficiencia.medirTiempo("lectura CSV desde archivo guardado", () -> {
                        try {
                            Tabla tabla2 = gestor.cargarCSV(rutaSalida);
                            System.out.println("Tabla leída desde el archivo guardado:");
                            imprimirTabla(tabla2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //mostramos por pantalla la tabla en formato simple sin el uso de visualizador, porque esto es solo para probar el paquete actual
    private static void imprimirTabla(Tabla tabla) {
        //imprimimos el encabezado
        for (Etiqueta et : tabla.getEtiquetasColumnas()) {
            System.out.print(et + "\t");
        }
        System.out.println();
        //imprimimos las filas
        for (Etiqueta fila : tabla.getEtiquetasFilas()) {
            for (Etiqueta col : tabla.getEtiquetasColumnas()) {
                System.out.print(tabla.getCelda(col, fila) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
