package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.persistence.PersonRepository;
import com.tricode.checkin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class PersistentPersonService implements PersonService {

    private final PersonRepository personRepository;

    private final EventManager eventManager;

    @Autowired
    public PersistentPersonService(PersonRepository personRepository, EventManager eventManager) {
        this.personRepository = personRepository;
        this.eventManager = eventManager;
    }

    @Override
    public Collection<Person> list() {
        return personRepository.findAll();
    }

    @Override
    public Person get(int id) {
        return personRepository.get(id);
    }

    @Override
    public Person getByExternalId(String externalId) {
        return personRepository.getByExternalId(externalId);
    }

    @Override
    public Person save(Person person) {
        final Person oldValue;
        if (person.getId() != null) {
            oldValue = personRepository.get(person.getId());
        } else {
            oldValue = null;
        }

        final Person saved = personRepository.save(person);

        if (oldValue != null) {
            eventManager.raiseUpdateEvent(oldValue, person);
        } else {
            eventManager.raiseCreateEvent(saved);
        }
        return saved;
    }
}
