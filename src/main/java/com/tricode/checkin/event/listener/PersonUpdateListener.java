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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonUpdateListener implements EventListener<Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonUpdateListener.class);

    private final LogService logService;

    @Autowired
    public PersonUpdateListener(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void onEvent(Person personBefore, Person personAfter, EventType eventType) {
        if (personBefore.getStatus() != personAfter.getStatus()) {
            log.debug("person status change received [{}] from {} to {}", personBefore.getId(), personBefore.getStatus(), personAfter.getStatus());

            final StatusChangeLog statusChangeLog = StatusChangeLog.Builder.withId(personBefore.getId())
                    .withStatusFrom(personBefore.getStatus()).withStatusTo(personAfter.getStatus())
                    .withTimestamp(new DateTime().getMillis()).createInstance();

            logService.addStatusChange(statusChangeLog);
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
