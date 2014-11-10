package bo.net.tigo.service;

import bo.net.tigo.dao.JobDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.exception.LuckyNumbersGenericException;
import bo.net.tigo.model.Job;
import bo.net.tigo.model.Task;
import bo.net.tigo.rest.domain.JobRequest;
import bo.net.tigo.rest.domain.TaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Transactional
    public Job createJob(JobRequest jobRequest) throws LuckyNumbersGenericException {
        logger.info("createJob:"+jobRequest);
        Job job = new Job();
        job.setName(jobRequest.getName());
        job.setDescription(jobRequest.getDescription());
        if(jobRequest.getNow())
            job.setScheduledDate(new Date());
        else
            job.setScheduledDate(jobRequest.getScheduledDate());
        job.setNow(jobRequest.getNow());
        jobDao.save(job);

        for(TaskRequest taskRequest : jobRequest.getTasks())   {
            Task task = new Task();
            task.setType(taskRequest.getType());
            task.setCity(taskRequest.getCity());
            task.setFrom(taskRequest.getFrom());
            task.setTo(taskRequest.getTo());
            task.setJob(job);
            taskDao.save(task);
        }
        return job;
    }

}
