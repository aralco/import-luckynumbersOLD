package bo.net.tigo.service;

import bo.net.tigo.dao.BCCSDao;
import bo.net.tigo.dao.InAuditDao;
import bo.net.tigo.dao.OutAuditDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                StringBuffer taskLog = new StringBuffer();
                Date currentDate = calendar.getTime();
                Job job = task.getJob();
                logger.info("Start task execution for task=> taskId:"+task.getId()+", status="+task.getStatus()+", executionDate="+task.getExecutionDate()+", job="+job);
                taskLog.append("Task execution started.||");
                task.setStatus(Status.STARTED.name());
                task.setExecutionDate(currentDate);
                if(job.getState().equals(State.NOT_STARTED.name()))  {
                   job.setState(State.IN_PROGRESS.name());
                }
                logger.info("Start task execution for task=> taskId:" + task.getId() + ", status=" + task.getStatus() + ", executionDate=" + task.getExecutionDate() + ", job=" + task.getJob().getState());
                taskLog.append("Retrieving numbers.||");
                List<InAudit> retrievedNumbers;
                if(task.getType().equals(Type.FREE.name())) {
                    retrievedNumbers = bccsDao.getFreeNumbers(task.getCity(), task.getFrom(), task.getTo());
                } else  {
                    retrievedNumbers = bccsDao.getFrozenNumbers(task.getCity(), task.getFrom(), task.getTo());
                }
                logger.info("Values from Caller retrievedNumbers:"+retrievedNumbers.toString());
                taskLog.append("Total retrieved numbers:").append(retrievedNumbers.size()).append("||");
                if(retrievedNumbers.size()>0)   {
                    String fileContent =
                            "Numero,Ciudad,Channel";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String fileName = sdf.format(currentDate)+".in";

                    for(InAudit inAudit : retrievedNumbers) {
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
                        logger.info("Processing retrievedNumber:"+inAudit.getRow());
                    }
                    logger.info("File generation:"+retrievedNumbers);


                    File inFile = new File(fileName);
                    logger.info("File generated:"+inFile);
                    taskLog.append("File generated:").append(fileName).append("||");

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
                    logger.info("Sending file :"+fileName+" to FTP server.");
                    taskLog.append("Sending file ").append(fileName).append(" to FTP server.||");
                    task.setStatus(Status.COMPLETED_PHASE1_OK.name());
                    logger.info("Phase 1 completed successfully.");
                    taskLog.append("Phase 1 completed successfully.").append("||");
                    task.setUrlin(fileName);
                    calendar.add(Calendar.SECOND, +1);
                } else  {
                    task.setStatus(Status.COMPLETED_PHASE1_WITH_ERRORS.name());
                    logger.warn("Phase 1 completed with errors. .in File could not be generated.");
                    taskLog.append("Phase 1 completed with errors. .in File could not be generated. ||");
                    task.setUrlin("NA");
                }
                Long inFiles = inAuditDao.countInFilesByJob(job.getId());
                Long outFiles = outAuditDao.countOutFilesByJob(job.getId());
                logger.info("Total created files: "+(inFiles==null?0:inFiles)+"(.in) -VS- "+(outFiles==null?0:outFiles)+"(.out)");
                job.setSummary(" Total archivos creados: "+(inFiles==null?0:inFiles)+"(.in) -VS- "+(outFiles==null?0:outFiles)+"(.out) ||");
                //TODO calculate the coverage based on tasks
                float jobPercentage=50;
                job.setTotalCoverage(jobPercentage+"%");
                job.setLastUpdate(currentDate);
                task.setSummary((task.getSummary()==null?"":task.getSummary())+taskLog.toString());
                task.setLastUpdate(currentDate);
            }
        }
    }
}
