package com.tricode.checkin.service;

import com.tricode.checkin.model.ReportData;

public interface ReportingService {

    ReportData get(int userId, int year, int week);

    ReportData save(ReportData reportData);

}
