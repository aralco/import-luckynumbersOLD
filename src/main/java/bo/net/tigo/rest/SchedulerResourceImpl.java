package bo.net.tigo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by aralco on 10/22/14.
 */
@Controller
@RequestMapping(value = "/luckynumbers/import")
public class SchedulerResourceImpl {

    @RequestMapping(value = "/scheduler", method = RequestMethod.POST)
    public ResponseEntity<String> schedule() {
        return new ResponseEntity<String>("Ordenes agendadas satisfactoriamente.", HttpStatus.OK);
    }
}