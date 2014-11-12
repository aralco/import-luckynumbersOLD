package bo.net.tigo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by aralco on 11/11/14.
 */
public class SecurityUtils {

    public static Authentication getCurrentAuthenticateObject()  {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserDetails getCurrentUserDetails()  {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails)principal;
        }
        return null;
    }

    public static String getCurrentUsername()  {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
