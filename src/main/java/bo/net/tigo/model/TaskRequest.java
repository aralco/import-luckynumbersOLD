package bo.net.tigo.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by aralco on 11/4/14.
 */
public class TaskRequest {
    private Long id;
    private Date datetime;
    private boolean now;
    private Set<OrderRequest> orderRequests;

    public TaskRequest(Date datetime, boolean now) {
        this.datetime = datetime;
        this.now = now;
    }

    public TaskRequest(Date datetime, boolean now, Set<OrderRequest> orderRequests) {
        this.datetime = datetime;
        this.now = now;
        this.orderRequests = orderRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean isNow() {
        return now;
    }

    public void setNow(boolean now) {
        this.now = now;
    }

    public Set<OrderRequest> getOrderRequests() {
        return orderRequests;
    }

    public void setOrderRequests(Set<OrderRequest> orderRequests) {
        this.orderRequests = orderRequests;
    }
}
