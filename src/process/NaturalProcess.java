package process;

import entity.ConcursoBean;
import entity.DezenaBean;
import entity.LoteriaBean;
import util.$;

public class NaturalProcess implements Runnable {

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public NaturalProcess(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    /*
     *
     */
    @Override
    public void run() {
        // soma do conjunto de inferiores e superiores ate o concurso corrente:
        int i3s12 = 0; // 3 inferiores, 12 superiores
        int i4s11 = 0; // 4 inferiores, 11 superiores
        int i5s10 = 0; // 5 inferiores, 10 superiores
        int i6s9 = 0; // 6 inferiores, 9 superiores
        int i7s8 = 0; // 7 inferiores, 8 superiores
        int i8s7 = 0; // 8 inferiores, 7 superiores
        int i9s6 = 0; // 9 inferiores, 6 superiores
        int i10s5 = 0; // 10 inferiores, 5 superiores
        int i11s4 = 0; // 11 inferiores, 4 superiores
        int i12s3 = 0; // 12 inferiores, 3 superiores
        int i13s2 = 0; // 13 inferiores, 2 superiores

        // soma do conjunto de pares e impares ate o concurso corrente:
        int p2i13 = 0; // 2 pares, 13 impares
        int p3i12 = 0; // 3 pares, 12 impares
        int p4i11 = 0; // 4 pares, 11 impares
        int p5i10 = 0; // 5 pares, 10 impares
        int p6i9 = 0; // 6 pares, 9 impares
        int p7i8 = 0; // 7 pares, 8 impares
        int p8i7 = 0; // 8 pares, 7 impares
        int p9i6 = 0; // 9 pares, 6 impares
        int p10i5 = 0; // 10 pares, 5 impares
        int p11i4 = 0; // 11 pares, 4 impares
        int p12i3 = 0; // 12 pares, 3 impares

        // soma do conjunto de primos ate o concurso corrente:
        int pr0 = 0; // nenhum primo
        int pr1 = 0; // 1 primo
        int pr2 = 0; // 2 primos
        int pr3 = 0; // 3 primos
        int pr4 = 0; // 4 primos
        int pr5 = 0; // 5 primos
        int pr6 = 0; // 6 primos
        int pr7 = 0; // 7 primos
        int pr8 = 0; // 8 primos
        int pr9 = 0; // 9 primos
        int pr10 = 0; // 10 primos

        // contabilizacao do numero de concursos com a media correspondente:
        int md8 = 0; // dezenas com media 8
        int md9 = 0; // dezenas com media 9
        int md10 = 0; // dezenas com media 10
        int md11 = 0; // dezenas com media 11
        int md12 = 0; // dezenas com media 12
        int md13 = 0; // dezenas com media 13
        int md14 = 0; // dezenas com media 14
        int md15 = 0; // dezenas com media 15
        int md16 = 0; // dezenas com media 16
        int md17 = 0; // dezenas com media 17
        int md18 = 0; // dezenas com media 18

        // contabilizacao do numero se sequencias em todos os concursos ate o presente.
        int sq5 = 0; // 5 dezenas em sequencia
        int sq6 = 0; // 6 dezenas em sequencia
        int sq7 = 0; // 7 dezenas em sequencia
        int sq8 = 0; // 8 dezenas em sequencia
        int sq9 = 0; // 9 dezenas em sequencia
        int sq10 = 0; // 10 dezenas em sequencia
        int sq11 = 0; // 11 dezenas em sequencia
        int sq12 = 0; // 12 dezenas em sequencia
        int sq13 = 0; // 13 dezenas em sequencia
        int sq14 = 0; // 14 dezenas em sequencia
        int sq15 = 0; // 15 dezenas em sequencia

        // contabilizacao do numero maximo de dezenas em seguida em todos os concursos ate o presente.
        int sg2 = 0; // 2 dezenas em sequencia
        int sg3 = 0; // 3 dezenas em sequencia
        int sg4 = 0; // 4 dezenas em sequencia
        int sg5 = 0; // 5 dezenas em sequencia
        int sg6 = 0; // 6 dezenas em sequencia
        int sg7 = 0; // 7 dezenas em sequencia
        int sg8 = 0; // 8 dezenas em sequencia
        int sg9 = 0; // 9 dezenas em sequencia
        int sg10 = 0; // 10 dezenas em sequencia
        int sg11 = 0; // 11 dezenas em sequencia
        int sg12 = 0; // 12 dezenas em sequencia

        // contabilizacao da distancia entre as dezenas ate o concurso corrente.
        int ds14 = 0; // 14 unidades de distancia
        int ds15 = 0; // 15 unidades de distancia
        int ds16 = 0; // 16 unidades de distancia
        int ds17 = 0; // 17 unidades de distancia
        int ds18 = 0; // 18 unidades de distancia
        int ds19 = 0; // 19 unidades de distancia
        int ds20 = 0; // 20 unidades de distancia
        int ds21 = 0; // 21 unidades de distancia
        int ds22 = 0; // 22 unidades de distancia
        int ds23 = 0; // 23 unidades de distancia
        int ds24 = 0; // 24 unidades de distancia

        // percorre os resultados a partir do primeiro concurso, para acumular os conjuntos.
        for (int i = 0; i < this.loteria.lenConcursos; i++) {
            // concurso a ser processado.
            ConcursoBean concurso = this.loteria.concursos[i];

            // contabiliza em seguida a classificacao basica das dezenas:
            concurso.qtInferior = 0;
            concurso.qtSuperior = 0;

            concurso.qtPar = 0;
            concurso.qtImpar = 0;
            concurso.qtPrimo = 0;
            concurso.qtFatorial = 0;
            concurso.qtPerfeito = 0;
            concurso.qtAbundante = 0;
            concurso.qtDeficiente = 0;
            concurso.qtImperfeito = 0;
            concurso.qtQuadrado = 0;
            concurso.qtFibonacci = 0;

            concurso.dzSoma = 0;

            // verifica cada uma das dezenas quanto a sua classificacao:
            for (DezenaBean dezena : concurso.dezenas) {
                int dez = dezena.numero; // melhora a legibilidade.

                if (dezena.isInferior) concurso.qtInferior++;
                if (dezena.isSuperior) concurso.qtSuperior++;
                if (dezena.isPar) concurso.qtPar++;
                if (dezena.isImpar) concurso.qtImpar++;
                if (dezena.isPrimo) concurso.qtPrimo++;
                if (dezena.isFatorial) concurso.qtFatorial++;
                if (dezena.isPerfeito) concurso.qtPerfeito++;
                if (dezena.isAbundante) concurso.qtAbundante++;
                if (dezena.isDeficiente) concurso.qtDeficiente++;
                if (dezena.isImperfeito) concurso.qtImperfeito++;
                if (dezena.isQuadrado) concurso.qtQuadrado++;
                if (dezena.isFibonacci) concurso.qtFibonacci++;

                concurso.dzSoma += dez;
            }
            concurso.dzMedia = concurso.dzSoma / 15;

            // acumula a quantidade relativa de inferiores e superiores ate o concurso atual:
            switch (concurso.qtInferior) {
                case 3:
                    i3s12++;
                    break;

                case 4:
                    i4s11++;
                    break;

                case 5:
                    i5s10++;
                    break;

                case 6:
                    i6s9++;
                    break;

                case 7:
                    i7s8++;
                    break;

                case 8:
                    i8s7++;
                    break;

                case 9:
                    i9s6++;
                    break;

                case 10:
                    i10s5++;
                    break;

                case 11:
                    i11s4++;
                    break;

                case 12:
                    i12s3++;
                    break;

                case 13:
                    i13s2++;
                    break;

                default:
                    $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                    break;
            }

            // soma do conjunto de inferiores e superiores ate o concurso corrente:
            concurso.i3s12 = i3s12; // 3 inferiores, 12 superiores
            concurso.i4s11 = i4s11; // 4 inferiores, 11 superiores
            concurso.i5s10 = i5s10; // 5 inferiores, 10 superiores
            concurso.i6s9 = i6s9; // 6 inferiores, 9 superiores
            concurso.i7s8 = i7s8; // 7 inferiores, 8 superiores
            concurso.i8s7 = i8s7; // 8 inferiores, 7 superiores
            concurso.i9s6 = i9s6; // 9 inferiores, 6 superiores
            concurso.i10s5 = i10s5; // 10 inferiores, 5 superiores
            concurso.i11s4 = i11s4; // 11 inferiores, 4 superiores
            concurso.i12s3 = i12s3; // 12 inferiores, 3 superiores
            concurso.i13s2 = i13s2; // 13 inferiores, 2 superiores

            // acumula a quantidade relativa de pares e impares ate o concurso atual:
            switch (concurso.qtPar) {
                case 2:
                    p2i13++;
                    break;

                case 3:
                    p3i12++;
                    break;

                case 4:
                    p4i11++;
                    break;

                case 5:
                    p5i10++;
                    break;

                case 6:
                    p6i9++;
                    break;

                case 7:
                    p7i8++;
                    break;

                case 8:
                    p8i7++;
                    break;

                case 9:
                    p9i6++;
                    break;

                case 10:
                    p10i5++;
                    break;

                case 11:
                    p11i4++;
                    break;

                case 12:
                    p12i3++;
                    break;

                default:
                    $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                    break;
            }

            // soma do conjunto de pares e impares ate o concurso corrente:
            concurso.p2i13 = p2i13; // 2 pares, 13 impares
            concurso.p3i12 = p3i12; // 3 pares, 12 impares
            concurso.p4i11 = p4i11; // 4 pares, 11 impares
            concurso.p5i10 = p5i10; // 5 pares, 10 impares
            concurso.p6i9 = p6i9; // 6 pares, 9 impares
            concurso.p7i8 = p7i8; // 7 pares, 8 impares
            concurso.p8i7 = p8i7; // 8 pares, 7 impares
            concurso.p9i6 = p9i6; // 9 pares, 6 impares
            concurso.p10i5 = p10i5; // 10 pares, 5 impares
            concurso.p11i4 = p11i4; // 11 pares, 4 impares
            concurso.p12i3 = p12i3; // 12 pares, 3 impares

            // computa a quantidade relativa de primos:
            switch (concurso.qtPrimo) {
                case 0:
                    pr0++;
                    break;

                case 1:
                    pr1++;
                    break;

                case 2:
                    pr2++;
                    break;

                case 3:
                    pr3++;
                    break;

                case 4:
                    pr4++;
                    break;

                case 5:
                    pr5++;
                    break;

                case 6:
                    pr6++;
                    break;

                case 7:
                    pr7++;
                    break;

                case 8:
                    pr8++;
                    break;

                case 9:
                    pr9++;
                    break;

                case 10:
                    pr10++;
                    break;

                default:
                    $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                    break;
            }

            // registra soma do conjunto de primos ate o concurso corrente:
            concurso.pr0 = pr0; // nenhum primo
            concurso.pr1 = pr1; // 1 primo
            concurso.pr2 = pr2; // 2 primos
            concurso.pr3 = pr3; // 3 primos
            concurso.pr4 = pr4; // 4 primos
            concurso.pr5 = pr5; // 5 primos
            concurso.pr6 = pr6; // 6 primos
            concurso.pr7 = pr7; // 7 primos
            concurso.pr8 = pr8; // 8 primos
            concurso.pr9 = pr9; // 9 primos
            concurso.pr10 = pr10; // 10 primos

            // computa o numero de concursos com a media correspondente:
            switch (concurso.dzMedia) {
                case 8:
                    md8++;
                    break;

                case 9:
                    md9++;
                    break;

                case 10:
                    md10++;
                    break;

                case 11:
                    md11++;
                    break;

                case 12:
                    md12++;
                    break;

                case 13:
                    md13++;
                    break;

                case 14:
                    md14++;
                    break;

                case 15:
                    md15++;
                    break;

                case 16:
                    md16++;
                    break;

                case 17:
                    md17++;
                    break;

                case 18:
                    md18++;
                    break;

                default:
                    $.log("ERRO: Concurso contabilizado com media {0} de dezenas: {1}", concurso.dzMedia, concurso);
                    break;
            }

            // registra a media das dezenas em todos os concursos ate o presente.
            concurso.md8 = md8; // dezenas com media 8
            concurso.md9 = md9; // dezenas com media 9
            concurso.md10 = md10; // dezenas com media 10
            concurso.md11 = md11; // dezenas com media 11
            concurso.md12 = md12; // dezenas com media 12
            concurso.md13 = md13; // dezenas com media 13
            concurso.md14 = md14; // dezenas com media 14
            concurso.md15 = md15; // dezenas com media 15
            concurso.md16 = md16; // dezenas com media 16
            concurso.md17 = md17; // dezenas com media 17
            concurso.md18 = md18; // dezenas com media 18

            // verifica se ha dezenas em sequencia no concurso:
            concurso.dzSeguidas = 0;
            concurso.dzSequencia = 0;

            int dezAnterior = -1; // nao ha dezena anterior neste momento.
            boolean emSequencia = false;
            int emSeguida = 0;

            for (int dez : concurso.ordenadas) {
                if (dez == (dezAnterior + 1)) { // se a dezena corrente vem logo a seguir a anterior:
                    if (emSequencia) {
                        // se ja estiver em modo sequencia, entao eh apenas mais uma em sequencia.
                        concurso.dzSequencia++;

                        // mais uma dezena em seguida.
                        emSeguida++;
                        if (emSeguida > concurso.dzSeguidas) concurso.dzSeguidas = emSeguida;

                    } else {
                        // se nao estiver em sequencia, ativa entao o modo-sequencia.
                        emSequencia = true;
                        concurso.dzSequencia += 2; // adiciona ambas dezenas na contagem.

                        // uma nova sequencia de dezenas em seguida.
                        emSeguida = 2; // inicia com 2 dezenas em seguida.
                        if (emSeguida > concurso.dzSeguidas) concurso.dzSeguidas = emSeguida;
                    }

                } else { // se for diferente, entao nao esta mais em modo sequencia.
                    emSequencia = false;
                    emSeguida = 0; // zera o contador de dezenas em seguida.
                }

                // atualiza a sequencia anterior:
                dezAnterior = dez;
            }

            // computa a quantidade de dezenas dispostas em sequencia ate o presente:
            switch (concurso.dzSequencia) {
                case 5:
                    sq5++;
                    break;

                case 6:
                    sq6++;
                    break;

                case 7:
                    sq7++;
                    break;

                case 8:
                    sq8++;
                    break;

                case 9:
                    sq9++;
                    break;

                case 10:
                    sq10++;
                    break;

                case 11:
                    sq11++;
                    break;

                case 12:
                    sq12++;
                    break;

                case 13:
                    sq13++;
                    break;

                case 14:
                    sq14++;
                    break;

                case 15:
                    sq15++;
                    break;

                default:
                    $.log("ERRO: Concurso contabilizado com {0} dezenas em sequencia: {1}", concurso.dzSequencia, concurso);
                    break;
            }

            // registra a soma do numero se sequencias em todos os concursos ate o presente.
            concurso.sq5 = sq5; // 5 dezenas em sequencia
            concurso.sq6 = sq6; // 6 dezenas em sequencia
            concurso.sq7 = sq7; // 7 dezenas em sequencia
            concurso.sq8 = sq8; // 8 dezenas em sequencia
            concurso.sq9 = sq9; // 9 dezenas em sequencia
            concurso.sq10 = sq10; // 1sq dezenas em sequencia
            concurso.sq11 = sq11; // 11 dezenas em sequencia
            concurso.sq12 = sq12; // 12 dezenas em sequencia
            concurso.sq13 = sq13; // 13 dezenas em sequencia
            concurso.sq14 = sq14; // 14 dezenas em sequencia
            concurso.sq15 = sq15; // 15 dezenas em sequencia

            // computa a quantidade de dezenas dispostas em sequencia ate o presente:
            switch (concurso.dzSeguidas) {
                case 2:
                    sg2++;
                    break;

                case 3:
                    sg3++;
                    break;

                case 4:
                    sg4++;
                    break;

                case 5:
                    sg5++;
                    break;

                case 6:
                    sg6++;
                    break;

                case 7:
                    sg7++;
                    break;

                case 8:
                    sg8++;
                    break;

                case 9:
                    sg9++;
                    break;

                case 10:
                    sg10++;
                    break;

                case 11:
                    sg11++;
                    break;

                case 12:
                    sg12++;
                    break;

                default:
                    // $.log("ERRO: Concurso contabilizado com {0} dezenas em seguida: {1}", concurso.dzSeguidas, concurso);
                    break;
            }

            // registra a soma do numero maximo de dezenas em seguida em todos os concursos ate o presente.
            concurso.sg2 = sg2; // 2 dezenas em sequencia
            concurso.sg3 = sg3; // 3 dezenas em sequencia
            concurso.sg4 = sg4; // 4 dezenas em sequencia
            concurso.sg5 = sg5; // 5 dezenas em sequencia
            concurso.sg6 = sg6; // 6 dezenas em sequencia
            concurso.sg7 = sg7; // 7 dezenas em sequencia
            concurso.sg8 = sg8; // 8 dezenas em sequencia
            concurso.sg9 = sg9; // 9 dezenas em sequencia
            concurso.sg10 = sg10; // 1sg dezenas em sequencia
            concurso.sg11 = sg11; // 11 dezenas em sequencia
            concurso.sg12 = sg12; // 12 dezenas em sequencia

            // a distancia sera a diferenca entre a primeira e ultima dezenas.
            concurso.dzDistancia = concurso.ordenadas[14] - concurso.ordenadas[0];

            // computa a distancia entre as dezenas ate o momento:
            switch (concurso.dzDistancia) {
                case 14:
                    ds14++;
                    break;

                case 15:
                    ds15++;
                    break;

                case 16:
                    ds16++;
                    break;

                case 17:
                    ds17++;
                    break;

                case 18:
                    ds18++;
                    break;

                case 19:
                    ds19++;
                    break;

                case 20:
                    ds20++;
                    break;

                case 21:
                    ds21++;
                    break;

                case 22:
                    ds22++;
                    break;

                case 23:
                    ds23++;
                    break;

                case 24:
                    ds24++;
                    break;

                default:
                    $.log("ERRO: Concurso nao contabilizado corretamente: {0}", concurso);
                    break;
            }

            // registra a soma da distancia entre as dezenas ate o concurso corrente.
            concurso.ds14 = ds14; // 14 unidades de distancia
            concurso.ds15 = ds15; // 15 unidades de distancia
            concurso.ds16 = ds16; // 16 unidades de distancia
            concurso.ds17 = ds17; // 17 unidades de distancia
            concurso.ds18 = ds18; // 18 unidades de distancia
            concurso.ds19 = ds19; // 19 unidades de distancia
            concurso.ds20 = ds20; // 20 unidades de distancia
            concurso.ds21 = ds21; // 21 unidades de distancia
            concurso.ds22 = ds22; // 22 unidades de distancia
            concurso.ds23 = ds23; // 23 unidades de distancia
            concurso.ds24 = ds24; // 24 unidades de distancia

            /*
             * atualiza estrutura da loteria com as metricas calculadas:
             */
            this.loteria.incQtInferior(concurso.qtInferior);
            this.loteria.incQtPar(concurso.qtPar);
            this.loteria.incQtPrimo(concurso.qtPrimo);
            this.loteria.incDzSequencia(concurso.dzSequencia);
            this.loteria.incDzSeguidas(concurso.dzSeguidas);
            this.loteria.incDzDistancia(concurso.dzDistancia);
            this.loteria.incDzMedia(concurso.dzMedia);
        }
    }
}
