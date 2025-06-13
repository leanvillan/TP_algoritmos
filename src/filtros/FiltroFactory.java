package filtros;

import gestiondeerrores.ExcepcionOperacionNoValida;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction; // Para los creadores de filtros logicos
import java.util.function.Function; // Para los creadores de filtros comparadores
import java.util.function.Supplier; // Para los filtros que no necesite pasarle parametors

/**
 * La clase 'FiltroFactory' se encarga de instanciar las clases que implementan Filtro - FiltroLogico - FiltroNOT - FiltroSinParametros.
 *
 * Utiliza mapas para registrar nuevas clases que implementen las interfaces mencionadas.
 *
 * Para crear los filtros, se utilizan metodos que devuelven el tipo de filtro creado.
 * Los metodos son 'static', no se necesita instanciar 'FiltroFactory' para crear los filtros.
 *
 */



public class FiltroFactory {

    /// --- Mapa para Filtros sin parámetros (uso de supplier)---
    private static final Map<TipoSinParametro, Supplier<FiltroSinParametro>> filtrosSinParametros = new HashMap<>();

    /// --- Mapa para Filtros Comparadores (todos usan Object como valor a comparar) ---
    private static final Map<TipoComparador, Function<Object, Filtro>> filtrosComparadores = new HashMap<>();

    /// --- Mapa para Filtros Lógicos ( toma dos parametros de tipo Filtro y retorna tipo FiltroLogico ) ---
    private static final Map<OperadorLogico, BiFunction<Filtro, Filtro, FiltroLogico>> filtroslogicos = new HashMap<>();

    static {
       // --- Registro de Filtros sin parámetros ---
        filtrosSinParametros.put(TipoSinParametro.ES_NULO, EsNulo::new); // Constructor EsNulo()

        // --- Registro de Filtros Comparadores (todos toman Object para instanciarse) ---
        filtrosComparadores.put(TipoComparador.IGUAL, Igual::new); // Constructor Igual(Object)
        filtrosComparadores.put(TipoComparador.DISTINTO, Distinto::new); //Constructor Distinto(Object)
        filtrosComparadores.put(TipoComparador.MAYOR, Mayor::new); // Constructor Mayor(Object)
        filtrosComparadores.put(TipoComparador.MENOR, Menor::new); // Constructor Menor(Object)
        filtrosComparadores.put(TipoComparador.MAYOR_IGUAL, MayorIgual::new); // Constructor MayorIgual(Object)
        filtrosComparadores.put(TipoComparador.MENOR_IGUAL, MenorIgual::new); // Constructor MenorIgual(Object)

        // --- Registro de Filtros Lógicos ---
        filtroslogicos.put(OperadorLogico.AND, FiltroAND::new); //Constructor FiltroAND(Filtro,Filtro)
        filtroslogicos.put(OperadorLogico.OR, FiltroOR::new);  //Constructor FiltroAND(Filtro,Filtro)
    }

    ///  ---- Creamos intancias de filtros simples, pasando el Tipo de comprador y el valor a comparar
    public static Filtro crearFiltroComparador(TipoComparador tipo, Object valorAComparar) {
        if (tipo == null) {
            throw new ExcepcionOperacionNoValida("El operador de comparación no puede ser nulo.");
        }

        Function<Object, Filtro> referencia = filtrosComparadores.get(tipo); // obtenemos el tipo de comparador que necesitamos
        if (referencia == null) {
            throw new ExcepcionOperacionNoValida("Tipo de comparación no reconocido: " + tipo);
        }
        return referencia.apply(valorAComparar);
        //Utilizando la referencia a metodo, creamos y retornamos el objeto tipo 'Filtro' ( pasamos el valor a comparar)
    }

    /// ----- Creamos el FiltroLogico, cuyo operador logico binario pertenecer al enumerable OperadorLogico
    public static FiltroLogico crearFiltroLogico(OperadorLogico operadorLogico, Filtro filtro1, Filtro filtro2) {
        if (operadorLogico == null) {
            throw new ExcepcionOperacionNoValida("El operador logico no puede ser nulo.");
        }
        if (filtro1 == null || filtro2 == null) {
            throw new ExcepcionOperacionNoValida("Ambos filtros internos deben ser validos para un filtro logico.");
        }

        BiFunction<Filtro, Filtro, FiltroLogico> referencia = filtroslogicos.get(operadorLogico);
        if (referencia == null) {
            throw new ExcepcionOperacionNoValida("Operador lógico no reconocido: " + operadorLogico);
        }
        return referencia.apply(filtro1, filtro2);
        //Utilizando la referencia a metodo, creamos y retornamos el objeto tipo 'FiltroLogico' (pasamos los filtros internos)
    }

    /// ---- Creamos el filtro NOT, requiere unicamente de un filtro interno
    public static FiltroNOT crearFiltroNOT(Filtro filtroInterno) {
        if (filtroInterno == null) {
            throw new ExcepcionOperacionNoValida("El filtro interno para la operación NOT no puede ser nulo.");
        }
        return new FiltroNOT(filtroInterno);
    }

    /// --- Creamos instancias de filtros que no necesitan pasar valores por parametro, pasandole el tipo de Filtro
    public static FiltroSinParametro crearFiltroSinParametros(TipoSinParametro tipoFiltro) {
        Supplier<FiltroSinParametro> referencia = filtrosSinParametros.get(tipoFiltro);
        if (referencia == null) {
            throw new ExcepcionOperacionNoValida("Tipo de filtrosin parámetros no reconocido: " + tipoFiltro);
        }
        return referencia.get(); // Utilizando la referencia a metodo, llamamos al constructor de 'FiltroSinParametros', creamos y retornamos este objeto
    }

}