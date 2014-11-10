package bo.net.tigo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by aralco on 11/8/14.
 */
public class Task {
    private Long id;
    private String type; //{FROZEN;FREE}
    private Integer city;
    private String from;
    private String to;
    @JsonSerialize(using=DateSerializer.class)
    private Date executionDate;  //yyyy-MM-ddThh:mm:ss±hh:mm; e.g. 2014-11-05T12:54:00-04:00
    private String status; //{SCHEDULED;STARTED;COMPLETED_OK;CANCELLED;COMPLETED_WITH_ERRORS}            
    private Integer processed;
    private Integer passed;
    private Integer failed;
    private String summary;
    private String coverage; //{0%-100%}
    private String urlin; //ftp link for .in file?
    private String urlout; //ftp link for .out file?
    @JsonSerialize(using=DateSerializer.class)
    private Date createdDate;
    @JsonSerialize(using=DateSerializer.class)
    private Date lastUpdate;
    @NotNull
    private Job job;

    public Task(Long id, String type, Integer city, String from, String to, Date executionDate, String status, Integer processed, Integer passed, Integer failed, String summary, String coverage, String urlin, String urlout, Date createdDate, Date lastUpdate, Job job) {
        this.id = id;
        this.type = type;
        this.city = city;
        this.from = from;
        this.to = to;
        this.executionDate = executionDate;
        this.status = status;
        this.processed = processed;
        this.passed = passed;
        this.failed = failed;
        this.summary = summary;
        this.coverage = coverage;
        this.urlin = urlin;
        this.urlout = urlout;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.job = job;
    }

    public Task(Long id, String type, Integer city, String from, String to, Date executionDate, String status, Integer processed, Integer passed, Integer failed, String summary, String coverage, String urlin, String urlout, Date createdDate, Date lastUpdate) {
        this.id = id;
        this.type = type;
        this.city = city;
        this.from = from;
        this.to = to;
        this.executionDate = executionDate;
        this.status = status;
        this.processed = processed;
        this.passed = passed;
        this.failed = failed;
        this.summary = summary;
        this.coverage = coverage;
        this.urlin = urlin;
        this.urlout = urlout;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getUrlin() {
        return urlin;
    }

    public void setUrlin(String urlin) {
        this.urlin = urlin;
    }

    public String getUrlout() {
        return urlout;
    }

    public void setUrlout(String urlout) {
        this.urlout = urlout;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
