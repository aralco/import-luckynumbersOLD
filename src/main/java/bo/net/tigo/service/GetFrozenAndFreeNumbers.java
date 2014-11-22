package bo.net.tigo.service;

import bo.net.tigo.dao.BCCSDao;
import bo.net.tigo.dao.InAuditDao;
import bo.net.tigo.dao.OutAuditDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 11/11/14.
 */
@Service
@PropertySource("classpath:luckynumbers.properties")
public class GetFrozenAndFreeNumbers {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private MessageChannel ftpChannelOUT;
    @Autowired
    private BCCSDao bccsDao;
    @Autowired
    private InAuditDao inAuditDao;
    @Autowired
    private OutAuditDao outAuditDao;
    @Value("${file.path.import.in}")
    private String filePath;

    private static final Logger logger = LoggerFactory.getLogger(GetFrozenAndFreeNumbers.class);

    @Transactional
    public void executeTasks() throws IOException {
        List<Task> scheduledAndReScheduledTasks = taskDao.findScheduledAndReScheduledTasks(new Date());
        logger.info("Total ScheduledAndReScheduled Tasks:" + scheduledAndReScheduledTasks.size());
        if(scheduledAndReScheduledTasks.size()<=0)   {
            logger.info("No tasks to execute:" + scheduledAndReScheduledTasks.size());
        } else  {
            Calendar calendar = Calendar.getInstance();
            for(Task task:scheduledAndReScheduledTasks) {
                StringBuilder taskLog = new StringBuilder();
                Date currentDate = calendar.getTime();
                Job job = task.getJob();
                logger.info("Start task execution for task=> taskId:"+task.getId()+", status="+task.getStatus()+", executionDate="+task.getExecutionDate()+", job="+job);
                taskLog.append("Task execution started.||");
                task.setSummary(taskLog.toString());
                task.setStatus(Status.STARTED.name());
                task.setExecutionDate(currentDate);
                if(job.getState().equals(State.NOT_STARTED.name()))  {
                   job.setState(State.IN_PROGRESS.name());
                }
                logger.info("Start task execution for task=> taskId:" + task.getId() + ", status=" + task.getStatus() + ", executionDate=" + task.getExecutionDate() + ", job=" + task.getJob().getState());
                taskLog.append("Retrieving numbers.||");
                task.setSummary(taskLog.toString());
                List<InAudit> retrievedNumbers;
                if(task.getType().equals(Type.FREE.name())) {
                    retrievedNumbers = bccsDao.getFreeNumbers(task.getCity(), task.getFrom(), task.getTo());
                } else  {
                    retrievedNumbers = bccsDao.getFrozenNumbers(task.getCity(), task.getFrom(), task.getTo());
                }
                logger.info("Values from Caller retrievedNumbers:"+retrievedNumbers.toString());
                taskLog.append("Total retrieved numbers:"+retrievedNumbers.size()+"||");
                task.setSummary(taskLog.toString());
                if(retrievedNumbers.size()>0)   {
                    String fileContent =
                            "Numero,Ciudad,Channel";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String fileName = sdf.format(currentDate)+".in";

                    for(InAudit inAudit : retrievedNumbers) {
                        task.setSummary(taskLog.toString());
                        fileContent=fileContent+"\n"+inAudit.getRow();
                        inAudit.setCreatedDate(currentDate);
                        inAudit.setCity(task.getCity());
                        inAudit.setTaskId(task.getId());
                        inAudit.setJobId(task.getJob().getId());
                        String fields[] = inAudit.getRow().split(",");
                        inAudit.setNumber(fields[0]);
                        inAudit.setCity(Integer.valueOf(fields[1]));
                        inAudit.setChannel(Integer.valueOf(fields[2]));
                        inAudit.setFileName(fileName);
                        inAuditDao.save(inAudit);
                        logger.info("Processing retrievedNumber:"+inAudit.toString());
                        taskLog.append("Processing retrievedNumber:"+inAudit.toString()+"||");
                    }
                    logger.info("File generation:"+retrievedNumbers);


                    File inFile = new File(filePath+"/"+fileName);
                    logger.info("File to copy:"+inFile);
                    taskLog.append("File generation."+fileName+"||");
                    task.setSummary(taskLog.toString());

                    if(!inFile.exists())
                        inFile.createNewFile();
                    logger.info("File exists:"+inFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(inFile);
                    byte[] contentInBytes = fileContent.getBytes();
                    fileOutputStream.write(contentInBytes);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    final Message<File> fileMessage = MessageBuilder.withPayload(inFile).build();
                    ftpChannelOUT.send(fileMessage);
                    taskLog.append("Sending file "+fileName+" to FTP server.||");
                    task.setSummary(taskLog.toString());
                    task.setStatus(Status.COMPLETED_PHASE1_OK.name());
                    taskLog.append("Phase 1 completed successfully."+fileName+"||");
                    task.setSummary(taskLog.toString());
                    task.setUrlin(fileName);
                    calendar.add(Calendar.SECOND, +1);
                } else  {
                    task.setStatus(Status.COMPLETED_PHASE1_WITHOUT_DATA.name());
                    taskLog.append("Phase 1 completed without data.||");
                    task.setSummary(taskLog.toString());
                    task.setUrlin("NA");
                }
                job.setSummary(job.getSummary()+"Created files -> .in:"+(inAuditDao.countInFilesByJob(job.getId()))+" -VS- :.out"+(outAuditDao.countOutFilesByJob(job.getId()))+" ||");
                task.setSummary(taskLog.toString());
            }
        }
    }
}
