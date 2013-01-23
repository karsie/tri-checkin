package com.tricode.checkin.model;

import org.hibernate.annotations.NaturalId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserReport implements Cloneable {

    private static final Logger log = LoggerFactory.getLogger(WeekReport.class);

    @Id
    @GeneratedValue
    private Integer id;

    @NaturalId
    private int userId;

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

    public UserReport clone() {
        try {
            return (UserReport) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("unable to clone user report", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
