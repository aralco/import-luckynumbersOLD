package bo.net.tigo.rest;

import bo.net.tigo.rest.domain.CommonResponse;
import bo.net.tigo.rest.domain.TaskRequest;
import bo.net.tigo.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by aralco on 10/22/14.
 */
@Controller
@RequestMapping(value = "/import",
        consumes = "application/json",
        produces = "application/json")
public class SchedulerResourceImpl {

    @Autowired
    private SchedulerService schedulerService;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerResourceImpl.class);


    @RequestMapping(value = "/scheduler", method = RequestMethod.POST)

    public @ResponseBody ResponseEntity<CommonResponse> createScheduledTask(@RequestBody TaskRequest taskRequest) {
        logger.info("1-->SchedulerResourceImpl:"+taskRequest);
        CommonResponse commonResponse = new CommonResponse();
        try {
            if(taskRequest==null)
                throw new Exception();
            schedulerService.scheduleTask(taskRequest);
            commonResponse.setMessage("Tarea agendada satisfactoriamente.");
        } catch (Exception e)   {
            commonResponse.setMessage("Tarea no agendada, debido a:"+e);
            return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
    }
}
