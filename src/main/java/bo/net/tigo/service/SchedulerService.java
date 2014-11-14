package bo.net.tigo.service;

import bo.net.tigo.dao.JobDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.exception.LuckyNumbersGenericException;
import bo.net.tigo.model.Job;
import bo.net.tigo.model.State;
import bo.net.tigo.model.Status;
import bo.net.tigo.model.Task;
import bo.net.tigo.rest.domain.JobRequest;
import bo.net.tigo.rest.domain.TaskRequest;
import bo.net.tigo.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aralco on 10/22/14.
 */
@Service
public class SchedulerService {
    @Autowired
    private JobDao jobDao;
    @Autowired
    private TaskDao taskDao;

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Transactional
    public Job createJob(JobRequest jobRequest) throws LuckyNumbersGenericException {
        logger.info("createJob:"+jobRequest);
        Date creationDate = new Date();
        Job job = new Job();
        job.setName(jobRequest.getName());
        job.setDescription(jobRequest.getDescription());
        if(jobRequest.getNow())
            job.setScheduledDate(creationDate);
        else
            job.setScheduledDate(jobRequest.getScheduledDate());
        job.setNow(jobRequest.getNow());
        job.setState(String.valueOf(State.NOT_STARTED));
        job.setOwner(SecurityUtils.getCurrentUsername());
        int totalTasks=0;
        if(jobRequest.getTasks()!=null && jobRequest.getTasks().size()>0)
            totalTasks=jobRequest.getTasks().size();
        job.setTotalTasks(totalTasks);
        job.setPassedTasks(0);
        job.setFailedTasks(0);
        job.setTotalCoverage("0%");
        job.setSummary("");
        job.setCreatedDate(creationDate);
        jobDao.save(job);

        Set<Task> tasks = new HashSet<Task>(0);
        for(TaskRequest taskRequest : jobRequest.getTasks())   {
            Task task = new Task();
            task.setType(taskRequest.getType());
            task.setCity(taskRequest.getCity());
            task.setFrom(taskRequest.getFrom());
            task.setTo(taskRequest.getTo());
            task.setExecutionDate(job.getScheduledDate());
            task.setStatus(String.valueOf(Status.SCHEDULED));
            task.setProcessed(0);
            task.setPassed(0);
            task.setFailed(0);
            task.setJob(job);
            task.setSummary("");
            task.setCoverage("0%");
            task.setCreatedDate(creationDate);
            taskDao.save(task);
            tasks.add(task);
        }
        job.setTasks(tasks);
        return job;
    }

    @Transactional
    public Job getJob(Long jobId)   {
        return jobDao.findOne(jobId);
    }

    @Transactional
    public Job updateJob(Long jobId, Job job)   {
        jobDao.update(job);
        return job;
    }

    @Transactional
    public Task createTask(Long jobId, TaskRequest taskRequest) {
        Job job = jobDao.findOne(jobId);
        Task task = new Task();
        task.setType(taskRequest.getType());
        task.setCity(taskRequest.getCity());
        task.setFrom(taskRequest.getFrom());
        task.setTo(taskRequest.getTo());
        task.setExecutionDate(job.getScheduledDate());
        task.setStatus(String.valueOf(Status.SCHEDULED));
        task.setProcessed(0);
        task.setPassed(0);
        task.setFailed(0);
        task.setJob(job);
        task.setSummary("");
        task.setCoverage("0%");
        task.setCreatedDate(new Date());
        taskDao.save(task);

        return task;
    }

    @Transactional
    public Task getTask(Long taskId) {
        return taskDao.findOne(taskId);
    }

    @Transactional
    public Task updateTask(Task task) {
        taskDao.update(task);
        return task;
    }
}
