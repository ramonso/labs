package entity;

import java.io.Serializable;

import util.$;
import util.Lot;
import util.SortComparator;

import static util.$.*;

/**
 * Representa as informacoes basicas de cada dezena, sem
 * considerar os dados de sorteio ou vinculo com concursos.
 *
 * Considerando apenas dezenas entre 1 e 100 no maximo...
 */
public class DezenaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int[] DEZENAS_INFERIORES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public static final int[] DEZENAS_PARES = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24};

    // --- PROPRIEDADES -------------------------------------------------------

    public final int numero;

    // valores posicionais dos 2 algarismos do número;
    public final int x;

    public final int y;

    // linha e colunas de acordo com a cartela de aposta
    public final int linha;

    public final int coluna;

    // 13: [ 1 2 3 4 5 6 7 8 9 10 11 12 13]
    public final boolean isInferior;

    // 12: [ 14 15 16 17 18 19 20 21 22 23 24 25 ]
    public final boolean isSuperior;

    // 12: [ 2 4 6 8 10 12 14 16 18 20 22 24 ]
    public final boolean isPar; // System.out.println('\u2588'); // █

    // 13: [ 1 3 5 7 9 11 13 15 17 19 21 23 25 ]
    public final boolean isImpar; // System.out.println('\u2592'); // ▒

    // 10: [ 1 2 3 5 7 11 13 17 19 23 ]
    public final boolean isPrimo; // System.out.println('\u2591'); // ░

    // 4: [ 1 2 6 24 ]
    public final boolean isFatorial; // System.out.println('\u2593'); // ▓

    // 2: [ 1 6 ]
    public final boolean isPerfeito; // System.out.println('\u256C'); // ╬

    // 4: [ 12 18 20 24 ]
    public final boolean isAbundante; // System.out.println('\u2548'); // ≈

    // 19: [ 2 3 4 5 7 8 9 10 11 13 14 15 16 17 19 21 22 23 25 ]
    public final boolean isDeficiente; // System.out.println('\u25AC'); // ▬

    // 4: [ 2 4 8 16 ]
    public final boolean isImperfeito; // System.out.println('\u256B'); // ╫

    // 5: [ 1 4 9 16 25 ]
    public final boolean isQuadrado;

    // 7: [ 1 2 3 5 8 13 21 ]
    public final boolean isFibonacci;

    public DezenaBean(final int p_numero) {
        super();

        this.numero = p_numero;

        int pos = (p_numero % 10);
        this.x = (pos == 0) ? 10 : pos;
        this.y = ((p_numero - 1) / 10) + 1;

        pos = (p_numero % 5);
        this.coluna = (pos == 0) ? 5 : pos;
        this.linha = ((p_numero - 1) / 5) + 1;

        this.isInferior = (p_numero <= 13);
        this.isSuperior = (p_numero >= 14);

        this.isPar = (p_numero % 2 == 0);
        this.isImpar = !this.isPar;
        this.isPrimo = isPrimo(p_numero);
        this.isFatorial = (p_numero == 1 || p_numero == 2 || p_numero == 6 || p_numero == 24);
        this.isQuadrado = (p_numero == 1 || p_numero == 4 || p_numero == 9 || p_numero == 16 || p_numero == 25);

        long somaDivisores = somaDivisores(p_numero);
        this.isPerfeito = (p_numero == somaDivisores); // (numero == 1 || numero == 6 || numero == 28);
        this.isAbundante = (p_numero < somaDivisores);
        this.isImperfeito = (p_numero == (somaDivisores + 1));
        this.isDeficiente = (p_numero > somaDivisores);

        this.isFibonacci = (p_numero == 1 || p_numero == 2 || p_numero == 3 || p_numero == 5 || p_numero == 8 || p_numero == 13 || p_numero == 21);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");

        sb.append(Lot.formatDez(numero, 2));

        sb.append(isInferior ? "<" : ">");
        sb.append(isPar ? "\"" : "'");
        if (isQuadrado) sb.append("²");
        if (isPrimo) sb.append("*");
        if (isFatorial) sb.append("!");

        if (isAbundante) sb.append("+");
        if (isDeficiente) sb.append("-");
        if (isPerfeito) sb.append("=");
        if (isImperfeito) sb.append("#");
        if (isFibonacci) sb.append("@");

        sb.append(" ");
        sb.append(y);
        sb.append(",");
        sb.append(x);

        return sb.append(")").toString();
    }

    public final String toJson() {
        StringBuilder sb = new StringBuilder(this.getClass().getCanonicalName()).append(" {");

        sb.append("\n\tnumero: ").append(numero);
        sb.append("\n\tisInferior: ").append(isInferior);
        sb.append("\n\tisSuperior: ").append(isSuperior);
        sb.append("\n\tisPar: ").append(isPar);
        sb.append("\n\tisImpar: ").append(isImpar);
        sb.append("\n\tisPrimo: ").append(isPrimo);
        sb.append("\n\tisFatorial: ").append(isFatorial);
        sb.append("\n\tisAbundante: ").append(isAbundante);
        sb.append("\n\tisDeficiente: ").append(isDeficiente);
        sb.append("\n\tisPerfeito: ").append(isPerfeito);
        sb.append("\n\tisImperfeito: ").append(isImperfeito);
        sb.append("\n\tisQuadrado: ").append(isQuadrado);
        sb.append("\n\tisFibonacci: ").append(isFibonacci);
        sb.append("\n\ty: ").append(y);
        sb.append("\n\tx: ").append(x);

        return sb.append("\n}").toString();
    }

    // Constantes -------------------------------------------------------------

    /*
     * A inicializacao pre das dezenas evita gasto excessivo de memoria.
     */
    public static DezenaBean DEZENA_01 = new DezenaBean(1);

    public static DezenaBean DEZENA_02 = new DezenaBean(2);

    public static DezenaBean DEZENA_03 = new DezenaBean(3);

    public static DezenaBean DEZENA_04 = new DezenaBean(4);

    public static DezenaBean DEZENA_05 = new DezenaBean(5);

    public static DezenaBean DEZENA_06 = new DezenaBean(6);

    public static DezenaBean DEZENA_07 = new DezenaBean(7);

    public static DezenaBean DEZENA_08 = new DezenaBean(8);

    public static DezenaBean DEZENA_09 = new DezenaBean(9);

    public static DezenaBean DEZENA_10 = new DezenaBean(10);

    public static DezenaBean DEZENA_11 = new DezenaBean(11);

    public static DezenaBean DEZENA_12 = new DezenaBean(12);

    public static DezenaBean DEZENA_13 = new DezenaBean(13);

    public static DezenaBean DEZENA_14 = new DezenaBean(14);

    public static DezenaBean DEZENA_15 = new DezenaBean(15);

    public static DezenaBean DEZENA_16 = new DezenaBean(16);

    public static DezenaBean DEZENA_17 = new DezenaBean(17);

    public static DezenaBean DEZENA_18 = new DezenaBean(18);

    public static DezenaBean DEZENA_19 = new DezenaBean(19);

    public static DezenaBean DEZENA_20 = new DezenaBean(20);

    public static DezenaBean DEZENA_21 = new DezenaBean(21);

    public static DezenaBean DEZENA_22 = new DezenaBean(22);

    public static DezenaBean DEZENA_23 = new DezenaBean(23);

    public static DezenaBean DEZENA_24 = new DezenaBean(24);

    public static DezenaBean DEZENA_25 = new DezenaBean(25);

    /*
     * Retorna a constante de DezenaBean referente a dezena fornecida (de 1 a 25).
     */
    public static DezenaBean valueOf(final int p_numero) {
        switch (p_numero) {
            case 1:
                return DEZENA_01;

            case 2:
                return DEZENA_02;

            case 3:
                return DEZENA_03;

            case 4:
                return DEZENA_04;

            case 5:
                return DEZENA_05;

            case 6:
                return DEZENA_06;

            case 7:
                return DEZENA_07;

            case 8:
                return DEZENA_08;

            case 9:
                return DEZENA_09;

            case 10:
                return DEZENA_10;

            case 11:
                return DEZENA_11;

            case 12:
                return DEZENA_12;

            case 13:
                return DEZENA_13;

            case 14:
                return DEZENA_14;

            case 15:
                return DEZENA_15;

            case 16:
                return DEZENA_16;

            case 17:
                return DEZENA_17;

            case 18:
                return DEZENA_18;

            case 19:
                return DEZENA_19;

            case 20:
                return DEZENA_20;

            case 21:
                return DEZENA_21;

            case 22:
                return DEZENA_22;

            case 23:
                return DEZENA_23;

            case 24:
                return DEZENA_24;

            case 25:
                return DEZENA_25;
            default:
                return null;
        }
    }

    // Comparator ---------------------------------------------------------------------------------

    public static final int FIELD_NUMERO = 0;

    public static final int FIELD_X = 1;

    public static final int FIELD_Y = 2;

    public static final int FIELD_ISPAR = 3;

    public static final int FIELD_ISPRIMO = 4;

    public static final int FIELD_ISFATORIAL = 5;

    public static final int FIELD_ISPERFEITO = 6;

    public static final int FIELD_ISABUNDANTE = 7;

    public static final int FIELD_ISDEFICIENTE = 8;

    public static final int FIELD_ISIMPERFEITO = 9;

    public static DezenaBeanComparator getComparator(final int sortField, final boolean sortAsc) {
        DezenaBeanComparator comparator = new DezenaBeanComparator();
        comparator.sortField = sortField;
        comparator.sortAsc = sortAsc;

        return comparator;
    }

    private static class DezenaBeanComparator extends SortComparator<DezenaBean> {
        public int compare(DezenaBean o1, DezenaBean o2) {
            switch (this.sortField) {
                case FIELD_NUMERO:
                    return $.compare(o1.numero, o2.numero, this.sortAsc);
                case FIELD_X:
                    return $.compare(o1.x, o2.x, this.sortAsc);
                case FIELD_Y:
                    return $.compare(o1.y, o2.y, this.sortAsc);
                case FIELD_ISPAR:
                    return $.compare(o1.isPar, o2.isPar, this.sortAsc);
                case FIELD_ISPRIMO:
                    return $.compare(o1.isPrimo, o2.isPrimo, this.sortAsc);
                case FIELD_ISFATORIAL:
                    return $.compare(o1.isFatorial, o2.isFatorial, this.sortAsc);
                case FIELD_ISPERFEITO:
                    return $.compare(o1.isPerfeito, o2.isPerfeito, this.sortAsc);
                case FIELD_ISABUNDANTE:
                    return $.compare(o1.isAbundante, o2.isAbundante, this.sortAsc);
                case FIELD_ISDEFICIENTE:
                    return $.compare(o1.isDeficiente, o2.isDeficiente, this.sortAsc);
                case FIELD_ISIMPERFEITO:
                    return $.compare(o1.isImperfeito, o2.isImperfeito, this.sortAsc);

                default:
                    return -1;
            }
        }
    }
}
