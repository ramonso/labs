package lotofacil;

import process.BinarioProcess;
import process.CicloProcess;
import process.EstatisticaProcess;
import process.FrequenciaProcess;
import process.NaturalProcess;
import util.Lot;
import filter.ParetoFilter;
import filter.ParidadeFilter;

import static util.$.*;

import entity.ApostaBean;
import entity.LoteriaBean;

public final class LotofacilManager implements Runnable {

    // --- Propriedades ---------------------------------------------------------

    public final String zipFile;

    // estrutura principal da loteria, contendo as principais estatisticas.
    private LoteriaBean loteria = new LoteriaBean();

    // --- Construtor ---------------------------------------------------------

    public LotofacilManager(final String p_zipFile) {
        super();

        zipFile = p_zipFile;
    }

    // --- Operacoes ---------------------------------------------------------

    /*
     * Carrega os resultados da lotofacil atraves do arquivo HTML descompactado.
     */
    public int load(final String p_filePath) {
        // leitor do arquivo de resultados.
        final LotofacilReader lfReader = new LotofacilReader(p_filePath);

        // Le o ultimo arquivo de resultados fornecidos e carrega os concursos.
        this.loteria.setConcursos(lfReader.read());

        // retorna o numero de concursos lidos do arquivo de resultados.
        return this.loteria.lenConcursos;
    }

    /*
     * Gera apostas para todas as combinacoes possiveis de jogos para a Lotofacil.
     */
    public int generate() {
        // gera todas as 3.268.760 combinacoes para a Lotofacil.
        final int[][] jogos = Lot.geraCombinacoes(25, 15); // total de 25 dezenas, 15 dezenas por combinacao.
        final int lenJogos = jogos.length;

        this.loteria.lenApostas = lenJogos;
        this.loteria.apostas = new ApostaBean[lenJogos];

        // percorre todos os jogos para gerar os objetos ApostaBean.
        for (int i = 0; i < lenJogos; i++) {
            // por enquanto, apenas gera o objeto ApostaBean, para posterior parsing nos filtros.
            // this.loteria.apostas[i] = ApostaBean.parseAposta(i + 1, jogos[i], this.loteria);
            this.loteria.apostas[i] = new ApostaBean(i + 1, jogos[i]);
        }

        // retorna o numero de apostas geradas, para todas as combinacoes da Lotofacil.
        return lenJogos;
    }

    /*
     *
     */
    @Override
    public void run() {
        // (1) Descompacta o arquivo de resultados no diretorio corrente da execucao.
        log("1> Descompactando arquivo: {0}", zipFile);
        final String[] arquivos = unzipFile(zipFile);
        log("\t Arquivo ZIP descompactado com sucesso!\n");

        if (isEmpty(arquivos) || isEmpty(arquivos[0])) {
            log("ERRO: Nenhum arquivo descompactado! Forneca um arquivo ZIP valido, contendo resultados da LotoFacil.");
            System.exit(1);
        }

        // Identifica o arquivo de resultados da loteria (*.HTM).
        String arqLoteria = null;
        for (String arquivo : arquivos) {
            // o arquivo de resultados eh um arquivo HTML.
            if (arquivo.toLowerCase().endsWith(".htm") || arquivo.toLowerCase().endsWith(".html")) {
                arqLoteria = arquivo;
            }
        }
        if (isEmpty(arqLoteria)) {
            log("ERRO: Arquivo de resultados nao encontrado! Forneca um arquivo ZIP valido, contendo resultados da LotoFacil.");
            System.exit(1);
        }

        // (2) Efetua leitura dos concursos a partir do arquivo de resultados descompactado.
        log("2> Lendo arquivo de resultados: {0}", arqLoteria);
        // Apenas precisa carregar uma unica vez.
        load(arqLoteria);
        log("\t Encontrados {0} concursos da loteria.", this.loteria.lenConcursos);
        log("\t Arquivo de resultados carregado com sucesso!\n");

        // (3) Executa os processos para calculos das dezenas.
        log("3> Executando processamento de calculos para as dezenas dos concursos:");

        log("\t Realizando calculos de numeros naturais para as dezenas...");
        NaturalProcess naturalProcess = new NaturalProcess(this.loteria);
        naturalProcess.run();

        log("\t Calculando os fechamentos de ciclos das dezenas...");
        CicloProcess cicloProcess = new CicloProcess(this.loteria);
        cicloProcess.run();

        log("\t Calculando as frequencias das dezenas...");
        FrequenciaProcess frequenciaProcess = new FrequenciaProcess(this.loteria);
        frequenciaProcess.run();

        log("\t Processando distribuicao binaria e posicional das dezenas...");
        BinarioProcess binarioProcess = new BinarioProcess(this.loteria);
        binarioProcess.run();

        log("\t Calculando estatisticas para as dezenas...");
        EstatisticaProcess estatisticaProcess = new EstatisticaProcess(this.loteria);
        estatisticaProcess.run();

        log("\t Processamento de concursos executado com sucesso!\n");

        // (4) Imprime resultado em dashboard para maior legibilidade.
        // log("4> Imprimindo resultados em Dashboard para maior legibilidade:");
        // DashboardProcess dashboardProcess = new DashboardProcess(this.loteria);
        // dashboardProcess.run();

        // (5) Avaliando melhor estrategia para geracao de apostas.
        log("5> Realizando analise de estrategias para geracao das apostas:");

        // generate(); // gera todas as 3.268.760 combinacoes para a Lotofacil.
        // log("\t Geradas {0} apostas da Lotofacil para filtros.", this.loteria.lenApostas);

        log("\t Analisando a simetria de paridades (12) das 25 dezenas...");
        ParidadeFilter paridadeFilter = new ParidadeFilter(this.loteria);
        paridadeFilter.run();

        log("\t Avaliando metricas das dezenas sorteadas pela Curva ABC (Pareto)...");
        ParetoFilter paretoStrategy = new ParetoFilter(this.loteria);
        paretoStrategy.run();

        log("\t Gerando apostas de forma aleatoria, sorteadas por Pareto...");
    }
}
