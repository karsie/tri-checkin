package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.persistence.UserReportRepository;
import com.tricode.checkin.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersistentReportingService implements ReportingService {

    private final UserReportRepository weekReportRepository;

    private final EventManager eventManager;

    @Autowired
    public PersistentReportingService(UserReportRepository weekReportRepository, EventManager eventManager) {
        this.weekReportRepository = weekReportRepository;
        this.eventManager = eventManager;
    }

    @Override
    public WeekReport get(int userId, int year, int week) {
        WeekReport weekReport = weekReportRepository.getWeekReportByYearAndWeek(userId, year, week);
        if (weekReport == null) {
            weekReport = WeekReport.Builder.withUserId(userId).withYear(year).withWeek(week).get();
        }
        return weekReport;
    }

    @Override
    public <T extends UserReport> T save(T userReport) {
        final UserReport oldValue;
        if (userReport.getId() != null) {
            oldValue = weekReportRepository.get(userReport.getClass(), userReport.getId()).clone();
        } else {
            oldValue = null;
        }

        final T saved = weekReportRepository.save(userReport);

        if (oldValue != null) {
            eventManager.raiseUpdateEvent(oldValue, userReport);
        } else {
            eventManager.raiseCreateEvent(saved);
        }
        return saved;
    }

    @Override
    public List<Integer> listStoredYears() {
        return weekReportRepository.findWeekReportYears();
    }

    @Override
    public List<Integer> listStoredWeeks(int year) {
        return weekReportRepository.findWeekReportWeeks(year);
    }
}
