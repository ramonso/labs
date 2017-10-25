package util;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Arrays;

/*
 * Classe utilitaria contendo funcoes diversas de manipulacao de concursos e dezenas da loteria.
 */
public final class Lot {

    // --- Funcoes para formatacao de dezenas ---------------------------------

    /*
     * Formata um valor inteiro, normalmente uma dezena, em tamanho fixo de caracteres,
     * alinhando o valor a direita.
     */
    public static String formatDez(final int p_val, final int p_len) {
        // ja inicializa a string com ' ' espacado com tamanho p_len.
        final StringBuilder str = new StringBuilder($.repeat(' ', p_len));
        str.append(p_val); // adiciona o valor com os espacos a esquerda.

        final int length = str.length();
        return str.substring(length - p_len).toString();
    }

    /*
     * Formata um valor float, normalmente uma dezena, em tamanho fixo de caracteres,
     * alinhando o valor a direita.
     */
    public static String formatDez(final float p_val, final String p_mask, final int p_len) {
        // ja inicializa a string com ' ' espacado com tamanho p_len.
        final StringBuilder str = new StringBuilder($.repeat(' ', p_len));

        // adiciona o valor com os espacos a esquerda.
        str.append(MessageFormat.format("{0,number," + p_mask + "}", p_val));

        final int length = str.length();
        return str.substring(length - p_len).toString();
    }

    // --- Funcoes para formatacao de arrays de dezenas -----------------------

    /*
     * Formata um array de dezenas de forma legivel e simples,
     * com tamanho fixo de 2 casas:
     * [ 1 2 3 4 6 8 90 ]
     */
    public static String formatArray(final int[] p_array) {
        final StringBuilder str = new StringBuilder("[ ");

        if ($.isNotEmpty(p_array)) {
            for (int numero : p_array) {
                str.append(numero).append(' '); // separa as dezenas por um ' '.
            }
        }
        str.append("]");

        return str.toString();
    }

    /*
     * Formata um array de dezenas de forma legivel e simples,
     * com tamanho fixo de 'p_fix' casas: [ 1 2 3 4 6 8 90 ].
     * NAO IGNORA O ZERO.
     */
    public static String formatArray(final int[] p_array, final int p_fix) {
        final StringBuilder str = new StringBuilder("[ ");

        if (p_array != null) {
            for (int numero : p_array) {
                // formata cada dezena em 'p_fix' caracteres.
                str.append(formatDez(numero, p_fix)).append(' '); // separa as dezenas por um ' '.
            }
        }
        str.append("]");

        return str.toString();
    }

    /*
     * Formata um array de valores de forma legivel e simples,
     * com tamanho fixo de 'p_fix' casas: [ 1 2 3 4 6 8 90 ].
     * NAO IGNORA O ZERO.
     */
    public static String formatArray(final float[] p_array, final String p_mask, final int p_fix) {
        final StringBuilder str = new StringBuilder("[ ");

        if (p_array != null) {
            for (float numero : p_array) {
                // formata cada dezena em 'p_fix' caracteres.
                str.append(formatDez(numero, p_mask, p_fix)).append(' '); // separa as dezenas por um ' '.
            }
        }
        str.append("]");

        return str.toString();
    }

    /*
     * Formata um array de inteiros de forma legivel e simples,
     * com tamanho fixo de 4 casas:
     * [     1    2    3    4 ]
     */
    public static String formatDezenas(final int[] p_array) {
        final StringBuilder str = new StringBuilder("[ ");

        if ($.isNotEmpty(p_array)) {
            for (int numero : p_array) {
                // formata cada dezena em 4 caracteres (' 1', ' 2', ..., '10', '25').
                if (numero == 0) {
                    str.append("    "); // zero nao aparece, eh uma lacuna na lista.
                } else {
                    str.append(formatDez(numero, 4));
                }
                str.append(' '); // separa as dezenas por um ' '.
            }
        }
        str.append("]");

        return str.toString();
    }

