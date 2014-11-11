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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by aralco on 10/22/14.
 */
@Service
public class SchedulerService {
    @Autowired
    private JobDao jobDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

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
        job.setOwner("user");
        int totalTasks=0;
        if(jobRequest.getTasks()!=null && jobRequest.getTasks().size()>0)
            totalTasks=jobRequest.getTasks().size();
        job.setTotalTasks(totalTasks);
        job.setPassedTasks(0);
        job.setFailedTasks(0);
        job.setTotalCoverage("0%");
        job.setSummary("");
        //job.setCreatedDate(creationDate);
        jobDao.save(job);

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
            //task.setCreatedDate(creationDate);
            taskDao.save(task);
        }

        importNumbers(job.getScheduledDate());
        return job;
    }

    private void importNumbers(Date scheduledDate) {
        System.out.println("import Numbers");
        threadPoolTaskScheduler.schedule(new GetFrozenAndFreeNumbers(), scheduledDate);
    }


}
