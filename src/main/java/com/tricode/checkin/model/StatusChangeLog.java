package com.tricode.checkin.model;

public class StatusChangeLog {

    private int userId;
    private LocationStatus statusFrom;
    private LocationStatus statusTo;
    private long timestamp;

    public StatusChangeLog(int userId, LocationStatus statusFrom, LocationStatus statusTo, long timestamp) {
        this.userId = userId;
        this.statusFrom = statusFrom;
        this.statusTo = statusTo;
        this.timestamp = timestamp;
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
}
