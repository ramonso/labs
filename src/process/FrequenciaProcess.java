package process;

import java.util.Arrays;

import entity.ConcursoBean;
import entity.LoteriaBean;
import util.$;
import util.Lot;

public class FrequenciaProcess implements Runnable {

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public FrequenciaProcess(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    /*
     *
     */
    @Override
    public void run() {
        // soma do conjunto de dezenas repetidas ate o concurso corrente:
        int rp5 = 0; // 5 repetidas
        int rp6 = 0; // 6 repetidas
        int rp7 = 0; // 7 repetidas
        int rp8 = 0; // 8 repetidas
        int rp9 = 0; // 9 repetidas
        int rp10 = 0; // 10 repetidas
        int rp11 = 0; // 11 repetidas
        int rp12 = 0; // 12 repetidas
        int rp13 = 0; // 13 repetidas
        int rp14 = 0; // 14 repetidas
        int rp15 = 0; // 15 repetidas

        // soma da pontuacao, equivalente ao numero maximo de dezenas que "acertaram" em todos os concursos.
        int pt5 = 0; // 5 pontos
        int pt6 = 0; // 6 pontos
        int pt7 = 0; // 7 pontos
        int pt8 = 0; // 8 pontos
        int pt9 = 0; // 9 pontos
        int pt10 = 0; // 10 pontos
        int pt11 = 0; // 11 pontos
        int pt12 = 0; // 12 pontos
        int pt13 = 0; // 13 pontos
        int pt14 = 0; // 14 pontos
        int pt15 = 0; // 15 pontos

        // a partir do primeiro concurso, as dezenas possuem 'zero' atraso.
        final int[] dzOcorrencias = new int[25]; // Relacao de dezenas, com a quantidade de ocorrencias de cada.

        final int[] dzAtrasadas = new int[25]; // Relacao de dezenas, com a quantidade de atrasos de cada.
        final int[] dzMaxAtrasadas = new int[25]; // Relacao de dezenas, com a quantidade maxima de atrasos de cada.
        final int[] dzAtrasos = new int[25]; // Relacao de dezenas, com a quantidade de vezes em que ficaram atrasadas
                                             // ao longo dos concursos.
        final int[] dzSomAtrasos = new int[25]; // Relacao de dezenas, com a soma dos atrasos ao longo dos concursos.
        final float[] dzMedAtrasos = new float[25]; // Relacao de dezenas, com a media dos atrasos ao longo dos
                                                    // concursos.

        final int[] dzRepetidas = new int[25]; // Relacao de dezenas, com a quantidade de recorrencias de cada.
        final int[] dzMaxRepetidas = new int[25]; // Relacao de dezenas, com a quantidade maxima de repeticoes de
                                                  // cada.
        final int[] dzRecorrentes = new int[25]; // Relacao de dezenas, com a quantidade de vezes em que se repetiram ao
                                                 // longo dos
        // concursos.
        final int[] dzSomRecorrentes = new int[25]; // Relacao de dezenas, com a soma das repeticoes ao longo dos
                                                    // concursos.
        final float[] dzMedRecorrentes = new float[25]; // Relacao de dezenas, com a media das repeticoes ao longo dos
                                                        // concursos.

        // percorre os resultados a partir do primeiro concurso, para acumular as frequencias.
        for (int i = 0; i < this.loteria.lenConcursos; i++) {
            // concurso a ser processado.
            ConcursoBean concurso = this.loteria.concursos[i];

            // no mesmo loop computa as atrasadas e repetidas.
            for (int d = 0; d < 25; d++) { // avalia todas as 25 dezenas.
                int dez = d + 1; // numero da dezena, apenas para fins de melhor legibilidade.
                boolean achou = false;
                for (int s = 0; s < 15; s++) { // percorre as dezenas sorteadas.
                    // o numero da dezena sorteada eh o indice na estrutura de atrasos.
                    if (dez == concurso.ordenadas[s]) {
                        achou = true;
                        break;
                    }
                }

                // assim que as dezenas foram sorteadas nesse concurso, zerou o atraso destas.
                if (achou) { // se a dezena foi sorteada, entao o atraso encerrou e a repeticao iniciou.
                    // mais um concurso em que a dezena foi sorteada.
                    dzOcorrencias[d]++;

                    // se a dezena estava atrasada, e agora nao esta mais, entao foi uma sequencia que passou.
                    if (dzAtrasadas[d] > 0) {
                        dzAtrasos[d]++; // uma sequencia a mais de atrasos que se encerrou.
                    }

                    dzAtrasadas[d] = 0; // como a dezena foi sorteada, o atraso sera zerado.

                    // computa mais uma repeticao para a dezena.
                    dzRepetidas[d]++;
                    dzSomRecorrentes[d]++; // acumula repeticoes, mas esse acumulador nunca eh zerado.

                    // ja soma e calcula a media:
                    dzMedRecorrentes[d] = (float) concurso.id / dzSomRecorrentes[d]; // em media, a cada quantos
                                                                                     // concursos
                    // ha repeticao da dezena.

                    // se esse total de repeticoes foi a maior repeticao ja ocorrida, entao deixa registrado.
                    if (dzRepetidas[d] > dzMaxRepetidas[d]) {
                        dzMaxRepetidas[d] = dzRepetidas[d]; // nova maxima repeticao da dezena.
                    }

                } else { // se nao achou, entao esta dezena nao foi sorteada neste concurso.
                    dzAtrasadas[d]++; // incrementa mais um atraso desta dezena.
                    dzSomAtrasos[d]++; // acumula os atrasos, mas esse acumulador nunca eh zerado.

                    // ja soma e calcula a media:
                    dzMedAtrasos[d] = (float) concurso.id / dzSomAtrasos[d]; // em media, a cada quantos concursos ha
                                                                             // atraso da dezena.

                    // se esse total de atrasos foi o maior atraso ja sofrido, entao deixa registrado.
                    if (dzAtrasadas[d] > dzMaxAtrasadas[d]) {
                        dzMaxAtrasadas[d] = dzAtrasadas[d]; // novo maximo atraso da dezena.
                    }

                    // se a dezena era recorrente, e agora nao foi sorteada, entao foi uma recorrencia que encerrou.
                    if (dzRepetidas[d] > 0) {
                        dzRecorrentes[d]++;
                    }

                    // como a dezena nao foi sorteada, a recorrencia sera zerada.
                    dzRepetidas[d] = 0;

                }
            }

            // verifica quantas e quais dezenas do concurso anterior se repetiram neste concurso.
            concurso.qtRepetidas = 0;
            concurso.qtPenRepetidas = 0;
            concurso.qtAcertos = 0;

            // contabilizacao do numero de dezenas que "acertaram" em todos os concursos.
            concurso.ac5 = 0; // 5 acertos
            concurso.ac6 = 0; // 6 acertos
            concurso.ac7 = 0; // 7 acertos
            concurso.ac8 = 0; // 8 acertos
            concurso.ac9 = 0; // 9 acertos
            concurso.ac10 = 0; // 10 acertos
            concurso.ac11 = 0; // 11 acertos
            concurso.ac12 = 0; // 12 acertos
            concurso.ac13 = 0; // 13 acertos
            concurso.ac14 = 0; // 14 acertos
            concurso.ac15 = 0; // 15 acertos

            if (i > 0) { // se estiver no primeiro concurso, nao ha eco.
                // para computar as dezenas repetidas, basta comparar com o concurso anterior.
                ConcursoBean anteriorStruct = this.loteria.concursos[i - 1];

                // varre as dezenas do concurso e verifica quantas e quais sairam no ultimo concurso.
                concurso.ultRepetidas = new int[15]; // podem haver no maximo todas as dezenas repetidas.

                concurso.qtRepetidas = 0; // contabiliza as dezenas repetidas e tambem o indice do novo array.
                for (int dez : concurso.ordenadas) {
                    // verifica se a dezena do concurso foi sorteada no ultimo concurso.
                    if (Arrays.binarySearch(anteriorStruct.ordenadas, dez) > -1) {
                        concurso.ultRepetidas[concurso.qtRepetidas++] = dez; // mais uma dezena que se repetiu entre os arrays.
                    }
                }
                // se repetiram menos que 15 dezenas, entao faz 'trim' no array das dezenas repetidas:
                if (concurso.qtRepetidas < 15) {
                    concurso.ultRepetidas = Arrays.copyOf(concurso.ultRepetidas, concurso.qtRepetidas);
                }

                if (i > 1) { // valida antes para verificar se bhouve um penultimo concurso.
                    anteriorStruct = this.loteria.concursos[i - 2];

                    // varre as dezenas do concurso e verifica quantas e quais sairam no penultimo concurso.
                    concurso.penRepetidas = new int[15]; // podem haver no maximo todas as dezenas repetidas.

                    concurso.qtPenRepetidas = 0; // contabiliza as dezenas repetidas e tambem o indice do novo array.
                    for (int dez : concurso.ordenadas) {
                        // verifica se a dezena do concurso foi sorteada no penultimo concurso.
                        if (Arrays.binarySearch(anteriorStruct.ordenadas, dez) > -1) {
                            concurso.penRepetidas[concurso.qtPenRepetidas++] = dez; // mais uma dezena que se repetiu entre os arrays.
                        }
                    }
                    // se repetiram menos que 15 dezenas, entao faz 'trim' no array das dezenas repetidas:
                    if (concurso.qtPenRepetidas < 15) {
                        concurso.penRepetidas = Arrays.copyOf(concurso.penRepetidas, concurso.qtPenRepetidas);
                    }
                }

                // acumula a quantidade relativa de dezenas repetidas ate o concurso atual:
                switch (concurso.qtRepetidas) {
                    case 5:
                        rp5++;
                        break;

                    case 6:
                        rp6++;
                        break;

                    case 7:
                        rp7++;
                        break;

                    case 8:
                        rp8++;
                        break;

                    case 9:
                        rp9++;
                        break;

                    case 10:
                        rp10++;
                        break;

                    case 11:
                        rp11++;
                        break;

                    case 12:
                        rp12++;
                        break;

                    case 13:
                        rp13++;
                        break;

                    case 14:
                        rp14++;
                        break;

                    case 15:
                        rp15++;
                        break;

                    default:
                        $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                        break;
                }

                // registro do conjunto de dezenas repetidas ate o concurso corrente:
                concurso.rp5 = rp5; // 5 repetidas
                concurso.rp6 = rp6; // 6 repetidas
                concurso.rp7 = rp7; // 7 repetidas
                concurso.rp8 = rp8; // 8 repetidas
                concurso.rp9 = rp9; // 9 repetidas
                concurso.rp10 = rp10; // 10 repetidas
                concurso.rp11 = rp11; // 11 repetidas
                concurso.rp12 = rp12; // 12 repetidas
                concurso.rp13 = rp13; // 13 repetidas
                concurso.rp14 = rp14; // 14 repetidas
                concurso.rp15 = rp15; // 15 repetidas

                // para computar a pontuacao, equivalente as dezenas "acertadas",
                // precisa comparar com todos os concursos anteriores.
                for (int j = i - 1; j >= 0; j--) {
                    anteriorStruct = this.loteria.concursos[j];

                    int acertos = Lot.countRepetidas(concurso.ordenadas, anteriorStruct.ordenadas);

                    // acumula a quantidade maxima de dezenas "acertadas" desde o primeiro concurso:
                    switch (acertos) {
                        case 5:
                            concurso.ac5++;
                            break;

                        case 6:
                            concurso.ac6++;
                            break;

                        case 7:
                            concurso.ac7++;
                            break;

                        case 8:
                            concurso.ac8++;
                            break;

                        case 9:
                            concurso.ac9++;
                            break;

                        case 10:
                            concurso.ac10++;
                            break;

                        case 11:
                            concurso.ac11++;
                            break;

                        case 12:
                            concurso.ac12++;
                            break;

                        case 13:
                            concurso.ac13++;
                            break;

                        case 14:
                            concurso.ac14++;
                            break;

                        case 15:
                            concurso.ac15++;
                            break;

                        default:
                            $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                            break;
                    }

                    // registra apenas o maximo de acertos atingidos em todos os concursos.
                    if (acertos > concurso.qtAcertos) {
                        concurso.qtAcertos = acertos;
                    }
                }

                // acumula a quantidade maxima de dezenas "acertadas" ate o concurso atual:
                switch (concurso.qtAcertos) {
                    case 5:
                        pt5++;
                        break;

                    case 6:
                        pt6++;
                        break;

                    case 7:
                        pt7++;
                        break;

                    case 8:
                        pt8++;
                        break;

                    case 9:
                        pt9++;
                        break;

                    case 10:
                        pt10++;
                        break;

                    case 11:
                        pt11++;
                        break;

                    case 12:
                        pt12++;
                        break;

                    case 13:
                        pt13++;
                        break;

                    case 14:
                        pt14++;
                        break;

                    case 15:
                        pt15++;
                        break;

                    default:
                        $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                        break;
                }

                // registro da pontuacao do concurso, equivalente ao maximo de dezenas acertadas em todos os concursos:
                concurso.pt5 = pt5; // 5 acertos
                concurso.pt6 = pt6; // 6 acertos
                concurso.pt7 = pt7; // 7 acertos
                concurso.pt8 = pt8; // 8 acertos
                concurso.pt9 = pt9; // 9 acertos
                concurso.pt10 = pt10; // 10 acertos
                concurso.pt11 = pt11; // 11 acertos
                concurso.pt12 = pt12; // 12 acertos
                concurso.pt13 = pt13; // 13 acertos
                concurso.pt14 = pt14; // 14 acertos
                concurso.pt15 = pt15; // 15 acertos
            }

            // as estruturas computadas permanecerao como a situacao corrente neste concurso.
            concurso.dzOcorrencias = Arrays.copyOf(dzOcorrencias, 25);

            concurso.dzAtrasadas = Arrays.copyOf(dzAtrasadas, 25);
            concurso.dzMaxAtrasadas = Arrays.copyOf(dzMaxAtrasadas, 25);
            concurso.dzAtrasos = Arrays.copyOf(dzAtrasos, 25);
            concurso.dzSomAtrasos = Arrays.copyOf(dzSomAtrasos, 25);
            concurso.dzMedAtrasos = Arrays.copyOf(dzMedAtrasos, 25);

            concurso.dzRepetidas = Arrays.copyOf(dzRepetidas, 25);
            concurso.dzMaxRepetidas = Arrays.copyOf(dzMaxRepetidas, 25);
            concurso.dzRecorrentes = Arrays.copyOf(dzRecorrentes, 25);
            concurso.dzSomRecorrentes = Arrays.copyOf(dzSomRecorrentes, 25);
            concurso.dzMedRecorrentes = Arrays.copyOf(dzMedRecorrentes, 25);

            /*
             * atualiza estrutura da loteria com as metricas calculadas:
             */
            this.loteria.incQtRepetidas(concurso.qtRepetidas);
            this.loteria.incQtPenRepetidas(concurso.qtPenRepetidas);
            this.loteria.incQtAcertos(concurso.qtAcertos);
        }
    }
}
