package bo.net.tigo.rest.domain;

import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
public class Task {
    private final int taskId;
    private final String datetime;
    private final String owner;
    private final String totalCoverage; //{0%-100%}
    private final List<Order> orders;

    public Task(int taskId, String datetime, String owner, String totalCoverage, List<Order> orders) {
        this.taskId = taskId;
        this.datetime = datetime;
        this.owner = owner;
        this.totalCoverage = totalCoverage;
        this.orders = orders;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getOwner() {
        return owner;
    }

    public String getTotalCoverage() {
        return totalCoverage;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
