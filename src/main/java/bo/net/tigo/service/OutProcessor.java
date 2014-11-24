package bo.net.tigo.service;

import bo.net.tigo.dao.*;
import bo.net.tigo.exception.LuckyNumbersGenericException;
import bo.net.tigo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
    public void process(File file) throws Exception{
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
            logger.info("Filename to Process:"+fileName);
            boolean goodFileToProcess = Pattern.matches("[0-9]{8}_[0-9]{6}\\.out", fileName);
            if(!goodFileToProcess)    {
                throw new LuckyNumbersGenericException(HttpStatus.PRECONDITION_FAILED.toString(),"Filename:"+fileName+" to be processed is not valid.");
            }

            String shortFilename = fileName.substring(0,15);
            logger.info("array Size:"+shortFilename.length()+",values:"+shortFilename);
            Task task = taskDao.findByFileName(shortFilename);
            if(task==null)  {
                throw new LuckyNumbersGenericException(HttpStatus.NOT_FOUND.toString(),"Related task for filename:"+fileName+" cannot be found or task doesn't exist yet.");
            } else if(task.getUrlout()!=null) {
                throw new LuckyNumbersGenericException(HttpStatus.CONFLICT.toString(),"Related task for filename:"+fileName+ " might already been processed. TaskId="+task.getId());
            }
            taskLog.append("|| ************************** ||");
            taskLog.append("Processing .out File: ").append(fileName).append("||");
            task.setUrlout(fileName);
            task.setLastUpdate(currentDate);
            task.setStatus(Status.STARTED_PHASE2.name());
            boolean taskCompletedOK=true;
            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                logger.info("line to process:"+line);
                taskLog.append("Processing line: ").append(line).append("||");
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
                outAudit.setLuckyReserved(false);
                outAudit.setCreatedDate(currentDate);
                outAudit.setTaskId(task.getId());
                outAudit.setJobId(task.getJob().getId());
                outAuditDao.save(outAudit);
                processed++;
                if(outAudit.getCodePassed().equals(0)) {
                    passed++;
                    logger.info("Lucky Number to be reserved:"+outAudit.getNumber());
                    taskLog.append("Lucky Number to be reserved:").append(outAudit.getNumber()).append("||");
                    String message = bccsDao.reserveNumber(outAudit.getNumber());
                    if(message!=null && message.contains("OK"))  {
                        logger.info("Number has been correctly reserved :"+message);
                        taskLog.append("Number has been correctly reserved :").append(message).append("||");
                        outAudit.setLuckyReserved(true);
                        reservedLuckyNumbers++;
                    } else  {
                        logger.info("Number has NOT been reserved, calling rollback:"+message);
                        taskLog.append("Number has NOT been reserved, calling rollback:").append(message).append("||");
                        luckyNumbersDao.unReserveNumber(outAudit.getNumber(),false);
                        taskCompletedOK=false;
                        logger.info("Rollback completed:"+message);
                        taskLog.append("Rollback completed:").append(message).append("||");
                    }
                } else {
                    failed++;
                    logger.info("Number processed is not lucky:"+outAudit.getNumber());
                    taskLog.append("Number processed is not lucky:").append(outAudit.getNumber()).append("||");
                }
            }
            //CONCILIATION: Call BCCS to ask for LN numbers only if there are lucky numbers
            if(reservedLuckyNumbers>0)  {
                logger.info("Calling BCCS:SP1_LNROSLNXSUCURSALNRODESDEHASTA to ask for conciliation of LN numbers using values:city="+task.getCity()+",from="+task.getFrom()+",to="+task.getTo());
                taskLog.append("Calling BCCS:SP1_LNROSLNXSUCURSALNRODESDEHASTA to ask for conciliation of LN numbers using values:city=").append(task.getCity()).append(",from=").append(task.getFrom()).append(",to=").append(task.getTo()).append(" ||");
                List<InAudit> lnNumbers = bccsDao.getLnNumbers(task.getCity(),task.getFrom(),task.getTo());
                if(lnNumbers!=null) {
                    if(lnNumbers.size()==reservedLuckyNumbers)
                        taskCompletedOK=true;
                    else
                        taskCompletedOK=false;
                    logger.info("LuckyNumbers in -> BCCS:"+lnNumbers.size()+" -VS- LUCKY_NUMBERS:"+reservedLuckyNumbers);
                    taskLog.append("LuckyNumbers in -> BCCS:").append(lnNumbers.size()).append(" -VS- ").append(reservedLuckyNumbers).append(":LUCKY_NUMBERS ||");
                } else  {
                    taskCompletedOK=false;
                    logger.info("LN Numbers in -> BCCS seems to be empty.");
                    taskLog.append("LN Numbers in -> BCCS seems to be empty. ||");
                }
            } else {
                logger.info("No Lucky Numbers found on processed file");
                taskLog.append("No Lucky Numbers found on processed file. ||");
            }

            //CONCILIATION: Call to BCCS to change state of numbers to LC only for FREE numbers
            if(task.getType().equals(Type.FREE.name())) {
                logger.info("Calling BCCS:SP2_LNROSLCXSUCURSALNRODESDEHASTAPORC_ACTESTLI1 to unlock numbers using values:city="+task.getCity()+",from="+task.getFrom()+",to="+task.getTo());
                taskLog.append("Calling BCCS:SP2_LNROSLCXSUCURSALNRODESDEHASTAPORC_ACTESTLI1 to unlock numbers using values:city=").append(task.getCity()).append(",from=").append(task.getFrom()).append(",to=").append(task.getTo()).append(" ||");
                List<InAudit> unlockedNumbers = bccsDao.unlockNumbers(task.getCity(),task.getFrom(),task.getTo());
                if(unlockedNumbers!=null)   {
                    if(unlockedNumbers.size()>0)
                        taskCompletedOK=true;
                    else
                        taskCompletedOK=false;
                    logger.info("unlockedNumbers: "+unlockedNumbers);
                    taskLog.append("unlockedNumbers: ").append(unlockedNumbers).append("||");
                } else  {
                    taskCompletedOK=false;
                    logger.info("LCNumbers in -> BCCS seems to be empty.");
                    taskLog.append("LCNumbers in -> BCCS seems to be empty. ||");
                }
                //Call to BCCS to ask for LC numbers only for FREE numbers
                logger.info("Calling BCCS:SP1_LNROSLCXSUCURSALNRODESDEHASTA to list unlocked numbers using values:city="+task.getCity()+",from="+task.getFrom()+",to="+task.getTo());
                taskLog.append("Calling BCCS:SP1_LNROSLCXSUCURSALNRODESDEHASTA to list unlocked numbers using values:city=").append(task.getCity()).append(",from=").append(task.getFrom()).append(",to=").append(task.getTo()).append(" ||");
                List<InAudit> lcNumbers = bccsDao.getLcNumbers(task.getCity(), task.getFrom(), task.getTo());
                if(lcNumbers!=null)  {
                    if(lcNumbers.size()==0)
                        taskCompletedOK=true;
                    else
                        taskCompletedOK=false;
                    logger.info("LCNumbers in -> BCCS:"+lcNumbers.size());
                    taskLog.append("LCNumbers in -> BCCS:").append(lcNumbers.size()).append(" ||");
                }
                else    {
                    taskCompletedOK=false;
                    logger.info("LCNumbers in -> BCCS seems to be empty.");
                    taskLog.append("LCNumbers in -> BCCS seems to be empty. ||");
                }

            }

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
            Long inFiles = inAuditDao.countInFilesByJob(job.getId());
            Long outFiles = outAuditDao.countOutFilesByJob(job.getId());
            logger.info("Created files -> .in:"+inFiles+" -VS- .out:"+outFiles);
            job.setSummary(job.getSummary()+"|| Created files -> .in:"+inFiles+" -VS- :.out"+outFiles+" ||");
            job.setLastUpdate(currentDate);
            logger.info("Task completed: "+task.getStatus());
            taskLog.append("Task completed: ").append(task.getStatus()).append("||");
            task.setSummary(task.getSummary()+taskLog.toString());
            task.setProcessed(processed);
            task.setPassed(passed);
            task.setFailed(failed);
            task.setCoverage(String.valueOf(percentage)+" %");

        } catch (FileNotFoundException e) {
                logger.error("FileNotFoundException for file:" + file + ", with errors: " + e.getMessage());
                e.printStackTrace();
        } catch (IOException e) {
            logger.error("IOException for file:" + file + ", with errors: " + e.getMessage());
            e.printStackTrace();
        } catch (LuckyNumbersGenericException e) {
                logger.warn("LuckyNumbersGenericException -> error:"+e.getErrorCode()+", message: "+e.getErrorMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("IOException for file:"+file+", with errors: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

}
