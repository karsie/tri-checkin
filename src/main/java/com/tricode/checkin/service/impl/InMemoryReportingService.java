package com.tricode.checkin.service.impl;

import com.tricode.checkin.model.ReportData;
import com.tricode.checkin.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryReportingService implements ReportingService {

    private final Map<String, ReportData> data = new HashMap<String, ReportData>();

    private final ReportingIdFactory reportingIdFactory;

    @Autowired
    public InMemoryReportingService(ReportingIdFactory reportingIdFactory) {
        this.reportingIdFactory = reportingIdFactory;
    }

    @Override
    public ReportData get(int userId, int year, int week) {
        ReportData reportData = data.get(reportingIdFactory.get(userId, year, week));
        if (reportData == null) {
            reportData = new ReportData(userId, year, week);
        }
        return reportData;
    }

    @Override
    public ReportData save(ReportData reportData) {
        data.put(reportingIdFactory.get(reportData.getUserId(), reportData.getYear(), reportData.getWeek()), reportData);
        return reportData;
    }
}
