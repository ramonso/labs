import java.util.Locale;
import java.util.concurrent.TimeUnit;

import lotofacil.LotofacilManager;
import util.$;

import static util.$.*;

/*
 * Eclipse working directory: D:\Crediare.com\git\loto.
 */
public final class Main {

    /*
     *
     */
    public static void main(final String[] args) {
        // Se nao foi fornecido o arquivo zip com resultados, ja finaliza com erro.
        if (isEmpty(args) || isEmpty(args[0])) {
            log("ERRO: Arquivo ZIP inexistente! Forneca o nome do arquivo ZIP contendo resultados da LotoFacil.");
            System.exit(1);
        }

        // muda o locale para USA para formatar os valores decimais com ponto decimal.
        // se deixar o default da JVM, fica Brasil, com decimal formatado com virgula decimal.
        Locale.setDefault(Locale.US);

        // Contabiliza o tempo gasto.
        long millis = System.currentTimeMillis();

        // Efetua o processamento da loteria Lotofacil, a partir do arquivo ZIP fornecido.
        LotofacilManager lfManager = new LotofacilManager(args[0]);
        lfManager.run();

        // Contabiliza e apresenta o tempo gasto.
        millis = System.currentTimeMillis() - millis;
        $.log("\n\n >> TEMPO DE PROCESSAMENTO: {0}", String.format("%d min, %d seg", //
                                                                   TimeUnit.MILLISECONDS.toMinutes(millis), //
                                                                   TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));

        // Processamento realizado com sucesso!
        System.exit(0);
    }
}
