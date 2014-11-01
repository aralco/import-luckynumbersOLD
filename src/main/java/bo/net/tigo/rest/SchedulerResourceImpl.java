package bo.net.tigo.rest;

import bo.net.tigo.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aralco on 10/22/14.
 */
@RestController
@RequestMapping(value = "/luckynumbers/import")
public class SchedulerResourceImpl {

    @RequestMapping(value = "/scheduler", method = RequestMethod.POST)
    public ResponseEntity<String> schedule() {
        return new ResponseEntity<String>("Ordenes agendadas satisfactoriamente.", HttpStatus.OK);
    }
}