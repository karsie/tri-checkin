package com.tricode.checkin.model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class EatingInLog extends Log implements Serializable {

    private static final long serialVersionUID = 5266286788292191344L;

    private boolean eatingIn;

    public boolean isEatingIn() {
        return eatingIn;
    }

    public void setEatingIn(boolean eatingIn) {
        this.eatingIn = eatingIn;
    }

    @Override
    public String toString() {
        return "EatingInLog{" +
                super.toString() +
                "eatingIn=" + eatingIn +
                '}';
    }

    public static class Builder {

        private final EatingInLog log;

        private Builder(Integer id) {
            log = new EatingInLog();
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

        public Builder withEatingIn(Boolean eatingIn) {
            log.setEatingIn(eatingIn);
            return this;
        }

        public EatingInLog get() {
            return log;
        }
    }
}
