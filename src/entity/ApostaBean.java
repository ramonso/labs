package entity;

import java.io.Serializable;

import util.Lot;

public class ApostaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public final int id;

    public final int[] ordenadas; // dezenas na ordem numeral (ascendente).

    /*
     *
     */
    public ApostaBean(final int p_id, final int[] p_ordenadas) {
        super();

        this.id = p_id;
        this.ordenadas = p_ordenadas;
    }

    // Objetos DezenaBean ajudam na identificacao das metricas.
    public DezenaBean[] dezenas = new DezenaBean[15];

    // --- Classificacao dos Numeros Naturais ---------------------------------

    public int qtInferior = 0;

    public int qtSuperior = 0;

    public int qtPar = 0;

    public int qtImpar = 0;

    public int qtPrimo = 0;

    public int qtFatorial = 0;

    public int qtPerfeito = 0;

    public int qtAbundante = 0;

    public int qtDeficiente = 0;

    public int qtImperfeito = 0;

    public int qtQuadrado = 0;

    public int qtFibonacci = 0;

    // estatisticas do valor das dezenas do concurso:

    public int dzSequencia = 0; // Numero de dezenas dispostas em sequencia.

    public int dzSeguidas = 0; // Numero maximo de dezenas em seguida.

    public int dzDistancia = 0; // Distancia entre a menor e maior dezena.

    public int dzSoma = 0;

    public int dzMedia = 0;

    // --- Frequencias de ocorrencias, repeticoes e atrasos das dezenas -------

    public int qtRepetidas = 0; // Quantidade de dezenas que repetiram (eco) desde o ultimo concurso.

    public int qtPenRepetidas = 0; // Quantidade de dezenas que repetiram em relacao ao penultimo concurso.

    // Quantidade maxima de dezenas que "acertaram" em todos os concursos.
    public int qtAcertos = 0; // Tambem equivalente a pontuacao do concurso.

    // --- Ciclo das dezenas sorteadas (15) e da lotofacil (25) -----------

    public int ciclo15 = 0;

    public int ciclo25 = 0;

    // --- Distribuicao combinatoria das dezenas em linhas e colunas ----------

    public String distBinario; // eg: 0101101010011011101110101

    public String distLinhas; // eg: "3-1-4-3-4"

    public String distColunas; // eg: "3-1-4-3-4"

    // --- Parsing ------------------------------------------------------------

    /*
     * calcula as propriedades e metricas da aposta, a partir das dezenas sorteadas.
     */
    public ApostaBean parseDezenas(final LoteriaBean p_loteria) {
        this.qtInferior = 0;
        this.qtSuperior = 0;
        this.qtPar = 0;
        this.qtImpar = 0;
        this.qtPrimo = 0;
        this.qtFatorial = 0;
        this.qtPerfeito = 0;
        this.qtAbundante = 0;
        this.qtDeficiente = 0;
        this.qtImperfeito = 0;
        this.qtQuadrado = 0;
        this.qtFibonacci = 0;
        this.dzSequencia = 0;
        this.dzSeguidas = 0;
        this.dzDistancia = 0;
        this.dzSoma = 0;
        this.dzMedia = 0;

        // identifica a distribuicao combinatoria das dezenas em linhas e colunas no concurso.
        int c1 = 0; // primeira coluna
        int c2 = 0; // segunda coluna
        int c3 = 0; // terceira coluna
        int c4 = 0; // quarta coluna
        int c5 = 0; // quinta coluna

        int l1 = 0; // primeira linha
        int l2 = 0; // segunda linha
        int l3 = 0; // terceira linha
        int l4 = 0; // quarta linha
        int l5 = 0; // quinta linha

        // ajuda a compor a distribuicao binaria da aposta.
        char[] binario = {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', //
                          '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'}; // 25

        // variaveis auxiliares para identificar dezenas em sequencia.
        int dez, dezAnterior = -1;
        boolean emSequencia = false;
        int emSeguida = 0;

        // contabiliza as metricas para o jogo, percorrendo todas as 15 dezenas do jogo.
        for (int i = 0; i < 15; i++) {
            dez = this.ordenadas[i];
            this.dezenas[i] = DezenaBean.valueOf(dez);

            // marca na distribuicao binaria que a dezena foi sorteada:
            binario[i] = '1';

            // calcula as metricas para o jogo:
            if (this.dezenas[i].isInferior) this.qtInferior++;
            if (this.dezenas[i].isSuperior) this.qtSuperior++;
            if (this.dezenas[i].isPar) this.qtPar++;
            if (this.dezenas[i].isImpar) this.qtImpar++;
            if (this.dezenas[i].isPrimo) this.qtPrimo++;
            if (this.dezenas[i].isFatorial) this.qtFatorial++;
            if (this.dezenas[i].isPerfeito) this.qtPerfeito++;
            if (this.dezenas[i].isAbundante) this.qtAbundante++;
            if (this.dezenas[i].isDeficiente) this.qtDeficiente++;
            if (this.dezenas[i].isImperfeito) this.qtImperfeito++;
            if (this.dezenas[i].isQuadrado) this.qtQuadrado++;
            if (this.dezenas[i].isFibonacci) this.qtFibonacci++;

            this.dzSoma += dez; // calcula depois a media.

            // verifica a distribuicao em termos de linhas:
            if (dez <= 5) l1++;
            if (dez >= 6 && dez <= 10) l2++;
            if (dez >= 11 && dez <= 15) l3++;
            if (dez >= 15 && dez <= 20) l4++;
            if (dez >= 21) l5++;

            // verifica a distribuicao em termos de colunas:
            if (dez == 1 || dez == 6 || dez == 11 || dez == 16 || dez == 21) c1++;
            if (dez == 2 || dez == 7 || dez == 12 || dez == 17 || dez == 22) c2++;
            if (dez == 3 || dez == 8 || dez == 13 || dez == 18 || dez == 23) c3++;
            if (dez == 4 || dez == 9 || dez == 14 || dez == 19 || dez == 24) c4++;
            if (dez == 5 || dez == 10 || dez == 15 || dez == 20 || dez == 25) c5++;

            // verifica a dezena atual esta em sequencia com a anterior:
            if (dez == (dezAnterior + 1)) { // se a dezena corrente vem logo a seguir a anterior:
                if (emSequencia) {
                    // se ja estiver em modo sequencia, entao eh apenas mais uma em sequencia.
                    this.dzSequencia++;

                    // mais uma dezena em seguida.
                    emSeguida++;
                    if (emSeguida > this.dzSeguidas) this.dzSeguidas = emSeguida;

                } else {
                    // se nao estiver em sequencia, ativa entao o modo-sequencia.
                    emSequencia = true;
                    this.dzSequencia += 2; // adiciona ambas dezenas na contagem.

                    // uma nova sequencia de dezenas em seguida.
                    emSeguida = 2; // inicia com 2 dezenas em seguida.
                    if (emSeguida > this.dzSeguidas) this.dzSeguidas = emSeguida;
                }

            } else { // se for diferente, entao nao esta mais em modo sequencia.
                emSequencia = false;
                emSeguida = 0; // zera o contador de dezenas em seguida.
            }

            // atualiza a sequencia anterior:
            dezAnterior = dez;

        } // ao final, o array dezenas[] possui as dezenas sorteadas, ja ordenadas.

        // finaliza calculando a media e a maior distancia entre as dezenas extremas.
        this.dzMedia = this.dzSoma / 15; // media como valor inteiro.
        this.dzDistancia = this.ordenadas[14] - this.ordenadas[0];

        // distribuicao combinatoria do concurso:
        this.distLinhas = l1 + "-" + l2 + "-" + l3 + "-" + l4 + "-" + l5; // distribuicao em termos de linhas
        this.distColunas = c1 + "-" + c2 + "-" + c3 + "-" + c4 + "-" + c5; // distribuicao em termos de colunas

        // varre o vetor de representacao binaria de dezenas, para compor a distribuicao binaria da aposta.
        StringBuilder binStr = new StringBuilder(25);
        for (char dig : binario) {
            binStr.append(dig);
        }
        this.distBinario = binStr.toString();

        /*
         * calcula demais propriedades dependentes do historico de concursos da loteria.
         */

        // calculo de qtRepetidas.
        this.qtRepetidas = p_loteria.countUltRepetidas(this.ordenadas);

        // calculo de qtPenRepetidas.
        this.qtPenRepetidas = p_loteria.countPenRepetidas(this.ordenadas);

        // calculo de qtAcertos.
        this.qtAcertos = p_loteria.countMaxAcertos(this.ordenadas);

        // ciclo das 15 ultimas dezenas sorteadas
        this.ciclo15 = p_loteria.countCiclo_15(this.ordenadas);

        // ciclo das 25 dezenas lotofacil
        this.ciclo25 = p_loteria.countCiclo_25(this.ordenadas);
        
        // retorna a propria instancia, para permitir fluent-interface:
        return this;
    }

    // --- Object -------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Lot.formatDez(this.id, 4));

        sb.append("  [ ");
        for (int i = 0; i < this.ordenadas.length; i++) {
            sb.append(Lot.formatDez(this.ordenadas[i], 2)).append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    /*
     * cria uma nova instancia de ApostaBean, preenchendo todas as suas propriedades.
     */
    public static ApostaBean parseAposta(final int p_id, final int[] p_ordenadas, final LoteriaBean p_loteria) {
        ApostaBean aposta = new ApostaBean(p_id, p_ordenadas);

        // calcula o maximo de propriedades dependentes das dezenas:
        aposta.parseDezenas(p_loteria);

        return aposta;
    }
}
