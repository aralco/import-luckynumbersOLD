package bo.net.tigo.security;

import bo.net.tigo.model.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    DaoAuthentication daoAuthentication;//Debería permitir autenticar primero en lucky numbers si OK then login in AD
    @Autowired
    ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        logger.info("authenticate:"+authentication);
//Used for DEV mode
        boolean userExists = true;//daoAuthentication.isUser(authentication.getPrincipal().toString());
        if(userExists)  {
//            TODO Load roles for user
//            Authentication auth = activeDirectoryLdapAuthenticationProvider.authenticate(authentication);
            Authentication auth = authentication;
            if(authentication.getPrincipal().equals("sysportal")&& authentication.getCredentials().equals("Sysp0rt4l")) {
                List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
                //TODO with role loaded from user
                grantedAuthorities.add(new LuckyNumbersGrantedAuthorities(String.valueOf(Roles.ROLE_USER)));
                AAAUserAuthenticationToken authTemp = new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);
                auth = authTemp;
                logger.info("User successfully authenticated - authenticate:"+auth);
            }
            if(authentication.getPrincipal().equals("admin")&& authentication.getCredentials().equals("admin")) {
                List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
                //TODO with role loaded from user
                grantedAuthorities.add(new LuckyNumbersGrantedAuthorities(String.valueOf(Roles.ROLE_ADMIN)));
                AAAUserAuthenticationToken authTemp = new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);
                auth = authTemp;
                logger.info("User successfully authenticated - authenticate:"+auth);
            }

            return auth;


        } else {
            throw new UsernameNotFoundException("Username provided doesn't exist in our registry.");
            //throw new BadCredentialsException("Bad User Credentials.");
        }
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
}