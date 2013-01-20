package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.EatingInLog;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EatingInLogCreatedListener implements EventListener<EatingInLog> {

    private static final Logger log = LoggerFactory.getLogger(EatingInLogCreatedListener.class);

    private final ReportingService reportingService;

    @Autowired
    public EatingInLogCreatedListener(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @Override
    public void onEvent(EatingInLog objectBefore, EatingInLog objectAfter, EventType eventType) {
        log.debug("EIL event");

        final DateTime now = DateTime.now();
        int dayIndex = now.getDayOfWeek() - 1; // MONDAY = 1, so index = 0
        if (dayIndex == 6) { // SUNDAY
            dayIndex = 0; // becomes MONDAY
        } else if (dayIndex == 5) { // SATURDAY
            dayIndex = 4; // becomes FRIDAY
        }

        final WeekReport weekReport = reportingService.get(objectAfter.getUserId(), now.getWeekyear(), now.getWeekOfWeekyear());

        weekReport.getEatingIn().set(dayIndex, objectAfter.isEatingIn());
        reportingService.save(weekReport);
    }

    @Override
    public Class<EatingInLog> listenerClass() {
        return EatingInLog.class;
    }

    @Override
    public EventType listenerType() {
        return EventType.CREATE;
    }
}
