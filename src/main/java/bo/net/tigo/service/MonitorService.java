package bo.net.tigo.service;

import bo.net.tigo.dao.JobDao;
import bo.net.tigo.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aralco on 10/22/14.
 */
@Service
public class MonitorService {
    @Autowired
    private JobDao jobDao;

    private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);

    @Transactional
    public List<Job> monitorJobs()  {
        logger.info("monitorJobsBEFORE:");
        List<Job> jobs= jobDao.findAll();
        logger.info("monitorJobsAFTER:"+jobs);
        return jobs;
    }
}
