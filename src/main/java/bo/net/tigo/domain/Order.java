package bo.net.tigo.domain;

/**
 * Created by aralco on 10/29/14.
 */
public class Order {
    private final int id;
    private final String orderType;
    private final int city;
    private final String from;
    private final String to;
    private final String status;//{SCHEDULED,STARTED,COMPLETED_OK,CANCELLED,COMPLETED_WITH_ERRORS}
    private final String description;
    private final int numberQuantity;
    private final String coverage;//0%-100%
    private final String urlin;//link para descargar .in file?
    private final String urlout;//link para descargar .out file?

    public Order(int id, String orderType, int city, String from, String to, String status, String description, int numberQuantity, String coverage, String urlin, String urlout) {
        this.id = id;
        this.orderType = orderType;
        this.city = city;
        this.from = from;
        this.to = to;
        this.status = status;
        this.description = description;
        this.numberQuantity = numberQuantity;
        this.coverage = coverage;
        this.urlin = urlin;
        this.urlout = urlout;
    }

    public int getId() {
        return id;
    }

    public String getOrderType() {
        return orderType;
    }

    public int getCity() {
        return city;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberQuantity() {
        return numberQuantity;
    }

    public String getCoverage() {
        return coverage;
    }

    public String getUrlin() {
        return urlin;
    }

    public String getUrlout() {
        return urlout;
    }
}