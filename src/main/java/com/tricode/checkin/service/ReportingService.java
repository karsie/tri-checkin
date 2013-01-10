package com.tricode.checkin.service;

import com.tricode.checkin.model.WeekReport;

public interface ReportingService {

    WeekReport get(int userId, int year, int week);

    WeekReport save(WeekReport weekReport);

}
