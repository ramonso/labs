package filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entity.ConcursoBean;
import entity.DezenaBean;
import entity.LoteriaBean;
import util.$;
import util.Lot;

/**
 * Classe <code>ParidadeFilter</code>.
 *
 * @category strategy.
 */
public class ParidadeFilter implements Runnable {

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public ParidadeFilter(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    private Map<Integer, Integer> getMapIntPareto(final Map<Integer, Integer> p_map) {
        Map<Integer, Integer> mapPareto = new HashMap<Integer, Integer>();

        List<Map.Entry<Integer, Integer>> list = $.sortByValue(p_map);
        float percAcum = 0f;
        for (int i = list.size() - 1; i >= 0 && percAcum < 80f; i--) {
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
         * Gera todas as 5.200.300 combinacoes para paridades de 12x13 em 25 dezenas.
         */
        int[][] paridades = Lot.geraCombinacoes(25, 12); // total de 25 dezenas, 12 dezenas por paridade.
        final int lenParidade = paridades.length;
        $.log("\t Total de {0} combinacoes de paridades geradas.", lenParidade);

        $.log("\n" + $.repeat("-", 180) + "\n");

        /*
         * imprime comparativo entre as paridades acima de 440 hits.
         */

        boolean isQtInferiorOK;
        Map<Integer, Integer> mapQtInferior = getMapIntPareto(this.loteria.mapQtInferior);

        // quantidade de dezenas pares (max 12)
        boolean isQtParOK;
        Map<Integer, Integer> mapQtPar = getMapIntPareto(this.loteria.mapQtPar);

        // quantidade de dezenas primos.
        boolean isQtPrimoOK;
        Map<Integer, Integer> mapQtPrimo = getMapIntPareto(this.loteria.mapQtPrimo);

        // 3.702.530 [ 2 6 7 8 12 14 15 16 17 20 21 23 ] .:. [9=125, 6=198, 8=274, 7=441] [4=13, 10=27, 5=95, 9=125, 6=198, 8=274, 7=441]
        int countPar441;
        boolean isPar441OK;
        final int[] par441 = {2, 6, 7, 8, 12, 14, 15, 16, 17, 20, 21, 23};
        Map<Integer, Integer> mapPar441 = new HashMap<Integer, Integer>();
        mapPar441.put(5, 95);
        mapPar441.put(6, 198);
        mapPar441.put(7, 441);
        mapPar441.put(8, 274);
        mapPar441.put(9, 125);

        // 2.583.407 [ 2 3 4 5 8 9 12 13 14 16 17 19 ] .:. [6=219, 8=289, 7=442] [3=1, 11=4, 4=11, 10=28, 5=73, 9=106, 6=219, 8=289, 7=442]
        int countPar442;
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
        int countPar444;
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
        int countPar446;
        boolean isPar446OK;
        final int[] par446 = {2, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 18};
        Map<Integer, Integer> mapPar446 = new HashMap<Integer, Integer>();
        mapPar446.put(5, 69);
        mapPar446.put(6, 225);
        mapPar446.put(7, 446);
        mapPar446.put(8, 259);
        mapPar446.put(9, 129);

        // Map<Integer, Integer> mapNumAposta = new HashMap<Integer, Integer>();

        // imprime o cabecalho da listagem primeiro, antes de varrer os concursos:
        $.log("\nCONCURSO  DEZENAS                                        INFERIOR   PAR PRIMO       P.441 P.442 P.444 P.446       REPET: INF PAR 441 442 444 446     PENREP: INF PAR 441 442 444 446");
        $.log($.repeat("-", 180));

        // percorre os resultados a partir do ultimo concurso, para exibir em lista a partir do mais recente.
        for (int i = this.loteria.lenConcursos - 1; i >= 0; i--) {
            // a cada 110 concursos, imprime novamente o cabecalho para meior legibilidade.
            if (i % 50 == 0 && i > 0) {
                $.log("\nCONCURSO  DEZENAS                                        INFERIOR   PAR PRIMO       P.441 P.442 P.444 P.446       REPET: INF PAR 441 442 444 446     PENREP: INF PAR 441 442 444 446");
                $.log($.repeat("-", 180));
            }

            // concurso a ser processado.
            ConcursoBean concurso = this.loteria.concursos[i];

            int countUltRepetInf = Lot.countRepetidas(DezenaBean.DEZENAS_INFERIORES, concurso.ultRepetidas);
            int countUltRepetPar = Lot.countRepetidas(DezenaBean.DEZENAS_PARES, concurso.ultRepetidas);
            int countUltRepet441 = Lot.countRepetidas(par441, concurso.ultRepetidas);
            int countUltRepet442 = Lot.countRepetidas(par442, concurso.ultRepetidas);
            int countUltRepet444 = Lot.countRepetidas(par444, concurso.ultRepetidas);
            int countUltRepet446 = Lot.countRepetidas(par446, concurso.ultRepetidas);

            int countPenRepetInf = Lot.countRepetidas(DezenaBean.DEZENAS_INFERIORES, concurso.penRepetidas);
            int countPenRepetPar = Lot.countRepetidas(DezenaBean.DEZENAS_PARES, concurso.penRepetidas);
            int countPenRepet441 = Lot.countRepetidas(par441, concurso.penRepetidas);
            int countPenRepet442 = Lot.countRepetidas(par442, concurso.penRepetidas);
            int countPenRepet444 = Lot.countRepetidas(par444, concurso.penRepetidas);
            int countPenRepet446 = Lot.countRepetidas(par446, concurso.penRepetidas);

            // int numAposta, idAposta = 0;
            // for (int j = 0; j < this.loteria.lenApostas; j++) {
            // if (Lot.countRepetidas(concurso.ordenadas, this.loteria.apostas[j].ordenadas) == 15) {
            // idAposta = this.loteria.apostas[j].id;
            // break;
            // }
            // }
            //
            // // em que quadradante se encontra a aposta.
            // numAposta = Math.floorDiv(idAposta, 330000); // vai de 0 a 9 o fator numeral da aposta.

            // registra a quantidade no map de contabilizacao de paridades:
            // int val = 0;
            // if (mapNumAposta.containsKey(numAposta)) {
            // val = mapNumAposta.get(numAposta);
            // }
            // mapNumAposta.put(numAposta, ++val);

            // verifica se o concurso possui alguma metrica na faixa satisfatoria:
            isQtInferiorOK = mapQtInferior.containsKey(concurso.qtInferior);
            isQtParOK = mapQtPar.containsKey(concurso.qtPar);
            isQtPrimoOK = mapQtPrimo.containsKey(concurso.qtPrimo);

            countPar441 = Lot.countRepetidas(par441, concurso.ordenadas);
            isPar441OK = mapPar441.containsKey(countPar441);

            countPar442 = Lot.countRepetidas(par442, concurso.ordenadas);
            isPar442OK = mapPar442.containsKey(countPar442);

            countPar444 = Lot.countRepetidas(par444, concurso.ordenadas);
            isPar444OK = mapPar444.containsKey(countPar444);

            countPar446 = Lot.countRepetidas(par446, concurso.ordenadas);
            isPar446OK = mapPar446.containsKey(countPar446);

            // se ok, imprime o concurso:
            $.log("    {0}  {1}  {2}{3} {4}{5} {6}{7}       {8}{9} {10}{11} {12}{13} {14}{15}         {16}   {17}  {18}  {19}  {20}  {21}  {22}         {23}   {24}  {25}  {26}  {27}  {28}  {29}", //
                  Lot.formatDez(concurso.id, 4), // 0
                  Lot.formatDezenas(concurso.ordenadas, 2), // 1

                  Lot.formatDez(concurso.qtInferior, 4), isQtInferiorOK ? "*" : " ", // 2,3
                  Lot.formatDez(concurso.qtPar, 4), isQtParOK ? "*" : " ", // 4,5
                  Lot.formatDez(concurso.qtPrimo, 4), isQtPrimoOK ? "*" : " ", // 6,7

                  Lot.formatDez(countPar441, 4), isPar441OK ? "*" : " ", // 8,9
                  Lot.formatDez(countPar442, 4), isPar442OK ? "*" : " ", // 10,11
                  Lot.formatDez(countPar444, 4), isPar444OK ? "*" : " ", // 12,13
                  Lot.formatDez(countPar446, 4), isPar446OK ? "*" : " ", // 14,15

                  Lot.formatDez(concurso.qtRepetidas, 2), // 16
                  Lot.formatDez(countUltRepetInf, 2), // 17
                  Lot.formatDez(countUltRepetPar, 2), // 18
                  Lot.formatDez(countUltRepet441, 2), // 19
                  Lot.formatDez(countUltRepet442, 2), // 20
                  Lot.formatDez(countUltRepet444, 2), // 21
                  Lot.formatDez(countUltRepet446, 2), // 22

                  Lot.formatDez(concurso.qtPenRepetidas, 2), // 23
                  Lot.formatDez(countPenRepetInf, 2), // 24
                  Lot.formatDez(countPenRepetPar, 2), // 25
                  Lot.formatDez(countPenRepet441, 2), // 26
                  Lot.formatDez(countPenRepet442, 2), // 27
                  Lot.formatDez(countPenRepet444, 2), // 28
                  Lot.formatDez(countPenRepet446, 2) // 29
            );
        }

        $.log("\n" + $.repeat("-", 180) + "\n");

        // $.log("\n > mapNumAposta: {0}", $.sortByValue(mapNumAposta));

        // $.log("\n" + $.repeat("-", 180));

        if (1 < 2) return; // FIXME

        // imprime o cabecalho da listagem primeiro, antes de varrer as combinacoes:
        $.log("\n       #   PARIDADE                                      KEY/VALUE    SIZE    MAPPING PARETO                  MAPPING");
        $.log($.repeat("-", 180));

        Map<Integer, Integer> map400Paridade = new TreeMap<Integer, Integer>();

        // percorre todas as paridades para verificar a distribuicao mais simetrica nos concursos ocorridos.
        for (int p = 0; p < 0 /* lenParidade */; p++) { // lenParidade
            // paridades[p] = array de 12 dezenas formando uma combinacao de paridade.

            // registro da quantidade de numeros dentro de uma paridade gerada (max 12).
            Map<Integer, Integer> mapQtParidade = new TreeMap<Integer, Integer>();

            // percorre os resultados dos concursos, para verificar as metricas da paridade corrente.
            for (int i = 0; i < this.loteria.lenConcursos; i++) {
                // concurso a ser processado.
                ConcursoBean concurso = this.loteria.concursos[i];

                // verifica quantas dezenas do concurso pertencem a paridade corrente:
                int contQtParidade = Lot.countRepetidas(paridades[p], concurso.ordenadas);

                // registra a quantidade no map de contabilizacao de paridades:
                int val = 0;
                if (mapQtParidade.containsKey(contQtParidade)) {
                    val = mapQtParidade.get(contQtParidade);
                }
                mapQtParidade.put(contQtParidade, ++val);
            }

            // verifica metricas da paridade gerada:
            List<Map.Entry<Integer, Integer>> ranking = $.sortByValue(mapQtParidade);
            Map.Entry<Integer, Integer> maxParidade = ranking.get(ranking.size() - 1);

            mapQtParidade = getMapIntPareto(mapQtParidade);

            // Registra as paridades com mais de 400 concursos no max.
            if (maxParidade.getValue() >= 400) {
                int val = 0;
                if (map400Paridade.containsKey(maxParidade.getValue())) {
                    val = map400Paridade.get(maxParidade.getValue());
                }
                map400Paridade.put(maxParidade.getValue(), ++val);
            }

            /*
             * Exibe as medidas das metricas que serao consideradas pelo FATOR.
             */
            if (maxParidade.getValue() > 440 /* maxValue */) { // 441=1, 442=1, 444=1, 446=1
                $.log(">{0}   {1}  .:.   {2}={3}       {4}     {5}    {6}", //
                      Lot.formatDez(p, 7), //
                      Lot.formatArray(paridades[p], 2), //
                      Lot.formatDez(maxParidade.getKey(), 2), //
                      Lot.formatDez(maxParidade.getValue(), 4), //
                      mapQtParidade.size(), //
                      $.sortByValue(mapQtParidade), //
                      ranking);
            }
        }

        $.log("\n" + $.repeat("-", 180));

        $.log("\n > map400Paridade: {0}", $.sortByValue(map400Paridade));

        $.log("\n" + $.repeat("-", 180));

    }
}
