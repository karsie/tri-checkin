package com.tricode.checkin.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ReportData {
    private int userId;
    private int year;
    private int week;
    private List<Long> days;

    public ReportData() {
    }

    public ReportData(int userId, int year, int week) {
        this.userId = userId;
        this.year = year;
        this.week = week;
        this.days = Arrays.asList(0L, 0L, 0L, 0L, 0L);
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
}
