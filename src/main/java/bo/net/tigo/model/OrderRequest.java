package bo.net.tigo.model;

/**
 * Created by aralco on 11/6/14.
 */
public class OrderRequest {
    private Long id;
    private String orderType;
    private int city;
    private String from;
    private String to;
    private TaskRequest taskRequest;

    public OrderRequest(String orderType, int city, String from, String to) {
        this.orderType = orderType;
        this.city = city;
        this.from = from;
        this.to = to;
    }

    public OrderRequest(String orderType, int city, String from, String to, TaskRequest taskRequest) {
        this.orderType = orderType;
        this.city = city;
        this.from = from;
        this.to = to;
        this.taskRequest = taskRequest;
    }

    public OrderRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TaskRequest getTaskRequest() {
        return taskRequest;
    }

    public void setTaskRequest(TaskRequest taskRequest) {
        this.taskRequest = taskRequest;
    }
}
