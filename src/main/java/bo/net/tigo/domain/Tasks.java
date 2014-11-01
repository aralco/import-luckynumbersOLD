package bo.net.tigo.domain;

import java.util.List;

/**
 * Created by aralco on 10/31/14.
 */
public class Tasks {
    private final List<Task> tasks;

    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
