package strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import loteria.ApostaBean;
import loteria.ConcursoBean;
import loteria.Lot;
import loteria.LoteriaBean;
import util.$;

/**
 * Classe <code>ParetoStrategy</code>.
 *
 * @category strategy.
 */
public class FatorParetoStrategy implements Runnable {

    // fatores pareto, para filtrar resultados indesejados:
    public static final float FATOR_METRICAS = 0.3f; // 0.5 ... 0.8

    public static final float FATOR_CICLO = 0.3f; // 0.5 ... 0.8

    public static final float FATOR_DISTRIB = 0.1f; // 0.5 ... 0.8

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public FatorParetoStrategy(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
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
        final int minQtInferior = (int) (this.loteria.maxQtInferior() * FATOR_METRICAS);

        Map<Integer, Integer> mapQtInferior = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapQtInferior.entrySet()) {
            if (entry.getValue() > minQtInferior) {
                mapQtInferior.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de dezenas pares (max 12)
        int contQtPar;
        boolean isQtParOK;
        final int minQtPar = (int) (this.loteria.maxQtPar() * FATOR_METRICAS);

        Map<Integer, Integer> mapQtPar = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapQtPar.entrySet()) {
            if (entry.getValue() > minQtPar) {
                mapQtPar.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de dezenas primos.
        int contQtPrimo;
        boolean isQtPrimoOK;
        final int minQtPrimo = (int) (this.loteria.maxQtPrimo() * FATOR_METRICAS);

        Map<Integer, Integer> mapQtPrimo = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapQtPrimo.entrySet()) {
            if (entry.getValue() > minQtPrimo) {
                mapQtPrimo.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de dezenas em sequencia.
        int contDzSequencia;
        boolean isDzSequenciaOK;
        final int minDzSequencia = (int) (this.loteria.maxDzSequencia() * FATOR_METRICAS);

        Map<Integer, Integer> mapDzSequencia = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapDzSequencia.entrySet()) {
            if (entry.getValue() > minDzSequencia) {
                mapDzSequencia.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade maxima de dezenas em seguida.
        int contDzSeguidas;
        boolean isDzSeguidasOK;
        final int minDzSeguidas = (int) (this.loteria.maxDzSeguidas() * FATOR_METRICAS);

        Map<Integer, Integer> mapDzSeguidas = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapDzSeguidas.entrySet()) {
            if (entry.getValue() > minDzSeguidas) {
                mapDzSeguidas.put(entry.getKey(), entry.getValue());
            }
        }

        // medida da distancia entre as dezenas.
        int contDzDistancia;
        boolean isDzDistanciaOK;
        final int minDzDistancia = (int) (this.loteria.maxDzDistancia() * FATOR_METRICAS);

        Map<Integer, Integer> mapDzDistancia = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapDzDistancia.entrySet()) {
            if (entry.getValue() > minDzDistancia) {
                mapDzDistancia.put(entry.getKey(), entry.getValue());
            }
        }

        // medida da media aritmetica entre as dezenas.
        int contDzMedia;
        boolean isDzMediaOK;
        final int minDzMedia = (int) (this.loteria.maxDzMedia() * FATOR_METRICAS);

        Map<Integer, Integer> mapDzMedia = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapDzMedia.entrySet()) {
            if (entry.getValue() > minDzMedia) {
                mapDzMedia.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de dezenas repetidas em relacao ao ultimo concurso.
        int contQtRepetidas;
        boolean isQtRepetidasOK;
        final int minQtRepetidas = (int) (this.loteria.maxQtRepetidas() * FATOR_METRICAS);

        Map<Integer, Integer> mapQtRepetidas = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapQtRepetidas.entrySet()) {
            if (entry.getValue() > minQtRepetidas) {
                mapQtRepetidas.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de acertos em relacao a todos os outros concursos anteriores.
        int contQtAcertos;
        boolean isQtAcertosOK;
        final int minQtAcertos = (int) (this.loteria.maxQtAcertos() * FATOR_METRICAS);

        Map<Integer, Integer> mapQtAcertos = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapQtAcertos.entrySet()) {
            if (entry.getValue() > minQtAcertos) {
                mapQtAcertos.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de ciclos de 15 dezenas sorteadas, em relacao a todos os outros concursos anteriores.
        int contCiclo15;
        boolean isCiclo15OK;
        final int minCiclo15 = (int) (this.loteria.maxCiclo15() * FATOR_CICLO);

        Map<Integer, Integer> mapCiclo15 = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapCiclo15.entrySet()) {
            if (entry.getValue() > minCiclo15) {
                mapCiclo15.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de ciclos de 25 dezenas da loteria, em relacao a todos os outros concursos anteriores.
        int contCiclo25;
        boolean isCiclo25OK;
        final int minCiclo25 = (int) (this.loteria.maxCiclo25() * FATOR_CICLO);

        Map<Integer, Integer> mapCiclo25 = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : this.loteria.mapCiclo25.entrySet()) {
            if (entry.getValue() > minCiclo25) {
                mapCiclo25.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de distribuicoes de linhas em relacao a todos os outros concursos anteriores.
        int contDistLinhas;
        boolean isDistLinhasOK;
        final int minDistLinhas = (int) (this.loteria.maxDistLinhas() * FATOR_DISTRIB);

        Map<String, Integer> mapDistLinhas = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : this.loteria.mapDistLinhas.entrySet()) {
            if (entry.getValue() > minDistLinhas) {
                mapDistLinhas.put(entry.getKey(), entry.getValue());
            }
        }

        // quantidade de distribuicoes de colunas em relacao a todos os outros concursos anteriores.
        int contDistColunas;
        boolean isDistColunasOK;
        final int minDistColunas = (int) (this.loteria.maxDistColunas() * FATOR_DISTRIB);

        Map<String, Integer> mapDistColunas = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : this.loteria.mapDistColunas.entrySet()) {
            if (entry.getValue() > minDistColunas) {
                mapDistColunas.put(entry.getKey(), entry.getValue());
            }
        }

        Map<Integer, Integer> mapQtMetrica = new TreeMap<Integer, Integer>();

        /*
         * Lista os concursos anteriores, exibindo as metricas mais frequentes em destaque.
         */

        $.log("\n" + $.repeat("-", 150) + "\n");

        // imprime o cabecalho da listagem primeiro, antes de varrer os concursos:
        $.log("\nCONCURSO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
        $.log($.repeat("-", 170));

        // percorre os resultados a partir do ultimo concurso, para exibir em lista a partir do mais recente.
        int contAtende, naoAtende = 0;
        boolean isTudoOk;

        contQtInferior = 0;
        contQtPar = 0;
        contQtPrimo = 0;
        contDzSequencia = 0;
        contDzSeguidas = 0;
        contDzDistancia = 0;
        contDzMedia = 0;
        contQtRepetidas = 0;
        contQtAcertos = 0;
        contCiclo15 = 0;
        contCiclo25 = 0;
        contDistLinhas = 0;
        contDistColunas = 0;

        for (int i = this.loteria.lenConcursos - 1; i >= 0; i--) {
            // a cada 110 concursos, imprime novamente o cabecalho para meior legibilidade.
            if (i % 50 == 0 && i > 0) {
                $.log("\nCONCURSO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
                $.log($.repeat("-", 170));
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
            isQtAcertosOK = mapQtAcertos.containsKey(concurso.qtAcertos);
            isCiclo15OK = mapCiclo15.containsKey(concurso.ciclo15);
            isCiclo25OK = mapCiclo25.containsKey(concurso.ciclo25);
            isDistLinhasOK = mapDistLinhas.containsKey(concurso.distLinhas);
            isDistColunasOK = mapDistColunas.containsKey(concurso.distColunas);

            // contabiliza cada metrica no conjunto de concursos:
            contAtende = 0;
            if (isQtInferiorOK) {
                contAtende++;
                contQtInferior++;
            }
            if (isQtParOK) {
                contAtende++;
                contQtPar++;
            }
            if (isQtPrimoOK) {
                contAtende++;
                contQtPrimo++;
            }
            if (isDzSequenciaOK) {
                contAtende++;
                contDzSequencia++;
            }
            if (isDzSeguidasOK) {
                contAtende++;
                contDzSeguidas++;
            }
            if (isDzDistanciaOK) {
                contAtende++;
                contDzDistancia++;
            }
            if (isDzMediaOK) {
                contAtende++;
                contDzMedia++;
            }
            if (isQtRepetidasOK) {
                contAtende++;
                contQtRepetidas++;
            }
            if (isQtAcertosOK) {
                contAtende++;
                contQtAcertos++;
            }
            if (isCiclo15OK) {
                contAtende++;
                contCiclo15++;
            }
            if (isCiclo25OK) {
                contAtende++;
                contCiclo25++;
            }
            if (isDistLinhasOK) {
                contAtende++;
                contDistLinhas++;
            }
            if (isDistColunasOK) {
                contAtende++;
                contDistColunas++;
            }
            int val = 0;
            if (mapQtMetrica.containsKey(contAtende)) {
                val = mapQtMetrica.get(contAtende);
            }
            mapQtMetrica.put(contAtende, ++val);

            // se o concurso atende a todos os filtros de metricas:
            isTudoOk = isQtInferiorOK && isQtParOK && isQtPrimoOK && isDzSequenciaOK && isDzSeguidasOK //
                       && isDzDistanciaOK && isDzMediaOK && isQtRepetidasOK && isQtAcertosOK //
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
                || isQtAcertosOK //
                || isCiclo15OK //
                || isCiclo25OK //
                || isDistLinhasOK //
                || isDistColunasOK) {
                // se ok, imprime o concurso:
                $.log("{0} {1}  {2}   {3}{4}  {5}{6}  {7}{8}  {9}{10}  {11}{12}  {13}{14}  {15}{16}  {17}{18}  {19}{20}     {21}{22}    {23}{24}     {25}{26}    {27}{28}", //
                      Lot.formatDez(contAtende, 2) + (isTudoOk ? "*" : " "), //
                      Lot.formatDez(concurso.id, 4), //
                      Lot.formatDezenas(concurso.ordenadas, 2), //
                      Lot.formatDez(concurso.qtInferior, 4), isQtInferiorOK ? "*" : " ", //
                      Lot.formatDez(concurso.qtPar, 4), isQtParOK ? "*" : " ", //
                      Lot.formatDez(concurso.qtPrimo, 4), isQtPrimoOK ? "*" : " ", //
                      Lot.formatDez(concurso.dzSequencia, 4), isDzSequenciaOK ? "*" : " ", //
                      Lot.formatDez(concurso.dzSeguidas, 4), isDzSeguidasOK ? "*" : " ", //
                      Lot.formatDez(concurso.dzDistancia, 4), isDzDistanciaOK ? "*" : " ", //
                      Lot.formatDez(concurso.dzMedia, 4), isDzMediaOK ? "*" : " ", //
                      Lot.formatDez(concurso.qtRepetidas, 4), isQtRepetidasOK ? "*" : " ", //
                      Lot.formatDez(concurso.qtAcertos, 4), isQtAcertosOK ? "*" : " ", //

                      Lot.formatDez(concurso.ciclo15, 4), isCiclo15OK ? "*" : " ", //
                      Lot.formatDez(concurso.ciclo25, 4), isCiclo25OK ? "*" : " ", //

                      concurso.distLinhas, isDistLinhasOK ? "*" : " ", //
                      concurso.distColunas, isDistColunasOK ? "*" : " ");

            } else { // se nao esta na faixa, exibe lacuna.
                $.log("............");
                naoAtende++;
            }
        }

        $.log("\n" + $.repeat("-", 150) + "\n");

        $.log("> TOTAL DE CONCURSOS QUE NAO ATENDEM AS METRICAS:  {0}", naoAtende);

        /*
         * Exibe as medidas das metricas que serao consideradas pelo FATOR.
         */

        $.log("\n" + $.repeat("-", 150) + "\n");

        $.log(" > mapQtMetrica: {0}", //
              mapQtMetrica);

        $.log("\n > mapQtInferior: {0}  .:.  Cont: {1} == {2}%", //
              mapQtInferior, contQtInferior, (contQtInferior * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtPar: {0}  .:.  Cont: {1} == {2}%", //
              mapQtPar, contQtPar, (contQtPar * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtPrimo: {0}  .:.  Cont: {1} == {2}%", //
              mapQtPrimo, contQtPrimo, (contQtPrimo * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzSequencia: {0}  .:.  Cont: {1} == {2}%", //
              mapDzSequencia, contDzSequencia, (contDzSequencia * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzSeguidas: {0}  .:.  Cont: {1} == {2}%", //
              mapDzSeguidas, contDzSeguidas, (contDzSeguidas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzDistancia: {0}  .:.  Cont: {1} == {2}%", //
              mapDzDistancia, contDzDistancia, (contDzDistancia * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDzMedia: {0}  .:.  Cont: {1} == {2}%", //
              mapDzMedia, contDzMedia, (contDzMedia * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtRepetidas: {0}  .:.  Cont: {1} == {2}%", //
              mapQtRepetidas, contQtRepetidas, (contQtRepetidas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapQtAcertos: {0}  .:.  Cont: {1} == {2}%", //
              mapQtAcertos, contQtAcertos, (contQtAcertos * 100) / this.loteria.lenConcursos);

        $.log("\n > mapCiclo15: {0}  .:.  Cont: {1} == {2}%", //
              mapCiclo15, contCiclo15, (contCiclo15 * 100) / this.loteria.lenConcursos);

        $.log("\n > mapCiclo25: {0}  .:.  Cont: {1} == {2}%", //
              mapCiclo25, contCiclo25, (contCiclo25 * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDistLinhas: {0}  .:.  Cont: {1} == {2}%", //
              mapDistLinhas, contDistLinhas, (contDistLinhas * 100) / this.loteria.lenConcursos);

        $.log("\n > mapDistColunas: {0}  .:.  Cont: {1} == {2}%", //
              mapDistColunas, contDistColunas, (contDistColunas * 100) / this.loteria.lenConcursos);

        $.log("\n" + $.repeat("-", 150) + "\n");

         System.exit(0);

        /*
         * Identifica as combinacoes de dezenas que atendem as metricas em destaque.
         */

        // gera todas as 3.268.760 combinacoes para a Lotofacil.
        int[][] jogos = Lot.geraCombinacoes(25, 15); // total de 25 dezenas, 15 dezenas por combinacao.
        final int lenJogos = jogos.length;
        $.log("\nTotal de {0} combinacoes de jogos gerados para a Lotofacil.\n", lenJogos);

        $.log($.repeat("-", 150));

        // imprime o cabecalho da listagem primeiro, antes de varrer os concursos:
        $.log("\n       JOGO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
        $.log($.repeat("-", 170));

        // percorre todos os jogos para calcular as metricas de cada jogo.
        int simAtende = 0; // total de jogos que atendem ao filtro das metricas.
        boolean incAtende = false;

        for (int i = 0; i < lenJogos; i++) {
            // a cada 40 concursos, imprime novamente o cabecalho para meior legibilidade.
            if (simAtende % 40 == 0 && incAtende) {
                $.log("\n       JOGO  DEZENAS                                        INFERIOR    PAR  PRIMO    SEQ  SEGUD   DIST  MEDIA  REPET  PONTO    CICLO15  CICLO25    DIST.LINHAS  DIST.COLUNAS");
                $.log($.repeat("-", 170));
                incAtende = false;
            }

            // gera objeto ApostaBean para processamento das metricas do jogo:
            ApostaBean aposta = ApostaBean.parseAposta(i + 1, jogos[i], this.loteria.concursos);

            // de posse das metricas, verifica se o jogo atende ao filtro.
            isQtInferiorOK = mapQtInferior.containsKey(aposta.qtInferior);
            isQtParOK = mapQtPar.containsKey(aposta.qtPar);
            isQtPrimoOK = mapQtPrimo.containsKey(aposta.qtPrimo);
            isDzSequenciaOK = mapDzSequencia.containsKey(aposta.dzSequencia);
            isDzSeguidasOK = mapDzSeguidas.containsKey(aposta.dzSeguidas);
            isDzDistanciaOK = mapDzDistancia.containsKey(aposta.dzDistancia);
            isDzMediaOK = mapDzMedia.containsKey(aposta.dzMedia);
            isQtRepetidasOK = mapQtRepetidas.containsKey(aposta.qtRepetidas);
            isQtAcertosOK = mapQtAcertos.containsKey(aposta.qtAcertos);
            isCiclo15OK = mapCiclo15.containsKey(aposta.ciclo15);
            isCiclo25OK = mapCiclo25.containsKey(aposta.ciclo25);
            isDistLinhasOK = mapDistLinhas.containsKey(aposta.distLinhas);
            isDistColunasOK = mapDistColunas.containsKey(aposta.distColunas);

            // contabiliza cada metrica no conjunto de concursos:
            contAtende = 0;
            if (isQtInferiorOK) contAtende++;
            if (isQtParOK) contAtende++;
            if (isQtPrimoOK) contAtende++;
            if (isDzSequenciaOK) contAtende++;
            if (isDzSeguidasOK) contAtende++;
            if (isDzDistanciaOK) contAtende++;
            if (isDzMediaOK) contAtende++;
            if (isQtRepetidasOK) contAtende++;
            if (isQtAcertosOK) contAtende++;
            if (isCiclo15OK) contAtende++;
            if (isCiclo25OK) contAtende++;
            if (isDistLinhasOK) contAtende++;
            if (isDistColunasOK) contAtende++;

            // if (contAtende >= 11) {
            if (isCiclo15OK && isCiclo25OK) {

                // se atende ao menos 12 metricas, considera como aposta e imprime o jogo:
                $.log("{0}> {1}  {2}   {3}{4}  {5}{6}  {7}{8}  {9}{10}  {11}{12}  {13}{14}  {15}{16}  {17}{18}  {19}{20}     {21}{22}    {23}{24}     {25}{26}    {27}{28}", //
                      Lot.formatDez(contAtende, 2), //
                      Lot.formatDez(aposta.id, 7), //
                      Lot.formatDezenas(aposta.ordenadas, 2), //
                      Lot.formatDez(aposta.qtInferior, 4), isQtInferiorOK ? "*" : " ", //
                      Lot.formatDez(aposta.qtPar, 4), isQtParOK ? "*" : " ", //
                      Lot.formatDez(aposta.qtPrimo, 4), isQtPrimoOK ? "*" : " ", //
                      Lot.formatDez(aposta.dzSequencia, 4), isDzSequenciaOK ? "*" : " ", //
                      Lot.formatDez(aposta.dzSeguidas, 4), isDzSeguidasOK ? "*" : " ", //
                      Lot.formatDez(aposta.dzDistancia, 4), isDzDistanciaOK ? "*" : " ", //
                      Lot.formatDez(aposta.dzMedia, 4), isDzMediaOK ? "*" : " ", //
                      Lot.formatDez(aposta.qtRepetidas, 4), isQtRepetidasOK ? "*" : " ", //
                      Lot.formatDez(aposta.qtAcertos, 4), isQtAcertosOK ? "*" : " ", //

                      Lot.formatDez(aposta.ciclo15, 4), isCiclo15OK ? "*" : " ", //
                      Lot.formatDez(aposta.ciclo25, 4), isCiclo25OK ? "*" : " ", //

                      aposta.distLinhas, isDistLinhasOK ? "*" : " ", //
                      aposta.distColunas, isDistColunasOK ? "*" : " ");

                simAtende++; // contabiliza para verificar quantos jogos atendem ao filtro.
                incAtende = true; // manda testar para ver se imprime o cabecalho novamente.
            }
        }

        $.log("\n" + $.repeat("-", 150) + "\n");

        $.log("> TOTAL DE CONCURSOS QUE ATENDEM AO FILTRO DS METRICAS:  {0}", simAtende);

    }
    // --- Helpers diversos ---------------------------------------------------

}
