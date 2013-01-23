package com.tricode.checkin.task;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonResetNightlyTask {

    private final PersonService personService;

    @Autowired
    public PersonResetNightlyTask(PersonService personService) {
        this.personService = personService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void runTask() {
        final Collection<Person> persons = personService.list();
        for (Person person : persons) {
            if (person.isEatingIn()) {
                person.setEatingIn(false);
                personService.save(person);
            }
        }
    }
}
