package bo.net.tigo.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
public class TaskRequest {
    private final Date datetime;
    private final boolean now;
    private final List<OrderRequest> orderRequests;

    public TaskRequest(Date datetime, boolean now, List<OrderRequest> orderRequests) {
        this.datetime = datetime;
        this.now = now;
        this.orderRequests = orderRequests;
    }

    public Date getDatetime() {
        return datetime;
    }

    public boolean isNow() {
        return now;
    }

    public List<OrderRequest> getOrderRequests() {
        return orderRequests;
    }
}
