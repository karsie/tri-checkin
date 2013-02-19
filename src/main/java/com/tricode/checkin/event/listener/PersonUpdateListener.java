package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.EatingInLog;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.service.LogService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PersonUpdateListener implements EventListener<Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonUpdateListener.class);

    private final LogService logService;

    @Autowired
    public PersonUpdateListener(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void onEvent(Person personBefore, Person personAfter, EventType eventType) {
        if (hasStateChanged(personBefore, personAfter)) {
            log.debug("person status change received [{}] from {} to {}", personBefore.getId(), personBefore.getStatus(), personAfter.getStatus());

            final Log statusChangeLog = StatusChangeLog.Builder.withUserId(personBefore.getId())
                    .withStatusFrom(personBefore.getStatus()).withStatusTo(personAfter.getStatus())
                    .withTimestamp(new DateTime().getMillis()).get();

            logService.addLog(statusChangeLog);
        } else if (hasEatingInChanged(personBefore, personAfter)) {
            log.debug("person eating in received [{}] from {} to {}", personBefore.getId(), personBefore.isEatingIn(), personAfter.isEatingIn());

            final Log eatingInLog = EatingInLog.Builder.withUserId(personBefore.getId())
                    .withEatingIn(personAfter.isEatingIn())
                    .withTimestamp(new DateTime().getMillis()).get();

            logService.addLog(eatingInLog);
        }
    }

    private static boolean hasStateChanged(Person personBefore, Person personAfter) {
        return personBefore.getStatus() != personAfter.getStatus();
    }

    private static boolean hasEatingInChanged(Person personBefore, Person personAfter) {
        return personBefore.isEatingIn() != personAfter.isEatingIn();
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
