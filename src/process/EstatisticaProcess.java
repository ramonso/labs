package process;

import entity.LoteriaBean;

public class EstatisticaProcess implements Runnable {

    // --- Propriedades -------------------------------------------------------

    // estrutura principal da loteria, contendo as principais estatisticas.
    private final LoteriaBean loteria;

    public EstatisticaProcess(final LoteriaBean p_loteria) {
        super();

        this.loteria = p_loteria;
    }

    /*
     *
     */
    @Override
    public void run() {
    }

    /*
     * 
     */
    // public void runTest() {}
}