    /*
     * Formata um array de inteiros de forma legivel e simples,
     * com tamanho fixo de 4 casas:
     * [     1    2    3    4 ]
     */
    public static String formatDezenas(final int[] p_array, final int p_fix) {
        final StringBuilder str = new StringBuilder("[ ");

        if ($.isNotEmpty(p_array)) {
            for (int numero : p_array) {
                // formata cada dezena em 'p_fix' caracteres (' 1', ' 2', ..., '10', '25').
                if (numero == 0) {
                    str.append($.repeat(' ', p_fix)); // zero nao aparece, eh uma lacuna na lista.
                } else {
                    str.append(formatDez(numero, p_fix));
                }
                str.append(' '); // separa as dezenas por um ' '.
            }
        }
        str.append("]");

        return str.toString();
    }

    /*
     * Formata um array de floats de forma legivel e simples,
     * com tamanho fixo de 4 casas:
     * [  1.1  1.1 30.0 40.0 60.0 80.0 90.0 ]
     */
    public static String formatDezenas(final float[] p_array) {
        final StringBuilder str = new StringBuilder("[ ");

        if ($.isNotEmpty(p_array)) {
            for (float numero : p_array) {
                // formata cada dezena em 4 caracteres (' 1', ' 2', ..., ' 10', ' 25').
                if (numero == 0f) {
                    str.append("    "); // zero nao aparece, eh uma lacuna na lista.
                } else {
                    str.append(formatDez(numero, "#0.0", 4));
                }
                str.append(' '); // separa as dezenas por um ' '.
            }
        }
        str.append("]");

        return str.toString();
    }

    /*
     * calcula o fatorial do numero n.
     */
    public static BigInteger factorial(final int n) {
        if (n == 0) return BigInteger.ONE; // fatorial de 0 eh 1.

        BigInteger result = BigInteger.valueOf(n);

        for (int i = n - 1; i > 0; i--)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    /*
     * calcula o numero de combinacoes de n elementos, considerados p a p.
     */
    public static int combinacoes(final int n, final int p) {
        if (n == p) return 1;

        BigInteger nFat = factorial(n);
        BigInteger pFat = factorial(p);
        BigInteger nMinusPFat = factorial(n - p);
        BigInteger result = nFat.divide(pFat.multiply(nMinusPFat));

        return result.intValue();
    }

    /*
     * 
     */
    public static int[][] geraCombinacoes(final int n, final int p) {
        final int c = combinacoes(n, p);
        final int[][] m = new int[c][p];
        final int[] vN = new int[p];

        // gera as combinacoes de jogos:
        for (int i = 0; i < p; i++) {
            vN[i] = i;
            m[0][i] = i;
        }

        for (int i = 1; i < c; i++) {
            for (int j = p - 1; j >= 0; j--) {
                if (vN[j] < (n - p + j)) {
                    vN[j]++;
                    for (int k = j + 1; k < p; k++) {
                        vN[k] = vN[j] + k - j;
                    }
                    for (int l = 0; l < p; l++) {
                        m[i][l] = vN[l];
                    }

                    break;
                }
            }
        }

        // normaliza os jogos, que estao com as dezenas iniciando em zero:
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < p; j++) {
                m[i][j]++;
            }
        }

        return m;
    }

    /*
     * identifica quantas dezenas do primeiro array se repetiram no segundo array.
     * o array p_primeiro NAO precisa estar ordenado. o array p_segundo precisa estar ordenado.
     */
    public static int countRepetidas(final int[] p_primeiro, final int[] p_segundo) {
        if ($.isEmpty(p_primeiro) || $.isEmpty(p_segundo)) return 0;

        int qtRepetidas = 0; // acumula o numero de dezenas que se repetiram.

        // varre as dezenas do primeiro array e verifica quantas sairam no segundo array.
        for (int dez : p_primeiro) { // avalia todas as 15 dezenas do array.

            // verifica se a dezena no primeiro esta presente no segundo.
            if (Arrays.binarySearch(p_segundo, dez) > -1) {
                qtRepetidas++; // mais uma dezena que se repetiu entre os arrays.
            }
        }

        return qtRepetidas; // retorna o numero de dezenas do primeiro array que se repetiram no segundo.
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            $.log("Numero = {0}", $.random(10));
    }
}
