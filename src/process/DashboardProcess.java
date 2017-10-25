package process;

import entity.ConcursoBean;
import entity.LoteriaBean;
import util.$;
import util.Lot;

public class DashboardProcess implements Runnable {

    // exibe as barras de percentuais alternando o simbolo:
    static char CBAR = '\u2591';

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public DashboardProcess(final LoteriaBean p_loteria) {
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
            // for (int i = 0; i < length; i++) {
            // concurso a ser impresso.
            ConcursoBean concurso = this.loteria.concursos[i];

            // imprime as dezenas sorteadas no concurso atual.
            $.log("\nConcurso: {0}  L: {1}  {2}  .:.  SEQ: {3}  .:.  SGD: {4}  .:.  DST: {5}  .:.  MED: {6}  .:.  REP: {7}  .:.  PTO: {8}", //
                  Lot.formatDez(concurso.id, 4), concurso.distLinhas, Lot.formatArray(concurso.ordenadas), concurso.dzSequencia, concurso.dzSeguidas, concurso.dzDistancia, concurso.dzMedia, concurso.qtRepetidas, concurso.qtAcertos);

            // impressao da estatistica para o concurso:
            $.log("\t        C: {0}  --------------------------------------------------------------------------------------------------------------------------------", concurso.distColunas);

            $.log("\t                      {0}", Lot.formatDezenas(this.loteria.DEZENAS));
            $.log("\t           Repetidas: {0}", Lot.formatDezenas(concurso.dzRepetidas));
            $.log("\t      Max. Repetidas: {0}", Lot.formatDezenas(concurso.dzMaxRepetidas));
            $.log("\t         Recorrentes: {0}", Lot.formatDezenas(concurso.dzRecorrentes));
            // $.log("\t    Soma Recorrentes: {0}", Lot.formatDezenas(concurso.dzSomRecorrentes));
            $.log("\t   Media Recorrentes: {0}", Lot.formatDezenas(concurso.dzMedRecorrentes));
            $.log("\t                      --------------------------------------------------------------------------------------------------------------------------------");

            // $.log("\t                      {0}", Lot.formatDezenas(DEZENAS));
            $.log("\t                         {0}", concurso.distCartao);
            $.log("\t           Atrasadas: {0}", Lot.formatDezenas(concurso.dzAtrasadas));
            $.log("\t      Max. Atrasadas: {0}", Lot.formatDezenas(concurso.dzMaxAtrasadas));
            $.log("\t             Atrasos: {0}", Lot.formatDezenas(concurso.dzAtrasos));
            // $.log("\t        Soma Atrasos: {0}", Lot.formatDezenas(concurso.dzSomAtrasos));
            $.log("\t       Media Atrasos: {0}", Lot.formatDezenas(concurso.dzMedAtrasos));
            $.log("\t                      --------------------------------------------------------------------------------------------------------------------------------");

            $.log("\t                      {0}", Lot.formatDezenas(this.loteria.DEZENAS));
            $.log("\t         Ocorrencias: {0}", Lot.formatDezenas(concurso.dzOcorrencias));
            $.log("\t------------------------------------------------------------------------------------------------------------------------------------------------------");

            // monta a disposicao em frame das ultimas 11 distribuicoes binarias:
            final String[] distBinaria11 = new String[11]; // ja deixa com espacos quando nao tiver concurso para preencher.
            final int idxMenos11 = i - 10;
            int idx11 = 0;
            for (int j = i; j >= idxMenos11; j--) {
                // evita estouro de array.
                distBinaria11[idx11++] = (j > -1) ? this.loteria.concursos[j].distBinario : "                         ";
            }

            // imprime graficamente os tipos de numeros e distribuicao das dezenas.
            $.log("\t          Inferiores: {0} /13        5 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtInferior, 2), Lot.formatDez(concurso.ac5, 3), distBinaria11[0], "                        ", "");
            $.log("\t          Superiores: {0} /12        6 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtSuperior, 2), Lot.formatDez(concurso.ac6, 3), distBinaria11[1], concurso.distRank.substring(0, 24), concurso.distCartao.substring(0, 24));
            $.log("\t               Pares: {0} /12        7 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtPar, 2), Lot.formatDez(concurso.ac7, 3), distBinaria11[2], "                        ", "");
            $.log("\t             Impares: {0} /13        8 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtImpar, 2), Lot.formatDez(concurso.ac8, 3), distBinaria11[3], concurso.distRank.substring(25, 49), concurso.distCartao.substring(25, 49));
            $.log("\t              Primos: {0} /10      < 9 acertou: {1} >     {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtPrimo, 2), Lot.formatDez(concurso.ac9, 3), distBinaria11[4], "                        ", "");
            $.log("\t           Fibonacci: {0} / 7       10 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtFibonacci, 2), Lot.formatDez(concurso.ac10, 3), distBinaria11[5], concurso.distRank.substring(50, 74), concurso.distCartao.substring(50, 74));
            $.log("\t           Quadrados: {0} / 5       11 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtQuadrado, 2), Lot.formatDez(concurso.ac11, 3), distBinaria11[6], "                        ", "");
            $.log("\t           Fatoriais: {0} / 4       12 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtFatorial, 2), Lot.formatDez(concurso.ac12, 3), distBinaria11[7], concurso.distRank.substring(75, 99), concurso.distCartao.substring(75, 99));
            $.log("\t          Abundantes: {0} / 4       13 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtAbundante, 2), Lot.formatDez(concurso.ac13, 3), distBinaria11[8], "                        ", "");
            $.log("\t         Imperfeitos: {0} / 4       14 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtImperfeito, 2), Lot.formatDez(concurso.ac14, 3), distBinaria11[9], concurso.distRank.substring(100, 124), concurso.distCartao.substring(100, 124));
            $.log("\t         Deficientes: {0} /19       15 acertou: {1}       {2}    |    {3}    |    {4}", //
                  Lot.formatDez(concurso.qtDeficiente, 2), Lot.formatDez(concurso.ac15, 3), distBinaria11[10], "                        ", "");
            $.log("\t------------------------------------------------------------------------------------------------------------------------------------------------------");

            // imprime os ciclos 15 e 25 das dezenas sorteadas no concurso atual:
            $.log("\t           Concursos: {0}  {1}  .:.  CICLO 15: {2}%  .:.  CICLO 25: {3}%", //
                  Lot.formatDez(concurso.id, 4), Lot.formatDezenas(concurso.ordenadas, 2), //
                  Lot.formatArray(concurso.ciclosPercentual15), //
                  Lot.formatArray(concurso.ciclosPercentual25));

            // imprime os ciclos de 15 dezenas utilizando copia da lista de dezenas mapeadas do concurso.
            final int len15 = (concurso.ciclosCont15 == null) ? 0 : concurso.ciclosCont15.length;
            int id15 = concurso.id; // vai decrementando o numero do concurso.
            for (int j = 0; j < len15; j++) {
                $.log("\t                      {0}  {1}  = {2} = {3}%  {4}", //
                      Lot.formatDez(--id15, 4), //
                      Lot.formatDezenas(concurso.ciclosDezenas15[j], 2), //
                      Lot.formatDez(concurso.ciclosCont15[j], 2), //
                      Lot.formatDez(concurso.ciclosPerc15[j], 3), //
                      $.repeat((char) (CBAR + (j % 3)), (int) (concurso.ciclosPerc15[j] * 0.65)));
            }

            // separa as listagens para melhor legibilidade.
            $.log("\t                      ------------------------------------------------------  .:.  CICLO 15: {0}  .:.  CICLO 25: {1}", concurso.ciclo15, concurso.ciclo25);

            // imprime os ciclos de 25 dezenas utilizando copia da lista de dezenas mapeadas do concurso.
            final int len25 = (concurso.ciclosCont25 == null) ? 0 : concurso.ciclosCont25.length;
            int id25 = concurso.id; // vai decrementando o numero do concurso.
            for (int j = 0; j < len25; j++) {
                // imprime o concurso anterior, destacando as colunas iguais ao concurso atual.
                $.log("\t                      {0}  {1}  = {2} = {3}%  {4}", //
                      Lot.formatDez(id25--, 4), //
                      Lot.formatDezenas(concurso.ciclosDezenas25[j], 2), //
                      Lot.formatDez(concurso.ciclosCont25[j], 2), //
                      Lot.formatDez(concurso.ciclosPerc25[j], 3), //
                      $.repeat((char) (CBAR + (j % 3)), (int) (concurso.ciclosPerc25[j] * 0.65)));
            }

            // separa as listagens para melhor legibilidade.
            $.log("\t------------------------------------------------------------------------------------------------------------------------------------------------------");

            // impressao da contabilizacao das classificacoes ate o presente.
            $.log("\t   3/12 inf: {0}      2/13 par: {1}      1 prm: {2}      7 seq: {3}     10 sgd: {4}     15 dst: {5}      8 med: {6}     15 rep: {7}       8 pto: {8}", //
                  Lot.formatDez(concurso.i3s12, 3), Lot.formatDez(concurso.p2i13, 3), Lot.formatDez(concurso.pr1, 3), Lot.formatDez(concurso.sq7, 3), Lot.formatDez(concurso.sg10, 3), Lot.formatDez(concurso.ds15, 3), Lot.formatDez(concurso.md8, 3),
                  Lot.formatDez(concurso.rp15, 3), Lot.formatDez(concurso.pt8, 3));
            $.log("\t   4/11 inf: {0}      3/12 par: {1}      2 prm: {2}      8 seq: {3}     11 sgd: {4}     17 dst: {5}      9 med: {6}      5 rep: {7}       9 pto: {8}", //
                  Lot.formatDez(concurso.i4s11, 3), Lot.formatDez(concurso.p3i12, 3), Lot.formatDez(concurso.pr2, 3), Lot.formatDez(concurso.sq8, 3), Lot.formatDez(concurso.sg11, 3), Lot.formatDez(concurso.ds17, 3), Lot.formatDez(concurso.md9, 3),
                  Lot.formatDez(concurso.rp5, 3), Lot.formatDez(concurso.pt9, 3));
            $.log("\t   5/10 inf: {0}      4/11 par: {1}      3 prm: {2}      9 seq: {3}     12 sgd: {4}     19 dst: {5}     10 med: {6}      6 rep: {7}      10 pto: {8}", //
                  Lot.formatDez(concurso.i5s10, 3), Lot.formatDez(concurso.p4i11, 3), Lot.formatDez(concurso.pr3, 3), Lot.formatDez(concurso.sq9, 3), Lot.formatDez(concurso.sg12, 3), Lot.formatDez(concurso.ds19, 3), Lot.formatDez(concurso.md10, 3),
                  Lot.formatDez(concurso.rp6, 3), Lot.formatDez(concurso.pt10, 3));
            $.log("\t   6/ 9 inf: {0}      5/10 par: {1}      4 prm: {2}     10 seq: {3}      2 sgd: {4}     21 dst: {5}     11 med: {6}      7 rep: {7}      11 pto: {8}", //
                  Lot.formatDez(concurso.i6s9, 3), Lot.formatDez(concurso.p5i10, 3), Lot.formatDez(concurso.pr4, 3), Lot.formatDez(concurso.sq10, 3), Lot.formatDez(concurso.sg2, 3), Lot.formatDez(concurso.ds21, 3), Lot.formatDez(concurso.md11, 3),
                  Lot.formatDez(concurso.rp7, 3), Lot.formatDez(concurso.pt11, 3));
            $.log("\t   7/ 8 inf: {0}      6/ 9 par: {1}      5 prm: {2}     11 seq: {3}      3 sgd: {4}     23 dst: {5}     12 med: {6}      8 rep: {7}      12 pto: {8}", //
                  Lot.formatDez(concurso.i7s8, 3), Lot.formatDez(concurso.p6i9, 3), Lot.formatDez(concurso.pr5, 3), Lot.formatDez(concurso.sq11, 3), Lot.formatDez(concurso.sg3, 3), Lot.formatDez(concurso.ds23, 3), Lot.formatDez(concurso.md12, 3),
                  Lot.formatDez(concurso.rp8, 3), Lot.formatDez(concurso.pt12, 3));
            $.log("\t   8/ 7 inf: {0} ---  7/ 8 par: {1} ---  6 prm: {2} --- 12 seq: {3} ---  4 sgd: {4} --- 24 dst: {5} --- 13 med: {6} ---  9 rep: {7} ---- 13 pto: {8}", //
                  Lot.formatDez(concurso.i8s7, 3), Lot.formatDez(concurso.p7i8, 3), Lot.formatDez(concurso.pr6, 3), Lot.formatDez(concurso.sq12, 3), Lot.formatDez(concurso.sg4, 3), Lot.formatDez(concurso.ds24, 3), Lot.formatDez(concurso.md13, 3),
                  Lot.formatDez(concurso.rp9, 3), Lot.formatDez(concurso.pt13, 3));
            $.log("\t   9/ 6 inf: {0}      8/ 7 par: {1}      7 prm: {2}     13 seq: {3}      5 sgd: {4}     22 dst: {5}     14 med: {6}     10 rep: {7}      14 pto: {8}", //
                  Lot.formatDez(concurso.i9s6, 3), Lot.formatDez(concurso.p8i7, 3), Lot.formatDez(concurso.pr7, 3), Lot.formatDez(concurso.sq13, 3), Lot.formatDez(concurso.sg5, 3), Lot.formatDez(concurso.ds22, 3), Lot.formatDez(concurso.md14, 3),
                  Lot.formatDez(concurso.rp10, 3), Lot.formatDez(concurso.pt14, 3));
            $.log("\t  10/ 5 inf: {0}      9/ 6 par: {1}      8 prm: {2}     14 seq: {3}      6 sgd: {4}     20 dst: {5}     15 med: {6}     11 rep: {7}      15 pto: {8}", //
                  Lot.formatDez(concurso.i10s5, 3), Lot.formatDez(concurso.p9i6, 3), Lot.formatDez(concurso.pr8, 3), Lot.formatDez(concurso.sq14, 3), Lot.formatDez(concurso.sg6, 3), Lot.formatDez(concurso.ds20, 3), Lot.formatDez(concurso.md15, 3),
                  Lot.formatDez(concurso.rp11, 3), Lot.formatDez(concurso.pt15, 3));
            $.log("\t  11/ 4 inf: {0}     10/ 5 par: {1}      9 prm: {2}     15 seq: {3}      7 sgd: {4}     18 dst: {5}     16 med: {6}     12 rep: {7}       5 pto: {8}", //
                  Lot.formatDez(concurso.i11s4, 3), Lot.formatDez(concurso.p10i5, 3), Lot.formatDez(concurso.pr9, 3), Lot.formatDez(concurso.sq15, 3), Lot.formatDez(concurso.sg7, 3), Lot.formatDez(concurso.ds18, 3), Lot.formatDez(concurso.md16, 3),
                  Lot.formatDez(concurso.rp12, 3), Lot.formatDez(concurso.pt5, 3));
            $.log("\t  12/ 3 inf: {0}     11/ 4 par: {1}     10 prm: {2}      5 seq: {3}      8 sgd: {4}     16 dst: {5}     17 med: {6}     13 rep: {7}       6 pto: {8}", //
                  Lot.formatDez(concurso.i12s3, 3), Lot.formatDez(concurso.p11i4, 3), Lot.formatDez(concurso.pr10, 3), Lot.formatDez(concurso.sq5, 3), Lot.formatDez(concurso.sg8, 3), Lot.formatDez(concurso.ds16, 3), Lot.formatDez(concurso.md17, 3),
                  Lot.formatDez(concurso.rp13, 3), Lot.formatDez(concurso.pt6, 3));
            $.log("\t  13/ 2 inf: {0}     12/ 3 par: {1}      0 prm: {2}      6 seq: {3}      9 sgd: {4}     14 dst: {5}     18 med: {6}     14 rep: {7}       7 pto: {8}", //
                  Lot.formatDez(concurso.i13s2, 3), Lot.formatDez(concurso.p12i3, 3), Lot.formatDez(concurso.pr0, 3), Lot.formatDez(concurso.sq6, 3), Lot.formatDez(concurso.sg9, 3), Lot.formatDez(concurso.ds14, 3), Lot.formatDez(concurso.md18, 3),
                  Lot.formatDez(concurso.rp14, 3), Lot.formatDez(concurso.pt7, 3));
            $.log("\t------------------------------------------------------------------------------------------------------------------------------------------------------");

            $.pause(" ");
        }
        $.log("\n");
    }
}
