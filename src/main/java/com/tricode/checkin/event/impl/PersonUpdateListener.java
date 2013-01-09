package com.tricode.checkin.event.impl;

import com.tricode.checkin.event.EventListener;
import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.ReportData;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PersonUpdateListener implements EventListener<Person> {

    private final ReportingService reportingService;

    @Autowired
    public PersonUpdateListener(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @Override
    public void onEvent(Person eventObject) {
        if (eventObject.getStatus() == LocationStatus.OUT) {
            final DateTime signOutTime = new DateTime();
            final int dayIndex = signOutTime.getDayOfWeek() - 1; // MONDAY = 1, so index = 0

            Duration signedInFor = new Duration(eventObject.getSignInTime(), signOutTime.getMillis());

            ReportData reportData = reportingService.get(eventObject.getId(), signOutTime.getWeekyear(), signOutTime.getWeekOfWeekyear());
            signedInFor = signedInFor.plus(reportData.getDays().get(dayIndex));

            reportData.getDays().set(dayIndex, signedInFor.getMillis());
            reportingService.save(reportData);
        }
    }

    @Override
    public Class<Person> eventClass() {
        return Person.class;
    }

    @Override
    public EventType eventType() {
        return EventType.UPDATE;
    }
}
