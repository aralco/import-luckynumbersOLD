package bo.net.tigo.service;

import bo.net.tigo.dao.OrderRequestDao;
import bo.net.tigo.dao.TaskRequestDao;
import bo.net.tigo.domain.TaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by aralco on 10/22/14.
 */
@Service
public class SchedulerService {
    @Autowired
    private TaskRequestDao taskRequestDao;
    @Autowired
    private OrderRequestDao orderRequestDao;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Transactional
    public void scheduleTask(TaskRequest taskRequest) throws ParseException {
        bo.net.tigo.model.TaskRequest taskRequestModel = null;
        logger.info("2--> SchedulerService:scheduleTask:"+taskRequest.isNow());
        if(taskRequest.isNow())
            taskRequestModel = new bo.net.tigo.model.TaskRequest(new Date(), taskRequest.isNow());
        else
            taskRequestModel = new bo.net.tigo.model.TaskRequest(taskRequest.getDatetime(), taskRequest.isNow());
        taskRequestDao.save(taskRequestModel);

        for(bo.net.tigo.domain.OrderRequest or : taskRequest.getOrderRequests()) {
            bo.net.tigo.model.OrderRequest tempOrder = new bo.net.tigo.model.OrderRequest();
            tempOrder.setCity(or.getCity());
            tempOrder.setFrom(or.getFrom());
            tempOrder.setTo(or.getTo());
            tempOrder.setOrderType(or.getOrderType());
            tempOrder.setTaskRequest(taskRequestModel);
            orderRequestDao.save(tempOrder);

        }

    }
}
