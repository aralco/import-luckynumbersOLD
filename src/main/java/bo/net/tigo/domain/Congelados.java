package bo.net.tigo.domain;

import java.util.List;

/**
 * Created by aralco on 10/22/14.
 */
public class Congelados {
    private final List<Congelado> congelados;

    public Congelados(List<Congelado> congelados) {
        this.congelados = congelados;
    }

    public List<Congelado> getCongelados() {
        return congelados;
    }
}
