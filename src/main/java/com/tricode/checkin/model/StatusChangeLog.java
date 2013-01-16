package com.tricode.checkin.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class StatusChangeLog implements Serializable {

    private static final long serialVersionUID = -5781552727094648406L;

    @Id
    @GeneratedValue
    private Integer id;

    private int userId;

    @Enumerated(EnumType.STRING)
    private LocationStatus statusFrom;

    @Enumerated(EnumType.STRING)
    private LocationStatus statusTo;

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

    public LocationStatus getStatusFrom() {
        return statusFrom;
    }

    public void setStatusFrom(LocationStatus statusFrom) {
        this.statusFrom = statusFrom;
    }

    public LocationStatus getStatusTo() {
        return statusTo;
    }

    public void setStatusTo(LocationStatus statusTo) {
        this.statusTo = statusTo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StatusChangeLog{" +
                "userId=" + userId +
                ", statusFrom=" + statusFrom +
                ", statusTo=" + statusTo +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder {

        private final StatusChangeLog log;

        private Builder(Integer id) {
            log = new StatusChangeLog();
            log.setId(id);
        }

        private Builder(Integer id, int userId) {
            this(id);
            log.setUserId(userId);
        }

        public static Builder withId(int id) {
            return new Builder(id);
        }

        public static Builder empty() {
            return new Builder(null);
        }

        public static Builder withUserId(int id) {
            return new Builder(null, id);
        }

        public Builder withTimestamp(long timestamp) {
            log.setTimestamp(timestamp);
            return this;
        }

        public Builder withStatusFrom(LocationStatus status) {
            log.setStatusFrom(status);
            return this;
        }

        public Builder withStatusTo(LocationStatus status) {
            log.setStatusTo(status);
            return this;
        }

        public Builder withStatuses(LocationStatus from, LocationStatus to) {
            log.setStatusFrom(from);
            log.setStatusTo(to);
            return this;
        }

        public StatusChangeLog get() {
            return log;
        }
    }
}
