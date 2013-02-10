package com.tricode.checkin.service;

import com.tricode.checkin.model.MonthReport;
import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;

import java.util.List;

public interface ReportingService {

    WeekReport getWeek(int userId, int year, int week);

    MonthReport getMonth(int userId, int year, int month);

    <T extends UserReport> T save(T userReport);

    List<Integer> listWeekReportYears();

    List<Integer> listWeekReportWeeks(int year);

    List<Integer> listMonthReportYears();

    List<Integer> listMonthReportMonths(int year);
}
