package util;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import entity.ConcursoBean;

import static util.$.*;

public abstract class ResultadosReader<T extends ConcursoBean> {

    private final ArquivoReader arqReader;

    protected ResultadosReader(final String filePath) {
        super();

        this.arqReader = new ArquivoReader(filePath);
    }

    protected void open() {
        this.arqReader.open();
    }

    protected void close() {
        this.arqReader.close();
    }

    protected long getFileSize() {
        return this.arqReader.length;
    }

    protected TRow getNextRow() throws EOFException {
        // armazena a proxima linha (texto) lida do arquivo.
        String line = ""; // "" apenas para passar no teste da primeira iteracao.

        // armazena uma linha de tabela (<tr>) completa, referente a resultado.
        StringBuilder row = new StringBuilder();

        // no primeiro loop, segue lendo ate encontrar primeira linha (<tr>) de resultado (row).
        while (arqReader.isReady()) {
            line = arqReader.getNextLine();

            // valor de linha nula significa que chegou no fim do arquivo.
            if (line == null) {
                break; // mais a frente ira verificar o que ocorreu.
            }

            // linha vazia ("") sera ignorada, e sera lida a proxima.
            if (isNotEmpty(line)) {
                line = line.trim();
                if (line.startsWith("<tr")) {
                    row.append(line);
                    break; // encontrou o inicio da linha, entao tem que procurar pelo final (/tr>).
                }
            }
        }

        // se nao conseguiu ler nenhuma linha (row) valida, entao chegou no fim do arquivo.
        if ($.isEmpty(row)) {
            throw new EOFException();

        } else if (row.toString().endsWith("/tr>")) {
            // se a linha lida ja possui as 2 tags (<tr> </tr>), entao leu uma linha (row) inteira.
            return new TRow(row.toString()); // retorna a linha (row) valida lida.
        }

        // no segundo loop. segue lendo as linhas de resultados (row) ate chegar ao final da linha (/tr>).
        while (arqReader.isReady()) {
            line = arqReader.getNextLine();

            // valor de linha nula significa que chegou no fim do arquivo.
            if (line == null) {
                break; // mais a frente ira verificar o que ocorreu.
            }

            // linha vazia ("") sera ignorada, e sera lida a proxima.
            if (isNotEmpty(line)) {
                line = line.trim();
                row.append(line);
                if (line.endsWith("/tr>")) {
                    break; // encontrou o final da linha.
                }
            }
        }

        // se nao conseguiu ler nenhuma linha (row) valida, entao chegou no fim do arquivo.
        if ($.isEmpty(row)) {
            throw new EOFException();
        }

        // retorna a linha (row) valida lida.
        return new TRow(row.toString());
    }

    public abstract T[] read();

    // --- Classe TRow --------------------------------------------------------

    /**
     * Classe criada para manipular uma linha de tabela de forma estruturada.
     *
     * Identifica automaticamente as colunas (TD) separadamente da tabela para facilitar
     * a carga de resultados.
     *
     * @category util.
     */
    protected static class TRow {
        public final String text;

        public final int length;

        public final String[] cells;

        public final int size;

        public TRow(final String p_text) {
            super();

            text = p_text;

            if ($.isEmpty(p_text)) {
                length = 0;
                cells = null;
                size = 0;

            } else {
                length = p_text.length();
                cells = parseData(p_text);
                size = $.isEmpty(cells) ? 0 : cells.length;
            }
        }

        private String[] parseData(final String p_text) {
            final List<String> tdata = new ArrayList<String>(2);

            // percorre o texto a medida que pesquisa '<'>', do inicio ao final (tamanho).
            int len = p_text.length();
            int begin = 0; // inicia a pesquisa pelo inicio do texto, e vai ate seu tamanho.
            int end = len - 1; // posicao do ultimo caractere no texto.

            int ini; // offset relativo ao inicio do texto contendo dado (TD)
            while (begin < end) {
                begin = p_text.indexOf("<td", begin);
                if (begin == -1) break; // se nao tem mais data, entao pula fora.

                begin = p_text.indexOf(">", begin);
                if (begin == -1 || begin == end) break; // se o '>' esta no final da linha, ignora.

                ini = begin + 1; // o texto do dado inicia apos o '>'
                begin = p_text.indexOf("<", begin); // procura o final do texto do dado.
                if (begin == -1) break; // se o '>' nao esta presente, ignora porque a linha esta invalida.

                // adiciona o dado encontrato, delimitado por '<'>'.
                tdata.add(p_text.substring(ini, begin)); // o caractere apontado por begin eh exclusivo.
            }

            // evita criar objetos se nao ha elementos no array.
            return (tdata.size() > 0) ? tdata.toArray(new String[tdata.size()]) : null;
        }

        public boolean isEmpty() {
            return (size == 0);
        }
    }

}
