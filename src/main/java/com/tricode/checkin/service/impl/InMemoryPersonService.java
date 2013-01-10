package com.tricode.checkin.service.impl;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Service
public class InMemoryPersonService implements PersonService {

    private static final Object newPersonLock = new Object();

    private final Map<Integer, Person> persons = new HashMap<Integer, Person>();

    private final EventManager eventManager;

    @Autowired
    public InMemoryPersonService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public Collection<Person> list() {
        return persons.values();
    }

    @Override
    public Person get(Integer id) {
        return persons.get(id);
    }

    @Override
    public Person save(Person person) {
        notNull(person);

        if (person.getStatus() == LocationStatus.IN && person.getSignInTime() == 0) {
            person.setSignInTime(new DateTime().getMillis());
        }

        if (person.getId() == null) {
            synchronized (newPersonLock) {
                if (person.getId() == null) {
                    int id = Collections.max(persons.keySet()) + 1;
                    person.setId(id);
                    persons.put(id, person);

                    eventManager.raiseCreateEvent(person);
                    return person;
                }
            }
        }

        if (person.getId() != null) {
            final Person oldValue = persons.put(person.getId(), person);

            eventManager.raiseUpdateEvent(oldValue, person);
        }
        return person;
    }
}
