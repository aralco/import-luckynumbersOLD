package bo.net.tigo.util;

import bo.net.tigo.dao.FreeAndFrozenDao;
import bo.net.tigo.dao.OutAuditDao;
import bo.net.tigo.dao.TaskDao;
import bo.net.tigo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by aralco on 11/20/14.
 */
public class OutProcessor {
    @Autowired
    private OutAuditDao outAuditDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private FreeAndFrozenDao freeAndFrozenDao;

    private static final Logger logger = LoggerFactory.getLogger(OutProcessor.class);


    @Transactional
    public File process(File file) throws Exception{
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
            Task task = taskDao.findByFileName(shortFilename);
            task.setUrlout(fileName);
            task.setLastUpdate(currentDate);
            task.setStatus(Status.STARTED_PHASE2.name());

            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                logger.info("line to process:"+line);
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
                    String message = freeAndFrozenDao.reserveNumber(outAudit.getNumber());
                    logger.info("ReservedNumber:"+message);
                }
            }
            List<InAudit> unlockedNumbers = freeAndFrozenDao.unlockNumbers(task.getCity(),task.getFrom(),task.getTo());
            logger.info("unlockedNumbers: "+unlockedNumbers);
            task.setStatus(Status.COMPLETED_OK.name());


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

        //TODO save in OUTAudit table
        //TODO Extract luckynumbers
        //TODO make reservation
        //
        return file;
    }
}
