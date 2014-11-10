package bo.net.tigo.dao;

import bo.net.tigo.model.AccessLog;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class AccessLogDaoImpl implements AccessLogDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(AccessLog accessLog) {

    }

    @Override
    public List<AccessLog> findAll() {
        return null;
    }
}
