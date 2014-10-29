package bo.net.tigo.domain;

import java.util.List;

/**
 * Created by aralco on 10/22/14.
 */
public class Libres {
    private final List<Libre> libres;

    public Libres(List<Libre> libres) {
        this.libres = libres;
    }

    public List<Libre> getLibres() {
        return libres;
    }
}
