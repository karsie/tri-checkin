package com.tricode.checkin.service.memory;

import com.tricode.checkin.event.manager.EventManager;
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

    private final Map<String, WeekReport> data = new HashMap<String, WeekReport>();

    private final EventManager eventManager;

    @Autowired
    public InMemoryReportingService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public WeekReport get(int userId, int year, int week) {
        WeekReport weekReport = data.get(toId(userId, year, week));
        if (weekReport == null) {
            weekReport = WeekReport.Builder.withUserId(userId).withYear(year).withWeek(week).get();
        }
        return weekReport;
    }

    @Override
    public WeekReport save(WeekReport weekReport) {
        final WeekReport oldValue = data.put(toId(weekReport), weekReport);
        if (oldValue == null) {
            eventManager.raiseCreateEvent(weekReport);
        } else {
            eventManager.raiseUpdateEvent(oldValue, weekReport);
        }
        return weekReport;
    }

    @Override
    public List<Integer> listStoredYears() {
        throw new NotImplementedException();
    }

    @Override
    public List<Integer> listStoredWeeks(int year) {
        throw new NotImplementedException();
    }

    private static String toId(int userId, int year, int week) {
        return userId + "-" + year + "-" + week;
    }

    private static String toId(WeekReport weekReport) {
        return toId(weekReport.getUserId(), weekReport.getYear(), weekReport.getWeek());
    }
}
