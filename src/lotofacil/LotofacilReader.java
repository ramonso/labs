package lotofacil;

import java.io.EOFException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.ConcursoBean;
import entity.DezenaBean;
import util.ResultadosReader;

import static util.$.*;

public final class LotofacilReader extends ResultadosReader<ConcursoBean> {

    public static final int TAMANHO_MINIMO_TROW = 250;

    public static final int TAMANHO_MEDIO_TROW = 1111;

    public LotofacilReader(final String p_filePath) {
        super(p_filePath);
    }

    /*
     *
     */
    private final ConcursoBean parseConcurso(final String[] p_cells) throws ParseException {
        final DezenaBean[] dezenas = new DezenaBean[15];
        final int[] ordenadas = new int[15];

        ordenadas[0] = parseInteger(p_cells[2]);
        dezenas[0] = DezenaBean.valueOf(ordenadas[0]);

        ordenadas[1] = parseInteger(p_cells[3]);
        dezenas[1] = DezenaBean.valueOf(ordenadas[1]);

        ordenadas[2] = parseInteger(p_cells[4]);
        dezenas[2] = DezenaBean.valueOf(ordenadas[2]);

        ordenadas[3] = parseInteger(p_cells[5]);
        dezenas[3] = DezenaBean.valueOf(ordenadas[3]);

        ordenadas[4] = parseInteger(p_cells[6]);
        dezenas[4] = DezenaBean.valueOf(ordenadas[4]);

        ordenadas[5] = parseInteger(p_cells[7]);
        dezenas[5] = DezenaBean.valueOf(ordenadas[5]);

        ordenadas[6] = parseInteger(p_cells[8]);
        dezenas[6] = DezenaBean.valueOf(ordenadas[6]);

        ordenadas[7] = parseInteger(p_cells[9]);
        dezenas[7] = DezenaBean.valueOf(ordenadas[7]);

        ordenadas[8] = parseInteger(p_cells[10]);
        dezenas[8] = DezenaBean.valueOf(ordenadas[8]);

        ordenadas[9] = parseInteger(p_cells[11]);
        dezenas[9] = DezenaBean.valueOf(ordenadas[9]);

        ordenadas[10] = parseInteger(p_cells[12]);
        dezenas[10] = DezenaBean.valueOf(ordenadas[10]);

        ordenadas[11] = parseInteger(p_cells[13]);
        dezenas[11] = DezenaBean.valueOf(ordenadas[11]);

        ordenadas[12] = parseInteger(p_cells[14]);
        dezenas[12] = DezenaBean.valueOf(ordenadas[12]);

        ordenadas[13] = parseInteger(p_cells[15]);
        dezenas[13] = DezenaBean.valueOf(ordenadas[13]);

        ordenadas[14] = parseInteger(p_cells[16]);
        dezenas[14] = DezenaBean.valueOf(ordenadas[14]);

        // ordena as dezenas sorteadas em ordem numeral.
        Arrays.sort(ordenadas);

        return new ConcursoBean(parseInteger(p_cells[0]), // id (numero) do concurso.
                                parseDate(p_cells[1]).getTime(), // data do sorteio.
                                dezenas, ordenadas); // dezenas sorteadas.
    }

    /*
     *
     */
    @Override
    public final ConcursoBean[] read() {
        // pelo tamanho do arquivo, calcula uma media de numero de resultados a serem lidos.
        final int avgConcursos = (int) (getFileSize() / TAMANHO_MEDIO_TROW);
        // Inicializa a estrutura resultante antes de mais nada.
        final List<ConcursoBean> listConcursos = new ArrayList<ConcursoBean>(avgConcursos);

        try {
            open();

            // pula a primeira linha (<tr>) de cabecalho, e posiciona na segunda tag <tr>
            TRow trow = getNextRow();

            // inicia a leitura dos resultados, ate o final do arquivo (EOFException).
            for (trow = getNextRow();; trow = getNextRow()) {
                // se for uma linha de cidade (ao final do resultado), ignora.
                if (trow.length < TAMANHO_MINIMO_TROW || trow.size <= 2) continue;

                // mantem os valores de cada concurso, um item de array pra cada propriedade
                listConcursos.add(parseConcurso(trow.cells));

            } // segue lendo cada linha para montar os resultados, ate chegar no final (EOFException).

        } catch (EOFException e) {
            // chegou no fim do arquivo.

        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);

        } finally {
            // garante que sempre ira fechar o arquivo no final da leitura.
            close();
        }

        return listConcursos.toArray(new ConcursoBean[listConcursos.size()]);
    }
}
