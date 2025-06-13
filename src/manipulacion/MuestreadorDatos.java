package manipulacion;
import gestiondedatos.*;
import java.util.*;

//toma un porcentaje de filas
//elimina filas que no entran en la muestra. (Se puede adaptar para devolver una tabla nueva si preferís no modificar la original)

public class MuestreadorDatos extends ManipuladorDatos {
    private double porcentaje;

    public MuestreadorDatos(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public void manipular(Tabla tabla) {
        int totalFilas = tabla.contarFilas();
        int cantidadAMostrar = (int) Math.round(totalFilas * porcentaje);
        if (cantidadAMostrar <= 0) return;
        List<Etiqueta> todasEtiquetas = new ArrayList<>(tabla.getEtiquetasFilas());
        Collections.shuffle(todasEtiquetas); // Mezcla aleatoriamente
        List<Etiqueta> seleccionadas = todasEtiquetas.subList(0, cantidadAMostrar);

        //se pueden dejar sólo las filas seleccionadas (x ej, eliminando las demás)
        List<Etiqueta> aEliminar = new ArrayList<>();
        for (Etiqueta et : tabla.getEtiquetasFilas()) {
            if (!seleccionadas.contains(et)) aEliminar.add(et);
        }
        for (Etiqueta et : aEliminar) {
            tabla.eliminarFila(et);
        }
    }
}
