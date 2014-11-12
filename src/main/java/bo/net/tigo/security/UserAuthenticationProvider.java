package bo.net.tigo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;

/**
 * Created by aralco on 10/29/14.
 */
@Service
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        logger.info("authenticate:"+authentication);
        if(authentication!=null && authentication.isAuthenticated())    {
//        authentication = activeDirectoryLdapAuthenticationProvider.authenticate(authentication);
            if(authentication.getPrincipal().equals("sysportal")&& authentication.getCredentials().equals("Sysp0rt4l")) {
                UserAuthenticationTokenImpl authTemp = new UserAuthenticationTokenImpl(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
                authentication = authTemp;
                logger.info("User successfully authenticated - authenticate:"+authentication);
            }
            else if(authentication.getPrincipal().equals("admin")&& authentication.getCredentials().equals("admin")) {
                UserAuthenticationTokenImpl authTemp = new UserAuthenticationTokenImpl(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
                authentication = authTemp;
                logger.info("User successfully authenticated - authenticate:"+authentication);
            } else {
                throw new BadCredentialsException("Bad User Credentials.");
            }
            return authentication;
        } else  {
            throw new UsernameNotFoundException("user "+authentication.getPrincipal().toString()+" doesn't exists.");
        }
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }

}