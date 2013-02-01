package com.tricode.checkin.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MonthReport extends UserReport implements Serializable {

    private static final long serialVersionUID = 881412293349374207L;

    @NaturalId
    private int year;

    @NaturalId
    private int month;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "id"))
    @OrderColumn(name = "sorting")
    private List<Boolean> eatingIn;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Boolean> getEatingIn() {
        return eatingIn;
    }

    public void setEatingIn(List<Boolean> eatingIn) {
        this.eatingIn = eatingIn;
    }

    public static class Builder {

        private final MonthReport monthReport;

        private Builder(Integer id) {
            monthReport = new MonthReport();
            monthReport.setId(id);
            final List<Boolean> defaultList = new ArrayList<Boolean>(31);
            for (int i = 0; i < 31; i++) {
                defaultList.add(Boolean.FALSE);
            }
            monthReport.setEatingIn(defaultList);
        }

        private Builder(Integer id, int userId) {
            this(id);
            monthReport.setUserId(userId);
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
            monthReport.setYear(year);
            return this;
        }

        public Builder withMonth(int month) {
            monthReport.setMonth(month);
            return this;
        }

        public MonthReport get() {
            return monthReport;
        }
    }
}