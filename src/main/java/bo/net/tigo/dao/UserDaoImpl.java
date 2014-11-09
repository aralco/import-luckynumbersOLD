package bo.net.tigo.dao;

import bo.net.tigo.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public User findOne(Long userId) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        //MOCK
        User user = new User();
        user.setUsername("sysportal");
        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
