package filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entity.ApostaBean;
import entity.ConcursoBean;
import entity.LoteriaBean;
import util.$;
import util.Lot;

/**
 * Classe <code>ParetoStrategy</code>.
 *
 * @category strategy.
 */
public class ParetoFilter implements Runnable {

    // 6 dezenas = 74.500 jogos
    // 7 dezenas = 35.255 jogos
    // 8 dezenas = 80%: 15.604 jogos // 90%: 18.472 jogos
    // 9 dezenas = 7.705 jogos
    // 10 dezenas = 2,875 jogos
    // 11 dezenas = 947 jogos
    // static final int[] FIXAS = {1, 2, 3, 5, 6, 11, 15, 18, 22, 23, 25};

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public ParetoFilter(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    private Map<Integer, Integer> getMapIntPareto(final Map<Integer, Integer> p_map) {
        Map<Integer, Integer> mapPareto = new HashMap<Integer, Integer>();

        List<Map.Entry<Integer, Integer>> list = $.sortByValue(p_map);
        float percAcum = 0f;
        for (int i = list.size() - 1; i >= 0 && percAcum < 88f; i--) {
            mapPareto.put(list.get(i).getKey(), list.get(i).getValue());

            percAcum += (list.get(i).getValue() * 100.0f) / this.loteria.lenConcursos;
        }

        // retorna p_map filtrado com valores da curva A (Pareto).
        return mapPareto;
    }

    private Map<String, Integer> getMapStrPareto(final Map<String, Integer> p_map) {
        Map<String, Integer> mapPareto = new HashMap<String, Integer>();

        List<Map.Entry<String, Integer>> list = $.sortByValue(p_map);
        float percAcum = 0f;
        for (int i = list.size() - 1; i >= 0 && percAcum < 88f; i--) {
            mapPareto.put(list.get(i).getKey(), list.get(i).getValue());

            percAcum += (list.get(i).getValue() * 100.0f) / this.loteria.lenConcursos;
        }

        // retorna p_map filtrado com valores da curva A (Pareto).
        return mapPareto;
    }

    /*
     *
     */
    @Override
    public void run() {
        // obtem o ultimo concurso para obter as estatisticas da loteria:
        if (this.loteria.lenConcursos == 0) return;

        /*
         * Identifica o maximo valor de cada metrica da loteria:
         */

        // quantidade de numeros inferiores (max 13).
        int contQtInferior;
        boolean isQtInferiorOK;
        Map<Integer, Integer> mapQtInferior = getMapIntPareto(this.loteria.mapQtInferior);

        // quantidade de dezenas pares (max 12)
        int contQtPar;
        boolean isQtParOK;
        Map<Integer, Integer> mapQtPar = getMapIntPareto(this.loteria.mapQtPar);

        // quantidade de dezenas primos.
        int contQtPrimo;
        boolean isQtPrimoOK;
        Map<Integer, Integer> mapQtPrimo = getMapIntPareto(this.loteria.mapQtPrimo);

        // quantidade de dezenas em sequencia.
        int contDzSequencia;
        boolean isDzSequenciaOK;
        Map<Integer, Integer> mapDzSequencia = getMapIntPareto(this.loteria.mapDzSequencia);

        // quantidade maxima de dezenas em seguida.
        int contDzSeguidas;
        boolean isDzSeguidasOK;
        Map<Integer, Integer> mapDzSeguidas = getMapIntPareto(this.loteria.mapDzSeguidas);

        // medida da distancia entre as dezenas.
        int contDzDistancia;
        boolean isDzDistanciaOK;
        Map<Integer, Integer> mapDzDistancia = getMapIntPareto(this.loteria.mapDzDistancia);

        // medida da media aritmetica entre as dezenas.
        int contDzMedia;
        boolean isDzMediaOK;
        Map<Integer, Integer> mapDzMedia = getMapIntPareto(this.loteria.mapDzMedia);

        // quantidade de dezenas repetidas em relacao ao ultimo concurso.
        int contQtRepetidas;
        boolean isQtRepetidasOK;
        Map<Integer, Integer> mapQtRepetidas = getMapIntPareto(this.loteria.mapQtRepetidas);

        // quantidade de dezenas repetidas em relacao ao penultimo concurso.
        int contQtPenRepetidas;
        boolean isQtPenRepetidasOK;
        Map<Integer, Integer> mapQtPenRepetidas = getMapIntPareto(this.loteria.mapQtPenRepetidas);

        // quantidade de acertos em relacao a todos os outros concursos anteriores.
        int contQtAcertos;
        boolean isQtAcertosOK;
        Map<Integer, Integer> mapQtAcertos = getMapIntPareto(this.loteria.mapQtAcertos);

        // quantidade de ciclos de 15 dezenas sorteadas, em relacao a todos os outros concursos anteriores.
        int contCiclo15;
        boolean isCiclo15OK;
        Map<Integer, Integer> mapCiclo15 = getMapIntPareto(this.loteria.mapCiclo15);

        // quantidade de ciclos de 25 dezenas da loteria, em relacao a todos os outros concursos anteriores.
        int contCiclo25;
        boolean isCiclo25OK;
        Map<Integer, Integer> mapCiclo25 = getMapIntPareto(this.loteria.mapCiclo25);

        // quantidade de distribuicoes de linhas em relacao a todos os outros concursos anteriores.
        int contDistLinhas;
        boolean isDistLinhasOK;
        Map<String, Integer> mapDistLinhas = getMapStrPareto(this.loteria.mapDistLinhas);

        // quantidade de distribuicoes de colunas em relacao a todos os outros concursos anteriores.
        int contDistColunas;
        boolean isDistColunasOK;
        Map<String, Integer> mapDistColunas = getMapStrPareto(this.loteria.mapDistColunas);

        /*
         *
         */

        // 3.702.530 [ 2 6 7 8 12 14 15 16 17 20 21 23 ] .:. [9=125, 6=198, 8=274, 7=441] [4=13, 10=27, 5=95, 9=125, 6=198, 8=274, 7=441]
        int qtPar441;
        boolean isPar441OK;
        final int[] par441 = {2, 6, 7, 8, 12, 14, 15, 16, 17, 20, 21, 23};
        Map<Integer, Integer> mapPar441 = new HashMap<Integer, Integer>();
        mapPar441.put(5, 95);
        mapPar441.put(6, 198);
        mapPar441.put(7, 441);
        mapPar441.put(8, 274);
        mapPar441.put(9, 125);

        // 2.583.407 [ 2 3 4 5 8 9 12 13 14 16 17 19 ] .:. [6=219, 8=289, 7=442] [3=1, 11=4, 4=11, 10=28, 5=73, 9=106, 6=219, 8=289, 7=442]
        int qtPar442;
        boolean isPar442OK;
        final int[] par442 = {2, 3, 4, 5, 8, 9, 12, 13, 14, 16, 17, 19};
        Map<Integer, Integer> mapPar442 = new HashMap<Integer, Integer>();
        mapPar442.put(5, 73);
        mapPar442.put(6, 219);
        mapPar442.put(7, 442);
        mapPar442.put(8, 289);
        mapPar442.put(9, 106);

        // 3.492.310 [ 2 4 10 14 15 16 17 18 20 22 23 25 ] .:. [6=222, 8=275, 7=444] [3=1, 11=3, 4=13, 10=26, 5=64, 9=125, 6=222, 8=275,
        // 7=444]
        int qtPar444;
        boolean isPar444OK;
        final int[] par444 = {2, 4, 10, 14, 15, 16, 17, 18, 20, 22, 23, 25};
        Map<Integer, Integer> mapPar444 = new HashMap<Integer, Integer>();
        mapPar444.put(5, 64);
        mapPar444.put(6, 222);
        mapPar444.put(7, 275);
        mapPar444.put(8, 444);
        mapPar444.put(9, 125);

        // 3.144.223 [ 2 4 5 6 7 8 9 11 12 14 15 18 ] .:. [9=129, 6=225, 8=259, 7=446] [3=1, 11=4, 4=14, 10=26, 5=69, 9=129, 6=225, 8=259,
        // 7=446]
        int qtPar446;
        boolean isPar446OK;
        final int[] par446 = {2, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 18};
        Map<Integer, Integer> mapPar446 = new HashMap<Integer, Integer>();
        mapPar446.put(5, 69);
        mapPar446.put(6, 225);
        mapPar446.put(7, 446);
        mapPar446.put(8, 259);
        mapPar446.put(9, 129);

        /*
         *
         */

        Map<Integer, Integer> mapQtMetrica = new TreeMap<Integer, Integer>();
        Map<Integer, Integer> mapQtCiclo = new TreeMap<Integer, Integer>();
        Map<Integer, Integer> mapQtDist = new TreeMap<Integer, Integer>();

        Map<String, Integer> mapQtComb = new TreeMap<String, Integer>();

        /*
         * Lista os concursos anteriores, exibindo as metricas mais frequentes em destaque.
         */

        $.log("\n" + $.repeat("-", 182) + "\n");

        // imprime o cabecalho da listagem primeiro, antes de varrer os concursos:
        $.log("\n      CONCURSO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET PENREP  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
        $.log($.repeat("-", 182));

        // percorre os resultados a partir do ultimo concurso, para exibir em lista a partir do mais recente.
        int contNaoAtende = 0;
        boolean isTudoOk;

        contQtInferior = 0;
        contQtPar = 0;
        contQtPrimo = 0;
        contDzSequencia = 0;
        contDzSeguidas = 0;
        contDzDistancia = 0;
        contDzMedia = 0;
        contQtRepetidas = 0;
        contQtPenRepetidas = 0;
        contQtAcertos = 0;

        contCiclo15 = 0;
        contCiclo25 = 0;

        contDistLinhas = 0;
        contDistColunas = 0;

        int contMetrica, contCiclo, contDist;
        String comb;

        for (int i = this.loteria.lenConcursos - 1; i >= 0; i--) {
            // a cada 110 concursos, imprime novamente o cabecalho para meior legibilidade.
            if (i % 50 == 0 && i > 0) {
                $.log("\n      CONCURSO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET PENREP  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
                $.log($.repeat("-", 182));
            }

            // concurso a ser processado.
            ConcursoBean concurso = this.loteria.concursos[i];

            // verifica se o concurso possui alguma metrica na faixa satisfatoria:
            isQtInferiorOK = mapQtInferior.containsKey(concurso.qtInferior);
            isQtParOK = mapQtPar.containsKey(concurso.qtPar);
            isQtPrimoOK = mapQtPrimo.containsKey(concurso.qtPrimo);
            isDzSequenciaOK = mapDzSequencia.containsKey(concurso.dzSequencia);
            isDzSeguidasOK = mapDzSeguidas.containsKey(concurso.dzSeguidas);
            isDzDistanciaOK = mapDzDistancia.containsKey(concurso.dzDistancia);
            isDzMediaOK = mapDzMedia.containsKey(concurso.dzMedia);
            isQtRepetidasOK = mapQtRepetidas.containsKey(concurso.qtRepetidas);
            isQtPenRepetidasOK = mapQtPenRepetidas.containsKey(concurso.qtPenRepetidas);
            isQtAcertosOK = mapQtAcertos.containsKey(concurso.qtAcertos);

            isCiclo15OK = mapCiclo15.containsKey(concurso.ciclo15);
            isCiclo25OK = mapCiclo25.containsKey(concurso.ciclo25);

            isDistLinhasOK = mapDistLinhas.containsKey(concurso.distLinhas);
            isDistColunasOK = mapDistColunas.containsKey(concurso.distColunas);

            // contabiliza cada metrica no conjunto de concursos:
            contMetrica = 0;
            if (isQtInferiorOK) {
                contMetrica++;
                contQtInferior++;
            }
            if (isQtParOK) {
                contMetrica++;
                contQtPar++;
            }
            if (isQtPrimoOK) {
                contMetrica++;
                contQtPrimo++;
            }
            if (isDzSequenciaOK) {
                contMetrica++;
                contDzSequencia++;
            }
            if (isDzSeguidasOK) {
                contMetrica++;
                contDzSeguidas++;
            }
            if (isDzDistanciaOK) {
                contMetrica++;
                contDzDistancia++;
            }
            if (isDzMediaOK) {
                contMetrica++;
                contDzMedia++;
            }
            if (isQtRepetidasOK) {
                contMetrica++;
                contQtRepetidas++;
            }
            if (isQtPenRepetidasOK) {
                contMetrica++;
                contQtPenRepetidas++;
            }
            if (isQtAcertosOK) {
                contMetrica++;
                contQtAcertos++;
            }

            contCiclo = 0;
            if (isCiclo15OK) {
                contCiclo++;
                contCiclo15++;
            }
            if (isCiclo25OK) {
                contCiclo++;
                contCiclo25++;
            }

            contDist = 0;
            if (isDistLinhasOK) {
                contDist++;
                contDistLinhas++;
            }
            if (isDistColunasOK) {
                contDist++;
                contDistColunas++;
            }

            int val = 0;
            if (mapQtMetrica.containsKey(contMetrica)) {
                val = mapQtMetrica.get(contMetrica);
            }
            mapQtMetrica.put(contMetrica, ++val);

            val = 0;
            if (mapQtCiclo.containsKey(contCiclo)) {
                val = mapQtCiclo.get(contCiclo);
            }
            mapQtCiclo.put(contCiclo, ++val);

            val = 0;
            if (mapQtDist.containsKey(contDist)) {
                val = mapQtDist.get(contDist);
            }
            mapQtDist.put(contDist, ++val);

            comb = Lot.formatDez(contMetrica, 2) + "." + contCiclo + "." + contDist;
            val = 0;
            if (mapQtComb.containsKey(comb)) {
                val = mapQtComb.get(comb);
            }
            mapQtComb.put(comb, ++val);

            // se o concurso atende a todos os filtros de metricas:
            isTudoOk = isQtInferiorOK && isQtParOK && isQtPrimoOK && isDzSequenciaOK && isDzSeguidasOK //
                       && isDzDistanciaOK && isDzMediaOK && isQtRepetidasOK && isQtPenRepetidasOK && isQtAcertosOK //
                       && isCiclo15OK && isCiclo25OK && isDistLinhasOK && isDistColunasOK;

            // exibe aqueles concursos que atendem a pelo menos uma das metricas:
            if (isQtInferiorOK //
                || isQtParOK //
                || isQtPrimoOK //
                || isDzSequenciaOK //
                || isDzSeguidasOK //
                || isDzDistanciaOK //
                || isDzMediaOK //
                || isQtRepetidasOK //
                || isQtPenRepetidasOK //
                || isQtAcertosOK //
                || isCiclo15OK //
                || isCiclo25OK //
                || isDistLinhasOK //
                || isDistColunasOK) {
                // se ok, imprime o concurso:
                $.log("{0}   {1}  {2}   {3}{4}  {5}{6}  {7}{8}  {9}{10}  {11}{12}  {13}{14}  {15}{16}  {17}{18}  {19}{20}  {21}{22}     {23}{24}    {25}{26}     {27}{28}    {29}{30}", //
                      comb + (isTudoOk ? "*" : " "), // 0
                      Lot.formatDez(concurso.id, 4), // 1
                      Lot.formatDezenas(concurso.ordenadas, 2), // 2

                      Lot.formatDez(concurso.qtInferior, 4), isQtInferiorOK ? "*" : " ", // 3,4
                      Lot.formatDez(concurso.qtPar, 4), isQtParOK ? "*" : " ", // 5,6
                      Lot.formatDez(concurso.qtPrimo, 4), isQtPrimoOK ? "*" : " ", // 7,8
                      Lot.formatDez(concurso.dzSequencia, 4), isDzSequenciaOK ? "*" : " ", // 9,10
                      Lot.formatDez(concurso.dzSeguidas, 4), isDzSeguidasOK ? "*" : " ", // 11,12
                      Lot.formatDez(concurso.dzDistancia, 4), isDzDistanciaOK ? "*" : " ", // 13,14
                      Lot.formatDez(concurso.dzMedia, 4), isDzMediaOK ? "*" : " ", // 15,16
                      Lot.formatDez(concurso.qtRepetidas, 4), isQtRepetidasOK ? "*" : " ", // 17,18
                      Lot.formatDez(concurso.qtPenRepetidas, 4), isQtPenRepetidasOK ? "*" : " ", // 19,20
                      Lot.formatDez(concurso.qtAcertos, 4), isQtAcertosOK ? "*" : " ", // 21,22

                      Lot.formatDez(concurso.ciclo15, 4), isCiclo15OK ? "*" : " ", // 23,24
                      Lot.formatDez(concurso.ciclo25, 4), isCiclo25OK ? "*" : " ", // 25,26

                      concurso.distLinhas, isDistLinhasOK ? "*" : " ", // 27,28
                      concurso.distColunas, isDistColunasOK ? "*" : " "); // 29,30

            } else { // se nao esta na faixa, exibe lacuna.
                $.log("............");
                contNaoAtende++;
            }
        }

        $.log("\n" + $.repeat("-", 182) + "\n");

        $.log("> TOTAL DE CONCURSOS QUE NAO ATENDEM AS METRICAS:  {0}", contNaoAtende);

        /*
         * Exibe as medidas das metricas que serao consideradas pelo FATOR.
         */

        $.log("\n" + $.repeat("-", 182) + "\n");

        $.log(" > mapQtInferior: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapQtInferior), contQtInferior, (contQtInferior * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtPar: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapQtPar), contQtPar, (contQtPar * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtPrimo: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapQtPrimo), contQtPrimo, (contQtPrimo * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzSequencia: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapDzSequencia), contDzSequencia, (contDzSequencia * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzSeguidas: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapDzSeguidas), contDzSeguidas, (contDzSeguidas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzDistancia: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapDzDistancia), contDzDistancia, (contDzDistancia * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzMedia: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapDzMedia), contDzMedia, (contDzMedia * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtRepetidas: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapQtRepetidas), contQtRepetidas, (contQtRepetidas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtPenRepetidas: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapQtPenRepetidas), contQtPenRepetidas, (contQtPenRepetidas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtAcertos: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapQtAcertos), contQtAcertos, (contQtAcertos * 100) / this.loteria.lenConcursos);

        $.log("\n > mapCiclo15: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapCiclo15), contCiclo15, (contCiclo15 * 100) / this.loteria.lenConcursos);

        $.log("\n > mapCiclo25: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapCiclo25), contCiclo25, (contCiclo25 * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDistLinhas: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapDistLinhas), contDistLinhas, (contDistLinhas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDistColunas: {0}  .:.  Cont: {1} == {2}%", //
              $.sortByValue(mapDistColunas), contDistColunas, (contDistColunas * 100) / this.loteria.lenConcursos);

        $.log("\n" + $.repeat("-", 182) + "\n");

        $.log(" > mapQtMetrica: {0}   .:.  Pareto: {1}", //
              $.sortByValue(mapQtMetrica), $.sortByValue(getMapIntPareto(mapQtMetrica)));

        $.log("\n > mapQtCiclo: {0}   .:.  Pareto: {1}", //
              $.sortByValue(mapQtCiclo), $.sortByValue(getMapIntPareto(mapQtCiclo)));

        $.log("\n > mapQtDist: {0}   .:.  Pareto: {1}", //
              $.sortByValue(mapQtDist), $.sortByValue(getMapIntPareto(mapQtDist)));

        $.log("\n" + $.repeat("-", 182) + "\n");

        $.log(" > mapQtComb: {0} \n\t Pareto: {1}", //
              $.sortByValue(mapQtComb), $.sortByValue(getMapStrPareto(mapQtComb)));

        $.log("\n" + $.repeat("-", 182) + "\n");

        /*
         * Identifica as combinacoes de dezenas que atendem as metricas em destaque.
         */

        // identifica a curva A das metricas para o teste de pareto das apostas:
        mapQtMetrica = getMapIntPareto(mapQtMetrica);
        mapQtCiclo = getMapIntPareto(mapQtCiclo);
        mapQtDist = getMapIntPareto(mapQtDist);

        // gera todas as 3.268.760 combinacoes para a Lotofacil.
        int[][] jogos = Lot.geraCombinacoes(25, 15); // total de 25 dezenas, 15 dezenas por combinacao.
        final int lenJogos = jogos.length;
        $.log("\nTotal de {0} combinacoes de jogos gerados para a Lotofacil.\n", lenJogos);

        $.log($.repeat("-", 182));

        // imprime o cabecalho da listagem primeiro, antes de varrer os concursos:
        $.log("\n          JOGO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET PENREP  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
        $.log($.repeat("-", 182));

        // percorre todos os jogos para calcular as metricas de cada jogo.
        int quoc, numeroAposta, contAtende = numeroAposta = 5; // total de jogos que atendem ao filtro das metricas.
        final int fator_10 = 327000; // ao dividir o total de jogos por este fator, resulta em 0 a 9 (floor).

        // prepara regras para sorteio das apostas:
        RegraStruct.next(); // posiciona current na proxima regra valida.
        for (int i = 0; i < lenJogos; i++) {
            // calcula o quociente do fator 10:
            quoc = Math.floorDiv(i, fator_10); // retorna de 0 a 9.
            if (quoc < numeroAposta) continue; // se ja gerou aposta para a posicao quociente, entao pula ate chegar no proximo quadrante.

            // gera objeto ApostaBean para processamento das metricas do jogo:
            ApostaBean aposta = ApostaBean.parseAposta(i + 1, jogos[i], this.loteria);

            // de posse das metricas, verifica se o jogo atende ao filtro.
            isQtInferiorOK = mapQtInferior.containsKey(aposta.qtInferior);
            isQtParOK = mapQtPar.containsKey(aposta.qtPar);
            isQtPrimoOK = mapQtPrimo.containsKey(aposta.qtPrimo);
            isDzSequenciaOK = mapDzSequencia.containsKey(aposta.dzSequencia);
            isDzSeguidasOK = mapDzSeguidas.containsKey(aposta.dzSeguidas);
            isDzDistanciaOK = mapDzDistancia.containsKey(aposta.dzDistancia);
            isDzMediaOK = mapDzMedia.containsKey(aposta.dzMedia);
            isQtRepetidasOK = mapQtRepetidas.containsKey(aposta.qtRepetidas);
            isQtPenRepetidasOK = mapQtPenRepetidas.containsKey(aposta.qtPenRepetidas);
            isQtAcertosOK = mapQtAcertos.containsKey(aposta.qtAcertos);

            isCiclo15OK = mapCiclo15.containsKey(aposta.ciclo15);
            isCiclo25OK = mapCiclo25.containsKey(aposta.ciclo25);

            isDistLinhasOK = mapDistLinhas.containsKey(aposta.distLinhas);
            isDistColunasOK = mapDistColunas.containsKey(aposta.distColunas);

            //
            qtPar441 = Lot.countRepetidas(par441, aposta.ordenadas);
            isPar441OK = mapPar441.containsKey(qtPar441);

            qtPar442 = Lot.countRepetidas(par442, aposta.ordenadas);
            isPar442OK = mapPar442.containsKey(qtPar442);

            qtPar444 = Lot.countRepetidas(par444, aposta.ordenadas);
            isPar444OK = mapPar444.containsKey(qtPar444);

            qtPar446 = Lot.countRepetidas(par446, aposta.ordenadas);
            isPar446OK = mapPar446.containsKey(qtPar446);

            // contabiliza cada metrica no conjunto de concursos:
            contMetrica = 0;
            if (isQtInferiorOK) contMetrica++;
            if (isQtParOK) contMetrica++;
            if (isQtPrimoOK) contMetrica++;
            if (isDzSequenciaOK) contMetrica++;
            if (isDzSeguidasOK) contMetrica++;
            if (isDzDistanciaOK) contMetrica++;
            if (isDzMediaOK) contMetrica++;
            if (isQtRepetidasOK) contMetrica++;
            if (isQtPenRepetidasOK) contMetrica++;
            if (isQtAcertosOK) contMetrica++;

            contCiclo = 0;
            if (isCiclo15OK) contCiclo++;
            if (isCiclo25OK) contCiclo++;

            contDist = 0;
            if (isDistLinhasOK) contDist++;
            if (isDistColunasOK) contDist++;

            int countPar440 = 0;
            if (isPar441OK) countPar440++;
            if (isPar442OK) countPar440++;
            if (isPar444OK) countPar440++;
            if (isPar446OK) countPar440++;

            comb = Lot.formatDez(contMetrica, 2) + "." + contCiclo + "." + contDist;

            /*
             * verifica se a aposta atende as regras da proxima estrutura sorteada aleatoriamente:
             */

            // regra corrente a ser considerada para a proxima aposta:
            RegraStruct regra = RegraStruct.current;

            // verifica se a aposta atende as regras da proxima estrutura sorteada aleatoriamente:
            if (regra.qtMetricas == contMetrica && //
                regra.qtCiclos == contCiclo && //
                regra.qtDist == contDist && //
                regra.qtAcertos == aposta.qtAcertos) {

                RegraStruct.countRandom[quoc]--; // indica quantos numeros aleatorios serao passados:
                if (RegraStruct.countRandom[quoc] > 0) continue; // se ainda nao atingiu a meta de aleatorios, continua.

                // se a aposta atende as metricas, entao regstra nova aposta, avanca nas regras e imprime o volante:
                numeroAposta++;
                RegraStruct.next();

                // se atende ao menos 12 metricas, considera como aposta e imprime o jogo:
                $.log("{0} {1}  {2}   {3}{4}  {5}{6}  {7}{8}  {9}{10}  {11}{12}  {13}{14}  {15}{16}  {17}{18}  {19}{20}  {21}{22}     {23}{24}    {25}{26}     {27}{28}    {29}{30}", //
                      comb, // 0
                      Lot.formatDez(aposta.id, 7), // 1
                      Lot.formatDezenas(aposta.ordenadas, 2), // 2

                      Lot.formatDez(aposta.qtInferior, 4), isQtInferiorOK ? "*" : " ", // 3,4
                      Lot.formatDez(aposta.qtPar, 4), isQtParOK ? "*" : " ", // 5,6
                      Lot.formatDez(aposta.qtPrimo, 4), isQtPrimoOK ? "*" : " ", // 7,8
                      Lot.formatDez(aposta.dzSequencia, 4), isDzSequenciaOK ? "*" : " ", // 9,10
                      Lot.formatDez(aposta.dzSeguidas, 4), isDzSeguidasOK ? "*" : " ", // 11,12
                      Lot.formatDez(aposta.dzDistancia, 4), isDzDistanciaOK ? "*" : " ", // 13,14
                      Lot.formatDez(aposta.dzMedia, 4), isDzMediaOK ? "*" : " ", // 15,16
                      Lot.formatDez(aposta.qtRepetidas, 4), isQtRepetidasOK ? "*" : " ", // 17,18
                      Lot.formatDez(aposta.qtPenRepetidas, 4), isQtPenRepetidasOK ? "*" : " ", // 19,20
                      Lot.formatDez(aposta.qtAcertos, 4), isQtAcertosOK ? "*" : " ", // 21,22

                      Lot.formatDez(aposta.ciclo15, 4), isCiclo15OK ? "*" : " ", // 23,24
                      Lot.formatDez(aposta.ciclo25, 4), isCiclo25OK ? "*" : " ", // 25,26

                      aposta.distLinhas, isDistLinhasOK ? "*" : " ", // 27,28
                      aposta.distColunas, isDistColunasOK ? "*" : " "); // 29,30

                contAtende++; // contabiliza para verificar quantos jogos atendem ao filtro.
//                $.pause(">");

            } else {
//                $.log("i: {0}     quoc: {1}     numeroAposta: {2}     countRandom: {3}", i, quoc, numeroAposta, RegraStruct.countRandom[quoc]);
            }
        }

        $.log("\n" + $.repeat("-", 182) + "\n");

        $.log("> TOTAL DE CONCURSOS QUE ATENDEM AO FILTRO DS METRICAS:  {0}", contAtende);

    }

    // --- Helpers diversos ---------------------------------------------------

    static class RegraStruct {

        // --- PROPRIEDADES ---------------------------------------------------

        final int qtMetricas, qtCiclos, qtDist, qtPar440;

        final int qtAcertos;

        public RegraStruct(final int p_qtMetricas, final int p_qtCiclos, final int p_qtDist, final int p_qtPar440, final int p_qtAcertos) {
            super();

            this.qtMetricas = p_qtMetricas;
            this.qtCiclos = p_qtCiclos;
            this.qtDist = p_qtDist;
            this.qtPar440 = p_qtPar440;
            this.qtAcertos = p_qtAcertos;
        }

        // indica em qual posicao do quadrante do quociente sera considerada a aposta:
//        final static int[] countRandom = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        final static int[] countRandom = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

        // serao sorteadas 10 apostas de forma aleatoria.
        final static RegraStruct[] regras = {new RegraStruct(7, 2, 2, 4, 12), // 0
                                             new RegraStruct(10, 2, 1, 4, 13), // 1
                                             new RegraStruct(9, 1, 2, 3, 12), // 2
                                             new RegraStruct(9, 2, 2, 3, 13), // 3
                                             new RegraStruct(10, 2, 2, 4, 14), // 4
                                             new RegraStruct(10, 2, 1, 4, 13), // 5
                                             new RegraStruct(9, 2, 2, 3, 12), // 6
                                             new RegraStruct(8, 2, 2, 4, 13), // 7
                                             new RegraStruct(10, 2, 1, 2, 12), // 8
                                             new RegraStruct(8, 2, 2, 4, 13)}; // 9

        static RegraStruct current = null;

        static int count = 0;

        static void next() {
            // se ja gerou as 10 regras, entao nao precisa fazer nada.
            if (count >= 10) return;

            // obtem proxima regra de forma aleatoria:
            int idx;
            do {
                idx = $.random(10); // obtem proxima regra aleatoriamente.
            } while (regras[idx] == null);

            current = regras[idx];
            regras[idx] = null; // regra nao mais sera utilizada.

            // sinaliza que obteve uma proxima regra:
            count++;
        }
    }

}
