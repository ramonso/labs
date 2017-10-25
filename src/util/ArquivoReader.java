package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public final class ArquivoReader {

    public final File file;

    public final long length;

    private FileReader fileReader;

    private BufferedReader buffReader;

    private String lastLine;

    private int numberLine;

    public ArquivoReader(final String filePath) {
        if (filePath == null || filePath.trim().length() == 0) {
            throw new IllegalArgumentException("Nome de arquivo eh nulo ou invalido!");
        }

        // O arquivo deve ser textual, pois sera lido linha-a-linha.
        try {
            file = new File(filePath);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Arquivo [" + filePath + "] nao eh valido!");
        }

        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new IllegalArgumentException("Arquivo [" + filePath + "] nao pode ser lido!");
        }

        length = file.length();
    }

    public final void open() {
        try {
            // Possibilita a leitura de linha-a-linha, ja que sempre sera arquivo texto.
            fileReader = new FileReader(file);
            buffReader = new BufferedReader(fileReader);

        } catch (Throwable e) {
            close();
            throw new RuntimeException(e.getMessage(), e);
        }

        // Zera apontadores de linhas;
        numberLine = 0;
        lastLine = null;
    }

    public final void close() {
        // Fecha na ordem em que foi criado, e garante que nao havera mais referencia para qualquer recurso.
        try {
            buffReader.close();
        } catch (Throwable e) {
        } finally {
            buffReader = null;
        }

        try {
            fileReader.close();
        } catch (Throwable e) {
        } finally {
            fileReader = null;
        }

        // Zera apontadores de linhas;
        numberLine = 0;
        lastLine = null;
    }

    public final boolean isReady() {
        try {
            return (buffReader != null && buffReader.ready());

        } catch (Throwable e) {
            return false;
        }
    }

    public final String getNextLine() {
        try {
            if (isReady()) {
                lastLine = buffReader.readLine();
                numberLine++;
                return lastLine;
            }

        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return null;
    }

    public final String getLastLine() {
        return lastLine;
    }

    public Integer getNumberLine() {
        return numberLine;
    }
}
