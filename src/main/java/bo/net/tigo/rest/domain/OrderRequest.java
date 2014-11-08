package bo.net.tigo.rest.domain;

/**
 * Created by aralco on 10/29/14.
 */
public class OrderRequest {
    private String orderType;
    private int city;
    private String from;
    private String to;

    public OrderRequest(String orderType, int city, String from, String to) {
        this.orderType = orderType;
        this.city = city;
        this.from = from;
        this.to = to;
    }

    public OrderRequest() {
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
