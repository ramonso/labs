package process;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entity.ConcursoBean;
import entity.DezenaBean;
import entity.LoteriaBean;
import util.$;
import util.Lot;

public class BinarioProcess implements Runnable {

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public BinarioProcess(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    public Map<String, Integer> mapMagcLinhas = new TreeMap<String, Integer>();

    public Map<String, Integer> mapMagcColunas = new TreeMap<String, Integer>();

    public Map<String, Integer> mapMagcTotal = new TreeMap<String, Integer>();

    public int incMagcLinhas(final String p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapMagcLinhas.containsKey(p_key)) {
            val = mapMagcLinhas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapMagcLinhas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    public int incMagcColunas(final String p_key) {
        int val = 0; // caso nao exista ainda valor para a chave, comeca do zero.

        if (mapMagcColunas.containsKey(p_key)) {
            val = mapMagcColunas.get(p_key); // utiliza o valor atual para incrementar.
        }
        mapMagcColunas.put(p_key, ++val);

        // retorna o novo valor apos o incremento.
        return val;
    }

    /*
     *
     */
    @Override
    public void run() {
        String[] binDezenas = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""}; // 25

        // controle da distribuicao magica das linhas e colunas de dezenas:
        int ml1, ml2, ml3, ml4, ml5; // linhas
        int mc1, mc2, mc3, mc4, mc5; // colunas
        String magcLinhas, magcColunas;

        // percorre os resultados a partir do primeiro concurso.
        for (int i = 0; i < this.loteria.lenConcursos; i++) {
            // concurso a ser processado.
            ConcursoBean concurso = this.loteria.concursos[i];

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

            ml1 = ml2 = ml3 = ml4 = ml5 = 0; // linhas
            mc1 = mc2 = mc3 = mc4 = mc5 = 0; // colunas

            // verifica cada uma das dezenas quanto a sua classificacao:
            for (DezenaBean dezena : concurso.dezenas) {
                int dez = dezena.numero; // melhora a legibilidade.

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

                //
                if (dez == 11 || dez == 4 || dez == 17 || dez == 10 || dez == 23) mc1++;
                if (dez == 24 || dez == 12 || dez == 5 || dez == 18 || dez == 6) mc2++;
                if (dez == 7 || dez == 25 || dez == 13 || dez == 1 || dez == 19) mc3++;
                if (dez == 20 || dez == 8 || dez == 21 || dez == 14 || dez == 2) mc4++;
                if (dez == 3 || dez == 16 || dez == 9 || dez == 22 || dez == 15) mc5++;

                if (dez == 11 || dez == 24 || dez == 7 || dez == 20 || dez == 3) ml1++;
                if (dez == 4 || dez == 12 || dez == 25 || dez == 8 || dez == 16) ml2++;
                if (dez == 17 || dez == 5 || dez == 13 || dez == 21 || dez == 9) ml3++;
                if (dez == 10 || dez == 18 || dez == 1 || dez == 14 || dez == 22) ml4++;
                if (dez == 23 || dez == 6 || dez == 19 || dez == 2 || dez == 15) ml5++;

            }

            // distribuicao combinatoria do concurso:
            concurso.distLinhas = l1 + "-" + l2 + "-" + l3 + "-" + l4 + "-" + l5; // distribuicao em termos de linhas
            concurso.distColunas = c1 + "-" + c2 + "-" + c3 + "-" + c4 + "-" + c5; // distribuicao em termos de colunas

            magcLinhas = ml1 + "-" + ml2 + "-" + ml3 + "-" + ml4 + "-" + ml5;
            magcColunas = mc1 + "-" + mc2 + "-" + mc3 + "-" + mc4 + "-" + mc5;


            /*
             * atualiza estrutura da loteria com as metricas calculadas:
             */
            this.loteria.incDistLinhas(concurso.distLinhas);
            this.loteria.incDistColunas(concurso.distColunas);

            //
            this.incMagcLinhas(magcLinhas);
            this.incMagcColunas(magcColunas);

            // calcula o numero de combinacoes possiveis para a distribuicao:
            int total = Lot.combinacoes(5, l1) * Lot.combinacoes(5, l2) * Lot.combinacoes(5, l3) * Lot.combinacoes(5, l4) * Lot.combinacoes(5, l5);
            this.loteria.mapDistTotal.put(concurso.distLinhas, total);
            total = Lot.combinacoes(5, c1) * Lot.combinacoes(5, c2) * Lot.combinacoes(5, c3) * Lot.combinacoes(5, c4) * Lot.combinacoes(5, c5);
            this.loteria.mapDistTotal.put(concurso.distColunas, total);

            //
            total = Lot.combinacoes(5, ml1) * Lot.combinacoes(5, ml2) * Lot.combinacoes(5, ml3) * Lot.combinacoes(5, ml4) * Lot.combinacoes(5, ml5);
            this.mapMagcTotal.put(magcLinhas, total);
            total = Lot.combinacoes(5, mc1) * Lot.combinacoes(5, mc2) * Lot.combinacoes(5, mc3) * Lot.combinacoes(5, mc4) * Lot.combinacoes(5, mc5);
            this.mapMagcTotal.put(magcColunas, total);
            
            
            // compoe a distribuicao das dezenas no mesmo formato da cartela.
            StringBuilder distBinario = new StringBuilder(25);
            StringBuilder distCartao = new StringBuilder(125); // concatena as dezenas no mesmo formato do cartao.
            for (int dez = 1; dez <= 25; dez++) {
                if (concurso.isDezSorteada(dez)) {
                    distBinario.append('1');
                    distCartao.append("[\u2588\u2588] ");
                    binDezenas[dez - 1] += '1';
                } else {
                    distBinario.append('0');
                    distCartao.append(MessageFormat.format("[{0,number,00}] ", dez));
                    binDezenas[dez - 1] += '0';
                }
            }
            concurso.distBinario = distBinario.toString();
            concurso.distCartao = distCartao.toString();
        }

        // $.log("\n\n");

        // imprime os maps de linhas e colunas lado-a-lado, em ordem decrescente:
//        List<Map.Entry<String, Integer>> sortedLin = $.sortByValue(this.loteria.mapDistLinhas);
//        int idxLin = sortedLin.size();
//        List<Map.Entry<String, Integer>> sortedCol = $.sortByValue(this.loteria.mapDistColunas);
//        int idxCol = sortedCol.size();
//
//        while (--idxLin > -1 | --idxCol > -1) {
//            if (idxLin > -1) {
//                System.out.print("Dist.Linhas: " + sortedLin.get(idxLin).getKey() + " == " + //
//                                 Lot.formatDez(sortedLin.get(idxLin).getValue(), 2) + " / " + //
//                                 Lot.formatDez(this.loteria.mapDistTotal.get(sortedLin.get(idxLin).getKey()), 6));
//            }
//            if (idxCol > -1) {
//                System.out.print("   ...............   Dist.Colunas: " + sortedCol.get(idxCol).getKey() + " == " + //
//                                 Lot.formatDez(sortedCol.get(idxCol).getValue(), 2) + " / " + //
//                                 Lot.formatDez(this.loteria.mapDistTotal.get(sortedCol.get(idxCol).getKey()), 6));
//            }
//
//            System.out.println("");
//        }

//        $.log("\n\n");

        // imprime os maps de linhas e colunas do cubo magico lado-a-lado, em ordem decrescente:
//        sortedLin = $.sortByValue(this.mapMagcLinhas);
//        idxLin = sortedLin.size();
//        sortedCol = $.sortByValue(this.mapMagcColunas);
//        idxCol = sortedCol.size();
//
//        while (--idxLin > -1 | --idxCol > -1) {
//            if (idxLin > -1) {
//                System.out.print("Magc.Linhas: " + sortedLin.get(idxLin).getKey() + " == " + //
//                                 Lot.formatDez(sortedLin.get(idxLin).getValue(), 2) + " / " + //
//                                 Lot.formatDez(this.mapMagcTotal.get(sortedLin.get(idxLin).getKey()), 6));
//            }
//            if (idxCol > -1) {
//                System.out.print("   ...............   Magc.Colunas: " + sortedCol.get(idxCol).getKey() + " == " + //
//                                 Lot.formatDez(sortedCol.get(idxCol).getValue(), 2) + " / " + //
//                                 Lot.formatDez(this.mapMagcTotal.get(sortedCol.get(idxCol).getKey()), 6));
//            }
//
//            System.out.println("");
//        }

//        $.log("\n\n");

        // imprime os maps de combinacoes de dezenas sorteadas e nao sorteadas, em ordem decrescente.
        final int max = binDezenas[0].length();

        for (int ciclo = 2; ciclo <= 10; ciclo++) {
            // limpa a estrutura para cada ciclo:
            TreeMap<String, Integer> mapBinDezenas = new TreeMap<String, Integer>();
            int total = 0;

            for (int idz = 0; idz < 25; idz++) {
                int cont, end, begin = 0; // posicionamento para fazer o substring.
                String bin;
                do {
                    end = begin + ciclo;
                    if (end >= max) {
                        bin = binDezenas[idz].substring(begin);

                    } else {
                        bin = binDezenas[idz].substring(begin, end);
                    }

                    cont = 0;
                    if (mapBinDezenas.containsKey(bin)) {
                        cont = mapBinDezenas.get(bin);
                    }
                    mapBinDezenas.put(bin, ++cont);

                    // totaliza o contador para depois calcular o percentual.
                    total++;

                    begin += ciclo;
                } while (begin < max);
            }

            List<Map.Entry<String, Integer>> list = $.sortByValue(mapBinDezenas);
            int perc, acum = 0;
            for (int idx = list.size() - 1; idx >= 0; idx--) {
                perc = (list.get(idx).getValue() * 100) / total;
                acum += perc;

                // System.out.println("Bin.Dezenas: " + list.get(idx).getKey() + " == " + //
                // Lot.formatDez(list.get(idx).getValue(), 5) + " / " + //
                // Lot.formatDez(total, 4) + "   >   " + //
                // Lot.formatDez(perc, 2) + "%  ...  §= " + //
                // Lot.formatDez(acum, 2) + "%");
            }

            // $.log("\n\n");
        }

    }
}
