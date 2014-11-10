package bo.net.tigo.rest;

import bo.net.tigo.model.Job;
import bo.net.tigo.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 10/29/14.
 */
@Controller
@RequestMapping(value = "/monitor",
        consumes = "application/json",
        produces = "application/json")
public class MonitorResource {

    @Autowired
    private MonitorService monitorService;
    private static final Logger logger = LoggerFactory.getLogger(MonitorResource.class);

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Job>> monitorJobs(
            @RequestParam(value = "jobId", required = false) Long jobId,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "jobState", required = false) String jobState,
            @RequestParam(value = "from", required = false) Date from,
            @RequestParam(value = "to", required = false) Date to)   {
        logger.info("monitorJobs:");
        List<Job> jobs = monitorService.monitorJobs();
        return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
    }

}
