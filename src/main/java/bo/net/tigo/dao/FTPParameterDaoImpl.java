package bo.net.tigo.dao;

import bo.net.tigo.model.FTPParameter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class FTPParameterDaoImpl implements FTPParameterDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(FTPParameter ftpParameter) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ftpParameter);
    }

    @Override
    public FTPParameter findLast() {
        //TODO should return the last inserted row
        Session session = sessionFactory.getCurrentSession();
        return (FTPParameter)session.createQuery("from FTPParameter")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FTPParameter> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from FTPParameter").list();
    }
}
