package bo.net.tigo.dao;

import bo.net.tigo.model.TaskRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aralco on 11/4/14.
 */
@Repository
public class TaskRequestDaoImpl implements TaskRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(TaskRequest taskRequest) {
        System.out.println("TaskRequestDao:taskRequest:" + taskRequest.isNow() + "," + taskRequest.getDatetime());
        Session session = sessionFactory.getCurrentSession();
        session.save(taskRequest);
    }

}
