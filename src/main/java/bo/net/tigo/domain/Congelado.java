package bo.net.tigo.domain;

/**
 * Created by aralco on 10/22/14.
 */
public class Congelado {
    private final String nombre;
    private final String url;

    public Congelado(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }
}
