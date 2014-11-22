package bo.net.tigo.service;

import bo.net.tigo.dao.BCCSDao;
import bo.net.tigo.dao.LuckyNumbersDao;
import bo.net.tigo.dao.OutAuditDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 11/20/14.
 */
@Service
public class OutProcessor {
    @Autowired
    private OutAuditDao outAuditDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private BCCSDao bccsDao;
    @Autowired
    private LuckyNumbersDao luckyNumbersDao;

    private static final Logger logger = LoggerFactory.getLogger(OutProcessor.class);

//    LOG.trace("Hello World!");
//    LOG.debug("How are you today?");
//    LOG.info("I am fine.");
//    LOG.warn("I love programming.");
//    LOG.error("I am programming.");


    @Transactional
    public File process(File file) throws Exception{
        StringBuilder taskLog = new StringBuilder();
        logger.info("Processing File: " + file);
        BufferedReader bufferedReader = null;
        String splitBy = ",";
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            Date currentDate = Calendar.getInstance().getTime();
            String fileName = file.getName();
            logger.info("filename to Process:"+fileName);
            String shortFilename = fileName.substring(0,15);
            logger.info("array Size:"+shortFilename.length()+",values:"+shortFilename);
            //TODO create a exception when file doesn't exist in db
            Task task = taskDao.findByFileName(shortFilename);
            taskLog.append(task.getSummary()+"\n ************************** \n");
            task.setSummary(taskLog.toString());
            taskLog.append("Processing .out File: " + fileName+"\n");
            task.setUrlout(fileName);
            task.setLastUpdate(currentDate);
            task.setStatus(Status.STARTED_PHASE2.name());
            boolean taskCompletedOK=true;
            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                logger.info("line to process:"+line);
                taskLog.append("Processing line: " + line+"\n");
                task.setSummary(taskLog.toString());
                String[] outRow = line.split(splitBy);
                logger.info("array Size:"+outRow.length+",vlaues:"+outRow);
                OutAudit outAudit = new OutAudit();
                outAudit.setFileName(fileName);
                outAudit.setRow(line);
                outAudit.setNumber(outRow[0]);
                outAudit.setCodePassed(Integer.valueOf(outRow[1]));
                outAudit.setCodeFailed(outRow[2]);
                outAudit.setMessage(outRow[3]);
                outAudit.setCreatedDate(currentDate);
                outAudit.setTaskId(task.getId());
                outAudit.setJobId(task.getJob().getId());
                outAuditDao.save(outAudit);
                if(outAudit.getCodePassed().equals(0)) {
                    logger.info("Number to be reserved:"+outAudit.getNumber());
                    taskLog.append("Number to be reserved:"+outAudit.getNumber()+"\n");
                    task.setSummary(taskLog.toString());
                    String message = bccsDao.reserveNumber(outAudit.getNumber());
                    if(message.contains("OK"))  {
                        logger.info("Number has been reserved correctly:"+message);
                        taskLog.append("Number has been reserved correctly:"+message+"\n");
                        task.setSummary(taskLog.toString());
                    } else  {
                        logger.info("Number has NOT been reserved, calling rollback:"+message);
                        taskLog.append("Number has NOT been reserved, calling rollback:"+message+"\n");
                        task.setSummary(taskLog.toString());
                        luckyNumbersDao.unReserveNumber(outAudit.getNumber(),false);
                        taskCompletedOK=false;
                        logger.info("Rollback completed:"+message);
                        taskLog.append("Rollback completed:"+message+"\n");
                        task.setSummary(taskLog.toString());
                    }
                }
            }
            logger.info("values:city="+task.getCity()+",from="+task.getFrom()+",to="+task.getTo());
            List<InAudit> unlockedNumbers = bccsDao.unlockNumbers(task.getCity(),task.getFrom(),task.getTo());
            logger.info("unlockedNumbers: "+unlockedNumbers);
            taskLog.append("unlockedNumbers: "+unlockedNumbers+"\n");
            task.setSummary(taskLog.toString());
            //TODO ejecutar el query de conciliacion
            if(taskCompletedOK)
                task.setStatus(Status.COMPLETED_OK.name());
            else
                task.setStatus(Status.COMPLETED_WITH_ERRORS.name());

            logger.info("Task completed: "+task.getStatus());
            taskLog.append("Task completed: "+task.getStatus()+"\n");
            task.setSummary(taskLog.toString());


        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }
}
