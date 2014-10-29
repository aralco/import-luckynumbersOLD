package bo.net.tigo.rest;

import bo.net.tigo.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by aralco on 10/22/14.
 */
@RestController
@RequestMapping(value = "/luckynumbers/import")
public class ImportLuckyNumbersResourceImpl {
    private static final String template = "Hello, %s!";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "secret";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="User") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Token> authentication(@RequestBody User user)  {
        System.out.println("1 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
        if(user != null)  {
            System.out.println("2 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
            if(user.getUsername().equals(USERNAME) && user.getPassword().equals(PASSWORD)) {
                System.out.println("3 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
                return new ResponseEntity<Token>(new Token("12345abcde12345abcde"), HttpStatus.OK);
            }
        }
        System.out.println("4 Username:"+user.getUsername()+","+"Password:"+user.getPassword());
        return new ResponseEntity<Token>(new Token("notvaliduser"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/libres", method = RequestMethod.POST)
    public Libres libres()  {
        List<Libre> libres = new ArrayList<Libre>(3);
        libres.add(new Libre("77498989","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        libres.add(new Libre("77383832","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        libres.add(new Libre("7777771","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        return new Libres(libres);
    }

    @RequestMapping(value = "/congelados", method = RequestMethod.POST)
    public Congelados congelados()  {
        List<Congelado> congelados = new ArrayList<Congelado>(6);
        congelados.add(new Congelado("67398983","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        congelados.add(new Congelado("66677726","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        congelados.add(new Congelado("64334434","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        congelados.add(new Congelado("76376321","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        congelados.add(new Congelado("7077071","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        congelados.add(new Congelado("7731234","http://localhost:8080/import-luckynumbers/luckynumbers/import/luckynumber?numero=numero"));
        return new Congelados(congelados);

    }
}
