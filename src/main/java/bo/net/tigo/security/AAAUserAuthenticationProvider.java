package bo.net.tigo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
public class AAAUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MyAuthenticator myAuthenticator;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        System.out.println("User Authentication:"+authentication.getPrincipal()+","+authentication.getCredentials());
        if(authentication.getPrincipal().equals("user")&& authentication.getCredentials().equals("user"))
        {

            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            AAAUserAuthenticationToken auth=new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);

            System.out.println("OAUTH: auth object -->> "+auth);
            return auth;

        }
        else if(authentication.getPrincipal().equals("admin")&& authentication.getCredentials().equals("admin"))
        {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            AAAUserAuthenticationToken auth=new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);
            System.out.println("OAUTH: auth object -->> "+auth);
            return auth;
        }
        else if(authentication.getPrincipal().equals("user1")&& authentication.getCredentials().equals("user1"))
        {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            AAAUserAuthenticationToken auth=new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);
            System.out.println("OAUTH: auth object -->> "+auth);
            return auth;
        }
        else{
            throw new BadCredentialsException("Bad User Credentials.");
        }
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
}