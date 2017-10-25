package entity;

import java.io.Serializable;
import java.util.Arrays;

import util.$;
import util.Lot;
import util.SortComparator;

import static util.$.*;

public class ConcursoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public final int id;

    public final long data;

    public final DezenaBean[] dezenas; // dezenas na ordem de sorteio.

    public final int[] ordenadas; // dezenas na ordem numeral (ascendente).

    /*
     *
     */
    public ConcursoBean(final int p_id, final long p_data, final DezenaBean[] p_dezenas, final int[] p_ordenadas) {
        super();

        this.id = p_id;
        this.data = p_data;
        // numero fixo de dezenas da loteria.
        this.dezenas = p_dezenas;
        this.ordenadas = p_ordenadas;
    }

    // --- Classificacao dos Numeros Naturais ---------------------------------

    public int qtInferior;

    public int qtSuperior;

    // contabilizacao do conjunto de inferiores e superiores ate o concurso corrente:
    public int i3s12; // 3 inferiores, 12 superiores

    public int i4s11; // 4 inferiores, 11 superiores

    public int i5s10; // 5 inferiores, 10 superiores

    public int i6s9; // 6 inferiores, 9 superiores

    public int i7s8; // 7 inferiores, 8 superiores

    public int i8s7; // 8 inferiores, 7 superiores

    public int i9s6; // 9 inferiores, 6 superiores

    public int i10s5; // 10 inferiores, 5 superiores

    public int i11s4; // 11 inferiores, 4 superiores

    public int i12s3; // 12 inferiores, 3 superiores

    public int i13s2; // 13 inferiores, 2 superiores

    //

    public int qtPar;

    public int qtImpar;

    // contabilizacao do conjunto de pares e impares ate o concurso corrente:
    public int p2i13; // 2 pares, 13 impares

    public int p3i12; // 3 pares, 12 impares

    public int p4i11; // 4 pares, 11 impares

    public int p5i10; // 5 pares, 10 impares

    public int p6i9; // 6 pares, 9 impares

    public int p7i8; // 7 pares, 8 impares

    public int p8i7; // 8 pares, 7 impares

    public int p9i6; // 9 pares, 6 impares

    public int p10i5; // 10 pares, 5 impares

    public int p11i4; // 11 pares, 4 impares

    public int p12i3; // 12 pares, 3 impares

    //

    public int qtPrimo;

    // contabilizacao do conjunto de primos ate o concurso corrente:
    public int pr0; // nenhum primo

    public int pr1; // 1 primo

    public int pr2; // 2 primos

    public int pr3; // 3 primos

    public int pr4; // 4 primos

    public int pr5; // 5 primos

    public int pr6; // 6 primos

    public int pr7; // 7 primos

    public int pr8; // 8 primos

    public int pr9; // 9 primos

    public int pr10; // 10 primos

    //

    public int qtFatorial;

    public int qtPerfeito;

    public int qtAbundante;

    public int qtDeficiente;

    public int qtImperfeito;

    public int qtQuadrado;

    public int qtFibonacci;

    // estatisticas do valor das dezenas do concurso:
    public int dzSoma;

    public int dzMedia;

    // contabilizacao do numero de concursos com a media correspondente:
    public int md8; // dezenas com media 8

    public int md9; // dezenas com media 9

    public int md10; // dezenas com media 10

    public int md11; // dezenas com media 11

    public int md12; // dezenas com media 12

    public int md13; // dezenas com media 13

    public int md14; // dezenas com media 14

    public int md15; // dezenas com media 15

    public int md16; // dezenas com media 16

    public int md17; // dezenas com media 17

    public int md18; // dezenas com media 18

    //

    public int dzSequencia; // Numero de dezenas dispostas em sequencia.

    // contabilizacao do numero de sequencias em todos os concursos ate o presente.
    public int sq5; // 5 dezenas em sequencia

    public int sq6; // 6 dezenas em sequencia

    public int sq7; // 7 dezenas em sequencia

    public int sq8; // 8 dezenas em sequencia

    public int sq9; // 9 dezenas em sequencia

    public int sq10; // 10 dezenas em sequencia

    public int sq11; // 11 dezenas em sequencia

    public int sq12; // 12 dezenas em sequencia

    public int sq13; // 13 dezenas em sequencia

    public int sq14; // 14 dezenas em sequencia

    public int sq15; // 15 dezenas em sequencia

    //

    public int dzSeguidas; // Numero maximo de dezenas dispostas em seguida.

    // contabilizacao do numero maximo de dezenas em seguida em todos os concursos ate o presente.
    public int sg2; // 2 dezenas em sequencia

    public int sg3; // 3 dezenas em sequencia

    public int sg4; // 4 dezenas em sequencia

    public int sg5; // 5 dezenas em sequencia

    public int sg6; // 6 dezenas em sequencia

    public int sg7; // 7 dezenas em sequencia

    public int sg8; // 8 dezenas em sequencia

    public int sg9; // 9 dezenas em sequencia

    public int sg10; // 10 dezenas em sequencia

    public int sg11; // 11 dezenas em sequencia

    public int sg12; // 12 dezenas em sequencia

    //

    public int dzDistancia; // Distancia entre a menor e maior dezena.

    // contabilizacao da distancia entre as dezenas ate o concurso corrente.
    public int ds14; // 14 unidades de distancia

    public int ds15; // 15 unidades de distancia

    public int ds16; // 16 unidades de distancia

    public int ds17; // 17 unidades de distancia

    public int ds18; // 18 unidades de distancia

    public int ds19; // 19 unidades de distancia

    public int ds20; // 20 unidades de distancia

    public int ds21; // 21 unidades de distancia

    public int ds22; // 22 unidades de distancia

    public int ds23; // 23 unidades de distancia

    public int ds24; // 24 unidades de distancia

    // --- Ciclo das dezenas sorteadas (15) e da lotofacil (25) -----------

    public int ciclo15;

    public int[] ciclosPercentual15;

    public int[][] ciclosDezenas15;

    public int[] ciclosCont15;

    public int[] ciclosPerc15;

    //

    public int ciclo25;

    public int[] ciclosPercentual25;

    public int[][] ciclosDezenas25;

    public int[] ciclosCont25;

    public int[] ciclosPerc25;

    // --- Frequencias de ocorrencias, repeticoes e atrasos das dezenas -------

    public int[] dzOcorrencias; // relacao de dezenas, com a quantidade de vezes em que foram sorteadas.

    //

    public int[] dzRepetidas; // Relacao de dezenas, com a quantidade de recorrencias de cada.

    public int[] dzMaxRepetidas; // Relacao de dezenas, com a quantidade maxima de repeticoes de cada.

    public int[] dzRecorrentes; // Relacao de dezenas, com a quantidade de vezes em que se repetiram ao longo dos
                                // concursos.

    public int[] dzSomRecorrentes; // Relacao de dezenas, com a soma das repeticoes ao longo dos concursos.

    public float[] dzMedRecorrentes; // Relacao de dezenas, com a media das repeticoes ao longo dos concursos.

    //

    public int[] dzAtrasadas; // Relacao de dezenas, com a quantidade de atrasos de cada.

    public int[] dzMaxAtrasadas; // Relacao de dezenas, com a quantidade maxima de atrasos de cada.

    public int[] dzAtrasos; // Relacao de dezenas, com a quantidade de vezes em que ficaram atrasadas ao longo dos
                            // concursos.

    public int[] dzSomAtrasos; // Relacao de dezenas, com a soma dos atrasos ao longo dos concursos.

    public float[] dzMedAtrasos; // Relacao de dezenas, com a media dos atrasos ao longo dos concursos.

    //

    public int qtRepetidas; // Quantidade de dezenas que repetiram (eco) desde o ultimo concurso.

    public int[] ultRepetidas; // Relacao das dezenas que se repetiram desde o ultimo concurso.

    public int qtPenRepetidas; // Quantidade de dezenas que repetiram (eco) em relacao ao penultimo concurso.

    public int[] penRepetidas; // Relacao das dezenas que se repetiram em relacao ao penultimo concurso.

    // contabilizacao do numero de dezenas repetidas no concurso.
    public int rp5; // 5 repetidas

    public int rp6; // 6 repetidas

    public int rp7; // 7 repetidas

    public int rp8; // 8 repetidas

    public int rp9; // 9 repetidas

    public int rp10; // 10 repetidas

    public int rp11; // 11 repetidas

    public int rp12; // 12 repetidas

    public int rp13; // 13 repetidas

    public int rp14; // 14 repetidas

    public int rp15; // 15 repetidas

    //

    // Quantidade maxima de dezenas que "acertaram" em todos os concursos.
    public int qtAcertos; // Tambem equivalente a pontuacao do concurso.

    // contabilizacao do numero de dezenas que "acertaram" em todos os concursos.
    public int ac5; // 5 acertos

    public int ac6; // 6 acertos

    public int ac7; // 7 acertos

    public int ac8; // 8 acertos

    public int ac9; // 9 acertos

    public int ac10; // 10 acertos

    public int ac11; // 11 acertos

    public int ac12; // 12 acertos

    public int ac13; // 13 acertos

    public int ac14; // 14 acertos

    public int ac15; // 15 acertos

    //

    // contabilizacao da pontuacao, equivalente ao numero maximo de dezenas que "acertaram" em todos os concursos.
    public int pt5; // 5 pontos

    public int pt6; // 6 pontos

    public int pt7; // 7 pontos

    public int pt8; // 8 pontos

    public int pt9; // 9 pontos

    public int pt10; // 10 pontos

    public int pt11; // 11 pontos

    public int pt12; // 12 pontos

    public int pt13; // 13 pontos

    public int pt14; // 14 pontos

    public int pt15; // 15 pontos

    // --- Distribuicao combinatoria das dezenas em linhas e colunas ----------

    public String distBinario; // eg: 0101101010011011101110101

    public String distLinhas; // eg: "3-1-4-3-4"

    public String distColunas; // eg: "3-1-4-3-4"

    // composicao da dezenas no mesmo formato do cartao.
    public String distCartao; // eg: "[01] [xx] [03] [04] [05]"

    // valor gerado na estrategia, com ranking das dezenas no presente concurso.
    public String distRank = "[__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__] [__]";

    /*
     * Verifica se uma dezena fornecida eh uma das dezenas sorteadas no concurso.
     */
    public boolean isDezSorteada(final int p_dez) {
        return (Arrays.binarySearch(this.ordenadas, p_dez) > -1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Lot.formatDez(this.id, 4)).append("  (").append($.formatDate(this.data)).append(')');

        sb.append("  [ ");
        for (int i = 0; i < this.ordenadas.length; i++) {
            sb.append(Lot.formatDez(this.ordenadas[i], 2)).append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\tid: ").append(id);
        sb.append("\n\tdata: ").append(formatDate(data));

        sb.append("\n\tdezenas: [ ");
        for (int i = 0; i < ordenadas.length; i++) {
            sb.append(Lot.formatDez(this.ordenadas[i], 2)).append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    // Comparator ---------------------------------------------------------------------------------

    public static final int FIELD_NRCONCURSO = 1;

    public static final int FIELD_DTSORTEIO = 2;

    public static class ConcursoBeanComparator extends SortComparator<ConcursoBean> {
        public int compare(ConcursoBean o1, ConcursoBean o2) {
            switch (this.sortField) {
                case FIELD_NRCONCURSO:
                    return $.compare(o1.id, o2.id, this.sortAsc);

                case FIELD_DTSORTEIO:
                    return $.compare(o1.data, o2.data, this.sortAsc);

                default:
                    return -1;
            }
        }
    }

    public static ConcursoBeanComparator getComparator(final int sortField, final boolean sortAsc) {
        ConcursoBeanComparator comparator = new ConcursoBeanComparator();
        comparator.sortField = sortField;
        comparator.sortAsc = sortAsc;

        return comparator;
    }
}
