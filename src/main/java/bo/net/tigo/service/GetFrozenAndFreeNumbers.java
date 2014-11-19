package bo.net.tigo.service;

import bo.net.tigo.dao.FreeAndFrozenDao;
import bo.net.tigo.dao.InAuditDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 11/11/14.
 */
@Service
public class GetFrozenAndFreeNumbers {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private MessageChannel ftpChannelOUT;
    @Autowired
    private FreeAndFrozenDao freeAndFrozenDao;
    @Autowired
    private InAuditDao inAuditDao;

    private static final Logger logger = LoggerFactory.getLogger(GetFrozenAndFreeNumbers.class);

    @Transactional
    public void executeTasks() throws IOException {
        List<Task> scheduledAndReScheduledTasks = taskDao.findScheduledAndReScheduledTasks(new Date());
        logger.info("Total ScheduledAndReScheduled Tasks:" + scheduledAndReScheduledTasks.size());
        if(scheduledAndReScheduledTasks.size()<=0)   {
            logger.info("No tasks to execute:" + scheduledAndReScheduledTasks.size());
        } else  {
            for(Task task:scheduledAndReScheduledTasks) {
                Job job = task.getJob();
                logger.info("Start task execution for task=> taskId:"+task.getId()+", status="+task.getStatus()+", executionDate="+task.getExecutionDate()+", job="+job);
                task.setStatus(Status.STARTED.name());
                task.setExecutionDate(new Date());
                if(job.getState().equals(State.NOT_STARTED.name()))  {
                   job.setState(State.IN_PROGRESS.name());
                }
                logger.info("Start task execution for task=> taskId:"+task.getId()+", status="+task.getStatus()+", executionDate="+task.getExecutionDate()+", job="+task.getJob().getState());
                List<InAudit> retrievedNumbers;
                if(task.getType().equals(Type.FREE.name())) {
                    retrievedNumbers = freeAndFrozenDao.getFreeNumbers(task.getCity(), task.getFrom(), task.getTo());
                } else  {
                    retrievedNumbers = freeAndFrozenDao.getFrozenNumbers(task.getCity(), task.getFrom(), task.getTo());
                }
                logger.info("Values from Caller retrievedNumbers:"+retrievedNumbers);
                String header =
                        "Numero,Ciudad,Channel";

                for(InAudit inAudit : retrievedNumbers) {
                        header=header+"\n"+inAudit.getRow();
                        inAudit.setCreatedDate(new Date());
                        inAuditDao.save(inAudit);
                }
                logger.info("File generation:"+retrievedNumbers);

                //TODO If success Guardar registro de importados
                //TODO Create temporal file using executionDate
                //TODO copy file to FTP server
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String fileName = sdf.format(task.getExecutionDate())+".in";
                File inFile = new File(fileName);
                logger.info("File to copy:"+inFile);
                if(!inFile.exists())
                    inFile.createNewFile();
                logger.info("File exists:"+inFile);
                FileOutputStream fileOutputStream = new FileOutputStream(inFile);
                byte[] contentInBytes = header.getBytes();
                fileOutputStream.write(contentInBytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                final Message<File> fileMessage = MessageBuilder.withPayload(inFile).build();
                ftpChannelOUT.send(fileMessage);
                task.setStatus(Status.COMPLETED_PHASE1_OK.name());

            }
        }
    }
}
