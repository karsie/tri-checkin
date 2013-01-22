package com.tricode.checkin.persistence;

import com.tricode.checkin.model.WeekReport;

import java.util.List;

public interface WeekReportRepository extends Repository<WeekReport> {

    WeekReport getByUserIdYearAndWeek(int userId, int year, int week);

    List<Integer> findYears();

    List<Integer> findWeeks(int year);
}
