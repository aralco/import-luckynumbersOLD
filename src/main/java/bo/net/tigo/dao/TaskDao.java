package bo.net.tigo.dao;

import bo.net.tigo.model.Task;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
public interface TaskDao {
    public void save(Task task);
    public void update(Task task);
    public Task findOne(Long taskId);
    public List<Task> findAll();
}