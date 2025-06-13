package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.Etiqueta;
import gestiondedatos.Tabla;

import java.util.List;
import java.util.Map;

/**
 * Representa el resultado de agrupar una tabla según ciertas columnas.
 * Guarda los grupos formados, las columnas de agrupación y la tabla original.
 */

public class TablaAgrupada {

    // Mapa que representa los grupos:
    // Clave: combinación de valores de agrupación (uno por columna)
    // Valor: lista de filas (cada fila es una lista de celdas) que pertenecen a ese grupo
    private final Map<List<Object>, List<List<Celda<?>>>> grupos;

    // Lista de etiquetas de las columnas utilizadas para agrupar
    private final List<Etiqueta> etiquetasColumnasGrupo;

    // Referencia a la tabla original que fue agrupada
    private final Tabla tablaOriginal;

    // Constructor que inicializa los datos de la tabla agrupada.
    public TablaAgrupada(Map<List<Object>, List<List<Celda<?>>>> grupos,
                         List<Etiqueta> etiquetasColumnasGrupo,
                         Tabla tablaOriginal) {
        this.grupos = grupos;
        this.etiquetasColumnasGrupo = etiquetasColumnasGrupo;
        this.tablaOriginal = tablaOriginal;
    }

    // Devuelve el mapa de grupos (clave: combinación de valores, valor: filas del grupo).
    public Map<List<Object>, List<List<Celda<?>>>> getGrupos() {
        return grupos;
    }

    // Devuelve la lista de etiquetas de las columnas usadas para agrupar.
    public List<Etiqueta> getEtiquetasColumnasGrupo() {
        return etiquetasColumnasGrupo;
    }

    // Devuelve la tabla original de donde provienen los grupos.
    public Tabla getTablaOriginal() {
        return tablaOriginal;
    }
}
