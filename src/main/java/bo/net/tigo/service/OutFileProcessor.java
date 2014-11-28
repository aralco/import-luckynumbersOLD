package bo.net.tigo.service;

import bo.net.tigo.dao.InAuditDao;
import bo.net.tigo.dao.OutAuditDao;
import bo.net.tigo.dao.TaskDao;
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
import java.util.regex.Pattern;

/**
 * Created by aralco on 11/20/14.
 */
@Service
public class OutFileProcessor {
    @Autowired
    private InAuditDao inAuditDao;
    @Autowired
    private OutAuditDao outAuditDao;
    @Autowired
    private TaskDao taskDao;

    private static final Logger logger = LoggerFactory.getLogger(OutFileProcessor.class);

    @Transactional
    public void processOutFile(File file) throws Exception{
        StringBuilder taskLog = new StringBuilder();
        logger.info("Processing File: " + file);
        BufferedReader bufferedReader = null;
        String splitBy = ",";
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            int processed=0;
            int passed=0;
            int failed=0;
            //TODO fix percentage maybe we could ask this value from .in file
            float percentage=75;
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
            task.setUrlout(fileName);
            task.setStatus(Status.STARTED_PHASE2.name());
            taskLog.append("********************************************************** ||");
            taskLog.append("Procesando archivo .out: ").append(fileName).append("||");
            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                logger.info("line to process:"+line);
                String[] outRow = line.split(splitBy);
                logger.info("array Size:"+outRow.length+",values:"+outRow);
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
                } else {
                    failed++;
                }
            }

            Job job = task.getJob();
            Long inFiles = inAuditDao.countInFilesByJob(job.getId());
            Long outFiles = outAuditDao.countOutFilesByJob(job.getId());

            logger.info("Total created files: "+(inFiles==null?0:inFiles)+"(.in) -VS- "+(outFiles==null?0:outFiles)+"(.out)");
            logger.info("Out file processing successfully completed.");
            taskLog.append("Procesamiento del archivo .out completado exitosamente. ||");

            String jobSummary =
                    "Total de archivos .in creados: "+(inFiles==null?0:inFiles)+" ||"+
                    "Total de archivos .out creados: "+(outFiles==null?0:outFiles)+" ||";

            job.setSummary(jobSummary);
            job.setTotalCoverage(percentage+"%");
            job.setLastUpdate(currentDate);

            task.setProcessed(processed);
            task.setPassed(passed);
            task.setFailed(failed);
            task.setCoverage(percentage+"%");
            task.setSummary(task.getSummary()+taskLog.toString());
            task.setLastUpdate(currentDate);

        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException for file:" + file + ", with errors: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IOException for file:" + file + ", with errors: " + e.getMessage());
            e.printStackTrace();
        } catch (LuckyNumbersGenericException e) {
            logger.warn("LuckyNumbersGenericException -> error:" + e.getErrorCode() + ", message: " + e.getErrorMessage());
        } catch (Exception e)  {
            logger.error("Unknown exception -> error:" + e.toString() + ", message: " + e.getMessage());
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
