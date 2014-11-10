package bo.net.tigo.security;

import bo.net.tigo.dao.UserDao;
import bo.net.tigo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aralco on 11/7/14.
 */
@Service
public class DaoAuthentication {

    @Autowired
    private UserDao userDao;

    public boolean isUser(String username)   {
        boolean exists = false;
        User user = userDao.findByUsername(username);
        if(user != null && user.getUsername().equals(username))
            exists = true;
        return exists;
    }
}
