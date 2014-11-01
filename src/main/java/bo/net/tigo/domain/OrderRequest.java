package bo.net.tigo.domain;

/**
 * Created by aralco on 10/29/14.
 */
public class OrderRequest {
    private final String orderType;
    private final int city;
    private final String from;
    private final String to;

    public OrderRequest(String orderType, int city, String from, String to) {
        this.orderType = orderType;
        this.city = city;
        this.from = from;
        this.to = to;
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
}
