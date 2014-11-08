package bo.net.tigo.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
public class TaskRequest {
    @JsonSerialize(using=DateSerializer.class)
    private Date datetime;
    private boolean now;
    private List<OrderRequest> orderRequests;

    public TaskRequest(Date datetime, boolean now, List<OrderRequest> orderRequests) {
        this.datetime = datetime;
        this.now = now;
        this.orderRequests = orderRequests;
    }


    public TaskRequest() {
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

    public List<OrderRequest> getOrderRequests() {
        return orderRequests;
    }

    public void setOrderRequests(List<OrderRequest> orderRequests) {
        this.orderRequests = orderRequests;
    }
}
