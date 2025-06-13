package agregacion;

import gestiondedatos.*;

import java.util.List;
import java.util.Map;

public class AgregadorTabla {

    public TablaAgrupada groupBy(Tabla tablaOriginal, List<Etiqueta> etiquetasGrupo) {

        // Agrupa las filas de una tabla basándose en los valores de las columnas especificadas.
        // Este metodo solo realiza la agrupación, no la sumarización.

        // Validaciones Iniciales para Agrupación
        if (tablaOriginal == null) {
            throw new IllegalArgumentException("La tabla original no puede ser nula.");
        }
        if (etiquetasGrupo == null || etiquetasGrupo.isEmpty()) {
            throw new IllegalArgumentException("Las etiquetas de agrupación no pueden ser " +
                    "nulas o vacías.");
        }

        // Crear y Devolver los Grupos
        Map<List<Object>, List<List<Celda<?>>>> grupos = crearGrupos(tablaOriginal, etiquetasGrupo);

        return new TablaAgrupada(grupos, etiquetasGrupo, tablaOriginal);
    }

    public Tabla summarize(TablaAgrupada tablaAgrupada,
                           List<Etiqueta> etiquetasColumnasASumarizar,
                           TipoOperacion tipoOperacion) {

        // Validaciones Iniciales para Sumarización
        if (tablaAgrupada == null) {
            throw new IllegalArgumentException("La Tabla Agrupada no puede ser nula.");
        }
        if (etiquetasColumnasASumarizar == null || etiquetasColumnasASumarizar.isEmpty()) {
            throw new IllegalArgumentException("Las etiquetas de columnas a sumarizar" +
                    " no pueden ser nulas o vacías.");
        }
        if (tipoOperacion == null) {
            throw new IllegalArgumentException("El tipo de operación no puede ser nulo.");
        }

        // Obtener la tabla original y las etiquetas de grupo del objeto TablaAgrupada
        Tabla tablaOriginal = tablaAgrupada.getTablaOriginal();
        List<Etiqueta> etiquetasGrupo = tablaAgrupada.getEtiquetasColumnasGrupo();
        Map<List<Object>, List<List<Celda<?>>>> grupos = tablaAgrupada.getGrupos();

        // Verificar que no haya solapamiento entre columnas de agrupación y de sumarización
        for (Etiqueta etiqGrupo : etiquetasGrupo) {
            if (etiquetasColumnasASumarizar.contains(etiqGrupo)) {
                throw new IllegalArgumentException("La columna '" + etiqGrupo.getValor() +
                        "' no puede ser tanto de agrupación como de sumarización al mismo tiempo.");
            }
        }

        // Validar y Preparar Columnas para Sumarización
        for (Etiqueta etiqSumarizar : etiquetasColumnasASumarizar) {
            if (!tablaOriginal.getEtiquetasColumnas().contains(etiqSumarizar)) {
                throw new IllegalArgumentException("La columna a sumarizar '" +
                        etiqSumarizar.getValor() + "' no existe en la tabla original.");
            }
            if (tablaOriginal.getTipoDatoColumna(etiqSumarizar) != TipoDato.NUMERICO) {
                throw new IllegalArgumentException("La columna a sumarizar '"
                        + etiqSumarizar.getValor() + "' no es de tipo NUMERICO.");
            }
        }

        // Construir la Tabla Agregada
        return construirTablaAgregada(grupos, tablaOriginal, etiquetasColumnasASumarizar,
                tipoOperacion);
    }

    // con este método agrupo filas
    private Map<List<Object>, List<List<Celda<?>>>> crearGrupos(
            Tabla tablaOriginal, List<Etiqueta> etiquetasGrupo) {
        Map<List<Object>, List<List<Celda<?>>>> grupos = new java.util.HashMap<>();

        //acá hacemos un recoorrido de todas las filas de la tabla original
        for (List<Celda<?>> fila : tablaOriginal.getFilas()) {
            //armamos la clave del grupo: los valores de las columnas de agrupación en esta fila
            List<Object> claveGrupo = new java.util.ArrayList<>();
            for (Etiqueta etiqueta : etiquetasGrupo) {
                int idx = tablaOriginal.getEtiquetasColumnas().indexOf(etiqueta);
                claveGrupo.add(fila.get(idx).getValor());
            }
            //agregamos la fila al grupo correspondiente
            grupos.computeIfAbsent(claveGrupo, k -> new java.util.ArrayList<>()).add(fila);
        }
        return grupos;
    }

