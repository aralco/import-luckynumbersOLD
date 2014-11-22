package bo.net.tigo.service;

import bo.net.tigo.dao.*;
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
    private InAuditDao inAuditDao;
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
            int reservedLuckyNumbers=0;
            int processed=0;
            int passed=0;
            int failed=0;
            //TODO fix percentage maybe we could ask this value from .in file
            float percentage=100;
            Date currentDate = Calendar.getInstance().getTime();
            String fileName = file.getName();
            logger.info("filename to Process:"+fileName);
            String shortFilename = fileName.substring(0,15);
            logger.info("array Size:"+shortFilename.length()+",values:"+shortFilename);
            //TODO create a exception when file doesn't exist in db
            Task task = taskDao.findByFileName(shortFilename);
            taskLog.append(task.getSummary()+"|| ************************** ||");
            task.setSummary(taskLog.toString());
            taskLog.append("Processing .out File: " + fileName+"||");
            task.setUrlout(fileName);
            task.setLastUpdate(currentDate);
            task.setStatus(Status.STARTED_PHASE2.name());
            boolean taskCompletedOK=true;
            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                logger.info("line to process:"+line);
                taskLog.append("Processing line: " + line+"||");
                task.setSummary(taskLog.toString());
                task.setProcessed(processed);
                task.setPassed(passed);
                task.setFailed(failed);
                task.setCoverage(String.valueOf(percentage)+" %");
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
                processed++;
                if(outAudit.getCodePassed().equals(0)) {
                    passed++;
                    logger.info("Number to be reserved:"+outAudit.getNumber());
                    taskLog.append("Number to be reserved:"+outAudit.getNumber()+"||");
                    task.setSummary(taskLog.toString());
                    String message = bccsDao.reserveNumber(outAudit.getNumber());
                    if(message.contains("OK"))  {
                        logger.info("Number has been reserved correctly:"+message);
                        taskLog.append("Number has been reserved correctly:"+message+"||");
                        task.setSummary(taskLog.toString());
                        outAudit.setLuckyReserved(true);
                        reservedLuckyNumbers++;
                    } else  {
                        logger.info("Number has NOT been reserved, calling rollback:"+message);
                        taskLog.append("Number has NOT been reserved, calling rollback:"+message+"||");
                        task.setSummary(taskLog.toString());
                        luckyNumbersDao.unReserveNumber(outAudit.getNumber(),false);
                        taskCompletedOK=false;
                        logger.info("Rollback completed:"+message);
                        taskLog.append("Rollback completed:"+message+"||");
                        task.setSummary(taskLog.toString());
                        outAudit.setLuckyReserved(false);
                    }
                } else {
                    failed++;
                }
            }
            //Call BCCS to ask for LN numbers
            logger.info("Calling BCCS to ask for conciliation of LN numbers");
            taskLog.append("Calling BCCS to ask for conciliation of LN numbers ||");
            task.setSummary(taskLog.toString());
            List<InAudit> lnNumbers = bccsDao.getLnNumbers(task.getCity(),task.getFrom(),task.getTo());
            logger.info("LuckyNumbers in -> BCCS:"+lnNumbers.size()+" VS LUCKY_NUMBERS:"+reservedLuckyNumbers);
            taskLog.append("LuckyNumbers in -> BCCS:"+lnNumbers.size()+" VS "+reservedLuckyNumbers+":LUCKY_NUMBERS ||");
            task.setSummary(taskLog.toString());
            //Call to BCCS to change state of numbers to LC
            logger.info("values:city="+task.getCity()+",from="+task.getFrom()+",to="+task.getTo());
            List<InAudit> unlockedNumbers = bccsDao.unlockNumbers(task.getCity(),task.getFrom(),task.getTo());
            logger.info("unlockedNumbers: "+unlockedNumbers);
            taskLog.append("unlockedNumbers: "+unlockedNumbers+"||");
            task.setSummary(taskLog.toString());
            //Call to BCCS to ask for LC numbers
            logger.info("Calling BCCS to ask for conciliation of LC numbers");
            taskLog.append("Calling BCCS to ask for conciliation of LC numbers ||");
            task.setSummary(taskLog.toString());
            List<InAudit> lcNumbers = bccsDao.getLcNumbers(task.getCity(), task.getFrom(), task.getTo());
            logger.info("LCNumbers in -> BCCS:"+lcNumbers.size());
            taskLog.append("LCNumbers in -> BCCS:" + lcNumbers.size() + " ||");
            task.setSummary(taskLog.toString());
            if(lnNumbers.size()==reservedLuckyNumbers && lcNumbers.size()==0)
                taskCompletedOK=true;
            else
                taskCompletedOK=false;

            Job job = task.getJob();
            //TODO calculate the coverage based on tasks
            float jobPercentage=100;
            if(taskCompletedOK) {
                task.setStatus(Status.COMPLETED_OK.name());
                job.setPassedTasks(job.getPassedTasks()+1);
            }
            else {
                task.setStatus(Status.COMPLETED_WITH_ERRORS.name());
                job.setPassedTasks(job.getFailedTasks()+1);
            }
            job.setTotalCoverage(jobPercentage+"%");
            //MEANS that a job is completed or requires some criteria acceptance
            if(job.getTotalTasks().equals(job.getPassedTasks()))    {
                job.setState(State.DONE.name());
            } else if(job.getTotalTasks().equals(job.getPassedTasks()+job.getFailedTasks())) {
                job.setState(State.CRITERIA_ACCEPTANCE.name());
            }
            job.setSummary(job.getSummary()+"Created files -> .in:"+(inAuditDao.countInFilesByJob(job.getId()))+" -VS- :.out"+(outAuditDao.countOutFilesByJob(job.getId()))+" ||");
            logger.info("Task completed: "+task.getStatus());
            taskLog.append("Task completed: "+task.getStatus()+"||");
            task.setSummary(taskLog.toString());
            task.setProcessed(processed);
            task.setPassed(passed);
            task.setFailed(failed);
            task.setCoverage(String.valueOf(percentage)+" %");

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
