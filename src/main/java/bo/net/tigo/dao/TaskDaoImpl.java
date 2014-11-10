package bo.net.tigo.dao;

import bo.net.tigo.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class TaskDaoImpl implements TaskDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.save(task);
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public Task findOne(Long taskId) {
        return null;
    }

    @Override
    public List<Task> findAll() {
        return null;
    }
}
