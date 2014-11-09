package bo.net.tigo.rest;

import bo.net.tigo.rest.domain.Token;
import bo.net.tigo.rest.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by aralco on 10/22/14.
 */
@Controller
@RequestMapping(value = "/import")
public class AuthResourceImpl {
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "secret";

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ResponseEntity<Token> authenticate(@RequestBody User user)  {
        System.out.println("1 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
        if(user != null)  {
            System.out.println("2 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
            if(user.getUsername().equals(USERNAME) && user.getPassword().equals(PASSWORD)) {
                System.out.println("3 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
                return new ResponseEntity<Token>(new Token("72c793c8-5f60-4788-b5a8-1ee6828460d7", "bearer", "9a651e00-a1aa-4284-87a8-c99e80ec4ce2",600), HttpStatus.OK);
            }
        }
        System.out.println("4 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
        return new ResponseEntity<Token>(new Token("00000000000000000000", "bearer", "00000000000000000000",600), HttpStatus.UNAUTHORIZED);
    }


}