    private Tabla construirTablaAgregada(
            Map<List<Object>, List<List<Celda<?>>>> grupos,
            Tabla tablaOriginal,
            List<Etiqueta> etiquetasColumnasASumarizar,
            TipoOperacion tipoOperacion) {

        //armado de la lista de columnas finales: primero las de agrupación, después las sumarizadas
        List<Etiqueta> columnasFinales = new java.util.ArrayList<>();
        int cantidadColumnasGrupo = grupos.keySet().isEmpty() ? 0 : grupos.keySet().iterator().next().size();
        for (int i = 0; i < cantidadColumnasGrupo; i++) {
            columnasFinales.add(tablaOriginal.getEtiquetasColumnas().get(i));
        }
        columnasFinales.addAll(etiquetasColumnasASumarizar);


        // Armamdo de la lista de tipos para cada columna de la tabla resumen
        List<TipoDato> tiposFinales = new java.util.ArrayList<>();
        // Primero, los tipos de las columnas de agrupación
        for (int i = 0; i < cantidadColumnasGrupo; i++) {
            tiposFinales.add(tablaOriginal.getTipoDatoColumna(columnasFinales.get(i)));
        }
        //tipos de las columnas sumarizadas
        for (Etiqueta et : etiquetasColumnasASumarizar) {
            tiposFinales.add(tablaOriginal.getTipoDatoColumna(et));
        }
        //tabla con las columnas bien tipadas:
        // Crear la tabla con las columnas y tipos de dato correctos
        Tabla tablaResumida = new Tabla();
        for (int i = 0; i < columnasFinales.size(); i++) {
            Etiqueta etiqueta = columnasFinales.get(i);
            TipoDato tipoDato = tablaOriginal.getTipoDatoColumna(etiqueta);
            tablaResumida.agregarColumna(etiqueta, new Columna(tipoDato));
        }



        FabricaOperacionesSumarizacion fabrica = new FabricaOperacionesSumarizacion();
        Sumarizador sumarizador = fabrica.obtenerEstrategia(tipoOperacion);

        //por cada grupo, armamos la fila resumen
        for (Map.Entry<List<Object>, List<List<Celda<?>>>> grupo : grupos.entrySet()) {
            List<Object> valoresGrupo = grupo.getKey();
            List<List<Celda<?>>> filasDelGrupo = grupo.getValue();

            List<Celda<?>> filaResumen = new java.util.ArrayList<>();

            //1ro agregamos los valores de agrupación
            for (int i = 0; i < valoresGrupo.size(); i++) {
                Object valor = valoresGrupo.get(i);
                Etiqueta etiqueta = columnasFinales.get(i);
                filaResumen.add(new Celda<>(valor, tablaOriginal.getTipoDatoColumna(etiqueta)));
            }

            //2do, paara cada columna a sumarizar, aplica el sumarizador a esa columna en las filas del grupo
            for (Etiqueta columnaSumarizar : etiquetasColumnasASumarizar) {
                int idx = tablaOriginal.getEtiquetasColumnas().indexOf(columnaSumarizar);

                // Juntar todas las celdas de esa columna en este grupo
                List<Celda<?>> celdasColumna = new java.util.ArrayList<>();
                for (List<Celda<?>> fila : filasDelGrupo) {
                    celdasColumna.add(fila.get(idx));
                }
                // aplicamos el sumarizador y agregamos el resultado a la fila resumen
                filaResumen.add(sumarizador.sumarizar(celdasColumna));
            }
            //3ro agregamos la fila resumen a la nueva tabla
            tablaResumida.agregarFila(filaResumen);
        }
        return tablaResumida;
    }


}