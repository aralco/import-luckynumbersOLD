package bo.net.tigo.service;

import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aralco on 11/11/14.
 */
@Service
public class GetFrozenAndFreeNumbers {
    @Autowired
    private TaskDao taskDao;

    private static final Logger logger = LoggerFactory.getLogger(GetFrozenAndFreeNumbers.class);

    @Transactional
    public void run() {
        List<Task> taskList = taskDao.findAll();
        logger.info("Total Queued tasks:"+taskList.size());
        for(Task task:taskList) {
            logger.info("Queued task:"+task);
        }
    }
}
