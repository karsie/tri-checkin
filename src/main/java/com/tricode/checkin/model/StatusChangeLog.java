package com.tricode.checkin.model;

public class StatusChangeLog {

    private int userId;
    private LocationStatus statusFrom;
    private LocationStatus statusTo;
    private long timestamp;

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

    public String toId() {
        return "" + userId + "-" + timestamp;
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

        private int id;
        private long timestamp;
        private LocationStatus statusFrom;
        private LocationStatus statusTo;

        private Builder(int id) {
            this.id = id;
        }

        public static Builder withId(int id) {
            return new Builder(id);
        }

        public Builder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withStatusFrom(LocationStatus status) {
            this.statusFrom = status;
            return this;
        }

        public Builder withStatusTo(LocationStatus status) {
            this.statusTo = status;
            return this;
        }

        public Builder withStatuses(LocationStatus from, LocationStatus to) {
            this.statusFrom = from;
            this.statusTo = to;
            return this;
        }

        public StatusChangeLog createInstance() {
            final StatusChangeLog statusChangeLog = new StatusChangeLog();
            statusChangeLog.setUserId(id);
            statusChangeLog.setStatusFrom(statusFrom);
            statusChangeLog.setStatusTo(statusTo);
            statusChangeLog.setTimestamp(timestamp);
            return statusChangeLog;
        }
    }
}
