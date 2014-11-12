package bo.net.tigo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by aralco on 10/29/14.
 */
public class UserAuthenticationTokenImpl

        extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1092219614309982278L;
    private final Object principal;
    private Object credentials;

    public UserAuthenticationTokenImpl(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public UserAuthenticationTokenImpl(Object principal, Object credentials,
                                       Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }
}