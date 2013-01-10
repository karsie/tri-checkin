package com.tricode.checkin.service.impl;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryReportingService implements ReportingService {

    private final Map<String, WeekReport> data = new HashMap<String, WeekReport>();

    private final ReportingIdFactory reportingIdFactory;
    private final EventManager eventManager;

    @Autowired
    public InMemoryReportingService(ReportingIdFactory reportingIdFactory, EventManager eventManager) {
        this.reportingIdFactory = reportingIdFactory;
        this.eventManager = eventManager;
    }

    @Override
    public WeekReport get(int userId, int year, int week) {
        WeekReport weekReport = data.get(reportingIdFactory.get(userId, year, week));
        if (weekReport == null) {
            weekReport = new WeekReport(userId, year, week);
        }
        return weekReport;
    }

    @Override
    public WeekReport save(WeekReport weekReport) {
        final WeekReport oldValue = data.put(reportingIdFactory.get(weekReport.getUserId(), weekReport.getYear(), weekReport.getWeek()), weekReport);
        if (oldValue == null) {
            eventManager.raiseCreateEvent(weekReport);
        } else {
            eventManager.raiseUpdateEvent(oldValue, weekReport);
        }
        return weekReport;
    }
}
