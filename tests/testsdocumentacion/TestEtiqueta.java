package testsdocumentacion;

import gestiondedatos.*;

public class TestEtiqueta {
    public static void main(String[] args) {
        //Creamos las etiquetas
        Etiqueta<String> colNombre = new EtiquetaString("Nombre");
        Etiqueta<Integer> filaUno = new EtiquetaEntero(1);

        //Imprimimos información de las etiquetas
        System.out.println("Etiqueta de columna: " + colNombre.getValor());
        System.out.println("Etiqueta de fila: " + filaUno.getValor());
        System.out.println("Etiqueta columna toString: " + colNombre);
        System.out.println("Etiqueta fila toString: " + filaUno);

        //Copiamos las etiquetas
        Etiqueta<String> copiaColNombre = colNombre.copiar();
        Etiqueta<Integer> copiaFilaUno = filaUno.copiar();

        System.out.println("¿Copia de columna es igual al original?: " + copiaColNombre.equals(colNombre));
        System.out.println("¿Copia de fila es igual al original?: " + copiaFilaUno.equals(filaUno));
    }
}
