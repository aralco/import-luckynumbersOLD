package bo.net.tigo.rest;

import bo.net.tigo.exception.LuckyNumbersGenericException;
import bo.net.tigo.model.Job;
import bo.net.tigo.model.Task;
import bo.net.tigo.rest.domain.JobRequest;
import bo.net.tigo.rest.domain.TaskRequest;
import bo.net.tigo.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by aralco on 10/22/14.
 */
@Controller
@RequestMapping(value = "/scheduler",
        produces = "application/json")
public class SchedulerResource {

    @Autowired
    private SchedulerService schedulerService;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerResource.class);

    @RequestMapping(value = "/job", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Job> createJob(@RequestBody @Valid JobRequest jobRequest) {
        if(jobRequest==null)
            throw new LuckyNumbersGenericException(HttpStatus.BAD_REQUEST.toString(),"Job Request cannot be null");
        logger.info("createJob:"+jobRequest.toString());
        Job job = schedulerService.createJob(jobRequest);
        return new ResponseEntity<Job>(job, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/job/{jobId:[\\p{Digit}]+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Job> viewJob(@PathVariable Long jobId)   {
        Job job = schedulerService.getJob(jobId);
        if(job==null)
            return new ResponseEntity<Job>(new Job(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Job>(job, HttpStatus.OK);
    }

    @RequestMapping(value = "/job/{jobId:[\\p{Digit}]+}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Job> updateJob(@PathVariable Long jobId, @RequestBody @Valid Job job)   {
        Job updatedJob = schedulerService.updateJob(jobId, job);
        return new ResponseEntity<Job>(updatedJob, HttpStatus.OK);
    }


    @RequestMapping(value = "/task/{jobId:[\\p{Digit}]+}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Task> createTask(@PathVariable Long jobId, @RequestBody @Valid TaskRequest taskRequest)   {
        Task createdTask = schedulerService.createTask(jobId, taskRequest);
        return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/task/{taskId:[\\p{Digit}]+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> viewTask(@PathVariable Long taskId)   {
        Task task = schedulerService.getTask(taskId);
        if(task==null)
            return new ResponseEntity<Task>(new Task(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/task/{taskId:[\\p{Digit}]+}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody @Valid Task task)   {
        Task updatedTask = schedulerService.updateTask(task);
        return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);

    }



}
