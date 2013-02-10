package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.MonthReport;
import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.persistence.UserReportRepository;
import com.tricode.checkin.service.AbstractService;
import com.tricode.checkin.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersistentReportingService extends AbstractService implements ReportingService {

    private final UserReportRepository userReportRepository;

    @Autowired
    public PersistentReportingService(UserReportRepository userReportRepository, EventManager eventManager) {
        super(eventManager);
        this.userReportRepository = userReportRepository;
    }

    @Override
    public WeekReport getWeek(int userId, int year, int week) {
        WeekReport weekReport = userReportRepository.getWeekReportByYearAndWeek(userId, year, week);
        if (weekReport == null) {
            weekReport = WeekReport.Builder.withUserId(userId).withYear(year).withWeek(week).get();
        }
        return weekReport;
    }

    @Override
    public MonthReport getMonth(int userId, int year, int month) {
        MonthReport monthReport = userReportRepository.getMonthReportByYearAndMonth(userId, year, month);
        if (monthReport == null) {
            monthReport = MonthReport.Builder.withUserId(userId).withYear(year).withMonth(month).get();
        }
        return monthReport;
    }

    @Override
    public <T extends UserReport> T save(T userReport) {
        final UserReport oldValue;
        if (userReport.getId() != null) {
            oldValue = userReportRepository.get(userReport.getClass(), userReport.getId()).clone();
        } else {
            oldValue = null;
        }

        final T saved = userReportRepository.save(userReport);

        if (oldValue != null) {
            eventManager.raiseUpdateEvent(oldValue, userReport);
        } else {
            eventManager.raiseCreateEvent(saved);
        }
        return saved;
    }

    @Override
    public List<Integer> listWeekReportYears() {
        return userReportRepository.findWeekReportYears();
    }

    @Override
    public List<Integer> listWeekReportWeeks(int year) {
        return userReportRepository.findWeekReportWeeks(year);
    }

    @Override
    public List<Integer> listMonthReportYears() {
        return userReportRepository.findMonthReportYears();
    }

    @Override
    public List<Integer> listMonthReportMonths(int year) {
        return userReportRepository.findMonthReportMonths(year);
    }
}
