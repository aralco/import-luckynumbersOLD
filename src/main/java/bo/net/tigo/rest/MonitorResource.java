package bo.net.tigo.rest;

import bo.net.tigo.model.Job;
import bo.net.tigo.rest.domain.JobResponse;
import bo.net.tigo.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        produces = "application/json")
public class MonitorResource {

    @Autowired
    private MonitorService monitorService;
    private static final Logger logger = LoggerFactory.getLogger(MonitorResource.class);

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<JobResponse> monitorJobs(
            @RequestParam(value = "jobId", defaultValue = "0") Long jobId,
            @RequestParam(value = "owner", defaultValue = "NA") String owner,
            @RequestParam(value = "jobState", defaultValue = "NA") String jobState,
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to)   {
        logger.info("monitorJobs:jobId="+jobId+", owner="+owner+", jobState="+jobState+", from="+from+", to="+to);
        List<Job> jobs = monitorService.monitorJobs(jobId, owner, jobState, from, to);
        JobResponse jobResponse = new JobResponse(jobs);
        return new ResponseEntity<JobResponse>(jobResponse, HttpStatus.OK);
    }

}
