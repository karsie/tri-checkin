package com.tricode.checkin.task;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonEatingInResetDailyTask implements RunnableTask {

    private final PersonService personService;

    @Autowired
    public PersonEatingInResetDailyTask(PersonService personService) {
        this.personService = personService;
    }

    @Override
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

    @Override
    public String schedule() {
        return "Every day, at 01:00";
    }

    @Override
    public String description() {
        return "Resets the 'eating-in' flag for every person";
    }
}
