package process;

import java.util.Arrays;

import entity.ConcursoBean;
import entity.LoteriaBean;
import util.$;

public class CicloProcess implements Runnable {

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public CicloProcess(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    /*
     *
     */
    @Override
    public void run() {
        // percorre os resultados a partir do ultimo concurso, para exibir em lista a partir do mais recente.
        for (int i = this.loteria.lenConcursos - 1; i >= 0; i--) {
            // concurso a ser processado.
            ConcursoBean concurso = this.loteria.concursos[i];

            // identifica quantos concursos foram necessarios para fechar os ciclos.
            concurso.ciclo15 = 0; // comeca a contar a partir do concurso anterior (a seguir);
            concurso.ciclo25 = 0; // comeca a contar a partir do atual, mas no loop a seguir;

            /*
             * --- CICLO DAS 15 ULTIMAS DEZENAS SORTEADAS ---------------------
             */

            // utiliza copia da lista de dezenas sorteadas do concurso.
            final int[] atual15 = Arrays.copyOf(concurso.ordenadas, 15);
            int cont15Dezenas = 0; // contabiliza as dezenas correspondentes (match).

            // registra os ciclos de 15 dezenas para posteriormente incluir no ConcursoBean.
            int[][] aux_ciclosDezenas15 = new int[16][]; // normalmente nao passa de 11 concursos para fechar o ciclo.
            int[] aux_ciclosCont15 = new int[16];
            int[] aux_ciclosPerc15 = new int[16];

            // a partir do concurso anterior, percorre os resultados para fechar o ciclo das 15 dezenas (atual).
            // pula fora quando encontrar as 15 dezenas (do atual) nos concursos anteriores.
            for (int j = i - 1; (j >= 0 && cont15Dezenas < 15); j--) {
                // concurso.ciclo15 ira indicar o indice no array concurso.ciclosDezenas15.

                // / estrutura do concurso anterior a ser manipulado.
                ConcursoBean anteriorStruct = this.loteria.concursos[j];

                // utiliza copia da lista de dezenas sorteadas do concurso.
                final int[] anterior15 = Arrays.copyOf(anteriorStruct.ordenadas, 15);
                int cont = 0; // conta dezenas do concurso anterior que batem com concurso atual.

                // verifica quais dezenas do concurso anterior batem com o concurso atual.
                for (int k = 0; k < 15; k++) { // indice utilizado no array de dezenas do concurso anterior.
                    int idxDezAtual = Arrays.binarySearch(concurso.ordenadas, anterior15[k]);
                    if (idxDezAtual > -1) { // esta dezena do concurso anterior foi sorteada no concurso atual.
                        cont++; // contabiliza as dezenas iguais entre os dois concursos.

                        // se ainda nao computou a dezena entre as 15 do concurso atual:
                        if (atual15[idxDezAtual] > 0) {
                            atual15[idxDezAtual] = 0; // anula a dezena para nao contabilizar em outros concursos.
                            cont15Dezenas++; // quando chegar em 15 entao encontrou todas as dezenas.
                        }

                    } else { // se nao achou a dezena, entao limpa a lacuna.
                        anterior15[k] = 0; // nao ira aparecer na impressao, ja que nao bate com atual.
                    }
                }

                // registra percentual de contribuicao do concurso para o ciclo de 15 dezenas do concurso atual.
                int perc = (cont * 100) / 15; // inverte a ordem de calculo de percentual para resultado inteiro.
                anteriorStruct.ciclosPercentual15 = $.insertArray(perc, anteriorStruct.ciclosPercentual15);

                // mais um concurso anterior (atual nao incluido) a percorrer, para fechar as 15 dezenas.
                aux_ciclosDezenas15[concurso.ciclo15] = anterior15; // registro das dezenas do concurso mapeadas no
                                                                    // ciclo 15.
                aux_ciclosCont15[concurso.ciclo15] = cont;
                aux_ciclosPerc15[concurso.ciclo15] = perc;

                concurso.ciclo15++;
            }

            // ao final do processamento do ciclo 15, faz "trim" no array concurso.ciclosDezenas15:
            if (concurso.ciclo15 > 0) {
                final int len15 = concurso.ciclo15;

                concurso.ciclosDezenas15 = new int[len15][];
                concurso.ciclosCont15 = new int[len15];
                concurso.ciclosPerc15 = new int[len15];

                System.arraycopy(aux_ciclosDezenas15, 0, concurso.ciclosDezenas15, 0, len15);
                System.arraycopy(aux_ciclosCont15, 0, concurso.ciclosCont15, 0, len15);
                System.arraycopy(aux_ciclosPerc15, 0, concurso.ciclosPerc15, 0, len15);
            }

            /*
             * --- CICLO DAS 25 DEZENAS LOTOFACIL -----------------------------
             */

            // utiliza lista completa de possiveis dezenas da loteria.
            final int[] atual25 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
            int cont25Dezenas = 0; // contabiliza as dezenas correspondentes (match).

            // registra os ciclos de 25 dezenas para posteriormente incluir no ConcursoBean.
            int[][] aux_ciclosDezenas25 = new int[16][]; // normalmente nao passa de 11 concursos para fechar o ciclo.
            int[] aux_ciclosCont25 = new int[16];
            int[] aux_ciclosPerc25 = new int[16];

            // a partir do concurso atual, percorre os resultados para fechar o ciclo das 25 dezenas.
            // pula fora quando encontrar as 25 dezenas nos concursos anteriores.
            for (int j = i; (j >= 0 && cont25Dezenas < 25); j--) {
                // concurso.ciclo15 ira indicar o indice no array concurso.ciclosDezenas15.

                // / estrutura do concurso anterior a ser manipulado.
                ConcursoBean anteriorStruct = this.loteria.concursos[j];

                // utiliza copia da lista de dezenas sorteadas do concurso.
                final int[] anterior15 = Arrays.copyOf(anteriorStruct.ordenadas, 15);
                int cont = 0; // conta dezenas do concurso anterior que batem com concurso atual.

                // verifica quais dezenas do concurso batem com alguma das 25 dezenzas da loteria ainda nao computadas.
                for (int k = 0; k < 15; k++) { // indice utilizado no array de dezenas do concurso anterior.
                    int idxDezLoto = Arrays.binarySearch(this.loteria.DEZENAS, anterior15[k]);
                    if (idxDezLoto > -1 && atual25[idxDezLoto] > 0) { // se a dezena ainda nao foi computada:
                        cont++; // contabiliza as dezenas correspondentes neste concurso.

                        atual25[idxDezLoto] = 0; // anula a dezena para nao contabilizar em outros concursos.
                        cont25Dezenas++; // quando chegar em 25 entao encontrou todas as dezenas.

                    } else { // se nao achou a dezena, entao limpa a lacuna.
                        anterior15[k] = 0; // nao ira aparecer na impressao, ja que nao bate com atual.
                    }
                }

                // registra percentual de contribuicao do concurso para o ciclo de 25 dezenas.
                int perc = (cont * 100) / 25; // inverte a ordem de calculo de percentual para resultado inteiro.
                anteriorStruct.ciclosPercentual25 = $.insertArray(perc, anteriorStruct.ciclosPercentual25);

                // mais um concurso anterior (atual tambem incluido) a percorrer, para fechar as 25 dezenas.
                aux_ciclosDezenas25[concurso.ciclo25] = anterior15; // registro das dezenas do concurso mapeadas no
                                                                    // ciclo 25.
                aux_ciclosCont25[concurso.ciclo25] = cont;
                aux_ciclosPerc25[concurso.ciclo25] = perc;

                concurso.ciclo25++;
            }

            // ao final do processamento do ciclo 25, faz "trim" no array concurso.ciclosDezenas25:
            if (concurso.ciclo25 > 0) {
                final int len25 = concurso.ciclo25;

                concurso.ciclosDezenas25 = new int[len25][];
                concurso.ciclosCont25 = new int[len25];
                concurso.ciclosPerc25 = new int[len25];

                System.arraycopy(aux_ciclosDezenas25, 0, concurso.ciclosDezenas25, 0, len25);
                System.arraycopy(aux_ciclosCont25, 0, concurso.ciclosCont25, 0, len25);
                System.arraycopy(aux_ciclosPerc25, 0, concurso.ciclosPerc25, 0, len25);
            }

            /*
             * atualiza estrutura da loteria com as metricas calculadas:
             */
            this.loteria.incCiclo15(concurso.ciclo15);
            this.loteria.incCiclo25(concurso.ciclo25);
        }

        /*
         * --- MATRIZ DE CICLOS DAS 25 DEZENAS DA LOTERIA -----------------
         */

        // verifica o espacamento entre concursos para compor o ciclo de repeticao de dezena.
        final int lenMatriz = this.loteria.matrizCiclos.length;
        for (int idxCiclo = 0; idxCiclo < lenMatriz; idxCiclo++) {
            final int ciclo = idxCiclo + 1; // melhor legibilidade com uma variavel indicando o ciclo.

            // var ciclo eh o numero de concursos que supostamente fecham um ciclo de repeticao.
            this.loteria.matrizCiclos[idxCiclo] = new int[ciclo + 1][25]; // registra o numero de repeticoes de 0 a 'ciclo'
            // vezes para
            // cada dezena.
            int cont_ciclo = 0;

            // percorre todos os concursos para verificar quantas vezes cada dezena repetiu no ciclo.
            int[] sorteadas = new int[25]; // quantas vezes cada dezena repetiu em um numero 'ciclo' de concursos.
            for (int i = 0; i < this.loteria.lenConcursos; i++) {
                // concurso a ser processado.
                ConcursoBean concurso = this.loteria.concursos[i];

                // incrementa repeticao de cada dezena sorteada no concurso.
                for (int dez : concurso.ordenadas) { // indice da dezena.
                    sorteadas[dez - 1]++; // o numero da dezena eh usado como indice (vai de 0 a 24).
                }

                // controla o numero de concursos percorridos.
                cont_ciclo++;
                if (cont_ciclo == ciclo) { // se fechou um ciclo de repeticoes:
                    // cada dezena pode ter repetido de 0 a 'ciclo' vezes.
                    for (int idx = 0; idx < 25; idx++) {
                        this.loteria.matrizCiclos[idxCiclo][sorteadas[idx]][idx]++;
                    }

                    // zera o numero de repeticoes de cada dezena por ciclo.
                    sorteadas = new int[25]; // quantas vezes cada dezena repetiu em um numero 'ciclo' de concursos.

                    cont_ciclo = 0; // inicia novamente um ciclo;
                }
            }

            // nao deixa de encerrar o ultimo ciclo.
            if (cont_ciclo > 0) { // se ainda nao fechou o ultimo ciclo:
                // cada dezena pode ter repetido de 0 a 'ciclo' vezes.
                for (int idx = 0; idx < 25; idx++) {
                    this.loteria.matrizCiclos[idxCiclo][sorteadas[idx]][idx]++;
                }
            }

            // imprime as estatisticas de repeticoes de cada dezena por ciclo:
            // int fracao = this.loteria.concursos.length / ciclo;
            // $.log("\nCiclo: {0} x {1}      {2}    \u00A7", Lot.formatDez(ciclo, 3), Lot.formatDez(fracao, 4),
            // Lot.formatDezenas(DEZENAS));
            // $.log("\t               --------------------------------------------------------------------------------------------------------------------------------");
            //
            // float mediaLin, mediaCol = 0f;
            // for (int i = 0; i <= ciclo; i++) {
            // mediaLin = 0f;
            // for (int j = 0; j < 25; j++) {
            // int val = this.loteria.matrizCiclos[idxCiclo][i][j];
            //
            // mediaLin += val;
            // }
            // mediaLin = mediaLin / 25f;
            // mediaCol += mediaLin;
            //
            // $.log("\t Repetiu {0}x: {1} {2}", Lot.formatDez(i, 3), Lot.formatArray(this.loteria.matrizCiclos[idxCiclo][i],
            // 4), Lot.formatDez(mediaLin, "##0.0000", 9));
            // }
            // mediaCol = mediaCol / (ciclo + 1);
            // $.log("\t --------------------------------------------------------------------------------------------------------------------------------------------------------");
            // $.log("\t {0}  {1}\n", $.repeat(' ', 140), Lot.formatDez(mediaCol, "##0.0000", 9));

        }
    }
}
