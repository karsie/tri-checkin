package com.tricode.checkin.persistence;

import com.tricode.checkin.model.WeekReport;

public interface WeekReportRepository extends Repository<WeekReport> {

    WeekReport getByUserIdYearAndWeek(int userId, int year, int week);
}
