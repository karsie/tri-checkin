package com.tricode.checkin.task;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonEatingInResetDailyTask implements RunnableTask {

    private static final RunnableTaskMetadata METADATA = new RunnableTaskMetadata(PersonEatingInResetDailyTask.class,
                "Resets the 'eating-in' flag for every person",
                "Every day, at 01:00");

    private final PersonService personService;
    private final EventManager eventManager;

    @Autowired
    public PersonEatingInResetDailyTask(PersonService personService, EventManager eventManager) {
        this.personService = personService;
        this.eventManager = eventManager;
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void runTask() {
        eventManager.stopForCurrentThread();
        final Collection<Person> persons = personService.list();
        for (Person person : persons) {
            if (person.isEatingIn()) {
                person.setEatingIn(false);
                personService.save(person);
            }
        }
        eventManager.startForCurrentThread();
    }

    @Override
    public RunnableTaskMetadata metadata() {
        return METADATA;
    }
}
