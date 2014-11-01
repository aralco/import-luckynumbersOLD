package bo.net.tigo.rest;

import bo.net.tigo.domain.Order;
import bo.net.tigo.domain.Task;
import bo.net.tigo.domain.Tasks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
@Controller
@RequestMapping(value = "/luckynumbers/import")
public class MonitorResourceImpl {

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ResponseEntity<Tasks> schedule() {

        List<Order> orders = new ArrayList<Order>(6);
        orders.add(new Order(1, "frozen", 1, "7730000", "77499999","SCHEDULED", "SCHEDULED", 100, "0%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders.add(new Order(2, "frozen", 2, "7750000", "77699999","SCHEDULED", "SCHEDULED", 100, "0%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders.add(new Order(3, "frozen", 3, "7770000", "77899999","SCHEDULED", "SCHEDULED", 100, "0%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders.add(new Order(4, "free", 1, "7410000", "74399999","SCHEDULED", "SCHEDULED", 100, "0%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders.add(new Order(5, "free", 2, "7440000", "74599999","SCHEDULED", "SCHEDULED", 100, "0%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders.add(new Order(6, "free", 3, "7460000", "74899999","SCHEDULED", "SCHEDULED", 100, "0%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));

        List<Order> orders1 = new ArrayList<Order>(6);
        orders1.add(new Order(1, "frozen", 1, "7730000", "77499999","COMPLETED_WITH_ERRORS", "COMPLETED_WITH_ERRORS", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders1.add(new Order(2, "frozen", 2, "7750000", "77699999","COMPLETED_OK", "COMPLETED_OK", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders1.add(new Order(3, "frozen", 3, "7770000", "77899999","STARTED", "STARTED", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders1.add(new Order(4, "free", 1, "7410000", "74399999","STARTED", "STARTED", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders1.add(new Order(5, "free", 2, "7440000", "74599999","CANCELLED", "CANCELLED", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders1.add(new Order(6, "free", 3, "7460000", "74899999","CANCELLED", "CANCELLED", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));

        List<Order> orders2 = new ArrayList<Order>(6);
        orders2.add(new Order(1, "frozen", 1, "7730000", "77499999","COMPLETED_WITH_ERRORS", "COMPLETED_WITH_ERRORS", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders2.add(new Order(2, "frozen", 2, "7750000", "77699999","COMPLETED_OK", "COMPLETED_OK", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders2.add(new Order(3, "frozen", 3, "7770000", "77899999","COMPLETED_OK", "COMPLETED_OK", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders2.add(new Order(4, "free", 1, "7410000", "74399999","COMPLETED_OK", "COMPLETED_OK", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders2.add(new Order(5, "free", 2, "7440000", "74599999","COMPLETED_OK", "COMPLETED_OK", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));
        orders2.add(new Order(6, "free", 3, "7460000", "74899999","COMPLETED_OK", "COMPLETED_OK", 100, "100%", "https://dl.dropboxusercontent.com/u/1360957/20140910_151411.in","https://dl.dropboxusercontent.com/u/1360957/20141010_151411.out"));

        List<Task> tasks = new ArrayList<Task>(2);
        tasks.add(new Task(1, "2014-10-20 11:00:00", "admin", "0%", orders));
        tasks.add(new Task(2, "2014-10-30 20:45:00", "admin", "50%", orders1));
        tasks.add(new Task(3, "2014-10-30 20:45:00", "admin", "100%", orders2));
        return new ResponseEntity<Tasks>(new Tasks(tasks), HttpStatus.OK);
    }
}
