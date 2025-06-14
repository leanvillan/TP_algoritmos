package gestiondedatos;
import visualizador.Visualizador;
import java.util.Arrays;
import java.util.List;
import copiayconcatenacion.CopiaTabla;
import gestiondeerrores.ExcepcionTabular;
import gestiondeerrores.GestionErrores;

public class Main {
    public static void main(String[] args) {
        try {

            // --- PRUEBA 1: Tabla Completa y Heterogénea ---
            System.out.println("--- TABLA DE EJEMPLO COMPLETA (MAX_ANCHO_CELDA y TRUNCATE_SUFFIX) ---");

            // 1. Crear columnas vacías con sus tipos y etiquetas
            Columna colNumerica = new Columna(TipoDato.NUMERICO);
            Columna colCadena = new Columna(TipoDato.CADENA);
            Columna colBooleana = new Columna(TipoDato.BOOLEANO);
            Columna colLarga = new Columna(TipoDato.CADENA); // Columna extra para probar truncado horizontal

            // 2. Crear etiquetas de columna
            EtiquetaString etColNum = new EtiquetaString("Edad");
            EtiquetaString etColStr = new EtiquetaString("Nombre Completo Largo Que Se Trunca");
            EtiquetaString etColBool = new EtiquetaString("Activo");
            EtiquetaString etColLarga = new EtiquetaString("Descripción Muy Detallada Del Producto Que Excede el Ancho");

            // 3. Crear la tabla y agregar las columnas
            Tabla tablaOriginal = new Tabla(); // Cambié el nombre a tablaOriginal
            tablaOriginal.agregarColumna(etColNum, colNumerica);
            tablaOriginal.agregarColumna(etColStr, colCadena);
            tablaOriginal.agregarColumna(etColBool, colBooleana);
            tablaOriginal.agregarColumna(etColLarga, colLarga);


            // 4. Agregar filas con datos de diferentes longitudes y NAs
            // Fila 0
            List<Celda<?>> fila0 = Arrays.asList(
                    new Celda<>(25, TipoDato.NUMERICO),
                    new Celda<>("Juan Pérez", TipoDato.CADENA),
                    new Celda<>(true, TipoDato.BOOLEANO),
                    new Celda<>("Caja de 12 unidades de galletas de avena con chocolate oscuro", TipoDato.CADENA)
            );
            tablaOriginal.agregarFila(new EtiquetaString("A1"), fila0); // Etiqueta de fila String

            // Fila 1
            List<Celda<?>> fila1 = Arrays.asList(
                    new Celda<>(30.5, TipoDato.NUMERICO),
                    new Celda<>("María Laura Rodríguez Pérez de la Cienaga Larga", TipoDato.CADENA), // Valor de cadena más largo
                    new Celda<>(false, TipoDato.BOOLEANO),
                    new Celda<>("Botella de agua mineral de 2 litros con gas y sabor a limón", TipoDato.CADENA)
            );
            tablaOriginal.agregarFila(new EtiquetaEntero(2), fila1); // Etiqueta de fila Integer

            // Fila 2 (con un NA)
            List<Celda<?>> fila2 = Arrays.asList(
                    new Celda<>(), // NA
                    new Celda<>("Carlos", TipoDato.CADENA),
                    new Celda<>(true, TipoDato.BOOLEANO),
                    new Celda<>("Bolsa de papas fritas sabor queso y cebolla de 200g", TipoDato.CADENA)
            );
            tablaOriginal.agregarFila(new EtiquetaString("Registro_3"), fila2);

            // Fila 3 (con varios NAs)
            List<Celda<?>> fila3 = Arrays.asList(
                    new Celda<>(42, TipoDato.NUMERICO),
                    new Celda<>(), // NA
                    new Celda<>(), // NA
                    new Celda<>("Pack de 6 yogures descremados frutilla", TipoDato.CADENA)
            );
            tablaOriginal.agregarFila(fila3); // Etiqueta de fila auto-generada (Integer)

            // Fila 4 (valores cortos)
            List<Celda<?>> fila4 = Arrays.asList(
                    new Celda<>(7, TipoDato.NUMERICO),
                    new Celda<>("Sol", TipoDato.CADENA),
                    new Celda<>(false, TipoDato.BOOLEANO),
                    new Celda<>("Leche", TipoDato.CADENA)
            );
            tablaOriginal.agregarFila(fila4);

            //        System.out.println("\n--- TABLA ORIGINAL ANTES DE LA COPIA ---");
            //        Visualizador.imprimirTabla(tablaOriginal);
            //
            //        // --- PRUEBA DE COPIA PROFUNDA ---
            //        System.out.println("\n--- INICIO DE PRUEBA DE COPIA PROFUNDA ---");
            //
            //        // Realizar la copia profunda
            //        Tabla tablaCopia = CopiaTabla.copiarTabla(tablaOriginal);
            //        System.out.println("Tabla original y copia creadas.");
            //
            //        System.out.println("\n--- TABLA ORIGINAL DESPUÉS DE LA COPIA (DEBE SER IDÉNTICA A LA ANTERIOR) ---");
            //        Visualizador.imprimirTabla(tablaOriginal);
            //
            //        System.out.println("\n--- TABLA COPIA DESPUÉS DE LA COPIA (DEBE SER IDÉNTICA A LA ORIGINAL) ---");
            //        Visualizador.imprimirTabla(tablaCopia);


            ///  info  de la tabla

            Visualizador.info(tablaOriginal);
        } catch (ExcepcionTabular e) {
            GestionErrores.logError(e);
        } catch (Exception e) {
            GestionErrores.logError("Error inesperado: " + e.getMessage());
        }


    }
}