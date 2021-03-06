package com.tricode.checkin.service.memory;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.MonthReport;
import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.ReportingService;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryReportingService implements ReportingService {

    private final Map<String, UserReport> data = new HashMap<String, UserReport>();

    private final EventManager eventManager;

    @Autowired
    public InMemoryReportingService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public WeekReport getWeek(int userId, int year, int week) {
        WeekReport weekReport = (WeekReport) data.get(toId(userId, year, week));
        if (weekReport == null) {
            weekReport = WeekReport.Builder.withUserId(userId).withYear(year).withWeek(week).get();
        }
        return weekReport;
    }

    @Override
    public MonthReport getMonth(int userId, int year, int month) {
        throw new NotImplementedException();
    }

    @Override
    public <T extends UserReport> T save(T userReport) {
        final UserReport oldValue = data.put(toId(userReport), userReport);
        if (oldValue == null) {
            eventManager.raiseCreateEvent(userReport);
        } else {
            eventManager.raiseUpdateEvent(oldValue, userReport);
        }
        return userReport;
    }

    @Override
    public List<Integer> listWeekReportYears() {
        throw new NotImplementedException();
    }

    @Override
    public List<Integer> listWeekReportWeeks(int year) {
        throw new NotImplementedException();
    }

    @Override
    public List<Integer> listMonthReportYears() {
        throw new NotImplementedException();
    }

    @Override
    public List<Integer> listMonthReportMonths(int year) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        data.clear();
    }

    private static String toId(int userId, int year, int week) {
        return userId + "-" + year + "-" + week;
    }

    private static String toId(UserReport userReport) {
        if (userReport instanceof WeekReport) {
            WeekReport weekReport = (WeekReport) userReport;
            return toId(userReport.getUserId(), weekReport.getYear(), weekReport.getWeek());
        } else {
            return null;
        }
    }
}
