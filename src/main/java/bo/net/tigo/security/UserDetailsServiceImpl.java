package bo.net.tigo.security;

import bo.net.tigo.dao.UserDao;
import bo.net.tigo.exception.LuckyNumbersGenericException;
import bo.net.tigo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aralco on 11/7/14.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername:"+username);
        User domainUser = userDao.findByUsername(username);
        if(domainUser==null)
            throw new UsernameNotFoundException("user "+username+" doesn't exists.");
        if(!domainUser.getEnabled())
            throw new LuckyNumbersGenericException(HttpStatus.UNAUTHORIZED.toString(), "User "+domainUser.getUsername()+" has disabled account.");

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
        authorities.add(new LuckyNumbersGrantedAuthorities(domainUser.getRole()));

        return new org.springframework.security.core.userdetails.User(
                domainUser.getUsername(),
                domainUser.getPassword(),
                domainUser.getEnabled(),
                domainUser.getEnabled(),
                domainUser.getEnabled(),
                domainUser.getEnabled(),
                authorities
        );
    }
}
