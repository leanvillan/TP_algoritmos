package manipulacion;
import gestiondedatos.*;
import java.util.*;

//Esta clase ordena las etiquetas de filas según el valor en la columna dada
public class OrdenadorDatos extends ManipuladorDatos {
    private List<String> criterioOrden;

    public OrdenadorDatos(List<String> criterioOrden) {
        this.criterioOrden = criterioOrden;
    }

    @Override
    public void manipular(Tabla tabla) {
        if (criterioOrden == null || criterioOrden.isEmpty()) return;

        String nombreColumna = criterioOrden.get(0);

        Etiqueta columnaEtiqueta = null;
        for (Etiqueta et : tabla.getEtiquetasColumnas()) {
            if (et.getValor().equals(nombreColumna)) {
                columnaEtiqueta = et;
                break;
            }
        }
        if (columnaEtiqueta == null) return; // columna no existe

        //guardamos el orden original de las etiquetas antes de ordenar
        List<Etiqueta> etiquetasFilasOriginal = new ArrayList<>(tabla.getEtiquetasFilas());

        //creamos lista de pares (etiqueta, valor)
        List<Pair> pares = new ArrayList<>();
        for (Etiqueta et : etiquetasFilasOriginal) {
            Comparable valor = (Comparable) tabla.getCelda(columnaEtiqueta, et).getValor();
            pares.add(new Pair(et, valor));
        }
        //ordenamos los pares por valor
        pares.sort(Comparator.comparing(p -> p.valor, Comparator.nullsLast(Comparator.naturalOrder())));

        //nuevo orden de etiquetas (de menor a mayor)
        List<Etiqueta> nuevoOrdenEtiquetas = new ArrayList<>();
        for (Pair p : pares) {
            nuevoOrdenEtiquetas.add(p.etiqueta);
        }
        tabla.getEtiquetasFilas().clear();
        tabla.getEtiquetasFilas().addAll(nuevoOrdenEtiquetas);

        //reordenamos las celdas de cada columna según el nuevo orden
        for (Columna columna : tabla.getColumnasMap().values()) {
            List<Celda<?>> nuevasCeldas = new ArrayList<>();
            for (Etiqueta et : nuevoOrdenEtiquetas) {
                int idx = etiquetasFilasOriginal.indexOf(et); // ¡usamos el orden original!
                nuevasCeldas.add(columna.getCeldas().get(idx));
            }
            columna.getCeldas().clear();
            columna.getCeldas().addAll(nuevasCeldas);
        }
    }

    //clase Pair igual que antes
    private static class Pair {
        Etiqueta etiqueta;
        Comparable valor;
        Pair(Etiqueta e, Comparable v) {
            this.etiqueta = e;
            this.valor = v;
        }
    }


}
