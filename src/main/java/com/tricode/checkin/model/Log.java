package com.tricode.checkin.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Log implements Cloneable {

    private static final Logger log = LoggerFactory.getLogger(Log.class);

    @Id
    @GeneratedValue
    private Integer id;

    private int userId;

    private long timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Log clone() {
        try {
            return (Log) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("unable to clone log", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                '}';
    }
}
