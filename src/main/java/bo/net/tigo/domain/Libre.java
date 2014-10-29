package bo.net.tigo.domain;

/**
 * Created by aralco on 10/22/14.
 */
public class Libre {
    private final String nombre;
    private final String url;

    public Libre(String nombre, String url) {
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
