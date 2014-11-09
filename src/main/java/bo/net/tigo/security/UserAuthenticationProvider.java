package bo.net.tigo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

/**
 * Created by aralco on 10/29/14.
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    DaoAuthentication daoAuthentication;//Debería permitir autenticar primero en lucky numbers si OK then login in AD
    @Autowired
    ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        boolean userExists = daoAuthentication.isUser(authentication.getPrincipal().toString());
        if(userExists)  {
//            TODO Load roles for user
            Authentication auth = activeDirectoryLdapAuthenticationProvider.authenticate(authentication);
            return auth;

        } else {
            throw new UsernameNotFoundException("Username provided doesn't exist in our registry.");
        }


//        if(authentication.getPrincipal().equals("user")&& authentication.getCredentials().equals("user"))
//        {
//
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//            AAAUserAuthenticationToken auth=new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);
//
//            System.out.println("OAUTH: auth object -->> "+auth);
//            return auth;
//
//        }
//        else if(authentication.getPrincipal().equals("admin")&& authentication.getCredentials().equals("admin"))
//        {
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//            AAAUserAuthenticationToken auth=new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);
//            System.out.println("OAUTH: auth object -->> "+auth);
//            return auth;
//        }
//        else if(authentication.getPrincipal().equals("user1")&& authentication.getCredentials().equals("user1"))
//        {
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//            AAAUserAuthenticationToken auth=new AAAUserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);
//            System.out.println("OAUTH: auth object -->> "+auth);
//            return auth;
//        }
//        else{
//            throw new BadCredentialsException("Bad User Credentials.");
//        }
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
}