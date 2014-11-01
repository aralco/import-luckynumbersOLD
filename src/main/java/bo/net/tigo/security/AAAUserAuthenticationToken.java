package bo.net.tigo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by aralco on 10/29/14.
 */
public class AAAUserAuthenticationToken

        extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1092219614309982278L;
    private final Object principal;
    private Object credentials;

    public AAAUserAuthenticationToken(Object principal, Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        System.out.println("-->>1 Token Authentication:"+authorities);
        this.principal = principal;
        this.credentials = credentials;
        System.out.println("-->>2 Token Authentication:"+principal+", "+credentials);
        super.setAuthenticated(true);
        System.out.println("-->>3 Token Authentication:"+principal+", "+credentials);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }
}