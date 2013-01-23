package com.tricode.checkin.service;

import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;

import java.util.List;

public interface ReportingService {

    WeekReport get(int userId, int year, int week);

    <T extends UserReport> T save(T userReport);

    List<Integer> listStoredYears();

    List<Integer> listStoredWeeks(int year);

}
