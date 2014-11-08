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
//    @Autowired
//    ActiveDirectoryLdapAuthenticationProvider myAuthenticationManager;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
//Use it only on Test Server
//        boolean result = myAuthenticator.login(authentication.getPrincipal()
//                .toString(), authentication.getCredentials().toString());
        boolean result = true;

        System.out.println("##** ->> Lllamando al AAAUserAuthenticationProvider:authenticate:"+authentication);

        if (result) {
            List<GrantedAuthority> grantedAuthorities =

                    new ArrayList<GrantedAuthority>();
            AAAUserAuthenticationToken auth =

                    new AAAUserAuthenticationToken(authentication.getPrincipal(),
                            authentication.getCredentials(), grantedAuthorities);

            return auth;
        } else {
            throw new BadCredentialsException("Bad User Credentials.");
        }
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
}