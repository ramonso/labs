package entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import util.$;
import util.Lot;

import static util.$.*;

public class LoteriaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // relacao das dezenas utilizadas na Loteria (25 dezenas):
    public final static int[] DEZENAS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};

    public final static int COMBINACOES = 3268760;

    // --- CONCURSOS ----------------------------------------------------------

    public ConcursoBean[] concursos; // relacao de resultados dos concursos processados.

    public int lenConcursos; // para otimizar codigo, guarda o tamanho do array.

    // --- APOSTAS ------------------------------------------------------------

    public ApostaBean[] apostas; // relacao de apostas geradas e filtradas.

    public int lenApostas; // para otimizar codigo, guarda o tamanho do array.

    // --- PROPRIEDADES -------------------------------------------------------

    /*
     * Metricas das dezenas sorteadas.
     */

    public Map<Integer, Integer> mapQtInferior = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapQtPar = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapQtPrimo = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapDzSequencia = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapDzSeguidas = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapDzDistancia = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapDzMedia = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapQtRepetidas = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapQtPenRepetidas = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapQtAcertos = new TreeMap<Integer, Integer>();

    /*
     * Metricas dos ciclos para 15 dezenas sorteadas e 25 dezenas da loteria.
     */

    public Map<Integer, Integer> mapCiclo15 = new TreeMap<Integer, Integer>();

    public Map<Integer, Integer> mapCiclo25 = new TreeMap<Integer, Integer>();

    /*
     * Metricas das distribuicoes das dezenas sorteadas.
     */

    public Map<String, Integer> mapDistLinhas = new TreeMap<String, Integer>();

    public Map<String, Integer> mapDistColunas = new TreeMap<String, Integer>();

    public Map<String, Integer> mapDistTotal = new TreeMap<String, Integer>();

    /*
     * Estatisticas diversas.
     */

    // composicao do ciclo de repeticao das dezena.
    public int[][][] matrizCiclos = new int[100][][]; // serao 100 ciclos, de 1 a 100.

    /*
     *
     */
    public LoteriaBean() {
        super();

    }

    /**
     * Define um novo valor para concursos.
     *
     * @param concursos O novo valor para concursos.
     *
     */
    public void setConcursos(final ConcursoBean[] p_concursos) {
        this.concursos = p_concursos;

        this.lenConcursos = isEmpty(this.concursos) ? 0 : this.concursos.length;
    }

    // --- INCREMENTO DO VALOR DE ACORDO COM A CHAVE --------------------------

    public int incQtInferior(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapQtInferior.containsKey(p_key)) {
            val = mapQtInferior.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapQtInferior.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incQtPar(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapQtPar.containsKey(p_key)) {
            val = mapQtPar.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapQtPar.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incQtPrimo(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapQtPrimo.containsKey(p_key)) {
            val = mapQtPrimo.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapQtPrimo.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incDzSequencia(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapDzSequencia.containsKey(p_key)) {
            val = mapDzSequencia.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapDzSequencia.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incDzSeguidas(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapDzSeguidas.containsKey(p_key)) {
            val = mapDzSeguidas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapDzSeguidas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incDzDistancia(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapDzDistancia.containsKey(p_key)) {
            val = mapDzDistancia.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapDzDistancia.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incDzMedia(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapDzMedia.containsKey(p_key)) {
            val = mapDzMedia.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapDzMedia.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incQtRepetidas(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapQtRepetidas.containsKey(p_key)) {
            val = mapQtRepetidas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapQtRepetidas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incQtPenRepetidas(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapQtPenRepetidas.containsKey(p_key)) {
            val = mapQtPenRepetidas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapQtPenRepetidas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incQtAcertos(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapQtAcertos.containsKey(p_key)) {
            val = mapQtAcertos.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapQtAcertos.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incCiclo15(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapCiclo15.containsKey(p_key)) {
            val = mapCiclo15.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapCiclo15.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incCiclo25(final int p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapCiclo25.containsKey(p_key)) {
            val = mapCiclo25.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapCiclo25.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incDistLinhas(final String p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapDistLinhas.containsKey(p_key)) {
            val = mapDistLinhas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapDistLinhas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incDistColunas(final String p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapDistColunas.containsKey(p_key)) {
            val = mapDistColunas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapDistColunas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    // --- OBTEM MAIOR VALOR (MAXIMO) DO CONJUNTO DE MEDIDAS ------------------

    public int maxQtInferior() {
        int max = 0;
        for (int val : mapQtInferior.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxQtPar() {
        int max = 0;
        for (int val : mapQtPar.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxQtPrimo() {
        int max = 0;
        for (int val : mapQtPrimo.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxDzSequencia() {
        int max = 0;
        for (int val : mapDzSequencia.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxDzSeguidas() {
        int max = 0;
        for (int val : mapDzSeguidas.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxDzDistancia() {
        int max = 0;
        for (int val : mapDzDistancia.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxDzMedia() {
        int max = 0;
        for (int val : mapDzMedia.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxQtRepetidas() {
        int max = 0;
        for (int val : mapQtRepetidas.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxQtPenRepetidas() {
        int max = 0;
        for (int val : mapQtPenRepetidas.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxQtAcertos() {
        int max = 0;
        for (int val : mapQtAcertos.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxCiclo15() {
        int max = 0;
        for (int val : mapCiclo15.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxCiclo25() {
        int max = 0;
        for (int val : mapCiclo25.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxDistLinhas() {
        int max = 0;
        for (int val : mapDistLinhas.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    public int maxDistColunas() {
        int max = 0;
        for (int val : mapDistColunas.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    // --- OBTEM METRICAS A PARTIR DO CONJUNTO DE CONCURSOS -------------------

    /*
     * identifica quantas dezenas do array fornecido se repetiram no ultimo concurso.
     * o array p_dezenas NAO precisa estar ordenado.
     */
    public int countUltRepetidas(final int[] p_dezenas) {
        // eh preciso ao menos 1 concurso para se considerar um ultimo concurso.
        if ($.isEmpty(p_dezenas) || this.lenConcursos < 1) return 0;

        // para computar as dezenas repetidas, precisa comparar com o ultimo concurso.
        final ConcursoBean ultimoStruct = this.concursos[this.lenConcursos - 1];

        // varre as dezenas do parametro e verifica quantas sairam no ultimo concurso.
        final int qtRepetidas = Lot.countRepetidas(p_dezenas, ultimoStruct.ordenadas);

        return qtRepetidas; // retorna o numero de dezenas do array que se repetiram no ultimo concurso.
    }

    /*
     * identifica quais dezenas do array fornecido se repetiram no ultimo concurso, retornando um
     * novo array com estas dezenas repetidas.
     * o array p_dezenas NAO precisa estar ordenado.
     */
    public int[] getUltRepetidas(final int[] p_dezenas) {
        final int lenDezenas = (p_dezenas == null) ? 0 : p_dezenas.length;
        // eh preciso ao menos 1 concurso para se considerar um ultimo concurso.
        if (lenDezenas == 0 || this.lenConcursos < 1) return null;

        // para computar as dezenas repetidas, precisa comparar com o ultimo concurso.
        final ConcursoBean ultimoConcurso = this.concursos[this.lenConcursos - 1];

        // varre as dezenas do parametro e verifica quantas sairam no ultimo concurso.
        final int[] repetidas = new int[lenDezenas]; // podem haver no maximo todas as dezenas repetidas.
        int qtRepetidas = 0; // contabiliza as dezenas repetidas e tambem o indice do novo array.
        for (int dez : p_dezenas) {
            // verifica se a dezena do array foi sorteada no ultimo concurso.
            if (Arrays.binarySearch(ultimoConcurso.ordenadas, dez) > -1) {
                repetidas[qtRepetidas++] = dez; // mais uma dezena que se repetiu entre os arrays.
            }
        }

        // verifica quantas dezenas foram repetidas, para retornar um array no tamanho correspondente.
        return (qtRepetidas == lenDezenas) ? repetidas // se repetiram todas as dezenas, entao o array final esta pronto.
            : Arrays.copyOf(repetidas, qtRepetidas); // retorna novo array com o numero exato de dezenas repetidas:
    }

    /*
     * identifica quantas dezenas do array fornecido se repetiram no penultimo concurso.
     * o array p_dezenas NAO precisa estar ordenado.
     */
    public int countPenRepetidas(final int[] p_dezenas) {
        // eh preciso ao menos 2 concursos para se considerar um penultimo concurso.
        if ($.isEmpty(p_dezenas) || this.lenConcursos < 2) return 0;

        // para computar as dezenas repetidas, precisa comparar com o penultimo concurso.
        final ConcursoBean penultimoConcurso = this.concursos[this.lenConcursos - 2];

        // varre as dezenas do parametro e verifica quantas sairam no penultimo concurso.
        final int qtRepetidas = Lot.countRepetidas(p_dezenas, penultimoConcurso.ordenadas);

        return qtRepetidas; // retorna o numero de dezenas do array que se repetiram no penultimo concurso.
    }


    /*
     * identifica quais dezenas do array fornecido se repetiram no penultimo concurso, retornando um
     * novo array com estas dezenas repetidas.
     * o array p_dezenas NAO precisa estar ordenado.
     */
    public int[] getPenRepetidas(final int[] p_dezenas) {
        final int lenDezenas = (p_dezenas == null) ? 0 : p_dezenas.length;
        // eh preciso ao menos 2 concursos para se considerar um penultimo concurso.
        if (lenDezenas == 0 || this.lenConcursos < 2) return null;

        // para computar as dezenas repetidas, precisa comparar com o penultimo concurso.
        final ConcursoBean penultimoConcurso = this.concursos[this.lenConcursos - 2];

        // varre as dezenas do parametro e verifica quantas sairam no penultimo concurso.
        final int[] repetidas = new int[lenDezenas]; // podem haver no maximo todas as dezenas repetidas.
        int qtRepetidas = 0; // contabiliza as dezenas repetidas e tambem o indice do novo array.
        for (int dez : p_dezenas) {
            // verifica se a dezena do array foi sorteada no penultimo concurso.
            if (Arrays.binarySearch(penultimoConcurso.ordenadas, dez) > -1) {
                repetidas[qtRepetidas++] = dez; // mais uma dezena que se repetiu entre os arrays.
            }
        }

        // verifica quantas dezenas foram repetidas, para retornar um array no tamanho correspondente.
        return (qtRepetidas == lenDezenas) ? repetidas // se repetiram todas as dezenas, entao o array final esta pronto.
            : Arrays.copyOf(repetidas, qtRepetidas); // retorna novo array com o numero exato de dezenas repetidas:
    }

    /*
     * contabiliza o maximo de acertos das dezenas do array fornecido nos concursos.
     * o array p_dezenas NAO precisa estar ordenado.
     */
    public int countMaxAcertos(final int[] p_dezenas) {
        if ($.isEmpty(p_dezenas) || this.lenConcursos == 0) return 0;

        int acertos, maxAcertos = 0; // registro o maximo numero de dezenas que se repetiram.

        // para computar a maxima pontuacao de dezenas "acertadas", precisa comparar com todos os concursos anteriores.
        for (ConcursoBean concurso : this.concursos) {

            // varre as dezenas do parametro e verifica quantas sairam no concurso.
            acertos = Lot.countRepetidas(p_dezenas, concurso.ordenadas);

            // pega o maior numero de acertos:
            if (acertos > maxAcertos) maxAcertos = acertos;
        }

        return maxAcertos; // retorna o maximo de dezenas do array que se repetiram nos concursos.
    }

    /*
     * contabiliza o numero de concursos necessarios para fechar o ciclo das 15 dezenas fornecidas.
     * o array p_dezenas precisa estar ordenado.
     */
    public int countCiclo_15(final int[] p_dezenas) {
        if ($.isEmpty(p_dezenas) || this.lenConcursos == 0) return 0;

        int ciclo_15 = 0; // contabiliza o numero de concursos para completar as 15 dezenas.

        // utiliza copia da lista de dezenas fornecidas, para nao alterar seu conteudo.
        final int[] dezenas_15 = Arrays.copyOf(p_dezenas, 15);
        int cont15Dezenas = 0; // contabiliza as dezenas correspondentes (match).

        // a partir do ultimo concurso, percorre os resultados para fechar o ciclo das 15 dezenas.
        // pula fora quando encontrar as 15 dezenas (do array) nos concursos.
        for (int j = this.lenConcursos - 1; (j >= 0 && cont15Dezenas < 15); j--) {
            // estrutura do concurso a ser manipulado.
            final ConcursoBean concurso = this.concursos[j];

            // verifica quais dezenas do array batem com o concurso corrente.
            for (int dez : concurso.ordenadas) {
                final int idxDez = Arrays.binarySearch(p_dezenas, dez);
                if (idxDez > -1) { // esta dezena do concurso corrente esta presente array fornecido.
                    // se ainda nao computou a dezena entre as 15 do array fornecido:
                    if (dezenas_15[idxDez] > 0) {
                        dezenas_15[idxDez] = 0; // anula a dezena para nao contabilizar em outros concursos.
                        cont15Dezenas++; // quando chegar em 15 entao encontrou todas as dezenas.
                    }
                }
            }

            ciclo_15++; // mais um concurso a ser processado, ate fechar as 15 dezenas.
        }

        // retorna o numero de concursos necessarios para fechar o ciclo das 15 dezenas do parametro.
        return ciclo_15;
    }

    /*
     * contabiliza o numero de concursos necessarios para fechar o ciclo das 25 dezenas da loteria,
     * considerando o array fornecido como ponto de partida.
     * o array p_dezenas precisa estar ordenado.
     */
    public int countCiclo_25(final int[] p_dezenas) {
        if ($.isEmpty(p_dezenas) || this.lenConcursos == 0) return 0;

        int ciclo_25 = 0; // contabiliza o numero de concursos para completar as 25 dezenas.

        // utiliza lista completa de possiveis dezenas da loteria.
        final int[] dezenas_25 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        int cont25Dezenas = 0; // contabiliza as dezenas correspondentes (match).

        // verifica quais dezenas do array batem com alguma das 25 dezenzas da loteria ainda nao computadas.
        for (int dez : p_dezenas) { // varre todas as dezenas do array fornecido.
            int idxDezLoto = Arrays.binarySearch(LoteriaBean.DEZENAS, dez);
            if (idxDezLoto > -1) {
                if (dezenas_25[idxDezLoto] > 0) { // se a dezena ainda nao foi computada:
                    dezenas_25[idxDezLoto] = 0; // anula a dezena para nao contabilizar nos concursos.
                    cont25Dezenas++; // quando chegar em 25 entao encontrou todas as dezenas.
                }
            }
        }

        // o array fornecido deve ser considerado como primeiro concurso no ciclo das 25 dezenas.
        ciclo_25++;

        // pula fora quando encontrar as 25 dezenas nos concursos existentes.
        for (int j = lenConcursos - 1; (j >= 0 && cont25Dezenas < 25); j--) {
            // / estrutura do concurso a ser manipulado.
            final ConcursoBean concurso = this.concursos[j];

            // verifica quais dezenas do concurso batem com alguma das 25 dezenzas da loteria ainda nao computadas.
            for (int dez : concurso.ordenadas) { // indice utilizado no array de dezenas do concurso anterior.
                final int idxDezLoto = Arrays.binarySearch(LoteriaBean.DEZENAS, dez);
                if (idxDezLoto > -1) { // esta dezena do concurso anterior foi sorteada no concurso atual.
                    if (dezenas_25[idxDezLoto] > 0) { // se a dezena ainda nao foi computada:
                        dezenas_25[idxDezLoto] = 0; // anula a dezena para nao contabilizar em outros concursos.
                        cont25Dezenas++; // quando chegar em 25 entao encontrou todas as dezenas.
                    }
                }
            }

            ciclo_25++; // mais um concurso a ser processado, ate fechar as 25 dezenas.
        }

        // retorna o numero de concursos necessarios para fechar o ciclo das 25 dezenas da loteria.
        return ciclo_25;
    }
}
