package bo.net.tigo.dao;

import bo.net.tigo.model.InAudit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aralco on 11/18/14.
 */
@Repository
public class InAuditDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(InAudit inAudit) {
        Session session = sessionFactory.getCurrentSession();
        session.save(inAudit);
    }

}
