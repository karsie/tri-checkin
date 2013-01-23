package com.tricode.checkin.persistence;

import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;

import java.util.List;

public interface UserReportRepository extends Repository<UserReport> {

    <T extends UserReport> T get(Class<T> userReportClass, int id);

    WeekReport getWeekReportByYearAndWeek(int userId, int year, int week);

    List<Integer> findWeekReportYears();

    List<Integer> findWeekReportWeeks(int year);
}
