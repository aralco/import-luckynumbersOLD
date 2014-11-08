package bo.net.tigo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.util.Date;

/**
 * Created by aralco on 11/8/14.
 */
public class FTPParameter {
    private Long id;
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String pathin;
    private String pathout;
    private String pathtemp;
    @JsonSerialize(using=DateSerializer.class)
    private Date lastUpdate;

    public FTPParameter(Long id, String host, Integer port, String user, String password, String pathin, String pathout, String pathtemp, Date lastUpdate) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.pathin = pathin;
        this.pathout = pathout;
        this.pathtemp = pathtemp;
        this.lastUpdate = lastUpdate;
    }

    public FTPParameter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathin() {
        return pathin;
    }

    public void setPathin(String pathin) {
        this.pathin = pathin;
    }

    public String getPathout() {
        return pathout;
    }

    public void setPathout(String pathout) {
        this.pathout = pathout;
    }

    public String getPathtemp() {
        return pathtemp;
    }

    public void setPathtemp(String pathtemp) {
        this.pathtemp = pathtemp;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
