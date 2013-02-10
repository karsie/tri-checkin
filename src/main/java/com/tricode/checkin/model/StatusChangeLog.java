package com.tricode.checkin.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Entity
public class StatusChangeLog extends Log implements Serializable {

    private static final long serialVersionUID = -5781552727094648406L;

    @Enumerated(EnumType.STRING)
    private LocationStatus statusFrom;

    @Enumerated(EnumType.STRING)
    private LocationStatus statusTo;

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

    @Override
    public String toString() {
        return "StatusChangeLog{" +
                super.toString() +
                "statusFrom=" + statusFrom +
                ", statusTo=" + statusTo +
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
