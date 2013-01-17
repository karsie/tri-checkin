package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.persistence.WeekReportRepository;
import com.tricode.checkin.service.ReportingService;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersistentReportingService implements ReportingService {

    private final WeekReportRepository weekReportRepository;

    private final EventManager eventManager;

    @Autowired
    public PersistentReportingService(WeekReportRepository weekReportRepository, EventManager eventManager) {
        this.weekReportRepository = weekReportRepository;
        this.eventManager = eventManager;
    }

    @Override
    public WeekReport get(int userId, int year, int week) {
        throw new NotImplementedException();
    }

    @Override
    public WeekReport save(WeekReport weekReport) {
        final WeekReport oldValue;
        if (weekReport.getId() != null) {
            oldValue = weekReportRepository.get(weekReport.getId());
        } else {
            oldValue = null;
        }

        final WeekReport saved = weekReportRepository.save(weekReport);

        if (oldValue != null) {
            eventManager.raiseUpdateEvent(oldValue, weekReport);
        } else {
            eventManager.raiseCreateEvent(saved);
        }
        return saved;
    }
}
