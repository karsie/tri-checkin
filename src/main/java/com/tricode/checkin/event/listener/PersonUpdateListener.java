package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.LogService;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonUpdateListener implements EventListener<Person> {

    private final LogService logService;

    @Autowired
    public PersonUpdateListener(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void onEvent(Person personBefore, Person personAfter, EventType eventType) {
        if (personBefore.getStatus() != personAfter.getStatus()) {
            final StatusChangeLog log = new StatusChangeLog(personBefore.getId(),
                    personBefore.getStatus(),
                    personAfter.getStatus(),
                    new DateTime().getMillis());

            logService.addStatusChange(log);
        }
    }

    @Override
    public Class<Person> listenerClass() {
        return Person.class;
    }

    @Override
    public EventType listenerType() {
        return EventType.UPDATE;
    }
}
