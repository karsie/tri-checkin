package com.tricode.checkin.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity
public class WeekReport implements Serializable {

    private static final long serialVersionUID = -7783307886739901626L;

    @Id
    @GeneratedValue
    private Integer id;

    @NaturalId
    private int userId;

    @NaturalId
    private int year;

    @NaturalId
    private int week;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "id"))
    private List<Long> days;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<Long> getDays() {
        return days;
    }

    public void setDays(List<Long> days) {
        this.days = days;
    }

    public static class Builder {

        private final WeekReport weekReport;

        private Builder(Integer id) {
            weekReport = new WeekReport();
            weekReport.setId(id);
            weekReport.setDays(Arrays.asList(0L, 0L, 0L, 0L, 0L));
        }

        private Builder(Integer id, int userId) {
            this(id);
            weekReport.setUserId(userId);
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

        public Builder withYear(int year) {
            weekReport.setYear(year);
            return this;
        }

        public Builder withWeek(int week) {
            weekReport.setWeek(week);
            return this;
        }

        public Builder withDays(List<Long> days) {
            weekReport.setDays(days);
            return this;
        }

        public Builder withDay(int index, Long value) {
            weekReport.getDays().set(index, value);
            return this;
        }

        public WeekReport get() {
            return weekReport;
        }
    }
}
