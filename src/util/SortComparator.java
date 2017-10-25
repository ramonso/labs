package util;

import java.util.Comparator;

/**
 * Ancestral para criacao de comparators especificos para uma determinada entidade. Descendentes desta classe podem ser
 * criados para uma entidade e deverao implementar os metodos equals() e compare().
 */
public abstract class SortComparator<T> implements Comparator<T> {

    // Instance ------------------------------------------------------------------------------------

    /**
     * Identifica o campo a ser utilizado para ordenacao de uma entidade, identificado por sua posicao sequencial.
     */
    public int sortField;

    /**
     * Identifica o criterio de ordenacao: True para ascendente, False para descendente.
     */
    public boolean sortAsc;

    /**
     * Retorna uma instancia valida (objeto) desta classe SortComparator.
     */
    protected SortComparator() {
        super();
    }
}
