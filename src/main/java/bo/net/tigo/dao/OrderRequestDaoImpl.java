package bo.net.tigo.dao;

import bo.net.tigo.model.OrderRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aralco on 11/6/14.
 */
@Repository
public class OrderRequestDaoImpl implements OrderRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(OrderRequest orderRequest) {
        System.out.println("OrderRequestDao:OrderRequest:" + orderRequest.getFrom());
        Session session = sessionFactory.getCurrentSession();
        session.save(orderRequest);

    }
}
