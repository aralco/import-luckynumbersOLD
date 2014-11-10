package bo.net.tigo.dao;

import bo.net.tigo.model.Job;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class JobDaoImpl implements JobDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Job job) {
        Session session = sessionFactory.getCurrentSession();
        session.save(job);
    }

    @Override
    public void update(Job job) {
        Session session = sessionFactory.getCurrentSession();
        session.update(job);
    }

    @Override
    public Job findOne(Long jobId) {
        Session session = sessionFactory.getCurrentSession();
        Job job = (Job) session.load(Job.class, jobId);
        return job;
    }

    @Override
    public List<Job> finByOwner(String owner) {
        return null;
    }

    @Override
    public List<Job> finByState(String state) {
        return null;
    }

    @Override
    public List<Job> finBetweenDates(Date from, Date to) {
        return null;
    }

    @Override
    public List<Job> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Job> jobs = (List<Job>)session.createQuery("from Job").list();
        return jobs;
    }
}
