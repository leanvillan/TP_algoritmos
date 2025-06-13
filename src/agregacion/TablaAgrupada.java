package agregacion;

import gestiondedatos.Celda;
import gestiondedatos.Etiqueta;
import gestiondedatos.Tabla;

import java.util.List;
import java.util.Map;

public class TablaAgrupada {
    // Mapa: Clave del grupo (valores de las columnas de agrupación)
    private final Map<List<Object>, List<List<Celda<?>>>> grupos;
    private final List<Etiqueta> etiquetasColumnasGrupo; //Etiquetas de las columnas que uso
    //para agrupar
    private final Tabla tablaOriginal;

    public TablaAgrupada(Map<List<Object>, List<List<Celda<?>>>> grupos,
                         List<Etiqueta> etiquetasColumnasGrupo,
                         Tabla tablaOriginal) {
        this.grupos = grupos;
        this.etiquetasColumnasGrupo = etiquetasColumnasGrupo;
        this.tablaOriginal = tablaOriginal;
    }

    public Map<List<Object>, List<List<Celda<?>>>> getGrupos() {
        return grupos;
    }

    public List<Etiqueta> getEtiquetasColumnasGrupo() {
        return etiquetasColumnasGrupo;
    }

    public Tabla getTablaOriginal() {
        return tablaOriginal;
    }
}
