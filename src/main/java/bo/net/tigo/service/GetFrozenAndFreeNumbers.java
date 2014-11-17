package bo.net.tigo.service;

import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.Job;
import bo.net.tigo.model.State;
import bo.net.tigo.model.Status;
import bo.net.tigo.model.Task;
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
    private MessageChannel ftpChannel;

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
                //TODO query AS400 for import Congelados/Libres
                //TODO If success Guardar registro de importados
                //TODO Create temporal file using executionDate
                //TODO copy file to FTP server
                String content =
                        "78426471,1,1\n" +
                        "77216828,1,1\n" +
                        "69156173,1,1\n" +
                        "69383077,1,1\n" +
                        "75657386,1,1\n" +
                        "76836282,1,1\n" +
                        "77487719,1,1";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String fileName = sdf.format(task.getExecutionDate())+".in";
                File inFile = new File(fileName);
                logger.info("File to copy:"+inFile);
                if(!inFile.exists())
                    inFile.createNewFile();
                logger.info("File exists:"+inFile);
                FileOutputStream fileOutputStream = new FileOutputStream(inFile);
                byte[] contentInBytes = content.getBytes();
                fileOutputStream.write(contentInBytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                final Message<File> fileMessage = MessageBuilder.withPayload(inFile).build();
                ftpChannel.send(fileMessage);

            }
        }
    }
}
