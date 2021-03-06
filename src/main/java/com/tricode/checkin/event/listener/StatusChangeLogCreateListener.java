package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.LogService;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StatusChangeLogCreateListener implements EventListener<StatusChangeLog> {

    private static final Logger log = LoggerFactory.getLogger(StatusChangeLogCreateListener.class);

    private final ReportingService reportingService;
    private final LogService logService;

    @Autowired
    public StatusChangeLogCreateListener(ReportingService reportingService, LogService logService) {
        this.reportingService = reportingService;
        this.logService = logService;
    }

    @Override
    public void onEvent(StatusChangeLog objectBefore, StatusChangeLog objectAfter, EventType eventType) {
        log.debug("SCL event");
        if (objectAfter.getStatusTo() == LocationStatus.OUT) {
            log.debug("checkout received for [{}], updating week report", objectAfter.getUserId());

            final DateTime signOutTime = new DateTime(objectAfter.getTimestamp());
            int dayIndex = signOutTime.getDayOfWeek() - 1; // MONDAY = 1, so index = 0

            if (dayIndex >= 0 && dayIndex < 5) {
                final StatusChangeLog previousStatus = logService.getPreviousStatusChangeForUser(objectAfter.getUserId(), LocationStatus.IN, objectAfter.getTimestamp());
                final WeekReport weekReport = reportingService.getWeek(objectAfter.getUserId(), signOutTime.getWeekyear(), signOutTime.getWeekOfWeekyear());

                Duration signedInFor = new Duration(previousStatus.getTimestamp(), objectAfter.getTimestamp());
                signedInFor = signedInFor.plus(weekReport.getDays().get(dayIndex));

                weekReport.getDays().set(dayIndex, signedInFor.getMillis());
                reportingService.save(weekReport);
            }
        }
    }

    @Override
    public Class<StatusChangeLog> listenerClass() {
        return StatusChangeLog.class;
    }

    @Override
    public EventType listenerType() {
        return EventType.CREATE;
    }
}
