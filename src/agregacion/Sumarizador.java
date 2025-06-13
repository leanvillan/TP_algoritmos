package agregacion;

import gestiondedatos.Celda;
import java.util.List;

public interface Sumarizador {

    Celda<?> sumarizar(List<Celda<?>> celdas);

}
