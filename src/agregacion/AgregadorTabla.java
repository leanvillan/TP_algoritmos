package agregacion;

import gestiondedatos.*;

import java.util.List;
import java.util.Map;

public class AgregadorTabla {

    public TablaAgrupada groupBy(Tabla tablaOriginal, List<Etiqueta> etiquetasGrupo) {

        // Agrupa las filas de una tabla basándose en los valores de las columnas especificadas.
        // Este metodo solo realiza la agrupación, no la sumarización.

        // Validaciones iniciales para la agrupación
        if (tablaOriginal == null) {
            throw new IllegalArgumentException("La tabla original no puede ser nula.");
        }
        if (etiquetasGrupo == null || etiquetasGrupo.isEmpty()) {
            throw new IllegalArgumentException("Las etiquetas de agrupación no pueden ser nulas o vacías.");
        }

        // Crear y devolver los grupos
        Map<List<Object>, List<List<Celda<?>>>> grupos = crearGrupos(tablaOriginal, etiquetasGrupo);

        return new TablaAgrupada(grupos, etiquetasGrupo, tablaOriginal);
    }

    public Tabla summarize(TablaAgrupada tablaAgrupada,
                           List<Etiqueta> etiquetasColumnasASumarizar,
                           TipoOperacion tipoOperacion) {

        // Validaciones iniciales para la sumarización
        if (tablaAgrupada == null) {
            throw new IllegalArgumentException("La Tabla Agrupada no puede ser nula.");
        }
        if (etiquetasColumnasASumarizar == null || etiquetasColumnasASumarizar.isEmpty()) {
            throw new IllegalArgumentException("Las columnas a sumarizar no pueden ser nulas o vacías.");
        }
        if (tipoOperacion == null) {
            throw new IllegalArgumentException("El tipo de operación no puede ser nulo.");
        }

        // Obtener la tabla original y las columnas de agrupación desde el objeto TablaAgrupada
        Tabla tablaOriginal = tablaAgrupada.getTablaOriginal();
        List<Etiqueta> etiquetasGrupo = tablaAgrupada.getEtiquetasColumnasGrupo();
        Map<List<Object>, List<List<Celda<?>>>> grupos = tablaAgrupada.getGrupos();

        // Verificar que no haya solapamiento entre columnas de agrupación y de resumen
        for (Etiqueta etiqGrupo : etiquetasGrupo) {
            if (etiquetasColumnasASumarizar.contains(etiqGrupo)) {
                throw new IllegalArgumentException("La columna '" + etiqGrupo.getValor() +
                        "' no puede ser de agrupación y de resumen al mismo tiempo.");
            }
        }

        // Validar y preparar columnas para sumarización
        for (Etiqueta etiqSumarizar : etiquetasColumnasASumarizar) {
            if (!tablaOriginal.getEtiquetasColumnas().contains(etiqSumarizar)) {
                throw new IllegalArgumentException("La columna '" + etiqSumarizar.getValor() + "' no existe en la tabla.");
            }
            if (tablaOriginal.getTipoDatoColumna(etiqSumarizar) != TipoDato.NUMERICO) {
                throw new IllegalArgumentException("La columna '" + etiqSumarizar.getValor() + "' no es de tipo NUMÉRICO.");
            }
        }

        // Construir y devolver la tabla agregada
        return construirTablaAgregada(grupos, tablaOriginal, etiquetasColumnasASumarizar, tipoOperacion);
    }

    // Metodo para agrupar filas
    private Map<List<Object>, List<List<Celda<?>>>> crearGrupos(
            Tabla tablaOriginal, List<Etiqueta> etiquetasGrupo) {
        Map<List<Object>, List<List<Celda<?>>>> grupos = new java.util.HashMap<>();

        // Recorrer todas las filas de la tabla original
        for (List<Celda<?>> fila : tablaOriginal.getFilas()) {
            // Crear la clave del grupo con los valores de las columnas de agrupación
            List<Object> claveGrupo = new java.util.ArrayList<>();
            for (Etiqueta etiqueta : etiquetasGrupo) {
                int idx = tablaOriginal.getEtiquetasColumnas().indexOf(etiqueta);
                claveGrupo.add(fila.get(idx).getValor());
            }
            // Agregar la fila al grupo correspondiente
            grupos.computeIfAbsent(claveGrupo, k -> new java.util.ArrayList<>()).add(fila);
        }
        return grupos;
    }

    private Tabla construirTablaAgregada(
            Map<List<Object>, List<List<Celda<?>>>> grupos,
            Tabla tablaOriginal,
            List<Etiqueta> etiquetasColumnasASumarizar,
            TipoOperacion tipoOperacion) {

        // Construir la lista de columnas finales: primero las de agrupación, luego las de resumen
        List<Etiqueta> columnasFinales = new java.util.ArrayList<>();
        int cantidadColumnasGrupo = grupos.keySet().isEmpty() ? 0 : grupos.keySet().iterator().next().size();
        for (int i = 0; i < cantidadColumnasGrupo; i++) {
            columnasFinales.add(tablaOriginal.getEtiquetasColumnas().get(i));
        }
        columnasFinales.addAll(etiquetasColumnasASumarizar);

        // Construir la lista de tipos de dato correspondientes
        List<TipoDato> tiposFinales = new java.util.ArrayList<>();
        for (int i = 0; i < cantidadColumnasGrupo; i++) {
            tiposFinales.add(tablaOriginal.getTipoDatoColumna(columnasFinales.get(i)));
        }
        for (Etiqueta et : etiquetasColumnasASumarizar) {
            tiposFinales.add(tablaOriginal.getTipoDatoColumna(et));
        }

        // Crear tabla con las columnas correctamente tipadas
        Tabla tablaResumida = new Tabla();
        for (int i = 0; i < columnasFinales.size(); i++) {
            Etiqueta etiqueta = columnasFinales.get(i);
            TipoDato tipoDato = tablaOriginal.getTipoDatoColumna(etiqueta);
            tablaResumida.agregarColumna(etiqueta, new Columna(tipoDato));
        }

        // Obtener la estrategia de sumarización
        FabricaOperacionesSumarizacion fabrica = new FabricaOperacionesSumarizacion();
        Sumarizador sumarizador = fabrica.obtenerEstrategia(tipoOperacion);

        // Construir una fila resumen para cada grupo
        for (Map.Entry<List<Object>, List<List<Celda<?>>>> grupo : grupos.entrySet()) {
            List<Object> valoresGrupo = grupo.getKey();
            List<List<Celda<?>>> filasDelGrupo = grupo.getValue();

            List<Celda<?>> filaResumen = new java.util.ArrayList<>();

            // Agregar valores de agrupación
            for (int i = 0; i < valoresGrupo.size(); i++) {
                Object valor = valoresGrupo.get(i);
                Etiqueta etiqueta = columnasFinales.get(i);
                filaResumen.add(new Celda<>(valor, tablaOriginal.getTipoDatoColumna(etiqueta)));
            }

            // Aplicar operación de resumen por cada columna seleccionada
            for (Etiqueta columnaSumarizar : etiquetasColumnasASumarizar) {
                int idx = tablaOriginal.getEtiquetasColumnas().indexOf(columnaSumarizar);

                List<Celda<?>> celdasColumna = new java.util.ArrayList<>();
                for (List<Celda<?>> fila : filasDelGrupo) {
                    celdasColumna.add(fila.get(idx));
                }

                filaResumen.add(sumarizador.sumarizar(celdasColumna));
            }

            tablaResumida.agregarFila(filaResumen);
        }
        return tablaResumida;
    }
}
